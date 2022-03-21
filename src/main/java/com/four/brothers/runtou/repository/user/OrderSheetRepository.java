package com.four.brothers.runtou.repository.user;

import com.four.brothers.runtou.domain.OrderSheet;
import com.four.brothers.runtou.domain.OrderSheetCategory;
import com.four.brothers.runtou.domain.Orderer;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

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
   * pk값으로 삭제하는 메서드
   * @param pk
   */
  public void deleteOrderSheetById(long pk) {
    String jpql = "delete from OrderSheet o " +
      "where o.id = :pk";
    em.createQuery(jpql).setParameter("pk", pk).executeUpdate();
  }
}
