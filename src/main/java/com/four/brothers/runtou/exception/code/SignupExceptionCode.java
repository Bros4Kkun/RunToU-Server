package com.four.brothers.runtou.exception.code;

/**
 * 회원가입 관련 예외 코드
 */
public enum SignupExceptionCode implements ExceptionCode {
  ALREADY_EXIST_INFO("이미 존재하는 회원정보가 포함되어 있습니다.");

  private String value;

  SignupExceptionCode(String value) {
    this.value = value;
  }

  @Override
  public String getValue() {
    return this.value;
  }
}
