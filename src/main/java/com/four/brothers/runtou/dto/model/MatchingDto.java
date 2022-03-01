package com.four.brothers.runtou.dto.model;

import com.four.brothers.runtou.domain.Matching;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MatchingDto implements ModelDto<MatchingDto, Matching> {
  private Long id;
  private Long orderSheetId;
  private Long performerId;
  private Long reviewId;
  private Boolean isCompleted;
  private LocalDateTime completedDateTime;

  @Override
  public MatchingDto toDtoFromEntity(Matching entity) {
    this.id = entity.getId();
    this.orderSheetId = entity.getOrderSheet().getId();
    this.performerId = entity.getPerformer().getId();
    this.reviewId = entity.getReview().getId();
    this.isCompleted = entity.getIsCompleted();
    this.completedDateTime = entity.getCompletedDateTime();
    return this;
  }

  @Override
  public String getFieldValueByName(String fieldName) {
    switch (fieldName) {
      case "id":
        return String.valueOf(this.id);
      case "orderSheetId":
        return String.valueOf(this.orderSheetId);
      case "performerId":
        return String.valueOf(this.performerId);
      case "reviewid":
        return String.valueOf(this.reviewId);
      case "isCompleted":
        return String.valueOf(this.isCompleted);
      case "completedDateTime":
        return String.valueOf(this.completedDateTime);
    }
    return "";
  }
}
