package com.four.brothers.runtou.repository.user;

import com.four.brothers.runtou.domain.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDateTime;
import java.util.List;

import static com.sun.tools.doclint.Entity.or;
import static org.junit.jupiter.api.Assertions.*;

@Import({MatchingRepository.class, OrdererRepository.class, OrderSheetRepository.class, PerformerRepository.class})
@DataJpaTest
class MatchingRepositoryTest {

  @Autowired
  private MatchingRepository matchingRepository;
  @Autowired
  private OrdererRepository ordererRepository;
  @Autowired
  private OrderSheetRepository orderSheetRepository;
  @Autowired
  private PerformerRepository performerRepository;

  @DisplayName("매칭정보 저장")
  @Test
  void saveMatchingTest(){
    //given
    String accountId = "test";
    String password = "test";
    String realname = "test";
    String nickname = "test";
    String phoneNumber = "01021311231";
    String accountNumber = "12341234111";
    Boolean isDoingJobNow = false;
    Orderer orderer = new Orderer(accountId, password, realname, nickname, phoneNumber, accountNumber, isDoingJobNow);
    ordererRepository.saveOrderer(accountId, password, realname, nickname, phoneNumber, accountNumber);
    Orderer findOrderer = ordererRepository.findAll(1,1).get(0);

    String title = "장보기";
    String content = "당근사다주세요";
    OrderSheetCategory category = OrderSheetCategory.DELIVERY_AND_SHOPPING;
    String destination = "강남";
    Integer cost = 1000;
    Boolean isPayed = true;
    LocalDateTime wishedDeadline = LocalDateTime.now();
    OrderSheet orderSheet = new OrderSheet(orderer, title, content, category, destination, cost, isPayed, wishedDeadline);
    orderSheetRepository.saveOrderSheet(findOrderer, title, content, category, destination, cost, isPayed, wishedDeadline);
    OrderSheet findOrderSheet = orderSheetRepository.findAll(1,1).get(0);

    String accountIdP = "testP";
    String passwordP = "testP";
    String realNameP = "testP";
    String nicknameP = "testP";
    String phoneNumberP = "01012222222";
    String accountNumberP = "12312312311";
    Boolean isDoingJobNowP = false;
    LocalDateTime becamePerformerDateTimeP = LocalDateTime.now();
    Long earnedMoney = 1000l;
    Performer performer = new Performer(accountIdP, passwordP, realNameP, nicknameP, phoneNumberP,
        accountNumberP, isDoingJobNowP, becamePerformerDateTimeP, earnedMoney);
    performerRepository.savePerformer(accountIdP, passwordP, realNameP, nicknameP, phoneNumberP, accountNumberP);
    Performer findPerformer = performerRepository.findAll(1,1).get(0);

    boolean isCompleted = false;
    LocalDateTime completedDateTime = LocalDateTime.now();

    //when-then
    assertDoesNotThrow(
        ()->{
          matchingRepository.saveMatching(findOrderSheet,findPerformer,isCompleted,completedDateTime);
        }
    );

  }

