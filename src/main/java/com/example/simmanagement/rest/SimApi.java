package com.example.simmanagement.rest;

import com.example.simmanagement.dto.CustomerDTO;
import com.example.simmanagement.dto.SimDTO;
import com.example.simmanagement.entity.Customer;
import com.example.simmanagement.entity.Sim;
import com.example.simmanagement.service.CustomerService;
import com.example.simmanagement.service.SimService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sim")
public class SimApi {
    @Autowired
    private SimService simService;
    @PostMapping
    public Sim createCustomer(@RequestBody SimDTO simDTO) {
        return simService.createSim(simDTO);
    }
}
