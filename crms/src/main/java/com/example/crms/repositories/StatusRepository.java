package com.example.crms.repositories;

import com.example.crms.models.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepository extends JpaRepository<Status, Integer> {
    String findStatusMessageByStatusId(Integer statusId);
}