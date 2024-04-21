package com.example.crms.services;

import com.example.crms.models.Reimbursement;
import com.example.crms.models.Status;
import com.example.crms.repositories.ReimbursementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
public class ReimbursementService {

    private final ReimbursementRepository reimbursementRepository;
    private final ReimbursementCalculator reimbursementCalculator;

    private final StatusService statusService;

    @Autowired
    public ReimbursementService(ReimbursementRepository reimbursementRepository,StatusService statusService,ReimbursementCalculator reimbursementCalculator) {
        this.reimbursementRepository = reimbursementRepository;
        this.statusService=statusService;
        this.reimbursementCalculator = reimbursementCalculator;
    }
    public Iterable<Reimbursement> getReimbursements(Integer employeeId, Integer status, Integer category,Integer employerId) {
        return reimbursementRepository.findReimbursements(employeeId, status, category,employerId);
    }
    @Transactional
    public void createReimbursement(Reimbursement reimbursement) {
        reimbursementRepository.save(reimbursement);
    }

    @Transactional
    public void updateReimbursement(Reimbursement reimbursement) {
        reimbursementRepository.save(reimbursement);
    }

    @Transactional
    public void deleteReimbursement(Reimbursement reimbursement) {
        reimbursementRepository.delete(reimbursement);
    }

    @Transactional(readOnly = true)
    public List<Reimbursement> getPendingReimbursementsByEmployeeId(Integer employeeId) {
        return reimbursementRepository.findByEmployeeIdAndStatusId(employeeId, 1);
    }

    @Transactional(readOnly = true)
    public List<Reimbursement> getApprovedReimbursementsByEmployeeId(Integer employeeId) {
        return reimbursementRepository.findByEmployeeIdAndStatusId(employeeId, 2);
    }

    @Transactional(readOnly = true)
    public List<Reimbursement> getDeniedReimbursementsByEmployeeId(Integer employeeId) {
        // Assuming status IDs for denied reimbursements are 3, 4, 5, and 6
        List<Integer> deniedStatusIds = Arrays.asList(3, 4, 5, 6);
        return reimbursementRepository.findByEmployeeIdAndStatusIdIn(employeeId, deniedStatusIds);
    }

    @Transactional(readOnly = true)
    public Reimbursement getReimbursementByRequestId(Integer requestId) {

        return reimbursementRepository.findReimbursementByRequestId(requestId);
    }

    public Reimbursement changeReimbursementStatus(Integer requestId, Integer newStatus) {
        Reimbursement reimbursement = reimbursementRepository.findById(requestId).orElse(null);
        if (reimbursement != null) {
            reimbursement.setStatusId(newStatus);
            Status status = statusService.getStatusById(reimbursement.getStatusId());
            reimbursement.setStatus(status);
            // Save updated reimbursement
            return reimbursementRepository.save(reimbursement);
        }
        return null;
    }

    public double calculateEmployeeReimbursement(double amountRequested, double categoryLimit, double percentage) {
        return reimbursementCalculator.calculateReimbursement(amountRequested, categoryLimit, percentage);
    }
}
