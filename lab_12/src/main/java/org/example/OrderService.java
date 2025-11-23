package org.example;

import java.util.UUID;

// GRASP:
// - Pure Fabrication (службовий клас для оформлення замовлень)
// - Indirection (посередник між Controller та Payment/Shipping)
// - Low Coupling (контролер не знає деталей оплати/доставки)
public class OrderService {

    public Order createOrder(Customer customer,
                             Cart cart,
                             PaymentStrategy payment,
                             ShippingStrategy shipping) {
        if (cart.isEmpty()) {
            throw new IllegalStateException("Кошик порожній, не можна створити замовлення.");
        }
        String orderId = UUID.randomUUID().toString();
        return new Order(orderId, customer, cart, payment, shipping);
    }

    public boolean processPayment(Order order) {
        double amount = order.getGrandTotal();
        return order.getPaymentStrategy().pay(amount);
    }

    public void shipOrder(Order order) {
        System.out.println("Відправляємо замовлення " + order.getId()
                + " через " + order.getShippingStrategy().getName()
                + ". До сплати: " + order.getGrandTotal() + " грн");
    }
}
