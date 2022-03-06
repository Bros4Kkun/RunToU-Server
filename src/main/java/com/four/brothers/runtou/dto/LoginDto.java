package com.four.brothers.runtou.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class LoginDto {

  @Getter
  @Setter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class LoginRequest {
    private String accountId;
    private String rawPassword;
    private UserRole role;
  }

  @Getter
  @Setter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class LoginResponse {
    private boolean success;
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
