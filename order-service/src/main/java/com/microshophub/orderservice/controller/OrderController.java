package com.microshophub.orderservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;

import com.microshophub.orderservice.OrderService;
import com.microshophub.orderservice.dto.PostOrderRequest;

@RestController
@RequestMapping("/api")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * Méthode pour enregistrer une nouvelle commande
     * 
     * @param postOrderRequest la commande à enregistrer
     */
    @PostMapping("/orders")
    public ResponseEntity<?> register(@Valid @RequestBody PostOrderRequest registerRequest) {
        try {
            orderService.createOrder(registerRequest.getUserId(), registerRequest.getProduct(), registerRequest.getQuantity());
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Méthode pour récupérer la liste des commandes d'un utilisateur
     * 
     * @param userId l'identifiant de l'utilisateur
     */
    @GetMapping("/orders/users/{userId}")
    public ResponseEntity<?> getOrders(@PathVariable String userId) {
        try {
            return ResponseEntity.ok(orderService.getOrders(Long.parseLong(userId)));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

