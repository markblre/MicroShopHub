package com.microshophub.orderservice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microshophub.orderservice.model.Order;
import com.microshophub.orderservice.repository.OrderRepository;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    /**
     * Méthode pour créer une commande.
     * 
     * @param userId L'identifiant de l'utilisateur
     * @param product Le produit commandé
     * @param quantity La quantité commandée
     */
    public void createOrder(Long userId, String product, int quantity) {
        Order order = new Order(userId, product, quantity);

        orderRepository.save(order);
    }

    /**
     * Méthode pour récupérer les commandes d'un utilisateur.
     * 
     * @param userId L'identifiant de l'utilisateur
     */
    public List<Order> getOrders(Long userId) {
        return orderRepository.findByUserId(userId);
    }
}
