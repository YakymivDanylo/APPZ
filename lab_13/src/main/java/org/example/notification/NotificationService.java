package org.example.notification;

// ISP: окремий інтерфейс для сповіщень
public interface NotificationService {
    void sendOrderConfirmation(String email, String orderId, double amount);
}
