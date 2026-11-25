package com.internship.bookstore.service;

import com.internship.bookstore.model.*;
import com.internship.bookstore.repository.CustomerRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    private final CustomerRepository customers; private final PasswordEncoder encoder;
    public CustomerService(CustomerRepository c, PasswordEncoder e){customers=c;encoder=e;}
    public void register(String name, String email, String password) {
        if(customers.findByEmail(email).isPresent()) throw new IllegalArgumentException("Email is already registered.");
        Customer c=new Customer(); c.setName(name); c.setEmail(email);
        c.setPassword(encoder.encode(password)); c.setRole(Role.CUSTOMER); customers.save(c);
    }
}
