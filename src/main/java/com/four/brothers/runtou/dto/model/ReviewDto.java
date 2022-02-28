package com.four.brothers.runtou.dto.model;

import com.four.brothers.runtou.domain.ReviewScore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ReviewDto {
  private Long id;
  private Long matchingId;
  private ReviewScore score;
  private String content;
}
