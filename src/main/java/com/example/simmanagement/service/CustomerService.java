package com.example.simmanagement.service;

import com.example.simmanagement.dto.CustomerDTO;
import com.example.simmanagement.entity.Customer;

public interface CustomerService {
    Customer createCustomer(CustomerDTO customerDto);
}
