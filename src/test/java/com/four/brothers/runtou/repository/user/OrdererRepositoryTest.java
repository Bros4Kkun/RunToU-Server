package com.four.brothers.runtou.repository.user;

import com.four.brothers.runtou.domain.Orderer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Import({OrdererRepository.class})
@DataJpaTest

class OrdererRepositoryTest {

  @Autowired
  private OrdererRepository ordererRepository;

  @DisplayName("Orderer저장")
  @Test
  void saveOrdererTest()
  {
    //given
    String accountId = "test1";
    String password = "1234";
    String nickname ="ck";
    String phoneNumber = "01012341234";
    String accountNumber = "123451233";

    //when-then
    assertDoesNotThrow(
        ()->{ordererRepository.saveOrderer(accountId, password, nickname,phoneNumber,accountNumber);}
    );
  }

  @DisplayName("pk값으로 Orderer검색하기")
  @Test
  void findbyOrdererByIdTest(){
    //given
    String accountId = "test1";
    String password = "1234";
    String nickname ="ck";
    String phoneNumber = "01012341234";
    String accountNumber = "123451233";
    String pk;

    ordererRepository.saveOrderer(accountId,password,nickname,phoneNumber,accountNumber);

    pk = ordererRepository.findOrdererById(nickname).get().getNickname();
//    pk = ordererRepository.findOrdererById(nickname).get().getId()();

    //when
    Optional<Orderer> result = ordererRepository.findOrdererByAccountId(pk);

    //then
    assertTrue(result.isPresent());

  }

  @DisplayName("계정 id로 Orderer 검색")
  @Test
  void findByOrdererByAccountIdTest(){
    //given
    String accountId = "test1";
    String password = "1234";
    String nickname ="ck";
    String phoneNumber = "01012341234";
    String accountNumber = "123451233";

    ordererRepository.saveOrderer(accountId,password,nickname,phoneNumber,accountNumber);

    //when
    Optional<Orderer> result =ordererRepository.findOrdererByAccountId(accountId);

    //then
    assertFalse(result.isEmpty());
  }
  @DisplayName("모든 orderer 조회")
  @Test
  void findAllTest(){
    //given
    String accountId1 = "test1";
    String password1 = "1234";
    String nickname1 ="ck";
    String phoneNumber1 = "01012341234";
    String accountNumber1 = "123451233";

    String accountId2 = "test2";
    String password2= "1234";
    String nickname2 ="cg";
    String phoneNumber2 = "01012341234";
    String accountNumber2 = "123451233";

    int nowPage = 1;
    int itemSize1 = 1;
    int itemSize2 =2;

    ordererRepository.saveOrderer(accountId1,password1,nickname1,phoneNumber1,accountNumber1);
    ordererRepository.saveOrderer(accountId2,password2,nickname2,phoneNumber2,accountNumber2);

    //when-then
    assertAll(
        ()->{
          List<Orderer> result = ordererRepository.findAll(nowPage,itemSize1);
          assertSame(1,result.size());
        },
        ()->{
          List<Orderer> result = ordererRepository.findAll(nowPage,itemSize2);
          assertSame(2,result.size());
        },
        ()->{
          List<Orderer> result = ordererRepository.findAll(nowPage,itemSize1);
          assertNotSame(2,result.size());
        }

    );

  }

  @DisplayName("pk값으로 Orderer삭제")
  @Test
  void deleteOrdererByIdTest(){
    String accountId1 = "test1";
    String password1 = "1234";
    String nickname1 ="ck";
    String phoneNumber1 = "01012341234";
    String accountNumber1 = "123451233";

    long pk;

    ordererRepository.saveOrderer(accountId1,password1,nickname1,phoneNumber1,accountNumber1);
    ordererRepository.findAll(1,1);

    pk = ordererRepository.findAll(1,1).get(0).getId();

    //when-then
    assertDoesNotThrow(
        ()->ordererRepository.deleteOrdererById(pk)
    );
    
  }

  @DisplayName("계정id값으로 orderer삭제")
  @Test
  void deleteOrdererByAccountIdTest(){

    //given
    String accountId1 = "test1";
    String password1 = "1234";
    String nickname1 ="ck";
    String phoneNumber1 = "01012341234";
    String accountNumber1 = "123451233";
    String keyword;
    ordererRepository.saveOrderer(accountId1,password1,nickname1,phoneNumber1,accountNumber1);

    keyword = ordererRepository.findOrdererByAccountId(accountId1).get().getAccountId();

    //when-then
    assertDoesNotThrow(
        ()->ordererRepository.deleteOrdererByAccountId(keyword)
    );
  }
}