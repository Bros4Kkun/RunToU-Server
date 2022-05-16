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
    private long matchingId;
    private OrderSheetDto.SimpOrderSheetInfo orderSheetInfo;
    private OrdererDto.SimpOrdererInfo ordererInfo;
    private PerformerDto.SimpPerformerInfo performerInfo;
    private boolean isCompleted;
    private boolean completionRequest;
    private LocalDateTime completedDateTime;
    private ReviewDto.ReviewInfo reviewInfo;

    public MatchInfo(Matching entity) {
      this.matchingId = entity.getId();
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

  @Getter
  @Setter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class JobDoneRequestInfo {
    private long matchingId;
    private String performerAccountId;
    private String performerNickname;
    private LocalDateTime doneRequestDateTime;

    public JobDoneRequestInfo(Matching entity) {
      this.matchingId = entity.getId();
      this.performerAccountId = entity.getPerformer().getAccountId();
      this.performerNickname = entity.getPerformer().getNickname();
      doneRequestDateTime = LocalDateTime.now();
    }
  }

  @Getter
  @Setter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class MatchingFinishInfo {
    private long matchingId;
    private long orderSheetId;
    private String ordererAccountId;
    private String ordererNickname;
    private String performerAccountId;
    private String performerNickname;
    private LocalDateTime doneRequestDateTime;
    private LocalDateTime finishedDateTime;

    public MatchingFinishInfo(Matching entity) {
      this.matchingId = entity.getId();
      this.orderSheetId = entity.getOrderSheet().getId();
      this.ordererAccountId = entity.getOrderSheet().getOrderer().getAccountId();
      this.ordererNickname = entity.getOrderSheet().getOrderer().getNickname();
      this.performerAccountId = entity.getPerformer().getAccountId();
      this.performerNickname = entity.getPerformer().getNickname();

      LocalDateTime completedDateTime = entity.getCompletedDateTime();
      if (Objects.isNull(completedDateTime)) {
        entity.complete();
      }

      this.finishedDateTime = entity.getCompletedDateTime();
      this.doneRequestDateTime = LocalDateTime.now();
    }
  }
}
