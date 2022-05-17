package com.four.brothers.runtou.domain;

import com.four.brothers.runtou.domain.base.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Report extends BaseTimeEntity {
  @Id @GeneratedValue(strategy = GenerationType.AUTO)
  @Column
  private Long id;

  //신고한 사람
  @ManyToOne(fetch = FetchType.EAGER, optional = false)
  @JoinColumn(name = "REPORT_USER_ID", nullable = false)
  private User reportUser;

  //신고받은 사람
  @ManyToOne(fetch = FetchType.EAGER, optional = false)
  @JoinColumn(name = "REPORTED_USER_ID", nullable = false)
  private User haveReportedUser;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private ReportCategory category;

  @Lob
  @Column(length = 2024, nullable = false)
  private String content;

  public Report(User reportUser, User haveReportedUser, ReportCategory category, String content) {
    this.reportUser = reportUser;
    this.haveReportedUser = haveReportedUser;
    this.category = category;
    this.content = content;
  }

  protected void setReportUser(User reportUser) {
    this.reportUser = reportUser;
  }

  protected void setHaveReportedUser(User isReportedUser) {
    this.haveReportedUser = isReportedUser;
  }

}
