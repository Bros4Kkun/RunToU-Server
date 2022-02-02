package com.four.brothers.runtou.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "DTYPE")
@Entity
public class User {
  @Id @GeneratedValue(strategy = GenerationType.AUTO)
  @Column
  private Long id;

  @Column(nullable = false, length = 30, unique = true)
  private String accountId;

  @Column(nullable = false, length = 50)
  private String password;

  @Column(nullable = false, length = 30)
  private String nickname;

  @Column(nullable = false, length = 11)
  private String phoneNumber;

  @Column(nullable = false)
  private String accountNumber;

  //내가 신고한 신고리스트
  @OneToMany(mappedBy = "reportUser")
  private List<Report> myReportList = new ArrayList<>();

  //내가 신고받은 신고리스트
  @OneToMany(mappedBy = "haveReportedUser")
  private List<Report> haveReportedList = new ArrayList<>();

  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
  private List<ChatMessage> chatMessages = new ArrayList<>();

  public User(String accountId, String password, String nickname, String phoneNumber, String accountNumber) {
    this.accountId = accountId;
    this.password = password;
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
}
