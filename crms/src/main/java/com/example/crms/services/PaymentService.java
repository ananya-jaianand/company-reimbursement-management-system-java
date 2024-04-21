package com.example.crms.services;

import com.example.crms.models.Payment;
import com.example.crms.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Transactional
    public Payment createPayment(Payment payment) {
        return paymentRepository.save(payment);
    }

    @Transactional(readOnly = true)
    public List<Payment> getAllPaymentsByEmployeeId(Integer employeeId) {
        return paymentRepository.findAllByReimbursement_EmployeeId(employeeId);
    }
}
