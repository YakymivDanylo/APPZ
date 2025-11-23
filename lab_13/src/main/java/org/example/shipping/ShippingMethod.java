package org.example.shipping;

// LSP/OCP/DIP аналогічно з оплатою
public interface ShippingMethod {
    double calculateCost(double productsTotal);
    String getName();
}
