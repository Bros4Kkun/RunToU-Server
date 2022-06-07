package com.four.brothers.runtou.controller;

import com.four.brothers.runtou.dto.LoginDto;
import com.four.brothers.runtou.dto.ReviewDto;
import com.four.brothers.runtou.exception.BadRequestException;
import com.four.brothers.runtou.exception.CanNotAccessException;
import com.four.brothers.runtou.exception.code.RequestExceptionCode;
import com.four.brothers.runtou.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.four.brothers.runtou.dto.LoginDto.*;
import static com.four.brothers.runtou.dto.ReviewDto.*;

@Slf4j
@Tag(name = "ReviewController", description = "리뷰 관련 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/review")
public class ReviewController {

  private final ReviewService reviewService;

  @Operation(summary = "새로운 리뷰 등록")
  @PostMapping("/matching/{matchingId}")
  public boolean addNewReview(@PathVariable long matchingId,
                              @Validated @RequestBody NewReview newReview,
                              BindingResult bindingResult) {

    if (bindingResult.hasErrors()) {
      throw new BadRequestException(RequestExceptionCode.WRONG_FORMAT, bindingResult.getFieldError().getDefaultMessage());
    }

    reviewService.addNewReview(newReview, matchingId);

    return true;
  }

  @Operation(summary = "매칭pk값으로 리뷰 조회", description = "해당 매칭에 달린 리뷰 찾기")
  @GetMapping("/matching/{matchingId}")
  public ReviewInfo getReviewByMatchingId(@PathVariable long matchingId) {
    ReviewInfo result = reviewService.showReviewByMatchingId(matchingId);
    return result;
  }

  @Operation(summary = "로그인한 유저(요청자)가 작성한 모든 리뷰 조회")
  @GetMapping("/orderer/own")
  public List<ReviewInfo> getReviewsByReviewer(@Parameter(hidden = true) @SessionAttribute LoginUser loginUser)
      throws CanNotAccessException {
    List<ReviewInfo> result = reviewService.showReviewsByReviewer(loginUser);
    return result;
  }

}
