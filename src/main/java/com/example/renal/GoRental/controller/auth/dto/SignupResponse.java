package com.example.renal.GoRental.controller.auth.dto;

import com.example.renal.GoRental.controller.auth.RegistartionStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SignupResponse {

    private String message;
    private RegistartionStatus status;


}
