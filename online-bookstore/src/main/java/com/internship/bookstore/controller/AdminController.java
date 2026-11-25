package com.internship.bookstore.controller;

import com.internship.bookstore.model.Book;
import com.internship.bookstore.repository.BookRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final BookRepository books;
    public AdminController(BookRepository books){this.books=books;}
    @GetMapping("/books") public String list(Model model){model.addAttribute("books",books.findAll());return "admin-books";}
    @GetMapping("/books/new") public String form(Model model){model.addAttribute("book",new Book());return "book-form";}
    @PostMapping("/books") public String save(@Valid @ModelAttribute Book book,BindingResult result){
        if(result.hasErrors())return "book-form";books.save(book);return "redirect:/admin/books";
    }
    @PostMapping("/books/{id}/delete") public String delete(@PathVariable Long id){books.deleteById(id);return "redirect:/admin/books";}
}
