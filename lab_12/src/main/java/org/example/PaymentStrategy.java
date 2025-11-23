package org.example;

// GRASP:
// - Protected Variations (стабільний інтерфейс оплати)
// - Polymorphism (різні способи оплати)
// - Low Coupling (OrderService не залежить від конкретних класів оплати)
public interface PaymentStrategy {
    boolean pay(double amount);
    String getName();
}
