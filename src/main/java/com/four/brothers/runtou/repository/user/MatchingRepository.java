package com.four.brothers.runtou.repository.user;

import com.four.brothers.runtou.domain.Matching;
import com.four.brothers.runtou.domain.OrderSheet;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class MatchingRepository {
  @PersistenceContext
  EntityManager em;

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
}
