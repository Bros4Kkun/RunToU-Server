package com.four.brothers.runtou.domain;

import com.four.brothers.runtou.domain.base.BaseTimeEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class ChatRoom extends BaseTimeEntity {
  @Id @GeneratedValue(strategy = GenerationType.AUTO)
  @Column
  private Long id;

  @ManyToOne(fetch = FetchType.EAGER, optional = false)
  @JoinColumn(name = "ORDERER_ID", nullable = false)
  private Orderer orderer;

  @ManyToOne(fetch = FetchType.EAGER, optional = false)
  @JoinColumn(name = "PERFORMER_ID", nullable = false)
  private Performer performer;

  @ManyToOne(fetch = FetchType.EAGER, optional = false)
  @JoinColumn(name = "ORDER_SHEET_ID", nullable = false)
  private OrderSheet orderSheet;

  @OneToMany(mappedBy = "chatRoom", fetch = FetchType.LAZY, orphanRemoval = true)
  private List<ChatMessage> chatMessages = new ArrayList<>();

  public ChatRoom(Orderer orderer, Performer performer, OrderSheet orderSheet) {
    this.orderer = orderer;
    this.performer = performer;
    this.orderSheet = orderSheet;
  }

  public void addChatMessage(ChatMessage message) {
    message.setChatRoom(this);
    this.chatMessages.add(message);
  }

  protected void setOrderer(Orderer orderer) {
    this.orderer = orderer;
  }

  protected void setPerformer(Performer performer) {
    this.performer = performer;
  }

  protected void setOrderSheet(OrderSheet orderSheet) {
    this.orderSheet = orderSheet;
  }
}
