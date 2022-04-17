package com.four.brothers.runtou.controller;

import com.four.brothers.runtou.dto.ChatRoomDto;
import com.four.brothers.runtou.dto.LoginDto;
import com.four.brothers.runtou.exception.BadRequestException;
import com.four.brothers.runtou.exception.CanNotAccessException;
import com.four.brothers.runtou.exception.code.RequestExceptionCode;
import com.four.brothers.runtou.service.ChatRoomService;
import com.four.brothers.runtou.service.SocketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.four.brothers.runtou.dto.ChatRoomDto.*;
import static com.four.brothers.runtou.dto.LoginDto.*;

@Tag(name = "ChatRoomController", description = "채팅방 관련 API")
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/chatroom")
@RestController
public class ChatRoomRestController {
  private final ChatRoomService chatRoomService;
  private final SocketService socketService;

  @Operation(
    summary = "채팅방 요청",
    description = "만약 기존의 채팅방이 있다면, 기존 것으로 응답\n" +
      "기존의 채팅방이 없다면, 새 채팅방으로 응답"
  )
  @GetMapping("/ordersheet/{orderSheetPk}")
  public NewChatRoomResponse addNewChatRoom(
    @PathVariable long orderSheetPk,
    @Parameter(hidden = true) @SessionAttribute LoginUser loginUser,
    @Validated @RequestBody NewChatRoomRequest request,
    BindingResult bindingResult) throws CanNotAccessException {

    if (bindingResult.hasFieldErrors()) {
      throw new BadRequestException(RequestExceptionCode.WRONG_FORMAT, bindingResult.getFieldError().getDefaultMessage());
    }

    NewChatRoomResponse newChatRoomResponse = chatRoomService.makeNewChatRoomByPerformer(request, loginUser);

    //만약 새로 생성된 채팅방이라면
    if (newChatRoomResponse.isNew()) {
      socketService.alertNewChatRoomToOrderer(newChatRoomResponse.getOrdererPk(), newChatRoomResponse.getChatRoomPk());
    }

    return newChatRoomResponse;
  }

}
