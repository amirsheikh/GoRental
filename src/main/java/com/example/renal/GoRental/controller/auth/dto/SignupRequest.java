package com.example.renal.GoRental.controller.auth.dto;

import lombok.Data;

@Data
public class SignupRequest {

    private String name;
    private String username;
    private String email;
    private String password;

}