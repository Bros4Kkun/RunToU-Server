package com.four.brothers.runtou.domain;

import com.four.brothers.runtou.domain.base.BaseTimeEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class OrderSheet extends BaseTimeEntity {
  @Id @GeneratedValue
  @Column
  private Long id;

  @ManyToOne(fetch = FetchType.EAGER, optional = false)
  @JoinColumn(name = "ORDERER_ID", nullable = false)
  private Orderer orderer;

  @OneToOne(mappedBy = "orderSheet", orphanRemoval = true)
  private Match match;

  @OneToMany(mappedBy = "orderSheet", fetch = FetchType.LAZY)
  private List<ChatRoom> chatRooms = new ArrayList<>();

  @Column(nullable = false)
  private String title;

  @Lob
  @Column(length = 2024, nullable = false)
  private String content; //CLOB 타입

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private OrderSheetCategory category;

  @Column(nullable = false)
  private String destination;

  @Column(nullable = false)
  private Integer cost;

  @Column(nullable = false)
  private Boolean isPayed;

  @Column(nullable = false)
  private LocalDateTime wishedDeadline;

  public OrderSheet(Orderer orderer, String title, String content, OrderSheetCategory category, String destination, Integer cost, Boolean isPayed, LocalDateTime wishedDeadline) {
    this.orderer = orderer;
    this.title = title;
    this.content = content;
    this.category = category;
    this.destination = destination;
    this.cost = cost;
    this.isPayed = isPayed;
    this.wishedDeadline = wishedDeadline;
  }

  public void updateMatch(Match match) {
    match.setOrderSheet(this);
    this.match = match;
  }

  public void addChatRoom(ChatRoom room) {
    room.setOrderSheet(this);
    this.chatRooms.add(room);
  }

  protected void setOrderer(Orderer orderer) {
    this.orderer = orderer;
  }

}
