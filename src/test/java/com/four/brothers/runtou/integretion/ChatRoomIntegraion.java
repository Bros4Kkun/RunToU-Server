package com.four.brothers.runtou.integretion;

import com.four.brothers.runtou.controller.ChatRoomRestController;
import com.four.brothers.runtou.dto.*;
import com.four.brothers.runtou.service.ChatRoomService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(
    properties = {
        "spring.config.location=classpath:application-local.properties",
        "spring.config.location=classpath:application.properties"
    },
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)

public class ChatRoomIntegraion {

    @Autowired
    private TestRestTemplate template;
  /**
   * 공용으로 사용하기 위해, 따로 빼둔 '수행자 회원가입 및 로그인 메서드'
   * @return JSESSIONID
   */
  private String signUpAndLoginAsPerformer() {
    //GIVEN
    String accountId = "testAccountId";
    String realName = "testRealName";
    String nickName = "testnickname";
    String passWord = "password";
    String phoneNumber = "01011112222";
    String accountNumber = "1231231231313";
    String rawPassword = "password";
    UserRole role = UserRole.PERFORMER;

    PerformerDto.SignUpAsPerformerRequest request =new PerformerDto.SignUpAsPerformerRequest();

    request.setAccountId(accountId);
    request.setRealName(realName);
    request.setNickname(nickName);
    request.setPassword(passWord);
    request.setPhoneNumber(phoneNumber);
    request.setAccountNumber(accountNumber);

    LoginDto.LoginRequest request1 = new LoginDto.LoginRequest();

    request1.setAccountId(accountId);
    request1.setRawPassword(rawPassword);
    request1.setRole(role);

    //WHEN
    HttpStatus resultStatusCode = template.postForEntity("/api/user/signup/performer", request, OrdererDto.SignUpAsOrdererResponse.class)
        .getStatusCode();
    HttpStatus resultStatusCode1 = template.postForEntity(
        "/api/user/signin", request1, LoginDto.LoginResponse.class).getStatusCode();
    //THEN

    List<String> strings = template.postForEntity(
        "/api/user/signin", request1, LoginDto.LoginResponse.class).getHeaders().get("Set-Cookie");

    return strings.get(0);
  }

  @Transactional
  @DisplayName("채팅방 요청")
  @Test
  void addNewChatroomTest(){
    //GIVEN
    String jsessionid = signUpAndLoginAsPerformer();
    long ordererPk = 1;
    long performerPk = 2;
    long orderSheetPk = 3;

    ChatRoomDto.NewChatRoomRequest chatRoomRequest = new ChatRoomDto.NewChatRoomRequest();
    chatRoomRequest.setOrdererPk(ordererPk);
    chatRoomRequest.setPerformerPk(performerPk);
    chatRoomRequest.setOrderSheetPk(orderSheetPk);

    HttpHeaders headers = new HttpHeaders();
    headers.set("Cookie", jsessionid);
    headers.set("Content-Type", "application/json");

    HttpEntity requestHttpEntity = new HttpEntity(chatRoomRequest, headers);

    //WHEN
    ResponseEntity<String> responseHttpEntity = template.exchange(
        "/api/chatroom/ordersheet/" + orderSheetPk,
        HttpMethod.POST,
        requestHttpEntity,
        String.class
    );
    //THEN
    assertTrue(true, String.valueOf(responseHttpEntity.getStatusCode().is2xxSuccessful()));
  }
  
  @Transactional
  @DisplayName("로그인 된 사용자가 참여하는 모든 채팅방 정보 확인(simpleChatRoomInfo)")
  @Test
  void showAllChatRoomTest(){
    //GIVEN
    String jsessionid = signUpAndLoginAsPerformer();

    long chatRoomPk = 10;
    String latestChatMessage = "latest message";
    LocalDateTime latestMsgSentDateTime = LocalDateTime.now();
    long ordererid = 11;
    String ordereraccountId = "test";
    String orderernickname = "testst";
    OrdererDto.SimpOrdererInfo ordererInfo = new OrdererDto.SimpOrdererInfo(ordererid, ordereraccountId, orderernickname);
    long performerid = 14;
    String performeraccountId = "ptest";
    String performernickname = "ptestst";
    PerformerDto.SimpPerformerInfo performerInfo = new PerformerDto.SimpPerformerInfo(performerid, performeraccountId, performernickname);
    long ordererSheetpk = 21;
    boolean isMatched = false;

    ChatRoomDto.SimpleChatRoomInfo simpleChatRoomInfo = new ChatRoomDto.SimpleChatRoomInfo();

    simpleChatRoomInfo.setChatRoomPk(chatRoomPk);
    simpleChatRoomInfo.setLatestChatMessage(latestChatMessage);
    simpleChatRoomInfo.setLatestMsgSentDateTime(latestMsgSentDateTime);
    simpleChatRoomInfo.setOrdererInfo(ordererInfo);
    simpleChatRoomInfo.setPerformerInfo(performerInfo);
    simpleChatRoomInfo.setOrdererSheetPk(ordererSheetpk);
    simpleChatRoomInfo.setMatched(isMatched);

    HttpHeaders headers = new HttpHeaders();
    headers.set("Cookie", jsessionid);
    headers.set("Content-Type", "application/json");

    HttpEntity requestHttpEntity = new HttpEntity(simpleChatRoomInfo, headers);

    //WHEN
    ResponseEntity<String> responseHttpEntity = template.exchange(
        "/api/chatroom/",
        HttpMethod.GET,
        requestHttpEntity,
        String.class
    );

    //THEN
    assertEquals(true, responseHttpEntity.getStatusCode().is2xxSuccessful());

  }
}
