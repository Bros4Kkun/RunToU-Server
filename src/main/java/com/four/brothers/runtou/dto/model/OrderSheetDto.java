package com.four.brothers.runtou.dto.model;

import com.four.brothers.runtou.domain.OrderSheet;
import com.four.brothers.runtou.domain.OrderSheetCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderSheetDto implements ModelDto<OrderSheetDto, OrderSheet> {
  private Long id;
  private Long ordererId;
  private Long matchingId;
  private String title;
  private String content;
  private OrderSheetCategory category;
  private String destination;
  private Integer cost;
  private Boolean isPayed;
  private LocalDateTime wishedDeadLine;

  @Override
  public OrderSheetDto toDtoFromEntity(OrderSheet entity) {
    this.id = entity.getId();
    this.ordererId = entity.getOrderer().getId();
    this.matchingId = entity.getMatching().getId();
    this.title = entity.getTitle();
    this.content = entity.getContent();
    this.category = entity.getCategory();
    this.destination = entity.getDestination();
    this.cost = entity.getCost();
    this.isPayed = entity.getIsPayed();
    this.wishedDeadLine = entity.getWishedDeadline();
    return this;
  }
}
