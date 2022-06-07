package com.four.brothers.runtou.service;

import com.four.brothers.runtou.domain.Matching;
import com.four.brothers.runtou.domain.Review;
import com.four.brothers.runtou.dto.ReviewDto;
import com.four.brothers.runtou.exception.BadRequestException;
import com.four.brothers.runtou.exception.code.MatchingExceptionCode;
import com.four.brothers.runtou.exception.code.ReviewExceptionCode;
import com.four.brothers.runtou.repository.MatchingRepository;
import com.four.brothers.runtou.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

import static com.four.brothers.runtou.dto.ReviewDto.*;

@RequiredArgsConstructor
@Service
public class ReviewService {
  private final ReviewRepository reviewRepository;
  private final MatchingRepository matchingRepository;

  /**
   * 리뷰를 추가하는 메서드
   * @param newReview
   * @param matchingId
   */
  @Transactional
  public void addNewReview(NewReview newReview, long matchingId) {
    Matching matching = getMatching(matchingId);

    reviewRepository.saveReview(matching, newReview.getScore(), newReview.getContent());
  }

  /**
   * 특정 매칭에 달린 리뷰를 찾는 메서드
   * @param matchingId
   */
  @Transactional
  public ReviewInfo showReviewByMatchingId(long matchingId) {
    Matching matching = getMatching(matchingId);
    Optional<Review> reviewOptional = reviewRepository.findByMatching(matching);

    if (reviewOptional.isEmpty()) {
      throw new BadRequestException(ReviewExceptionCode.NO_EXIST_REVIEW, "해당 매칭에는 리뷰가 존재하지 않습니다.");
    }

    return new ReviewInfo(reviewOptional.get());
  }

  private Matching getMatching(long matchingId) {
    Optional<Matching> matchingOptional = matchingRepository.findById(matchingId);
    if (matchingOptional.isEmpty()) {
      throw new BadRequestException(MatchingExceptionCode.WRONG_ID, "존재하지 않는 매칭ID 입니다.");
    }

    return matchingOptional.get();
  }

}
