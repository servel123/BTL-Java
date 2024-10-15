/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.project.fashion.service;

import com.project.fashion.dto.request.CreateCustomerDTO;
import com.project.fashion.dto.request.CusRequestDTO;
import com.project.fashion.dto.response.CustomerDetailResponse;

/**
 *
 * @author Vu
 */
public interface CustomerService {
    CustomerDetailResponse getInfoCustomer(long customerId);

    void updateInfoCustomer(CusRequestDTO cusRequestDTO);

    void deleteCustomer(int customerId);

    Long addCustomer(CreateCustomerDTO addCustomer);

}
