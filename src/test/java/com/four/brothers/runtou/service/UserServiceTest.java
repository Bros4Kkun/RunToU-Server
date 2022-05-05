package com.four.brothers.runtou.service;

import com.four.brothers.runtou.dto.OrdererDto;
import com.four.brothers.runtou.dto.PerformerDto;
import com.four.brothers.runtou.repository.user.AdminRepository;
import com.four.brothers.runtou.repository.user.OrdererRepository;
import com.four.brothers.runtou.repository.user.PerformerRepository;
import com.four.brothers.runtou.repository.user.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
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
}