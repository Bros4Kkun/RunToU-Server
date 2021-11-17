package com.four.brothers.runtou.repository.user;

import com.four.brothers.runtou.domain.user.Orderer;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Repository
public class OrdererRepository {

    @PersistenceContext
    EntityManager em;

    public void save(Orderer orderer) {

    }

    public Orderer findOne(Long id) {
        return new Orderer();
    }

    public List<Orderer> findAll() {
        return new ArrayList<Orderer>();
    }

    public void remove(Long id) {

    }
}
