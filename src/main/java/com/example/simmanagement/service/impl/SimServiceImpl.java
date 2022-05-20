package com.example.simmanagement.service.impl;

import com.example.simmanagement.dto.SimDTO;
import com.example.simmanagement.entity.Customer;
import com.example.simmanagement.entity.Sim;
import com.example.simmanagement.exception.SimException;
import com.example.simmanagement.repository.CustomerRepository;
import com.example.simmanagement.repository.SimRepository;
import com.example.simmanagement.service.SimService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class SimServiceImpl implements SimService {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private SimRepository simRepository;

    private Sim copyFromDTODetails(SimDTO dto) {
        Sim sim = new Sim();
        BeanUtils.copyProperties(dto, sim);
        Customer customer = customerRepository.findById(dto.getCustomer_Id()).orElse(null);
        sim.setCustomer(customer);
        return sim;
    }


    @Override
    public Sim createSim(SimDTO simDTO) {
        Sim sim = null;
        if (Objects.nonNull(simDTO.getSimId())) {
            Optional<Sim> simOptional = simRepository.findById(simDTO.getSimId());
            if (!simOptional.isPresent()) {
                log.error("No Sim exist for Sim id ::{}", simDTO.getSimId());
                throw new SimException("No Sim exist for Sim id ::" + simDTO.getSimId(), HttpStatus.NOT_FOUND);
            }
            sim = simOptional.get();
            if (!Objects.equals(sim.getSimNumber(), simDTO.getSimNumber())) {
                log.error("Not allowed to update Sim Number");
                throw new SimException("Not allowed to update Sim Number", HttpStatus.BAD_REQUEST);
            }
            copyDtoToSimDTO(sim, simDTO);
        } else {
            log.info("creating new Sim ::{}", simDTO.getSimNumber());
            if (simRepository.findBySimNumber(simDTO.getSimNumber()).isPresent()) {
                log.error("Sim already exist");
                throw new SimException("Sim already exist with Number", HttpStatus.CONFLICT);
            }
            sim = new Sim();
            sim = copyFromDTODetails(simDTO);
        }
        return simRepository.save(sim);
    }

    @Override
    public List<SimDTO> getAllSims() {
        log.debug("Finding All Accounts");
        List<Sim> sims = simRepository.findAll();
        List<SimDTO> simDTOS = new ArrayList<>();
        if (!sims.isEmpty()) {
            for (Sim sim : sims) {
                SimDTO studentDto = entityToDto(sim);
                simDTOS.add(studentDto);
            }

            return simDTOS;
        } else {
            throw new SimException("", HttpStatus.NO_CONTENT);
        }


    }

    @Override
    public SimDTO findBySimId(Long simId) {
        Sim sim = simRepository.findById(simId).orElse(null);
        SimDTO simDTO = entityToDto(sim);
        return simDTO;
    }


    private SimDTO entityToDto(Sim sim) {
        SimDTO simDTO = new SimDTO();
        simDTO.setSimId(sim.getId());
        simDTO.setSimNumber(sim.getSimNumber());
        simDTO.setMobileNumber(sim.getMobileNumber());
        simDTO.setNetwork(sim.getNetwork());
        Long custId;

        Customer customer = sim.getCustomer();
        if (Objects.nonNull(customer)) {
            custId = customer.getCustomerId();
        } else custId = null;
        simDTO.setCustomer_Id(custId);
        return simDTO;
    }

    private void copyDtoToSimDTO(Sim sim, SimDTO simDTO) {
        BeanUtils.copyProperties(simDTO, sim);
    }


}
