package com.four.brothers.runtou.exception;

import com.four.brothers.runtou.exception.code.ExceptionCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * 403 FORBIDDEN을 반환하는 예외
 * 잘못된 접근에 대한 예외
 */
@Getter
public class CanNotAccessException extends IllegalAccessException implements Exception {
  public static HttpStatus httpStatus = HttpStatus.FORBIDDEN;

  private ExceptionCode code;
  private String errorMsg;
  private String detail;

  /**
   * ExceptionCode 인터페이스를 상속받은 ENUM 타입 처리
   * @param code
   */
  public CanNotAccessException(ExceptionCode code, String detail) {
    if (!code.getClass().isEnum()) {
      throw new IllegalArgumentException("매개변수로 enum 타입이 와야합니다.");
    }
    if (!(code instanceof ExceptionCode)) {
      throw new IllegalArgumentException("오류코드 enum은 ExceptionCode 인터페이스를 상속받아야 합니다.");
    }

    this.code = code;
    this.errorMsg = code.getValue();
    this.detail = detail;
  }
}
