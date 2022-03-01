package com.four.brothers.runtou.dto.model;

import com.four.brothers.runtou.domain.Review;
import com.four.brothers.runtou.domain.ReviewScore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ReviewDto implements ModelDto<ReviewDto, Review> {
  private Long id;
  private Long matchingId;
  private ReviewScore score;
  private String content;

  @Override
  public ReviewDto toDtoFromEntity(Review entity) {
    this.id = entity.getId();
    this.matchingId = matchingId;
    this.score = entity.getScore();
    this.content = entity.getContent();
    return this;
  }
}