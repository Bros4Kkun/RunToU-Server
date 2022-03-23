package com.four.brothers.runtou.controller;

import com.four.brothers.runtou.domain.OrderSheetCategory;
import com.four.brothers.runtou.dto.LoginDto;
import com.four.brothers.runtou.dto.OrderSheetDto;
import com.four.brothers.runtou.exception.BadRequestException;
import com.four.brothers.runtou.exception.code.RequestExceptionCode;
import com.four.brothers.runtou.service.OrderSheetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.four.brothers.runtou.dto.LoginDto.*;
import static com.four.brothers.runtou.dto.OrderSheetDto.*;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/ordersheet")
@RestController
public class OrderSheetRestController {
  private final OrderSheetService orderSheetService;

  @Operation(summary = "새 요청서 등록")
  @PostMapping()
  public OrderSheetSaveResponse saveNewOrderSheet(
    @Validated @RequestBody OrderSheetSaveRequest request,
    BindingResult bindingResult,
    @Parameter(hidden = true) @SessionAttribute LoginUser loginUser
    ) {

    if (bindingResult.hasFieldErrors()) {
      throw new BadRequestException(RequestExceptionCode.WRONG_FORMAT);
    }

    orderSheetService.saveOrderSheet(request, loginUser);

    return new OrderSheetSaveResponse(true);
  }

  @Operation(summary = "모든 요청서 조회")
  @GetMapping("/list/{category}/{nowPage}")
  public AllOrderSheetResponse findAllOrderSheetWithCategory(
    @PathVariable String category,
    @PathVariable int nowPage
  ) {

    AllOrderSheetRequest request;

    if (category.equals("ALL")) {
      request = new AllOrderSheetRequest(null, nowPage, 10);
    } else {
      try {
        request = new AllOrderSheetRequest(OrderSheetCategory.valueOf(category), nowPage, 10);
      } catch (IllegalArgumentException e) {
        throw new BadRequestException(RequestExceptionCode.WRONG_FORMAT);
      }
    }

    AllOrderSheetResponse response = orderSheetService.lookUpAllOrderSheet(request);

    return response;
  }

  @Operation(summary = "특정 요청서 세부 정보 조회")
  @GetMapping("/{orderSheetId}")
  public OrderSheetDetailsResponse findOneOrderSheetWithId(@PathVariable long orderSheetId,
                                      @Parameter(hidden = true) @SessionAttribute LoginUser loginUser) {
    OrderSheetDetailsRequest request = new OrderSheetDetailsRequest(orderSheetId);
    OrderSheetDetailsResponse response = null;

    try {
      response = orderSheetService.lookUpOrderSheetDetails(request, loginUser);
    } catch(IllegalArgumentException e) {
      throw new BadRequestException(RequestExceptionCode.WRONG_FORMAT);
    }

    return response;
  }


}
