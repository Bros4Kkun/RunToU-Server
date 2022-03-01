package com.four.brothers.runtou.dto.model;

import com.four.brothers.runtou.domain.Performer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PerformerDto implements ModelDto<PerformerDto, Performer> {
  private Long id;
  private String accountId;
  private String password;
  private String nickname;
  private String accountNumber;
  private Boolean isDoingJobNow;
  private LocalDateTime becamePerformerDateTime;

  @Override
  public PerformerDto toDtoFromEntity(Performer entity) {
    this.id = entity.getId();
    this.accountId = entity.getAccountId();
    this.password = entity.getPassword();
    this.nickname = entity.getNickname();
    this.accountNumber = entity.getAccountNumber();
    this.isDoingJobNow = entity.getIsDoingJobNow();
    this.becamePerformerDateTime = entity.getBecamePerformerDateTime();
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
      case "isDoingJobNow":
        return String.valueOf(this.isDoingJobNow);
      case "becamePerformerDateTime":
        return String.valueOf(this.becamePerformerDateTime);
    }
    return "";
  }
}
