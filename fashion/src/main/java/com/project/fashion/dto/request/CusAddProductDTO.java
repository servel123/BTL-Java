package com.project.fashion.dto.request;

import java.io.Serializable;

import jakarta.validation.constraints.*;

import lombok.Getter;

@Getter
public class CusAddProductDTO implements Serializable {
    @Min(1)
    @NotNull
    private final Long customerId;

    @Min(1)
    @NotNull
    private final Long productId;
    @NotNull
    @Min(1)
    private final Long quantity;

    public CusAddProductDTO(Long productId, Long quantity, Long customerId) {
        this.customerId = null;
        this.productId = productId;
        this.quantity = quantity;
    }

}
