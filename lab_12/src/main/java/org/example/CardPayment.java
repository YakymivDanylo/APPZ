package org.example;

// Оплата карткою
public class CardPayment implements PaymentStrategy {
    @Override
    public boolean pay(double amount) {
        System.out.println("Оплата карткою на суму " + amount + " грн");
        // тут могла б бути реальна інтеграція
        return true;
    }

    @Override
    public String getName() {
        return "Оплата карткою";
    }
}
