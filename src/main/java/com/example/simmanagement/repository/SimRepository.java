package com.example.simmanagement.repository;

import com.example.simmanagement.entity.Customer;
import com.example.simmanagement.entity.Sim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SimRepository extends JpaRepository<Sim, Long> {
    Optional<Sim> findBySimNumber(String simNumber);

    List<Sim> findByCustomer(Optional<Customer> id);


}
