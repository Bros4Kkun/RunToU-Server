package com.four.brothers.runtou.domain;

import com.four.brothers.runtou.domain.base.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
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

  @Column(nullable = false)
  private Boolean isOrderSheetMatched;

  public MatchRequest(OrderSheet orderSheet, Performer performer, boolean isAccepted, boolean isOrderSheetMatched) {
    this.orderSheet = orderSheet;
    this.performer = performer;
    this.isAccepted = isAccepted;
    this.isOrderSheetMatched = isOrderSheetMatched;
  }

  /**
   * 본 매칭요청이 받아들여졌을 때
   */
  public void accept() {
    this.isAccepted = true;
    this.isOrderSheetMatched = true;
  }

  /**
   * 다른 매칭요청으로 마감되었을때
   */
  public void rejectByOtherMatchRequest() {
    this.isAccepted = false;
    this.isOrderSheetMatched = true;
  }

  protected void setOrderSheet(OrderSheet orderSheet) {
    this.orderSheet = orderSheet;
  }

  protected void setPerformer(Performer performer) {
    this.performer = performer;
  }

}
