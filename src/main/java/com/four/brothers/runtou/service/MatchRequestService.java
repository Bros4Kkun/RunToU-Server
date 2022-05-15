package com.four.brothers.runtou.service;

import com.four.brothers.runtou.domain.*;
import com.four.brothers.runtou.dto.MatchRequestDto;
import com.four.brothers.runtou.dto.UserRole;
import com.four.brothers.runtou.exception.BadRequestException;
import com.four.brothers.runtou.exception.CanNotAccessException;
import com.four.brothers.runtou.exception.code.ChatRoomExceptionCode;
import com.four.brothers.runtou.exception.code.MatchRequestExceptionCode;
import com.four.brothers.runtou.repository.ChatMessageRepository;
import com.four.brothers.runtou.repository.ChatRoomRepository;
import com.four.brothers.runtou.repository.MatchRequestRepository;
import com.four.brothers.runtou.repository.MatchingRepository;
import com.four.brothers.runtou.repository.user.PerformerRepository;
import com.four.brothers.runtou.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.four.brothers.runtou.dto.ChatMessageDto.*;
import static com.four.brothers.runtou.dto.LoginDto.*;
import static com.four.brothers.runtou.dto.MatchingDto.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class MatchRequestService {
  private final MatchRequestRepository matchRequestRepository;
  private final MatchingRepository matchingRepository;
  private final ChatRoomRepository chatRoomRepository;
  private final PerformerRepository performerRepository;
  private final ChatMessageRepository chatMessageRepository;
  private final UserRepository userRepository;

  private final ChatService chatService;

  private final SimpMessagingTemplate simpTemplate;

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
   * 매칭요청을 수락하는 메서드
   * @param matchRequestPk 수락할 매칭요청 pk 값
   * @return 성공여부
   */
  @Transactional
  public MatchInfo acceptRequestedMatch(long matchRequestPk, LoginUser loginUser) throws Exception {
    Optional<MatchRequest> matchRequestOptional = matchRequestRepository.findById(matchRequestPk);
    MatchRequest matchRequest;
    OrderSheet orderSheetAccepted;
    Performer performerAccepted;
    MatchInfo result;

    //매칭요청을 수락상태로 변경할 수 있는지 확인
    checkMatchRequestCanAccept(loginUser, matchRequestOptional);

    //매칭요청을 수락상태로 변경
    matchRequest = matchRequestOptional.get();
    matchRequest.accept(); //isAccepted 와 isOrderSheetMatched 를 true로 변경

    //매칭 요청을 수락받은 '심부름 수행자'를 doingJob 상태로 변환
    performerAccepted = matchRequest.getPerformer();
    performerAccepted.doJob();

    //매칭 저장
    orderSheetAccepted = matchRequest.getOrderSheet();
    matchingRepository.saveMatching(orderSheetAccepted, performerAccepted, false, null);
    Matching savedMatching = matchingRepository.findByOrderSheet(orderSheetAccepted).get();
    result = new MatchInfo(savedMatching);

    //매칭요청을 한 다른 수행자들에게 매칭이 완료되었음을 알려주기
    sendMsgToOtherMatchRequesters(loginUser, matchRequest);

    //요청이 수락된 사실을 관계자들에게 알려주기 (수락한 요청자, 수락받은 수행자)
    sendMsgToAcceptedUsers(loginUser, matchRequest);

    return result;
  }

  /**
   * 매칭 요청이 수락되었음을 '수락한 요청자'와 '수락받은 수행자'에게 알려주는 메서드
   * @param loginUser 수락한 요청자 (로그인된 사용자)
   * @param matchRequest 수락된 매칭요청
   */
  private void sendMsgToAcceptedUsers(LoginUser loginUser, MatchRequest matchRequest) {
    final String acceptMsg = "심부름 매칭 요청을 수락했습니다! 잘 부탁드려요 :)";
    OrderSheet acceptedOrderSheet = matchRequest.getOrderSheet();
    Orderer acceptOrderer = acceptedOrderSheet.getOrderer();
    Performer acceptedPerformer = matchRequest.getPerformer();
    ChatRoom chatRoomOfAcceptedMatchRequest = chatRoomRepository
      .findByOrdererAndPerformerAndOrderSheet(acceptOrderer, acceptedPerformer, acceptedOrderSheet)
      .get();

    chatService.sendNewMsg(acceptMsg, chatRoomOfAcceptedMatchRequest.getId(), loginUser);
  }

  /**
   * 매칭요청을 보냈으나 매칭이 완료되었을 때, 매칭되지 못한 사용자에게 안내하는 메서드
   * @param loginUser 매칭요청을 수락한 사용자 (심부름 요청자)
   * @param acceptedMatchRequest 수락된 매칭요청
   */
  private void sendMsgToOtherMatchRequesters(LoginUser loginUser,
                                             MatchRequest acceptedMatchRequest) throws CanNotAccessException {
    final String completedMsg = "다른 유저와 매칭되었습니다! 아쉽지만 다음에 부탁드릴게요. :)";
    OrderSheet orderSheetAccepted = acceptedMatchRequest.getOrderSheet();
    Performer performerAccepted = acceptedMatchRequest.getPerformer();
    //매칭 수락된 수행자 계정ID
    String acceptedPerformerAccountId = performerAccepted.getAccountId();
    //매칭을 완료한 현재 요청서와 연관된 모든 채팅방
    List<ChatRoom> chatRoomsWithCurrentOrderSheet;

    if (loginUser.getRole() != UserRole.ORDERER) {
      throw new CanNotAccessException(MatchRequestExceptionCode.WRONG_USER, "매칭을 수락한 사용자는 '심부름 요청자'이어야 합니다.");
    }

    chatRoomsWithCurrentOrderSheet = chatRoomRepository.findByOrderSheet(orderSheetAccepted);

    //현재 매칭 수락된 요청서와 연관된 모든 채팅방마다 반복
    for (ChatRoom relatedChatRoom : chatRoomsWithCurrentOrderSheet) {
      //연관된 채팅방의 수행자
      Performer relatedPerformer = relatedChatRoom.getPerformer();
      //연관된 채팅방의 수행자 계정ID
      String relatedPerformerAccountId = relatedPerformer.getAccountId();

      //현재 수락된 수행인의 채팅방인 경우 생략
      if (relatedPerformerAccountId.equals(acceptedPerformerAccountId)) continue;

      //매칭이 수락된 요청서와 연관있지만, 매칭되지 않은 '매칭요청' 엔티티
      MatchRequest otherMatchRequest =
        matchRequestRepository.findByOrderSheetAndPerform(orderSheetAccepted, relatedPerformer).get();

      //연관된 매칭요청을 거절상태로 변경
      otherMatchRequest.rejectByOtherMatchRequest();
      //다른 사용자와 매칭되었다고 알리기
      chatService.sendNewMsg(completedMsg, relatedChatRoom.getId(), loginUser);
    }
  }

  /**
   * 매칭요청을 수락할 수 있는지 확인하는 메서드
   * @param loginUser
   * @param matchRequestOptional
   * @throws Exception
   */
  private void checkMatchRequestCanAccept(LoginUser loginUser,
                                          Optional<MatchRequest> matchRequestOptional) throws Exception {
    MatchRequest matchRequest; //매칭요청 엔티티
    String ordererAccountIdOfOrderSheet; //매칭요청을 받은 요청자의 계정ID

    //유효한 매칭요청 pk 값인지 확인
    if (matchRequestOptional.isEmpty()) {
      throw new BadRequestException(MatchRequestExceptionCode.WRONG_MATCH_REQUEST, "존재하지 않는 매칭요청 id입니다.");
    }

    //적절한 요청자인지 확인
    matchRequest = matchRequestOptional.get();
    ordererAccountIdOfOrderSheet = matchRequest.getOrderSheet().getOrderer().getAccountId();
    if (!ordererAccountIdOfOrderSheet.equals(loginUser.getAccountId())) {
      throw new CanNotAccessException(MatchRequestExceptionCode.WRONG_USER, "본 매칭요청에 해당되지 않는 심부름 요청자입니다.");
    }

    //이미 수락된 매칭요청인지 확인
    if (matchRequest.getIsAccepted()) {
      throw new BadRequestException(MatchRequestExceptionCode.ALREADY_ACCEPTED, "이미 수락된 매칭요청입니다.");
    }

    //다른 유저와 매칭되었는지 확인
    Optional<Matching> alreadyExistMatchAfterAccept = matchingRepository.findByOrderSheet(matchRequest.getOrderSheet());
    if (alreadyExistMatchAfterAccept.isPresent()) {
      throw new BadRequestException(MatchRequestExceptionCode.ALREADY_ACCEPTED_WITH_OTHER_USER, "해당 요청서가 이미 다른 유저와 매칭되었습니다.");
    }
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
        MatchRequestExceptionCode.WRONG_USER, "오직 수행자만 매칭을 요청할 수 있습니다."
      );
    }

    //매칭을 요청한 사용자가 해당 채팅방에 참여중이지 않을 경우
    chatRoomPerformerAccountId = chatRoom.get().getPerformer().getAccountId(); //해당 채팅방의 수행자 계정 id
    if (!loginUser.getAccountId().equals(chatRoomPerformerAccountId)) {
      throw new CanNotAccessException(
        MatchRequestExceptionCode.WRONG_USER, "현재 채팅에 참여 중이지 않은 수행자입니다."
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
}
