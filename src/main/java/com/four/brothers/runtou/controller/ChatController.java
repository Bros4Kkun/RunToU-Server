package com.four.brothers.runtou.controller;

import com.four.brothers.runtou.dto.EchoDto;
import com.four.brothers.runtou.dto.LoginDto;
import com.four.brothers.runtou.service.SocketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import static com.four.brothers.runtou.dto.LoginDto.*;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ChatController {
  private final SocketService socketService;

  /**
   * 웹소켓을 통해, 웹소켓 통신하기
   * @param msg
   * @return
   */
  @MessageMapping("/chat/chatroom/{chatRoomPk}")
  public void sendChat(String msg, @DestinationVariable long chatRoomPk,
                          @SessionAttribute LoginUser loginUser) {
    log.info("msg: {}", msg);
    socketService.sendNewMsg(msg, chatRoomPk, loginUser);
  }
}
