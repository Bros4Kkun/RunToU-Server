package com.four.brothers.runtou.exception.code;

/**
 * 매칭 요청 관련 예외 코드
 */
public enum MatchRequestExceptionCode implements ExceptionCode {
  ALREADY_REQUESTED("중복된 매칭 요청"),
  WRONG_USER("잘못된 사용자의 요청"),
  ALREADY_DOING_JOB("수행자가 이미 심부름 수행 중"),
  WRONG_MATCH_REQUEST("존재하지 않는 매칭요청"),
  ALREADY_ACCEPTED("이미 수락된 매칭요청"),
  ALREADY_ACCEPTED_WITH_OTHER_USER("다른 사용자가 이미 수락한 요청서");

  private String value;

  MatchRequestExceptionCode(String value) {
    this.value = value;
  }

  @Override
  public String getValue() {
    return this.value;
  }
}
