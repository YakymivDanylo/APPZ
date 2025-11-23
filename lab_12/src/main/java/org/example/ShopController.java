package org.example;

import java.util.Scanner;

// GRASP:
// - Controller (керує сценарієм роботи з магазином, обробляє "запити користувача")
// - Low Coupling (делегує роботу Cart, ProductRepository, OrderService)
public class ShopController {

    private final ProductRepository productRepository;
    private final OrderService orderService;
    private final Cart cart;
    private final Customer customer;
    private final Scanner scanner = new Scanner(System.in);

    public ShopController(ProductRepository productRepository,
                          OrderService orderService,
                          Customer customer) {
        this.productRepository = productRepository;
        this.orderService = orderService;
        this.customer = customer;
        this.cart = new Cart();
    }

    public void start() {
        boolean running = true;
        while (running) {
            printMenu();
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> showProducts();
                case "2" -> addToCart();
                case "3" -> showCart();
                case "4" -> checkout();
                case "0" -> running = false;
                default -> System.out.println("Невірний вибір.");
            }
        }
        System.out.println("До побачення!");
    }

    private void printMenu() {
        System.out.println("\n=== Інтернет-магазин одягу ===");
        System.out.println("1) Переглянути товари");
        System.out.println("2) Додати товар у кошик");
        System.out.println("3) Переглянути кошик");
        System.out.println("4) Оформити замовлення");
        System.out.println("0) Вихід");
        System.out.print("Ваш вибір: ");
    }

    private void showProducts() {
        System.out.println("\n--- Список товарів ---");
        for (Product p : productRepository.findAll()) {
            System.out.println(p.getId() + ": " + p);
        }
    }

    private void addToCart() {
        System.out.print("Введіть ID товару: ");
        String id = scanner.nextLine().trim();
        productRepository.findById(id).ifPresentOrElse(product -> {
            System.out.print("Кількість: ");
            int qty = Integer.parseInt(scanner.nextLine().trim());
            cart.addProduct(product, qty);
            System.out.println("Товар додано у кошик.");
        }, () -> System.out.println("Товар з таким ID не знайдено."));
    }

    private void showCart() {
        System.out.println("\n--- Кошик ---");
        if (cart.isEmpty()) {
            System.out.println("Кошик порожній.");
            return;
        }
        for (CartItem item : cart.getItems()) {
            System.out.println(item);
        }
        System.out.println("Разом по товарах: " + cart.getTotal() + " грн");
    }

    private void checkout() {
        if (cart.isEmpty()) {
            System.out.println("Кошик порожній, спочатку додайте товари.");
            return;
        }

        System.out.println("\nОберіть спосіб оплати:");
        System.out.println("1) Картка");
        System.out.println("2) Готівка при отриманні");
        String payChoice = scanner.nextLine().trim();

        PaymentStrategy payment;
        if ("1".equals(payChoice)) {
            payment = new CardPayment();
        } else {
            payment = new CashOnDeliveryPayment();
        }

        System.out.println("\nОберіть спосіб доставки:");
        System.out.println("1) Нова Пошта");
        System.out.println("2) Кур'єр по місту");
        String shipChoice = scanner.nextLine().trim();

        ShippingStrategy shipping;
        if ("1".equals(shipChoice)) {
            shipping = new NovaPoshtaShipping();
        } else {
            shipping = new CourierShipping();
        }

        Order order = orderService.createOrder(customer, cart, payment, shipping);

        System.out.println("\nСума по товарах: " + order.getProductsTotal());
        System.out.println("Доставка: " + order.getShippingCost());
        System.out.println("До оплати: " + order.getGrandTotal());

        if (orderService.processPayment(order)) {
            orderService.shipOrder(order);
            cart.clear();
            System.out.println("Замовлення оформлено!");
        } else {
            System.out.println("Помилка оплати.");
        }
    }
}
