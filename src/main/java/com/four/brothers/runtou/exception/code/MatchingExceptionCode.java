package com.four.brothers.runtou.exception.code;

/**
 * 매칭 관련 예외 코드
 */
public enum MatchingExceptionCode implements ExceptionCode {
  NO_AUTHORITY("접근 권한이 없습니다."),
  WRONG_ID("존재하지 않는 PK 값"),
  ALREADY_REQUESTED_COMPLETION("수행 완료 요청이 중복됨."),
  ORDERER_REQUEST_COMPLETION("수행자만 심부름 완료 요청 가능");

  private String value;

  MatchingExceptionCode(String value) {
    this.value = value;
  }

  @Override
  public String getValue() {
    return this.value;
  }
}
