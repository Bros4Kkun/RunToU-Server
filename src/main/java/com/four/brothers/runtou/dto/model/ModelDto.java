package com.four.brothers.runtou.dto.model;

public interface ModelDto<T, E> {

  /**
   * Entity 객체의 각 필드값을 Dto 객체에 복사하는 메서드
   * @param entity
   * @return
   */
  public T toDtoFromEntity(E entity);

  public String getFieldValueByName(String fieldName);
}
