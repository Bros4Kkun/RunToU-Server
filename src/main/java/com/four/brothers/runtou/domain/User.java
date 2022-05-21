package com.four.brothers.runtou.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "DTYPE")
@Entity
public class User {
  @Id @GeneratedValue(strategy = GenerationType.AUTO)
  @Column
  private Long id;

  @Column(nullable = false, length = 30, unique = true)
  private String accountId;

  @Column(nullable = false)
  private String password;

  @Column(length = 20)
  private String realName;

  @Column(nullable = false, length = 30, unique = true)
  private String nickname;

  @Column(nullable = false, length = 11)
  private String phoneNumber;

  @Column(nullable = false)
  private String accountNumber;

  @Lob
  @Column
  private String selfIntroduction;

  @Column(nullable = false)
  private int point;

  //내가 신고한 신고리스트
  @OneToMany(mappedBy = "reportUser")
  private List<Report> myReportList = new ArrayList<>();

  //내가 신고받은 신고리스트
  @OneToMany(mappedBy = "haveReportedUser")
  private List<Report> haveReportedList = new ArrayList<>();

  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
  private List<ChatMessage> chatMessages = new ArrayList<>();

  public User(long id, String accountId, String password, String realName, String nickname, String phoneNumber, String accountNumber) {
    this.id = id;
    this.accountId = accountId;
    this.password = password;
    this.realName = realName;
    this.nickname = nickname;
    this.phoneNumber = phoneNumber;
    this.accountNumber = accountNumber;
  }


  public User(String accountId, String password, String realName, String nickname, String phoneNumber, String accountNumber) {
    this.accountId = accountId;
    this.password = password;
    this.realName = realName;
    this.nickname = nickname;
    this.phoneNumber = phoneNumber;
    this.accountNumber = accountNumber;
  }

  public void addMyReport(Report report) {
    report.setReportUser(this);
    this.myReportList.add(report);
  }

  public void addHaveReportedReport(Report report) {
    report.setHaveReportedUser(this);
    this.haveReportedList.add(report);
  }

  public void addChatMessage(ChatMessage message) {
    message.setUser(this);
    this.chatMessages.add(message);
  }

  public void changeNickname(String nickname) {
    this.nickname = nickname;
  }

  public void changeSelfIntroduction(String selfIntroduction) {
    this.selfIntroduction = selfIntroduction;
  }

  public void earnPoint(int earnedPoint) {
    this.point += earnedPoint;
  }

  public void spendPoint(int spentPoint) {
    final String errMsg = "사용하려는 포인트가 보유 포인트보다 클 수 없습니다.";
    boolean canSpendPoint = (this.point - spentPoint) >= 0;

    if (canSpendPoint) {
      this.point -= spentPoint;
    } else {
      throw new IllegalArgumentException(errMsg);
    }
  }
}
