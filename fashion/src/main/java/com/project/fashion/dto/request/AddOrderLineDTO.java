package com.project.fashion.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class AddOrderLineDTO {
    private Long orderLineId;
    @NotBlank
    private Long paymentId;
}
