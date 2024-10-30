/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.project.fashion.service;

import com.project.fashion.dto.request.CreateCustomerDTO;
import com.project.fashion.dto.request.CusModifyInfo;
import com.project.fashion.dto.response.CustomerDetailResponse;
import com.project.fashion.model.Customer;

/**
 *
 * @author Vu
 */
public interface CustomerService {

    CustomerDetailResponse getInfoCustomer(String username);

    CustomerDetailResponse updateCustomer(CusModifyInfo cusRequestDTO);

    boolean deleteCustomer(Long customerId);

    Long addCustomer(CreateCustomerDTO addCustomer);

    Customer updateRoleCustomer(Long customerId, String role);

    Customer hasEmail(String email);

    Customer hasUserName(String username);

    Customer hasPhoneNumber(String phoneNumber);
}
