package com.four.brothers.runtou.service;

import com.four.brothers.runtou.domain.*;
import com.four.brothers.runtou.dto.UserRole;
import com.four.brothers.runtou.exception.CanNotAccessException;
import com.four.brothers.runtou.exception.code.ChatRoomExceptionCode;
import com.four.brothers.runtou.repository.ChatRoomRepository;
import com.four.brothers.runtou.repository.OrderSheetRepository;
import com.four.brothers.runtou.repository.user.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.four.brothers.runtou.dto.ChatMessageDto.*;
import static com.four.brothers.runtou.dto.ChatRoomDto.*;
import static com.four.brothers.runtou.dto.LoginDto.*;
import static com.four.brothers.runtou.dto.OrdererDto.*;
import static com.four.brothers.runtou.dto.PerformerDto.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatRoomService {
  private final ChatRoomRepository chatRoomRepository;
  private final OrdererRepository ordererRepository;
  private final PerformerRepository performerRepository;
  private final OrderSheetRepository orderSheetRepository;
  private final UserRepository userRepository;

  /**
   * 요청에 의해 채팅방을 새로 만드는 메서드
   * 만약 기존의 채팅방이 이미 존재한다면, 기존의 것을 반환한다.
   * @param orderSheetPk
   * @param loginUser
   * @return
   * @throws CanNotAccessException
   */
  @Transactional
  public NewChatRoomResponse makeNewChatRoomByPerformer(long orderSheetPk, LoginUser loginUser) throws CanNotAccessException {
    Optional<OrderSheet> orderSheet;
    Optional<Orderer> orderer;
    Optional<Performer> performer;
    boolean isNewRoom = false;

    //만약 채팅을 신청한 사람이 '심부름 수행자'가 아니라면
    if (loginUser.getRole() != UserRole.PERFORMER) {
      throw new CanNotAccessException(ChatRoomExceptionCode.REQUEST_USER_IS_NOT_PERFORMER, "오직 심부름 수행자만 채팅을 요청할 수 있습니다.");
    }

    orderSheet = orderSheetRepository.findById(orderSheetPk);

    if (orderSheet.isEmpty()) {
      throw new IllegalArgumentException("존재하지 않는 OrderSheet id입니다.");
    }

    orderer = Optional.ofNullable(orderSheet.get().getOrderer());
    performer = performerRepository.findPerformerByAccountId(loginUser.getAccountId());

    isAllExistPkForNewChatRoom(orderer, performer, orderSheet); //실존하는 데이터인지 검사

    List<ChatRoom> sameChatRoomList = chatRoomRepository.findSameChatRoom(orderer.get(), performer.get(), orderSheet.get());
    if (sameChatRoomList.size() == 0) { //기존 채팅방이 존재하지 않는다면
      chatRoomRepository.saveChatRoom(orderer.get(), performer.get(), orderSheet.get());
      sameChatRoomList = chatRoomRepository.findSameChatRoom(orderer.get(), performer.get(), orderSheet.get());
      isNewRoom = true;
    }

    return new NewChatRoomResponse(orderer.get().getId(),
      performer.get().getId(),
      orderSheetPk,
      sameChatRoomList.get(0).getId(),
      isNewRoom);
  }

  /**
   * 실제로 존재하는 PK 값인지 검사하는 메서드
   * @param orderer
   * @param performer
   * @param orderSheet
   */
  private void isAllExistPkForNewChatRoom(Optional<Orderer> orderer, Optional<Performer> performer, Optional<OrderSheet> orderSheet) {
    if (orderer.isEmpty()) {
      throw new IllegalArgumentException("존재하지 않는 Orderer id입니다.");
    }

    if (performer.isEmpty()) {
      throw new IllegalArgumentException("존재하지 않는 Performer id입니다.");
    }

    if (orderSheet.isEmpty()) {
      throw new IllegalArgumentException("존재하지 않는 OrderSheet id입니다.");
    }
  }

  /**
   * 해당 PK값을 갖는 채팅방 정보를 반환하는 메서드
   * @param chatRoomPk 확인할 채팅방의 pk값
   * @return
   */
  public ExistChatRoomResponse loadExistChatRoomInfo(long chatRoomPk) {
    Optional<ChatRoom> chatRoom = chatRoomRepository.findChatRoomById(chatRoomPk);
    ExistChatRoomResponse response = new ExistChatRoomResponse();

    if (chatRoom.isEmpty()) {
      throw new IllegalArgumentException("존재하지 않는 ChatRoom id입니다.");
    }

    List<ChatMessage> chatMessages = chatRoom.get().getChatMessages();
    List<ChatMessageResponse> chatMessageDtoList = new ArrayList<>();
    chatMessageListToDtoList(chatMessages, chatMessageDtoList);

    response.setChatRoomPk(chatRoomPk);
    response.setMessageList(chatMessageDtoList);
    response.setOrdererInfo(new SimpOrdererInfo(chatRoom.get().getOrderer()));
    response.setPerformerInfo(new SimpPerformerInfo(chatRoom.get().getPerformer()));
    response.setOrdererSheetPk(chatRoom.get().getOrderSheet().getId());
    response.setMatched(false);

    return response;
  }

  private void chatMessageListToDtoList(List<ChatMessage> chatMessages, List<ChatMessageResponse> chatMessageDtoList) {
    chatMessages.stream().forEach((item) -> {
      ChatMessageResponse message = new ChatMessageResponse();
      message.setChatMessagePk(item.getId());
      message.setWriterAccountId(item.getUser().getAccountId());
      message.setWriterNickname(item.getUser().getNickname());
      message.setChatRoomPk(item.getChatRoom().getId());
      message.setContent(item.getContent());
      message.setCreatedDate(item.getCreatedDate());
      chatMessageDtoList.add(message);
    });
  }

  /**
   * 로그인된 사용자의 모든 채팅방 리스트를 반환하는 메서드
   * @param loginUser 로그인된 사용자
   * @return
   */
  public List<SimpleChatRoomInfo> loadAllChatRooms(LoginUser loginUser) {
    User user = userRepository.findUserByAccountId(loginUser.getAccountId()).get();
    List<ChatRoom> chatRoomByUser = chatRoomRepository.findChatRoomByUser(user);
    List<SimpleChatRoomInfo> result = new ArrayList<>();

    chatRoomListToDtoList(chatRoomByUser, result);

    return result;
  }

  /**
   * ChatRoom 리스트를 dto 타입인 SimpleChatRoomInfo 리스트로 변환하는 메서드
   * @param chatRoomByUser
   * @param result
   */
  private void chatRoomListToDtoList(List<ChatRoom> chatRoomByUser, List<SimpleChatRoomInfo> result) {
    chatRoomByUser.stream().forEach(
      (item) -> {
        SimpleChatRoomInfo info = new SimpleChatRoomInfo();

        if (item.getChatMessages().size() != 0) {
          int lastMsgIndex = item.getChatMessages().size() - 1;
          info.setLatestChatMessage(item.getChatMessages().get(lastMsgIndex).getContent());
        }
        info.setChatRoomPk(item.getId());
        info.setOrdererInfo(new SimpOrdererInfo(item.getOrderer()));
        info.setPerformerInfo(new SimpPerformerInfo(item.getPerformer()));
        info.setOrdererSheetPk(item.getOrderSheet().getId());
        result.add(info);
      }
    );
  }
}
