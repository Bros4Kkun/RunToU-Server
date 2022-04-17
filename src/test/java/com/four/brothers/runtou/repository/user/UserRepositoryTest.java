package com.four.brothers.runtou.repository.user;

import com.four.brothers.runtou.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Import({UserRepository.class})
@DataJpaTest

class UserRepositoryTest {

  @Autowired
  private UserRepository userRepository;

  @DisplayName("새로운User저장")
  @Test
  void saveUserTest(){
    //given
    String accountId = "test";
    String password = "test";
    String realName = "test";
    String nickname ="test";
    String phoneNumber = "01012341234";
    String accountNumber = "12341234123";

    //when-then
    assertDoesNotThrow(
        ()->{
          userRepository.saveUser(accountId, password, realName, nickname, phoneNumber, accountNumber);
        }
    );
  }

  @DisplayName("계정Id로 user조회")
  @Test
  void findUserByAccountIdTest(){
    //given
    String accountId = "test";
    String password = "test";
    String realName = "test";
    String nickname ="test";
    String phoneNumber = "01012341234";
    String accountNumber = "12341234123";

    userRepository.saveUser(accountId, password, realName, nickname, phoneNumber, accountNumber);

    //when
    Optional<User> result = userRepository.findUserByAccountId(accountId);

    //then
    assertFalse(result.isEmpty());

  }

  @DisplayName("nickname으로 user 조회")
  @Test
  void findUserByNicknameTest(){
    //given
    String accountId = "test";
    String password = "test";
    String realName = "test";
    String nickname ="test";
    String phoneNumber = "01012341234";
    String accountNumber = "12341234123";

    userRepository.saveUser(accountId, password, realName, nickname, phoneNumber, accountNumber);

    //when
    Optional<User> result = userRepository.findUserByAccountId(nickname);

    //then
    assertFalse(result.isEmpty());
  }

}