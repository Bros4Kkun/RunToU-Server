package com.four.brothers.runtou.service;

import com.four.brothers.runtou.domain.Matching;
import com.four.brothers.runtou.dto.LoginDto;
import com.four.brothers.runtou.dto.MatchDto;
import com.four.brothers.runtou.repository.MatchingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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
    List<Matching> allMatchingByUserAccountId = matchingRepository.findAllMatchingByUserAccountId(loginUser.getAccountId());
    allMatchingByUserAccountId.stream().forEach((item) -> result.add(new SimpMatchInfo(item)));
    return result;
  }
}
