package com.four.brothers.runtou.service;

import com.four.brothers.runtou.domain.User;
import com.four.brothers.runtou.dto.OrdererDto;
import com.four.brothers.runtou.dto.UserDto;
import com.four.brothers.runtou.repository.user.OrdererRepository;
import com.four.brothers.runtou.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@RequiredArgsConstructor
@Service
public class UserService {
  private final OrdererRepository ordererRepository;
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
  public UserDto.DuplicatedAccountIdResponse isDuplicatedAccountId(UserDto.DuplicatedAccountIdRequest request) {
    Optional<User> userByAccountId = userRepository.findUserByAccountId(request.getAccountId());
    return new UserDto.DuplicatedAccountIdResponse(userByAccountId.isPresent());
  }

  /**
   * 중복 닉네임 확인
   * UserDto.DuplicatedNicknameResponse의 isDuplicatedNickname 필드값
   * true: 중복O, false: 중복X
   * @param request
   * @return
   */
  @Transactional
  public UserDto.DuplicatedNicknameResponse isDuplicatedNickname(UserDto.DuplicatedNicknameRequest request) {
    Optional<User> userByNickname = userRepository.findUserByNickname(request.getNickname());
    return new UserDto.DuplicatedNicknameResponse(userByNickname.isPresent());
  }

}
