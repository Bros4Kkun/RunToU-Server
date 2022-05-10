package com.four.brothers.runtou.dto;

import com.four.brothers.runtou.domain.Matching;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

public class MatchDto {
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

    public SimpMatchInfo(Matching entity) {
      this.id = entity.getId();
      this.orderSheetInfo = new OrderSheetDto.SimpOrderSheetInfo(entity.getOrderSheet());
      this.ordererInfo = new OrdererDto.SimpOrdererInfo(entity.getOrderSheet().getOrderer());
      this.performerInfo = new PerformerDto.SimpPerformerInfo(entity.getPerformer());
      this.isCompleted = entity.getIsCompleted();
      this.completedDateTime = entity.getCompletedDateTime();
    }
  }
}
