package com.four.brothers.runtou.repository.report;

import com.four.brothers.runtou.domain.report.MatchReport;
import com.four.brothers.runtou.domain.report.UserReport;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserReportRepository {

    @PersistenceContext
    EntityManager em;

    public void save(UserReport userReport) {

    }

    public UserReport findOne(Long id) {
        return new UserReport();
    }

    public List<UserReport> findAll() {
        return new ArrayList<UserReport>();
    }

    public void remove(Long id) {

    }
}
