package com.example.renal.GoRental.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;


public class UserContextUtil {

    /**
     * Retrieves the current authenticated user.
     * @return the username of the authenticated user, or null if no user is authenticated.
     */
    public static String getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof String) {
            return (String) authentication.getPrincipal();
        }
        return null;
    }
}
