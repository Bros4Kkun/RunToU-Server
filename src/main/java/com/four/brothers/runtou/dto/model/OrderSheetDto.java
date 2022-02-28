package com.four.brothers.runtou.dto.model;

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
public class OrderSheetDto {
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
}
