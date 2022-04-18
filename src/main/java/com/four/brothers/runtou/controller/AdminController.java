package com.four.brothers.runtou.controller;

import com.four.brothers.runtou.domain.TableNames;
import com.four.brothers.runtou.dto.UserRole;
import com.four.brothers.runtou.dto.model.*;
import com.four.brothers.runtou.service.admin.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.four.brothers.runtou.dto.LoginDto.*;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/admin")
@Controller
public class AdminController {

  private final AdminService adminService;

  @GetMapping("/db")
  public String login(@ModelAttribute("admin") LoginRequest request) {
    return "db-login";
  }

  @PostMapping("/db")
  public String login(@Validated @ModelAttribute("admin") AdminLoginRequest request,
                      BindingResult bindingResult,
                      HttpServletRequest httpServletRequest) {
    LoginRequest loginRequest = new LoginRequest(request.getAccountId(), request.getRawPassword(), UserRole.ADMIN);
    LoginUser loginAdmin = adminService.login(loginRequest);

    log.info("어드민 아이디: {}\n어드민 PW: {}", request.getAccountId(), request.getRawPassword());

    if (loginAdmin == null) {
      bindingResult.reject("wrongAdminAccount", "관리자 계정 정보가 틀립니다.");
    }
    if (bindingResult.hasErrors()) {
      return "db-login";
    }

    HttpSession session = httpServletRequest.getSession();
    session.setAttribute("loginAdmin", loginAdmin);

    return "redirect:/admin/db/main";
  }

  @GetMapping("/db/main")
  public String main() {
    return "db-main";
  }

  @GetMapping("/db/logout")
  public String logout(HttpServletRequest request) {
    HttpSession session = request.getSession();
    session.removeAttribute("loginAdmin");
    return "redirect:/admin/db";
  }

  @GetMapping("/db/table/orderer")
  public String ordererTable(Model model) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    model.addAttribute("entityName", TableNames.ORDERER);
    model.addAttribute("dtoFieldsNames", getDtoFieldsNames(OrdererDto.class));
    model.addAttribute("dtos", adminService.getAllOrderer());
    return "db-table";
  }

  @GetMapping("/db/table/performer")
  public String performerTable(Model model) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    model.addAttribute("entityName", TableNames.PERFORMER);
    model.addAttribute("dtoFieldsNames", getDtoFieldsNames(PerformerDto.class));
    model.addAttribute("dtos", adminService.getAllPerformer());
    return "db-table";
  }

  @GetMapping("/db/table/admin")
  public String adminTable(Model model) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    model.addAttribute("entityName", TableNames.ADMIN);
    model.addAttribute("dtoFieldsNames", getDtoFieldsNames(AdminDto.class));
    model.addAttribute("dtos", adminService.getAllAdmin());
    return "db-table";
  }

  @GetMapping("/db/table/ordersheet")
  public String orderSheetTable(Model model) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    model.addAttribute("entityName", TableNames.ORDER_SHEET);
    model.addAttribute("dtoFieldsNames", getDtoFieldsNames(OrderSheetDto.class));
    model.addAttribute("dtos", adminService.getAllOrderSheet());
    return "db-table";
  }

  @GetMapping("/db/table/report")
  public String reportTable(Model model) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    model.addAttribute("entityName", TableNames.REPORT);
    model.addAttribute("dtoFieldsNames", getDtoFieldsNames(ReportDto.class));
    model.addAttribute("dtos", adminService.getAllReport());
    return "db-table";
  }

  @GetMapping("/db/table/review")
  public String reviewTable(Model model) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    model.addAttribute("entityName", TableNames.REVIEW);
    model.addAttribute("dtoFieldsNames", getDtoFieldsNames(ReviewDto.class));
    model.addAttribute("dtos", adminService.getAllReview());
    return "db-table";
  }

  @GetMapping("/db/table/matching")
  public String matchingTable(Model model) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    model.addAttribute("entityName", TableNames.MATCHING);
    model.addAttribute("dtoFieldsNames", getDtoFieldsNames(MatchingDto.class));
    model.addAttribute("dtos", adminService.getAllMatching());
    return "db-table";
  }

  @GetMapping("/db/table/matchrequest")
  public String matchRequestTable(Model model) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    model.addAttribute("entityName", TableNames.MATCH_REQUEST);
    model.addAttribute("dtoFieldsNames", getDtoFieldsNames(MatchRequestDto.class));
    model.addAttribute("dtos", adminService.getAllMatchRequestDto());
    return "db-table";
  }

  @GetMapping("/db/table/chatroom")
  public String chatRoomTable(Model model) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    model.addAttribute("entityName", TableNames.CHAT_ROOM);
    model.addAttribute("dtoFieldsNames", getDtoFieldsNames(ChatRoomDto.class));
    model.addAttribute("dtos", adminService.getAllChatRoomDto());
    return "db-table";
  }

  @GetMapping("/db/table/chatmessage")
  public String chatMessageTable(Model model) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    model.addAttribute("entityName", TableNames.CHAT_MESSAGE);
    model.addAttribute("dtoFieldsNames", getDtoFieldsNames(ChatMessageDto.class));
    model.addAttribute("dtos", adminService.getAllChatMessageDto());
    return "db-table";
  }

  @GetMapping("/db/table/filterword")
  public String filterWordTable(Model model) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    model.addAttribute("entityName", TableNames.FILTER_WORD);
    model.addAttribute("dtoFieldsNames", getDtoFieldsNames(FilterWordDto.class));
    model.addAttribute("dtos", adminService.getAllFilterWord());
    return "db-table";
  }

  @DeleteMapping("/db/table/{tableName}/{rowPk}")
  public String deleteRow(@PathVariable String tableName,
                          @PathVariable long rowPk) {
    if (tableName.equals(TableNames.ORDERER.toString())) {
      adminService.deleteOrdererRow(rowPk);
    } else if (tableName.equals(TableNames.PERFORMER.toString())) {
      adminService.deletePerformerRow(rowPk);
    } else if (tableName.equals(TableNames.ADMIN.toString())) {
      adminService.deleteAdminRow(rowPk);
    } else if (tableName.equals(TableNames.MATCHING.toString())) {
      adminService.deleteMatchingRow(rowPk);
    } else if (tableName.equals(TableNames.FILTER_WORD.toString())) {
      adminService.deleteFilterWordRow(rowPk);
    } else if (tableName.equals(TableNames.MATCH_REQUEST.toString())) {
      adminService.deleteMatchRequestRow(rowPk);
    } else if (tableName.equals(TableNames.ORDER_SHEET.toString())) {
      adminService.deleteOrderSheetRow(rowPk);
    } else if (tableName.equals(TableNames.REPORT.toString())) {
      adminService.deleteReportRow(rowPk);
    } else if (tableName.equals(TableNames.REVIEW.toString())) {
      adminService.deleteReviewRow(rowPk);
    } else if (tableName.equals(TableNames.CHAT_ROOM.toString())) {
      adminService.deleteChatRoomRow(rowPk);
    } else if (tableName.equals(TableNames.CHAT_MESSAGE.toString())) {
      adminService.deleteChatMessageRow(rowPk);
    }

    return "redirect:/admin/db/table/" + tableName.toLowerCase(Locale.ROOT);
  }

  private <T> List<String> getDtoFieldsNames(Class<T> dtoType) {
    Field[] declaredFields = dtoType.getDeclaredFields();
    List<String> fieldNames = new ArrayList<>();
    for (Field f : declaredFields) {
      fieldNames.add(f.getName());
    }
    return fieldNames;
  }
}
