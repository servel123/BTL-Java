/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.project.fashion.service;

import java.util.List;

import com.project.fashion.dto.request.InfoCategoryDTO;
import com.project.fashion.model.Category;

/**
 *
 * @author Vu
 */
public interface CategoryService {
    Category getCategory(Long categoryId);

    List<Category> getCategory(Long[] categoryId);

    InfoCategoryDTO addCategory(InfoCategoryDTO infoCategoryDTO);

    InfoCategoryDTO updateCategory(InfoCategoryDTO infoCategoryDTO);

    void deleteCategory(Long categoryId);

}
