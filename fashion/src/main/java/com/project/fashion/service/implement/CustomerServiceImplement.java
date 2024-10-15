/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.project.fashion.service.implement;

import com.project.fashion.dto.request.CreateCustomerDTO;
import com.project.fashion.dto.request.CusRequestDTO;
import com.project.fashion.dto.response.CustomerDetailResponse;
import com.project.fashion.exception.AttributeAlreadyExistsException;
import com.project.fashion.exception.ResourceNotFoundException;
import com.project.fashion.repository.CustomerRepository;
import com.project.fashion.service.CustomerService;

import com.project.fashion.model.Customer;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 *
 * @author Vu
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceImplement implements CustomerService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    private final CustomerRepository customerRepository;

    private Customer getCustomerById(Long customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("CustomerId doesn't exits"));
    }

    @Override
    public Long addCustomer(CreateCustomerDTO addCustomer) {
        log.info("create customer");
        Customer customer = Customer.builder()
                .email(addCustomer.getEmail())
                .username(addCustomer.getUsername())
                .password(passwordEncoder.encode(addCustomer.getPassword()))
                .build();
        if (customerRepository.findByUsername(addCustomer.getUsername()).isPresent()) {
            throw new AttributeAlreadyExistsException("username already exists");
        }
        if (customerRepository.findByEmail(addCustomer.getEmail()).isPresent()) {
            throw new AttributeAlreadyExistsException("email already exists");
        }
        customerRepository.save(customer);
        log.info("Customer has created");
        return customer.getCustomerId();

    }

    @Override
    public CustomerDetailResponse getInfoCustomer(long customerId) {
        Customer customer = getCustomerById(customerId);
        return new CustomerDetailResponse(
                customer.getCustomerId(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getPhoneNumber(),
                customer.getEmail(),
                customer.getUsername(),
                customer.getPassword());
    }

    @Override
    public void updateInfoCustomer(CusRequestDTO cusRequestDTO) {
        Customer customer = getCustomerById(cusRequestDTO.getCustomerId());
        if (StringUtils.hasLength(cusRequestDTO.getFirstName())) {
            customer.setFirstName(cusRequestDTO.getFirstName());
        }
        if (StringUtils.hasLength(cusRequestDTO.getLastName())) {
            customer.setLastName(cusRequestDTO.getLastName());
        }
        if (StringUtils.hasLength(cusRequestDTO.getAddress())) {
            customer.setAddress(cusRequestDTO.getAddress());
        }
        if (StringUtils.hasLength(cusRequestDTO.getPhoneNumber())) {
            if (customerRepository.findByPhoneNumber(cusRequestDTO.getPhoneNumber()).isPresent()) {
                throw new AttributeAlreadyExistsException("PhoneNumber already exists");
            }
            customer.setPhoneNumber(cusRequestDTO.getPhoneNumber());
        }
        if (StringUtils.hasLength(cusRequestDTO.getEmail())) {
            if (customerRepository.findByEmail(cusRequestDTO.getPhoneNumber()).isPresent()) {
                throw new AttributeAlreadyExistsException("Email already exists");
            }
            customer.setEmail(cusRequestDTO.getEmail());
        }
        customer.setUsername(cusRequestDTO.getUsername());
        customer.setPassword(cusRequestDTO.getPassword());
        log.info("Update Successfully");
    }

    @Override
    public void deleteCustomer(int customerId) {

    }

}
