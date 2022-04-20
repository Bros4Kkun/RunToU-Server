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

    ordererRepository.saveOrderer(accountId, password, realname, nickname, phoneNumber, accountNumber);
    Orderer orderer = ordererRepository.findAll(1,1).get(0);

    String title = "장보기";
    String content = "당근사다주세요";
    OrderSheetCategory category = OrderSheetCategory.DELIVERY_AND_SHOPPING;
    String destination = "강남";
    Integer cost = 1000;
    Boolean isPayed = true;
    LocalDateTime wishedDeadline = LocalDateTime.now();

    orderSheetRepository.saveOrderSheet(orderer, title, content, category, destination, cost, isPayed, wishedDeadline);
    OrderSheet orderSheet = orderSheetRepository.findAll(1,1).get(0);

    String accountIdP = "testP";
    String passwordP = "testP";
    String realNameP = "testP";
    String nicknameP = "testP";
    String phoneNumberP = "01012222222";
    String accountNumberP = "12312312311";

    performerRepository.savePerformer(accountIdP, passwordP, realNameP, nicknameP, phoneNumberP, accountNumberP);
    Performer performer = performerRepository.findAll(1,1).get(0);

    boolean isCompleted = false;
    LocalDateTime completedDateTime = LocalDateTime.now();

    //when-then
    assertDoesNotThrow(
        ()->{
          matchingRepository.saveMatching(orderSheet, performer, isCompleted, completedDateTime);
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

    ordererRepository.saveOrderer(accountId, password, realname, nickname, phoneNumber, accountNumber);
    Orderer orderer = ordererRepository.findAll(1,1).get(0);

    String title = "장보기";
    String content = "당근사다주세요";
    OrderSheetCategory category = OrderSheetCategory.DELIVERY_AND_SHOPPING;
    String destination = "강남";
    Integer cost = 1000;
    Boolean isPayed = true;
    LocalDateTime wishedDeadline = LocalDateTime.now();

    orderSheetRepository.saveOrderSheet(orderer, title, content, category, destination, cost, isPayed, wishedDeadline);
    OrderSheet orderSheet = orderSheetRepository.findAll(1,1).get(0);

    String accountIdP = "testP";
    String passwordP = "testP";
    String realNameP = "testP";
    String nicknameP = "testP";
    String phoneNumberP = "01012222222";
    String accountNumberP = "12312312311";

    performerRepository.savePerformer(accountIdP, passwordP, realNameP, nicknameP, phoneNumberP, accountNumberP);
    Performer performer = performerRepository.findAll(1,1).get(0);

    String accountId1 = "test1";
    String password1 = "test1";
    String realname1 = "test1";
    String nickname1 = "test1";
    String phoneNumber1 = "01022311231";
    String accountNumber1 = "12351234111";

    ordererRepository.saveOrderer(accountId1, password1, realname1, nickname1, phoneNumber1, accountNumber1);
    Orderer orderer1 = ordererRepository.findAll(1,2).get(1);

    String title1 = "장보기1";
    String content1 = "당근사다주세요1";
    OrderSheetCategory category1 = OrderSheetCategory.DELIVERY_AND_SHOPPING;
    String destination1 = "강남";
    Integer cost1 = 10000;
    Boolean isPayed1 = true;
    LocalDateTime wishedDeadline1 = LocalDateTime.now();

    orderSheetRepository.saveOrderSheet(orderer1, title1, content1, category1, destination1, cost1, isPayed1, wishedDeadline1);
    OrderSheet orderSheet1  = orderSheetRepository.findAll(1,2).get(1);

    String accountIdP1 = "testP1";
    String passwordP1 = "testP1";
    String realNameP1 = "testP1";
    String nicknameP1 = "testP1";
    String phoneNumberP1 = "01022222222";
    String accountNumberP1 = "12412312311";

    performerRepository.savePerformer(accountIdP1, passwordP1, realNameP1, nicknameP1, phoneNumberP1, accountNumberP1);
    Performer performer1 = performerRepository.findAll(1,2).get(1);

    boolean isCompleted = false;
    LocalDateTime completedDateTime = LocalDateTime.now();

    int nowPage = 1;
    int itemSize = 1;
    int itemSize2 = 2;
    matchingRepository.saveMatching(orderSheet,performer,isCompleted,completedDateTime);
    matchingRepository.saveMatching(orderSheet1,performer1,isCompleted,completedDateTime);

    //when-then
    assertAll(
        ()->{
          List<Matching> result = matchingRepository.findAll(nowPage, itemSize);
          assertSame(1,result.size());
        },
        ()->{
          List<Matching> result = matchingRepository.findAll(nowPage, itemSize2);
          assertSame(2,result.size());

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

    ordererRepository.saveOrderer(accountId, password, realname, nickname, phoneNumber, accountNumber);
    Orderer orderer = ordererRepository.findAll(1,1).get(0);

    String title = "장보기";
    String content = "당근사다주세요";
    OrderSheetCategory category = OrderSheetCategory.DELIVERY_AND_SHOPPING;
    String destination = "강남";
    Integer cost = 1000;
    Boolean isPayed = true;
    LocalDateTime wishedDeadline = LocalDateTime.now();

    orderSheetRepository.saveOrderSheet(orderer, title, content, category, destination, cost, isPayed, wishedDeadline);
    OrderSheet orderSheet = orderSheetRepository.findAll(1,1).get(0);

    String accountIdP = "testP";
    String passwordP = "testP";
    String realNameP = "testP";
    String nicknameP = "testP";
    String phoneNumberP = "01012222222";
    String accountNumberP = "12312312311";

    performerRepository.savePerformer(accountIdP, passwordP, realNameP, nicknameP, phoneNumberP, accountNumberP);
    Performer performer = performerRepository.findAll(1,1).get(0);

    boolean isCompleted = false;
    LocalDateTime completedDateTime = LocalDateTime.now();

    long pk;

    matchingRepository.saveMatching(orderSheet, performer, isCompleted, completedDateTime);
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