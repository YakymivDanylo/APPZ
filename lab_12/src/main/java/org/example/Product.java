package org.example;

// GRASP: Information Expert (знає свою ціну),
// High Cohesion, Polymorphism (базовий клас для різних товарів),
// Protected Variations (стабільний інтерфейс для всіх типів товарів)
public abstract class Product {
    private final String id;
    private final String name;
    private final double basePrice;
    private final String size;

    public Product(String id, String name, double basePrice, String size) {
        this.id = id;
        this.name = name;
        this.basePrice = basePrice;
        this.size = size;
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

    public String getSize() {
        return size;
    }

    /**
     * Кінцева ціна може відрізнятись у підкласів (знижки, націнки тощо)
     */
    public abstract double getFinalPrice();

    @Override
    public String toString() {
        return name + " (" + size + "), ціна: " + getFinalPrice();
    }
}
