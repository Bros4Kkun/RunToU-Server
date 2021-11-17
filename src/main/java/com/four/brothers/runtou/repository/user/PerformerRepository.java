package com.four.brothers.runtou.repository.user;

import com.four.brothers.runtou.domain.user.Performer;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PerformerRepository {

    @PersistenceContext
    EntityManager em;

    public void save(Performer performer) {

    }

    public Performer findOne(Long id) {
        return new Performer();
    }

    public List<Performer> findAll() {
        return new ArrayList<Performer>();
    }

    public void remove(Long id) {

    }
}
