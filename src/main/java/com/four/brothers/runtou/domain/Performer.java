package com.four.brothers.runtou.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("PERFORMER")
@Entity
public class Performer extends User {
  @Column(nullable = false)
  private Boolean isDoingJobNow;

  @Column(nullable = false)
  private LocalDateTime becamePerformerDateTime;

  @Column(nullable = false)
  private Long earnedMoney;

  @OneToMany(mappedBy = "performer")
  private List<Matching> matchings = new ArrayList<>();

  @OneToMany(mappedBy = "performer", fetch = FetchType.LAZY)
  private List<ChatRoom> chatRooms = new ArrayList<>();

  public Performer(long id, String accountId, String password, String realName, String nickname, String phoneNumber, String accountNumber, Boolean isDoingJobNow, LocalDateTime becamePerformerDateTime, Long earnedMoney) {
    super(id, accountId, password, realName, nickname, phoneNumber, accountNumber);
    this.isDoingJobNow = isDoingJobNow;
    this.becamePerformerDateTime = becamePerformerDateTime;
    this.earnedMoney = earnedMoney;
  }

  public Performer(String accountId, String password, String realName, String nickname, String phoneNumber, String accountNumber, Boolean isDoingJobNow, LocalDateTime becamePerformerDateTime, Long earnedMoney) {
    super(accountId, password, realName, nickname, phoneNumber, accountNumber);
    this.isDoingJobNow = isDoingJobNow;
    this.becamePerformerDateTime = becamePerformerDateTime;
    this.earnedMoney = earnedMoney;
  }

  public void addMatching(Matching matching) {
    matching.setPerformer(this);
    this.matchings.add(matching);
  }

  public void addChatRoom(ChatRoom room) {
    room.setPerformer(this);
    this.chatRooms.add(room);
  }

  public void doJob() {
    this.isDoingJobNow = true;
  }

  public void finishJob() {
    this.isDoingJobNow = false;
  }
}
