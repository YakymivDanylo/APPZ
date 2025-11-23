package org.example.payment;

public class CashOnDelivery implements PaymentMethod {
    @Override
    public boolean pay(double amount) {
        System.out.println("Оплата готівкою при отриманні. Сума " + amount + " грн");
        return true;
    }

    @Override
    public String getName() {
        return "Готівка при отриманні";
    }
}
