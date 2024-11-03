package com.project.fashion.dto.request;

import java.io.Serializable;

import com.project.fashion.util.PhoneNumber;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@RequiredArgsConstructor // auto create constructor full parameter
@Getter
@Setter
public class CusModifyInfo implements Serializable {

    private Long customerId;

    private String firstName;

    private String lastName;

    @NotBlank(message = "not blank")
    private String username;

    @Email(message = "email invalid format")
    private String email;

    private String address;

    @PhoneNumber(message = "phone invalid format")
    private String phoneNumber;

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
}
