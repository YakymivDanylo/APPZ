package org.example.shipping;

public class NovaPoshtaShipping implements ShippingMethod {
    @Override
    public double calculateCost(double productsTotal) {
        return 80.0;
    }

    @Override
    public String getName() {
        return "Нова Пошта";
    }
}
