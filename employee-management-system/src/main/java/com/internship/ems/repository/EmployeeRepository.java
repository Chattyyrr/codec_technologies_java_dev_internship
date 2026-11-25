package com.internship.ems.repository;

import com.internship.ems.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    boolean existsByEmployeeCode(String employeeCode);
    boolean existsByEmail(String email);
    boolean existsByEmployeeCodeAndIdNot(String employeeCode, Long id);
    boolean existsByEmailAndIdNot(String email, Long id);
    List<Employee> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrEmployeeCodeContainingIgnoreCase(
        String firstName, String lastName, String employeeCode);
}
