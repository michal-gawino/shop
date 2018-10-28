package com.michal.enumerated;


public enum UserRole {

    CUSTOMER("CUSTOMER"),
    ADMIN("ADMIN");

    private String role;

    UserRole(String role) {
        this.role = role;
    }
}
