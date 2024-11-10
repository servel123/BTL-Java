package com.project.fashion.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
public class AddOrderLineDTO {
    private Long orderLineId;
    @NotBlank(message = "not blank")
    private Long paymentId;
    @Pattern(regexp = "^(PAID|NOT_YET_PAID)$")
    private String status;
    private String tranCode;
}
