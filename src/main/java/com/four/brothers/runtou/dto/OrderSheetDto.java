package com.four.brothers.runtou.dto;

import com.four.brothers.runtou.domain.OrderSheet;
import com.four.brothers.runtou.domain.OrderSheetCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;
import java.util.List;

public class OrderSheetDto {

  @Getter
  @Setter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class AllOrderSheetRequest {
    @Schema(description = "조회할 카테고리\n**만약 `null`값이라면, 모든 카테고리 검색**")
    private OrderSheetCategory category = null;

    @Schema(description = "현재 페이지")
    @Positive
    private int nowPage;

    @Schema(description = "페이지당 아이템 수")
    @Positive
    private int itemSize;
  }

  @Getter
  @Setter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class AllOrderSheetResponse {
    private List<OrderSheetItemSample> orderSheetItemSampleList;
  }

  @Getter
  @Setter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class OrderSheetDetailsRequest {
    @NotNull
    private long orderSheetId;
  }

  @Getter
  @Setter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class OrderSheetDetailsResponse {
    private OrderSheetItem orderSheetItem;
  }

  @Getter
  @Setter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class OrderSheetSaveRequest {
    @NotEmpty
    @Schema(description = "요청서 제목")
    private String title;

    @NotEmpty
    @Schema(description = "요청서 내용")
    private String content;

    @NotNull
    @Schema(description = "요청서 카테고리\n" +
      "- DELIVERY_AND_SHOPPING: 배달 및 장보기\n" +
      "- CLEANING_AND_HOUSEWORK: 청소 및 집안일\n" +
      "- DELIVERY_AND_INSTALLATION: 설치 조립 운반\n" +
      "- ACCOMPANY: 동행 및 돌봄\n" +
      "- ANTI_BUG: 벌레 및 쥐잡기\n" +
      "- ROLE_ACTING: 역할 대행\n" +
      "- ETC: 기타",
      enumAsRef = true
    )
    private OrderSheetCategory category;

    @Schema(description = "목적지")
    @NotEmpty
    private String destination;

    @Schema(description = "1 이상, 100,000 이하의 의뢰금액")
    @Range(min = 1, max = 100000)
    private int cost;

    @Schema(description = "원하는 수행 완료 일자")
    @NotNull
    private LocalDateTime wishedDeadline;
  }

  @Getter
  @Setter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class OrderSheetSaveResponse {
    boolean saveResult;
  }

  /**
   * 주문서 요약 DTO 클래스.
   * 주문서 목록의 요소로 사용된다.
   */
  @Getter
  @Setter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class OrderSheetItemSample {
    private long orderSheetId;
    private String ordererNickname;
    private String title;
    private String contentSample;
    private OrderSheetCategory category;
    private String destination;
    private int cost;

    public OrderSheetItemSample(OrderSheet entity) {
      this.orderSheetId = entity.getId();
      this.ordererNickname = entity.getOrderer().getNickname();
      this.title = entity.getTitle();

      //내용이 20글자를 넘지 않는 경우에 대한, 예외처리
      try {
        //미리보기이므로, 최대 20글자까지만 제공한다.
        this.contentSample = entity.getContent().substring(0, 20);
      } catch (IndexOutOfBoundsException e) {
        this.contentSample = entity.getContent();
      }

      this.category = entity.getCategory();
      this.destination = entity.getDestination();
      this.cost = entity.getCost();
    }
  }

  /**
   * 주문서 상세정보 DTO 클래스.
   * 주문서 상세 보기에서 사용된다.
   */
  @Getter
  @Setter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class OrderSheetItem {
    private long orderSheetId;
    private long matchingId;
    private String ordererAccountId;
    private String ordererNickname;
    private String title;
    private String content;
    private OrderSheetCategory category;
    private String destination;
    private int cost;
    private LocalDateTime wishedDeadLine;
    private boolean isWrittenByCurrentUser;

    public OrderSheetItem(OrderSheet entity, boolean isWrittenByCurrentUser) {
      this.orderSheetId = entity.getId();
      if (entity.getMatching() != null) {
        this.matchingId = entity.getMatching().getId();
      }
      this.ordererAccountId = entity.getOrderer().getAccountId();
      this.ordererNickname = entity.getOrderer().getNickname();
      this.title = entity.getTitle();
      this.content = entity.getContent();
      this.category = entity.getCategory();
      this.destination = entity.getDestination();
      this.cost = entity.getCost();
      this.wishedDeadLine = entity.getWishedDeadline();
      this.isWrittenByCurrentUser = isWrittenByCurrentUser;
    }
  }


}
