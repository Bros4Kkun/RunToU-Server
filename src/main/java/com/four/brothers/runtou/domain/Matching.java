package com.four.brothers.runtou.domain;

import com.four.brothers.runtou.domain.base.BaseTimeEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.FetchType;
import javax.persistence.OneToOne;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Matching extends BaseTimeEntity {
  @Id @GeneratedValue(strategy = GenerationType.AUTO)
  @Column
  private Long id;

  @OneToOne(fetch = FetchType.EAGER, optional = false)
  @JoinColumn(name = "ORDER_SHEET_ID", nullable = false, unique = true)
  private OrderSheet orderSheet;

  @ManyToOne(fetch = FetchType.EAGER, optional = false)
  @JoinColumn(name = "PERFORMER_ID", nullable = false)
  private Performer performer;

  @OneToOne(mappedBy = "matching", fetch = FetchType.EAGER, orphanRemoval = true)
  private Review review;

  @Column(nullable = false)
  private Boolean isCompleted;

  @Column(nullable = true)
  private LocalDateTime completedDateTime;

  @Column(nullable = false)
  private Boolean completionRequest;


  public Matching(Boolean isCompleted, @Nullable LocalDateTime completedDateTime, OrderSheet orderSheet, Performer performer, Boolean completionRequest) {
    this.isCompleted = isCompleted;
    this.completedDateTime = completedDateTime;
    this.orderSheet = orderSheet;
    this.performer = performer;
    this.completionRequest = completionRequest;
  }

  public void updateReview(Review review) {
    if (this.review != null) {
      this.review.setMatching(null);
    }
    review.setMatching(this);
    this.review = review;
  }

  public void requestCompletion() {
    this.completionRequest = true;
  }

  protected void setPerformer(Performer performer) {
    this.performer = performer;
  }

  protected void setOrderSheet(OrderSheet orderSheet) {
    this.orderSheet = orderSheet;
  }
}
