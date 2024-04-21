package com.example.crms.repositories;

import com.example.crms.models.Employee;
import com.example.crms.models.Reimbursement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    boolean existsById(Integer employeeId);
    Employee findByEmployeeId(Integer employeeId);

    @Query("SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END FROM Employee e WHERE e.managerId = :employeeId")
    boolean existsByManagerId(@Param("employeeId") Integer employeeId);

    @Query("SELECT e FROM Employee e WHERE e.managerId = :employerId")
    List<Employee> findAllEmployees(Integer employerId);

    Iterable<Integer> findEmployeeIdByManagerId(Integer managerId);

    @Query("SELECT e FROM Reimbursement r INNER JOIN Employee e ON r.employeeId = e.employeeId WHERE r.requestId = :reimbursementId")
    Employee findByReimbursementId(Integer reimbursementId);

    List<Employee> findByManagerId(Integer managerId);
}