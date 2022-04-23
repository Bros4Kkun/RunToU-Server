package com.four.brothers.runtou.repository;

import com.four.brothers.runtou.domain.ChatMessage;
import com.four.brothers.runtou.domain.ChatRoom;
import com.four.brothers.runtou.domain.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class ChatMessageRepository {
  @PersistenceContext
  private EntityManager em;

  /**
   * 새로운 채팅 메시지를 저장하는 메서드
   * @param user 송신자
   * @param chatRoom 채팅방 pk 값
   * @param content 내용
   */
  public void saveChatMessage(User user, ChatRoom chatRoom, String content) {
    ChatMessage chatMessage = new ChatMessage(user, chatRoom, content);
    em.persist(chatMessage);
  }

  /**
   * 모든 채팅 메시지를 조회하는 메서드
   * @param nowPage
   * @param itemSize
   * @return
   */
  public List<ChatMessage> findAll(int nowPage, int itemSize) {
    if (nowPage < 1) {
      throw new IllegalArgumentException("조회할 현재 페이지는 1 이상이어야 합니다.");
    }
    if (itemSize < 1) {
      throw new IllegalArgumentException("한번에 조회할 수 있는 엔티티의 개수는 1 이상이어야 합니다.");
    }

    String jpql = "select c from ChatMessage c";
    List<ChatMessage> resultList = em.createQuery(jpql, ChatMessage.class)
      .setFirstResult((nowPage - 1) * itemSize)
      .setMaxResults(itemSize)
      .getResultList();

    return resultList;
  }

  /**
   * pk값으로 삭제하는 메서드
   * @param pk
   */
  public void deleteChatMessageById(long pk) {
    String jpql = "delete from ChatMessage c " +
      "where c.id = :pk";
    em.createQuery(jpql)
      .setParameter("pk", pk)
      .executeUpdate();
  }
}
