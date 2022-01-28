package com.four.brothers.runtou.domain;

import com.four.brothers.runtou.domain.base.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Review extends BaseTimeEntity {
  @Id @GeneratedValue(strategy = GenerationType.AUTO)
  @Column
  private Long id;

  @OneToOne(fetch = FetchType.EAGER, optional = false)
  @JoinColumn(name = "MATCH_ID", nullable = false, unique = true)
  private Match match;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 20)
  private ReviewScore score;

  @Lob
  @Column(nullable = false, length = 2024)
  private String content; //CLOB 타입

  public Review(Match match, ReviewScore score, String content) {
    this.match = match;
    this.score = score;
    this.content = content;
  }

  protected void setMatch(Match match) {
    this.match = match;
  }
}
