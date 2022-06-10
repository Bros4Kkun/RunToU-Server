package com.four.brothers.runtou.integretion;

import com.four.brothers.runtou.domain.*;
import com.four.brothers.runtou.dto.*;
import com.four.brothers.runtou.repository.ChatRoomRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.lang.Nullable;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(
    properties = {
        "spring.config.location=classpath:application-local.properties",
        "spring.config.location=classpath:application.properties"
    },
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
public class MatchingIntegration {

  @Autowired
  private TestRestTemplate template;

  /**
   * 공용으로 사용하기 위해, 따로 빼둔 '수행자 회원가입 및 로그인 메서드'
   *
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

    PerformerDto.SignUpAsPerformerRequest request = new PerformerDto.SignUpAsPerformerRequest();

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
    HttpStatus resultStatusCode = template.postForEntity("/api/user/signup/performer", request, PerformerDto.SignUpAsPerformerResponse.class)
        .getStatusCode();
    HttpStatus resultStatusCode1 = template.postForEntity(
        "/api/user/signin", request1, LoginDto.LoginResponse.class).getStatusCode();
    //THEN

    List<String> strings = template.postForEntity(
        "/api/user/signin", request1, LoginDto.LoginResponse.class).getHeaders().get("Set-Cookie");

    return strings.get(0);


  }

  @Transactional
  @DisplayName("매칭 요청")
  @Test
  void requestMatchingTest() {
    //GIVEN
    ChatRoomRepository chatRoomRepository = new ChatRoomRepository();
    String jsessionid = signUpAndLoginAsPerformer();
    long chatRoomPk = 77;
    long matchRequestId = 33;
    long orderSheetId = 44;
    String ordererNickname = "testnickname";
    String title = "testtitle";
    String contentSample = "sameple";
    OrderSheetCategory orderSheetCategory = OrderSheetCategory.DELIVERY_AND_SHOPPING;
    String destination = "강서구";
    int cost = 15000;

    long ordererid = 23;
    String ordereraccountId = "aaaaaddd";

    long perforemrid = 71;
    String performeracoountid = "perforemaaa";
    String performernickname = "faster";

    boolean isAccepted = false;
    boolean isOrderSheetMatched = false;

    OrderSheetDto.SimpOrderSheetInfo orderSheetInfo = new OrderSheetDto.SimpOrderSheetInfo
        (orderSheetId, ordererNickname, title, contentSample, orderSheetCategory,destination, cost );

    OrdererDto.SimpOrdererInfo ordererInfo = new OrdererDto.SimpOrdererInfo(ordererid, ordereraccountId, ordererNickname);

    PerformerDto.SimpPerformerInfo performerInfo = new PerformerDto.SimpPerformerInfo(perforemrid, performeracoountid, performernickname);

    MatchRequestDto.MatchRequestInfo matchRequestInfo = new MatchRequestDto.MatchRequestInfo
        (matchRequestId, orderSheetInfo, ordererInfo, performerInfo, isAccepted, isOrderSheetMatched);

    long ordererid1 = 23;
    //String ordereraccountId1 = "aaaaaddd";
    String ordererpassword = "aasdfava";
    //String ordererNickname1 = "testnickname";
    String ordererrealName = "ck";
    String ordererphoneNumber = "0101998992";
    String ordereraccountNumber = "1123123141";
    Boolean isDoingJobNow = false;
    Orderer orderer = new Orderer
        (ordererid, ordereraccountId, ordererpassword, ordererNickname, ordererrealName, ordererphoneNumber, ordereraccountNumber, isDoingJobNow );

    String perforemrpassword = "aaaaaaaa";
    String perforemrrealName = "cg";
    String perforemrphoneNumber = "01011112222";
    String performeraccountNumber = "1515151222";
    LocalDateTime becamePerformerDateTime = LocalDateTime.now();
    Long earnedMoney = 15000l;
    Performer performer = new Performer
        (perforemrid, performeracoountid, perforemrpassword, performernickname, perforemrrealName, perforemrphoneNumber, performeraccountNumber,isDoingJobNow, becamePerformerDateTime, earnedMoney);

    String ordersheetcontent = "빠르게";
    LocalDateTime wishedDeadline = LocalDateTime.now();
    OrderSheet orderSheet = new OrderSheet(orderer, title, ordersheetcontent, orderSheetCategory, destination, cost, wishedDeadline);

    chatRoomRepository.saveChatRoom(orderer, performer, orderSheet);
    chatRoomRepository.findChatRoomById(chatRoomPk);

    HttpHeaders headers = new HttpHeaders();
    headers.set("Cookie", jsessionid);
    headers.set("Content-Type", "application/json");

    HttpEntity requestHttpEntity = new HttpEntity(matchRequestInfo, headers);

    //WHEN
    ResponseEntity<String> responseHttpEntity = template.exchange(
        "api/match/chatroom/" + chatRoomPk,
        HttpMethod.POST,
        requestHttpEntity,
        String.class
    );

    //THEN
    assertEquals(true, responseHttpEntity.getStatusCode().is2xxSuccessful());

  }
}
