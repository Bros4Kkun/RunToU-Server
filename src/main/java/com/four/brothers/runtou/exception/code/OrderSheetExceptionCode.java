package com.four.brothers.runtou.exception.code;

/**
 * 요청서 관련 예외
 */
public enum OrderSheetExceptionCode implements ExceptionCode {
  
  ALREADY_MATCH_REQUESTED("이미 매칭이 요청된 심부름 요청서입니다."),
  ALREADY_PAYED("이미 결제된 요청서입니다.");
  
  private String value;
  
  OrderSheetExceptionCode(String value) {
    this.value = value;
  }
  
  @Override
  public String getValue() {
    return this.value;
  }
}
