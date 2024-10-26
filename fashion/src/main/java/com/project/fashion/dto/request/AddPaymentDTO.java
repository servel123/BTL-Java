package com.project.fashion.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddPaymentDTO {

    private Long paymentId;
    @NotNull
    private Long customerId;
    @NotBlank
    private String method;
}
