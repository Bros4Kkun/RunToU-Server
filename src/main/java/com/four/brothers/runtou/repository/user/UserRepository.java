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
