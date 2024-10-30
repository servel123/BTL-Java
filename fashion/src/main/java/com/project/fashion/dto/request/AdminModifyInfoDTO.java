package com.project.fashion.dto.request;

import com.project.fashion.util.PhoneNumber;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AdminModifyInfoDTO {
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    private String address;
    @NotBlank
    private String email;
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @PhoneNumber
    private String phoneNumber;
    @NotBlank
    private String role;
}
