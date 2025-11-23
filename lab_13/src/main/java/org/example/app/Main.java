package org.example.app;

import org.example.domain.Customer;

// Точка входу. Дотримання KISS: лише створює залежності та запускає контролер.
public class Main {
    public static void main(String[] args) {
        ProductRepository productRepository = new ProductRepository();
        Customer customer = new Customer("C1", "Кирило", "kirill@example.com");

        ShopController controller = new ShopController(productRepository, customer);
        controller.start();
    }
}
