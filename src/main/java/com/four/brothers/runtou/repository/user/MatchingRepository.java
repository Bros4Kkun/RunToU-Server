package com.four.brothers.runtou.repository.user;

import com.four.brothers.runtou.domain.Matching;
import com.four.brothers.runtou.domain.OrderSheet;
import com.four.brothers.runtou.domain.Performer;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class MatchingRepository {
  @PersistenceContext
  EntityManager em;

  /**
   * 주문 정보를 저장하는 메서드
   * @param orderSheet 주문서
   * @param performer 수행자
   * @param isCompleted 완료여부
   * @param completedDateTime 완료시간
   */
  public void saveMatching(OrderSheet orderSheet, Performer performer, boolean isCompleted, LocalDateTime completedDateTime) {
    Matching matching = new Matching(isCompleted, completedDateTime, orderSheet, performer);
    em.persist(matching);
  }

  /**
   * 모든 매칭을 조회하는 메서드
   * @param nowPage
   * @param itemSize
   * @return
   */
  public List<Matching> findAll(int nowPage, int itemSize) {
    if (nowPage < 1) {
      throw new IllegalArgumentException("조회할 현재 페이지는 1 이상이어야 합니다.");
    }
    if (itemSize < 1) {
      throw new IllegalArgumentException("한번에 조회할 수 있는 엔티티의 개수는 1 이상이어야 합니다.");
    }

    String jpql = "select p from Matching p";
    List<Matching> resultList = em.createQuery(jpql, Matching.class)
      .setFirstResult((nowPage - 1) * itemSize)
      .setMaxResults(itemSize)
      .getResultList();

    return resultList;
  }

  /**
   * pk값으로 삭제하는 메서드
   * @param pk
   */
  public void deleteMatchingById(long pk) {
    String jpql = "delete from Matching o " +
      "where o.id = :pk";
    em.createQuery(jpql).setParameter("pk", pk).executeUpdate();
  }
}
