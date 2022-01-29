package com.four.brothers.runtou.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@NoArgsConstructor
@Entity
public class FilterWord {
  @Id @GeneratedValue
  @Column
  private Long id;

  @Column(length = 100, nullable = false)
  private String word;
}
