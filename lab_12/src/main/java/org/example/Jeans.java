package org.example;

public class Jeans extends Product {
    public Jeans(String id, String name, double basePrice, String size) {
        super(id, name, basePrice, size);
    }

    @Override
    public double getFinalPrice() {
        // Джинси без знижки
        return getBasePrice();
    }
}
