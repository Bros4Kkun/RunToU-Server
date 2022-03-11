package com.four.brothers.runtou.exception.code;

/**
 * 로그인 관련 예외 코드
 */
public enum LoginExceptionCode implements ExceptionCode {
  WRONG_VALUE("로그인 정보가 잘못되었습니다.");

  private String value;

  LoginExceptionCode(String value) {
    this.value = value;
  }


  @Override
  public String getValue() {
    return this.value;
  }
}
