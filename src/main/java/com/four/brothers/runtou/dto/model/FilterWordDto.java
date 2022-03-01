package com.four.brothers.runtou.dto.model;

import com.four.brothers.runtou.domain.FilterWord;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FilterWordDto implements ModelDto<FilterWordDto, FilterWord> {
  private Long id;
  private String word;

  @Override
  public FilterWordDto toDtoFromEntity(FilterWord entity) {
    this.id = entity.getId();
    this.word = entity.getWord();
    return this;
  }

  @Override
  public String getFieldValueByName(String fieldName) {
    switch (fieldName) {
      case "id":
        return String.valueOf(this.id);
      case "word":
        return this.word;
    }
    return "";
  }
}
