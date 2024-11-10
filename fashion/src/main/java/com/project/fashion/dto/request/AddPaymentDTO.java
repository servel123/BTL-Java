package com.project.fashion.dto.request;

import java.io.Serializable;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddPaymentDTO implements Serializable {

    private Long paymentId;
    @NotNull
    private Long customerId;
    @NotBlank
    private String method;
}
