package com.project.fashion.dto.request;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ListCartCreateBillDTO {
    private List<Long> carts;
    private Long paymentId;
}
