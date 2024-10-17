package com.project.fashion.dto.response;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
@Builder
public class CustomerDetailResponse implements Serializable {
    private String firstName;
    private String lastName;
    private String address;
    private String phone;

    public CustomerDetailResponse(String firstName, String lastName, String address, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phone = phone;
    }

}