package com.four.brothers.runtou.domain.report;

import com.four.brothers.runtou.domain.user.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
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

    @Enumerated(EnumType.STRING)
    private ReportCategory category;
    private String content;
    private LocalDateTime reportedDateTime;

}
