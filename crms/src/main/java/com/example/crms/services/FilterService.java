package com.example.crms.services;

import com.example.crms.FilterStrategy.Filter;
import com.example.crms.models.Reimbursement;
import com.example.crms.repositories.ReimbursementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FilterService implements Filter {


    private final ReimbursementRepository reimbursementRepository;

    @Autowired
    public FilterService(ReimbursementRepository reimbursementRepository) {
        this.reimbursementRepository = reimbursementRepository;
    }

    @Override
    public Iterable<Reimbursement> fetchReimbursements(Integer employeeId,Integer status, Integer category,Integer employerId) {
        // Implement logic to fetch pending reimbursements
        return reimbursementRepository.findReimbursements(employeeId, status, category,employerId);
    }
}
