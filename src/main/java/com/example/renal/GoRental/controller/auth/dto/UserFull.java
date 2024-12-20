package com.example.renal.GoRental.controller.auth.dto;

import com.example.renal.GoRental.model.User;
import com.example.renal.GoRental.model.UserRoles;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserFull {

    private String name;
    private String userName;
    private UserRoles role;

    public static UserFull of(User user) {
        return UserFull.builder()
                .userName(user.getUsername())
                .role(user.getRole())
                .name(user.getName()).build();
    }
}
