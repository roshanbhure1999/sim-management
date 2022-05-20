package com.example.simmanagement.rest;

import com.example.simmanagement.dto.SimDTO;
import com.example.simmanagement.service.SimService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/sim")
public class SimApi {
    @Autowired
    private SimService simService;

    @PutMapping
    public ResponseEntity<Object> createAccount(@RequestBody @Valid SimDTO simDto) {
        return new ResponseEntity<>(simService.createSim(simDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<SimDTO>> listAllSims() {
        return ResponseEntity.ok(simService.getAllSims());
    }

    @GetMapping("/{simId}")
    public ResponseEntity<SimDTO> findByAccountNumber(@PathVariable("simId") long simId) {
        return ResponseEntity.ok(simService.findBySimId(simId));
    }

}
