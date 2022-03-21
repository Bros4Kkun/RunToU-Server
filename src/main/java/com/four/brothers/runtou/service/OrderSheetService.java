package com.four.brothers.runtou.service;

import com.four.brothers.runtou.domain.OrderSheet;
import com.four.brothers.runtou.repository.user.OrderSheetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.four.brothers.runtou.dto.LoginDto.*;
import static com.four.brothers.runtou.dto.OrderSheetDto.*;

@RequiredArgsConstructor
@Service
public class OrderSheetService {

  private final OrderSheetRepository orderSheetRepository;



  /**
   * 결제가 완료된 모든 주문서를 조회하는 메서드 (페이징처리 O)
   * @param request
   * @return OrderSheetItem이 담긴 List형 변수
   */
  @Transactional
  public AllOrderSheetResponse lookUpAllOrderSheet(AllOrderSheetRequest request) {
    List<OrderSheet> orderSheetList = orderSheetRepository.findAllOnlyPayed(request.getNowPage(),
                                                              request.getItemSize(), request.getCategory());
    List<OrderSheetItemSample> result = new ArrayList<>();

    //entity -> dto 변환 로직
    for (OrderSheet entity : orderSheetList) {
      result.add(new OrderSheetItemSample(entity));
    }

    return new AllOrderSheetResponse(result);
  }


  @Transactional
  public OrderSheetDetailsResponse lookUpOrderSheetDetails(OrderSheetDetailsRequest request, LoginUser loginUser) {
    Optional<OrderSheet> orderSheet = orderSheetRepository.findById(request.getOrderSheetId());

    if (orderSheet.isEmpty()) {
      throw new IllegalArgumentException("존재하지 않는 주문서 id입니다.");
    }

    //본인이 작성한 글인지 확인하는 조건문
    boolean isWrittenByCurrentUser = false;
    if (orderSheet.get().getOrderer().getAccountId().equals(loginUser.getAccountId())) {
      isWrittenByCurrentUser = true;
    }

    OrderSheetItem item = new OrderSheetItem(orderSheet.get(), isWrittenByCurrentUser);
    return new OrderSheetDetailsResponse(item);
  }


}
