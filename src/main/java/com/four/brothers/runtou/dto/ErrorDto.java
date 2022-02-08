package com.four.brothers.runtou.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class ErrorDto {
  @Getter @Setter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Response {
    private String errorMsg;
  }
}
