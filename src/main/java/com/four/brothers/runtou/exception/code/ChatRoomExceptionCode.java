package com.four.brothers.runtou.exception.code;

/**
 * 채팅방 관련 예외 코드
 */
public enum ChatRoomExceptionCode implements ExceptionCode {
  REQUEST_USER_IS_NOT_PERFORMER("오직 PERFORMER만 채팅을 요청할 수 있습니다."),
  ALREADY_EXIST_CHAT_ROOM("이미 존재하는 채팅방입니다."),
  WRONG_NUMBER_OF_CHAT_ROOM("존재하지 않는 채팅방입니다.");

  private String value;

  ChatRoomExceptionCode(String value) {
    this.value = value;
  }

  @Override
  public String getValue() {
    return this.value;
  }
}
