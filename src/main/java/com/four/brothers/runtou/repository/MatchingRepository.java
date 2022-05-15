package com.four.brothers.runtou.repository;

import com.four.brothers.runtou.domain.Matching;
import com.four.brothers.runtou.domain.OrderSheet;
import com.four.brothers.runtou.domain.Performer;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
  public void saveMatching(OrderSheet orderSheet, Performer performer, boolean isCompleted, LocalDateTime completedDateTime, boolean completionRequest) {
    Matching matching = new Matching(isCompleted, completedDateTime, orderSheet, performer, completionRequest);
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
   * 유니크 키인 orderSheet로 찾는 메서드
   * @param orderSheet
   * @return
   */
  public Optional<Matching> findByOrderSheet(OrderSheet orderSheet) {
    Matching result = null;
    String jpql = "select m from Matching m " +
      "where m.orderSheet = :orderSheet";

    TypedQuery<Matching> query = em.createQuery(jpql, Matching.class)
      .setParameter("orderSheet", orderSheet);

    try {
      result = query.getSingleResult();
    } catch (NoResultException e) {
      return Optional.empty();
    }

    return Optional.of(result);
  }

  /**
   * pk값으로 Matching 찾는 메서드
   * @param pk
   * @return
   */
  public Optional<Matching> findById(long pk) {
    return Optional.ofNullable(em.find(Matching.class, pk));
  }

  /**
   * 사용자 계정 id와 연관된 매칭 조회
   * @param userAccountId
   * @param onlyCompleted true인 경우 완료된 매칭만 조회, false인 경우 현재 수행 중인 매칭만 조회
   * @return
   */
  public List<Matching> findMatchingByUserAccountId(String userAccountId, boolean onlyCompleted) {
    String jpql = "select m from Matching m " +
      "where (m.orderSheet.orderer.accountId = :userAccountIdAsOrderer " +
      "or m.performer.accountId = :userAccountIdAsPerformer) " +
      "and m.isCompleted = :isCompleted";

    List<Matching> resultList = em.createQuery(jpql, Matching.class)
      .setParameter("userAccountIdAsOrderer", userAccountId)
      .setParameter("userAccountIdAsPerformer", userAccountId)
      .setParameter("isCompleted", onlyCompleted)
      .getResultList();

    return resultList;
  }

  /**
   * 사용자 계정 id와 연관된 모든 매칭 조회
   * @param userAccountId
   * @return
   */
  public List<Matching> findMatchingByUserAccountId(String userAccountId) {
    String jpql = "select m from Matching m " +
      "where m.orderSheet.orderer.accountId = :userAccountIdAsOrderer " +
      "or m.performer.accountId = :userAccountIdAsPerformer";

    List<Matching> resultList = em.createQuery(jpql, Matching.class)
      .setParameter("userAccountIdAsOrderer", userAccountId)
      .setParameter("userAccountIdAsPerformer", userAccountId)
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
