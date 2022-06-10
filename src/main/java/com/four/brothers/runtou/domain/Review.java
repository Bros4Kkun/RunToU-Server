package com.four.brothers.runtou.domain;

import com.four.brothers.runtou.domain.base.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Review extends BaseTimeEntity {
  @Id @GeneratedValue(strategy = GenerationType.AUTO)
  @Column
  private Long id;

  @OneToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "MATCHING_ID", nullable = false, unique = true)
  private Matching matching;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 20)
  private ReviewScore score;

  @Lob
  @Column(nullable = false, length = 2024)
  private String content; //CLOB 타입

  public Review(Matching matching, ReviewScore score, String content) {
    this.matching = matching;
    this.score = score;
    this.content = content;
  }

  protected void setMatching(Matching matching) {
    this.matching = matching;
  }
}
