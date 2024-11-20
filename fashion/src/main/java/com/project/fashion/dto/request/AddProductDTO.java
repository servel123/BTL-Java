/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.project.fashion.dto.request;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

import lombok.*;

/**
 *
 * @author Vu
 */
@Getter
@Setter
public class AddProductDTO implements Serializable {
    private MultipartFile image;
    private String name, description;
    private Long originalprice, price;
    private String category;
    private int stock;
}
