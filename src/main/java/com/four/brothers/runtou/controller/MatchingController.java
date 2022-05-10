package com.four.brothers.runtou.controller;

import com.four.brothers.runtou.dto.LoginDto;
import com.four.brothers.runtou.dto.MatchDto;
import com.four.brothers.runtou.service.MatchingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.List;

import static com.four.brothers.runtou.dto.LoginDto.*;
import static com.four.brothers.runtou.dto.MatchDto.*;

@RequiredArgsConstructor
@RequestMapping("/api/match")
@RestController
public class MatchingController {
  private final MatchingService matchingService;

  @Operation(summary = "자신과 연관된 모든 매칭정보 조회")
  @GetMapping
  List<SimpMatchInfo> getAllMatchingInfo(@Parameter(hidden = true) @SessionAttribute LoginUser loginUser) {
    List<SimpMatchInfo> result = matchingService.showAllMatches(loginUser);
    return result;
  }
}
