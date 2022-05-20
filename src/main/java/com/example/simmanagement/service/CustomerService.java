package com.example.simmanagement.service;

import com.example.simmanagement.dto.CustomerDTO;
import com.example.simmanagement.entity.Customer;
import com.example.simmanagement.entity.Sim;

import java.io.ByteArrayInputStream;
import java.util.List;

public interface CustomerService {
    Customer createCustomer(CustomerDTO customerDto);

    List<CustomerDTO> findAll();

    CustomerDTO findById(Long id);

    List<Sim> getSims(long customerId);

    void mail();

    String[] getMailAfterSevenDay();

    ByteArrayInputStream dailyExport();


}
