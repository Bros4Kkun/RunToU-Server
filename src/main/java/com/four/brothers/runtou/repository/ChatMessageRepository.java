package com.four.brothers.runtou.repository;

import com.four.brothers.runtou.domain.ChatMessage;
import com.four.brothers.runtou.domain.ChatRoom;
import com.four.brothers.runtou.domain.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

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
   * 특정 채팅방에서 '가장 늦게 보낸 메시지'를 내용을 통해 찾는 메서드
   * @param chatRoom 찾을 메시지가 있는 채팅방 pk 값
   * @param content 찾을 메시지의 내용
   * @return
   */
    public Optional<ChatMessage> findLatestChatMsgFromChatRoom(ChatRoom chatRoom, String content) {
    String jpql = "select c from ChatMessage c " +
      "where c.chatRoom = :chatRoom " +
      "and c.content = :content " +
      "order by c.createdDate desc";

    ChatMessage result = em.createQuery(jpql, ChatMessage.class)
      .setParameter("chatRoom", chatRoom)
      .setParameter("content", content)
      .setFirstResult(0)
      .setMaxResults(1)
      .getSingleResult();

    return Optional.ofNullable(result);
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
