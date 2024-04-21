package com.example.crms.services;

import com.example.crms.models.Employee;
import com.example.crms.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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

    // Get an employer by employee ID
    public Employee getEmployerById(Integer employeeId) {
        // Check if the employee is an employer
        boolean isEmployer = employeeRepository.existsByManagerId(employeeId);
        if (isEmployer) {
            // If the employee is an employer, retrieve their details
            return employeeRepository.findByEmployeeId(employeeId);
        } else {
            // If not an employer, return null or throw an exception as per your application logic
            return null;
        }
    }
    //Get list of employees under an employer
    public List<Employee> getAllEmployees(Integer employerId){
        return employeeRepository.findAllEmployees(employerId);
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }
    // Add an employee
    public Employee addEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    // Update an employee
    public Employee updateEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    // Delete an employee by ID
    public void deleteEmployee(Integer employeeId) {
        employeeRepository.deleteById(employeeId);
    }

    public void notifyEmployees(Employee deletedEmployee) {
        List<Employee> allEmployees = employeeRepository.findAll();
        for (Employee employee : allEmployees) {
            if (employee.getManagerId() != null && employee.getManagerId().equals(deletedEmployee.getEmployeeId())) {
                employee.setManagerId(null); // Set manager ID to null or another appropriate value
                employeeRepository.save(employee);
            }
        }
    }

    public List<Employee> getEmployeesByManagerId(Integer managerId) {
        return employeeRepository.findByManagerId(managerId);
    }
}
