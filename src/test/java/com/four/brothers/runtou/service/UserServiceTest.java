package com.four.brothers.runtou.service;

import com.four.brothers.runtou.dto.AdminDto;
import com.four.brothers.runtou.dto.OrdererDto;
import com.four.brothers.runtou.dto.PerformerDto;
import com.four.brothers.runtou.repository.user.AdminRepository;
import com.four.brothers.runtou.repository.user.OrdererRepository;
import com.four.brothers.runtou.repository.user.PerformerRepository;
import com.four.brothers.runtou.repository.user.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
  @Mock
  OrdererRepository ordererRepository;
  @Mock
  PerformerRepository performerRepository;
  @Mock
  AdminRepository adminRepository;
  @Mock
  UserRepository userRepository;
  @Mock
  PasswordEncoder passwordEncoder;

  @DisplayName("심부름 요청자 회원가입")
  @Test
  void signUpAsOrdererTest() {
    //GIVEN
    UserService userService = new UserService(ordererRepository,
      performerRepository, adminRepository,
      userRepository, passwordEncoder);

    String accoundId1 = "testAccountId1";
    String password1 = "testPassword1";
    String realName1 = "testRealName1";
    String nickname1 = "testNickname1";
    String phoneNumber1 = "01012341234";
    String accountNumber1 = "1234567890";

    OrdererDto.SignUpAsOrdererRequest request1 = new OrdererDto.SignUpAsOrdererRequest();
    request1.setAccountId(accoundId1);
    request1.setPassword(password1);
    request1.setRealName(realName1);
    request1.setNickname(nickname1);
    request1.setPhoneNumber(phoneNumber1);
    request1.setAccountNumber(accountNumber1);

    //mock 행동 정의 - 처음 호출시에는 정상동작, 두번째 호출시에는 예외 던짐 (accountId 중복)
    willDoNothing().willThrow(new IllegalArgumentException()).given(ordererRepository).saveOrderer(
      eq(accoundId1), anyString(), anyString(), anyString(), anyString(), anyString()
      );
    given(passwordEncoder.encode(password1)).willReturn("encodedPasswordValue");

    //WHEN
    boolean result = userService.signUpAsOrderer(request1);

    //THEN
    //Success Case
    assertEquals(true, result);

    //Fail Case - 중복 정보일 때
    assertThrows(IllegalArgumentException.class,
      () -> {
        userService.signUpAsOrderer(request1);
      });

    //mock 객체 검증 - 총 2번 호출되었는지 검증
    then(ordererRepository).should(times(2)).saveOrderer(eq(accoundId1), anyString(), anyString(), anyString(), anyString(), anyString());
  }

  @DisplayName("심부름 수행자 회원가입")
  @Test
  void signUpAsPerformer() {
    //GIVEN
    UserService userService = new UserService(ordererRepository,
      performerRepository, adminRepository,
      userRepository, passwordEncoder);

    String accoundId1 = "testAccountId1";
    String password1 = "testPassword1";
    String realName1 = "testRealName1";
    String nickname1 = "testNickname1";
    String phoneNumber1 = "01012341234";
    String accountNumber1 = "1234567890";

    PerformerDto.SignUpAsPerformerRequest request = new PerformerDto.SignUpAsPerformerRequest();
    request.setAccountId(accoundId1);
    request.setPassword(password1);
    request.setRealName(realName1);
    request.setNickname(nickname1);
    request.setPhoneNumber(phoneNumber1);
    request.setAccountNumber(accountNumber1);

    //mock 행동 정의
    given(passwordEncoder.encode(anyString())).willReturn("encodedPasswordValue");
    //mock 행동 정의 - 처음 호출시에는 정상동작, 두번째 호출시에는 예외 던짐 (accountId 중복)
    willDoNothing().willThrow(new IllegalArgumentException())
      .given(performerRepository).savePerformer(eq(accoundId1), anyString(), anyString(), anyString(), anyString(), anyString());

    //WHEN
    boolean result = userService.signUpAsPerformer(request);

    //THEN
    //Success Case
    assertEquals(true, result);

    //Fail Case - 중복 아이디로 회원가입 시
    assertThrows(IllegalArgumentException.class,
      () -> {
        userService.signUpAsPerformer(request);
      });

    //mock 객체 검증 - 총 2번 호출되었는지
    then(performerRepository).should(times(2))
      .savePerformer(eq(accoundId1), anyString(), anyString(), anyString(), anyString(), anyString());
  }

  @DisplayName("관리자 등록")
  @Test
  void signUpAsAdmin() {
    //GIVEN
    UserService userService = new UserService(ordererRepository,
      performerRepository, adminRepository,
      userRepository, passwordEncoder);

    String accoundId1 = "testAccountId1";
    String password1 = "testPassword1";
    String realName1 = "testRealName1";
    String nickname1 = "testNickname1";
    String phoneNumber1 = "01012341234";
    String accountNumber1 = "1234567890";

    AdminDto.SignUpAsAdminRequest request = new AdminDto.SignUpAsAdminRequest();
    request.setAccountId(accoundId1);
    request.setPassword(password1);
    request.setRealName(realName1);
    request.setNickname(nickname1);
    request.setPhoneNumber(phoneNumber1);
    request.setAccountNumber(accountNumber1);

    //mock 행동 정의
    given(passwordEncoder.encode(anyString())).willReturn("encodedPasswordValue");
    //mock 행동 정의 - 처음 호출시에는 정상동작, 두번째 호출시에는 예외 던짐 (accountId 중복)
    willDoNothing().willThrow(new IllegalArgumentException())
      .given(adminRepository).saveAdmin(eq(accoundId1), anyString(), anyString(), anyString(), anyString(), anyString());

    //WHEN
    boolean result = userService.addNewAdmin(request);

    //THEN
    //Success Case
    assertEquals(true, result);

    //Fail Case - 중복 아이디로 회원가입 시
    assertThrows(IllegalArgumentException.class,
      () -> {
        userService.addNewAdmin(request);
      });

    //mock 객체 검증 - 총 2번 호출되었는지
    then(adminRepository).should(times(2))
      .saveAdmin(eq(accoundId1), anyString(), anyString(), anyString(), anyString(), anyString());
  }

}