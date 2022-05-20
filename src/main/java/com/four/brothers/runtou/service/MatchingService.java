package com.four.brothers.runtou.service;

import com.four.brothers.runtou.domain.*;
import com.four.brothers.runtou.dto.UserRole;
import com.four.brothers.runtou.exception.BadRequestException;
import com.four.brothers.runtou.exception.CanNotAccessException;
import com.four.brothers.runtou.exception.NoAuthorityException;
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
  public JobDoneRequestInfo requestToFinishJob(long matchingId, LoginUser loginPerformer) throws CanNotAccessException {
    final String requestMsg = "심부름을 완료했습니다! 심부름 완료 요청을 받아주세요.";
    Optional<Matching> doneRequestedMatchingOptional = matchingRepository.findById(matchingId);
    Matching doneRequestedMatching;
    Performer performer;
    JobDoneRequestInfo response;

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
    response = new JobDoneRequestInfo(doneRequestedMatching);
    simpTemplate.convertAndSend("/topic/match/done/" + response.getMatchingId(), response);

    //심부름 완료 요청 사실을 채팅방에 전달하기
    performer = doneRequestedMatching.getPerformer();
    sendMsgToMatchingChatRoom(doneRequestedMatching, performer, requestMsg);

    return response;
  }

  /**
   * 심부름 업무 최종 완료 메서드
   * 요청된 '심부름 완료 요청'을 수락하는 메서드
   * @param matchingPk
   * @param loginOrderer
   * @return
   * @throws CanNotAccessException
   * @throws NoAuthorityException
   */
  @Transactional
  public MatchingFinishInfo acceptJobDoneRequest(long matchingPk, LoginUser loginOrderer) throws CanNotAccessException, NoAuthorityException {
    final String acceptMsg = "업무 완료 요청을 수락했습니다! 고생 많으셨습니다. 감사합니다!";
    Optional<Matching> matchingOptional = matchingRepository.findById(matchingPk);
    Matching matching;
    OrderSheet orderSheet;
    Performer performer;
    Orderer orderer;
    MatchingFinishInfo response;

    //유효한 매칭pk값인지 확인
    checkExistMatching(matchingOptional);
    matching = matchingOptional.get();

    //해당 매칭에 대해 접근권한이 있는 사용자인지 확인
    checkRightAuthority(loginOrderer, matching);

    //심부름 요청자가 매칭 완료 요청을 승인하는지 확인
    if (loginOrderer.getRole() != UserRole.ORDERER) {
      throw new NoAuthorityException(MatchingExceptionCode.PERFORMER_CANNOT_ACCEPT_COMPLETION, "수행자는 매칭을 완료처리하지 못합니다.");
    }

    //중복된 승인인지 확인
    if (matching.getIsCompleted() == true) {
      throw new BadRequestException(MatchingExceptionCode.ALREADY_ACCEPTED_COMPLETION, "이미 수행 완료 요청을 수락했습니다.");
    }

    //매칭 업무 최종 완료 처리
    matching.complete();
    //심부름 수행자를 다시 업무 가능 상태로 변경
    performer = matching.getPerformer();
    performer.finishJob();
    //심부름 수행자에게 포인트 지급
    orderSheet = matching.getOrderSheet();
    performer.earnPoint(orderSheet.getCost());
    //채팅방에 알림
    orderer = matching.getOrderSheet().getOrderer();
    sendMsgToMatchingChatRoom(matching, orderer, acceptMsg);

    response = new MatchingFinishInfo(matching);
    return response;
  }

  /**
   * 해당 요청서와 연관된 사용자들의 채팅방에 메시지를 전달하는 메서드
   * '매칭' 과 연관된 채팅방 (unique) 에 메시지를 전달한다.
   * @param matching 연관된 매칭
   * @param writer 채팅 메시지 작성자
   * @param msg 메시지 내용
   */
  private void sendMsgToMatchingChatRoom(Matching matching, User writer, String msg) {
    String ordererAccountId = matching.getOrderSheet().getOrderer().getAccountId();
    String performerAccountId = matching.getPerformer().getAccountId(); //로그인된 사용자(완료 요청을 한 사용자)가 수행자이므로

    Orderer orderer = ordererRepository.findOrdererByAccountId(ordererAccountId).get();
    Performer performer = performerRepository.findPerformerByAccountId(performerAccountId).get();
    OrderSheet orderSheet = matching.getOrderSheet();
    ChatRoom chatRoom = chatRoomRepository.findByOrdererAndPerformerAndOrderSheet(orderer, performer, orderSheet).get();
    ChatMessage chatMessage;

    chatMessageRepository.saveChatMessage(writer, chatRoom, msg);
    chatMessage = chatMessageRepository.findLatestChatMsgFromChatRoom(chatRoom, msg).get();

    ChatMessageResponse response = new ChatMessageResponse(
      chatMessage.getId(),
      writer.getAccountId(),
      writer.getNickname(),
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
