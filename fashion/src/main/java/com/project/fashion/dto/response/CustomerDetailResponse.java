package com.project.fashion.dto.response;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
@Builder
public class CustomerDetailResponse implements Serializable {
    private Long customerId;
    private String email;
    private String username;
    private String firstName;
    private String lastName;
    private String address;
    private String phone;

    public CustomerDetailResponse(Long customerId, String email, String username, String firstName, String lastName,
            String address, String phone) {
        this.customerId = customerId;
        this.email = email;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phone = phone;
    }

    public CustomerDetailResponse(String firstName, String lastName, String address, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phone = phone;
    }
}