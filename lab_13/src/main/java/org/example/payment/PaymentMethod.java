package org.example.payment;

// ISP: дрібний, вузький інтерфейс (тільки оплата)
// DIP: сервіси працюють через абстракцію
public interface PaymentMethod {
    boolean pay(double amount);
    String getName();
}
