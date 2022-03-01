package com.four.brothers.runtou.repository.user;

import com.four.brothers.runtou.domain.OrderSheet;
import com.four.brothers.runtou.domain.Review;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Transactional
public class ReviewRepository {
  @PersistenceContext
  EntityManager em;

  /**
   * 모든 리뷰를 조회하는 메서드
   * @param nowPage
   * @param itemSize
   * @return
   */
  public List<Review> findAll(int nowPage, int itemSize) {
    if (nowPage < 1) {
      throw new IllegalArgumentException("조회할 현재 페이지는 1 이상이어야 합니다.");
    }
    if (itemSize < 1) {
      throw new IllegalArgumentException("한번에 조회할 수 있는 엔티티의 개수는 1 이상이어야 합니다.");
    }

    String jpql = "select p from Review p";
    List<Review> resultList = em.createQuery(jpql, Review.class)
      .setFirstResult((nowPage - 1) * itemSize)
      .setMaxResults(itemSize)
      .getResultList();

    return resultList;
  }
}