package com.four.brothers.runtou.domain;

public enum OrderSheetCategory {
  DELIVERY_AND_SHOPPING("배달 및 장보기"),
  CLEANING_AND_HOUSEWORK("청소 및 장보기"), DELIVERY_AND_INSTALLATION("설치 조립 운반"),
  ACCOMPANY("동행 돌봄"), ANTI_BUG("벌레 및 쥐잡기"),
  ROLE_ACTING("역할 대행"), ETC("기타");

  private String value;


  OrderSheetCategory(String v) {
    value = v;
  }

  String getValue() {
    return value;
  }
}
