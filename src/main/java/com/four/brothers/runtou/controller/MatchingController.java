package com.four.brothers.runtou.controller;

import com.four.brothers.runtou.dto.MatchRequestDto;
import com.four.brothers.runtou.exception.CanNotAccessException;
import com.four.brothers.runtou.service.MatchRequestService;
import com.four.brothers.runtou.service.MatchingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.four.brothers.runtou.dto.LoginDto.*;
import static com.four.brothers.runtou.dto.MatchRequestDto.*;
import static com.four.brothers.runtou.dto.MatchingDto.*;

@Tag(name = "MatchingController", description = "매칭 관련 API")
@RequiredArgsConstructor
@RequestMapping("/api/match")
@RestController
public class MatchingController {
  private final MatchingService matchingService;
  private final MatchRequestService matchRequestService;

  @Operation(summary = "자신과 연관된 모든 매칭정보 조회")
  @GetMapping
  List<SimpMatchInfo> getAllMatchingInfo(@Parameter(hidden = true) @SessionAttribute LoginUser loginUser) {
    List<SimpMatchInfo> result = matchingService.showAllMatches(loginUser);
    return result;
  }

  @Operation(summary = "자신과 연관된 모든 매칭 중, 현재 수행 중인 매칭만 조회")
  @GetMapping("/now")
  List<SimpMatchInfo> getMatchingInfoDuringJob(@Parameter(hidden = true) @SessionAttribute LoginUser loginUser) {
    List<SimpMatchInfo> result = matchingService.showMatchesDuringJob(loginUser);
    return result;
  }

  @Operation(summary = "자신과 연관된 모든 매칭 중, 완료된 매칭만 조회")
  @GetMapping("/done")
  List<SimpMatchInfo> getDoneMatchingInfo(@Parameter(hidden = true) @SessionAttribute LoginUser loginUser) {
    List<SimpMatchInfo> result = matchingService.showDoneMatches(loginUser);
    return result;
  }

  @Operation(summary = "자신과 연관된 모든 매칭 중, 업무 완료가 요청된 매칭만 조회")
  @GetMapping("/done/request")
  List<SimpMatchInfo> getCompletionRequestedMatchingInfo(@Parameter(hidden = true) @SessionAttribute LoginUser loginUser) {
    List<SimpMatchInfo> result = matchingService.showCompletionRequestedMatches(loginUser);
    return result;
  }

  @Operation(summary = "매칭 상세 정보 조회")
  @GetMapping("/{matchPk}")
  MatchInfo getMatchingInfo(
    @PathVariable long matchPk,
    @Parameter(hidden = true) @SessionAttribute LoginUser loginUser) throws CanNotAccessException {
    MatchInfo result = matchingService.showMatchDetail(matchPk, loginUser);
    return result;
  }

  @Operation(summary = "매칭 요청")
  @PostMapping("/chatroom/{chatRoomPk}")
  MatchRequestInfo requestMatching(
    @PathVariable long chatRoomPk,
    @Parameter(hidden = true) @SessionAttribute LoginUser loginUser) throws Exception {
    MatchRequestInfo result = matchRequestService.requestMatching(chatRoomPk, loginUser);
    return result;
  }

  @Operation(summary = "매칭 요청 수락")
  @PostMapping("/request/{matchRequestPk}")
  MatchInfo acceptMatchRequest(
    @PathVariable long matchRequestPk,
    @Parameter(hidden = true) @SessionAttribute LoginUser loginUser) throws Exception {
    MatchInfo result = matchRequestService.acceptRequestedMatch(matchRequestPk, loginUser);
    return result;
  }

  @Operation(summary = "심부름 완료 요청")
  @PostMapping("/job/done/{matchingPk}")
  JobDoneRequestInfo requestJobDone(
    @PathVariable long matchingPk,
    @Parameter(hidden = true) @SessionAttribute LoginUser loginUser) throws Exception {
    JobDoneRequestInfo response = matchingService.requestToFinishJob(matchingPk, loginUser);
    return response;
  }

  @Operation(summary = "심부름 완료 요청 수락")
  @PostMapping("/done/request/{matchingPk}")
  MatchingFinishInfo finishMatching(
    @PathVariable long matchingPk,
    @Parameter(hidden = true) @SessionAttribute LoginUser loginUser) throws Exception {
    MatchingFinishInfo response = matchingService.acceptJobDoneRequest(matchingPk, loginUser);
    return response;
  }
}
