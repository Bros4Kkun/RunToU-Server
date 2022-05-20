package com.four.brothers.runtou.repository;

import com.four.brothers.runtou.domain.*;
import com.four.brothers.runtou.repository.MatchingRepository;
import com.four.brothers.runtou.repository.OrderSheetRepository;
import com.four.brothers.runtou.repository.ReviewRepository;
import com.four.brothers.runtou.repository.user.OrdererRepository;
import com.four.brothers.runtou.repository.user.PerformerRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

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
    boolean completionRequest = false;
    LocalDateTime completedDateTime = LocalDateTime.now();
    String accountId = "test";
    String password = "test";
    String realName = "test";
    String nickname= "test";
    String phoneNumber = "0101231231";
    String accountNumber = "11111111111";
    Boolean isDoingJobNow = true;

    ordererRepository.saveOrderer(accountId, password, realName, nickname, phoneNumber, accountNumber);
    Orderer orderer = ordererRepository.findAll(1,1).get(0);

    String title = "장보기";
    String content = "오이2개;";
    OrderSheetCategory category = OrderSheetCategory.DELIVERY_AND_SHOPPING;
    String destination = "강남";
    Integer cost = 1000;
    LocalDateTime wishedDeadline = LocalDateTime.now();

    orderSheetRepository.saveOrderSheet(orderer, title, content, category, destination, cost, wishedDeadline);
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

    performerRepository.savePerformer(accountIdP, passwordP, realNameP, nicknameP, phoneNumberP, accountNumberP);
    Performer performer = performerRepository.findAll(1,1).get(0);

    matchingRepository.saveMatching(findOrderSheet, performer, isCompleted, completedDateTime, completionRequest);
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
    boolean completionRequest = false;
    LocalDateTime completedDateTime = LocalDateTime.now();
    String accountId = "test";
    String password = "test";
    String realName = "test";
    String nickname= "test";
    String phoneNumber = "0101231231";
    String accountNumber = "11111111111";
    Boolean isDoingJobNow = true;

    ordererRepository.saveOrderer(accountId, password, realName, nickname, phoneNumber, accountNumber);
    Orderer orderer = ordererRepository.findAll(1,1).get(0);

    String title = "장보기";
    String content = "오이2개;";
    OrderSheetCategory category = OrderSheetCategory.DELIVERY_AND_SHOPPING;
    String destination = "강남";
    Integer cost = 1000;
    LocalDateTime wishedDeadline = LocalDateTime.now();

    orderSheetRepository.saveOrderSheet(orderer, title, content, category, destination, cost, wishedDeadline);
    OrderSheet orderSheet = orderSheetRepository.findAll(1,1).get(0);

    String accountIdP = "TestP";
    String passwordP = "TestP";
    String realNameP = "TestP";
    String nicknameP = "TestP";
    String phoneNumberP = "01011123321";
    String accountNumberP = "111231231111";
    Boolean isDoingJobNowP = true;
    LocalDateTime becamePerformerDateTime = LocalDateTime.now();
    Long earnedMoney =1000l;

    performerRepository.savePerformer(accountIdP, passwordP, realNameP, nicknameP, phoneNumberP, accountNumberP);
    Performer performer = performerRepository.findAll(1,1).get(0);

    matchingRepository.saveMatching(orderSheet, performer, isCompleted, completedDateTime, completionRequest);
    Matching matching = matchingRepository.findAll(1,1).get(0);

    ReviewScore reviewScore = ReviewScore.EIGHT;
    String contentReview = "배달이 빨랐습니다.";

    Boolean isCompleted1 = false;
    boolean completionRequest1 = false;
    LocalDateTime completedDateTime1 = LocalDateTime.now();
    String accountId1 = "test1";
    String password1 = "test1";
    String realName1 = "test1";
    String nickname1 = "test1";
    String phoneNumber1 = "0111231231";
    String accountNumber1 = "12111111111";
    Boolean isDoingJobNow1 = true;

    ordererRepository.saveOrderer(accountId1, password1, realName1, nickname1, phoneNumber1, accountNumber1);
    Orderer orderer1 = ordererRepository.findAll(1,2).get(1);

    String title1 = "장보기1";
    String content1 = "오이1개;";
    OrderSheetCategory category1 = OrderSheetCategory.DELIVERY_AND_SHOPPING;
    String destination1 = "강남";
    Integer cost1 = 1000;
    LocalDateTime wishedDeadline1 = LocalDateTime.now();

    orderSheetRepository.saveOrderSheet(orderer1, title1, content1, category1, destination1, cost1, wishedDeadline1);
    OrderSheet orderSheet1 = orderSheetRepository.findAll(1,2).get(0);

    String accountIdP1 = "TestP1";
    String passwordP1 = "TestP1";
    String realNameP1 = "TestP1";
    String nicknameP1 = "TestP1";
    String phoneNumberP1 = "01211123321";
    String accountNumberP1 = "101231231111";
    Boolean isDoingJobNowP1 = true;
    LocalDateTime becamePerformerDateTime1 = LocalDateTime.now();
    Long earnedMoney1 =10000l;

    performerRepository.savePerformer(accountIdP1, passwordP1, realNameP1, nicknameP1, phoneNumberP1, accountNumberP1);
    Performer performer1 = performerRepository.findAll(1,2).get(1);

    matchingRepository.saveMatching(orderSheet1, performer1, isCompleted1, completedDateTime1, completionRequest);
    Matching matching1 = matchingRepository.findAll(1,2).get(1);

    ReviewScore reviewScore1 = ReviewScore.FIVE;
    String contentReview1 = "배달이 빨랐습니다.1";

    int nowPage = 1;
    int itemSize = 1;
    int itemSize2 = 2;

    reviewRepository.saveReview(matching, reviewScore, contentReview);
    reviewRepository.saveReview(matching1, reviewScore1, contentReview1);

    //when-then
    assertAll(
        ()->{
          List<Review> result = reviewRepository.findAll(nowPage, itemSize);
          assertSame(1,result.size());
        },
        ()->{
          List<Review> result = reviewRepository.findAll(nowPage, itemSize2);
          assertSame(2,result.size());

        }
    );
  }

  @DisplayName("pk로삭제")
  @Test
  void deleteReviewByIdTest(){
    //given
    Boolean isCompleted = false;
    boolean completionRequest = false;
    LocalDateTime completedDateTime = LocalDateTime.now();
    String accountId = "test";
    String password = "test";
    String realName = "test";
    String nickname= "test";
    String phoneNumber = "0101231231";
    String accountNumber = "11111111111";
    Boolean isDoingJobNow = true;

    ordererRepository.saveOrderer(accountId, password, realName, nickname, phoneNumber, accountNumber);
    Orderer orderer = ordererRepository.findAll(1,1).get(0);

    String title = "장보기";
    String content = "오이2개;";
    OrderSheetCategory category = OrderSheetCategory.DELIVERY_AND_SHOPPING;
    String destination = "강남";
    Integer cost = 1000;
    LocalDateTime wishedDeadline = LocalDateTime.now();

    orderSheetRepository.saveOrderSheet(orderer, title, content, category, destination, cost, wishedDeadline);
    OrderSheet orderSheet = orderSheetRepository.findAll(1,1).get(0);

    String accountIdP = "TestP";
    String passwordP = "TestP";
    String realNameP = "TestP";
    String nicknameP = "TestP";
    String phoneNumberP = "01011123321";
    String accountNumberP = "111231231111";
    Boolean isDoingJobNowP = true;
    LocalDateTime becamePerformerDateTime = LocalDateTime.now();
    Long earnedMoney =1000l;

    performerRepository.savePerformer(accountIdP, passwordP, realNameP, nicknameP, phoneNumberP, accountNumberP);
    Performer performer = performerRepository.findAll(1,1).get(0);

    Matching matching = new Matching(isCompleted, completedDateTime, orderSheet, performer, completionRequest);
    matchingRepository.saveMatching(orderSheet, performer, isCompleted, completedDateTime, completionRequest);
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