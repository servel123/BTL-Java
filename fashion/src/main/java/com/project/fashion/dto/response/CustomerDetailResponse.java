package com.project.fashion.dto.response;

import lombok.*;

import java.io.Serializable;

import jakarta.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@ToString
@Builder
public class CustomerDetailResponse implements Serializable {
    @NotNull
    private long customerId;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String username;
    private String password;

}