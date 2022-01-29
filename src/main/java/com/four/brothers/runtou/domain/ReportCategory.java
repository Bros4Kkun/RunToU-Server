package com.four.brothers.runtou.domain;

public enum ReportCategory {
  PROCESS_TIME("수행시간"), PROCESS_CONTEXT("수행내용"),
  BAD_WORD("욕설"), SEXUAL_HARASSMENT("성희롱");

  private final String value;

  ReportCategory(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
