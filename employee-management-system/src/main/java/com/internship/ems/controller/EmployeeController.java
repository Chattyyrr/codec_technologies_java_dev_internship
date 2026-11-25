package com.internship.ems.controller;

import com.internship.ems.model.Employee;
import com.internship.ems.model.EmploymentStatus;
import com.internship.ems.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class EmployeeController {
    private final EmployeeService service;
    public EmployeeController(EmployeeService service){this.service=service;}

    @GetMapping("/") public String root(){return "redirect:/employees";}
    @GetMapping("/login") public String login(){return "login";}

    @GetMapping("/employees") public String list(@RequestParam(required=false) String q, Model model){
        model.addAttribute("employees",service.search(q)); model.addAttribute("q",q==null?"":q);
        return "employees";
    }
    @GetMapping("/employees/{id}") public String details(@PathVariable Long id, Model model){
        model.addAttribute("employee",service.findById(id)); return "employee-details";
    }
    @GetMapping("/employees/new") public String createForm(Model model){
        Employee employee=new Employee(); employee.setStatus(EmploymentStatus.ACTIVE);
        model.addAttribute("employee",employee); model.addAttribute("pageTitle","Add employee"); return "employee-form";
    }
    @GetMapping("/employees/{id}/edit") public String editForm(@PathVariable Long id, Model model){
        model.addAttribute("employee",service.findById(id)); model.addAttribute("pageTitle","Edit employee"); return "employee-form";
    }
    @PostMapping("/employees/save") public String save(@Valid @ModelAttribute Employee employee, BindingResult result, Model model){
        if(result.hasErrors()){model.addAttribute("pageTitle",employee.getId()==null?"Add employee":"Edit employee");return "employee-form";}
        try{service.save(employee);return "redirect:/employees?saved";}
        catch(IllegalArgumentException ex){model.addAttribute("pageTitle",employee.getId()==null?"Add employee":"Edit employee");model.addAttribute("formError",ex.getMessage());return "employee-form";}
    }
    @PostMapping("/employees/{id}/delete") public String delete(@PathVariable Long id){
        service.delete(id); return "redirect:/employees?deleted";
    }
}
