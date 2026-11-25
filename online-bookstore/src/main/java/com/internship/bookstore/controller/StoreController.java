package com.internship.bookstore.controller;

import com.internship.bookstore.model.Book;
import com.internship.bookstore.repository.BookRepository;
import com.internship.bookstore.service.*;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@Controller
public class StoreController {
    private final StoreService store; private final CustomerService customers; private final BookRepository books;
    public StoreController(StoreService s, CustomerService c, BookRepository b){store=s;customers=c;books=b;}
    @GetMapping("/") public String home(@RequestParam(required=false) String q, Model model){
        model.addAttribute("books",store.search(q)); model.addAttribute("q",q==null?"":q); return "index";
    }
    @GetMapping("/books/{id}") public String detail(@PathVariable Long id, Model model){model.addAttribute("book",store.getBook(id));return "book";}
    @GetMapping("/login") public String login(){return "login";}
    @GetMapping("/register") public String registerForm(Model model){model.addAttribute("name","");model.addAttribute("email","");return "register";}
    @PostMapping("/register") public String register(@RequestParam String name,@RequestParam String email,@RequestParam String password,Model model){
        try{customers.register(name,email,password);return "redirect:/login?registered";}
        catch(IllegalArgumentException ex){model.addAttribute("error",ex.getMessage());model.addAttribute("name",name);model.addAttribute("email",email);return "register";}
    }
    @PostMapping("/cart/add/{id}") public String add(@PathVariable Long id,@RequestParam(defaultValue="1") int quantity,HttpSession session){
        if(quantity<1) quantity=1;
        store.getBook(id); Map<Long,Integer> cart=cart(session);
        cart.merge(id,quantity,Integer::sum); session.setAttribute("cart",cart); return "redirect:/cart";
    }
    @GetMapping("/cart") public String cart(HttpSession session,Model model){
        Map<Long,Integer> cart=cart(session); List<Map<String,Object>> lines=new ArrayList<>(); java.math.BigDecimal total=java.math.BigDecimal.ZERO;
        for(var e:cart.entrySet()){Book b=store.getBook(e.getKey()); java.math.BigDecimal line=b.getPrice().multiply(java.math.BigDecimal.valueOf(e.getValue())); total=total.add(line);
            lines.add(Map.of("book",b,"quantity",e.getValue(),"lineTotal",line));}
        model.addAttribute("lines",lines);model.addAttribute("total",total);return "cart";
    }
    @PostMapping("/cart/update") public String update(@RequestParam Long bookId,@RequestParam int quantity,HttpSession session){
        Map<Long,Integer> cart=cart(session); if(quantity<=0)cart.remove(bookId);else cart.put(bookId,quantity);session.setAttribute("cart",cart);return "redirect:/cart";
    }
    @PostMapping("/checkout") public String checkout(org.springframework.security.core.Authentication auth,HttpSession session,Model model){
        try{var order=store.checkout(auth.getName(),cart(session));session.setAttribute("lastOrderId",order.getId());session.setAttribute("cart",new HashMap<Long,Integer>());return "redirect:/payment/"+order.getId();}
        catch(IllegalArgumentException ex){model.addAttribute("error",ex.getMessage());return cart(session,model);}
    }
    @GetMapping("/payment/{id}") public String payment(@PathVariable Long id,org.springframework.security.core.Authentication auth,Model model){
        var order=store.ordersFor(auth.getName()).stream().filter(o->o.getId().equals(id)).findFirst().orElseThrow();
        model.addAttribute("order",order);return "payment";
    }
    @PostMapping("/payment/{id}/demo-success") public String demoPayment(@PathVariable Long id,org.springframework.security.core.Authentication auth){
        store.markPaid(id,auth.getName());return "redirect:/orders?paid";
    }
    @GetMapping("/orders") public String orders(org.springframework.security.core.Authentication auth,Model model){
        model.addAttribute("orders",store.ordersFor(auth.getName()));return "orders";
    }
    private Map<Long,Integer> cart(HttpSession s){
        Object value=s.getAttribute("cart");
        if(value instanceof Map<?,?>) return (Map<Long,Integer>)value;
        Map<Long,Integer> cart=new LinkedHashMap<>();s.setAttribute("cart",cart);return cart;
    }
}
