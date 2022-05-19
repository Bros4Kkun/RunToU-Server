package com.four.brothers.runtou.repository;

import com.four.brothers.runtou.domain.*;
import com.four.brothers.runtou.repository.MatchRequestRepository;
import com.four.brothers.runtou.repository.OrderSheetRepository;
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

@Import({MatchRequestRepository.class, OrdererRepository.class,
    OrderSheetRepository.class, PerformerRepository.class})
@DataJpaTest
class MatchRequestRepositoryTest {

  @Autowired
  private MatchRequestRepository matchRequestRepository;
  @Autowired
  private OrdererRepository ordererRepository;
  @Autowired
  private OrderSheetRepository orderSheetRepository;
  @Autowired
  private PerformerRepository performerRepository;

  @DisplayName("매칭요청 저장")
  @Test
  void saveMatchRequestTest(){
    //given
    Boolean isCompleted = false;
    LocalDateTime completedDateTime = LocalDateTime.now();

    String accountId = "test";
    String password = "test";
    String realName = "test";
    String nickname = "test";
    String phoneNumber = "01012341234";
    String accountNumber = "123111233411";
    Boolean isDoingJobNow = false;
    ordererRepository.saveOrderer(accountId, password, realName, nickname, phoneNumber, accountNumber);
    Orderer findOrderer = ordererRepository.findAll(1,1).get(0);

    String title = "test";
    String content = "test";
    OrderSheetCategory category =  OrderSheetCategory.DELIVERY_AND_SHOPPING;
    String destination = "test";
    Integer cost = 2000;
    LocalDateTime wishedDeadline = LocalDateTime.now();
    orderSheetRepository.saveOrderSheet(findOrderer, title, content, category, destination, cost, wishedDeadline);
    OrderSheet findOrderSheet = orderSheetRepository.findAll(1,1).get(0);

    String accountIdP = "testP";
    String passwordP = "testP";
    String realNameP = "testP";
    String nicknameP = "testP";
    String phoneNumberP = "01012344411";
    String accountNumberP = "111122223232";
    Boolean isDoingJobNowP = false;
    LocalDateTime becamePerformerDateTimeP = LocalDateTime.now();
    Long earnedMoneyP = 2000l;
    performerRepository.savePerformer(accountIdP, passwordP, realNameP, nicknameP, phoneNumberP, accountNumberP);
    Performer findPerformer = performerRepository.findAll(1,1).get(0);

    //when-then
    assertDoesNotThrow(
        ()->{
          matchRequestRepository.saveMatchRequest(findOrderSheet, findPerformer);
        }
    );
  }

  @DisplayName("매칭요청 조회")
  @Test
  void findAllTest(){
    //given
    Boolean isCompleted = false;
    LocalDateTime completedDateTime = LocalDateTime.now();

    String accountId = "test";
    String password = "test";
    String realName = "test";
    String nickname = "test";
    String phoneNumber = "01012341234";
    String accountNumber = "123111233411";
    Boolean isDoingJobNow = false;
    Orderer orderer = new Orderer(accountId, password, realName, nickname, phoneNumber, accountNumber, isDoingJobNow);
    ordererRepository.saveOrderer(accountId, password, realName, nickname, phoneNumber, accountNumber);
    Orderer findOrderer = ordererRepository.findAll(1,1).get(0);

    String title = "test";
    String content = "test";
    OrderSheetCategory category =  OrderSheetCategory.DELIVERY_AND_SHOPPING;
    String destination = "test";
    Integer cost = 2000;
    LocalDateTime wishedDeadline = LocalDateTime.now();
    orderSheetRepository.saveOrderSheet(findOrderer, title, content, category, destination, cost, wishedDeadline);
    OrderSheet findOrderSheet = orderSheetRepository.findAll(1,1).get(0);

    String accountIdP = "testP";
    String passwordP = "testP";
    String realNameP = "testP";
    String nicknameP = "testP";
    String phoneNumberP = "01012344411";
    String accountNumberP = "111122223232";
    Boolean isDoingJobNowP = false;
    LocalDateTime becamePerformerDateTimeP = LocalDateTime.now();
    Long earnedMoneyP = 2000l;
    Performer performer = new Performer(accountIdP, passwordP, realNameP, nicknameP, phoneNumberP, accountNumberP, isDoingJobNowP, becamePerformerDateTimeP, earnedMoneyP);
    performerRepository.savePerformer(accountIdP, passwordP, realNameP, nicknameP, phoneNumberP, accountNumberP);
    Performer findPerformer = performerRepository.findAll(1,1).get(0);

    int nowPage = 1;
    int itemSize1 = 1;

    matchRequestRepository.saveMatchRequest(findOrderSheet, findPerformer);

    //when-then
    assertAll(
        ()->{
          List<MatchRequest> result = matchRequestRepository.findAll(nowPage, itemSize1);
          assertSame(1,result.size());
        }//,
      //  ()->{
         // List<MatchRequest> result = matchRequestRepository.findAll(nowPage, itemSize2);
          //assertSame(2,result.size());
        //}

    );

  }

  @DisplayName("pk값으로 삭제")
  @Test
  void deleteMatchingRequestByIdTest(){
    //given
    Boolean isCompleted = false;
    LocalDateTime completedDateTime = LocalDateTime.now();

    String accountId = "test";
    String password = "test";
    String realName = "test";
    String nickname = "test";
    String phoneNumber = "01012341234";
    String accountNumber = "123111233411";
    Boolean isDoingJobNow = false;
    Orderer orderer = new Orderer(accountId, password, realName, nickname, phoneNumber, accountNumber, isDoingJobNow);
    ordererRepository.saveOrderer(accountId, password, realName, nickname, phoneNumber, accountNumber);
    Orderer findOrderer = ordererRepository.findAll(1,1).get(0);

    String title = "test";
    String content = "test";
    OrderSheetCategory category =  OrderSheetCategory.DELIVERY_AND_SHOPPING;
    String destination = "test";
    Integer cost = 2000;
    LocalDateTime wishedDeadline = LocalDateTime.now();
    orderSheetRepository.saveOrderSheet(findOrderer, title, content, category, destination, cost, wishedDeadline);
    OrderSheet findOrderSheet = orderSheetRepository.findAll(1,1).get(0);

    String accountIdP = "testP";
    String passwordP = "testP";
    String realNameP = "testP";
    String nicknameP = "testP";
    String phoneNumberP = "01012344411";
    String accountNumberP = "111122223232";
    Boolean isDoingJobNowP = false;
    LocalDateTime becamePerformerDateTimeP = LocalDateTime.now();
    Long earnedMoneyP = 2000l;
    Performer performer = new Performer(accountIdP, passwordP, realNameP, nicknameP, phoneNumberP, accountNumberP, isDoingJobNowP, becamePerformerDateTimeP, earnedMoneyP);
    performerRepository.savePerformer(accountIdP, passwordP, realNameP, nicknameP, phoneNumberP, accountNumberP);
    Performer findPerformer = performerRepository.findAll(1,1).get(0);

    long pk;

    matchRequestRepository.saveMatchRequest(findOrderSheet, findPerformer);
    matchRequestRepository.findAll(1,1).get(0);

    pk = matchRequestRepository.findAll(1,1).get(0).getId();

    //when=then
    assertDoesNotThrow(
        ()->{
          matchRequestRepository.deleteMatchingRequestById(pk);
        }
    );
  }

}