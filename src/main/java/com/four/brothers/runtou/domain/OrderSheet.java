package com.four.brothers.runtou.domain;

import com.four.brothers.runtou.domain.base.BaseTimeEntity;
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
@Entity
public class OrderSheet extends BaseTimeEntity {
  @Id @GeneratedValue
  @Column
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "ORDERER_ID", nullable = false)
  private Orderer orderer;

  @OneToOne(mappedBy = "orderSheet", orphanRemoval = true)
  private Matching matching;

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
  private LocalDateTime wishedDeadline;

  @OneToMany(mappedBy = "orderSheet", fetch = FetchType.LAZY)
  private List<MatchRequest> matchRequests = new ArrayList<>();

  public OrderSheet(Orderer orderer, String title, String content, OrderSheetCategory category, String destination, Integer cost, LocalDateTime wishedDeadline) {
    this.orderer = orderer;
    this.title = title;
    this.content = content;
    this.category = category;
    this.destination = destination;
    this.cost = cost;
    this.wishedDeadline = wishedDeadline;
  }

  public void updateMatching(Matching matching) {
    matching.setOrderSheet(this);
    this.matching = matching;
  }

  public void addChatRoom(ChatRoom room) {
    room.setOrderSheet(this);
    this.chatRooms.add(room);
  }

  protected void setOrderer(Orderer orderer) {
    this.orderer = orderer;
  }

  public void addMatchRequest(MatchRequest matchRequest) {
    matchRequest.setOrderSheet(this);
    this.matchRequests.add(matchRequest);
  }

  public void update(String title, String content, OrderSheetCategory category, String destination, int cost, LocalDateTime wishedDeadline) {
    this.title = title;
    this.content = content;
    this.category = category;
    this.destination = destination;
    this.cost = cost;
    this.wishedDeadline = wishedDeadline;
  }
}
