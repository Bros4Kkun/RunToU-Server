package com.four.brothers.runtou.dto.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MatchingDto {
  private Long id;
  private Long orderSheetId;
  private Long performerId;
  private Long ReviewId;
  private Boolean isCompleted;
  private LocalDateTime completedDateTime;
}
