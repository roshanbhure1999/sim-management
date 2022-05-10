package com.example.simmanagement.repository;

import com.example.simmanagement.entity.Sim;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SimRepository extends JpaRepository<Sim, Long> {
}
