package com.project.fashion.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.project.fashion.model.Category;

public interface CategoryReponsitory extends JpaRepository<Category, Long> {
    List<Category> findTop2ByOrderByCategoryIdAsc();

    @Query("SELECT DISTINCT c.fashion FROM Category c")
    List<String> findFashion();

    List<Category> findAllByFashion(String fashion);

    Category findByName(String name);
}
