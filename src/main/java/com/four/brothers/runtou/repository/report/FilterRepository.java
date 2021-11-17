package com.four.brothers.runtou.repository.report;

import com.four.brothers.runtou.domain.Filter;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Repository
public class FilterRepository {

    @PersistenceContext
    EntityManager em;

    public void save(Filter filter) {

    }

    public Filter findOne(Long id) {
        return new Filter();
    }

    public List<Filter> findAll() {
        return new ArrayList<Filter>();
    }

    public void remove(Long id) {

    }
}
