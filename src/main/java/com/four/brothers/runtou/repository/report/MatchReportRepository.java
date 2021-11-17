package com.four.brothers.runtou.repository.report;

import com.four.brothers.runtou.domain.report.MatchReport;
import com.four.brothers.runtou.domain.report.OrderReport;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MatchReportRepository {

    @PersistenceContext
    EntityManager em;

    public void save(MatchReport matchReport) {

    }

    public MatchReport findOne(Long id) {
        return new MatchReport();
    }

    public List<MatchReport> findAll() {
        return new ArrayList<MatchReport>();
    }

    public void remove(Long id) {

    }
}
