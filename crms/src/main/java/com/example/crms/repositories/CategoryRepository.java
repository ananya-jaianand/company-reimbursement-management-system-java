package com.example.crms.repositories;

import com.example.crms.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    String findCategoryNameByCategoryId(Integer categoryId);
    Integer findMaximumLimitByCategoryId(Integer categoryId);
    Integer findCategoryPercentageByCategoryId(Integer categoryId);
}