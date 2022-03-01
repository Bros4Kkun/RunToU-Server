package com.four.brothers.runtou.repository.user;

import com.four.brothers.runtou.domain.Performer;
import org.hibernate.engine.spi.SessionDelegatorBaseImpl;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class PerformerRepository {
  @PersistenceContext
  private EntityManager em;

  /**
   * 모든 Performer를 조회하는 메서드
   * @param nowPage 출력할 현재 페이지
   * @param itemSize 한 페이지당 행 개수
   * @return
   */
  public List<Performer> findAll(int nowPage, int itemSize) {
    if (nowPage < 1) {
      throw new IllegalArgumentException("조회할 현재 페이지는 1 이상이어야 합니다.");
    }
    if (itemSize < 1) {
      throw new IllegalArgumentException("한번에 조회할 수 있는 엔티티의 개수는 1 이상이어야 합니다.");
    }

    String jpql = "select p from Performer p";
    List<Performer> resultList = em.createQuery(jpql, Performer.class)
      .setFirstResult((nowPage - 1) * itemSize)
      .setMaxResults(itemSize)
      .getResultList();

    return resultList;
  }

  /**
   * pk값으로 삭제하는 메서드
   * @param pk
   */
  public void deletePerformerById(long pk) {
    String jpql = "delete from Performer p " +
      "where p.id = :pk";
    em.createQuery(jpql)
      .setParameter("pk", pk)
      .executeUpdate();
  }
}
