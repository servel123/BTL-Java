package com.project.fashion.dto.response;

import java.util.List;

import com.project.fashion.model.Category;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryResDTO {
    private String name;
    private List<Category> categories;

}
