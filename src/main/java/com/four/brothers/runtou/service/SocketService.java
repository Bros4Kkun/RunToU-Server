package com.four.brothers.runtou.service;

import com.four.brothers.runtou.dto.EchoDto;
import com.four.brothers.runtou.dto.SocketDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class SocketService {
  private final SimpMessagingTemplate simpTemplate;

  /**
   * 새로운 채팅방이 생성되었음을 Orderer에게 알리는 메서드
   * @param ordererPk 알릴 Orderer의 PK 값
   * @param chatRoomPk 생성된 채팅방의 PK 값
   */
  public void alertNewChatRoomToOrderer(long ordererPk, long chatRoomPk) {
    simpTemplate.convertAndSend("/queue/orderer/" + ordererPk,
      new SocketDto.NewChatRoomAlertResponse("New ChatRoom Requested.", chatRoomPk));
  }
}
