package org.example.domain;

// SRP: одна позиція в кошику
// DRY: логіка підрахунку зосереджена тут, не дублюється
public class CartItem {
    private final Product product;
    private int quantity;

    public CartItem(Product product, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Кількість має бути > 0");
        }
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void increase(int delta) {
        if (delta > 0) {
            quantity += delta;
        }
    }

    public double getLineTotal() {
        return product.getFinalPrice() * quantity;
    }

    @Override
    public String toString() {
        return product.getName() + " x " + quantity + " = " + getLineTotal();
    }
}
