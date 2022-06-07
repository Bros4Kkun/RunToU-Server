package com.four.brothers.runtou.repository;

import com.four.brothers.runtou.domain.Matching;
import com.four.brothers.runtou.domain.OrderSheet;
import com.four.brothers.runtou.domain.Review;
import com.four.brothers.runtou.domain.ReviewScore;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class ReviewRepository {
  @PersistenceContext
  EntityManager em;

  /**
   * Review 를 저장하는 메서드
   * @param matching review를 할 매칭
   * @param score 점수
   * @param content 내용
   */
  public void saveReview(Matching matching, ReviewScore score, String content) {
    Review review = new Review(matching, score, content);
    em.persist(review);
  }

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

  /**
   * 매칭으로 리뷰를 찾는 메서드
   * @param matching
   * @return
   */
  public Optional<Review> findByMatching(Matching matching) {
    String jpql = "select r from Review r " +
        "where r.matching = :matching";
    Review entity = null;

    TypedQuery<Review> query = em.createQuery(jpql, Review.class)
        .setParameter("matching", matching);

    try {
      entity = query.getSingleResult();
    } catch (NoResultException e) {
      return Optional.empty();
    }

    return Optional.of(entity);
  }

  /**
   * 리뷰 pk 값으로 리뷰를 찾는 메서드
   * @param pk
   * @return
   */
  public Optional<Review> findById(long pk) {
    Review review = em.find(Review.class, pk);
    return Optional.ofNullable(review);
  }

  /**
   * 리뷰 작성자(심부름 요청자)가 작성한 리뷰 조회
   * @param ordererPk
   * @return
   */
  public List<Review> findByOrdererId(long ordererPk) {
    String jpql = "select r from Review r " +
        "join r.matching.orderSheet.orderer o " +
        "where o.id = :ordererPk";

    List<Review> result = em.createQuery(jpql, Review.class)
        .setParameter("ordererPk", ordererPk)
        .getResultList();

    return result;
  }

  /**
   * pk값으로 삭제하는 메서드
   * @param pk
   */
  public void deleteReviewById(long pk) {
    String jpql = "delete from Review o " +
      "where o.id = :pk";
    em.createQuery(jpql).setParameter("pk", pk).executeUpdate();
  }
}
