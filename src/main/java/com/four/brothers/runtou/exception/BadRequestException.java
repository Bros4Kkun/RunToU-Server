package com.four.brothers.runtou.exception;

import com.four.brothers.runtou.exception.code.ExceptionCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * "404 Bad Request"를 반환해야 하는 예외
 */
@Getter
@NoArgsConstructor
public class BadRequestException extends IllegalArgumentException {
  public static HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

  private ExceptionCode code;
  private String errorMsg;

  /**
   * ExceptionCode 인터페이스를 상속받은 ENUM 타입 처리
   * @param code
   */
  public BadRequestException(ExceptionCode code) {
    if (!code.getClass().isEnum()) {
      throw new IllegalArgumentException("매개변수로 enum 타입이 와야합니다.");
    }
    if (!(code instanceof ExceptionCode)) {
      throw new IllegalArgumentException("오류코드 enum은 ExceptionCode 인터페이스를 상속받아야 합니다.");
    }

    this.code = code;
    this.errorMsg = code.getValue();
  }
}
