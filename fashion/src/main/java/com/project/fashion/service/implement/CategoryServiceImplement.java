/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.project.fashion.service.implement;

import com.project.fashion.dto.request.AddCategoryDTO;
import com.project.fashion.dto.response.CategoryResDTO;
import com.project.fashion.exception.ResourceNotFoundException;
import com.project.fashion.model.Category;
import com.project.fashion.repository.CategoryReponsitory;
import com.project.fashion.service.CategoryService;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Vu
 */
@Service
@Slf4j
public class CategoryServiceImplement implements CategoryService {

    @Autowired
    private CategoryReponsitory categoryReponsitory;

    public List<Category> getAllCategories() {
        List<Category> cate = categoryReponsitory.findAll();
        return cate;
    }

    @Override
    public Category getCategory(Long categoryId) {
        return categoryReponsitory.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Not categoryId"));

    }

    @Override
    public Category getCategoryByName(String name) {
        Category category = categoryReponsitory.findByName(name);
        if (category != null) {
            return category;
        } else {
            throw new ResourceNotFoundException("Not Name");
        }
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
    public List<Category> getCategories() {
        return categoryReponsitory.findTop3ByOrderByCategoryIdAsc();
    }

    @Override
    public Category addCategory(AddCategoryDTO category) {
        Category cate = Category.builder()
                .name(category.getName())
                .fashion(category.getFashion())
                .build();
        categoryReponsitory.save(cate);
        return cate;
    }

    // update
    @Override
    public Category updateCategory(AddCategoryDTO category) {
        Category cate = categoryReponsitory.findById(category.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Not category"));
        cate.setName(category.getName());
        cate.setFashion(category.getFashion());
        categoryReponsitory.save(cate);
        return cate;
    }

    // delete
    @Override
    public void deleteCategory(Long categoryId) {
        categoryReponsitory.deleteById(categoryId);
    }

    @Override
    public List<CategoryResDTO> showCategory() {
        try {
            List<String> fashionList = categoryReponsitory.findFashion();
            for (String s : fashionList) {
                log.info("\n\n" + s);
            }
            List<CategoryResDTO> cateListRes = new ArrayList<CategoryResDTO>();
            for (String fashion : fashionList) {
                CategoryResDTO cate = new CategoryResDTO();
                List<Category> categories = categoryReponsitory.findAllByFashion(fashion);
                cate.setName(fashion);
                cate.setCategories(categories);
                cateListRes.add(cate);
            }
            return cateListRes;
        } catch (Exception e) {
            throw new ResourceNotFoundException("Không có danh muc nao!");
        }

    }
}
