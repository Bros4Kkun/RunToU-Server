package com.four.brothers.runtou.domain.report;

import com.four.brothers.runtou.domain.Match;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@Setter
@NoArgsConstructor
@Entity
@DiscriminatorValue("MATCH_REPORT")
public class MatchReport extends Report {

    @ManyToOne(optional = false)
    @JoinColumn(name = "MATCH_ID")
    private Match reportedMatch;

}
