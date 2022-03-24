package com.four.brothers.runtou.controller;

import com.four.brothers.runtou.service.OrderSheetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "테스트용 컨트롤러")
@RequiredArgsConstructor
@RestController
public class TestRestController {
  private final OrderSheetService orderSheetService;

  @Operation(summary = "Test용 결제 완료 변환", description = "해당 pk값을 갖는 주문서를 결제 완료 상태로 변환시키는 테스트용 API")
  @GetMapping("/api/ordersheet/test/pay/{orderSheetId}")
  public boolean acceptOrderSheetToPay(@PathVariable long orderSheetId) {
    orderSheetService.updateOrderSheetIsPayed(orderSheetId);

    return true;
  }
}
