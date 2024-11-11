package com.project.fashion.dto.request;

import java.io.Serializable;

import jakarta.validation.constraints.NotBlank;

public class CreateBillDTO implements Serializable {
    @NotBlank
    private String name;
    @NotBlank
    private String address;
    @NotBlank
    private String phone;

}
