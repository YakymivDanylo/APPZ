package org.example;

// GRASP: Information Expert (знає суму по позиції),
// High Cohesion (одна відповідальність - одна позиція кошика)
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
        if (delta <= 0) return;
        this.quantity += delta;
    }

    public double getLineTotal() {
        // Інформаційний експерт: має product і quantity, тому сам рахує суму
        return product.getFinalPrice() * quantity;
    }

    @Override
    public String toString() {
        return product + ", к-сть: " + quantity + ", разом: " + getLineTotal();
    }
}
