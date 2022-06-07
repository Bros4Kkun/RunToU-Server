package com.four.brothers.runtou.dto;

import com.four.brothers.runtou.domain.Matching;
import com.four.brothers.runtou.domain.Review;
import com.four.brothers.runtou.domain.ReviewScore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class ReviewDto {
  @Getter
  @Setter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class ReviewInfo {
    private long id;
    private long matching_id;
    private ReviewScore score;
    private String content;


    public ReviewInfo(Review entity) {
      this.id = entity.getId();
      this.matching_id = entity.getMatching().getId();
      this.score = entity.getScore();
      this.content = entity.getContent();
    }
  }

  @Getter
  @Setter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class NewReview {
    @NotNull
    private ReviewScore score;
    @NotEmpty
    private String content;
  }
}
