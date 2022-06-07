package com.four.brothers.runtou.service;

import com.four.brothers.runtou.domain.Matching;
import com.four.brothers.runtou.dto.ReviewDto;
import com.four.brothers.runtou.exception.BadRequestException;
import com.four.brothers.runtou.exception.code.MatchingExceptionCode;
import com.four.brothers.runtou.repository.MatchingRepository;
import com.four.brothers.runtou.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ReviewService {
  private final ReviewRepository reviewRepository;
  private final MatchingRepository matchingRepository;

  @Transactional
  public void addNewReview(ReviewDto.NewReview newReview, long matchingId) {
    Optional<Matching> matchingToReview = matchingRepository.findById(matchingId);

    if (matchingToReview.isEmpty()) {
      throw new BadRequestException(MatchingExceptionCode.WRONG_ID, "존재하지 않는 매칭ID 입니다.");
    }

    reviewRepository.saveReview(matchingToReview.get(), newReview.getScore(), newReview.getContent());
  }


}
