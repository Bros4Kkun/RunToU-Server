package com.four.brothers.runtou.repository;

import com.four.brothers.runtou.domain.ChatRoom;
import com.four.brothers.runtou.domain.OrderSheet;
import com.four.brothers.runtou.domain.Orderer;
import com.four.brothers.runtou.domain.Performer;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
public class ChatRoomRepository {
  @PersistenceContext
  private EntityManager em;

  /**
   * 새로운 채팅방을 저장하는 메서드
   * @param orderer 요청자
   * @param performer 수행자
   * @param orderSheet 요청서
   */
  public void saveChatRoom(Orderer orderer, Performer performer, OrderSheet orderSheet) {
    ChatRoom chatRoom = new ChatRoom(orderer, performer, orderSheet);
    em.persist(chatRoom);
  }

  /**
   * pk값으로 채팅방을 찾는 메서드
   * @param pk pk값
   * @return
   */
  public Optional<ChatRoom> findChatRoomById(long pk) {
    ChatRoom chatRoom = em.find(ChatRoom.class, pk);
    return Optional.ofNullable(chatRoom);
  }

}
