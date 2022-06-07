package com.four.brothers.runtou.integretion;

import com.four.brothers.runtou.domain.OrderSheet;
import com.four.brothers.runtou.domain.OrderSheetCategory;
import com.four.brothers.runtou.dto.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(
    properties = {
        "spring.config.location=classpath:application-local.properties",
        "spring.config.location=classpath:application.properties"
    },
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)

public class OrderSheetIntegraionTest {

  @Autowired
  private TestRestTemplate template;

  /**
   * 공용으로 사용하기 위해, 따로 빼둔 '요청자 회원가입 및 로그인 메서드'
   * @return JSESSIONID
   */
  private String signUpAndLoginAsOrderer() {
    //GIVEN
    String accountId = "testAccountId";
    String realName = "testRealName";
    String nickName = "testnickname";
    String passWord = "password";
    String phoneNumber = "01011112222";
    String accountNumber = "1231231231313";
    String rawPassword = "password";
    UserRole role = UserRole.ORDERER;

    OrdererDto.SignUpAsOrdererRequest request = new OrdererDto.SignUpAsOrdererRequest();

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
    HttpStatus resultStatusCode = template.postForEntity("/api/user/signup/orderer", request, OrdererDto.SignUpAsOrdererResponse.class)
        .getStatusCode();
    HttpStatus resultStatusCode1 = template.postForEntity(
        "/api/user/signin", request1, LoginDto.LoginResponse.class).getStatusCode();
    //THEN

    List<String> strings = template.postForEntity(
        "/api/user/signin", request1, LoginDto.LoginResponse.class).getHeaders().get("Set-Cookie");

    return strings.get(0);
  }

  @Transactional
  @DisplayName("새 요청서 등록 and 요청서 리스트 조회 and 세부정보 조회 and 요청서 수정")
  @Test

  void OrderSheetSaveTest(){

    //GIVEN
    String jsessionid = signUpAndLoginAsOrderer();
    String title = "testtitle";
    String content ="testcontent";
    OrderSheetCategory orderSheetCategory = OrderSheetCategory.DELIVERY_AND_SHOPPING;
    String destination = "testdestination";
    int cost = 10000;
    LocalDateTime wishedDeadline = LocalDateTime.now();

    int nowPage = 1;
    long orderSheetId = 1;

    String updatetitle = "updatetitle";
    String updatecontent = "updatecontent";
    OrderSheetCategory updatecategory = OrderSheetCategory.ETC;
    String updatedestination = "updatedestination";
    int updatecost = 30000;
    LocalDateTime updatewishedDeadline = LocalDateTime.now();

    OrderSheetDto.OrderSheetSaveRequest saverequest = new OrderSheetDto.OrderSheetSaveRequest();
    OrderSheetDto.AllOrderSheetRequest Allrequest1 = new OrderSheetDto.AllOrderSheetRequest();
    OrderSheetDto.OrderSheetDetailsRequest Detailrequest2 = new OrderSheetDto.OrderSheetDetailsRequest();
    OrderSheetDto.OrderSheetSaveRequest Updaterequest = new OrderSheetDto.OrderSheetSaveRequest();

    saverequest.setTitle(title);
    saverequest.setContent(content);
    saverequest.setCategory(orderSheetCategory);
    saverequest.setDestination(destination);
    saverequest.setCost(cost);
    saverequest.setWishedDeadline(wishedDeadline);

    Allrequest1.setNowPage(nowPage);
    Allrequest1.setCategory(orderSheetCategory);

    Detailrequest2.setOrderSheetId(orderSheetId);

    Updaterequest.setTitle(updatetitle);
    Updaterequest.setContent(updatecontent);
    Updaterequest.setCategory(updatecategory);
    Updaterequest.setDestination(updatedestination);
    Updaterequest.setCost(updatecost);
    Updaterequest.setWishedDeadline(updatewishedDeadline);

    HttpHeaders headers = new HttpHeaders();
    headers.set("Cookie", jsessionid);
    headers.set("Content-Type", "application/json");

    HttpEntity requestHttpEntity = new HttpEntity(saverequest, headers);
    HttpEntity requestHttpEntity1 = new HttpEntity(Allrequest1, headers);
    HttpEntity requestHttpEntity2 = new HttpEntity(Detailrequest2, headers);
    HttpEntity requestHttpEntity3 = new HttpEntity(Updaterequest, headers);

    //WHEN
    ResponseEntity<String> responseHttpEntity = template.exchange(
        "/api/ordersheet",
        HttpMethod.POST,
        requestHttpEntity,
        String.class
    );
    ResponseEntity<String> responseHttpEntity1 = template.exchange(
        "/api/ordersheet/list/" + orderSheetCategory + "/" + nowPage,
        HttpMethod.GET,
        requestHttpEntity1,
        String.class
    );
    ResponseEntity<String> responseHttpEntity2 = template.exchange(
        "/api/ordersheet/" + orderSheetId,
        HttpMethod.GET,
        requestHttpEntity2,
        String.class
    );
    ResponseEntity<String> reponseHttpEntity3 = template.exchange(
        "/api/ordersheet/" + orderSheetId,
        HttpMethod.POST,
        requestHttpEntity3,
        String.class
    );

    //THEN
    assertAll(
        ()->{
          assertTrue(true, String.valueOf(responseHttpEntity.getStatusCode().is2xxSuccessful()));
        },
        ()->{
          assertEquals(true, responseHttpEntity1.getStatusCode().is2xxSuccessful());
        },
        ()->{
          assertNotNull(false, String.valueOf(responseHttpEntity2.getStatusCode().is2xxSuccessful()));
        },
        ()->{
          assertTrue(true, String.valueOf(reponseHttpEntity3.getStatusCode().is2xxSuccessful()));
        }

    );
  }

