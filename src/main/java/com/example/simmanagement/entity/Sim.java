package com.example.simmanagement.entity;

import com.example.simmanagement.constant.Network;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id")
    private Customer customer;


}
