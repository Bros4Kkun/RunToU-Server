package com.four.brothers.runtou.exception.code;

/**
 * 포인트 관련 예외 코드
 */
public enum PointExceptionCode implements ExceptionCode {
  CANNOT_USE_POINT("포인트를 사용할 수 없음."),
  WRONG_CHARGE_VALUE("충전 금액이 잘못됨."),
  LOW_POINT("보유한 포인트가 충분하지 않음.");

  private String value;

  PointExceptionCode(String value) {
    this.value = value;
  }

  @Override
  public String getValue() {
    return this.value;
  }
}
