package com.four.brothers.runtou.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Getter
@NoArgsConstructor
@Entity
public class Admin extends User {

  public Admin(String accountId, String password, String nickname, String phoneNumber, String accountNumber) {
    super(accountId, password, nickname, phoneNumber, accountNumber);
  }

}
