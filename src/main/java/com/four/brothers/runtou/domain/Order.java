package com.four.brothers.runtou.domain;

import com.four.brothers.runtou.domain.report.OrderReport;
import com.four.brothers.runtou.domain.user.Orderer;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Order {

    @Id @GeneratedValue
    @Column(name = "ORDER_ID")
    private Long id;

    @OneToOne(optional = false)
    @JoinColumn(name = "ORDERER_NUMBER")
    private Orderer orderer;

    @OneToOne(mappedBy = "orderer")
    private Match match;

    @OneToMany(mappedBy = "reportedOrder", fetch = FetchType.LAZY)
    private List<OrderReport> orderReports = new ArrayList<OrderReport>();

    private String title;
    private String content;
    private OrderCategory category;
    private int cost;
    private boolean isPayed;
    private LocalDateTime orderDateTime;
    private LocalDateTime modifiedDateTime;

    public Order() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Orderer getOrderer() {
        return orderer;
    }

    public void setOrderer(Orderer orderer) {
        this.orderer = orderer;
    }

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public List<OrderReport> getOrderReports() {
        return orderReports;
    }

    public void setOrderReports(List<OrderReport> orderReports) {
        this.orderReports = orderReports;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public OrderCategory getCategory() {
        return category;
    }

    public void setCategory(OrderCategory category) {
        this.category = category;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public boolean isPayed() {
        return isPayed;
    }

    public void setPayed(boolean payed) {
        isPayed = payed;
    }

    public LocalDateTime getOrderDateTime() {
        return orderDateTime;
    }

    public void setOrderDateTime(LocalDateTime orderDateTime) {
        this.orderDateTime = orderDateTime;
    }

    public LocalDateTime getModifiedDateTime() {
        return modifiedDateTime;
    }

    public void setModifiedDateTime(LocalDateTime modifiedDateTime) {
        this.modifiedDateTime = modifiedDateTime;
    }
}
