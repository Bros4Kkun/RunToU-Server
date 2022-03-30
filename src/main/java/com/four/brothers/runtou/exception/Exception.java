package com.four.brothers.runtou.exception;

import com.four.brothers.runtou.exception.code.ExceptionCode;
import org.springframework.http.HttpStatus;

/**
 * 사용자 정의 예외는 모두 본 인터페이스를 상속받아야 한다.
 */
public interface Exception {
  HttpStatus httpStatus = null;
  ExceptionCode code = null;
  String errorMsg = "포괄적인 에러 메시지";
  String detail = "세부 메시지";

  ExceptionCode getCode();
  String getErrorMsg();
  String getDetail();
}
