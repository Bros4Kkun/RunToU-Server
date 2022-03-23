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
    if (entity.getMatching() != null) {
      this.matchingId = entity.getMatching().getId();
    }
    this.title = entity.getTitle();
    this.content = entity.getContent();
    this.category = entity.getCategory();
    this.destination = entity.getDestination();
    this.cost = entity.getCost();
    this.isPayed = entity.getIsPayed();
    this.wishedDeadLine = entity.getWishedDeadline();
    return this;
  }

  @Override
  public String getFieldValueByName(String fieldName) {
    switch (fieldName) {
      case "id":
        return String.valueOf(this.id);
      case "ordererId":
        return String.valueOf(this.ordererId);
      case "matchingId":
        return String.valueOf(this.matchingId);
      case "title":
        return this.title;
      case "content":
        return this.content;
      case "category":
        return String.valueOf(this.category);
      case "destination":
        return destination;
      case "cost":
        return String.valueOf(this.cost);
      case "isPayed":
        return String.valueOf(this.isPayed);
      case "wishedDeadLine":
        return String.valueOf(this.wishedDeadLine);
    }
    return "";
  }
}
