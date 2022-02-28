package com.four.brothers.runtou.dto.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AdminDto {
  private Long id;
  private String accountId;
  private String password;
  private String nickname;
  private String accountNumber;
  private Boolean isDoingJobNow;
}
