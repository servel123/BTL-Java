/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.project.fashion.service;

import java.util.List;

import com.project.fashion.dto.request.AddCategoryDTO;
import com.project.fashion.dto.response.CategoryResDTO;
import com.project.fashion.model.Category;

/**
 *
 * @author Vu
 */
public interface CategoryService {
    // Add
    Category addCategory(AddCategoryDTO category);

    // GET

    Category getCategory(Long categoryId);

    Category getCategoryByName(String name);

    List<Category> getCategories();

    List<Category> getCategory(Long[] categoryId);

    List<CategoryResDTO> showCategory();

    // UPDATE
    Category updateCategory(AddCategoryDTO categoryDTO);

    // DELETE
    void deleteCategory(Long categoryId);

}
