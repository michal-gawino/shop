package com.michal.entities;

import javax.persistence.*;
import java.util.List;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "`order`")
public class Order extends Auditor {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private OrderDetails orderDetails;

    @ManyToMany
    @JoinTable(
            name = "order_product",
            joinColumns = { @JoinColumn(name = "order_id") },
            inverseJoinColumns = { @JoinColumn(name = "product_id") })
    private List<Product> products;

    public Order() {
    }

    public Order(User user, OrderDetails orderDetails, List<Product> products) {
        this.user = user;
        this.orderDetails = orderDetails;
        this.products = products;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public OrderDetails getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(OrderDetails orderDetails) {
        this.orderDetails = orderDetails;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
