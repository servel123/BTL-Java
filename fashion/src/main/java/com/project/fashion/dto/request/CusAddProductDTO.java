package com.project.fashion.dto.request;

import java.io.Serializable;

import jakarta.validation.constraints.*;

import lombok.Getter;

@Getter
public class CusAddProductDTO implements Serializable {
    @Min(1)
    @NotNull
    private final int productId;
    @NotNull
    @Min(1)
    private final int quantity;

    public CusAddProductDTO(int productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

}
