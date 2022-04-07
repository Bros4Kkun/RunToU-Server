package com.four.brothers.runtou.repository.user;

import com.four.brothers.runtou.domain.Admin;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class AdminRepository {
  @PersistenceContext
  private EntityManager em;

  /**
   * Admin을 저장하는 메서드
   * @param accountId
   * @param encodedPassword
   * @param realName
   * @param nickname
   * @param phoneNumber
   * @param accountNumber
   */
  public void saveAdmin(String accountId, String encodedPassword, String realName, String nickname, String phoneNumber, String accountNumber) {
    Admin admin = new Admin(accountId, encodedPassword, realName, nickname, phoneNumber, accountNumber);
    em.persist(admin);
  }

  /**
   * Admin을 pk값으로 찾아 반환하는 메서드
   * @param id pk값
   * @return
   */
  public Optional<Admin> findAdminById(long id) {
    Admin admin = em.find(Admin.class, id);
    return Optional.ofNullable(admin);
  }

  /**
   * Admin을 계정 id 값으로 찾는 메서드
   * @param accountId 계정 id 값
   * @return
   */
  public Optional<Admin> findAdminByAccountId(String accountId) {
    String jpql = "select a from Admin a where a.accountId = :accountId";
    Admin admin;
    TypedQuery<Admin> typedQuery = em.createQuery(jpql, Admin.class)
          .setParameter("accountId", accountId);

    try {
      admin = typedQuery.getSingleResult();
    } catch (NoResultException e) {
      return Optional.empty();
    }

    return Optional.of(admin);
  }

  /**
   * 모든 Admin을 반환하는 메서드
   * @return
   */
  public List<Admin> findAll(int nowPage, int itemSize) {
    if (nowPage < 1) {
      throw new IllegalArgumentException("조회할 현재 페이지는 1 이상이어야 합니다.");
    }
    if (itemSize < 1) {
      throw new IllegalArgumentException("한번에 조회할 수 있는 엔티티의 개수는 1 이상이어야 합니다.");
    }
    String jpql = "select a from Admin a";
    List<Admin> list = em.createQuery(jpql, Admin.class)
      .setFirstResult((nowPage - 1) * itemSize)
      .setMaxResults(itemSize)
      .getResultList();
    return list;
  }

  /**
   * Admin을 pk값으로 삭제하는 메서드
   * @param id pk값
   */
  public void deleteAdminById(long id) {
    String jpql = "delete from Admin a where a.id = :id";
    em.createQuery(jpql)
      .setParameter("id", id)
      .executeUpdate();
  }

  /**
   * Admin을 계정 Id 값으로 삭제하는 메서드
   * @param accountId 계정 id
   */
  public void deleteAdminByAccountId(String accountId) {
    String jpql = "delete from Admin a where a.accountId = :accountId";
    em.createQuery(jpql)
      .setParameter("accountId", accountId)
      .executeUpdate();
  }
}
