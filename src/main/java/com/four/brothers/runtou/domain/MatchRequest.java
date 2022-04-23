package com.four.brothers.runtou.domain;

import com.four.brothers.runtou.domain.base.BaseTimeEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class MatchRequest extends BaseTimeEntity {
  @Id @GeneratedValue(strategy = GenerationType.AUTO)
  @Column
  private Long id;

  @ManyToOne(fetch = FetchType.EAGER, optional = false)
  @JoinColumn(name = "ORDER_SHEET_ID", nullable = false)
  private OrderSheet orderSheet;

  @OneToOne(fetch = FetchType.EAGER, optional = false)
  @JoinColumn(name = "PERFORMER_ID", nullable = false)
  private Performer performer;

  public MatchRequest(OrderSheet orderSheet, Performer performer) {
    this.orderSheet = orderSheet;
    this.performer = performer;
  }

  protected void setOrderSheet(OrderSheet orderSheet) {
    this.orderSheet = orderSheet;
  }

  protected void setPerformer(Performer performer) {
    this.performer = performer;
  }

}
