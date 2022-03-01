package com.four.brothers.runtou.dto.model;

import com.four.brothers.runtou.domain.Admin;
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

  public AdminDto(Admin admin) {
    this.id = admin.getId();
    this.accountId = admin.getAccountId();
    this.password = admin.getPassword();
    this.nickname = admin.getNickname();
    this.accountNumber = admin.getAccountNumber();
  }
}
