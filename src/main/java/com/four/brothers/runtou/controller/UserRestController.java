package com.four.brothers.runtou.controller;

import com.four.brothers.runtou.dto.UserRole;
import com.four.brothers.runtou.exception.BadRequestException;
import com.four.brothers.runtou.exception.code.LoginExceptionCode;
import com.four.brothers.runtou.exception.code.RequestExceptionCode;
import com.four.brothers.runtou.exception.code.SignupExceptionCode;
import com.four.brothers.runtou.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.JDBCException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityExistsException;
import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.sql.SQLException;

import static com.four.brothers.runtou.dto.LoginDto.*;
import static com.four.brothers.runtou.dto.OrdererDto.*;
import static com.four.brothers.runtou.dto.UserDto.*;

@Tag(name = "UserController",description = "유저 관련 API")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserRestController {
  private final UserService userService;

  @Operation(summary = "유저 회원가입")
  @PostMapping("/signup")
  public SignUpResponse signUp(
    @Parameter(name = "회원 정보")
    @Validated @RequestBody SignUpRequest request,
    BindingResult bindingResult, HttpServletRequest requestMsg
  ) {
    boolean result = false;

    if (bindingResult.hasFieldErrors()) {
      throw new BadRequestException(RequestExceptionCode.WRONG_FORMAT, bindingResult.getFieldError().getDefaultMessage());
    }

    try {
      result = userService.signUpAsOrderer(request);
    } catch (DataIntegrityViolationException e) {
      throw new BadRequestException(SignupExceptionCode.ALREADY_EXIST_INFO, "회원가입 정보가 중복됩니다.");
    }

    return new SignUpResponse(result);
  }

  @Operation(summary = "계정 아이디 중복 확인")
  @PostMapping("/signup/accountid")
  public DuplicatedAccountIdResponse checkDuplicatedAccountId(
    @Parameter(name = "확인할 아이디")
    @Validated @RequestBody DuplicatedAccountIdRequest request,
    BindingResult bindingResult
  ) {
    if (bindingResult.hasFieldErrors()) {
      throw new BadRequestException(RequestExceptionCode.WRONG_FORMAT, bindingResult.getFieldError().getDefaultMessage());
    }

    return userService.isDuplicatedAccountId(request);
  }

  @Operation(summary = "닉네임 중복 확인")
  @PostMapping("/signup/nickname")
  public DuplicatedNicknameResponse checkDuplicatedNickname(
    @Parameter(name = "확인할 닉네임")
    @Validated @RequestBody DuplicatedNicknameRequest request, BindingResult bindingResult
  ) {

    if (bindingResult.hasFieldErrors()) {
      throw new BadRequestException(RequestExceptionCode.WRONG_FORMAT, bindingResult.getFieldError().getDefaultMessage());
    }

    return userService.isDuplicatedNickname(request);
  }

  @Operation(summary = "로그인")
  @PostMapping("/signin")
  public LoginResponse login(@Validated @RequestBody LoginRequest request,
                             BindingResult bindingResult,
                             HttpServletRequest httpServletRequest) {

    if (bindingResult.hasFieldErrors()) {
      throw new BadRequestException(RequestExceptionCode.WRONG_FORMAT, bindingResult.getFieldError().getDefaultMessage());
    }

    LoginUser loginUser = null;
    if (request.getRole() == UserRole.ORDERER) {
      loginUser = userService.loginAsOrderer(request);
    } else if (request.getRole() == UserRole.PERFORMER) {
      loginUser = userService.loginAsPerformer(request);
    }

    if (loginUser == null) {
      throw new BadRequestException(LoginExceptionCode.WRONG_VALUE, "로그인 정보가 잘못되었습니다.");
    }

    HttpSession session = httpServletRequest.getSession();
    session.setAttribute("loginUser", loginUser);

    return new LoginResponse(true,
      loginUser.getAccountId(),
      loginUser.getRealName(),
      loginUser.getNickname(),
      loginUser.getPhoneNumber(),
      loginUser.getAccountNumber(),
      loginUser.getRole());
  }

  @Operation(summary = "로그아웃")
  @GetMapping("/logout")
  public boolean logout(HttpServletRequest request) {
    request.getSession().removeAttribute("loginUser");

    return true;
  }

  @Operation(summary = "현재 로그인한 사용자 정보")
  @GetMapping("/test")
  public LoginUser test(
    @Parameter(hidden = true) @SessionAttribute LoginUser loginUser
  ) {
    return loginUser;
  }

  @Operation(summary = "관리자 등록")
  @PostMapping("/admin")
  public SignUpResponse addAdmin(
    @Parameter(name = "회원 정보")
    @Validated @RequestBody SignUpRequest request,
    BindingResult bindingResult, HttpServletRequest requestMsg
  ) {
    boolean result = false;

    if (bindingResult.hasFieldErrors()) {
      throw new BadRequestException(RequestExceptionCode.WRONG_FORMAT, bindingResult.getFieldError().getDefaultMessage());
    }

    try {
      result = userService.signUpAsAdmin(request);
    } catch (DataIntegrityViolationException e) {
      throw new BadRequestException(SignupExceptionCode.ALREADY_EXIST_INFO, "관리자 정보가 중복됩니다.");
    }

    return new SignUpResponse(result);
  }
}
