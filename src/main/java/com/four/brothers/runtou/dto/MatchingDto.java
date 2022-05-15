package com.four.brothers.runtou.dto;

import com.four.brothers.runtou.domain.Matching;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;

public class MatchingDto {
  @Getter
  @Setter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class SimpMatchInfo {
    private long id;
    private OrderSheetDto.SimpOrderSheetInfo orderSheetInfo;
    private OrdererDto.SimpOrdererInfo ordererInfo;
    private PerformerDto.SimpPerformerInfo performerInfo;
    private boolean isCompleted;
    private LocalDateTime completedDateTime;
    private boolean completionRequest;

    public SimpMatchInfo(Matching entity) {
      this.id = entity.getId();
      this.orderSheetInfo = new OrderSheetDto.SimpOrderSheetInfo(entity.getOrderSheet());
      this.ordererInfo = new OrdererDto.SimpOrdererInfo(entity.getOrderSheet().getOrderer());
      this.performerInfo = new PerformerDto.SimpPerformerInfo(entity.getPerformer());
      this.isCompleted = entity.getIsCompleted();
      this.completedDateTime = entity.getCompletedDateTime();
      this.completionRequest = entity.getCompletionRequest();
    }
  }
  @Getter
  @Setter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class MatchInfo {
    private long id;
    private OrderSheetDto.SimpOrderSheetInfo orderSheetInfo;
    private OrdererDto.SimpOrdererInfo ordererInfo;
    private PerformerDto.SimpPerformerInfo performerInfo;
    private boolean isCompleted;
    private boolean completionRequest;
    private LocalDateTime completedDateTime;
    private ReviewDto.ReviewInfo reviewInfo;

    public MatchInfo(Matching entity) {
      this.id = entity.getId();
      this.orderSheetInfo = new OrderSheetDto.SimpOrderSheetInfo(entity.getOrderSheet());
      this.ordererInfo = new OrdererDto.SimpOrdererInfo(entity.getOrderSheet().getOrderer());
      this.performerInfo = new PerformerDto.SimpPerformerInfo(entity.getPerformer());
      this.isCompleted = entity.getIsCompleted();
      this.completionRequest = entity.getCompletionRequest();
      this.completedDateTime = entity.getCompletedDateTime();
      if (!Objects.isNull(entity.getReview())) {
        this.reviewInfo = new ReviewDto.ReviewInfo(entity.getReview());
      }
    }
  }
}
