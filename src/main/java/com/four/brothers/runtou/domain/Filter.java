package com.four.brothers.runtou.domain;

import javax.persistence.*;

@Entity
public class Filter {

    @Id
    @GeneratedValue
    @Column(name = "FILTER_ID")
    private Long id;

    private String word;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }
}
