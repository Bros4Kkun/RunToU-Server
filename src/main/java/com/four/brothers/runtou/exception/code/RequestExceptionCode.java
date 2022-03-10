package com.four.brothers.runtou.exception.code;

public enum RequestExceptionCode implements ExceptionCode {
  WRONG_FORMAT("요청시 작성된 json의 형식이나 값이 잘못되었습니다.");

  private String value;

  RequestExceptionCode(String value) {
    this.value = value;
  }

  @Override
  public String getValue() {
    return this.value;
  }
}