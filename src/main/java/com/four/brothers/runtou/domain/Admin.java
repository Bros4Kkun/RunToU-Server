package com.four.brothers.runtou.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Getter
@NoArgsConstructor
@Entity
public class Admin extends User {

  public Admin(String accountId, String password, String realName, String nickname, String phoneNumber, String accountNumber) {
    super(accountId, password, realName, nickname, phoneNumber, accountNumber);
  }

  public Admin(long id, String accountId, String password, String realName, String nickname, String phoneNumber, String accountNumber) {
    super(id, accountId, password, realName, nickname, phoneNumber, accountNumber);
  }

}
