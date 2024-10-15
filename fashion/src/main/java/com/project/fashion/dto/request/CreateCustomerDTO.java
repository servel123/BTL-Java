package com.project.fashion.dto.request;

import java.io.Serializable;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class CreateCustomerDTO implements Serializable {
    @Email(message = "email must be type email")
    @NotBlank
    private final String email;

    @NotNull(message = "username must be not null")
    @NotBlank(message = "username must be not blank")
    private final String username;

    @NotNull(message = "password must be not null")
    @NotBlank(message = "password must be not blank")
    private final String password;

}
