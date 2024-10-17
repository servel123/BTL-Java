/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.project.fashion.service.implement;

import com.project.fashion.dto.request.CreateCustomerDTO;
import com.project.fashion.dto.request.CusModifyInfo;
import com.project.fashion.dto.response.CustomerDetailResponse;
import com.project.fashion.exception.AttributeAlreadyExistsException;
import com.project.fashion.exception.ResourceNotFoundException;
import com.project.fashion.repository.CustomerRepository;
import com.project.fashion.service.CustomerService;

import com.project.fashion.model.Customer;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 *
 * @author Vu
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceImplement implements CustomerService, UserDetailsService {

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Autowired
    private final CustomerRepository customerRepository;

    protected Customer getCustomerById(Long customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("CustomerId doesn't exits"));
    }

    // customerId
    public Customer getCustomerId(String username) {
        return customerRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Username doesn't exits"));
    }

    // login
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer user = customerRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        return User.withUsername(user.getUsername())
                .password(user.getPassword())
                .roles()
                .build();
    }

    // register
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

    // update
    @Override
    public CustomerDetailResponse updateCustomer(CusModifyInfo cusRequestDTO) {
        try {
            Customer customer = getCustomerById(cusRequestDTO.getCustomerId());
            customer.setFirstName(cusRequestDTO.getFirstName());
            customer.setLastName(cusRequestDTO.getLastName());
            customer.setAddress(cusRequestDTO.getAddress());
            customer.setPhoneNumber(cusRequestDTO.getPhoneNumber());
            customerRepository.save(customer);
            return new CustomerDetailResponse(
                    customer.getFirstName(),
                    customer.getLastName(),
                    customer.getAddress(),
                    customer.getPhoneNumber());
        } catch (Exception e) {
            throw e;
        }

    }

    // delete customer
    @Override
    public boolean deleteCustomer(Long customerId) {
        try {
            customerRepository.deleteById(customerId);
            return true;
        } catch (Exception e) {
            throw e;
        }
    }

    // get information customer
    @Override
    public CustomerDetailResponse getInfoCustomer(Long customerId) {
        try {
            Customer customer = getCustomerById(customerId);
            return new CustomerDetailResponse(
                    customer.getFirstName(),
                    customer.getLastName(),
                    customer.getAddress(),
                    customer.getPhoneNumber());
        } catch (Exception e) {
            throw new ResourceNotFoundException("User doesn't already exists");
        }
    }

}
