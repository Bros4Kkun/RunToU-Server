package com.four.brothers.runtou.integretion;

import com.four.brothers.runtou.dto.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import javax.transaction.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
  @DisplayName("요청자 회원가입 and 로그인")
  @Test
  void ordererSignUpTest(){
    //GIVEN-WHEN
    String jsessionid = signUpAndLoginAsOrderer();

    //THEN
    assertNotNull(true, jsessionid);
  }

  @Transactional
  @DisplayName("수행자 회원가입 and 로그인")
  @Test
  void performerSignUpTest(){
    //GIVEN
    String accountId = "testAccountId1";
    String realName = "testRealName1";
    String nickName = "testnickname1";
    String passWord = "testpassword1";
    String phoneNumber = "01011122221";
    String accountNumber = "12312312313131";
    String rawPassword = "testpassword1";
    UserRole role = UserRole.PERFORMER;

    PerformerDto.SignUpAsPerformerRequest request = new PerformerDto.SignUpAsPerformerRequest();

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
    HttpStatus resultStatusCode = template.postForEntity("/api/user/signup/performer", request, PerformerDto.SignUpAsPerformerResponse.class)
        .getStatusCode();
    HttpStatus resultStatusCode1 = template.postForEntity("/api/user/signin", request1, LoginDto.LoginResponse.class)
        .getStatusCode();

    //THEN
    assertAll(
        ()->{
          assertEquals(true, resultStatusCode.is2xxSuccessful());
        },
        ()-> {
          assertEquals(true, resultStatusCode1.is2xxSuccessful());
        });


  }

  @Transactional
  @DisplayName("계정아이디 중복 확인")
  @Test
  void DuplicatedAccountIdTest(){
    //GIVEN
    String newaccountId = "Test";
    String existaccountId = "Test";

    UserDto.DuplicatedAccountIdRequest request = new UserDto.DuplicatedAccountIdRequest();
    request.setAccountId(newaccountId);
    request.setAccountId(existaccountId);


    //WHEN
    HttpStatus resultStatusCode = template.postForEntity("/api/user/signup/accountid", request, UserDto.DuplicatedAccountIdResponse.class)
        .getStatusCode();

    //THEN
    assertEquals(true,resultStatusCode.is2xxSuccessful());
  }

  @Transactional
  @DisplayName("닉네임 중복")
  @Test
  void DuplicatedNicknameTest(){
    //GIVEN
    String newnickname = "test";
    String existnickname = "test";

    UserDto.DuplicatedNicknameRequest request = new UserDto.DuplicatedNicknameRequest();
    request.setNickname(newnickname);
    request.setNickname(existnickname);

    //WHEN
    HttpStatus resultStatusCode = template.postForEntity
        ("/api/user/signup/nickname", request, UserDto.DuplicatedNicknameResponse.class).getStatusCode();

    //THEN
    assertEquals(true, resultStatusCode.is2xxSuccessful());

  }

  @DisplayName("포인트 충전 and 조회")
  @Test
  @Transactional
  void PointChargeTest(){
    //GIVEN
    String jsessionid = signUpAndLoginAsOrderer();
    int earnPoint = 10000;
    int point = 15000;
    String accountId = "test";
    String nickname = "test";

    UserDto.PointChargeRequest request = new UserDto.PointChargeRequest();
    UserDto.PointInfo request1 = new UserDto.PointInfo();
    request.setEarnPoint(earnPoint);
    request1.setPoint(point);
    request1.setAccountId(accountId);
    request1.setNickname(nickname);

    HttpHeaders headers = new HttpHeaders();
    headers.set("Cookie", jsessionid);
    headers.set("Content-Type", "application/json");

    HttpEntity requestHttpEntity = new HttpEntity(request, headers); //요청할 HTTP 메시지
    HttpEntity requestHttpEntity1 = new HttpEntity(request1, headers); //요청할 HTTP 메시지

    //WHEN
    //HTTP 응답 메시지에 대한 모든 정보가 담겨있는 객체
    ResponseEntity<String> responseHttpEntity = template.exchange(
        "/api/user/point",
        HttpMethod.POST,
        requestHttpEntity,
        String.class
    );
    ResponseEntity<String> responseHttpEntity1 = template.exchange(
        "/api/user/point",
        HttpMethod.GET,
        requestHttpEntity1,
        String.class
    );

    //THEN
    assertAll(
        ()->{
          assertEquals(true, responseHttpEntity.getStatusCode().is2xxSuccessful());
        },
        ()->{
          assertEquals(true, responseHttpEntity1.getStatusCode().is2xxSuccessful());

        }
    );

  }

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
}

