package com.four.brothers.runtou.repository.user;

import com.four.brothers.runtou.domain.Admin;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Import({AdminRepository.class})
@DataJpaTest
class AdminRepositoryTest {

  @Autowired
  private AdminRepository adminRepository;

  @Order(1)
  @DisplayName("Admin 저장")
  @Test
  void saveAdminTest() {
    //given
    String accountId1 = "test1";
    String encodedPassword1 = "encodedPassword123!";
    String realName1 = "test1";
    String nickname1 = "test1";
    String phoneNumber1 = "01012341234";
    String accountNumber1 = "1234567890";

    //when-then
    assertDoesNotThrow(() -> {
      adminRepository.saveAdmin(accountId1, encodedPassword1, realName1, nickname1, phoneNumber1, accountNumber1);
    });
  }

  @Order(2)
  @DisplayName("Account Id값으로 Admin 찾기")
  @Test
  void findAdminByAccountIdTest() {
    //given
    String accountId1 = "test1";
    String encodedPassword1 = "encodedPassword123!";
    String realName1 = "test1";
    String nickname1 = "test1";
    String phoneNumber1 = "01012341234";
    String accountNumber1 = "1234567890";

    adminRepository.saveAdmin(accountId1, encodedPassword1, realName1, nickname1, phoneNumber1, accountNumber1);

    //when
    Optional<Admin> result = adminRepository.findAdminByAccountId(accountId1);

    //then
    assertFalse(result.isEmpty());
  }

  @Order(3)
  @DisplayName("pk값으로 Admin 찾기")
  @Test
  void findByIdTest() {
    //given
    String accountId1 = "test1";
    String encodedPassword1 = "encodedPassword123!";
    String realName1 = "test1";
    String nickname1 = "test1";
    String phoneNumber1 = "01012341234";
    String accountNumber1 = "1234567890";
    long id;

    adminRepository.saveAdmin(accountId1, encodedPassword1, realName1, nickname1, phoneNumber1, accountNumber1);

    id = adminRepository.findAdminByAccountId(accountId1).get().getId();

    //when
    Optional<Admin> result = adminRepository.findAdminById(id);

    //then
    assertTrue(result.isPresent());
  }

  @Order(4)
  @DisplayName("모든 admin 반환하기")
  @Test
  void findAllTest() {
    //given
    String accountId1 = "test1";
    String encodedPassword1 = "encodedPassword123!";
    String realName1 = "test1";
    String nickname1 = "test1";
    String phoneNumber1 = "01012341234";
    String accountNumber1 = "1234567890";

    String accountId2 = "test2";
    String encodedPassword2 = "encodedPassword123!";
    String realName2 = "test2";
    String nickname2 = "test2";
    String phoneNumber2 = "01012341234";
    String accountNumber2 = "1234567890";

    int nowPage = 1;
    int itemSize1 = 1;
    int itemSize2 = 2;

    adminRepository.saveAdmin(accountId1, encodedPassword1, realName1, nickname1, phoneNumber1, accountNumber1);
    adminRepository.saveAdmin(accountId2, encodedPassword2, realName2, nickname2, phoneNumber2, accountNumber2);

    //when - then
    assertAll(
      () -> {
        List<Admin> result = adminRepository.findAll(nowPage, itemSize1);
        assertSame(1, result.size());
      },
      () -> {
        List<Admin> result = adminRepository.findAll(nowPage, itemSize2);
        assertSame(2, result.size());
      }
    );
  }

  @Order(5)
  @DisplayName("pk값으로 admin 삭제하기")
  @Test
  void deleteAdminById() {
    //given
    String accountId1 = "test1";
    String encodedPassword1 = "encodedPassword123!";
    String realName1 = "test1";
    String nickname1 = "test1";
    String phoneNumber1 = "01012341234";
    String accountNumber1 = "1234567890";
    long id;

    adminRepository.saveAdmin(accountId1, encodedPassword1, realName1, nickname1, phoneNumber1, accountNumber1);

    id = adminRepository.findAdminByAccountId(accountId1).get().getId();

    //when - then
    assertDoesNotThrow(() -> adminRepository.deleteAdminById(id));
  }

  @Order(6)
  @DisplayName("Account Id값으로 admin 삭제하기")
  @Test
  void deleteAdminByAccountId() {
    //given
    String accountId1 = "test1";
    String encodedPassword1 = "encodedPassword123!";
    String realName1 = "test1";
    String nickname1 = "test1";
    String phoneNumber1 = "01012341234";
    String accountNumber1 = "1234567890";
    String keyword;

    adminRepository.saveAdmin(accountId1, encodedPassword1, realName1, nickname1, phoneNumber1, accountNumber1);

    keyword = adminRepository.findAdminByAccountId(accountId1).get().getAccountId();

    //when - then
    assertDoesNotThrow(() -> adminRepository.deleteAdminByAccountId(keyword));
  }
}