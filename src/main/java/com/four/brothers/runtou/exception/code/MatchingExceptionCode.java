package com.four.brothers.runtou.exception.code;

/**
 * 매칭 관련 예외 코드
 */
public enum MatchingExceptionCode implements ExceptionCode {
  NO_AUTHORITY("접근 권한이 없습니다.");

  private String value;

  MatchingExceptionCode(String value) {
    this.value = value;
  }

  @Override
  public String getValue() {
    return null;
  }
}
