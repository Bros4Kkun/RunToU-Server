package com.four.brothers.runtou.service;

import com.four.brothers.runtou.domain.OrderSheetCategory;
import com.four.brothers.runtou.dto.OrderSheetDto;
import com.four.brothers.runtou.repository.user.OrderSheetRepository;
import com.four.brothers.runtou.repository.user.OrdererRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@Import({OrderSheetService.class, OrderSheetDto.OrderSheetSaveRequest.class})
@DataJpaTest

class OrderSheetServiceTest {

  @Autowired
  private OrderSheetService orderSheetService;

  @DisplayName("주문서 저장")
  @Test
  void saveOrderSheet(){
    String tilte = "장보기";
    String content = "당근사다주세요";
    OrderSheetCategory category = OrderSheetCategory.DELIVERY_AND_SHOPPING;
    String destination = "강서구";
    int cost = 5000;
    LocalDateTime wishedDeadLine = LocalDateTime.now();

    OrderSheetDto.OrderSheetSaveRequest orderSheetSaveRequest =
        new OrderSheetDto.OrderSheetSaveRequest(tilte, content, category, destination, cost, wishedDeadLine);

    String writerAccountId = "test";

    //when-then
    assertDoesNotThrow(
        ()->{
          orderSheetService.saveOrderSheet(orderSheetSaveRequest,writerAccountId);
        }
    );

  }
}