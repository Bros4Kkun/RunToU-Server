package com.four.brothers.runtou.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

public class ChatRoomDto {
  @Getter
  @Setter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class NewChatRoomRequest {
    @NotNull
    private long ordererPk;
    @NotNull
    private long performerPk;
    @NotNull
    private long orderSheetPk;
  }

  @Getter
  @Setter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class NewChatRoomResponse {
    private long ordererPk;
    private long performerPk;
    private long orderSheetPk;
    private long chatRoomPk;
    private boolean isNew;
  }
}
