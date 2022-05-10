package com.four.brothers.runtou.dto.model;

import com.four.brothers.runtou.domain.ChatRoom;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ChatRoomDto implements ModelDto<ChatRoomDto, ChatRoom> {
  private long id;
  private long ordererId;
  private long performerId;
  private long orderSheetId;

  @Override
  public ChatRoomDto toDtoFromEntity(ChatRoom entity) {
    this.id = entity.getId();
    this.ordererId = entity.getOrderer().getId();
    this.performerId = entity.getPerformer().getId();
    this.orderSheetId = entity.getOrderSheet().getId();
    return this;
  }

  @Override
  public String getFieldValueByName(String fieldName) {
    switch (fieldName) {
      case "id":
        return String.valueOf(this.id);
      case "ordererId":
        return String.valueOf(this.ordererId);
      case "performerId":
        return String.valueOf(this.performerId);
      case "orderSheetId":
        return String.valueOf(this.orderSheetId);
    }
    return "";
  }
}
