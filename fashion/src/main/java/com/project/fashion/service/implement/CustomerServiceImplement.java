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

import java.util.List;
import java.util.Optional;

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

    public Customer getCustomerById(Long customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("CustomerId doesn't exits"));
    }

    public Customer getCustomerByUsername(String username) {
        return customerRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("CustomerId doesn't exits"));
    }

    public List<Customer> getAllCustomer() {
        return customerRepository.findAll();
    }

    // login
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer user = customerRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        return User.withUsername(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole())
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
    public CustomerDetailResponse getInfoCustomer(String username) {
        try {
            Customer customer = getCustomerByUsername(username);
            return new CustomerDetailResponse(
                    customer.getCustomerId(),
                    customer.getEmail(),
                    customer.getUsername(),
                    customer.getFirstName(),
                    customer.getLastName(),
                    customer.getAddress(),
                    customer.getPhoneNumber());
        } catch (Exception e) {
            throw new ResourceNotFoundException("User doesn't already exists");
        }
    }

    @Override
    public Customer updateRoleCustomer(Long customerId, String role) {
        try {
            Customer customer = getCustomerById(customerId);

            if (customer == null) {
                throw new ResourceNotFoundException("Customer not found with id: " + customerId);
            }
            role = role.toUpperCase();
            customer.setRole(role);
            customerRepository.save(customer);

            return customer;
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error updating customer");
        }

    }

    @Override
    public Customer hasEmail(String email) {
        return customerRepository.findByEmail(email)
                .orElseThrow(null);
    }

    @Override
    public Customer hasUserName(String username) {
        return customerRepository.findByUsername(username)
                .orElseThrow(null);
    }

    @Override
    public Customer hasPhoneNumber(String phoneNumber) {
        return customerRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(null);
    }

    // update
    @Override
    public CustomerDetailResponse updateCustomer(CusModifyInfo cusRequestDTO) {

        Customer customer = getCustomerById(cusRequestDTO.getCustomerId());

        if (customer == null) {
            throw new ResourceNotFoundException("Customer not found with ID: " + cusRequestDTO.getCustomerId());
        }
        Optional<Customer> emailCustomer = customerRepository.findByEmail(cusRequestDTO.getEmail());
        if (emailCustomer.isPresent() && !emailCustomer.get().equals(customer)) {
            throw new AttributeAlreadyExistsException("Email already exists");
        }

        Optional<Customer> phoneCustomer = customerRepository.findByPhoneNumber(cusRequestDTO.getPhoneNumber());
        if (phoneCustomer.isPresent() && !phoneCustomer.get().equals(customer)) {
            throw new AttributeAlreadyExistsException("Phone number already exists");
        }

        customer.setFirstName(cusRequestDTO.getFirstName());
        customer.setLastName(cusRequestDTO.getLastName());
        customer.setAddress(cusRequestDTO.getAddress());
        customer.setEmail(cusRequestDTO.getEmail());
        customer.setPhoneNumber(cusRequestDTO.getPhoneNumber());

        customerRepository.save(customer);

        return new CustomerDetailResponse(
                customer.getFirstName(),
                customer.getLastName(),
                customer.getAddress(),
                customer.getPhoneNumber());

    }
}
