package com.four.brothers.runtou.controller;

import com.four.brothers.runtou.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.four.brothers.runtou.dto.OrdererDto.*;
import static com.four.brothers.runtou.dto.UserDto.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {
  private final UserService userService;

  @PostMapping("/signup")
  public SignUpResponse signUp(
    @Validated @RequestBody SignUpRequest request,
    BindingResult bindingResult
  ) {
    if (bindingResult.hasFieldErrors()) {
      throw new IllegalArgumentException("요청시 작성된 json의 형식이나 값이 잘못되었습니다.");
    }

    return new SignUpResponse(userService.signUpAsOrderer(request));
  }

  @PostMapping("/signup/accountid")
  public DuplicatedAccountIdResponse checkDuplicatedAccountId(
    @Validated @RequestBody DuplicatedAccountIdRequest request,
    BindingResult bindingResult
  ) {
    if (bindingResult.hasFieldErrors()) {
      throw new IllegalArgumentException("요청시 작성된 json의 형식이나 값이 잘못되었습니다.");
    }

    return userService.isDuplicatedAccountId(request);
  }

  @PostMapping("/signup/nickname")
  public DuplicatedNicknameResponse checkDuplicatedNickname(
    @Validated @RequestBody DuplicatedNicknameRequest request, BindingResult bindingResult
  ) {

    if (bindingResult.hasFieldErrors()) {
      throw new IllegalArgumentException("요청시 작성된 json의 형식이나 값이 잘못되었습니다.");
    }

    return userService.isDuplicatedNickname(request);
  }
}
