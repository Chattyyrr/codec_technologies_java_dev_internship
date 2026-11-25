package com.internship.bookstore.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "customer_orders")
public class CustomerOrder {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false) private Customer customer;
    @Column(nullable = false, precision = 10, scale = 2) private BigDecimal total;
    private LocalDateTime createdAt = LocalDateTime.now();
    @Enumerated(EnumType.STRING) private PaymentStatus paymentStatus = PaymentStatus.PENDING;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items = new ArrayList<>();

    public void addItem(OrderItem item){items.add(item); item.setOrder(this);}
    public Long getId(){return id;} public Customer getCustomer(){return customer;} public void setCustomer(Customer c){customer=c;}
    public BigDecimal getTotal(){return total;} public void setTotal(BigDecimal t){total=t;}
    public LocalDateTime getCreatedAt(){return createdAt;} public PaymentStatus getPaymentStatus(){return paymentStatus;}
    public void setPaymentStatus(PaymentStatus s){paymentStatus=s;} public List<OrderItem> getItems(){return items;}
}
