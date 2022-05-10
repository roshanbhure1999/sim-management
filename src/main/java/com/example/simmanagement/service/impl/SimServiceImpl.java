package com.example.simmanagement.service.impl;

import com.example.simmanagement.dto.SimDTO;
import com.example.simmanagement.entity.Customer;
import com.example.simmanagement.entity.Sim;
import com.example.simmanagement.repository.CustomerRepository;
import com.example.simmanagement.repository.SimRepository;
import com.example.simmanagement.service.SimService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SimServiceImpl implements SimService {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private SimRepository simRepository;


    private Sim dtoToEntity(SimDTO simDTO) {
        Sim sim = new Sim();
        sim.setId(simDTO.getId());
        sim.setSimNumber(simDTO.getSimNumber());
        sim.setMobileNumber(simDTO.getMobileNumber());
        sim.setNetwork(simDTO.getNetwork());

        Customer customer = customerRepository.findById(simDTO.getCustomerId()).orElse(null);
        sim.setCustomer(customer);
        return sim;
    }

    @Override
    public Sim createSim(SimDTO simDTO) {

        Sim sim = dtoToEntity(simDTO);

        return simRepository.save(sim);
    }
}
