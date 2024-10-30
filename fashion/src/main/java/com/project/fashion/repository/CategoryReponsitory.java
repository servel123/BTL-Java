package com.project.fashion.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.fashion.model.Category;

public interface CategoryReponsitory extends JpaRepository<Category, Long> {
    List<Category> findTop2ByOrderByCategoryIdAsc();
}
