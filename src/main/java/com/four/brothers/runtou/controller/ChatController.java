package com.four.brothers.runtou.controller;

import com.four.brothers.runtou.service.SocketService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.bind.annotation.*;

import static com.four.brothers.runtou.dto.JwtDto.*;
import static com.four.brothers.runtou.dto.LoginDto.*;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ChatController {
  private final SocketService socketService;

  /**
   * 채팅
   * @param msg 보낼 msg
   * @param chatRoomPk 채팅방 번호
   */
  @MessageMapping("/chat/chatroom/{chatRoomPk}")
  public void sendChat(String msg, @DestinationVariable long chatRoomPk,
                       SimpMessageHeaderAccessor headerAccessor) {
    log.info("Send Msg Destination: {}", headerAccessor.getDestination());
    log.info("Send Msg Authentication: {}", headerAccessor.getFirstNativeHeader("Authentication"));

    String token = (String) headerAccessor.getFirstNativeHeader("Authentication");
    LoginUser loginUser = socketService.parseJwtToGetLoginUser(token);

    socketService.sendNewMsg(msg, chatRoomPk, loginUser);
  }

  @GetMapping("/api/chat/jwt")
  public JwtDtoResponse getJwtToken(@Parameter(hidden = true) @SessionAttribute LoginUser loginUser) {
    return new JwtDtoResponse(socketService.createJwtForSocketConnection(loginUser));
  }
}
