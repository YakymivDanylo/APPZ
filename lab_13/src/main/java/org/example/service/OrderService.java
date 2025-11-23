package org.example.service;

import org.example.domain.Cart;
import org.example.domain.Customer;
import org.example.domain.Order;
import org.example.notification.NotificationService;
import org.example.payment.PaymentMethod;
import org.example.shipping.ShippingMethod;

import java.util.UUID;

// SRP: координує створення та обробку замовлення
// DIP: працює тільки з абстракціями (інтерфейсами)
// Indirection/ADP: посередник між app і payment/shipping/notification
public class OrderService {

    private final PricingService pricingService;
    private final OrderValidator orderValidator;
    private final NotificationService notificationService;

    public OrderService(PricingService pricingService,
                        OrderValidator orderValidator,
                        NotificationService notificationService) {
        this.pricingService = pricingService;
        this.orderValidator = orderValidator;
        this.notificationService = notificationService;
    }

    public Order createOrder(Customer customer,
                             Cart cart,
                             PaymentMethod paymentMethod,
                             ShippingMethod shippingMethod) {
        if (!orderValidator.isValid(customer, cart)) {
            throw new IllegalStateException("Некоректне замовлення (клієнт або кошик).");
        }

        double productsTotal = pricingService.calculateProductsTotal(cart);
        double shippingCost = shippingMethod.calculateCost(productsTotal);

        String orderId = UUID.randomUUID().toString();

        Order order = new Order(
                orderId,
                customer,
                productsTotal,
                shippingCost,
                paymentMethod.getName(),
                shippingMethod.getName()
        );

        double finalAmount = order.getGrandTotal();
        boolean paid = paymentMethod.pay(finalAmount);

        if (!paid) {
            throw new IllegalStateException("Оплата не пройшла.");
        }

        notificationService.sendOrderConfirmation(
                customer.getEmail(),
                order.getId(),
                finalAmount
        );

        cart.clear();

        return order;
    }
}
