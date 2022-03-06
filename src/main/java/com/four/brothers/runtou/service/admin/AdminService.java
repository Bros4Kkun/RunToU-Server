package com.four.brothers.runtou.service.admin;

import com.four.brothers.runtou.domain.*;
import com.four.brothers.runtou.dto.LoginDto;
import com.four.brothers.runtou.dto.UserRole;
import com.four.brothers.runtou.dto.model.*;
import com.four.brothers.runtou.repository.user.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.four.brothers.runtou.dto.LoginDto.*;

@RequiredArgsConstructor
@Service
public class AdminService {
  private final PasswordEncoder passwordEncoder;
  private final OrdererRepository ordererRepository;
  private final PerformerRepository performerRepository;
  private final AdminRepository adminRepository;
  private final OrderSheetRepository orderSheetRepository;
  private final ReviewRepository reviewRepository;
  private final MatchingRepository matchingRepository;
  private final FilterWordRepository filterWordRepository;
  private final ReportRepository reportRepository;
  private final MatchRequestRepository matchRequestRepository;

  @Transactional
  public List<OrdererDto> getAllOrderer() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    List<Orderer> result = ordererRepository.findAll(1, 100);
    return toDto(result, OrdererDto.class);
  }

  @Transactional
  public List<PerformerDto> getAllPerformer() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    List<Performer> result = performerRepository.findAll(1, 100);
    return toDto(result, PerformerDto.class);
  }

  @Transactional
  public List<AdminDto> getAllAdmin() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    List<Admin> result = adminRepository.findAll(1, 100);
    return toDto(result, AdminDto.class);
  }

  @Transactional
  public List<OrderSheetDto> getAllOrderSheet() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    List<OrderSheet> result = orderSheetRepository.findAll(1, 100);
    return toDto(result, OrderSheetDto.class);
  }

  @Transactional
  public List<ReviewDto> getAllReview() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    List<Review> result = reviewRepository.findAll(1, 100);
    return toDto(result, ReviewDto.class);
  }

  @Transactional
  public List<MatchingDto> getAllMatching() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    List<Matching> result = matchingRepository.findAll(1, 100);
    return toDto(result, MatchingDto.class);
  }

  @Transactional
  public List<FilterWordDto> getAllFilterWord() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    List<FilterWord> result = filterWordRepository.findAll(1, 100);
    return toDto(result, FilterWordDto.class);
  }

  @Transactional
  public List<ReportDto> getAllReport() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    List<Report> result = reportRepository.findAll(1, 100);
    return toDto(result, ReportDto.class);
  }

  @Transactional
  public List<MatchRequestDto> getAllMatchRequestDto() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    List<MatchRequest> result = matchRequestRepository.findAll(1, 100);
    return toDto(result, MatchRequestDto.class);
  }

  @Transactional
  public void deleteOrdererRow(long rowPk) {
    ordererRepository.deleteOrdererById(rowPk);
  }

  @Transactional
  public void deletePerformerRow(long rowPk) {
    performerRepository.deletePerformerById(rowPk);
  }

  @Transactional
  public void deleteAdminRow(long rowPk) {
    adminRepository.deleteAdminById(rowPk);
  }

  @Transactional
  public void deleteOrderSheetRow(long rowPk) {
    orderSheetRepository.deleteOrderSheetById(rowPk);
  }

  @Transactional
  public void deleteReviewRow(long rowPk) {
    reviewRepository.deleteReviewById(rowPk);
  }

  @Transactional
  public void deleteMatchingRow(long rowPk) {
    matchingRepository.deleteMatchingById(rowPk);
  }

  @Transactional
  public void deleteFilterWordRow(long rowPk) {
    filterWordRepository.deleteFilterWordById(rowPk);
  }

  @Transactional
  public void deleteReportRow(long rowPk) {
    reportRepository.deleteReportById(rowPk);
  }

  @Transactional
  public void deleteMatchRequestRow(long rowPk) {
    matchRequestRepository.deleteMatchingRequestById(rowPk);
  }

  /**
   * 어드민 로그인 메서드
   * @param request
   * @return
   */
  @Transactional
  public LoginUser login(LoginRequest request) {
    String accountId = request.getAccountId();
    String rawPassword = request.getRawPassword();
    Optional<Admin> admin = adminRepository.findAdminByAccountId(accountId);

    if (admin.isEmpty()) {
      return null;
    }

    if (!passwordEncoder.matches(rawPassword, admin.get().getPassword())) {
      return null;
    }

    return new LoginUser(admin.get().getAccountId(),
          admin.get().getNickname(),
          admin.get().getPhoneNumber(),
          admin.get().getAccountNumber(),
          UserRole.ADMIN);
  }

  private <F,T extends ModelDto> List<T> toDto(List<F> entityList, Class<T> dtoType) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
    List<T> result = new ArrayList<>();
    for (F entity : entityList) {
      Constructor<T> declaredConstructor = dtoType.getDeclaredConstructor(new Class[]{});
      T t = declaredConstructor.newInstance();
      result.add((T) t.toDtoFromEntity(entity));
    }

    return result;
  }
}
