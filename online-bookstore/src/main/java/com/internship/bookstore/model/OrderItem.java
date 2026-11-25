package com.internship.bookstore.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
public class OrderItem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false) private Book book;
    @ManyToOne(optional = false) private CustomerOrder order;
    private int quantity;
    @Column(precision = 10, scale = 2) private BigDecimal unitPrice;
    public Long getId(){return id;} public Book getBook(){return book;} public void setBook(Book b){book=b;}
    public CustomerOrder getOrder(){return order;} public void setOrder(CustomerOrder o){order=o;}
    public int getQuantity(){return quantity;} public void setQuantity(int q){quantity=q;}
    public BigDecimal getUnitPrice(){return unitPrice;} public void setUnitPrice(BigDecimal p){unitPrice=p;}
}
