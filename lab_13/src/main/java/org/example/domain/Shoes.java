package org.example.domain;

public class Shoes extends Product {

    public Shoes(String id, String name, double basePrice) {
        super(id, name, basePrice);
    }

    @Override
    public double getFinalPrice() {
        // Наприклад, націнка за сервіс
        return getBasePrice() * 1.1;
    }
}
