package com.four.brothers.runtou.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class LoginDto {

  @Getter
  @Setter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class LoginRequest {
    @NotEmpty
    private String accountId;
    @NotEmpty
    private String rawPassword;
    @NotNull
    private UserRole role;
  }

  @Getter
  @Setter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class AdminLoginRequest {
    @NotEmpty
    private String accountId;
    @NotEmpty
    private String rawPassword;
  }

  @Getter
  @Setter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class LoginResponse {
    private boolean success;
    private String accountId;
    private String nickname;
    private String phoneNumber;
    private String accountNumber;
    private UserRole role;
  }

  @Getter
  @Setter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class LoginUser {
    private String accountId;
    private String nickname;
    private String phoneNumber;
    private String accountNumber;
    private UserRole role;
  }

}
