package com.internship.bookstore.service;

import com.internship.bookstore.model.*;
import com.internship.bookstore.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.*;

@Service
public class StoreService {
    private final BookRepository books; private final OrderRepository orders; private final CustomerRepository customers;
    public StoreService(BookRepository b, OrderRepository o, CustomerRepository c){books=b;orders=o;customers=c;}
    public List<Book> search(String q){return q==null||q.isBlank()?books.findAll():books.findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(q,q);}
    public Book getBook(Long id){return books.findById(id).orElseThrow(()->new IllegalArgumentException("Book not found."));}
    public List<CustomerOrder> ordersFor(String email){return orders.findByCustomerEmailOrderByCreatedAtDesc(email);}
    @Transactional public CustomerOrder checkout(String email, Map<Long,Integer> cart) {
        if(cart.isEmpty()) throw new IllegalArgumentException("Your cart is empty.");
        Customer customer=customers.findByEmail(email).orElseThrow();
        CustomerOrder order=new CustomerOrder(); order.setCustomer(customer);
        BigDecimal total=BigDecimal.ZERO;
        for(var entry:cart.entrySet()) {
            Book book=getBook(entry.getKey()); int qty=entry.getValue();
            if(qty<1 || book.getStock()<qty) throw new IllegalArgumentException("Insufficient stock for: "+book.getTitle());
            book.setStock(book.getStock()-qty);
            OrderItem item=new OrderItem(); item.setBook(book); item.setQuantity(qty); item.setUnitPrice(book.getPrice());
            order.addItem(item); total=total.add(book.getPrice().multiply(BigDecimal.valueOf(qty)));
        }
        order.setTotal(total); return orders.save(order);
    }
    @Transactional public void markPaid(Long orderId, String email) {
        CustomerOrder order=orders.findById(orderId).orElseThrow();
        if(!order.getCustomer().getEmail().equals(email)) throw new IllegalArgumentException("Order not found.");
        order.setPaymentStatus(PaymentStatus.PAID);
    }
}
