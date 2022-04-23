package com.four.brothers.runtou.dto.model;

import com.four.brothers.runtou.domain.MatchRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MatchRequestDto implements ModelDto<MatchRequestDto, MatchRequest> {
  private Long id;
  private Long orderSheetId;
  private Long performerId;

  @Override
  public MatchRequestDto toDtoFromEntity(MatchRequest entity) {
    this.id = entity.getId();
    this.orderSheetId = entity.getOrderSheet().getId();
    this.performerId = entity.getPerformer().getId();
    return this;
  }

  @Override
  public String getFieldValueByName(String fieldName) {
    switch (fieldName) {
      case "id":
        return String.valueOf(this.id);
      case "orderSheetId":
        return String.valueOf(this.orderSheetId);
      case "performerId":
        return String.valueOf(this.performerId);
    }
    return "";
  }
}
