package com.four.brothers.runtou.repository;

import com.four.brothers.runtou.domain.Match;
import com.four.brothers.runtou.domain.user.Orderer;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MatchRepository {

    @PersistenceContext
    EntityManager em;

    public void save(Match match) {

    }

    public Match findOne(Long id) {
        return new Match();
    }

    public List<Match> findAll() {
        return new ArrayList<Match>();
    }

    public void remove(Long id) {

    }
}
