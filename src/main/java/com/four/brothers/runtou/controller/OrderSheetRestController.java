package com.four.brothers.runtou.controller;

import com.four.brothers.runtou.dto.LoginDto;
import com.four.brothers.runtou.dto.OrderSheetDto;
import com.four.brothers.runtou.exception.BadRequestException;
import com.four.brothers.runtou.exception.code.RequestExceptionCode;
import com.four.brothers.runtou.service.OrderSheetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.four.brothers.runtou.dto.LoginDto.*;
import static com.four.brothers.runtou.dto.OrderSheetDto.*;

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


}
