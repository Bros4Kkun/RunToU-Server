package com.four.brothers.runtou.dto;

import com.four.brothers.runtou.domain.MatchRequest;
import com.four.brothers.runtou.domain.Matching;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

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
    private boolean isOrderSheetMatched;

    public MatchRequestInfo(MatchRequest matchRequestEntity) {
      this.matchRequestId = matchRequestEntity.getId();
      this.orderSheetInfo = new OrderSheetDto.SimpOrderSheetInfo(matchRequestEntity.getOrderSheet());
      this.ordererInfo = new OrdererDto.SimpOrdererInfo(matchRequestEntity.getOrderSheet().getOrderer());
      this.performerInfo = new PerformerDto.SimpPerformerInfo(matchRequestEntity.getPerformer());
      this.isAccepted = matchRequestEntity.getIsAccepted();
      this.isOrderSheetMatched = matchRequestEntity.getIsOrderSheetMatched();
    }
  }

  @Getter
  @Setter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class MatchRequestAcceptInfo {
    private long matchRequestId;
    private long matchingId;
    private long orderSheetId;
    private String ordererAccountId;
    private String ordererNickname;
    private String performerAccountId;
    private String performerNickname;
    private LocalDateTime matchRequestAcceptDateTime;

    public MatchRequestAcceptInfo(MatchRequest matchRequestEntity, Matching matchingEntity) {
      this.matchRequestId = matchRequestEntity.getId();
      this.matchingId = matchingEntity.getId();
      this.orderSheetId = matchRequestEntity.getOrderSheet().getId();
      this.ordererAccountId = matchRequestEntity.getOrderSheet().getOrderer().getAccountId();
      this.ordererNickname = matchRequestEntity.getOrderSheet().getOrderer().getNickname();
      this.performerAccountId = matchRequestEntity.getPerformer().getAccountId();
      this.performerNickname = matchRequestEntity.getPerformer().getNickname();
      this.matchRequestAcceptDateTime = LocalDateTime.now();
    }
  }
}
