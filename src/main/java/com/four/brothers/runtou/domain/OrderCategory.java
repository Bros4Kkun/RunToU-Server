package com.four.brothers.runtou.domain;

public enum OrderCategory {
    ALL("전체"),
    SHOPPING("장보기 및 배달"),
    CLEANING("청소 및 집안일"),
    SETTING("설치 조립 운반"),
    CARE("돌봄"),
    INSECT_REPELLENT("방충"),
    ROLL_ACTING("역할대행");

    private final String value;

    OrderCategory(String value) {
        this.value = value;
    }
}
