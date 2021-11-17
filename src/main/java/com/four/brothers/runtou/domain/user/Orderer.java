package com.four.brothers.runtou.domain.user;

import com.four.brothers.runtou.domain.Order;
import com.four.brothers.runtou.domain.Review;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("ORDERER")
public class Orderer extends User {

    private int orderCount;

    @OneToOne(mappedBy = "orderer")
    private Order order;

    @OneToMany(mappedBy = "orderer", fetch = FetchType.LAZY)
    private List<Review> reviews = new ArrayList<Review>();

    public Orderer() { }

    public Orderer(int orderCount) {
        this.orderCount = orderCount;
    }

    public int getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(int orderCount) {
        this.orderCount = orderCount;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }
}
