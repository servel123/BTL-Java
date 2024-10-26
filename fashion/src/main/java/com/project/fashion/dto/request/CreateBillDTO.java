package com.project.fashion.dto.request;

import jakarta.validation.constraints.NotBlank;

public class CreateBillDTO {
    @NotBlank
    private String name;
    @NotBlank
    private String address;
    @NotBlank
    private String phone;

}
