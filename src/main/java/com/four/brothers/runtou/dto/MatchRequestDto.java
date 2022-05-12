package com.four.brothers.runtou.dto;

import com.four.brothers.runtou.domain.MatchRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class MatchRequestDto {
  @Getter
  @Setter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class MatchRequestInfo {
    private long matchRequestId;
    private OrderSheetDto.SimpOrderSheetInfo orderSheetInfo;
    private OrdererDto.SimpOrdererInfo ordererInfo;
    private PerformerDto.SimpPerformerInfo performerInfo;
    private boolean isAccepted;

    public MatchRequestInfo(MatchRequest matchRequestEntity) {
      this.matchRequestId = matchRequestEntity.getId();
      this.orderSheetInfo = new OrderSheetDto.SimpOrderSheetInfo(matchRequestEntity.getOrderSheet());
      this.ordererInfo = new OrdererDto.SimpOrdererInfo(matchRequestEntity.getOrderSheet().getOrderer());
      this.performerInfo = new PerformerDto.SimpPerformerInfo(matchRequestEntity.getPerformer());
      this.isAccepted = matchRequestEntity.getIsAccepted();
    }
  }
}
