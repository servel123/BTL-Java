package com.project.fashion.dto.request;

import java.io.Serializable;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCustomerDTO implements Serializable {
    @Email(message = "email must be type email")
    @NotBlank
    private String email;

    @NotNull(message = "username must be not null")
    @NotBlank(message = "username must be not blank")
    private String username;

    @NotNull(message = "password must be not null")
    @NotBlank(message = "password must be not blank")
    private String password;

    public CreateCustomerDTO() {
    }

    public CreateCustomerDTO(String email, String username, String password) {
        this.email = email;
        this.password = password;
        this.username = username;
    }
}
