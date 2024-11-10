package com.project.fashion.dto.request;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ListCartCreateBillDTO implements Serializable {
    private List<Long> carts;
    private Long paymentId;
}
