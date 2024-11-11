/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.project.fashion.dto.request;

import java.io.Serializable;

import lombok.*;

/**
 *
 * @author Vu
 */
@AllArgsConstructor
@Getter
@Setter
public class AddCategoryDTO implements Serializable {
    private Long id;
    private String name;
    private String fashion;

}
