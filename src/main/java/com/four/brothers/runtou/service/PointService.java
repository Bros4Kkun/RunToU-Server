package com.four.brothers.runtou.service;

import com.four.brothers.runtou.domain.User;
import com.four.brothers.runtou.dto.LoginDto;
import com.four.brothers.runtou.dto.UserDto;
import com.four.brothers.runtou.dto.UserRole;
import com.four.brothers.runtou.exception.CanNotAccessException;
import com.four.brothers.runtou.exception.code.PointExceptionCode;
import com.four.brothers.runtou.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.four.brothers.runtou.dto.LoginDto.*;
import static com.four.brothers.runtou.dto.UserDto.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class PointService {
  private final UserRepository userRepository;

  /**
   * 로그인 사용자의 포인트를 충전하는 메서드
   * @param additionPoint 충전할 포인트
   * @param loginUser 충전할 사용자
   */
  @Transactional
  public PointChargeResponse chargeUserPoint(int additionPoint, LoginUser loginUser) {
    String loginUserAccountId = loginUser.getAccountId();
    User user = userRepository.findUserByAccountId(loginUserAccountId).get();

    user.earnPoint(additionPoint);

    return new PointChargeResponse(true);
  }

  @Transactional
  public PointInfo showPointInfo(LoginUser loginUser) {
    String loginUserAccountId = loginUser.getAccountId();
    User user = userRepository.findUserByAccountId(loginUserAccountId).get();

    return new PointInfo(user.getAccountId(), user.getNickname(), user.getPoint());
  }

}
