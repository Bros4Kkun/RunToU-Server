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
  private Long haveReportedUser;
  private ReportCategory category;
  private String content;

  @Override
  public ReportDto toDtoFromEntity(Report entity) {
    this.id = entity.getId();
    this.reportUserId = entity.getReportUser().getId();
    this.haveReportedUser = entity.getHaveReportedUser().getId();
    this.category = entity.getCategory();
    this.content = entity.getContent();
    return this;
  }
}
