package com.four.brothers.runtou.dto;

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
  }
}
