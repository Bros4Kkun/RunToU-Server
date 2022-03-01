package com.four.brothers.runtou.dto.model;

import com.four.brothers.runtou.domain.Orderer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrdererDto implements ModelDto<OrdererDto, Orderer> {
  private Long id;
  private String accountId;
  private String password;
  private String nickname;
  private String accountNumber;
  private Boolean isDoingJobNow;

  @Override
  public OrdererDto toDtoFromEntity(Orderer entity) {
    this.id = entity.getId();
    this.accountId = entity.getAccountId();
    this.password = entity.getPassword();
    this.nickname = entity.getNickname();
    this.accountNumber = entity.getAccountNumber();
    this.isDoingJobNow = entity.getIsDoingJobNow();
    return this;
  }
}
