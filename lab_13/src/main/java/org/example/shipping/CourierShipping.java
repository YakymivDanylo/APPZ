package org.example.shipping;

public class CourierShipping implements ShippingMethod {
    @Override
    public double calculateCost(double productsTotal) {
        // Безкоштовно від певної суми
        return productsTotal >= 1500 ? 0.0 : 120.0;
    }

    @Override
    public String getName() {
        return "Кур'єр по місту";
    }
}
