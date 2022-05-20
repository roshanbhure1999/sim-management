package com.example.simmanagement.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Data
@Table(uniqueConstraints = @UniqueConstraint(name = "UniqueAadharNumber,EmailAnd", columnNames = {"aadharNumber", "email"}))
public class Customer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "mobileNumber")
    private String mobileNumber;

    @Column(name = "address")
    private String address;

    @Column(name = "dateOfBirth")
    private LocalDate dateOfBirth;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "aadharNumber", unique = true)
    private String aadharNumber;


}
