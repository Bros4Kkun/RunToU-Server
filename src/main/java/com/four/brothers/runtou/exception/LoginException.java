package com.four.brothers.runtou.exception;

import com.four.brothers.runtou.exception.code.LoginExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginException extends IllegalArgumentException {
  private LoginExceptionCode code;
  private String errorMsg;

  public LoginException(LoginExceptionCode code) {
    this.code = code;
    this.errorMsg = code.getValue();
  }
}
