package com.four.brothers.runtou.service;

import com.four.brothers.runtou.domain.Orderer;
import com.four.brothers.runtou.domain.Performer;
import com.four.brothers.runtou.domain.User;
import com.four.brothers.runtou.dto.LoginDto;
import com.four.brothers.runtou.dto.OrdererDto;
import com.four.brothers.runtou.dto.UserDto;
import com.four.brothers.runtou.dto.UserRole;
import com.four.brothers.runtou.repository.user.OrdererRepository;
import com.four.brothers.runtou.repository.user.PerformerRepository;
import com.four.brothers.runtou.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.four.brothers.runtou.dto.LoginDto.*;
import static com.four.brothers.runtou.dto.UserDto.*;


@RequiredArgsConstructor
@Service
public class UserService {
  private final OrdererRepository ordererRepository;
  private final PerformerRepository performerRepository;
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  /**
   * 회원가입 메서드
   * 회원가입시 모든 사용자는 Orderer로서 가입하게 된다.
   * @param signUpRequest
   */
  @Transactional
  public boolean signUpAsOrderer(OrdererDto.SignUpRequest signUpRequest) {
    String accountId = signUpRequest.getAccountId();
    String nickname = signUpRequest.getNickname();
    String password = passwordEncoder.encode(signUpRequest.getPassword());
    String phoneNumber = signUpRequest.getPhoneNumber();
    String accountNumber = signUpRequest.getAccountNumber();

    ordererRepository.saveOrderer(accountId, password, nickname, phoneNumber, accountNumber);

    return true;
  }

  /**
   * 중복 아이디 확인
   * UserDto.DuplicatedAccountIdResponse의 isDuplicatedAccountId 필드값
   * true: 중복O, false: 중복X
   * @param request
   * @return
   */
  @Transactional
  public DuplicatedAccountIdResponse isDuplicatedAccountId(DuplicatedAccountIdRequest request) {
    Optional<User> userByAccountId = userRepository.findUserByAccountId(request.getAccountId());
    return new DuplicatedAccountIdResponse(userByAccountId.isPresent());
  }

  /**
   * 중복 닉네임 확인
   * UserDto.DuplicatedNicknameResponse의 isDuplicatedNickname 필드값
   * true: 중복O, false: 중복X
   * @param request
   * @return
   */
  @Transactional
  public DuplicatedNicknameResponse isDuplicatedNickname(DuplicatedNicknameRequest request) {
    Optional<User> userByNickname = userRepository.findUserByNickname(request.getNickname());
    return new DuplicatedNicknameResponse(userByNickname.isPresent());
  }

  /**
   * Orderer로 로그인하는 메서드
   * @param request
   * @return
   */
  @Transactional
  public LoginUser loginAsOrderer(LoginRequest request) {
    String accountId = request.getAccountId();
    String rawPassword = request.getRawPassword();
    Optional<Orderer> orderer = ordererRepository.findOrdererByAccountId(accountId);
    String encodedPassword = orderer.get().getPassword();

    if (orderer.isEmpty() || !passwordEncoder.matches(rawPassword, encodedPassword)) {
      throw new IllegalArgumentException("로그인 정보가 잘못되었습니다.");
    }

    return new LoginUser(orderer.get().getAccountId(),
            orderer.get().getNickname(),
            orderer.get().getPhoneNumber(),
            orderer.get().getAccountNumber(),
            UserRole.ORDERER);
  }

  /**
   * Performer로 로그인하는 메서드
   * @param request
   * @return
   */
  @Transactional
  public LoginUser loginAsPerformer(LoginRequest request) {
    String accountId = request.getAccountId();
    String rawPassword = request.getRawPassword();
    Optional<Performer> performer = performerRepository.findPerformerByAccountId(accountId);
    String encodedPassword = performer.get().getPassword();

    if (performer.isEmpty() || !passwordEncoder.matches(rawPassword, encodedPassword)) {
      throw new IllegalArgumentException("로그인 정보가 잘못되었습니다.");
    }

    return new LoginUser(performer.get().getAccountId(),
      performer.get().getNickname(),
      performer.get().getPhoneNumber(),
      performer.get().getAccountNumber(),
      UserRole.PERFORMER);
  }

}
