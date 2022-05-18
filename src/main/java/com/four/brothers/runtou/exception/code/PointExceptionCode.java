package com.four.brothers.runtou.exception.code;

/**
 * 포인트 관련 예외 코드
 */
public enum PointExceptionCode implements ExceptionCode {
  PERFORMER_CANNOT_CHARGE_POINT("수행자가 포인트 충전을 시도함.");

  private String value;

  PointExceptionCode(String value) {
    this.value = value;
  }

  @Override
  public String getValue() {
    return this.value;
  }
}
