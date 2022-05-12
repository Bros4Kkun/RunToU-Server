package com.four.brothers.runtou.service;

import com.four.brothers.runtou.domain.*;
import com.four.brothers.runtou.exception.CanNotAccessException;
import com.four.brothers.runtou.exception.code.MatchingExceptionCode;
import com.four.brothers.runtou.repository.ChatMessageRepository;
import com.four.brothers.runtou.repository.ChatRoomRepository;
import com.four.brothers.runtou.repository.MatchRequestRepository;
import com.four.brothers.runtou.repository.MatchingRepository;
import com.four.brothers.runtou.repository.user.PerformerRepository;
import com.four.brothers.runtou.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.four.brothers.runtou.dto.LoginDto.*;
import static com.four.brothers.runtou.dto.MatchingDto.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class MatchingService {
  private final MatchingRepository matchingRepository;
  private final MatchRequestRepository matchRequestRepository;
  private final ChatRoomRepository chatRoomRepository;
  private final PerformerRepository performerRepository;
  private final ChatMessageRepository chatMessageRepository;
  private final UserRepository userRepository;

  private final SimpMessagingTemplate simpTemplate;

  /**
   * 로그인한 사용자의 모든 매칭 정보 응답
   * @param loginUser
   * @return
   */
  @Transactional
  public List<SimpMatchInfo> showAllMatches(LoginUser loginUser) {
    List<SimpMatchInfo> result = new ArrayList<>();
    List<Matching> allMatchingByUserAccountId = matchingRepository.findMatchingByUserAccountId(loginUser.getAccountId());
    allMatchingByUserAccountId.stream().forEach((item) -> result.add(new SimpMatchInfo(item)));
    return result;
  }

  /**
   * 로그인한 사용자의 모든 매칭 정보 중, 현재 수행중인 것만 응답
   * @param loginUser
   * @return
   */
  @Transactional
  public List<SimpMatchInfo> showMatchesDuringJob(LoginUser loginUser) {
    List<SimpMatchInfo> result = new ArrayList<>();
    List<Matching> allMatchingByUserAccountId = matchingRepository.findMatchingByUserAccountId(loginUser.getAccountId(), false);
    allMatchingByUserAccountId.stream().forEach((item) -> result.add(new SimpMatchInfo(item)));
    return result;
  }

  /**
   * 로그인한 사용자의 모든 매칭 정보 중, 완료된 것만 응답
   * @param loginUser
   * @return
   */
  @Transactional
  public List<SimpMatchInfo> showDoneMatches(LoginUser loginUser) {
    List<SimpMatchInfo> result = new ArrayList<>();
    List<Matching> allMatchingByUserAccountId = matchingRepository.findMatchingByUserAccountId(loginUser.getAccountId(), true);
    allMatchingByUserAccountId.stream().forEach((item) -> result.add(new SimpMatchInfo(item)));
    return result;
  }

  /**
   * 해당 pk값을 갖는 매칭의 상세정보 반환
   * @param matchingPk 확인할 매칭 PK 값
   * @param loginUser
   * @return
   */
  @Transactional
  public MatchInfo showMatchDetail(long matchingPk, LoginUser loginUser) throws CanNotAccessException {
    Optional<Matching> matching = matchingRepository.findById(matchingPk);

    if (matching.isEmpty()) {
      throw new IllegalArgumentException("존재하지 않는 매칭 id입니다.");
    }

    checkRightAuthority(loginUser, matching);

    return new MatchInfo(matching.get());
  }

  /**
   * 해당 매칭에 접근하려는 사용자가 접근 권한을 가지고 있는지 확인하는 메서드
   * @param loginUser
   * @param matching
   * @throws CanNotAccessException
   */
  private void checkRightAuthority(LoginUser loginUser, Optional<Matching> matching) throws CanNotAccessException {
    String ordererAccountId = matching.get().getOrderSheet().getOrderer().getAccountId();
    String performerAccountId = matching.get().getPerformer().getAccountId();
    String loginUserAccountId = loginUser.getAccountId();
    //접근 권한이 있다면
    if (ordererAccountId.equals(loginUserAccountId) || performerAccountId.equals(loginUserAccountId)) {
      return;
    }
    throw new CanNotAccessException(MatchingExceptionCode.NO_AUTHORITY, "해당 매칭에 대한 접근 권한이 없습니다.");
  }
}
