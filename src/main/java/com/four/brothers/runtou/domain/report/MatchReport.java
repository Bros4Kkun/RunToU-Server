package com.four.brothers.runtou.domain.report;

import com.four.brothers.runtou.domain.Match;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue("MATCH_REPORT")
public class MatchReport extends Report {

    @ManyToOne(optional = false)
    @JoinColumn(name = "MATCH_ID")
    private Match reportedMatch;

    public MatchReport() {}

    public Match getReportedMatch() {
        return reportedMatch;
    }

    public void setReportedMatch(Match reportedMatch) {
        this.reportedMatch = reportedMatch;
    }
}
