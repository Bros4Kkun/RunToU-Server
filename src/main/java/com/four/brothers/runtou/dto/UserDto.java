package com.four.brothers.runtou.dto;

import com.four.brothers.runtou.domain.User;
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

  @Setter
  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class ProfileInfo {
    private String accountId;
    private String nickname;
    private String realName;
    private String phoneNumber;
    private String selfIntroduction;
    private int point;

    public ProfileInfo(User entity) {
      this.accountId = entity.getAccountId();
      this.nickname = entity.getNickname();
      this.realName = entity.getRealName();
      this.phoneNumber = entity.getPhoneNumber();
      this.selfIntroduction = entity.getSelfIntroduction();
      this.point = entity.getPoint();
    }
  }

}
