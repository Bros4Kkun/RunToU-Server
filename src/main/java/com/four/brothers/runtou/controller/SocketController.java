package com.four.brothers.runtou.controller;

import com.four.brothers.runtou.dto.EchoDto;
import com.four.brothers.runtou.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class SocketController {
  private final ChatService chatService;

  /**
   * 웹소켓을 통해, 웹소켓 통신하기
   * @param message
   * @return
   */
  @MessageMapping("/hello-msg-mapping")
  @SendTo("/topic/greetings")
  public EchoDto echoMessageMapping(String message) {
    log.info("React to hello-msg-mapping");
    return new EchoDto(message.trim());
  }
}