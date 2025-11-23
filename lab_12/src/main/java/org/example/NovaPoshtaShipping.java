package org.example;

// Доставка Новою Поштою
public class NovaPoshtaShipping implements ShippingStrategy {
    @Override
    public double calculateShippingCost(Cart cart) {
        // умовно: фіксована ціна
        return 80.0;
    }

    @Override
    public String getName() {
        return "Нова Пошта";
    }
}
