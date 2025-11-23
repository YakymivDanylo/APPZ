package org.example;

public class Main {
    public static void main(String[] args) {
        ProductRepository productRepository = new ProductRepository();
        OrderService orderService = new OrderService();
        Customer customer = new Customer("C1", "Кирило", "kirill@example.com");

        ShopController controller = new ShopController(productRepository, orderService, customer);
        controller.start();
    }
}
