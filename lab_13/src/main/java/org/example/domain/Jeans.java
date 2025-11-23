package org.example.domain;

public class Jeans extends Product {

    public Jeans(String id, String name, double basePrice) {
        super(id, name, basePrice);
    }

    @Override
    public double getFinalPrice() {
        // Без змін ціни
        return getBasePrice();
    }
}
