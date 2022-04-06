package com.four.brothers.runtou.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

public class OrdererDto {

  @Getter
  @Setter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class SignUpRequest {
    @NotEmpty
    @Length(max = 30)
    private String accountId;
    @NotEmpty
    @Length(max = 20)
    private String realName;
    @NotEmpty
    @Length(max = 30)
    private String nickname;
    @NotEmpty
    @Length(max = 50)
    private String password;
    @NotEmpty
    @Length(max = 11)
    private String phoneNumber;
    @NotEmpty
    private String accountNumber;
    //프로필 사진 관련 내용은 추후에 작성
  }

  @Getter
  @Setter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class SignUpResponse {
    private boolean isSuccess;
    //프로필 사진 관련 내용은 추후에 작성
  }
}
