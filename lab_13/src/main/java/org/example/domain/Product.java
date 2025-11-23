package org.example.domain;

// SRP: зберігає дані про товар
// KISS: мінімум полів
// SAP/SDP: стабільна абстракція (ядро системи)
public abstract class Product {
    private final String id;
    private final String name;
    private final double basePrice;

    protected Product(String id, String name, double basePrice) {
        this.id = id;
        this.name = name;
        this.basePrice = basePrice;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getBasePrice() {
        return basePrice;
    }

    // LSP/OCP: підкласи можуть змінювати спосіб розрахунку кінцевої ціни
    public abstract double getFinalPrice();

    @Override
    public String toString() {
        return name + " (" + id + "), базова ціна: " + basePrice;
    }
}
