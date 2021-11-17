package com.four.brothers.runtou.domain.report;

import com.four.brothers.runtou.domain.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "DTYPE")
public abstract class Report {

    @Id @GeneratedValue
    @Column(name = "REPORT_ID")
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "REPORTER_NUMBER")
    private User reporter;

    private ReportCategory category;
    private String content;
    private LocalDateTime reportedDateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getReporter() {
        return reporter;
    }

    public void setReporter(User reporter) {
        this.reporter = reporter;
    }

    public ReportCategory getCategory() {
        return category;
    }

    public void setCategory(ReportCategory category) {
        this.category = category;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getReportedDateTime() {
        return reportedDateTime;
    }

    public void setReportedDateTime(LocalDateTime reportedDateTime) {
        this.reportedDateTime = reportedDateTime;
    }
}
