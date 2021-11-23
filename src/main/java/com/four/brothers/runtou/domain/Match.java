package com.four.brothers.runtou.domain;

import com.four.brothers.runtou.domain.report.MatchReport;
import com.four.brothers.runtou.domain.user.Orderer;
import com.four.brothers.runtou.domain.user.Performer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
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

}
