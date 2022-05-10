package com.example.simmanagement.service.impl;

import com.example.simmanagement.dto.CustomerDTO;
import com.example.simmanagement.entity.Customer;
import com.example.simmanagement.repository.CustomerRepository;
import com.example.simmanagement.repository.SimRepository;
import com.example.simmanagement.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private SimRepository simRepository;


    @Override
    public Customer createCustomer(CustomerDTO customerDto) {
        Customer save = customerRepository.save(dtoToEntity(customerDto));
        return save;
    }

    private Customer dtoToEntity(CustomerDTO customerDto) {
        Customer customer = new Customer();
        customer.setCustomerId(customerDto.getCustomerId());
        customer.setFirstName(customerDto.getFirstName());
        customer.setLastName(customerDto.getLastName());
        customer.setMobileNumber(customerDto.getMobileNumber());
        customer.setAddress(customerDto.getAddress());
        customer.setDateOfBirth(customerDto.getDateOfBirth());
        customer.setAadharNumber(customerDto.getAadharNumber());

        return customer;
    }
}
