package com.internship.ems.service;

import com.internship.ems.model.Employee;
import com.internship.ems.repository.EmployeeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class EmployeeService {
    private final EmployeeRepository employees;
    public EmployeeService(EmployeeRepository employees){this.employees=employees;}

    public List<Employee> findAll(){return employees.findAll();}
    public List<Employee> search(String q){
        return q==null || q.isBlank()?findAll():
            employees.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrEmployeeCodeContainingIgnoreCase(q,q,q);
    }
    public Employee findById(Long id){return employees.findById(id).orElseThrow(()->new IllegalArgumentException("Employee record not found."));}

    @Transactional public Employee save(Employee employee) {
        boolean duplicateCode = employee.getId()==null
            ? employees.existsByEmployeeCode(employee.getEmployeeCode())
            : employees.existsByEmployeeCodeAndIdNot(employee.getEmployeeCode(),employee.getId());
        boolean duplicateEmail = employee.getId()==null
            ? employees.existsByEmail(employee.getEmail())
            : employees.existsByEmailAndIdNot(employee.getEmail(),employee.getId());
        if(duplicateCode) throw new IllegalArgumentException("Employee ID is already in use.");
        if(duplicateEmail) throw new IllegalArgumentException("An employee with this email already exists.");
        return employees.save(employee);
    }
    public void delete(Long id){employees.delete(findById(id));}
}
