package com.example.crms.services;

import com.example.crms.models.Category;
import com.example.crms.models.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.crms.repositories.StatusRepository;

import java.util.List;

@Service
public class StatusService {

    private final StatusRepository statusRepository;

    @Autowired
    public StatusService(StatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    // Method to get status by ID
    public Status getStatusById(Integer statusId) {
        return statusRepository.findById(statusId)
                .orElseThrow(() -> new RuntimeException("Status not found with id: " + statusId));
    }
    public List<Status> getAllStatus() {
        return statusRepository.findAll();
    }
    // Other methods related to status if needed
}
