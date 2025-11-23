package org.example;

// Замовлення як доменна модель
public class Order {
    private final String id;
    private final Customer customer;
    private final Cart cart;
    private final PaymentStrategy paymentStrategy;
    private final ShippingStrategy shippingStrategy;
    private final double productsTotal;
    private final double shippingCost;

    public Order(String id,
                 Customer customer,
                 Cart cart,
                 PaymentStrategy paymentStrategy,
                 ShippingStrategy shippingStrategy) {
        this.id = id;
        this.customer = customer;
        this.cart = cart;
        this.paymentStrategy = paymentStrategy;
        this.shippingStrategy = shippingStrategy;
        this.productsTotal = cart.getTotal();
        this.shippingCost = shippingStrategy.calculateShippingCost(cart);
    }

    public String getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Cart getCart() {
        return cart;
    }

    public PaymentStrategy getPaymentStrategy() {
        return paymentStrategy;
    }

    public ShippingStrategy getShippingStrategy() {
        return shippingStrategy;
    }

    public double getProductsTotal() {
        return productsTotal;
    }

    public double getShippingCost() {
        return shippingCost;
    }

    public double getGrandTotal() {
        return productsTotal + shippingCost;
    }
}
