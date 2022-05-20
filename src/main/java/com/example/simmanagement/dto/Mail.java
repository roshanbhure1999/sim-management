package com.example.simmanagement.dto;

import lombok.Data;

@Data
public class Mail {
    private String mailFrom;

    private String[] mailTo;

    private String mailSubject;

    private String mailContent;

    private String contentType;

}
