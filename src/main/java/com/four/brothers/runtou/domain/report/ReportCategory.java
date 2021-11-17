package com.four.brothers.runtou.domain.report;

public enum ReportCategory {
    WRONG_TASK("잘못된 작업 수행"),
    CRIME("범죄"),
    BAD_WORD("욕설 및 성희롱"),
    BAD_REQUEST("부적절한 요청사항"),
    ETC("기타");

    private final String value;

    ReportCategory(String value) {
        this.value = value;
    }
}
