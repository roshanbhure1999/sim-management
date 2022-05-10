package com.example.simmanagement.rest;

import com.example.simmanagement.dto.CustomerDTO;
import com.example.simmanagement.entity.Customer;
import com.example.simmanagement.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/customer")
@Validated
public class CustomerApi {
    @Autowired
    private CustomerService customerService;
    @PostMapping
    public Customer createCustomer(@RequestBody  CustomerDTO customerDTO) {
        return customerService.createCustomer(customerDTO);
    }
}
