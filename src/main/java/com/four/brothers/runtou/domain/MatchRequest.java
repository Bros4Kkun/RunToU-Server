package com.four.brothers.runtou.domain;

import com.four.brothers.runtou.domain.base.BaseTimeEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(
  uniqueConstraints={
    @UniqueConstraint(
      name = "Constraint1234",
      columnNames={"ORDER_SHEET_ID", "PERFORMER_ID"}
    )
  }
)
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

  @Column(nullable = false)
  private Boolean isAccepted;

  public MatchRequest(OrderSheet orderSheet, Performer performer, boolean isAccepted) {
    this.orderSheet = orderSheet;
    this.performer = performer;
    this.isAccepted = isAccepted;
  }

  protected void setOrderSheet(OrderSheet orderSheet) {
    this.orderSheet = orderSheet;
  }

  protected void setPerformer(Performer performer) {
    this.performer = performer;
  }

}
