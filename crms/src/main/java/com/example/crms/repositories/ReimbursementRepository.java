package com.example.crms.repositories;

import com.example.crms.models.Reimbursement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReimbursementRepository extends JpaRepository<Reimbursement, Integer> {
    @Query("SELECT r FROM Reimbursement r JOIN r.employee e WHERE r.employeeId = :employeeId AND r.statusId = :status AND r.categoryId = :category AND e.managerId = :employerId")
    Iterable<Reimbursement> findReimbursements(Integer employeeId, Integer status, Integer category,Integer employerId);

    List<Reimbursement> findByEmployeeIdAndStatusId(Integer employeeId, Integer statusId);
    List<Reimbursement> findByEmployeeIdAndStatusIdIn(Integer employeeId, List<Integer> statusIds);

    Reimbursement findReimbursementByRequestId(Integer requestId);
    @Query("SELECT r FROM Reimbursement r INNER JOIN Employee e ON r.employeeId = e.employeeId WHERE e.managerId = :employerId")
    Iterable<Reimbursement> getAllReimbursements(Integer employerId);
}