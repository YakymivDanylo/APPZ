package org.example.notification;

// SRP: тільки email
// KISS: проста реалізація
public class EmailNotificationService implements NotificationService {
    @Override
    public void sendOrderConfirmation(String email, String orderId, double amount) {
        System.out.println("Надсилаємо email на " + email +
                ": Замовлення #" + orderId + " на суму " + amount + " грн підтверджено.");
    }
}
