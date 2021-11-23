package com.four.brothers.runtou.domain;

import com.four.brothers.runtou.domain.user.Orderer;
import com.four.brothers.runtou.domain.user.Performer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Review {

    @Id @GeneratedValue
    @Column(name = "REVIEW_ID")
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ORDERER_NUMBER")
    private Orderer orderer;

    @ManyToOne(optional = false)
    @JoinColumn(name = "PERFORMER_NUMBER")
    private Performer performer;

    @OneToOne(optional = false)
    @JoinColumn(name = "ORDER_ID")
    private Order order;

    private int score;
    private String content;
    private LocalDateTime reviewedDateTime;


    //롬복 setter 덮어쓰기
    public void setScore(int score) {
        if (score > 10 || score <= 0) {
            throw new IllegalArgumentException("리뷰 점수는 1~10 사이입니다.");
        }
        this.score = score;
    }

}
