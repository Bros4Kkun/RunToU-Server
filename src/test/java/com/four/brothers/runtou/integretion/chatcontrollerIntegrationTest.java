package com.four.brothers.runtou.integretion;

import com.four.brothers.runtou.domain.ChatMessage;
import com.four.brothers.runtou.dto.*;
import com.four.brothers.runtou.repository.ChatMessageRepository;
import com.four.brothers.runtou.service.ChatService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(
    properties = {
        "spring.config.location=classpath:application-local.properties",
        "spring.config.location=classpath:application.properties"
    },
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)

public class chatcontrollerIntegrationTest {

  @Autowired
  private TestRestTemplate template;

  /**
   * 공용으로 사용하기 위해, 따로 빼둔 '요청자 회원가입 및 로그인 메서드'
   * @return JSESSIONID
   */
  private String signUpAndLoginAsOrderer() {
    //GIVEN
    String accountId = "testAccountId";
    String realName = "testRealName";
    String nickName = "testnickname";
    String passWord = "password";
    String phoneNumber = "01011112222";
    String accountNumber = "1231231231313";
    String rawPassword = "password";
    UserRole role = UserRole.ORDERER;

    OrdererDto.SignUpAsOrdererRequest request = new OrdererDto.SignUpAsOrdererRequest();

    request.setAccountId(accountId);
    request.setRealName(realName);
    request.setNickname(nickName);
    request.setPassword(passWord);
    request.setPhoneNumber(phoneNumber);
    request.setAccountNumber(accountNumber);

    LoginDto.LoginRequest request1 = new LoginDto.LoginRequest();

    request1.setAccountId(accountId);
    request1.setRawPassword(rawPassword);
    request1.setRole(role);

    //WHEN
    HttpStatus resultStatusCode = template.postForEntity("/api/user/signup/orderer", request, OrdererDto.SignUpAsOrdererResponse.class)
        .getStatusCode();
    HttpStatus resultStatusCode1 = template.postForEntity(
        "/api/user/signin", request1, LoginDto.LoginResponse.class).getStatusCode();
    //THEN

    List<String> strings = template.postForEntity(
        "/api/user/signin", request1, LoginDto.LoginResponse.class).getHeaders().get("Set-Cookie");

    return strings.get(0);
  }

  @Transactional
  @DisplayName("jwt토큰발급")
  @Test
  void getJwtTokenTest()
  {
    //GIVEN
    String jsessionid = signUpAndLoginAsOrderer();
    String token = "aaaaa";

    HttpHeaders headers = new HttpHeaders();
    headers.set("Cookie", jsessionid);
    headers.set("Content-Type", "application/json");

    HttpEntity requestHttpEntity = new HttpEntity(token, headers);

    ResponseEntity<String> responseHttpEntity = template.exchange(
        "/api/chat/jwt",
        HttpMethod.GET,
        requestHttpEntity,
        String.class
    );

    //THEN
    assertEquals(true, responseHttpEntity.getStatusCode().is2xxSuccessful());
  }
}
