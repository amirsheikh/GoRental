package com.example.renal.GoRental.controller.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetTokenResponse {
    private String accessToken;
    private String refreshToken;
}
