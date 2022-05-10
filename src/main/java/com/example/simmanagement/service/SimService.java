package com.example.simmanagement.service;

import com.example.simmanagement.dto.SimDTO;
import com.example.simmanagement.entity.Sim;

public interface SimService {
    Sim createSim(SimDTO simDTO);
}
