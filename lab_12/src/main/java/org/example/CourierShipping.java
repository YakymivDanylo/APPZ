package org.example;

// Кур'єрська доставка по місту
public class CourierShipping implements ShippingStrategy {
    @Override
    public double calculateShippingCost(Cart cart) {
        // умовно: безкоштовно при сумі > 1500
        double total = cart.getTotal();
        return total > 1500 ? 0.0 : 120.0;
    }

    @Override
    public String getName() {
        return "Кур'єр по місту";
    }
}
