package com.four.brothers.runtou.repository.report;

import com.four.brothers.runtou.domain.report.OrderReport;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Repository
public class OrderReportRepository {
    @PersistenceContext
    EntityManager em;

    public void save(OrderReport orderReport) {

    }

    public OrderReport findOne(Long id) {
        return new OrderReport();
    }

    public List<OrderReport> findAll() {
        return new ArrayList<OrderReport>();
    }

    public void remove(Long id) {

    }
}
