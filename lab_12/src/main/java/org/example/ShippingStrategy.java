package org.example;

public interface ShippingStrategy {
    double calculateShippingCost(Cart cart);
    String getName();
}
