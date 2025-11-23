package org.example;

public class Shoes extends Product {
    public Shoes(String id, String name, double basePrice, String size) {
        super(id, name, basePrice, size);
    }

    @Override
    public double getFinalPrice() {
        // Взуття має націнку 15% (наприклад за гарантію)
        return getBasePrice() * 1.15;
    }
}
