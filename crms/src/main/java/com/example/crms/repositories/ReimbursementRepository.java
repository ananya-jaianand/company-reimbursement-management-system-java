package com.example.crms.repositories;

import com.example.crms.models.Reimbursement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReimbursementRepository extends JpaRepository<Reimbursement, Integer> {
    Reimbursement findByRequestId(Integer requestId);
}
