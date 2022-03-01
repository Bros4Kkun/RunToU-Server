package com.four.brothers.runtou.controller;

import com.four.brothers.runtou.domain.TableNames;
import com.four.brothers.runtou.dto.model.*;
import com.four.brothers.runtou.service.admin.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/admin")
@Controller
public class AdminController {

  private final AdminService adminService;

  @GetMapping("/db")
  public String main() {
    return "db-main";
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

  @GetMapping("/db/table/filterword")
  public String filterWordTable(Model model) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    model.addAttribute("entityName", TableNames.FILTER_WORD);
    model.addAttribute("dtoFieldsNames", getDtoFieldsNames(FilterWordDto.class));
    model.addAttribute("dtos", adminService.getAllFilterWord());
    return "db-table";
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
