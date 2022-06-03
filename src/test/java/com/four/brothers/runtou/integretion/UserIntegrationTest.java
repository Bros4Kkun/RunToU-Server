package com.four.brothers.runtou.integretion;

import com.four.brothers.runtou.domain.User;
import com.four.brothers.runtou.dto.*;
import com.four.brothers.runtou.repository.user.OrdererRepository;
import com.four.brothers.runtou.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertAll;
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
  @Autowired
  private OrdererRepository ordererRepository;
  @Transactional
  @DisplayName("요청자 회원가입 and 로그인")
  @Test
  void ordererSignUpTest(){
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
    //assertEquals(true, resultStatusCode.is2xxSuccessful());
    assertAll(
        ()->{
          assertEquals(true, resultStatusCode.is2xxSuccessful());
        },
        ()->{
          assertEquals(true, resultStatusCode1.is2xxSuccessful());
        }
    );

  }

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
  //I/O error on POST request 오류
  //adimnresponse가 아닌 ordererresponse??
  @DisplayName("관리자 등록")
  @Test
  void SignUpAsAdminTest(){
    //GIVEN
    String accountId = "admintest";
    String realName = "adminrealname";
    String nickname = "adminnickname";
    String password = "adminpassword";
    String phoneNumber = "01088223392";
    String accountNumber = "1112223323232";

    AdminDto.SignUpAsAdminRequest request = new AdminDto.SignUpAsAdminRequest();

    request.setAccountId(accountId);
    request.setRealName(realName);
    request.setNickname(nickname);
    request.setPassword(password);
    request.setPhoneNumber(phoneNumber);
    request.setAccountNumber(accountNumber);

    //WHEN
    HttpStatus resultStatusCode = template.postForEntity("/api/user/admin", request,OrdererDto.SignUpAsOrdererResponse.class)
            .getStatusCode();

    //THEN
    assertEquals(true, resultStatusCode.is2xxSuccessful());
  }
//I/O error on POST request 오류...
  @DisplayName("포인트 충전")
  @Test
  void PointChargeTest(){
    //GIVEN
    int earnPoint = 10000;

    UserDto.PointChargeRequest request = new UserDto.PointChargeRequest();
    request.setEarnPoint(earnPoint);

    //WHEN
    HttpStatus resultStatusCode = template.postForEntity("/api/user/point", request, UserDto.PointChargeResponse.class).getStatusCode();

    //THEN
    assertEquals(true, resultStatusCode.is2xxSuccessful());
  }
}
