package com.example.simmanagement.dto;

import com.example.simmanagement.constant.Network;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
@Data
public class SimDTO {
    private Long id;

//    @NotBlank(message = "Sim Number is required")
//    @Length(min = 10, max = 10, message = "Sim Number should 10 character only")
    private String simNumber;

//    @NotBlank(message = "Network  is required")
    private Network network;

//    @NotBlank(message = "mobile number is required")
//    @Length(min = 10, max = 10, message = "ifscCode should 11 character only")
    private String mobileNumber;

//    @NotBlank(message = "Network  is required")
    private long CustomerId;


}
