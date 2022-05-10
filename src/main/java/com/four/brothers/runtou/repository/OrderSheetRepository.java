package com.four.brothers.runtou.repository;

import com.four.brothers.runtou.domain.OrderSheet;
import com.four.brothers.runtou.domain.OrderSheetCategory;
import com.four.brothers.runtou.domain.Orderer;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class OrderSheetRepository {
  @PersistenceContext
  EntityManager em;

  /**
   * 주문서를 저장하는 메서드
   * @param orderer 주문자
   * @param title 제목
   * @param content 내용
   * @param category 요청 카테고리
   * @param destination 목적지
   * @param cost 비용
   * @param isPayed 결제가 완료되었는지
   * @param wishedDeadline 희망 완료기간
   */
  public void saveOrderSheet(Orderer orderer, String title, String content,
                             OrderSheetCategory category, String destination,
                             int cost, boolean isPayed, LocalDateTime wishedDeadline) {
    OrderSheet orderSheet = new OrderSheet(orderer, title, content, category, destination,
                                            cost, isPayed, wishedDeadline);
    em.persist(orderSheet);
  }

  /**
   * 모든 주문서를 조회하는 메서드
   * @param nowPage
   * @param itemSize
   * @return
   */
  public List<OrderSheet> findAll(int nowPage, int itemSize) {
    if (nowPage < 1) {
      throw new IllegalArgumentException("조회할 현재 페이지는 1 이상이어야 합니다.");
    }
    if (itemSize < 1) {
      throw new IllegalArgumentException("한번에 조회할 수 있는 엔티티의 개수는 1 이상이어야 합니다.");
    }

    String jpql = "select p from OrderSheet p";
    List<OrderSheet> resultList = em.createQuery(jpql, OrderSheet.class)
      .setFirstResult((nowPage - 1) * itemSize)
      .setMaxResults(itemSize)
      .getResultList();

    return resultList;
  }

  /**
   * 금액 지불이 완료된 주문서만 조회하는 메서드.
   * 또한 category 기준으로 조건을 걸어 조회한다.
   * @param nowPage 현재 페이지
   * @param itemSize 페이지당 출력할 개수
   * @param category 조회할 카테고리 (null인 경우, 카테고리 상관 X)
   * @return
   */
  public List<OrderSheet> findAllOnlyPayed(int nowPage, int itemSize, @Nullable OrderSheetCategory category) throws IllegalArgumentException {
    if (nowPage < 1) {
      throw new IllegalArgumentException("조회할 현재 페이지는 1 이상이어야 합니다.");
    }
    if (itemSize < 1) {
      throw new IllegalArgumentException("한번에 조회할 수 있는 엔티티의 개수는 1 이상이어야 합니다.");
    }

    String jpql = "";

    //category가 null이라면, 카테고리 조건없이 검색
    if (category == null) {
      jpql = "select p from OrderSheet p " +
        "where p.isPayed = true";

      List<OrderSheet> resultList = em.createQuery(jpql, OrderSheet.class)
        .setFirstResult((nowPage - 1) * itemSize)
        .setMaxResults(itemSize)
        .getResultList();

      return resultList;
    } else {
      jpql = "select p from OrderSheet p " +
        "where p.isPayed = true " +
        "and p.category = :category";

      List<OrderSheet> resultList = em.createQuery(jpql, OrderSheet.class)
        .setParameter("category", category)
        .setFirstResult((nowPage - 1) * itemSize)
        .setMaxResults(itemSize)
        .getResultList();

      return resultList;
    }
  }

  /**
   * pk값으로 OrderSheet를 조회하는 메서드
   * @param id
   * @return
   */
  public Optional<OrderSheet> findById(long id) {
    return Optional.ofNullable(em.find(OrderSheet.class, id));
  }

  /**
   * pk값으로 삭제하는 메서드
   * @param pk
   */
  public void deleteOrderSheetById(long pk) {
    String jpql = "delete from OrderSheet o " +
      "where o.id = :pk";
    em.createQuery(jpql).setParameter("pk", pk).executeUpdate();
  }
}
