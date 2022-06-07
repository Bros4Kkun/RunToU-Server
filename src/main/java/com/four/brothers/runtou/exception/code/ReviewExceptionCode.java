package com.four.brothers.runtou.exception.code;

public enum ReviewExceptionCode implements ExceptionCode {

  NO_EXIST_REVIEW("리뷰가 존재하지 않음");

  private String value;

  ReviewExceptionCode(String value) {
    this.value = value;
  }

  @Override
  public String getValue() {
    return this.value;
  }
}
