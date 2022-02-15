package com.four.brothers.runtou.domain;

import com.four.brothers.runtou.domain.base.BaseTimeEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class MatchRequest extends BaseTimeEntity {
  @Id @GeneratedValue(strategy = GenerationType.AUTO)
  @Column
  private Long id;

  @ManyToOne(fetch = FetchType.EAGER, optional = false)
  @JoinColumn(name = "MATCHING_ID", nullable = false)
  private Matching matching;

  @OneToOne(fetch = FetchType.EAGER, optional = false)
  @JoinColumn(name = "PERFORMER_ID", nullable = false)
  private Performer performer;

  public MatchRequest(Matching matching, Performer performer) {
    this.matching = matching;
    this.performer = performer;
  }

  protected void setMatching(Matching matching) {
    this.matching = matching;
  }

  protected void setPerformer(Performer performer) {
    this.performer = performer;
  }

}
