package com.four.brothers.runtou.repository.user;

import com.four.brothers.runtou.domain.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@Import({ReviewRepository.class, OrdererRepository.class, OrderSheetRepository.class, PerformerRepository.class, MatchingRepository.class})
@DataJpaTest
class ReviewRepositoryTest {

  @Autowired
  private ReviewRepository reviewRepository;
  @Autowired
  private OrdererRepository ordererRepository;
  @Autowired
  private OrderSheetRepository orderSheetRepository;
  @Autowired
  private PerformerRepository performerRepository;
  @Autowired
  private MatchingRepository matchingRepository;

  @DisplayName("Review저장")
  @Test
  void saveReviewTest(){
    //given
    Boolean isCompleted = false;
    LocalDateTime completedDateTime = LocalDateTime.now();
    String accountId = "test";
    String password = "test";
    String realName = "test";
    String nickname= "test";
    String phoneNumber = "0101231231";
    String accountNumber = "11111111111";
    Boolean isDoingJobNow = true;

    Orderer orderer = new Orderer(accountId, password, realName, nickname, phoneNumber, accountNumber, isDoingJobNow);
    ordererRepository.saveOrderer(accountId, password, realName, nickname, phoneNumber, accountNumber);
    Orderer findOrderer = ordererRepository.findAll(1,1).get(0);

    String title = "장보기";
    String content = "오이2개;";
    OrderSheetCategory category = OrderSheetCategory.DELIVERY_AND_SHOPPING;
    String destination = "강남";
    Integer cost = 1000;
    Boolean isPayed = true;
    LocalDateTime wishedDeadline = LocalDateTime.now();

    OrderSheet orderSheet = new OrderSheet(findOrderer, title, content, category, destination, cost, isPayed, wishedDeadline);
    orderSheetRepository.saveOrderSheet(findOrderer, title, content, category, destination, cost, isPayed, wishedDeadline);
    OrderSheet findOrderSheet = orderSheetRepository.findAll(1,1).get(0);

    String accountIdP = "TestP";
    String passwordP = "TestP";
    String realNameP = "TestP";
    String nicknameP = "TestP";
    String phoneNumberP = "01011123321";
    String accountNumberP = "111231231111";
    Boolean isDoingJobNowP = true;
    LocalDateTime becamePerformerDateTime = LocalDateTime.now();
    Long earnedMoney =1000l;

    Performer performer = new Performer(accountIdP, passwordP, realNameP, nicknameP, phoneNumberP, accountNumberP, isDoingJobNowP, becamePerformerDateTime, earnedMoney);
    performerRepository.savePerformer(accountIdP, passwordP, realNameP, nicknameP, phoneNumberP, accountNumberP);
    Performer findPerformer = performerRepository.findAll(1,1).get(0);

    Matching matching = new Matching(isCompleted, completedDateTime, findOrderSheet, findPerformer );
    matchingRepository.saveMatching(findOrderSheet, findPerformer, isCompleted, completedDateTime);
    Matching findmatching = matchingRepository.findAll(1,1).get(0);

    ReviewScore reviewScore = ReviewScore.EIGHT;
    String contentReview = "배달이 빨랐습니다.";

    //when-then
    assertDoesNotThrow(
        ()->{
          reviewRepository.saveReview(findmatching, reviewScore, contentReview);
        }
    );
  }

