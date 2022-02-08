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
  @JoinColumn(name = "MATCH_ID", nullable = false)
  private Match match;

  @OneToOne(fetch = FetchType.EAGER, optional = false)
  @JoinColumn(name = "PERFORMER_ID", nullable = false)
  private Performer performer;

  public MatchRequest(Match match, Performer performer) {
    this.match = match;
    this.performer = performer;
  }

  protected void setMatch(Match match) {
    this.match = match;
  }

  protected void setPerformer(Performer performer) {
    this.performer = performer;
  }

}
