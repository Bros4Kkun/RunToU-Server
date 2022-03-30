package com.four.brothers.runtou.service;

import com.four.brothers.runtou.domain.OrderSheet;
import com.four.brothers.runtou.domain.Orderer;
import com.four.brothers.runtou.exception.BadRequestException;
import com.four.brothers.runtou.exception.NoAuthorityException;
import com.four.brothers.runtou.exception.code.OrderSheetExceptionCode;
import com.four.brothers.runtou.exception.code.RequestExceptionCode;
import com.four.brothers.runtou.repository.user.OrderSheetRepository;
import com.four.brothers.runtou.repository.user.OrdererRepository;
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
  private final OrdererRepository ordererRepository;

  /**
   * 주문서를 저장하는 메서드
   * @param request 저장할 주문서 내용
   * @param writerAccountId 작성자 계정 Id
   */
  @Transactional
  public void saveOrderSheet(OrderSheetSaveRequest request, String writerAccountId) {
    Optional<Orderer> orderer = ordererRepository.findOrdererByAccountId(writerAccountId);
    orderSheetRepository.saveOrderSheet(
      orderer.get(),
      request.getTitle(),
      request.getContent(),
      request.getCategory(),
      request.getDestination(),
      request.getCost(),
      false,
      request.getWishedDeadline()
    );
  }

  /**
   * 결제가 완료된 모든 주문서를 조회하는 메서드 (페이징처리 O)
   * @param request
   * @return OrderSheetItem이 담긴 List형 변수
   */
  @Transactional
  public AllOrderSheetResponse lookUpAllOrderSheet(AllOrderSheetRequest request) throws IllegalArgumentException {
    List<OrderSheet> orderSheetList = orderSheetRepository.findAllOnlyPayed(request.getNowPage(),
                                                              request.getItemSize(), request.getCategory());
    List<OrderSheetItemSample> result = new ArrayList<>();

    //entity -> dto 변환 로직
    for (OrderSheet entity : orderSheetList) {
      result.add(new OrderSheetItemSample(entity));
    }

    return new AllOrderSheetResponse(result);
  }

  /**
   * 단일 주문서의 상세정보를 반환하는 메서드
   * @param request
   * @param loginUser
   * @return
   */
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

  /**
   * 해당 pk값을 갖는 주문서를 결제완료 상태로 전환시키는 메서드
   * @param id
   */
  @Transactional
  public void updateOrderSheetIsPayed(long id) {
    Optional<OrderSheet> foundOrderSheet = orderSheetRepository.findById(id);

    if (foundOrderSheet.isEmpty()) {
      throw new IllegalArgumentException("존재하지 않는 주문서 id입니다.");
    }

    foundOrderSheet.get().payComplete();
  }

  /**
   * 해당 주문서의 내용을 수정하는 메서드
   * @param orderSheetId 수정할 주문서의 pk값
   * @param request 수정할 내용
   */
  @Transactional
  public void updateOrderSheet(long orderSheetId, OrderSheetSaveRequest request, LoginUser loginUser) throws NoAuthorityException {
    Optional<OrderSheet> op = orderSheetRepository.findById(orderSheetId);

    if (op.isEmpty()) {
      throw new IllegalArgumentException("존재하지 않는 주문서 id입니다.");
    }


    OrderSheet orderSheet = op.get();

    //로그인한 유저가 작성한 글인지 확인
    if (!loginUser.getAccountId().equals(orderSheet.getOrderer().getAccountId())) {
      throw new NoAuthorityException(RequestExceptionCode.NO_AUTHORITY, "현재 로그인한 사용자가 작성하지 않은 게시글입니다."); //본인이 작성하지 않은 글을 수정하려고 하면, 예외 발생
    }

    //이미 수행 중인 심부름인지 확인
    if (orderSheet.getIsPayed()) {
      throw new BadRequestException(OrderSheetExceptionCode.ALREADY_MATCHED, "이미 결제가 완료된 요청서입니다.");
    }

    orderSheet.update(request.getTitle(), request.getContent(), request.getCategory(), request.getDestination(), request.getCost(), request.getWishedDeadline());
  }
}
