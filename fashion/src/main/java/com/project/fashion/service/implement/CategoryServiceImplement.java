/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.project.fashion.service.implement;

import com.project.fashion.dto.request.InfoCategoryDTO;
import com.project.fashion.exception.ResourceNotFoundException;
import com.project.fashion.model.Category;
import com.project.fashion.repository.CategoryReponsitory;
import com.project.fashion.service.CategoryService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Vu
 */
@Service
public class CategoryServiceImplement implements CategoryService {

    @Autowired
    private CategoryReponsitory categoryReponsitory;

    @Override
    public Category getCategory(Long categoryId) {
        return categoryReponsitory.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Not categoryId"));

    }

    @Override
    public List<Category> getCategory(Long[] categoryId) {
        List<Category> categorys = new ArrayList<>();
        for (Long id : categoryId) {
            Category category = categoryReponsitory.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Not categoryId"));
            categorys.add(category);
        }
        return categorys;
    }

    @Override
    public InfoCategoryDTO addCategory(InfoCategoryDTO infoCategoryDTO) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from
                                                                       // nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public InfoCategoryDTO updateCategory(InfoCategoryDTO infoCategoryDTO) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from
                                                                       // nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void deleteCategory(Long categoryId) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from
                                                                       // nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
