package com.four.brothers.runtou.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Filter {

    @Id
    @GeneratedValue
    @Column(name = "FILTER_ID")
    private Long id;

    private String word;

}
