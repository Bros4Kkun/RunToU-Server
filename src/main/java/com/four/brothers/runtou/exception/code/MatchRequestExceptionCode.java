package com.four.brothers.runtou.exception.code;

/**
 * 매칭 요청 관련 예외 코드
 */
public enum MatchRequestExceptionCode implements ExceptionCode {
  ALREADY_REQUESTED("중복된 매칭 요청"),
  WRONG_USER_ROLE("잘못된 사용자의 요청"),
  ALREADY_DOING_JOB("수행자가 이미 심부름 수행 중");

  private String value;

  MatchRequestExceptionCode(String value) {
    this.value = value;
  }

  @Override
  public String getValue() {
    return this.value;
  }
}
