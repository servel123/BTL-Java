package com.project.fashion.dto.response;

import java.io.*;

import jakarta.validation.constraints.*;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ProductDetailResponse implements Serializable {
    @Min(1)
    @NotBlank
    private int productId;
    @NotBlank
    private String description;
    @Min(0)
    @NotNull
    private Long price;
    private String sku;
    @NotNull
    private int stock;
    @NotBlank
    private String image;
}
