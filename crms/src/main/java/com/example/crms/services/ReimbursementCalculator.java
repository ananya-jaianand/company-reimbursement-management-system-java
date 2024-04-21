package com.example.crms.services;

import org.springframework.stereotype.Service;

@Service
public class ReimbursementCalculator {

    // Singleton instance
    private static ReimbursementCalculator instance;

    // Private constructor to prevent instantiation
    private ReimbursementCalculator() {}

    // Method to get the singleton instance
    public static synchronized ReimbursementCalculator getInstance() {
        if (instance == null) {
            instance = new ReimbursementCalculator();
        }
        return instance;
    }

    // Method to perform reimbursement calculation
    public double calculateReimbursement(double amountRequested, double categoryLimit, double percentage) {
        // Apply reimbursement calculation logic here
        // For example:
        double reimbursementAmount = amountRequested * percentage / 100.0;
        if (reimbursementAmount > categoryLimit) {
            reimbursementAmount = categoryLimit; // Apply category limit
        }
        return reimbursementAmount;
    }
}
