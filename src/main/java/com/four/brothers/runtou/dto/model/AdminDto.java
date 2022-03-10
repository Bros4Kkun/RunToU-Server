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
public class AdminDto implements ModelDto<AdminDto, Admin> {
  private Long id;
  private String accountId;
  private String password;
  private String nickname;
  private String accountNumber;
  private String selfIntroduction;

  @Override
  public AdminDto toDtoFromEntity(Admin entity) {
    this.id = entity.getId();
    this.accountId = entity.getAccountId();
    this.password = entity.getPassword();
    this.nickname = entity.getNickname();
    this.accountNumber = entity.getAccountNumber();
    this.selfIntroduction = entity.getSelfIntroduction();

    return this;
  }

  @Override
  public String getFieldValueByName(String fieldName) {
    switch (fieldName) {
      case "id":
        return String.valueOf(this.id);
      case "accountId":
        return this.accountId;
      case "password":
        return this.password;
      case "nickname":
        return this.nickname;
      case "accountNumber":
        return this.accountNumber;
      case "selfIntroduction":
        return this.selfIntroduction;
    }
    return "";
  }
}
