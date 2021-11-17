package com.four.brothers.runtou.repository;

import com.four.brothers.runtou.domain.Order;
import com.four.brothers.runtou.domain.user.Orderer;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Repository
public class OrderRepository {

    @PersistenceContext
    EntityManager em;

    public void save(Order order) {

    }

    public Order findOne(Long id) {
        return new Order();
    }

    public List<Order> findAll() {
        return new ArrayList<Order>();
    }

    public void remove(Long id) {

    }
}
