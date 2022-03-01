package com.four.brothers.runtou.controller;

import com.four.brothers.runtou.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import java.util.Enumeration;

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
    if (bindingResult.hasFieldErrors()) {
      throw new IllegalArgumentException("요청시 작성된 json의 형식이나 값이 잘못되었습니다.");
    }

    return new SignUpResponse(userService.signUpAsOrderer(request));
  }

  @Operation(summary = "계정 아이디 중복 확인")
  @PostMapping("/signup/accountid")
  public DuplicatedAccountIdResponse checkDuplicatedAccountId(
    @Parameter(name = "확인할 아이디")
    @Validated @RequestBody DuplicatedAccountIdRequest request,
    BindingResult bindingResult
  ) {
    if (bindingResult.hasFieldErrors()) {
      throw new IllegalArgumentException("요청시 작성된 json의 형식이나 값이 잘못되었습니다.");
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
      throw new IllegalArgumentException("요청시 작성된 json의 형식이나 값이 잘못되었습니다.");
    }

    return userService.isDuplicatedNickname(request);
  }
}
