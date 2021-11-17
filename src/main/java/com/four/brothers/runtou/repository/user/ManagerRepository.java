package com.four.brothers.runtou.repository.user;

import com.four.brothers.runtou.domain.user.Manager;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ManagerRepository {

    @PersistenceContext
    EntityManager em;

    public void save(Manager manager) {

    }

    public Manager findOne(Long id) {
        return new Manager();
    }

    public List<Manager> findAll() {
        return new ArrayList<Manager>();
    }

    public void remove(Long id) {

    }
}
