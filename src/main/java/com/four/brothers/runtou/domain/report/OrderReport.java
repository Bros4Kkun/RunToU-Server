package com.four.brothers.runtou.domain.report;

import com.four.brothers.runtou.domain.Order;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@Setter
@NoArgsConstructor
@Entity
@DiscriminatorValue("ORDER_REPORT")
public class OrderReport extends Report {

    @ManyToOne(optional = false)
    @JoinColumn(name = "ORDER_ID")
    private Order reportedOrder;

}
