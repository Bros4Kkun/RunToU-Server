package com.four.brothers.runtou.repository;

import com.four.brothers.runtou.domain.Review;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ReviewRepository {

    @PersistenceContext
    EntityManager em;

    public void save(Review review) {

    }

    public Review findOne(Long id) {
        return new Review();
    }

    public List<Review> findAll() {
        return new ArrayList<Review>();
    }

    public void remove(Long id) {

    }
}
