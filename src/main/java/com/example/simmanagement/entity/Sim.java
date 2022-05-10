package com.example.simmanagement.entity;

import com.example.simmanagement.constant.Network;
import com.example.simmanagement.dto.SimDTO;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Data
public class Sim implements Serializable {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @Column(name = "simNumber")
    private String simNumber;

    @Enumerated(EnumType.STRING)
    private Network network;

    @Column(name = "mobileNumber")
    private String mobileNumber;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id")
    private Customer customer;





}
