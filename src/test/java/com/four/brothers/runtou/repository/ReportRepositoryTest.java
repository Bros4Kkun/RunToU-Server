package com.four.brothers.runtou.repository;

import com.four.brothers.runtou.domain.Report;
import com.four.brothers.runtou.domain.ReportCategory;
import com.four.brothers.runtou.domain.User;
import com.four.brothers.runtou.repository.ReportRepository;
import com.four.brothers.runtou.repository.user.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Import({ReportRepository.class, UserRepository.class})
@DataJpaTest
class ReportRepositoryTest {

  @Autowired
  private ReportRepository reportRepository;

  @Autowired
  private UserRepository userRepository;

  @DisplayName("report저장")
  @Test
  void saveReportTest(){
    //given
    String accountId = "test";
    String password = "test";
    String realName = "test";
    String nickname = "test";
    String phoneNumber = "01012341234";
    String accountNumber = "12341234123";

    userRepository.saveUser(accountId, password, realName, nickname, phoneNumber, accountNumber);
    User reportUser = userRepository.findUserByAccountId(accountId).get();

    String accountId1 = "test1";
    String password1 = "test1";
    String realName1 = "test1";
    String nickname1 = "test1";
    String phoneNumber1 = "01012342234";
    String accountNumber1 = "12341244123";

    userRepository.saveUser(accountId1, password1, realName1, nickname1, phoneNumber1, accountNumber1);
    User haveReportUser = userRepository.findUserByAccountId(accountId1).get();

    ReportCategory category = ReportCategory.BAD_WORD;
    String content = "욕설";

    //when-then
    assertDoesNotThrow(
        ()->{reportRepository.saveReport(reportUser, haveReportUser, category, content);}
    );

  }

  @DisplayName("모든신고 조회")
  @Test
  void findAllTest(){
    //given
    String accountId = "test";
    String password = "test";
    String realName = "test";
    String nickname = "test";
    String phoneNumber = "01012341234";
    String accountNumber = "12341234123";

    userRepository.saveUser(accountId, password, realName, nickname, phoneNumber, accountNumber);
    User reportUser = userRepository.findUserByAccountId(accountId).get();

    String accountId1 = "test1";
    String password1 = "test1";
    String realName1 = "test1";
    String nickname1 = "test1";
    String phoneNumber1 = "01012342234";
    String accountNumber1 = "12341244123";

    userRepository.saveUser(accountId1, password1, realName1, nickname1, phoneNumber1, accountNumber1);
    User haveReportUser = userRepository.findUserByAccountId(accountId1).get();

    ReportCategory category = ReportCategory.BAD_WORD;
    String content = "욕설";

    String accountId2 = "test2";
    String password2 = "test2";
    String realName2 = "test2";
    String nickname2 = "test2";
    String phoneNumber2 = "01112341234";
    String accountNumber2 = "62341234123";

    userRepository.saveUser(accountId2, password2, realName2, nickname2, phoneNumber2, accountNumber2);
    User reportUser2 = userRepository.findUserByAccountId(accountId2).get();

    String accountId3 = "test3";
    String password3 = "test3";
    String realName3 = "test3";
    String nickname3 = "test3";
    String phoneNumber3 = "03012342234";
    String accountNumber3 = "32341244123";

    userRepository.saveUser(accountId3, password3, realName3, nickname3, phoneNumber3, accountNumber3);
    User haveReportUser2 = userRepository.findUserByAccountId(accountId3).get();

    ReportCategory category3 = ReportCategory.BAD_WORD;
    String content3 = "욕설";

    int nowPage = 1;
    int itemSize = 1;
    int itemSize2 =2;
    
    reportRepository.saveReport(reportUser, haveReportUser, category, content);
    reportRepository.saveReport(reportUser2, haveReportUser2, category3, content3);

    //when-then
    assertAll(
        ()->{
          List<Report> result = reportRepository.findAll(nowPage, itemSize);
          assertSame(1,result.size());
        },
        ()->{
          List<Report> result = reportRepository.findAll(nowPage, itemSize2);
          assertSame(2,result.size());

        }
    );
    
  }

  @DisplayName("pk값으로 삭제하는 메소드")
  @Test
  void deleteReportByIdTest(){
    //given
    String accountId = "test";
    String password = "test";
    String realName = "test";
    String nickname = "test";
    String phoneNumber = "01012341234";
    String accountNumber = "12341234123";

    userRepository.saveUser(accountId, password, realName, nickname, phoneNumber, accountNumber);
    User reportUser = userRepository.findUserByAccountId(accountId).get();

    String accountId1 = "test1";
    String password1 = "test1";
    String realName1 = "test1";
    String nickname1 = "test1";
    String phoneNumber1 = "01012342234";
    String accountNumber1 = "12341244123";

    userRepository.saveUser(accountId1, password1, realName1, nickname1, phoneNumber1, accountNumber1);
    User haveReportUser = userRepository.findUserByAccountId(accountId1).get();

    ReportCategory category = ReportCategory.BAD_WORD;
    String content = "욕설";

    long pk;

    reportRepository.saveReport(reportUser, haveReportUser, category, content);
    pk = reportRepository.findAll(1,1).get(0).getId();

    //when-then
    assertDoesNotThrow(
        ()->{
          reportRepository.deleteReportById(pk);
        }
    );
  }
}