package com.four.brothers.runtou.service;

import com.four.brothers.runtou.domain.Orderer;
import com.four.brothers.runtou.domain.Performer;
import com.four.brothers.runtou.domain.User;
import com.four.brothers.runtou.dto.*;
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

import java.time.LocalDateTime;
import java.util.Optional;

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

  @DisplayName("중복 아이디 확인")
  @Test
  void isDuplicatedAccountIdTest() {
    //GIVEN
    UserService userService = new UserService(ordererRepository,
      performerRepository, adminRepository,
      userRepository, passwordEncoder);

    String existAccountId = "testAccountId1"; //이미 존재하는 아이디
    String password1 = "testPassword1";
    String realName1 = "testRealName1";
    String nickname1 = "testNickname1";
    String phoneNumber1 = "01012341234";
    String accountNumber1 = "1234567890";

    String newAccountId = "testAccountId2"; //새 아이디

    UserDto.DuplicatedAccountIdRequest alreadyExistRequest = new UserDto.DuplicatedAccountIdRequest(); //이미 존재하는 아이디 사용
    alreadyExistRequest.setAccountId(existAccountId);
    UserDto.DuplicatedAccountIdRequest newRequest = new UserDto.DuplicatedAccountIdRequest(); //새 아이디 사용
    newRequest.setAccountId(newAccountId);

    //mock 행동 정의 - 이미 존재하는 accountId 일 때
    given(userRepository.findUserByAccountId(eq(existAccountId))).willReturn(
      Optional.of(new User(existAccountId, password1, realName1, nickname1, phoneNumber1, accountNumber1))
    );

    //WHEN
    UserDto.DuplicatedAccountIdResponse duplicatedResult = userService.isDuplicatedAccountId(alreadyExistRequest);
    UserDto.DuplicatedAccountIdResponse notDuplicatedResult = userService.isDuplicatedAccountId(newRequest);

    //THEN
    assertEquals(true, duplicatedResult.isDuplicatedAccountId());
    assertEquals(false, notDuplicatedResult.isDuplicatedAccountId());
  }

  @DisplayName("중복 닉네임 확인")
  @Test
  void isDuplicatedNicknameTest() {
    //GIVEN
    UserService userService = new UserService(ordererRepository,
      performerRepository, adminRepository,
      userRepository, passwordEncoder);

    String accountId1 = "testAccountId1";
    String password1 = "testPassword1";
    String realName1 = "testRealName1";
    String alreadyExistNickname = "testNickname1"; //이미 존재하는 닉네임
    String phoneNumber1 = "01012341234";
    String accountNumber1 = "1234567890";

    String newNickname = "testNickname2"; //새 아이디

    UserDto.DuplicatedNicknameRequest alreadyExistRequest = new UserDto.DuplicatedNicknameRequest(); //이미 존재하는 아이디 사용
    alreadyExistRequest.setNickname(alreadyExistNickname);
    UserDto.DuplicatedNicknameRequest newRequest = new UserDto.DuplicatedNicknameRequest(); //새 아이디 사용
    newRequest.setNickname(newNickname);

    //mock 행동 정의 - 이미 존재하는 accountId 일 때
    given(userRepository.findUserByNickname(eq(alreadyExistNickname))).willReturn(
      Optional.of(new User(accountId1, password1, realName1, alreadyExistNickname, phoneNumber1, accountNumber1))
    );

    //WHEN
    UserDto.DuplicatedNicknameResponse duplicatedResult = userService.isDuplicatedNickname(alreadyExistRequest);
    UserDto.DuplicatedNicknameResponse notDuplicatedResult = userService.isDuplicatedNickname(newRequest);

    //THEN
    assertEquals(true, duplicatedResult.isDuplicatedNickname());
    assertEquals(false, notDuplicatedResult.isDuplicatedNickname());
  }

  @DisplayName("요청자 로그인")
  @Test
  void loginAsOrdererTest() {
    //GIVEN
    UserService userService = new UserService(ordererRepository,
      performerRepository, adminRepository,
      userRepository, passwordEncoder);

    String realName = "testRealName1";
    String nickname = "testNickname1";
    String phoneNumber = "01012341234";
    String accountNumber = "1234567890";

    long userPk = 1L;
    String rightAccountId = "rightAccountId"; // 정확한 계정 ID
    String rightRawPassword = "rightRawPassword"; // 정확한 비밀번호
    String wrongAccountId = "wrongAccountId"; // 틀린 계정 ID
    String wrongRawPassword = "wrongRawPassword"; // 틀린 비밀번호
    String encodedRightPassword = "encodedRightPassword";
    UserRole role = UserRole.ORDERER;

    LoginDto.LoginRequest loginSuccRequest = new LoginDto.LoginRequest();
    loginSuccRequest.setAccountId(rightAccountId);
    loginSuccRequest.setRawPassword(rightRawPassword);
    loginSuccRequest.setRole(role);

    LoginDto.LoginRequest loginWrongIdRequest = new LoginDto.LoginRequest();
    loginWrongIdRequest.setAccountId(wrongAccountId);
    loginWrongIdRequest.setRawPassword(rightRawPassword);
    loginWrongIdRequest.setRole(role);

    LoginDto.LoginRequest loginWrongPasswordRequest = new LoginDto.LoginRequest();
    loginWrongPasswordRequest.setAccountId(rightAccountId);
    loginWrongPasswordRequest.setRawPassword(wrongRawPassword);
    loginWrongPasswordRequest.setRole(role);

    //mock 행동 정의
    given(ordererRepository.findOrdererByAccountId(eq(rightAccountId))).willReturn(
      Optional.of(new Orderer(userPk, rightAccountId, encodedRightPassword, realName, nickname, phoneNumber, accountNumber, false))
      );
    given(passwordEncoder.matches(eq(rightRawPassword), anyString())).willReturn(true);
    given(passwordEncoder.matches(eq(wrongRawPassword), anyString())).willReturn(false);

    //WHEN
    LoginDto.LoginUser result1 = userService.loginAsOrderer(loginSuccRequest); //로그인 성공
    LoginDto.LoginUser result2 = userService.loginAsOrderer(loginWrongIdRequest); //로그인 실패 (틀린 id)
    LoginDto.LoginUser result3 = userService.loginAsOrderer(loginWrongPasswordRequest); //로그인 실패 (틀린 pw)

    //THEN
    assertAll(
      () -> assertNotNull(result1),
      () -> assertEquals(rightAccountId, result1.getAccountId()),
      () -> assertEquals(nickname, result1.getNickname()),
      () -> assertEquals(realName, result1.getRealName()),
      () -> assertEquals(accountNumber, result1.getAccountNumber()),
      () -> assertEquals(phoneNumber, result1.getPhoneNumber()),
      () -> assertSame(role, result1.getRole()),
      () -> assertNull(result2),
      () -> assertNull(result3)
    );

  }

  @DisplayName("수행자 로그인")
  @Test
  void loginAsPerformerTest() {
    //GIVEN
    UserService userService = new UserService(ordererRepository,
      performerRepository, adminRepository,
      userRepository, passwordEncoder);

    String realName = "testRealName1";
    String nickname = "testNickname1";
    String phoneNumber = "01012341234";
    String accountNumber = "1234567890";

    long userPk = 1L; //사용자 pk 값
    String rightAccountId = "rightAccountId"; // 정확한 계정 ID
    String rightRawPassword = "rightRawPassword"; // 정확한 비밀번호
    String wrongAccountId = "wrongAccountId"; // 틀린 계정 ID
    String wrongRawPassword = "wrongRawPassword"; // 틀린 비밀번호
    String encodedRightPassword = "encodedRightPassword";
    UserRole role = UserRole.PERFORMER;

    LoginDto.LoginRequest loginSuccRequest = new LoginDto.LoginRequest();
    loginSuccRequest.setAccountId(rightAccountId);
    loginSuccRequest.setRawPassword(rightRawPassword);
    loginSuccRequest.setRole(role);

    LoginDto.LoginRequest loginWrongIdRequest = new LoginDto.LoginRequest();
    loginWrongIdRequest.setAccountId(wrongAccountId);
    loginWrongIdRequest.setRawPassword(rightRawPassword);
    loginWrongIdRequest.setRole(role);

    LoginDto.LoginRequest loginWrongPasswordRequest = new LoginDto.LoginRequest();
    loginWrongPasswordRequest.setAccountId(rightAccountId);
    loginWrongPasswordRequest.setRawPassword(wrongRawPassword);
    loginWrongPasswordRequest.setRole(role);

    //mock 행동 정의
    given(performerRepository.findPerformerByAccountId(eq(rightAccountId))).willReturn(
      Optional.of(new Performer(userPk, rightAccountId, encodedRightPassword, realName, nickname, phoneNumber, accountNumber, false, LocalDateTime.now(), 0L))
    );
    given(passwordEncoder.matches(eq(rightRawPassword), anyString())).willReturn(true);
    given(passwordEncoder.matches(eq(wrongRawPassword), anyString())).willReturn(false);

    //WHEN
    LoginDto.LoginUser result1 = userService.loginAsPerformer(loginSuccRequest); //로그인 성공
    LoginDto.LoginUser result2 = userService.loginAsPerformer(loginWrongIdRequest); //로그인 실패 (틀린 id)
    LoginDto.LoginUser result3 = userService.loginAsPerformer(loginWrongPasswordRequest); //로그인 실패 (틀린 pw)

    //THEN
    assertAll(
      () -> assertNotNull(result1),
      () -> assertEquals(rightAccountId, result1.getAccountId()),
      () -> assertEquals(nickname, result1.getNickname()),
      () -> assertEquals(realName, result1.getRealName()),
      () -> assertEquals(accountNumber, result1.getAccountNumber()),
      () -> assertEquals(phoneNumber, result1.getPhoneNumber()),
      () -> assertSame(role, result1.getRole()),
      () -> assertNull(result2),
      () -> assertNull(result3)
    );

  }
}