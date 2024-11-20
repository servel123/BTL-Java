package com.project.fashion.dto.request;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateProductDTO implements Serializable {
    private Long id;
    private MultipartFile image;
    private String name;
    private String description;
    private Long originalprice;
    private Long price;
    private String category;
    private int stock;
}
