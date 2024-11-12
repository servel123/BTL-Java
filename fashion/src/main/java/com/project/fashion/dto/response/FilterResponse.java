package com.project.fashion.dto.response;

import java.io.Serializable;
import java.util.List;

import com.project.fashion.model.Product;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FilterResponse implements Serializable {
    private String name;
    private List<Product> products;

}
