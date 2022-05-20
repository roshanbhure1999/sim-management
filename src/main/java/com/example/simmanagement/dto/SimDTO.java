package com.example.simmanagement.dto;

import com.example.simmanagement.constant.Network;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
public class SimDTO {
    private Long simId;

    @NotBlank(message = "Sim Number is required")
    @Length(min = 10, max = 10, message = "Sim Number should 10 character only")
    private String simNumber;

    private Network network;

    @NotBlank(message = "mobile number is required")
    @Length(min = 10, max = 10, message = "ifscCode should be of 11 character only")
    private String mobileNumber;

    private Long customer_Id;


}
