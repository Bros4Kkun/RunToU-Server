package com.four.brothers.runtou.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.four.brothers.runtou.dto.CostDto.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class CostRecommendService {

  private final static int minWage = 9140;

  /**
   * 배달 및 장보기 카테고리의 추천 가격 구하기
   * @param request
   * @return
   */
  public CostRecommendResponse getGoodCostForDeliveryShopping(DeliveryShoppingRequest request) {
    int costForBuying = request.getCost();
    int distance = request.getDistance();
    int spendMinutes = request.getMinutes();
    float q1 = 0; //거리 가중치
    float q2 = 0; //시간 가중치

    if (distance <= 1) {
      q1 = 0.3f;
    } else if (distance <= 3) {
      q1 = 0.6f;
    } else if (distance <= 5) {
      q1 = 0.9f;
    }

    if (spendMinutes <= 30) {
      q2 = 0.7f;
    } else if (spendMinutes <= 60) {
      q2 = 0.4f;
    } else if (spendMinutes <= 90) {
      q2 = 0.1f;
    }

    int recommendCost = costForBuying + (int) (costForBuying * q1 * q2);

    return new CostRecommendResponse(recommendCost);
  }

  /**
   * 청소 및 집안일 카테고리의 추천 가격 구하기
   * @param request
   * @return
   */
  public CostRecommendResponse getGoodCostForCleaningHousework(CleaningHouseworkRequest request) {
    int level = request.getLevel();
    int spendMinutes = request.getMinutes();
    float q1 = 0; //거리 가중치
    float q2 = 0; //시간 가중치

    if (level == 1) {
      q1 = 0.1f;
    } else if (level == 2) {
      q1 = 0.3f;
    } else if (level == 3) {
      q1 = 0.5f;
    } else if (level == 4) {
      q1 = 0.7f;
    } else if (level == 5) {
      q1 = 0.9f;
    }

    if (spendMinutes <= 30) {
      q2 = 0.1f;
    } else if (spendMinutes <= 60) {
      q2 = 0.3f;
    } else if (spendMinutes <= 90) {
      q2 = 0.5f;
    } else if (spendMinutes <= 120) {
      q2 = 0.7f;
    } else if (spendMinutes <= 150) {
      q2 = 0.9f;
    }

    int recommendCost = (int) (minWage + (minWage * q1 * q2));

    return new CostRecommendResponse(recommendCost);
  }

  /**
   * 설치 조립 및 운반 카테고리의 추천 가격 구하기
   * @param request
   * @return
   */
  public CostRecommendResponse getGoodCostForDeliveryInstallation(DeliveryInstallationRequest request) {
    int level = request.getLevel();
    int spendMinutes = request.getMinutes();
    float q1 = 0; //거리 가중치
    float q2 = 0; //시간 가중치

    if (level == 1) {
      q1 = 0.1f;
    } else if (level == 2) {
      q1 = 0.3f;
    } else if (level == 3) {
      q1 = 0.5f;
    } else if (level == 4) {
      q1 = 0.7f;
    } else if (level == 5) {
      q1 = 0.9f;
    }

    if (spendMinutes <= 30) {
      q2 = 0.1f;
    } else if (spendMinutes <= 60) {
      q2 = 0.3f;
    } else if (spendMinutes <= 90) {
      q2 = 0.5f;
    } else if (spendMinutes <= 120) {
      q2 = 0.7f;
    } else if (spendMinutes <= 150) {
      q2 = 0.9f;
    }

    int recommendCost = (int) (minWage + (minWage * q1 * q2));

    return new CostRecommendResponse(recommendCost);
  }

  /**
   * 동행 및 돌봄, 역할 대행 카테고리의 추천 가격 구하기
   * @param request
   * @return
   */
  public CostRecommendResponse getGoodCostForAccompanyRoleActing(AccompanyRoleActingRequest request) {
    int level = request.getLevel();
    int spendMinutes = request.getMinutes();
    float q1 = 0; //거리 가중치
    float q2 = 0; //시간 가중치

    if (level == 1) {
      q1 = 0.1f;
    } else if (level == 2) {
      q1 = 0.3f;
    } else if (level == 3) {
      q1 = 0.5f;
    } else if (level == 4) {
      q1 = 0.7f;
    } else if (level == 5) {
      q1 = 0.9f;
    }

    if (spendMinutes <= 60) {
      q2 = 0.3f;
    } else if (spendMinutes <= 120) {
      q2 = 0.6f;
    } else if (spendMinutes <= 180) {
      q2 = 0.9f;
    }

    int recommendCost = (int) (minWage + (minWage * q1 * q2));

    return new CostRecommendResponse(recommendCost);
  }
}
