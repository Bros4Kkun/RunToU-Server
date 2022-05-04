package com.four.brothers.runtou.service;

import com.four.brothers.runtou.domain.Orderer;
import com.four.brothers.runtou.domain.Performer;
import com.four.brothers.runtou.domain.User;
import com.four.brothers.runtou.dto.OrdererDto;
import com.four.brothers.runtou.dto.PerformerDto;
import com.four.brothers.runtou.dto.UserRole;
import com.four.brothers.runtou.repository.user.AdminRepository;
import com.four.brothers.runtou.repository.user.OrdererRepository;
import com.four.brothers.runtou.repository.user.PerformerRepository;
import com.four.brothers.runtou.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.four.brothers.runtou.dto.LoginDto.*;
import static com.four.brothers.runtou.dto.PerformerDto.*;
import static com.four.brothers.runtou.dto.UserDto.*;


@RequiredArgsConstructor
@Service
public class UserService {
  private final OrdererRepository ordererRepository;
  private final PerformerRepository performerRepository;
  private final AdminRepository adminRepository;
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  /**
   * 심부름 요청자 회원가입 메서드
   * 회원가입시 모든 사용자는 Orderer로서 가입하게 된다.
   * @param signUpAsOrdererRequest
   */
  @Transactional
  public boolean signUpAsOrderer(OrdererDto.SignUpAsOrdererRequest signUpAsOrdererRequest) {
    String accountId = signUpAsOrdererRequest.getAccountId();
    String realName = signUpAsOrdererRequest.getRealName();
    String nickname = signUpAsOrdererRequest.getNickname();
    String password = passwordEncoder.encode(signUpAsOrdererRequest.getPassword());
    String phoneNumber = signUpAsOrdererRequest.getPhoneNumber();
    String accountNumber = signUpAsOrdererRequest.getAccountNumber();

    try {
      ordererRepository.saveOrderer(accountId, password, realName, nickname, phoneNumber, accountNumber);
    } catch (Exception e) {
      throw new IllegalArgumentException("이미 회원정보가 존재합니다.");
    }

    return true;
  }

  /**
   * 심부름 수행자 회원가입 메서드
   * @param signUpAsPerformerRequest
   * @return
   */
  @Transactional
  public boolean signUpAsPerformer(SignUpAsPerformerRequest signUpAsPerformerRequest) {
    String accountId = signUpAsPerformerRequest.getAccountId();
    String realName = signUpAsPerformerRequest.getRealName();
    String nickname = signUpAsPerformerRequest.getNickname();
    String password = passwordEncoder.encode(signUpAsPerformerRequest.getPassword());
    String phoneNumber = signUpAsPerformerRequest.getPhoneNumber();
    String accountNumber = signUpAsPerformerRequest.getAccountNumber();

    try {
      performerRepository.savePerformer(accountId, password, realName, nickname, phoneNumber, accountNumber);
    } catch (Exception e) {
      throw new IllegalArgumentException("이미 회원정보가 존재합니다.");
    }

    return true;
  }

  /**
   * 관리자 등록 메서드
   * @param signUpAsOrdererRequest
   */
  @Transactional
  public boolean signUpAsAdmin(OrdererDto.SignUpAsOrdererRequest signUpAsOrdererRequest) {
    String accountId = signUpAsOrdererRequest.getAccountId();
    String realName = signUpAsOrdererRequest.getRealName();
    String nickname = signUpAsOrdererRequest.getNickname();
    String password = passwordEncoder.encode(signUpAsOrdererRequest.getPassword());
    String phoneNumber = signUpAsOrdererRequest.getPhoneNumber();
    String accountNumber = signUpAsOrdererRequest.getAccountNumber();

    try {
      adminRepository.saveAdmin(accountId, password, realName, nickname, phoneNumber, accountNumber);
    } catch (Exception e) {
      throw new IllegalArgumentException("이미 회원정보가 존재합니다.");
    }

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
    String encodedPassword;

    if (orderer.isEmpty()) {
      return null;
    }

    encodedPassword = orderer.get().getPassword();

    if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
      return null;
    }

    return new LoginUser(orderer.get().getAccountId(),
            orderer.get().getRealName(),
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
    String encodedPassword;

    if (performer.isEmpty()) {
      return null;
    }

    encodedPassword = performer.get().getPassword();

    if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
      return null;
    }

    return new LoginUser(performer.get().getAccountId(),
      performer.get().getRealName(),
      performer.get().getNickname(),
      performer.get().getPhoneNumber(),
      performer.get().getAccountNumber(),
      UserRole.PERFORMER);
  }

}
