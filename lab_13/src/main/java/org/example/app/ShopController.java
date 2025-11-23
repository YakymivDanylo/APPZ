package org.example.app;

import org.example.discount.DiscountPolicy;
import org.example.discount.NoDiscount;
import org.example.discount.PercentageDiscount;
import org.example.domain.Cart;
import org.example.domain.CartItem;
import org.example.domain.Customer;
import org.example.domain.Product;
import org.example.notification.EmailNotificationService;
import org.example.notification.NotificationService;
import org.example.payment.CardPayment;
import org.example.payment.CashOnDelivery;
import org.example.payment.PaymentMethod;
import org.example.service.OrderService;
import org.example.service.OrderValidator;
import org.example.service.PricingService;
import org.example.shipping.CourierShipping;
import org.example.shipping.NovaPoshtaShipping;
import org.example.shipping.ShippingMethod;

import java.util.Scanner;

// Controller: єдиний вхід для користувача
// KISS/YAGNI: тільки потрібний функціонал, ніяких зайвих "на майбутнє"
public class ShopController {

    private final ProductRepository productRepository;
    private final Cart cart;
    private final Customer customer;
    private final PricingService pricingService;
    private final OrderService orderService;
    private final Scanner scanner = new Scanner(System.in);

    public ShopController(ProductRepository productRepository, Customer customer) {
        this.productRepository = productRepository;
        this.cart = new Cart();
        this.customer = customer;

        // Налаштування залежностей (DIP через інтерфейси)
        DiscountPolicy discountPolicy = new NoDiscount(); // можна змінити на PercentageDiscount
        this.pricingService = new PricingService(discountPolicy);

        NotificationService notificationService = new EmailNotificationService();
        OrderValidator orderValidator = new OrderValidator();
        this.orderService = new OrderService(pricingService, orderValidator, notificationService);
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
                case "4" -> changeDiscount();
                case "5" -> checkout();
                case "0" -> running = false;
                default -> System.out.println("Невірний вибір.");
            }
        }
        System.out.println("До побачення!");
    }

    private void printMenu() {
        System.out.println("\n=== Інтернет-магазин одягу (SOLID) ===");
        System.out.println("1) Переглянути товари");
        System.out.println("2) Додати товар у кошик");
        System.out.println("3) Переглянути кошик");
        System.out.println("4) Змінити знижку");
        System.out.println("5) Оформити замовлення");
        System.out.println("0) Вихід");
        System.out.print("Ваш вибір: ");
    }

    private void showProducts() {
        System.out.println("\n--- Список товарів ---");
        for (Product p : productRepository.findAll()) {
            System.out.println(p.getId() + ": " + p + ", кінцева ціна: " + p.getFinalPrice());
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
        }, () -> System.out.println("Товар не знайдено."));
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
        double totalNoDiscount = cart.getTotal();
        double totalWithDiscount = pricingService.calculateProductsTotal(cart);
        System.out.println("Сума без знижки: " + totalNoDiscount);
        System.out.println("Застосована знижка: " + pricingService.getDiscountName());
        System.out.println("Сума зі знижкою: " + totalWithDiscount);
    }

    private void changeDiscount() {
        System.out.println("\nОберіть знижку:");
        System.out.println("1) Без знижки");
        System.out.println("2) 10%");
        System.out.println("3) 20%");
        String choice = scanner.nextLine().trim();

        DiscountPolicy newPolicy = switch (choice) {
            case "2" -> new PercentageDiscount(10);
            case "3" -> new PercentageDiscount(20);
            default -> new NoDiscount();
        };

        pricingService.setDiscountPolicy(newPolicy);
        System.out.println("Знижку змінено на: " + newPolicy.getName());
    }

    private void checkout() {
        if (cart.isEmpty()) {
            System.out.println("Кошик порожній.");
            return;
        }

        System.out.println("\nОберіть спосіб оплати:");
        System.out.println("1) Картка");
        System.out.println("2) Готівка при отриманні");
        String payChoice = scanner.nextLine().trim();

        PaymentMethod paymentMethod = "1".equals(payChoice)
                ? new CardPayment()
                : new CashOnDelivery();

        System.out.println("\nОберіть спосіб доставки:");
        System.out.println("1) Нова Пошта");
        System.out.println("2) Кур'єр по місту");
        String shipChoice = scanner.nextLine().trim();

        ShippingMethod shippingMethod = "1".equals(shipChoice)
                ? new NovaPoshtaShipping()
                : new CourierShipping();

        try {
            var order = orderService.createOrder(customer, cart, paymentMethod, shippingMethod);
            System.out.println("\nЗамовлення оформлено!");
            System.out.println("ID: " + order.getId());
            System.out.println("Товари: " + order.getProductsTotal());
            System.out.println("Доставка (" + order.getShippingMethodName() + "): " + order.getShippingCost());
            System.out.println("Разом до сплати: " + order.getGrandTotal());
        } catch (Exception e) {
            System.out.println("Помилка оформлення замовлення: " + e.getMessage());
        }
    }
}
