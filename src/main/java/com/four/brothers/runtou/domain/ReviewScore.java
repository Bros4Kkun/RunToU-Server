package com.four.brothers.runtou.domain;

public enum ReviewScore {
  ONE(1), TWO(2), THREE(3), FOUR(4), FIVE(5),
  SIX(6), SEVEN(7), EIGHT(8), NINE(9), TEN(10);

  private int value;

  ReviewScore(int v) {
    this.value = v;
  }

  public int getValue() {
    return this.value;
  }
}
