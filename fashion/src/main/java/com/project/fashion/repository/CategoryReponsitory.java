package com.project.fashion.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.fashion.model.Category;

public interface CategoryReponsitory extends JpaRepository<Category, Long> {
}