  @DisplayName("모든리뷰조회")
  @Test
  void findAllTest(){
    //given
    Boolean isCompleted = false;
    LocalDateTime completedDateTime = LocalDateTime.now();
    String accountId = "test";
    String password = "test";
    String realName = "test";
    String nickname= "test";
    String phoneNumber = "0101231231";
    String accountNumber = "11111111111";
    Boolean isDoingJobNow = true;

    Orderer orderer = new Orderer(accountId, password, realName, nickname, phoneNumber, accountNumber, isDoingJobNow);
    ordererRepository.saveOrderer(accountId, password, realName, nickname, phoneNumber, accountNumber);
    Orderer findOrderer = ordererRepository.findAll(1,1).get(0);

    String title = "장보기";
    String content = "오이2개;";
    OrderSheetCategory category = OrderSheetCategory.DELIVERY_AND_SHOPPING;
    String destination = "강남";
    Integer cost = 1000;
    Boolean isPayed = true;
    LocalDateTime wishedDeadline = LocalDateTime.now();

    OrderSheet orderSheet = new OrderSheet(findOrderer, title, content, category, destination, cost, isPayed, wishedDeadline);
    orderSheetRepository.saveOrderSheet(findOrderer, title, content, category, destination, cost, isPayed, wishedDeadline);
    OrderSheet findOrderSheet = orderSheetRepository.findAll(1,1).get(0);

    String accountIdP = "TestP";
    String passwordP = "TestP";
    String realNameP = "TestP";
    String nicknameP = "TestP";
    String phoneNumberP = "01011123321";
    String accountNumberP = "111231231111";
    Boolean isDoingJobNowP = true;
    LocalDateTime becamePerformerDateTime = LocalDateTime.now();
    Long earnedMoney =1000l;

    Performer performer = new Performer(accountIdP, passwordP, realNameP, nicknameP, phoneNumberP, accountNumberP, isDoingJobNowP, becamePerformerDateTime, earnedMoney);
    performerRepository.savePerformer(accountIdP, passwordP, realNameP, nicknameP, phoneNumberP, accountNumberP);
    Performer findPerformer = performerRepository.findAll(1,1).get(0);

    Matching matching = new Matching(isCompleted, completedDateTime, findOrderSheet, findPerformer );
    matchingRepository.saveMatching(findOrderSheet, findPerformer, isCompleted, completedDateTime);
    Matching findmatching = matchingRepository.findAll(1,1).get(0);

    ReviewScore reviewScore = ReviewScore.EIGHT;
    String contentReview = "배달이 빨랐습니다.";

    int nowPage = 1;
    int itemSize = 1;

    reviewRepository.saveReview(findmatching, reviewScore, contentReview);

    //when-then
    assertAll(
        ()->{
          List<Review> result = reviewRepository.findAll(nowPage, itemSize);
          assertSame(1,result.size());
        }
    );
  }

  @DisplayName("pk로삭제")
  @Test
  void deleteReviewByIdTest(){
    //given
    Boolean isCompleted = false;
    LocalDateTime completedDateTime = LocalDateTime.now();
    String accountId = "test";
    String password = "test";
    String realName = "test";
    String nickname= "test";
    String phoneNumber = "0101231231";
    String accountNumber = "11111111111";
    Boolean isDoingJobNow = true;

    Orderer orderer = new Orderer(accountId, password, realName, nickname, phoneNumber, accountNumber, isDoingJobNow);
    ordererRepository.saveOrderer(accountId, password, realName, nickname, phoneNumber, accountNumber);
    Orderer findOrderer = ordererRepository.findAll(1,1).get(0);

    String title = "장보기";
    String content = "오이2개;";
    OrderSheetCategory category = OrderSheetCategory.DELIVERY_AND_SHOPPING;
    String destination = "강남";
    Integer cost = 1000;
    Boolean isPayed = true;
    LocalDateTime wishedDeadline = LocalDateTime.now();

    OrderSheet orderSheet = new OrderSheet(findOrderer, title, content, category, destination, cost, isPayed, wishedDeadline);
    orderSheetRepository.saveOrderSheet(findOrderer, title, content, category, destination, cost, isPayed, wishedDeadline);
    OrderSheet findOrderSheet = orderSheetRepository.findAll(1,1).get(0);

    String accountIdP = "TestP";
    String passwordP = "TestP";
    String realNameP = "TestP";
    String nicknameP = "TestP";
    String phoneNumberP = "01011123321";
    String accountNumberP = "111231231111";
    Boolean isDoingJobNowP = true;
    LocalDateTime becamePerformerDateTime = LocalDateTime.now();
    Long earnedMoney =1000l;

    Performer performer = new Performer(accountIdP, passwordP, realNameP, nicknameP, phoneNumberP, accountNumberP, isDoingJobNowP, becamePerformerDateTime, earnedMoney);
    performerRepository.savePerformer(accountIdP, passwordP, realNameP, nicknameP, phoneNumberP, accountNumberP);
    Performer findPerformer = performerRepository.findAll(1,1).get(0);

    Matching matching = new Matching(isCompleted, completedDateTime, findOrderSheet, findPerformer );
    matchingRepository.saveMatching(findOrderSheet, findPerformer, isCompleted, completedDateTime);
    Matching findmatching = matchingRepository.findAll(1,1).get(0);

    ReviewScore reviewScore = ReviewScore.EIGHT;
    String contentReview = "배달이 빨랐습니다.";

    long pk;

    reviewRepository.saveReview(findmatching, reviewScore, contentReview);
    reviewRepository.findAll(1,1);
    pk = reviewRepository.findAll(1,1).get(0).getId();

    //when-then
    assertDoesNotThrow(
        ()->{
          reviewRepository.deleteReviewById(pk);
        }
    );
  }
}