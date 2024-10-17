package com.project.fashion.dto.request;

import java.io.Serializable;

import com.project.fashion.util.PhoneNumber;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor // auto create constructor full parameter
@Getter
public class CusModifyInfo implements Serializable {

    private Long customerId;

    private final String firstName;

    private final String lastName;

    private final String address;

    @PhoneNumber
    private final String phoneNumber;

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
}
