package com.four.brothers.runtou.repository.user;

import com.four.brothers.runtou.domain.user.Manager;
import com.four.brothers.runtou.domain.user.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    @PersistenceContext
    EntityManager em;

    public void save(User user) {

    }

    public User findOne(User user) {
        return new User();
    }

    public List<User> findAll() {
        return new ArrayList<User>();
    }

    public void remove(Long id) {

    }
}
