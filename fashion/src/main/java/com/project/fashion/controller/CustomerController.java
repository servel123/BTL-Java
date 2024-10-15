package com.project.fashion.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.fashion.dto.request.CreateCustomerDTO;
// import com.project.fashion.dto.request.CusAddProductDTO;
import com.project.fashion.dto.request.CusRequestDTO;
import com.project.fashion.dto.response.CustomerDetailResponse;
import com.project.fashion.dto.response.ResponseData;
//import com.project.fashion.exception.ResourceNotFoundException;
import com.project.fashion.service.implement.CustomerServiceImplement;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/user")
@Slf4j
public class CustomerController {

    @Autowired
    private CustomerServiceImplement customerServiceImplement;

    @PostMapping("/")
    public ResponseData<Long> createAccount(@Valid @RequestBody CreateCustomerDTO customer) {
        try {
            log.info("this is username:" + customer.getUsername());
            Long customerId = customerServiceImplement.addCustomer(customer);
            return new ResponseData<>(HttpStatus.CREATED.value(), HttpStatus.CREATED.getReasonPhrase(), customerId);
        } catch (Exception e) {
            return new ResponseData<>(HttpStatus.NOT_ACCEPTABLE.value(), e.getMessage());
        }
         
    }
    
    @GetMapping("/{customerId}")
    public ResponseData<?> infoCustomer(@Min(1) @PathVariable int customerId) {
        try {
            CustomerDetailResponse data = customerServiceImplement.getInfoCustomer(customerId);
            log.info("this is Object:" + data);
            return new ResponseData<Object>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), data);
        } catch (Exception e) {
            return new ResponseData<>(HttpStatus.NOT_FOUND.value(), e.getMessage());
        }
    }


    @PutMapping("/{customerId}")
    public ResponseData<?> updateCustomer(@Min(1) @PathVariable int customerId,
            @Valid @RequestBody CusRequestDTO cusDTO) {
        return new ResponseData<>(HttpStatus.ACCEPTED.value(), HttpStatus.ACCEPTED.getReasonPhrase());

    }

    @DeleteMapping("/{customerId}")
    public ResponseData<?> deleteCustomer(@PathVariable int customerId) {
        return new ResponseData<>(HttpStatus.NO_CONTENT.value(), HttpStatus.NO_CONTENT.getReasonPhrase());
    }

    @GetMapping(("/cart/{customerId}/{productId}"))
    public ResponseData<?> infoCart(@Min(1) @PathVariable int customerId, @Min(1) @PathVariable int productId) {
        Object data = "OK";
        return new ResponseData<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), data);
    }

    // @PostMapping("/{productId}")
    // public ResponseData<?> updateCart(@PathVariable int productId,
    // @Valid @RequestBody CusAddProductDTO cusAddProductDTO) {
    // Object data = "OK";
    // return new ResponseData<>(HttpStatus.OK.value(),
    // HttpStatus.OK.getReasonPhrase(), data);
    // }

    @DeleteMapping("/cart/{productId}")
    public ResponseData<?> deleteProductCart(@PathVariable int productId) {
        return new ResponseData<>(HttpStatus.NO_CONTENT.value(), HttpStatus.NO_CONTENT.getReasonPhrase());
    }

}
