package com.four.brothers.runtou.repository.user;

import com.four.brothers.runtou.domain.Performer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Import({PerformerRepository.class})
@DataJpaTest
class PerformerRepositoryTest {

  @Autowired
  private PerformerRepository performerRepository;

  @DisplayName("새로운 수행자 추가")
  @Test
  void savePerformerTest(){

    //given
    String accountId = "test1";
    String password = "test1";
    String realname = "testname";
    String nickname = "testnickname";
    String phoneNumber = "01012341234";
    String accountNumber = "12345678";

    //when-then
    assertDoesNotThrow(
        ()->{performerRepository.savePerformer(accountId,password,realname,nickname,phoneNumber,accountNumber);}
    );

  }

  @DisplayName("모든수행자 조회")
  @Test
  void findAllTest(){
    //given
    String accountId1 = "test1";
    String password1 = "test1";
    String realname1 = "testname1";
    String nickname1 = "testnickname1";
    String phoneNumber1 = "01012341234";
    String accountNumber1 = "12345678";

    String accountId2 = "test2";
    String password2 = "test2";
    String realname2 = "testnam2";
    String nickname2 = "testnickname2";
    String phoneNumber2 = "01012341513";
    String accountNumber2 = "1234098722";

    int nowPage = 1;
    int itemSize1 = 1;
    int itemSize2 = 2;
    performerRepository.savePerformer(accountId1, password1, realname1, nickname1, phoneNumber1, accountNumber1);
    performerRepository.savePerformer(accountId2, password2, realname2, nickname2, phoneNumber2, accountNumber2);

    //when-then
    assertAll(
        ()->{
          List<Performer> result = performerRepository.findAll(nowPage,itemSize1);
          assertSame(1,result.size());
        },
        ()->{
          List<Performer> result = performerRepository.findAll(nowPage,itemSize2);
          assertSame(2,result.size());

        }
    );

  }

  @DisplayName("계정id로 performer검색")
  @Test
  void findPerformerByAccountIdTest(){
    //given
    String accountId = "test1";
    String password = "test1";
    String realname = "testname";
    String nickname = "testnickname";
    String phoneNumber = "01012341234";
    String accountNumber = "12345678";

    performerRepository.savePerformer(accountId,password,realname,nickname,phoneNumber,accountNumber);

    //when
    Optional<Performer> result = performerRepository.findPerformerByAccountId(accountId);

    //then
    assertFalse(result.isEmpty());
  }

  @DisplayName("pk값으로 Performer 삭제")
  @Test
  void deletePerformerByIdTest(){
    //given
    String accountId = "test1";
    String password = "test1";
    String realname = "testname";
    String nickname = "testnickname";
    String phoneNumber = "01012341234";
    String accountNumber = "12345678";

    long pk;

    performerRepository.savePerformer(accountId,password,realname,nickname,phoneNumber,accountNumber);
    performerRepository.findAll(1,1);
    pk = performerRepository.findAll(1,1).get(0).getId();

    //when-then
    assertDoesNotThrow(
        ()->{
          performerRepository.deletePerformerById(pk);
        }
    );


  }
}