package com.four.brothers.runtou.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class JwtDto {
  @Getter
  @Setter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class JwtDtoResponse {
    private String token;
  }
}
