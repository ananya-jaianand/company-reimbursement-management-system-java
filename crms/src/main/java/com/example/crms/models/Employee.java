package com.example.crms.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Employee")
@Data
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Employee_Id")
    private Integer employeeId;

    @Column(name = "First_Name", nullable = false)
    private String firstName;

    @Column(name = "Last_Name", nullable = false)
    private String lastName;

    @Column(name = "Phone_No", nullable = false, length = 15)
    private String phoneNo;

    @Column(name = "Email", length = 100)
    private String email;

    @Column(name = "Department", nullable = false)
    private String department;

    @Column(name = "Salary", nullable = false)
    private Integer salary;

    @Column(name = "Manager_Id")
    private Integer managerId;

    @ManyToOne
    @JoinColumn(name = "Manager_Id", referencedColumnName = "Employee_Id", insertable = false, updatable = false)
    private Employee manager;

    public Employee() {

    }

    public Employee(String firstName, String lastName, String phoneNo, String email, String department, Integer salary, Integer managerId) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNo = phoneNo;
        this.email = email;
        this.department = department;
        this.salary = salary;
        this.managerId = managerId;
    }
}