package com.example.crms.repositories;

import com.example.crms.models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    Payment findByRequestId(Integer requestId);
}