package com.four.brothers.runtou.repository.user;

import com.four.brothers.runtou.domain.*;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Import({MatchingRepository.class})
@DataJpaTest
class MatchingRepositoryTest {

  @Autowired
  private MatchingRepository matchingRepository;

  @DisplayName("주문정보 저장")
  @Disabled
  @Test
  void saveMatchingTest(){
    //given

    String accountId = "test1";
    String password = "test1";
    String nickname ="orderertest";
    String phoneNumber = "01012341234";
    String accountNumber = "1234567890";
    Boolean isDoingNowJob = false;
    Orderer orderer = new Orderer(accountId,password,nickname,phoneNumber,accountNumber,isDoingNowJob);

    String title = "장봐주세요";
    String content = "장보기";
    OrderSheetCategory orderSheetCategory = OrderSheetCategory.DELIVERY_AND_SHOPPING;
    String destination = "강서구";
    int cost = 10000;
    Boolean ispayed = true;
    LocalDateTime wishedDeadLine = LocalDateTime.now();

    OrderSheet orderSheet = new OrderSheet(orderer, title,content,orderSheetCategory,
        destination,cost,ispayed,wishedDeadLine);

    String accountId1 ="test1";
    String password1 = "test1";
    String nickname1 = "performertest";
    String phoneNumber1 = "01011112222";
    String accountNumber1 ="1234123412";
    Boolean isDoingJobNow1 = false;
    LocalDateTime becamePerformerDateTime = LocalDateTime.now();
    Long earnedMoney = 1000l;

    Performer performer = new Performer(accountId1,password1,nickname1,phoneNumber1
        ,accountNumber1,isDoingJobNow1,becamePerformerDateTime,earnedMoney);

    Boolean isCompleted = false;
    LocalDateTime completedDateTime = LocalDateTime.now();

    //when-then
    assertDoesNotThrow(()->{
      matchingRepository.saveMatching(orderSheet,performer,isCompleted,completedDateTime);
    });

  }

  @DisplayName("모든 매칭을 조회하는 메서드")
  @Test
  void findAllTest(){

    //given
    String accountId = "test1";
    String password = "test1";
    String nickname ="orderertest";
    String phoneNumber = "01012341234";
    String accountNumber = "1234567890";
    Boolean isDoingNowJob = false;
    Orderer orderer = new Orderer(accountId,password,nickname,phoneNumber,accountNumber,isDoingNowJob);

    String title = "장봐주세요";
    String content = "장보기";
    OrderSheetCategory orderSheetCategory = OrderSheetCategory.DELIVERY_AND_SHOPPING;
    String destination = "강서구";
    int cost = 10000;
    Boolean ispayed = true;
    LocalDateTime wishedDeadLine = LocalDateTime.now();

    OrderSheet orderSheet = new OrderSheet(orderer, title,content,orderSheetCategory,
        destination,cost,ispayed,wishedDeadLine);

    String accountId1 ="test1";
    String password1 = "test1";
    String nickname1 = "performertest";
    String phoneNumber1 = "01011112222";
    String accountNumber1 ="1234123412";
    Boolean isDoingJobNow1 = false;
    LocalDateTime becamePerformerDateTime = LocalDateTime.now();
    Long earnedMoney = 1000l;

    Performer performer = new Performer(accountId1,password1,nickname1,phoneNumber1
        ,accountNumber1,isDoingJobNow1,becamePerformerDateTime,earnedMoney);

    Boolean isCompleted = false;
    LocalDateTime completedDateTime = LocalDateTime.now();

    int nowPage = 1;
    int itemSize = 1;

    matchingRepository.saveMatching(orderSheet,performer,isCompleted,completedDateTime);

    //when-then
    assertAll(
        ()->{
          List<Matching> result = matchingRepository.findAll(nowPage,itemSize);
          assertSame(1,result.size());
        }
    );

  }

  @DisplayName("pk값으로 삭제")
  @Test
  void deleteMatchingByIdTest(){
    //given
    String accountId = "test1";
    String password = "test1";
    String nickname ="orderertest";
    String phoneNumber = "01012341234";
    String accountNumber = "1234567890";
    Boolean isDoingNowJob = false;
    Orderer orderer = new Orderer(accountId,password,nickname,phoneNumber,accountNumber,isDoingNowJob);

    String title = "장봐주세요";
    String content = "장보기";
    OrderSheetCategory orderSheetCategory = OrderSheetCategory.DELIVERY_AND_SHOPPING;
    String destination = "강서구";
    int cost = 10000;
    Boolean ispayed = true;
    LocalDateTime wishedDeadLine = LocalDateTime.now();

    OrderSheet orderSheet = new OrderSheet(orderer, title,content,orderSheetCategory,
        destination,cost,ispayed,wishedDeadLine);

    String accountId1 ="test1";
    String password1 = "test1";
    String nickname1 = "performertest";
    String phoneNumber1 = "01011112222";
    String accountNumber1 ="1234123412";
    Boolean isDoingJobNow1 = false;
    LocalDateTime becamePerformerDateTime = LocalDateTime.now();
    Long earnedMoney = 1000l;

    Performer performer = new Performer(accountId1,password1,nickname1,phoneNumber1
        ,accountNumber1,isDoingJobNow1,becamePerformerDateTime,earnedMoney);

    Boolean isCompleted = false;
    LocalDateTime completedDateTime = LocalDateTime.now();

    long pk;
    matchingRepository.saveMatching(orderSheet,performer,isCompleted,completedDateTime);
    pk = matchingRepository.findAll(1,1).get(0).getId();

    //when-then
    assertDoesNotThrow(
        ()->{
          matchingRepository.deleteMatchingById(pk);
        }
    );

  }

}