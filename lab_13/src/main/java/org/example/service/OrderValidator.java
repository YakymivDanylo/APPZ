package org.example.service;

import org.example.domain.Cart;
import org.example.domain.Customer;

// SRP: тільки валідація
public class OrderValidator {

    public boolean isValid(Customer customer, Cart cart) {
        return customer != null && cart != null && !cart.isEmpty();
    }
}
