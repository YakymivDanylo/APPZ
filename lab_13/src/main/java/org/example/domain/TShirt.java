package org.example.domain;

// LSP: повністю замінює Product
public class TShirt extends Product {

    public TShirt(String id, String name, double basePrice) {
        super(id, name, basePrice);
    }

    @Override
    public double getFinalPrice() {
        // Наприклад, невелика знижка
        return getBasePrice() * 0.95;
    }
}
