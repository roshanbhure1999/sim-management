package com.example.simmanagement.service;

import com.example.simmanagement.dto.SimDTO;
import com.example.simmanagement.entity.Sim;

import java.util.List;

public interface SimService {
    Sim createSim(SimDTO simDTO);

    List<SimDTO> getAllSims();

    SimDTO findBySimId(Long simId);

}