  @DisplayName("모든매칭 조회 메서드")
  @Test
  void findAllTest(){
    //given
    String accountId = "test";
    String password = "test";
    String realname = "test";
    String nickname = "test";
    String phoneNumber = "01021311231";
    String accountNumber = "12341234111";
    Boolean isDoingJobNow = false;
    Orderer orderer = new Orderer(accountId, password, realname, nickname, phoneNumber, accountNumber, isDoingJobNow);
    ordererRepository.saveOrderer(accountId, password, realname, nickname, phoneNumber, accountNumber);
    Orderer findOrderer = ordererRepository.findAll(1,1).get(0);

    String title = "장보기";
    String content = "당근사다주세요";
    OrderSheetCategory category = OrderSheetCategory.DELIVERY_AND_SHOPPING;
    String destination = "강남";
    Integer cost = 1000;
    Boolean isPayed = true;
    LocalDateTime wishedDeadline = LocalDateTime.now();
    OrderSheet orderSheet = new OrderSheet(orderer, title, content, category, destination, cost, isPayed, wishedDeadline);
    orderSheetRepository.saveOrderSheet(findOrderer, title, content, category, destination, cost, isPayed, wishedDeadline);
    OrderSheet findOrderSheet = orderSheetRepository.findAll(1,1).get(0);

    String accountIdP = "testP";
    String passwordP = "testP";
    String realNameP = "testP";
    String nicknameP = "testP";
    String phoneNumberP = "01012222222";
    String accountNumberP = "12312312311";
    Boolean isDoingJobNowP = false;
    LocalDateTime becamePerformerDateTimeP = LocalDateTime.now();
    Long earnedMoney = 1000l;
    Performer performer = new Performer(accountIdP, passwordP, realNameP, nicknameP, phoneNumberP,
        accountNumberP, isDoingJobNowP, becamePerformerDateTimeP, earnedMoney);
    performerRepository.savePerformer(accountIdP, passwordP, realNameP, nicknameP, phoneNumberP, accountNumberP);
    Performer findPerformer = performerRepository.findAll(1,1).get(0);

    boolean isCompleted = false;
    LocalDateTime completedDateTime = LocalDateTime.now();

    int nowPage = 1;
    int itemSize = 1;
    matchingRepository.saveMatching(findOrderSheet,findPerformer,isCompleted,completedDateTime);

    //when-then
    assertAll(
        ()->{
          List<Matching> result = matchingRepository.findAll(nowPage, itemSize);
          assertSame(1,result.size());
        }
    );
  }

  @DisplayName("pk값으로 삭제")
  @Test
  void deleteMatchingByIdTest(){
    //given
    String accountId = "test";
    String password = "test";
    String realname = "test";
    String nickname = "test";
    String phoneNumber = "01021311231";
    String accountNumber = "12341234111";
    Boolean isDoingJobNow = false;
    Orderer orderer = new Orderer(accountId, password, realname, nickname, phoneNumber, accountNumber, isDoingJobNow);
    ordererRepository.saveOrderer(accountId, password, realname, nickname, phoneNumber, accountNumber);
    Orderer findOrderer = ordererRepository.findAll(1,1).get(0);

    String title = "장보기";
    String content = "당근사다주세요";
    OrderSheetCategory category = OrderSheetCategory.DELIVERY_AND_SHOPPING;
    String destination = "강남";
    Integer cost = 1000;
    Boolean isPayed = true;
    LocalDateTime wishedDeadline = LocalDateTime.now();
    OrderSheet orderSheet = new OrderSheet(orderer, title, content, category, destination, cost, isPayed, wishedDeadline);
    orderSheetRepository.saveOrderSheet(findOrderer, title, content, category, destination, cost, isPayed, wishedDeadline);
    OrderSheet findOrderSheet = orderSheetRepository.findAll(1,1).get(0);

    String accountIdP = "testP";
    String passwordP = "testP";
    String realNameP = "testP";
    String nicknameP = "testP";
    String phoneNumberP = "01012222222";
    String accountNumberP = "12312312311";
    Boolean isDoingJobNowP = false;
    LocalDateTime becamePerformerDateTimeP = LocalDateTime.now();
    Long earnedMoney = 1000l;
    Performer performer = new Performer(accountIdP, passwordP, realNameP, nicknameP, phoneNumberP,
        accountNumberP, isDoingJobNowP, becamePerformerDateTimeP, earnedMoney);
    performerRepository.savePerformer(accountIdP, passwordP, realNameP, nicknameP, phoneNumberP, accountNumberP);
    Performer findPerformer = performerRepository.findAll(1,1).get(0);

    boolean isCompleted = false;
    LocalDateTime completedDateTime = LocalDateTime.now();

    long pk;

    matchingRepository.saveMatching(findOrderSheet,findPerformer,isCompleted,completedDateTime);
    matchingRepository.findAll(1,1);

    pk = matchingRepository.findAll(1,1).get(0).getId();

    //when-then
    assertDoesNotThrow(
        ()->{
          matchingRepository.deleteMatchingById(pk);
        }
    );
  }
}