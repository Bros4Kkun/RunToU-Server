package com.four.brothers.runtou.repository.user;

import com.four.brothers.runtou.domain.Report;
import com.four.brothers.runtou.domain.ReportCategory;
import com.four.brothers.runtou.domain.User;
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
    User findUserByAccountId = userRepository.findUserByAccountId(accountId).get();

    String accountId1 = "test1";
    String password1 = "test1";
    String realName1 = "test1";
    String nickname1 = "test1";
    String phoneNumber1 = "01012342234";
    String accountNumber1 = "12341244123";
    User haveReportedUser = new User(accountId1, password1, realName1, nickname1, phoneNumber1, accountNumber1);

    User reporter = userRepository.findUserByAccountId(accountId1).get();

    ReportCategory category = ReportCategory.BAD_WORD;
    String content = "욕설";

    //when-then
    assertDoesNotThrow(
        ()->{reportRepository.saveReport(findUserByAccountId, reporter, category, content);}
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

    User reportUser = new User(accountId, password, realName, nickname, phoneNumber, accountNumber);

    userRepository.saveUser(accountId, password, realName, nickname, phoneNumber, accountNumber);
    User findreportUser = userRepository.findUserByAccountId(accountId).get();

    String accountId1 = "test1";
    String password1 = "test1";
    String realName1 = "test1";
    String nickname1 = "test1";
    String phoneNumber1 = "01012342234";
    String accountNumber1 = "12341244123";
    User haveReportedUser = new User(accountId1, password1, realName1, nickname1, phoneNumber1, accountNumber1);

    userRepository.saveUser(accountId1, password1, realName1, nickname1, phoneNumber1, accountNumber1);
    User findUserByAccountId = userRepository.findUserByAccountId(accountId1).get();

    ReportCategory category = ReportCategory.BAD_WORD;
    String content = "욕설";

    int nowPage = 1;
    int itemSize = 1;
    
    reportRepository.saveReport(findreportUser, findUserByAccountId, category, content);

    //when-then
    assertAll(
        ()->{
          List<Report> result = reportRepository.findAll(nowPage, itemSize);
          assertSame(1,result.size());
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

    User reportUser = new User(accountId, password, realName, nickname, phoneNumber, accountNumber);

    userRepository.saveUser(accountId, password, realName, nickname, phoneNumber, accountNumber);
    User findreporter = userRepository.findUserByAccountId(accountId).get();

    String accountId1 = "test1";
    String password1 = "test1";
    String realName1 = "test1";
    String nickname1 = "test1";
    String phoneNumber1 = "01012342234";
    String accountNumber1 = "12341244123";

    userRepository.saveUser(accountId1, password1, realName1, nickname1, phoneNumber1, accountNumber1);
    User findUserByAccountId = userRepository.findUserByAccountId(accountId1).get();

    ReportCategory category = ReportCategory.BAD_WORD;
    String content = "욕설";

    long pk;

    reportRepository.saveReport(findreporter, findUserByAccountId, category, content);
    pk = reportRepository.findAll(1,1).get(0).getId();

    //when-then
    assertDoesNotThrow(
        ()->{
          reportRepository.deleteReportById(pk);
        }
    );
  }
}