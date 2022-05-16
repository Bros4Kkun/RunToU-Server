package com.four.brothers.runtou.exception.code;

/**
 * 매칭 관련 예외 코드
 */
public enum MatchingExceptionCode implements ExceptionCode {
  NO_AUTHORITY("접근 권한이 없습니다."),
  WRONG_ID("존재하지 않는 PK 값"),
  ALREADY_REQUESTED_COMPLETION("수행 완료 요청이 중복됨."),
  ORDERER_REQUEST_COMPLETION("수행자만 심부름 완료 요청 가능"),
  PERFORMER_CANNOT_ACCEPT_COMPLETION("요청자만 심부름 완료 요청에 대해 승인 가능"),
  COMPLETE_BEFORE_REQUEST("업무 완료 요청을 하기 전, 완료 처리 불가능"),
  ALREADY_ACCEPTED_COMPLETION("수행 완료 승인이 중복됨.");

  private String value;

  MatchingExceptionCode(String value) {
    this.value = value;
  }

  @Override
  public String getValue() {
    return this.value;
  }
}
