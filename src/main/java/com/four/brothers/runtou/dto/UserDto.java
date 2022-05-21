package com.four.brothers.runtou.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
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

  @Setter
  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class SelfIntroductionUpdateRequest {
    @NotEmpty
    private String selfIntroduction;
  }

  @Setter
  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class PointChargeRequest {
    @Range(min = 1000, max = 100000)
    private int earnPoint;
  }

  @Setter
  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class PointChargeResponse {
    private boolean isSucceed;
  }

  @Setter
  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class PointInfo {
    private String accountId;
    private String nickname;
    private int point;
  }

}
