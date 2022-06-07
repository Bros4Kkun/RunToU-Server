package com.four.brothers.runtou.service;

import com.four.brothers.runtou.domain.Matching;
import com.four.brothers.runtou.domain.Performer;
import com.four.brothers.runtou.domain.Review;
import com.four.brothers.runtou.dto.LoginDto;
import com.four.brothers.runtou.dto.ReviewDto;
import com.four.brothers.runtou.dto.UserRole;
import com.four.brothers.runtou.exception.BadRequestException;
import com.four.brothers.runtou.exception.CanNotAccessException;
import com.four.brothers.runtou.exception.code.MatchingExceptionCode;
import com.four.brothers.runtou.exception.code.ReviewExceptionCode;
import com.four.brothers.runtou.repository.MatchingRepository;
import com.four.brothers.runtou.repository.ReviewRepository;
import com.four.brothers.runtou.repository.user.PerformerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.four.brothers.runtou.dto.LoginDto.*;
import static com.four.brothers.runtou.dto.ReviewDto.*;

@RequiredArgsConstructor
@Service
public class ReviewService {
  private final ReviewRepository reviewRepository;
  private final MatchingRepository matchingRepository;
  private final PerformerRepository performerRepository;

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

  /**
   * 사용자(심부름 요청자)가 작성한 모든 리뷰 조회
   * @param loginUser
   * @return
   */
  @Transactional
  public List<ReviewInfo> showReviewsByReviewer(LoginUser loginUser) throws CanNotAccessException {

    if (loginUser.getRole() != UserRole.ORDERER) {
      throw new CanNotAccessException(ReviewExceptionCode.NOT_ORDERER, "오직 심부름 요청자만 자신이 작성한 리뷰를 확인할 수 있습니다.");
    }

    long reviewerPk = loginUser.getUserPk();
    List<Review> resultEntityList = reviewRepository.findByOrdererId(reviewerPk);
    List<ReviewInfo> response = new ArrayList<>();

    resultEntityList.stream().forEach(item -> response.add(new ReviewInfo(item)));

    return response;
  }

  @Transactional
  public List<ReviewInfo> showReceivedReviews(long performerPk) {
    Optional<Performer> performerOptional = performerRepository.findPerformerById(performerPk);

    if (performerOptional.isEmpty()) {
      throw new IllegalArgumentException("존재하지 않는 수행자 pk 값입니다.");
    }

    List<Review> resultEntityList = reviewRepository.findByPerformerId(performerPk);
    List<ReviewInfo> response = new ArrayList<>();

    resultEntityList.stream().forEach(item -> response.add(new ReviewInfo(item)));

    return response;
  }

  private Matching getMatching(long matchingId) {
    Optional<Matching> matchingOptional = matchingRepository.findById(matchingId);
    if (matchingOptional.isEmpty()) {
      throw new BadRequestException(MatchingExceptionCode.WRONG_ID, "존재하지 않는 매칭ID 입니다.");
    }

    return matchingOptional.get();
  }

}
