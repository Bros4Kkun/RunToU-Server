package com.four.brothers.runtou.domain;

import com.four.brothers.runtou.domain.report.MatchReport;
import com.four.brothers.runtou.domain.user.Orderer;
import com.four.brothers.runtou.domain.user.Performer;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Match {

    @Id
    @GeneratedValue
    @Column(name = "MATCH_ID")
    private Long id;

    @OneToOne(optional = false)
    @JoinColumn(name = "ORDER_ID")
    private Order order;

    @ManyToOne(optional = false)
    @JoinColumn(name = "PERFORMER_NUMBER")
    private Performer performer;

    @OneToMany(mappedBy = "reportedMatch", fetch = FetchType.LAZY)
    private List<MatchReport> matchReports = new ArrayList<MatchReport>();

    private boolean isCompleted;
    private LocalDateTime matchedDateTime;
    private LocalDateTime completedDateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Performer getPerformer() {
        return performer;
    }

    public void setPerformer(Performer performer) {
        this.performer = performer;
    }

    public List<MatchReport> getMatchReports() {
        return matchReports;
    }

    public void setMatchReports(List<MatchReport> matchReports) {
        this.matchReports = matchReports;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public LocalDateTime getMatchedDateTime() {
        return matchedDateTime;
    }

    public void setMatchedDateTime(LocalDateTime matchedDateTime) {
        this.matchedDateTime = matchedDateTime;
    }

    public LocalDateTime getCompletedDateTime() {
        return completedDateTime;
    }

    public void setCompletedDateTime(LocalDateTime completedDateTime) {
        this.completedDateTime = completedDateTime;
    }
}
