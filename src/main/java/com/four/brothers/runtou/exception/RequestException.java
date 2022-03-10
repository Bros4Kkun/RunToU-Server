package com.four.brothers.runtou.exception;

import com.four.brothers.runtou.exception.code.RequestExceptionCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RequestException extends IllegalArgumentException {
  private RequestExceptionCode code;
  private String errorMsg;

  public RequestException(RequestExceptionCode code) {
    this.code = code;
    this.errorMsg = code.getValue();
  }
}
