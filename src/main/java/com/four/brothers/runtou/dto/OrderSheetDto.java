package com.four.brothers.runtou.dto;

import com.four.brothers.runtou.domain.OrderSheet;
import com.four.brothers.runtou.domain.OrderSheetCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

public class OrderSheetDto {

  @Getter
  @Setter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class AllOrderSheetRequest {
    @NotNull
    private OrderSheetCategory category;
    @NotNull
    private int nowPage;
    @NotNull
    private int itemSize;
  }

  @Getter
  @Setter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class AllOrderSheetResponse {
    private List<OrderSheetItem> orderSheetItemList;
  }

  @Getter
  @Setter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class OrderSheetItem {
    private long id;
    private long matchingId;
    private String ordererAccountId;
    private String ordererNickname;
    private String title;
    private String content;
    private OrderSheetCategory category;
    private String destination;
    private int cost;
    private boolean isPayed;
    private LocalDateTime wishedDeadLine;
    private boolean isWrittenByCurrentUser;

    public OrderSheetItem(OrderSheet entity, boolean isWrittenByCurrentUser) {
      this.id = entity.getId();
      this.matchingId = entity.getMatching().getId();
      this.ordererAccountId = entity.getOrderer().getAccountId();
      this.ordererNickname = entity.getOrderer().getNickname();
      this.title = entity.getTitle();
      this.content = entity.getContent();
      this.category = entity.getCategory();
      this.destination = entity.getDestination();
      this.cost = entity.getCost();
      this.isPayed = entity.getIsPayed();
      this.wishedDeadLine = entity.getWishedDeadline();
      this.isWrittenByCurrentUser = isWrittenByCurrentUser;
    }
  }


}
