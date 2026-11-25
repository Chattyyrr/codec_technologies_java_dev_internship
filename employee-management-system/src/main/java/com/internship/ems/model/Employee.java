package com.internship.ems.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "employees")
public class Employee {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank @Column(nullable = false, unique = true, length = 20)
    private String employeeCode;

    @NotBlank @Column(nullable = false)
    private String firstName;

    @NotBlank @Column(nullable = false)
    private String lastName;

    @NotBlank @Email @Column(nullable = false, unique = true)
    private String email;

    @NotBlank private String phone;
    @NotBlank private String department;
    @NotBlank private String designation;

    @NotNull @DecimalMin("0.0") @Column(precision = 12, scale = 2)
    private BigDecimal salary;

    @NotNull private LocalDate dateOfJoining;

    @Enumerated(EnumType.STRING) @Column(nullable = false)
    private EmploymentStatus status = EmploymentStatus.ACTIVE;

    public Long getId(){return id;} public void setId(Long id){this.id=id;}
    public String getEmployeeCode(){return employeeCode;} public void setEmployeeCode(String v){employeeCode=v;}
    public String getFirstName(){return firstName;} public void setFirstName(String v){firstName=v;}
    public String getLastName(){return lastName;} public void setLastName(String v){lastName=v;}
    public String getEmail(){return email;} public void setEmail(String v){email=v;}
    public String getPhone(){return phone;} public void setPhone(String v){phone=v;}
    public String getDepartment(){return department;} public void setDepartment(String v){department=v;}
    public String getDesignation(){return designation;} public void setDesignation(String v){designation=v;}
    public BigDecimal getSalary(){return salary;} public void setSalary(BigDecimal v){salary=v;}
    public LocalDate getDateOfJoining(){return dateOfJoining;} public void setDateOfJoining(LocalDate v){dateOfJoining=v;}
    public EmploymentStatus getStatus(){return status;} public void setStatus(EmploymentStatus v){status=v;}
    public String getFullName(){return firstName+" "+lastName;}
}
