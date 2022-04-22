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

@Import({MatchRequestRepository.class, MatchingRepository.class, OrdererRepository.class,
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
  @Autowired
  private MatchingRepository matchingRepository;

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
    Orderer orderer = new Orderer(accountId, password, realName, nickname, phoneNumber, accountNumber, isDoingJobNow);
    ordererRepository.saveOrderer(accountId, password, realName, nickname, phoneNumber, accountNumber);
    Orderer findOrderer = ordererRepository.findAll(1,1).get(0);

    String title = "test";
    String content = "test";
    OrderSheetCategory category =  OrderSheetCategory.DELIVERY_AND_SHOPPING;
    String destination = "test";
    Integer cost = 2000;
    Boolean isPayed = true;
    LocalDateTime wishedDeadline = LocalDateTime.now();
    OrderSheet orderSheet = new OrderSheet(findOrderer, title, content, category, destination, cost, isPayed, wishedDeadline);
    orderSheetRepository.saveOrderSheet(findOrderer, title, content, category, destination, cost, isPayed, wishedDeadline);
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

    Matching matching = new Matching(isCompleted, completedDateTime, findOrderSheet, findPerformer);
    matchingRepository.saveMatching(findOrderSheet, findPerformer, isCompleted, completedDateTime);
    Matching findMatcing = matchingRepository.findAll(1,1).get(0);

    /*String accountIdP1 = "testP";
    String passwordP1 = "testP";
    String realNameP1 = "testP";
    String nicknameP1 = "testP";
    String phoneNumberP1 = "01012344411";
    String accountNumberP1 = "111122223232";
    Boolean isDoingJobNowP1 = false;
    LocalDateTime becamePerformerDateTimeP1 = LocalDateTime.now();
    Long earnedMoneyP1 = 2000l;
    Performer performer1 = new Performer(accountIdP1, passwordP1, realNameP1, nicknameP1, phoneNumberP1, accountNumberP1, isDoingJobNowP1, becamePerformerDateTimeP1, earnedMoneyP1);
    performerRepository.savePerformer(accountIdP1, passwordP1, realNameP1, nicknameP1, phoneNumberP1, accountNumberP1);
    Performer findPerformer1 = performerRepository.findAll(1,1).get(0);
    */
    //when-then
    assertDoesNotThrow(
        ()->{
          matchRequestRepository.saveMatchRequest(findMatcing, findPerformer);
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
    Boolean isPayed = true;
    LocalDateTime wishedDeadline = LocalDateTime.now();
    OrderSheet orderSheet = new OrderSheet(findOrderer, title, content, category, destination, cost, isPayed, wishedDeadline);
    orderSheetRepository.saveOrderSheet(findOrderer, title, content, category, destination, cost, isPayed, wishedDeadline);
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

    Matching matching = new Matching(isCompleted, completedDateTime, findOrderSheet, findPerformer);
    matchingRepository.saveMatching(findOrderSheet, findPerformer, isCompleted, completedDateTime);
    Matching findMatcing = matchingRepository.findAll(1,1).get(0);

    /*//given
    Boolean isCompleted1 = false;
    LocalDateTime completedDateTime1 = LocalDateTime.now();

    String accountId1 = "test";
    String password1 = "test";
    String realName1 = "test";
    String nickname1 = "test";
    String phoneNumber1 = "01012341234";
    String accountNumber1 = "123111233411";
    Boolean isDoingJobNow1= false;
    Orderer orderer1 = new Orderer(accountId1, password1, realName1, nickname1, phoneNumber1, accountNumber1, isDoingJobNow1);
    ordererRepository.saveOrderer(accountId1, password1, realName1, nickname1, phoneNumber1, accountNumber1);
    Orderer findOrderer1 = ordererRepository.findAll(1,2).get(0);

    String title1 = "test";
    String content1 = "test";
    OrderSheetCategory category1 =  OrderSheetCategory.DELIVERY_AND_SHOPPING;
    String destination1 = "test";
    Integer cost1 = 2000;
    Boolean isPayed1 = true;
    LocalDateTime wishedDeadline1 = LocalDateTime.now();
    OrderSheet orderSheet1 = new OrderSheet(findOrderer1, title1, content1, category1, destination1, cost1, isPayed1, wishedDeadline1);
    orderSheetRepository.saveOrderSheet(findOrderer1, title1, content1, category1, destination1, cost1, isPayed1, wishedDeadline1);
    OrderSheet findOrderSheet1 = orderSheetRepository.findAll(1,2).get(0);

    String accountIdP1 = "testP";
    String passwordP1 = "testP";
    String realNameP1 = "testP";
    String nicknameP1 = "testP";
    String phoneNumberP1 = "01012344411";
    String accountNumberP1 = "111122223232";
    Boolean isDoingJobNowP1 = false;
    LocalDateTime becamePerformerDateTimeP1 = LocalDateTime.now();
    Long earnedMoneyP1 = 2000l;
    Performer performer1 = new Performer(accountIdP1, passwordP1, realNameP1, nicknameP1, phoneNumberP1, accountNumberP1, isDoingJobNowP1, becamePerformerDateTimeP1, earnedMoneyP1);
    performerRepository.savePerformer(accountIdP1, passwordP1, realNameP1, nicknameP1, phoneNumberP1, accountNumberP1);
    Performer findPerformer1 = performerRepository.findAll(1,2).get(0);

    Matching matching1 = new Matching(isCompleted1, completedDateTime1, findOrderSheet1, findPerformer1);
    matchingRepository.saveMatching(findOrderSheet1, findPerformer1, isCompleted1, completedDateTime1);
    Matching findMatcing1 = matchingRepository.findAll(1,2).get(0);
*/
    int nowPage = 1;
    int itemSize1 = 1;
  //  int itemSize2 = 2;

    matchRequestRepository.saveMatchRequest(findMatcing, findPerformer);
    //matchRequestRepository.saveMatchRequest(findMatcing1, findPerformer1);

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
    Boolean isPayed = true;
    LocalDateTime wishedDeadline = LocalDateTime.now();
    OrderSheet orderSheet = new OrderSheet(findOrderer, title, content, category, destination, cost, isPayed, wishedDeadline);
    orderSheetRepository.saveOrderSheet(findOrderer, title, content, category, destination, cost, isPayed, wishedDeadline);
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

    Matching matching = new Matching(isCompleted, completedDateTime, findOrderSheet, findPerformer);
    matchingRepository.saveMatching(findOrderSheet, findPerformer, isCompleted, completedDateTime);
    Matching findMatcing = matchingRepository.findAll(1,1).get(0);

    long pk;

    matchRequestRepository.saveMatchRequest(findMatcing, findPerformer);
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