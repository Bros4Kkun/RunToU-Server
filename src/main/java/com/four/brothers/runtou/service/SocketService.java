package com.four.brothers.runtou.service;

import com.four.brothers.runtou.domain.ChatMessage;
import com.four.brothers.runtou.domain.ChatRoom;
import com.four.brothers.runtou.domain.User;
import com.four.brothers.runtou.dto.ChatMessageDto;
import com.four.brothers.runtou.dto.EchoDto;
import com.four.brothers.runtou.dto.LoginDto;
import com.four.brothers.runtou.dto.SocketDto;
import com.four.brothers.runtou.repository.ChatMessageRepository;
import com.four.brothers.runtou.repository.ChatRoomRepository;
import com.four.brothers.runtou.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.four.brothers.runtou.dto.ChatMessageDto.*;
import static com.four.brothers.runtou.dto.LoginDto.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class SocketService {
  private final SimpMessagingTemplate simpTemplate;
  private final ChatMessageRepository chatMessageRepository;
  private final ChatRoomRepository chatRoomRepository;
  private final UserRepository userRepository;

  /**
   * 새로운 채팅방이 생성되었음을 Orderer에게 알리는 메서드
   * @param ordererPk 알릴 Orderer의 PK 값
   * @param chatRoomPk 생성된 채팅방의 PK 값
   */
  public void alertNewChatRoomToOrderer(long ordererPk, long chatRoomPk) {
    simpTemplate.convertAndSend("/queue/orderer/" + ordererPk,
      new SocketDto.NewChatRoomAlertResponse("New ChatRoom Requested.", chatRoomPk));
  }

  /**
   * 채팅방에 msg를 보내는 서비스
   * @param msg 메시지
   * @param chatRoomPk 메시지를 보낼 채팅방 pk 값
   * @param loginUser 메시지를 보낸 사용자
   */
  public void sendNewMsg(String msg, long chatRoomPk, LoginUser loginUser) {
    Optional<ChatRoom> chatRoom = chatRoomRepository.findChatRoomById(chatRoomPk);
    Optional<User> user = userRepository.findUserByAccountId(loginUser.getAccountId());

    if (chatRoom.isEmpty()) {
      throw new IllegalArgumentException("존재하지 않는 채팅방입니다.");
    }

    chatMessageRepository.saveChatMessage(user.get(), chatRoom.get(), msg);
    ChatMessage currentMsg = chatMessageRepository.findLatestChatMsgFromChatRoom(chatRoom.get(), msg).get();

    ChatMessageResponse response = new ChatMessageResponse(
      currentMsg.getId(),
      loginUser.getAccountId(),
      loginUser.getNickname(),
      chatRoomPk,
      msg,
      currentMsg.getCreatedDate()
    );

    simpTemplate.convertAndSend("/topic/chatroom/" + chatRoomPk, response);
  }
}
