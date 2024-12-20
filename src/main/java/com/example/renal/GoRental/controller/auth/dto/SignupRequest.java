package com.example.renal.GoRental.controller.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SignupRequest {

    private String name;
    private String username;
    private String email;
    private String password;

}