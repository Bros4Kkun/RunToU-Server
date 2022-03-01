package com.four.brothers.runtou.dto.model;

import com.four.brothers.runtou.domain.Report;
import com.four.brothers.runtou.domain.ReportCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ReportDto implements ModelDto<ReportDto, Report> {
  private Long id;
  private Long reportUserId;
  private Long haveReportedUserId;
  private ReportCategory category;
  private String content;

  @Override
  public ReportDto toDtoFromEntity(Report entity) {
    this.id = entity.getId();
    this.reportUserId = entity.getReportUser().getId();
    this.haveReportedUserId = entity.getHaveReportedUser().getId();
    this.category = entity.getCategory();
    this.content = entity.getContent();
    return this;
  }

  @Override
  public String getFieldValueByName(String fieldName) {
    switch (fieldName) {
      case "id":
        return String.valueOf(this.id);
      case "reportUserId":
        return String.valueOf(this.reportUserId);
      case "haveReportedUserId":
        return String.valueOf(this.haveReportedUserId);
      case "category":
        return String.valueOf(this.category);
      case "content":
        return this.content;
    }
    return "";
  }
}
