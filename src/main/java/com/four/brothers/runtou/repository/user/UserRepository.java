package com.four.brothers.runtou.repository.user;

import com.four.brothers.runtou.domain.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Optional;

@Repository
public class UserRepository {

  @PersistenceContext
  private EntityManager em;

  /**
   * 새로운 User를 저장하는 메서드
   * @param accountId 계정 ID
   * @param password 계정 비밀번호
   * @param realName 실명
   * @param nickname 닉네임
   * @param phoneNumber 휴대폰 번호
   * @param accountNumber 계좌 번호
   */
  public void saveUser(String accountId, String password, String realName, String nickname, String phoneNumber, String accountNumber) {
    User user = new User(accountId, password, realName, nickname, phoneNumber, accountNumber);
    em.persist(user);
  }

  /**
   * 계정 ID로 User를 찾는 메서드
   * @param accountId 계정 ID
   * @return
   */
  public Optional<User> findUserByAccountId(String accountId) {
    String jpql = "select u from User u where u.accountId = :accountId";
    TypedQuery<User> typedQuery = em.createQuery(jpql, User.class).setParameter("accountId", accountId);
    User user;

    try {
      user = typedQuery.getSingleResult();
    } catch (NoResultException e) {
      return Optional.empty();
    }
    return Optional.of(user);
  }

  /**
   * 닉네임으로 User 찾는 메서드
   * @param nickname 닉네임
   * @return
   */
  public Optional<User> findUserByNickname(String nickname) {
    String jpql = "select u from User u where u.nickname = :nickname";
    TypedQuery<User> typedQuery = em.createQuery(jpql, User.class).setParameter("nickname", nickname);
    User user;

    try {
      user = typedQuery.getSingleResult();
    } catch (NoResultException e) {
      return Optional.empty();
    }
    return Optional.of(user);
  }
}
