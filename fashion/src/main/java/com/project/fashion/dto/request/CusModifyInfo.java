package com.project.fashion.dto.request;

import java.io.Serializable;

import com.project.fashion.util.PhoneNumber;

import lombok.*;

@RequiredArgsConstructor // auto create constructor full parameter
@Getter
@Setter
public class CusModifyInfo implements Serializable {

    private Long customerId;

    private String firstName;

    private String lastName;

    private String address;

    @PhoneNumber
    private String phoneNumber;

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
}
