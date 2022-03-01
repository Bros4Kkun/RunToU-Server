package com.four.brothers.runtou.service.admin;

import com.four.brothers.runtou.domain.*;
import com.four.brothers.runtou.dto.model.*;
import com.four.brothers.runtou.repository.user.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AdminService {
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
