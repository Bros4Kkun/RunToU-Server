package com.four.brothers.runtou.service;

import com.four.brothers.runtou.domain.OrderSheet;
import com.four.brothers.runtou.repository.user.OrderSheetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.four.brothers.runtou.dto.LoginDto.*;
import static com.four.brothers.runtou.dto.OrderSheetDto.*;

@RequiredArgsConstructor
@Service
public class OrderSheetService {

  private final OrderSheetRepository orderSheetRepository;

  /**
   * 모든 주문서를 조회하는 메서드 (페이징처리 O)
   * @param request
   * @param loginUser
   * @return OrderSheetItem이 담긴 List형 변수
   */
  @Transactional
  public AllOrderSheetResponse lookUpAllOrderSheet(AllOrderSheetRequest request, LoginUser loginUser) {
    List<OrderSheet> orderSheetList = orderSheetRepository.findAll(request.getNowPage(), request.getItemSize());
    List<OrderSheetItem> result = new ArrayList<>();

    //entity -> dto 변환 로직
    for (OrderSheet entity : orderSheetList) {
      boolean isWrittenByCurrentUser = false;
      if (entity.getOrderer().getAccountId() == loginUser.getAccountId()) { //만약 현재 로그인한 유저가 작성한 글이라면
        isWrittenByCurrentUser = true;
      }
      result.add(new OrderSheetItem(entity, isWrittenByCurrentUser));
    }

    return new AllOrderSheetResponse(result);
  }


}
