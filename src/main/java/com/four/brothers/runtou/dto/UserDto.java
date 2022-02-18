package com.four.brothers.runtou.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

public class UserDto {

  @Setter
  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class DuplicatedAccountIdRequest {
    @NotEmpty
    @Length(max = 30)
    private String accountId;
  }

  @Setter
  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class DuplicatedAccountIdResponse {
    private boolean isDuplicatedAccountId;
  }

  @Setter
  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class DuplicatedNicknameRequest {
    @NotEmpty
    @Length(max = 30)
    private String nickname;
  }

  @Setter
  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class DuplicatedNicknameResponse {
    private boolean isDuplicatedNickname;
  }




}
