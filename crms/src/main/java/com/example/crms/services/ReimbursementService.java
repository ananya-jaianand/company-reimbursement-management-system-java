package com.example.crms.services;

import com.example.crms.models.Reimbursement;
import com.example.crms.repositories.ReimbursementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReimbursementService {

    private final ReimbursementRepository reimbursementRepository;

    @Autowired
    public ReimbursementService(ReimbursementRepository reimbursementRepository) {
        this.reimbursementRepository = reimbursementRepository;
    }

    @Transactional
    public void createReimbursement(Reimbursement reimbursement) {
        reimbursementRepository.save(reimbursement);
    }
}
