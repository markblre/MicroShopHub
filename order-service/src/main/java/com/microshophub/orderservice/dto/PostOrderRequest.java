package com.microshophub.orderservice.dto;

import jakarta.validation.constraints.*;

public class PostOrderRequest {

    @NotNull(message = "L'identifiant de l'utilisateur ne peut pas être vide")
    private Long userId;

    @NotBlank(message = "Le produit ne peut pas être vide")
    @NotNull(message = "Le produit ne peut pas être vide")
    @NotEmpty(message = "Le produit ne peut pas être vide")
    private String product;

    @Min(value = 1, message = "La quantité doit être supérieure à 0")
    private int quantity;

    public Long getUserId() {
        return userId;
    }

    public String getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }
}
