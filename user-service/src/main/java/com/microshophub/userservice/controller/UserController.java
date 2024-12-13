package com.microshophub.userservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;

import com.microshophub.userservice.UserService;
import com.microshophub.userservice.dto.LoginRequest;
import com.microshophub.userservice.dto.RegisterRequest;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Méthode pour enregistrer un nouvel utilisateur.
     * 
     * @param user nom d'utilisateur
     * @param password mot de passe en clair
     * @return un jeton JWT pour l'utilisateur nouvellement créé
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest registerRequest) {
        System.err.println("--> register");
        try {
            String token = userService.registerUser(registerRequest.getUsername(), registerRequest.getPassword());
            return ResponseEntity.ok().body(token);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Méthode pour authentifier un utilisateur.
     * 
     * @param user nom d'utilisateur
     * @param password mot de passe en clair
     * @return un jeton JWT pour l'utilisateur authentifié
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            String token = userService.authenticateUser(loginRequest.getUsername(), loginRequest.getPassword());
            return ResponseEntity.ok().body(token);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

