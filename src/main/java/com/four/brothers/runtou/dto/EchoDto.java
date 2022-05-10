package com.four.brothers.runtou.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EchoDto {

  private String echo;

  public EchoDto(String echo) {
    this.echo = echo;
  }

}