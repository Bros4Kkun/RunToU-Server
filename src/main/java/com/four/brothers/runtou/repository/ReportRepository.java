package com.four.brothers.runtou.repository;

import com.four.brothers.runtou.domain.OrderSheet;
import com.four.brothers.runtou.domain.Report;
import com.four.brothers.runtou.domain.ReportCategory;
import com.four.brothers.runtou.domain.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class ReportRepository {
  @PersistenceContext
  EntityManager em;

  /**
   * Report 를 저장하는 메서드
   * @param reportUser 신고자
   * @param haveReportedUser 피신고자 (신고를 당한 사람)
   * @param category 신고종류
   * @param content 신고내용
   */
  public void saveReport(User reportUser, User haveReportedUser, ReportCategory category, String content) {
    Report report = new Report(reportUser, haveReportedUser, category, content);
    em.persist(report);
  }

  /**
   * 모든 신고를 조회하는 메서드
   * @param nowPage
   * @param itemSize
   * @return
   */
  public List<Report> findAll(int nowPage, int itemSize) {
    if (nowPage < 1) {
      throw new IllegalArgumentException("조회할 현재 페이지는 1 이상이어야 합니다.");
    }
    if (itemSize < 1) {
      throw new IllegalArgumentException("한번에 조회할 수 있는 엔티티의 개수는 1 이상이어야 합니다.");
    }

    String jpql = "select p from Report p";
    List<Report> resultList = em.createQuery(jpql, Report.class)
      .setFirstResult((nowPage - 1) * itemSize)
      .setMaxResults(itemSize)
      .getResultList();

    return resultList;
  }

  /**
   * pk값으로 삭제하는 메서드
   * @param pk
   */
  public void deleteReportById(long pk) {
    String jpql = "delete from Report o " +
      "where o.id = :pk";
    em.createQuery(jpql).setParameter("pk", pk).executeUpdate();
  }
}
