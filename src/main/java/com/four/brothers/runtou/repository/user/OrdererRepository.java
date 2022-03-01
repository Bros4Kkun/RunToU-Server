package com.four.brothers.runtou.repository.user;

import com.four.brothers.runtou.domain.Orderer;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class OrdererRepository {
  @PersistenceContext
  private EntityManager em;

  /**
   * 새로운 Orderer 저장
   * @param accountId 계정id
   * @param password 비밀번호
   * @param nickname 닉네임
   * @param phoneNumber 휴대폰 번호
   * @param accountNumber 계좌 번호
   */
  public void saveOrderer(String accountId, String password, String nickname, String phoneNumber, String accountNumber) {
    Orderer newOrderer = new Orderer(accountId, password, nickname, phoneNumber, accountNumber, false);
    em.persist(newOrderer);
  }

  /**
   * PK값으로 Orderer 검색하기
   * @param pk Orderer의 PK값
   * @return 검색 결과
   */
  public Optional<Orderer> findOrdererById(String pk) {
    return Optional.ofNullable(em.find(Orderer.class, pk));
  }

  /**
   * 계정 Id로 Orderer 검색하기
   * @param accountId 계정 Id
   * @return 검색 결과
   */
  public Optional<Orderer> findOrdererByAccountId(String accountId) {
    String jpql = "select o from Orderer o " +
      "where o.accountId = :accountId";
    Orderer foundOrderer;

    TypedQuery<Orderer> query = em.createQuery(jpql, Orderer.class)
      .setParameter("accountId", accountId);

    try {
      foundOrderer = query.getSingleResult();
    } catch (NoResultException e) {
      return Optional.empty();
    }

    return Optional.of(foundOrderer);
  }

  /**
   * 모든 Orderer 조회
   * @param nowPage 조회할 페이지
   * @param itemSize 한 페이지당 조회할 Orderer 튜플 개수
   * @return
   */
  public List<Orderer> findAll(int nowPage, int itemSize) {
    if (nowPage < 1) {
      throw new IllegalArgumentException("조회할 현재 페이지는 1 이상이어야 합니다.");
    }
    if (itemSize < 1) {
      throw new IllegalArgumentException("한번에 조회할 수 있는 엔티티의 개수는 1 이상이어야 합니다.");
    }
    String jpql = "select o from Orderer o";
    List<Orderer> list = em.createQuery(jpql, Orderer.class)
      .setFirstResult((nowPage-1)*itemSize)
      .setMaxResults(itemSize)
      .getResultList();

    return list;

  }

  /**
   * Orderer의 pk값으로 Orderer 삭제
   * @param pk 삭제할 Orderer의 pk값
   */
  public void deleteOrdererById(long pk) {
    String jpql = "delete from Orderer o " +
      "where o.id = :pk";
    em.createQuery(jpql).setParameter("pk", pk).executeUpdate();
  }


  /**
   * Orderer의 계정 id값으로 Orderer 삭제
   * @param accountId 삭제할 Orderer의 계정 id값
   */
  public void deleteOrdererByAccountId(String accountId) {
    Optional<Orderer> orderer = findOrdererByAccountId(accountId);
    if (orderer.isEmpty()) {
      throw new IllegalArgumentException("존재하지 않는 Account Id 값입니다.");
    }
    em.remove(orderer.get());
  }

}
