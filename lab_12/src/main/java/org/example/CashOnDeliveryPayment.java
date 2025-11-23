package org.example;

// Оплата готівкою при отриманні
public class CashOnDeliveryPayment implements PaymentStrategy {
    @Override
    public boolean pay(double amount) {
        System.out.println("Оплата готівкою при отриманні. Сума: " + amount + " грн");
        return true;
    }

    @Override
    public String getName() {
        return "Готівка при отриманні";
    }
}
