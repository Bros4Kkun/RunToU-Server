package com.four.brothers.runtou.domain;

import com.four.brothers.runtou.domain.user.Orderer;
import com.four.brothers.runtou.domain.user.Performer;

import javax.persistence.*;
import java.time.LocalDateTime;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Orderer getOrderer() {
        return orderer;
    }

    public void setOrderer(Orderer orderer) {
        this.orderer = orderer;
    }

    public Performer getPerformer() {
        return performer;
    }

    public void setPerformer(Performer performer) {
        this.performer = performer;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        if (score > 10 || score <= 0) {
            throw new IllegalArgumentException("리뷰 점수는 1~10 사이입니다.");
        }
        this.score = score;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getReviewedDateTime() {
        return reviewedDateTime;
    }

    public void setReviewedDateTime(LocalDateTime reviewedDateTime) {
        this.reviewedDateTime = reviewedDateTime;
    }
}
