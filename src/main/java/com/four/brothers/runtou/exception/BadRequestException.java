package com.four.brothers.runtou.exception;

import com.four.brothers.runtou.exception.code.ExceptionCode;
import com.four.brothers.runtou.exception.code.RequestExceptionCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor
public class BadRequestException extends IllegalArgumentException {
  public static HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

  private ExceptionCode code;
  private String errorMsg;

  public BadRequestException(ExceptionCode code) {
    this.code = code;
    this.errorMsg = code.getValue();
  }
}
