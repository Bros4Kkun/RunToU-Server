package com.four.brothers.runtou.service;

import com.four.brothers.runtou.domain.ChatRoom;
import com.four.brothers.runtou.domain.OrderSheet;
import com.four.brothers.runtou.domain.Orderer;
import com.four.brothers.runtou.domain.Performer;
import com.four.brothers.runtou.dto.UserRole;
import com.four.brothers.runtou.exception.CanNotAccessException;
import com.four.brothers.runtou.exception.code.ChatRoomExceptionCode;
import com.four.brothers.runtou.repository.ChatRoomRepository;
import com.four.brothers.runtou.repository.user.OrderSheetRepository;
import com.four.brothers.runtou.repository.user.OrdererRepository;
import com.four.brothers.runtou.repository.user.PerformerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.four.brothers.runtou.dto.ChatRoomDto.*;
import static com.four.brothers.runtou.dto.LoginDto.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatRoomService {
  private final ChatRoomRepository chatRoomRepository;
  private final OrdererRepository ordererRepository;
  private final PerformerRepository performerRepository;
  private final OrderSheetRepository orderSheetRepository;

  /**
   * 요청에 의해 채팅방을 새로 만드는 메서드
   * @param newChatRoomRequest
   * @param loginUser
   * @return
   * @throws CanNotAccessException
   */
  @Transactional
  public NewChatRoomResponse makeNewChatRoomByPerformer(NewChatRoomRequest newChatRoomRequest, LoginUser loginUser) throws CanNotAccessException {
    Optional<Orderer> orderer;
    Optional<Performer> performer;
    Optional<OrderSheet> orderSheet;

    //만약 채팅을 신청한 사람이 '심부름 수행자'가 아니라면
    if (loginUser.getRole() == UserRole.PERFORMER) {
      throw new CanNotAccessException(ChatRoomExceptionCode.REQUEST_USER_IS_NOT_PERFORMER, "오직 심부름 수행자만 채팅을 요청할 수 있습니다.");
    }

    orderer = ordererRepository.findOrdererById(newChatRoomRequest.getOrdererPk());
    performer = performerRepository.findPerformerById(newChatRoomRequest.getPerformerPk());
    orderSheet = orderSheetRepository.findById(newChatRoomRequest.getOrderSheetPk());

    isAllExistPkForNewChatRoom(orderer, performer, orderSheet); //실존하는 데이터인지 검사

    List<ChatRoom> sameChatRoomList = chatRoomRepository.findSameChatRoom(orderer.get(), performer.get(), orderSheet.get());
    if (sameChatRoomList.size() == 0) { //기존 채팅방이 존재하지 않는다면
      chatRoomRepository.saveChatRoom(orderer.get(), performer.get(), orderSheet.get());
      sameChatRoomList = chatRoomRepository.findSameChatRoom(orderer.get(), performer.get(), orderSheet.get());
    }

    return new NewChatRoomResponse(sameChatRoomList.get(0).getId());
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


}
