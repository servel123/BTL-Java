/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.project.fashion.dto.request;

import com.project.fashion.model.Category;
import lombok.*;

/**
 *
 * @author Vu
 */
@Getter
@Setter
public class AddProductDTO {
    private String desciption, image, name;
    private Category category;
    private int stock;
    private Long price;

}
