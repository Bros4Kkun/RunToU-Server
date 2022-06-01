package com.four.brothers.runtou.integretion;

import com.four.brothers.runtou.dto.OrdererDto;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(
  properties = {
    "spring.config.location=classpath:application-local.properties",
    "spring.config.location=classpath:application.properties"
  },
  webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
public class UserIntegrationTest {
  @Autowired
  private TestRestTemplate template;

  @Transactional
  @DisplayName("사용자 회원가입")
  @Test
  void userSignUpTest() {
    //GIVEN
    String accountId1 = "testAccountId1";
    String realName1 = "testRealName1";
    String nickname1 = "testNickname1";
    String password1 = "testPassword1";
    String phoneNumber1 = "01011112222";
    String accountNumber1 = "1234567890";

    OrdererDto.SignUpAsOrdererRequest request = new OrdererDto.SignUpAsOrdererRequest();
    request.setAccountId(accountId1);
    request.setRealName(realName1);
    request.setNickname(nickname1);
    request.setPassword(password1);
    request.setPhoneNumber(phoneNumber1);
    request.setAccountNumber(accountNumber1);

    //WHEN
    HttpStatus resultStatusCode = template.postForEntity("/api/user/signup/orderer", request, OrdererDto.SignUpAsOrdererResponse.class)
      .getStatusCode();

    //THEN
    assertEquals(true, resultStatusCode.is2xxSuccessful());
  }
}