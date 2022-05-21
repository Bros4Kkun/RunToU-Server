package com.four.brothers.runtou.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Min;

public class CostDto {
  @Getter
  @Setter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class DeliveryShoppingRequest {
    @Range(min = 0, max = 5)
    private int distance; //중간지점과 최종지점 사이의 거리 (km)
    @Range(min = 0, max = 90)
    private int minutes; //소요시간 (분)
    @Min(100)
    private int cost; //재료값 등
  }

  @Getter
  @Setter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class CleaningHouseworkRequest {
    @Range(min = 0, max = 150)
    private int minutes; //소요시간 (분)
    @Range(min = 1, max = 5)
    private int level;
  }

  @Getter
  @Setter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class DeliveryInstallationRequest {
    @Range(min = 0, max = 150)
    private int minutes; //소요시간 (분)
    @Range(min = 1, max = 5)
    private int level;
  }

  @Getter
  @Setter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class AccompanyRoleActingRequest {
    @Range(min = 0, max = 180)
    private int minutes; //소요시간 (분)
    @Range(min = 1, max = 5)
    private int level;
  }

  @Getter
  @Setter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class CostRecommendResponse {
    private int recommendCost;
  }
}
