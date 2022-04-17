package com.four.brothers.runtou.controller;

import com.four.brothers.runtou.dto.EchoDto;
import com.four.brothers.runtou.service.SocketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
public class SocketController {
  private final SocketService socketService;

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

  /**
   * 직접 Service 인스턴스를 사용하여, 웹소켓 통신하기
   * @param message
   */
  @PostMapping("/hello-convert-and-send")
  public void echoConvertAndSend(@RequestParam("msg") String message) {
    log.info("echoConvertAndSend");
    socketService.echoMessage(message);
  }
}