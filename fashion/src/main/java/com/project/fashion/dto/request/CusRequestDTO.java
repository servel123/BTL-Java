package com.project.fashion.dto.request;

import java.io.Serializable;

import com.project.fashion.util.PhoneNumber;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor // auto create constructor full parameter
@Getter
public class CusRequestDTO implements Serializable {
    @NotNull(message = "customerId must be not null")
    private final Long customerId;

    @Pattern(regexp = "^$|^(?!\\s*$).+", message = "firstName must be not empty and space")
    private final String firstName;

    @Pattern(regexp = "^$|^(?!\\s*$).+", message = "lastName must be not empty and space")
    private final String lastName;

    @Pattern(regexp = "^$|^(?!\\s*$).+", message = "address must be not empty and space")
    private final String address;

    @Email(message = "email must be formated as an email")
    private final String email;

    @NotNull(message = "username must be not null")
    @NotBlank(message = "username must be not blank")
    private final String username;

    @NotNull(message = "password must be not null")
    @NotBlank(message = "password must be not blank")
    private final String password;

    @PhoneNumber
    private final String phoneNumber;

}
