package com.microshophub.userservice.dto;

import jakarta.validation.constraints.*;

public class LoginRequest {

    @NotBlank(message = "Le nom d'utilisateur ne peut pas être vide")
    @NotNull(message = "Le nom d'utilisateur ne peut pas être vide")
    @NotEmpty(message = "Le nom d'utilisateur ne peut pas être vide")
    private String username;

    @NotNull(message = "Le mot de passe ne peut pas être vide")
    @NotEmpty(message = "Le mot de passe ne peut pas être vide")
    private String password;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}