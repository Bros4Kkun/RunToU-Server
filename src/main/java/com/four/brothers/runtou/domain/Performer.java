package com.four.brothers.runtou.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
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

  public Performer(String accountId, String password, String nickname, String phoneNumber, String accountNumber, Boolean isDoingJobNow, LocalDateTime becamePerformerDateTime, Long earnedMoney) {
    super(accountId, password, nickname, phoneNumber, accountNumber);
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
}
