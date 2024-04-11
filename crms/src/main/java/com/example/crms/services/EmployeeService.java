package com.example.crms.services;

import com.example.crms.models.Employee;
import com.example.crms.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    // Check if an employee exists by employee ID
    public boolean existsById(Integer employeeId) {
        return employeeRepository.existsById(employeeId);
    }

    // Get an employee by employee ID
    public Employee getEmployeeById(Integer employeeId) {
        return employeeRepository.findByEmployeeId(employeeId);
    }
}
