/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.project.fashion.service;

import com.project.fashion.dto.request.CreateCustomerDTO;
import com.project.fashion.dto.request.CusModifyInfo;
import com.project.fashion.dto.response.CustomerDetailResponse;

/**
 *
 * @author Vu
 */
public interface CustomerService {
    CustomerDetailResponse getInfoCustomer(Long customerId);

    CustomerDetailResponse updateCustomer(CusModifyInfo cusRequestDTO);

    boolean deleteCustomer(Long customerId);

    Long addCustomer(CreateCustomerDTO addCustomer);

}
