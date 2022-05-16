package com.four.brothers.runtou.service;

import com.four.brothers.runtou.domain.*;
import com.four.brothers.runtou.dto.UserRole;
import com.four.brothers.runtou.exception.BadRequestException;
import com.four.brothers.runtou.exception.CanNotAccessException;
import com.four.brothers.runtou.exception.code.MatchingExceptionCode;
import com.four.brothers.runtou.repository.ChatMessageRepository;
import com.four.brothers.runtou.repository.ChatRoomRepository;
import com.four.brothers.runtou.repository.MatchingRepository;
import com.four.brothers.runtou.repository.user.OrdererRepository;
import com.four.brothers.runtou.repository.user.PerformerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.four.brothers.runtou.dto.ChatMessageDto.*;
import static com.four.brothers.runtou.dto.LoginDto.*;
import static com.four.brothers.runtou.dto.MatchingDto.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class MatchingService {
  private final MatchingRepository matchingRepository;
  private final ChatRoomRepository chatRoomRepository;
  private final OrdererRepository ordererRepository;
  private final PerformerRepository performerRepository;
  private final ChatMessageRepository chatMessageRepository;

  private final SimpMessagingTemplate simpTemplate;

  /**
   * 로그인한 사용자의 모든 매칭 정보 응답
   * @param loginUser
   * @return
   */
  @Transactional
  public List<SimpMatchInfo> showAllMatches(LoginUser loginUser) {
    List<SimpMatchInfo> result = new ArrayList<>();
    List<Matching> allMatchingByUserAccountId = matchingRepository.findMatchingByUserAccountId(loginUser.getAccountId());
    allMatchingByUserAccountId.stream().forEach((item) -> result.add(new SimpMatchInfo(item)));
    return result;
  }

  /**
   * 로그인한 사용자의 모든 매칭 정보 중, 현재 수행중인 것만 응답
   * @param loginUser
   * @return
   */
  @Transactional
  public List<SimpMatchInfo> showMatchesDuringJob(LoginUser loginUser) {
    List<SimpMatchInfo> result = new ArrayList<>();
    List<Matching> allMatchingByUserAccountId = matchingRepository.findMatchingByUserAccountId(loginUser.getAccountId(), false);
    allMatchingByUserAccountId.stream().forEach((item) -> result.add(new SimpMatchInfo(item)));
    return result;
  }

  /**
   * 로그인한 사용자의 모든 매칭 정보 중, 완료된 것만 응답
   * @param loginUser
   * @return
   */
  @Transactional
  public List<SimpMatchInfo> showDoneMatches(LoginUser loginUser) {
    List<SimpMatchInfo> result = new ArrayList<>();
    List<Matching> allMatchingByUserAccountId = matchingRepository.findMatchingByUserAccountId(loginUser.getAccountId(), true);
    allMatchingByUserAccountId.stream().forEach((item) -> result.add(new SimpMatchInfo(item)));
    return result;
  }

  /**
   * 로그인한 사용자의 모든 매칭 정보 중, 업무 완료 요청이 된 것만 응답
   * @param loginUser
   * @return
   */
  @Transactional
  public List<SimpMatchInfo> showCompletionRequestedMatches(LoginUser loginUser) {
    List<SimpMatchInfo> result = new ArrayList<>();
    List<Matching> completionRequestedMatchings = matchingRepository.findCompletionRequestedMatchingByUserAccountId(loginUser.getAccountId());
    completionRequestedMatchings.stream().forEach((item) -> result.add(new SimpMatchInfo(item)));
    return result;
  }

  /**
   * 해당 pk값을 갖는 매칭의 상세정보 반환
   * @param matchingPk 확인할 매칭 PK 값
   * @param loginUser
   * @return
   */
  @Transactional
  public MatchInfo showMatchDetail(long matchingPk, LoginUser loginUser) throws CanNotAccessException {
    Optional<Matching> matching = matchingRepository.findById(matchingPk);

    checkExistMatching(matching);

    checkRightAuthority(loginUser, matching.get());

    return new MatchInfo(matching.get());
  }

  /**
   * 해당 매칭을 완료하여, 완료상태로 만들기를 '심부름 요청자'에게 요청하는 메서드
   * @param matchingId 완료 요청할 매칭 pk 값
   * @param loginPerformer 심부름 수행을 완료한 '심부름 수행자'
   * @return
   */
  @Transactional
  public JobDoneResponse requestToFinishJob(long matchingId, LoginUser loginPerformer) throws CanNotAccessException {
    Optional<Matching> doneRequestedMatchingOptional = matchingRepository.findById(matchingId);
    Matching doneRequestedMatching;
    JobDoneResponse response;

    //심부름을 완료한다고 요청하는 사람이 수행자인지 확인
    if (loginPerformer.getRole() != UserRole.PERFORMER) {
      throw new BadRequestException(MatchingExceptionCode.ORDERER_REQUEST_COMPLETION, "수행자만 심부름 완료를 요청할 수 있습니다.");
    }

    //유효한 매칭 pk값인지 확인
    checkExistMatching(doneRequestedMatchingOptional);

    doneRequestedMatching = doneRequestedMatchingOptional.get();
    //접근권한이 있는지 확인
    checkRightAuthority(loginPerformer, doneRequestedMatching);

    //중복된 요청인지 확인
    if (doneRequestedMatching.getCompletionRequest()) {
      throw new BadRequestException(MatchingExceptionCode.ALREADY_REQUESTED_COMPLETION, "이미 수행 완료 요청을 했습니다.");
    }

    //심부름 완료 요청하기
    doneRequestedMatching.requestCompletion();

    //심부름 완료 요청을 알리기
    response = new JobDoneResponse(doneRequestedMatching);
    simpTemplate.convertAndSend("/topic/match/done/" + response.getMatchingId(), response);

    //심부름 완료 요청 사실을 채팅방에 전달하기
    sendMatchingCompletionRequestMsg(loginPerformer, doneRequestedMatching);

    return response;
  }

  /**
   * 채팅방에 '심부름 수행 완료 요청 사실'을 전달하는 메서드
   * @param loginPerformer 심부름을 수행 완료한 로그인된 '심부름 수행자'
   * @param doneRequestedMatching 심부름 완료 처리를 할 매칭
   */
  private void sendMatchingCompletionRequestMsg(LoginUser loginPerformer, Matching doneRequestedMatching) {
    final String msg = "심부름을 완료했습니다! 심부름 완료 요청을 받아주세요.";

    String ordererAccountId = doneRequestedMatching.getOrderSheet().getOrderer().getAccountId();
    String performerAccountId = loginPerformer.getAccountId(); //로그인된 사용자(완료 요청을 한 사용자)가 수행자이므로

    Orderer orderer = ordererRepository.findOrdererByAccountId(ordererAccountId).get();
    Performer performer = performerRepository.findPerformerByAccountId(performerAccountId).get();
    OrderSheet orderSheet = doneRequestedMatching.getOrderSheet();
    ChatRoom chatRoom = chatRoomRepository.findByOrdererAndPerformerAndOrderSheet(orderer, performer, orderSheet).get();
    ChatMessage chatMessage;

    chatMessageRepository.saveChatMessage(performer, chatRoom, msg);
    chatMessage = chatMessageRepository.findLatestChatMsgFromChatRoom(chatRoom, msg).get();

    ChatMessageResponse response = new ChatMessageResponse(
      chatMessage.getId(),
      performer.getAccountId(),
      performer.getNickname(),
      chatRoom.getId(),
      msg,
      chatMessage.getCreatedDate()
    );

    simpTemplate.convertAndSend("/topic/chatroom/" + chatRoom.getId(), response);
  }

  /**
   * 실제로 존재하는 매칭 pk 값인지 확인
   * @param matchingOptional
   */
  private void checkExistMatching(Optional<Matching> matchingOptional) {
    if (matchingOptional.isEmpty()) {
      throw new BadRequestException(MatchingExceptionCode.WRONG_ID, "존재하지 않는 매칭 id 입니다.");
    }
  }

  /**
   * 해당 매칭에 접근하려는 사용자가 접근 권한을 가지고 있는지 확인하는 메서드
   * @param loginUser
   * @param matching
   * @throws CanNotAccessException
   */
  private void checkRightAuthority(LoginUser loginUser, Matching matching) throws CanNotAccessException {
    String ordererAccountId = matching.getOrderSheet().getOrderer().getAccountId();
    String performerAccountId = matching.getPerformer().getAccountId();
    String loginUserAccountId = loginUser.getAccountId();
    //접근 권한이 있다면
    if (ordererAccountId.equals(loginUserAccountId) || performerAccountId.equals(loginUserAccountId)) {
      return;
    }
    throw new CanNotAccessException(MatchingExceptionCode.NO_AUTHORITY, "해당 매칭에 대한 접근 권한이 없습니다.");
  }
}
