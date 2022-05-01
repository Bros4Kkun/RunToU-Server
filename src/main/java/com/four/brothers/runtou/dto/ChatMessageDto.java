package com.four.brothers.runtou.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

public class ChatMessageDto {
  @Getter
  @Setter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class ChatMessageResponse {
    private long chatMessagePk;
    private String writerAccountId;
    private String writerNickname;
    private long chatRoomPk;
    private String content;
    private LocalDateTime createdDate;
  }
}
