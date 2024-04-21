package com.example.crms.FilterStrategy;

import com.example.crms.models.Reimbursement;

import java.util.List;

public interface Filter{

    Iterable<Reimbursement> fetchReimbursements(Integer employeeId, Integer status, Integer category,Integer employerId);
}
