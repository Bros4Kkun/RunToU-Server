package com.four.brothers.runtou.etc;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PasswordEncoderTest {

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Test
  @DisplayName("패스워드 암호화 테스트")
  void passwordEncode() {
    //given
    String password = "1a2b3c4d";

    //when
    String encodedPw = passwordEncoder.encode(password);

    //then
    assertAll(
      () -> assertNotEquals(password, encodedPw),
      () -> assertEquals(true, passwordEncoder.matches(password, encodedPw))
    );
  }
}
