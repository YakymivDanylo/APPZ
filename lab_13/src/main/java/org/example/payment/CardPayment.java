package org.example.payment;

public class CardPayment implements PaymentMethod {
    @Override
    public boolean pay(double amount) {
        System.out.println("Оплата карткою на суму " + amount + " грн");
        return true;
    }

    @Override
    public String getName() {
        return "Картка";
    }
}
