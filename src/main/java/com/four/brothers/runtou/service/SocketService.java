package com.four.brothers.runtou.service;

import com.four.brothers.runtou.dto.EchoDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class SocketService {
  private final SimpMessagingTemplate simpTemplate;

  public void echoMessage(String message) {
    log.info("Start convertAndSend ${new Date()}");
    simpTemplate.convertAndSend("/topic/greetings", new EchoDto(message));
    log.info("End convertAndSend ${new Date()}");
  }
}
