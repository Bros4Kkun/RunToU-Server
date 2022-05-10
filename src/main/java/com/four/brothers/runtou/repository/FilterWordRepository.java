package com.four.brothers.runtou.repository;

import com.four.brothers.runtou.domain.FilterWord;
import com.four.brothers.runtou.domain.OrderSheet;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class FilterWordRepository {
  @PersistenceContext
  EntityManager em;

  /**
   * 필터링 단어를 저장하는 메서드
   * @param word
   */
  public void saveWord(String word) {
    FilterWord filterWord = new FilterWord(word);
    em.persist(filterWord);
  }

  /**
   * 모든 필터링 단어를 조회하는 메서드
   * @param nowPage
   * @param itemSize
   * @return
   */
  public List<FilterWord> findAll(int nowPage, int itemSize) {
    if (nowPage < 1) {
      throw new IllegalArgumentException("조회할 현재 페이지는 1 이상이어야 합니다.");
    }
    if (itemSize < 1) {
      throw new IllegalArgumentException("한번에 조회할 수 있는 엔티티의 개수는 1 이상이어야 합니다.");
    }

    String jpql = "select p from FilterWord p";
    List<FilterWord> resultList = em.createQuery(jpql, FilterWord.class)
      .setFirstResult((nowPage - 1) * itemSize)
      .setMaxResults(itemSize)
      .getResultList();

    return resultList;
  }

  /**
   * pk값으로 삭제하는 메서드
   * @param pk
   */
  public void deleteFilterWordById(long pk) {
    String jpql = "delete from FilterWord o " +
      "where o.id = :pk";
    em.createQuery(jpql).setParameter("pk", pk).executeUpdate();
  }
}
