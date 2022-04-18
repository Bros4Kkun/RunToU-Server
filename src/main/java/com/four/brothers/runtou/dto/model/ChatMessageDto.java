package com.four.brothers.runtou.dto.model;

import com.four.brothers.runtou.domain.ChatMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ChatMessageDto implements ModelDto<ChatMessageDto, ChatMessage> {
  private long id;
  private long userId;
  private long chatRoomId;
  private String content;

  @Override
  public ChatMessageDto toDtoFromEntity(ChatMessage entity) {
    this.id = entity.getId();
    this.userId = entity.getUser().getId();
    this.chatRoomId = entity.getChatRoom().getId();
    this.content = entity.getContent();

    return this;
  }

  @Override
  public String getFieldValueByName(String fieldName) {
    switch (fieldName) {
      case "id":
        return String.valueOf(this.id);
      case "userId":
        return String.valueOf(this.userId);
      case "chatRoomId":
        return String.valueOf(this.chatRoomId);
      case "content":
        return String.valueOf(this.content);
    }
    return "";
  }
}
