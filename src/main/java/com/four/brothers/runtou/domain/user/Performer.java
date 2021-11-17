package com.four.brothers.runtou.domain.user;

import com.four.brothers.runtou.domain.Match;
import com.four.brothers.runtou.domain.Review;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("PERFORMER")
public class Performer extends User {

    private LocalDateTime becamePerformerDate;
    private Long earnedMoney;

    @OneToMany(mappedBy = "performer", fetch = FetchType.LAZY)
    private List<Review> reviews = new ArrayList<Review>();

    @OneToMany(mappedBy = "performer", fetch = FetchType.LAZY)
    private List<Match> matches = new ArrayList<Match>();

    public Performer() {}

    public Performer(LocalDateTime becamePerformerDate) {
        this.becamePerformerDate = becamePerformerDate;
    }

    public LocalDateTime getBecamePerformerDate() {
        return becamePerformerDate;
    }

    public void setBecamePerformerDate(LocalDateTime becamePerformerDate) {
        this.becamePerformerDate = becamePerformerDate;
    }

    public Long getEarnedMoney() {
        return earnedMoney;
    }

    public void setEarnedMoney(Long earnedMoney) {
        this.earnedMoney = earnedMoney;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public List<Match> getMatches() {
        return matches;
    }

    public void setMatches(List<Match> matches) {
        this.matches = matches;
    }
}
