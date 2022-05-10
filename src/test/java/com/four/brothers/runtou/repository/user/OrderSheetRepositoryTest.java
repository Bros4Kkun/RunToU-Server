package com.four.brothers.runtou.repository.user;

import com.four.brothers.runtou.domain.OrderSheet;
import com.four.brothers.runtou.domain.OrderSheetCategory;
import com.four.brothers.runtou.domain.Orderer;
import com.four.brothers.runtou.repository.OrderSheetRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

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

    ordererRepository.saveOrderer(accountId, password, realName, nickname, phoneNumber, accountNumber);
    Orderer orderer = ordererRepository.findAll(1,1).get(0);

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
          orderSheetRepository.saveOrderSheet(orderer, title, content, category, destination, cost
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

    ordererRepository.saveOrderer(accountId, password, realName, nickname, phoneNumber, accountNumber);
    Orderer orderer = ordererRepository.findAll(1,1).get(0);

    String title = "장봐주세요";
    String content = "당근";
    OrderSheetCategory category = OrderSheetCategory.DELIVERY_AND_SHOPPING;
    String destination = "강서구";
    int cost = 10000;
    boolean isPayed = false;
    LocalDateTime wishedDeadline = LocalDateTime.now();

    orderSheetRepository.saveOrderSheet(orderer, title, content, category, destination, cost, isPayed, wishedDeadline);

    String accountId2 = "test2";
    String password2 = "test2";
    String realName2 = "ck";
    String nickname2 = "testck2";
    String phoneNumber2 = "01012341634";
    String accountNumber2 = "1234561190";

    ordererRepository.saveOrderer(accountId2, password2, realName2, nickname2, phoneNumber2, accountNumber2);
    Orderer orderer2 = ordererRepository.findAll(1,2).get(1);

    String title2 = "장봐주세요1";
    String content2 = "당근1";
    OrderSheetCategory category2 = OrderSheetCategory.DELIVERY_AND_SHOPPING;
    String destination2 = "강서구1";
    int cost2 = 10000;
    boolean isPayed2 = false;
    LocalDateTime wishedDeadline2 = LocalDateTime.now();

    orderSheetRepository.saveOrderSheet(orderer2, title2, content2, category2, destination2, cost2, isPayed2, wishedDeadline2);

    int nowpage = 1;
    int itemSize1 = 1;
    int itemSize2 = 2;
    //when-then
    assertAll(
        ()->{
          List<OrderSheet> result = orderSheetRepository.findAll(nowpage, itemSize1);
          assertSame(1,result.size());
        },
        ()->{
          List<OrderSheet> result = orderSheetRepository.findAll(nowpage, itemSize2);
          assertSame(2,result.size());
        }
    );
  }

  @DisplayName("금액이 지불된 주문서 조회")
  @Test
  void findAllOnlyPayed(){
    //given
    String accountId = "test1";
    String password = "test1";
    String realName = "김창건";
    String nickname = "testck";
    String phoneNumber = "01012341234";
    String accountNumber = "1234567890";

    ordererRepository.saveOrderer(accountId, password, realName, nickname, phoneNumber, accountNumber);
    Orderer orderer = ordererRepository.findAll(1,1).get(0);

    String title = "장봐주세요";
    String content = "당근";
    OrderSheetCategory category = OrderSheetCategory.DELIVERY_AND_SHOPPING;
    String destination = "강서구";
    int cost = 10000;
    boolean isPayed = true;
    LocalDateTime wishedDeadline = LocalDateTime.now();

    orderSheetRepository.saveOrderSheet(orderer, title, content, category, destination, cost, isPayed, wishedDeadline);

    int nowpage = 1;
    int itemSize = 1;

    //when
    assertAll(
        ()->{
          List<OrderSheet> result = orderSheetRepository.findAllOnlyPayed(nowpage, itemSize, category);
          assertSame(1,result.size());
        },
        ()->{
          List<OrderSheet> result = orderSheetRepository.findAllOnlyPayed(nowpage, itemSize, null);
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

    ordererRepository.saveOrderer(accountId, password, realName, nickname, phoneNumber, accountNumber);
    Orderer orderer = ordererRepository.findAll(1,1).get(0);

    String title = "장봐주세요";
    String content = "당근";
    OrderSheetCategory category = OrderSheetCategory.DELIVERY_AND_SHOPPING;
    String destination = "강서구";
    int cost = 10000;
    boolean isPayed = true;
    LocalDateTime wishedDeadline = LocalDateTime.now();
    long pk;

    orderSheetRepository.saveOrderSheet(orderer, title, content, category, destination, cost, isPayed, wishedDeadline);

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

    ordererRepository.saveOrderer(accountId, password, realName, nickname, phoneNumber, accountNumber);
    Orderer orderer = ordererRepository.findAll(1,1).get(0);

    String title = "장봐주세요";
    String content = "당근";
    OrderSheetCategory category = OrderSheetCategory.DELIVERY_AND_SHOPPING;
    String destination = "강서구";
    int cost = 10000;
    boolean isPayed = true;
    LocalDateTime wishedDeadline = LocalDateTime.now();
    long pk;
    orderSheetRepository.saveOrderSheet(orderer, title, content, category, destination, cost, isPayed, wishedDeadline);
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
