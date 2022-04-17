package com.four.brothers.runtou.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class SocketDto {
  @Getter
  @Setter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class NewChatRoomAlertResponse {
    private String msg;
    private long chatRoomPk;
  }
}
