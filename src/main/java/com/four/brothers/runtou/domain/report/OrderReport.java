package com.four.brothers.runtou.domain.report;

import com.four.brothers.runtou.domain.Order;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue("ORDER_REPORT")
public class OrderReport extends Report {

    @ManyToOne(optional = false)
    @JoinColumn(name = "ORDER_ID")
    private Order reportedOrder;

    public OrderReport() { }

    public Order getReportedOrder() {
        return reportedOrder;
    }

    public void setReportedOrder(Order reportedOrder) {
        this.reportedOrder = reportedOrder;
    }
}
