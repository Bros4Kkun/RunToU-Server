package com.four.brothers.runtou.service;

import com.four.brothers.runtou.domain.*;
import com.four.brothers.runtou.dto.ChatMessageDto;
import com.four.brothers.runtou.dto.MatchRequestDto;
import com.four.brothers.runtou.dto.UserRole;
import com.four.brothers.runtou.exception.BadRequestException;
import com.four.brothers.runtou.exception.CanNotAccessException;
import com.four.brothers.runtou.exception.code.ChatRoomExceptionCode;
import com.four.brothers.runtou.exception.code.MatchRequestExceptionCode;
import com.four.brothers.runtou.exception.code.MatchingExceptionCode;
import com.four.brothers.runtou.repository.ChatMessageRepository;
import com.four.brothers.runtou.repository.ChatRoomRepository;
import com.four.brothers.runtou.repository.MatchRequestRepository;
import com.four.brothers.runtou.repository.MatchingRepository;
import com.four.brothers.runtou.repository.user.PerformerRepository;
import com.four.brothers.runtou.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.four.brothers.runtou.dto.ChatMessageDto.*;
import static com.four.brothers.runtou.dto.LoginDto.*;
import static com.four.brothers.runtou.dto.MatchDto.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class MatchingService {
  private final MatchingRepository matchingRepository;
  private final MatchRequestRepository matchRequestRepository;
  private final ChatRoomRepository chatRoomRepository;
  private final PerformerRepository performerRepository;
  private final ChatMessageRepository chatMessageRepository;
  private final UserRepository userRepository;

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
   * 해당 pk값을 갖는 매칭의 상세정보 반환
   * @param matchingPk 확인할 매칭 PK 값
   * @param loginUser
   * @return
   */
  @Transactional
  public MatchInfo showMatchDetail(long matchingPk, LoginUser loginUser) throws CanNotAccessException {
    Optional<Matching> matching = matchingRepository.findById(matchingPk);

    if (matching.isEmpty()) {
      throw new IllegalArgumentException("존재하지 않는 매칭 id입니다.");
    }

    checkRightAuthority(loginUser, matching);

    return new MatchInfo(matching.get());
  }

  /**
   * 매칭을 요청하는 메서드
   * @param chatRoomPk 매칭을 요청하는데 사용된 채팅방의 pk값
   * @param loginUser
   * @return
   */
  @Transactional
  public boolean requestMatching(long chatRoomPk, LoginUser loginUser) throws Exception {
    Optional<ChatRoom> chatRoom = chatRoomRepository.findChatRoomById(chatRoomPk);
    Optional<MatchRequest> matchRequest = null;
    User matchRequestUser;
    String matchRequestMsg;
    ChatMessage currentMsg;
    ChatMessageResponse response;

    isRightMatchRequest(loginUser, chatRoom);

    //매칭요청 엔티티 저장
    try {
      matchRequestRepository.saveMatchRequest(chatRoom.get().getOrderSheet(), chatRoom.get().getPerformer());
      matchRequest = matchRequestRepository.findByOrderSheetAndPerform(chatRoom.get().getOrderSheet(), chatRoom.get().getPerformer());
    } catch (DataIntegrityViolationException e) {
      throw new BadRequestException(
        MatchRequestExceptionCode.ALREADY_REQUESTED, "이미 동일한 요청서에 대해 매칭요청을 했습니다."
      );
    }

    MatchRequestDto.MatchRequestInfo matchRequestInfo = new MatchRequestDto.MatchRequestInfo(matchRequest.get());

    //매칭 요청 사실을 STOMP로 전달
    matchRequestUser = userRepository.findUserByAccountId(loginUser.getAccountId()).get();
    matchRequestMsg = "본 심부름에 대한 매칭을 요청했습니다! 알림을 확인해주세요.";
    chatMessageRepository.saveChatMessage(matchRequestUser, chatRoom.get(), matchRequestMsg);
    currentMsg = chatMessageRepository.findLatestChatMsgFromChatRoom(chatRoom.get(), matchRequestMsg).get();
    response = new ChatMessageResponse(
      currentMsg.getId(),
      loginUser.getAccountId(),
      loginUser.getNickname(),
      chatRoomPk,
      matchRequestMsg,
      currentMsg.getCreatedDate()
    );
    simpTemplate.convertAndSend("/topic/chatroom/" + chatRoomPk, response); //채팅방으로 전송
    simpTemplate.convertAndSend("/topic/match/chatroom/" + chatRoomPk, matchRequestInfo); //알림용 topic으로 전송

    return true;
  }

  /**
   * 적절한 매칭요청인지 확인하는 메서드
   * @param loginUser
   * @param chatRoom
   * @throws CanNotAccessException
   */
  private void isRightMatchRequest(LoginUser loginUser, Optional<ChatRoom> chatRoom) throws Exception {
    Performer matchRequestedPerformer;
    String chatRoomPerformerAccountId;
    //잘못된 채팅방 id 일 경우
    if (chatRoom.isEmpty()) {
      throw new BadRequestException(ChatRoomExceptionCode.WRONG_NUMBER_OF_CHAT_ROOM,"존재하지 않는 채팅방 id입니다.");
    }
    //수행자가 요청한 것이 아닐 경우
    if (loginUser.getRole() != UserRole.PERFORMER) {
      throw new BadRequestException(
        MatchRequestExceptionCode.WRONG_USER_ROLE, "오직 수행자만 매칭을 요청할 수 있습니다."
      );
    }
    //매칭을 요청한 사용자가 해당 채팅방에 참여중이지 않을 경우
    chatRoomPerformerAccountId = chatRoom.get().getPerformer().getAccountId(); //해당 채팅방의 수행자 계정 id
    if (!loginUser.getAccountId().equals(chatRoomPerformerAccountId)) {
      throw new CanNotAccessException(
        MatchRequestExceptionCode.WRONG_USER_ROLE, "현재 채팅에 참여 중이지 않은 수행자입니다."
      );
    }
    //매칭을 요청한 수행자가 현재 심부름을 수행 중일 경우
    matchRequestedPerformer = performerRepository.findPerformerByAccountId(loginUser.getAccountId()).get();
    if (matchRequestedPerformer.getIsDoingJobNow()) {
      throw new BadRequestException(
        MatchRequestExceptionCode.ALREADY_DOING_JOB, "매칭을 요청한 수행자가 현재 심부름을 수행 중입니다."
      );
    }
  }

  /**
   * 해당 매칭에 접근하려는 사용자가 접근 권한을 가지고 있는지 확인하는 메서드
   * @param loginUser
   * @param matching
   * @throws CanNotAccessException
   */
  private void checkRightAuthority(LoginUser loginUser, Optional<Matching> matching) throws CanNotAccessException {
    String ordererAccountId = matching.get().getOrderSheet().getOrderer().getAccountId();
    String performerAccountId = matching.get().getPerformer().getAccountId();
    String loginUserAccountId = loginUser.getAccountId();
    //접근 권한이 있다면
    if (ordererAccountId.equals(loginUserAccountId) || performerAccountId.equals(loginUserAccountId)) {
      return;
    }
    throw new CanNotAccessException(MatchingExceptionCode.NO_AUTHORITY, "해당 매칭에 대한 접근 권한이 없습니다.");
  }
}
