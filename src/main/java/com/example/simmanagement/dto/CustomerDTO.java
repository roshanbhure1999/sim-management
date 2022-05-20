package com.example.simmanagement.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Data
public class CustomerDTO {
    private Long CustomerId;

    @NotBlank(message = "Customer Firstname can not be empty.")
    private String firstName;

    @NotBlank(message = "Customer Last Name can not be empty.")
    private String lastName;

    @NotNull(message = "Mobile number is required")
    @Pattern(regexp = "[\\d]{10}", message = "Mobile number should 10 digit only")
    private String mobileNumber;

    @NotBlank(message = "Address is required")
    private String address;

    private LocalDate DateOfBirth;

    @NotBlank(message = "Email is required")
    private String email;

    @NotNull(message = "Aadhar number is required")
    @Pattern(regexp = "[\\d]{12}", message = "aadharNumber should 12 digit only")
    private String aadharNumber;
}
