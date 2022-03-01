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
  private Long matchingId;
  private Long performerId;

  @Override
  public MatchRequestDto toDtoFromEntity(MatchRequest entity) {
    this.id = entity.getId();
    this.matchingId = entity.getMatching().getId();
    this.performerId = entity.getPerformer().getId();
    return this;
  }
}