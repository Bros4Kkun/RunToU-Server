package com.four.brothers.runtou.repository.user;

import com.four.brothers.runtou.domain.OrderSheet;
import com.four.brothers.runtou.domain.OrderSheetCategory;
import com.four.brothers.runtou.domain.Orderer;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Import({OrderSheetRepository.class, OrdererRepository.class})
@DataJpaTest
class OrderSheetRepositoryTest {

  @Autowired
  private OrderSheetRepository orderSheetRepository;

  @Autowired
  private OrdererRepository ordererRepository;

  @DisplayName("주문서 저장")
  @Test
  void saveOrderSheetTest(){
    //given
    String accountId = "test1";
    String password = "test1";
    String realName = "김창건";
    String nickname = "testck";
    String phoneNumber = "01012341234";
    String accountNumber = "1234567890";
    Boolean isDoingJobnow = false;

    Orderer orderer = new Orderer(accountId, password, realName, nickname, phoneNumber, accountNumber, isDoingJobnow);

    ordererRepository.saveOrderer(accountId, password, realName, nickname, phoneNumber, accountNumber);
    Orderer findOrderer = ordererRepository.findAll(1,1).get(0);

    String title = "장봐주세요";
    String content = "당근";
    OrderSheetCategory category = OrderSheetCategory.DELIVERY_AND_SHOPPING;
    String destination = "강서구";
    int cost = 10000;
    boolean isPayed = true;
    LocalDateTime wishedDeadline = LocalDateTime.now();

    //when-then
    assertDoesNotThrow(
        ()->{
          orderSheetRepository.saveOrderSheet(findOrderer, title, content, category, destination, cost
              , isPayed, wishedDeadline);
        }
    );

  }
  @DisplayName("모든 주문서 조회")
  @Test
  void findAllTest(){
    //given
    String accountId = "test1";
    String password = "test1";
    String realName = "김창건";
    String nickname = "testck";
    String phoneNumber = "01012341234";
    String accountNumber = "1234567890";
    Boolean isDoingJobnow = false;

    Orderer orderer = new Orderer(accountId, password, realName, nickname, phoneNumber, accountNumber, isDoingJobnow);

    ordererRepository.saveOrderer(accountId, password, realName, nickname, phoneNumber, accountNumber);
    Orderer findOrderer = ordererRepository.findAll(1,1).get(0);


    String title = "장봐주세요";
    String content = "당근";
    OrderSheetCategory category = OrderSheetCategory.DELIVERY_AND_SHOPPING;
    String destination = "강서구";
    int cost = 10000;
    boolean isPayed = false;
    LocalDateTime wishedDeadline = LocalDateTime.now();

    orderSheetRepository.saveOrderSheet(findOrderer, title, content, category, destination, cost, isPayed, wishedDeadline);

    int nowpage = 1;
    int itemSize = 1;
    //when
    assertAll(
        ()->{
          List<OrderSheet> result = orderSheetRepository.findAll(nowpage,itemSize);
          assertSame(1,result.size());
        }
    );
  }
//헷갈림, 카테고리 기준걸고 조회?
  @DisplayName("금액이 지불된 주문서 조회")
  @Test
  @Disabled
  void findAllOnlyPayed(){
    //given
    String accountId = "test1";
    String password = "test1";
    String realName = "김창건";
    String nickname = "testck";
    String phoneNumber = "01012341234";
    String accountNumber = "1234567890";
    Boolean isDoingJobnow = false;

    Orderer orderer = new Orderer(accountId, password, realName, nickname, phoneNumber, accountNumber, isDoingJobnow);

    ordererRepository.saveOrderer(accountId, password, realName, nickname, phoneNumber, accountNumber);
    Orderer findOrderer = ordererRepository.findAll(1,1).get(0);


    String title = "장봐주세요";
    String content = "당근";
    OrderSheetCategory category = OrderSheetCategory.DELIVERY_AND_SHOPPING;
    String destination = "강서구";
    int cost = 10000;
    boolean isPayed = true;
    LocalDateTime wishedDeadline = LocalDateTime.now();

    orderSheetRepository.saveOrderSheet(findOrderer, title, content, category, destination, cost, isPayed, wishedDeadline);

    int nowpage = 1;
    int itemSize = 1;

    //when
    assertAll(
        ()->{
          List<OrderSheet> result = orderSheetRepository.findAllOnlyPayed(nowpage, itemSize, category);
          assertSame(1,result.size());
        }
    );
  }

  @DisplayName("pk값으로 ordersheet조회")
  @Test
  void findByIdTest(){
    //given
    String accountId = "test1";
    String password = "test1";
    String realName = "김창건";
    String nickname = "testck";
    String phoneNumber = "01012341234";
    String accountNumber = "1234567890";
    Boolean isDoingJobnow = false;

    Orderer orderer = new Orderer(accountId, password, realName, nickname, phoneNumber, accountNumber, isDoingJobnow);

    ordererRepository.saveOrderer(accountId, password, realName, nickname, phoneNumber, accountNumber);
    Orderer findOrderer = ordererRepository.findAll(1,1).get(0);

    String title = "장봐주세요";
    String content = "당근";
    OrderSheetCategory category = OrderSheetCategory.DELIVERY_AND_SHOPPING;
    String destination = "강서구";
    int cost = 10000;
    boolean isPayed = true;
    LocalDateTime wishedDeadline = LocalDateTime.now();
    long pk;

    orderSheetRepository.saveOrderSheet(findOrderer, title, content, category, destination, cost, isPayed, wishedDeadline);

    pk = orderSheetRepository.findAll(1,1).get(0).getId();

    //when
    Optional<OrderSheet> result = orderSheetRepository.findById(pk);

    //then
    assertTrue(result.isPresent());

  }
  @DisplayName("pk값으로 삭제")
  @Test
  void deleteOrderSheetByIdTest(){
    //given
    String accountId = "test1";
    String password = "test1";
    String realName = "김창건";
    String nickname = "testck";
    String phoneNumber = "01012341234";
    String accountNumber = "1234567890";
    Boolean isDoingJobnow = false;

    Orderer orderer = new Orderer(accountId, password, realName, nickname, phoneNumber, accountNumber, isDoingJobnow);

    ordererRepository.saveOrderer(accountId, password, realName, nickname, phoneNumber, accountNumber);
    Orderer findOrderer = ordererRepository.findAll(1,1).get(0);


    String title = "장봐주세요";
    String content = "당근";
    OrderSheetCategory category = OrderSheetCategory.DELIVERY_AND_SHOPPING;
    String destination = "강서구";
    int cost = 10000;
    boolean isPayed = true;
    LocalDateTime wishedDeadline = LocalDateTime.now();
    long pk;
    orderSheetRepository.saveOrderSheet(findOrderer, title, content, category, destination, cost, isPayed, wishedDeadline);
    orderSheetRepository.findAll(1,1);

    pk = orderSheetRepository.findAll(1,1).get(0).getId();

    //when-then
    assertDoesNotThrow(
        ()->{
          orderSheetRepository.deleteOrderSheetById(pk);
        }
    );

  }

}
