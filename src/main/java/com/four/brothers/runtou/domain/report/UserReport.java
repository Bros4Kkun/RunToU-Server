package com.four.brothers.runtou.domain.report;

import com.four.brothers.runtou.domain.user.User;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue("USER_REPORT")
public class UserReport extends Report {

    @ManyToOne(optional = false)
    @JoinColumn(name = "USER_NUMBER")
    private User reportedUser;

    public User getReportedUser() {
        return reportedUser;
    }

    public void setReportedUser(User reportedUser) {
        this.reportedUser = reportedUser;
    }
}
