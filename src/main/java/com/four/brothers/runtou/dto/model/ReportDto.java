package com.four.brothers.runtou.dto.model;

import com.four.brothers.runtou.domain.ReportCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ReportDto {
  private Long id;
  private Long reportUserId;
  private Long haveReportedUser;
  private ReportCategory category;
  private String content;
}
