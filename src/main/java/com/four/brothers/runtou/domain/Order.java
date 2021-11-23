package com.four.brothers.runtou.domain;

import com.four.brothers.runtou.domain.report.OrderReport;
import com.four.brothers.runtou.domain.user.Orderer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Order {

    @Id @GeneratedValue
    @Column(name = "ORDER_ID")
    private Long id;

    @OneToOne(optional = false)
    @JoinColumn(name = "ORDERER_NUMBER")
    private Orderer orderer;

    @OneToOne(mappedBy = "orderer")
    private Match match;

    @OneToMany(mappedBy = "reportedOrder", fetch = FetchType.LAZY)
    private List<OrderReport> orderReports = new ArrayList<OrderReport>();

    @Enumerated(EnumType.STRING)
    private OrderCategory category;

    private String title;
    private String content;
    private int cost;
    private boolean isPayed;
    private LocalDateTime orderDateTime;
    private LocalDateTime modifiedDateTime;

}