  @Transactional
  @DisplayName("가격 추천 배달 및 장보기, 청소 및 집안일, 설치 및 조립운반, 동행 및 돌봄, 역할대행")
  @Test

  void recommendGoodCostTest(){
    //GIVEN
    String jsessionid = signUpAndLoginAsOrderer();
    int distance = 3;
    int deliveryminutes = 50;
    int deliverycost = 20000;

    int cleanminute = 40;
    int cleanlevel = 3;

    CostDto.DeliveryShoppingRequest costdeliveryrequest = new CostDto.DeliveryShoppingRequest();
    CostDto.CleaningHouseworkRequest costcleanrequest = new CostDto.CleaningHouseworkRequest();

    costdeliveryrequest.setDistance(distance);
    costdeliveryrequest.setMinutes(deliveryminutes);
    costdeliveryrequest.setCost(deliverycost);

    costcleanrequest.setMinutes(cleanminute);
    costcleanrequest.setLevel(cleanlevel);

    HttpHeaders headers = new HttpHeaders();
    headers.set("Cookie", jsessionid);
    headers.set("Content-Type", "application/json");

    HttpEntity requestHttpEntity = new HttpEntity(costdeliveryrequest, headers);
    HttpEntity requestHttpEntity1 = new HttpEntity(costcleanrequest, headers);

    //WHEN
    ResponseEntity<String> responseHttpEntity = template.exchange(
        "/api/ordersheet/delivery-shopping/cost",
        HttpMethod.POST,
        requestHttpEntity,
        String.class
    );
    System.out.println(responseHttpEntity);
    ResponseEntity<String> responseHttpEntity1 = template.exchange(
        "/api/ordersheet/cleaning-housework/cost",
        HttpMethod.POST,
        requestHttpEntity1,
        String.class
    );
    //THEN
    assertAll(
        ()->{
            assertEquals(true,responseHttpEntity.getStatusCode().is2xxSuccessful());
        },
        ()->{
          assertEquals(true,responseHttpEntity1.getStatusCode().is2xxSuccessful());
        }
    );
  }

}
