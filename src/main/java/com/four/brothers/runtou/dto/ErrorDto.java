package com.four.brothers.runtou.dto;

import com.four.brothers.runtou.exception.code.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

public class ErrorDto {
  @Getter @Setter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Response {
    private HttpStatus httpStatus;
    private ExceptionCode code;
    private String errorMsg;
    private String detail;
  }
}
