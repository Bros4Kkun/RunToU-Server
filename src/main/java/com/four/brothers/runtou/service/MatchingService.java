package com.four.brothers.runtou.service;

import com.four.brothers.runtou.domain.Matching;
import com.four.brothers.runtou.dto.LoginDto;
import com.four.brothers.runtou.repository.MatchingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.four.brothers.runtou.dto.MatchDto.*;

@RequiredArgsConstructor
@Service
public class MatchingService {
  private final MatchingRepository matchingRepository;

  /**
   * 로그인한 사용자의 모든 매칭 정보 응답
   * @param loginUser
   * @return
   */
  @Transactional
  public List<SimpMatchInfo> showAllMatches(LoginDto.LoginUser loginUser) {
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
  public List<SimpMatchInfo> showMatchesDuringJob(LoginDto.LoginUser loginUser) {
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
  public List<SimpMatchInfo> showDoneMatches(LoginDto.LoginUser loginUser) {
    List<SimpMatchInfo> result = new ArrayList<>();
    List<Matching> allMatchingByUserAccountId = matchingRepository.findMatchingByUserAccountId(loginUser.getAccountId(), true);
    allMatchingByUserAccountId.stream().forEach((item) -> result.add(new SimpMatchInfo(item)));
    return result;
  }

  /**
   * 해당 pk값을 갖는 매칭의 상세정보 반환
   * @param matchingPk
   * @return
   */
  @Transactional
  public MatchInfo showMatchDetail(long matchingPk) {
    Optional<Matching> matching = matchingRepository.findById(matchingPk);

    if (matching.isEmpty()) {
      throw new IllegalArgumentException("존재하지 않는 매칭 id입니다.");
    }

    return new MatchInfo(matching.get());
  }
}
