package com.four.brothers.runtou.repository;

import com.four.brothers.runtou.domain.ChatRoom;
import com.four.brothers.runtou.domain.OrderSheet;
import com.four.brothers.runtou.domain.Orderer;
import com.four.brothers.runtou.domain.Performer;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
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

  /**
   * 모든 채팅방을 찾는 메서드
   * @param nowPage
   * @param itemSize
   * @return
   */
  public List<ChatRoom> findAll(int nowPage, int itemSize) {
    if (nowPage < 1) {
      throw new IllegalArgumentException("조회할 현재 페이지는 1 이상이어야 합니다.");
    }
    if (itemSize < 1) {
      throw new IllegalArgumentException("한번에 조회할 수 있는 엔티티의 개수는 1 이상이어야 합니다.");
    }

    String jpql = "select c from ChatRoom c";
    List<ChatRoom> resultList = em.createQuery(jpql, ChatRoom.class)
      .setFirstResult((nowPage - 1) * itemSize)
      .setMaxResults(itemSize)
      .getResultList();

    return resultList;
  }

  /**
   * 동일한 채팅방이 이미 존재하는지 확인하는 메서드
   * @param orderer
   * @param performer
   * @param orderSheet
   * @return
   */
  public List<ChatRoom> findSameChatRoom(Orderer orderer, Performer performer, OrderSheet orderSheet) {
    String jpql = "select c from ChatRoom c " +
      "where c.orderer = :orderer and " +
      "c.performer = :performer and " +
      "c.orderSheet = :orderSheet";

    List<ChatRoom> resultList = em.createQuery(jpql, ChatRoom.class)
      .setParameter("orderer", orderer)
      .setParameter("performer", performer)
      .setParameter("orderSheet", orderSheet)
      .getResultList();

    return resultList;
  }

  /**
   * pk값으로 삭제하는 메서드
   * @param pk
   */
  public void deleteChatRoomById(long pk) {
    String jpql = "delete from ChatRoom c " +
      "where c.id = :pk";
    em.createQuery(jpql)
      .setParameter("pk", pk)
      .executeUpdate();
  }

}
