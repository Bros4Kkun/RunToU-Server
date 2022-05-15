package com.four.brothers.runtou.repository;

import com.four.brothers.runtou.domain.*;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
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
   * 사용자가 속한 채팅방 찾기
   * @param user
   * @return
   */
  public List<ChatRoom> findChatRoomByUser(User user) {
    String jpql = "select c from ChatRoom c " +
      "where c.orderer.id = :id1 " +
      "or c.performer.id = :id2";

    List<ChatRoom> resultList = em.createQuery(jpql, ChatRoom.class)
      .setParameter("id1", user.getId())
      .setParameter("id2", user.getId())
      .getResultList();

    return resultList;
  }

  /**
   * 해당 요청서와 관련있는 모든 채팅방 찾는 메서드
   * @param orderSheet
   * @return
   */
  public List<ChatRoom> findByOrderSheet(OrderSheet orderSheet) {
    String jpql = "select c from ChatRoom c " +
      "where c.orderSheet = :orderSheet";

    List<ChatRoom> resultList = em.createQuery(jpql, ChatRoom.class)
      .setParameter("orderSheet", orderSheet)
      .getResultList();

    return resultList;
  }

  /**
   * Orderer, Performer, OrderSheet 로 채팅방 찾는 메서드
   * Orderer, Performer, OrderSheet 은 복합 유니크 키이다.
   * @param orderer
   * @param performer
   * @param orderSheet
   * @return
   */
  public Optional<ChatRoom> findByOrdererAndPerformerAndOrderSheet(Orderer orderer, Performer performer, OrderSheet orderSheet) {
    ChatRoom result = null;
    String jpql = "select c from ChatRoom c " +
      "where c.orderer = :orderer and " +
      "c.performer = :performer and " +
      "c.orderSheet = :orderSheet";

    TypedQuery<ChatRoom> query = em.createQuery(jpql, ChatRoom.class)
      .setParameter("orderer", orderer)
      .setParameter("performer", performer)
      .setParameter("orderSheet", orderSheet);

    try {
      result = query.getSingleResult();
    } catch (NoResultException e) {
      return Optional.empty();
    }

    return Optional.of(result);
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
