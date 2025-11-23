package org.example.domain;

// SRP: модель замовлення
// Не знає про оплату/доставку – DIP/ADP/SDP (домен незалежний)
public class Order {
    private final String id;
    private final Customer customer;
    private final double productsTotal;
    private final double shippingCost;
    private final String paymentMethodName;
    private final String shippingMethodName;

    public Order(String id,
                 Customer customer,
                 double productsTotal,
                 double shippingCost,
                 String paymentMethodName,
                 String shippingMethodName) {
        this.id = id;
        this.customer = customer;
        this.productsTotal = productsTotal;
        this.shippingCost = shippingCost;
        this.paymentMethodName = paymentMethodName;
        this.shippingMethodName = shippingMethodName;
    }

    public String getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
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

    public String getPaymentMethodName() {
        return paymentMethodName;
    }

    public String getShippingMethodName() {
        return shippingMethodName;
    }
}
