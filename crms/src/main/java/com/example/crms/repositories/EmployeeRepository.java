package com.example.crms.repositories;

import com.example.crms.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    List<Employee> findByManagerId(Integer managerId);
    Integer findManagerIdByEmployeeId(Integer employeeId);
    boolean existsById(Integer employeeId);
    Employee findByEmployeeId(Integer employeeId);
}