package com.four.brothers.runtou.dto;

import com.four.brothers.runtou.domain.ChatMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

import static com.four.brothers.runtou.dto.ChatMessageDto.*;
import static com.four.brothers.runtou.dto.OrdererDto.*;
import static com.four.brothers.runtou.dto.PerformerDto.*;

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

  @Getter
  @Setter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class ExistChatRoomResponse {
    private long chatRoomPk;
    private List<ChatMessageResponse> messageList;
    private ChatOrdererInfo ordererInfo;
    private ChatPerformerInfo performerInfo;
    private long ordererSheetPk;
    private boolean isMatched;
  }

  @Getter
  @Setter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class SimpleChatRoomInfo {
    private long chatRoomPk;
    private String latestChatMessage;
    private ChatOrdererInfo ordererInfo;
    private ChatPerformerInfo performerInfo;
    private long ordererSheetPk;
    private boolean isMatched;
  }


}
