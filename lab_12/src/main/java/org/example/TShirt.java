package org.example;

// GRASP: Polymorphism (варіативна поведінка getFinalPrice())
public class TShirt extends Product {
    public TShirt(String id, String name, double basePrice, String size) {
        super(id, name, basePrice, size);
    }

    @Override
    public double getFinalPrice() {
        // Наприклад, футболки мають знижку 10%
        return getBasePrice() * 0.9;
    }
}
