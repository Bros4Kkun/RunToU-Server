package com.four.brothers.runtou.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@DiscriminatorValue("ORDERER")
@Entity
public class Orderer extends User {
  @Column(nullable = false)
  private Boolean isDoingJobNow;

  @OneToMany(mappedBy = "orderer", fetch = FetchType.LAZY)
  private List<OrderSheet> orderSheets = new ArrayList<>();

  @OneToMany(mappedBy = "orderer", fetch = FetchType.LAZY)
  private List<ChatRoom> chatRooms = new ArrayList<>();

  public Orderer(long id, String accountId, String password, String realName, String nickname,
                 String phoneNumber, String accountNumber, Boolean isDoingJobNow) {
    super(id, accountId, password, realName, nickname, phoneNumber, accountNumber);
    this.isDoingJobNow = isDoingJobNow;
  }

  public Orderer(String accountId, String password, String realName, String nickname,
                 String phoneNumber, String accountNumber, Boolean isDoingJobNow) {
    super(accountId, password, realName, nickname, phoneNumber, accountNumber);
    this.isDoingJobNow = isDoingJobNow;
  }


  public void addOrderSheet(OrderSheet orderSheet) {
    orderSheet.setOrderer(this);
    this.orderSheets.add(orderSheet);
  }

  public void addChatRoom(ChatRoom room) {
    room.setOrderer(this);
    this.chatRooms.add(room);
  }

}
