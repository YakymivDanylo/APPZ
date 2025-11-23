package org.example.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// SRP: відповідає тільки за кошик
// Information Expert: знає всі позиції, тому рахує суму
public class Cart {
    private final List<CartItem> items = new ArrayList<>();

    public void addProduct(Product product, int quantity) {
        for (CartItem item : items) {
            if (item.getProduct().getId().equals(product.getId())) {
                item.increase(quantity);
                return;
            }
        }
        items.add(new CartItem(product, quantity));
    }

    public List<CartItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public double getTotal() {
        return items.stream()
                .mapToDouble(CartItem::getLineTotal)
                .sum();
    }

    public void clear() {
        items.clear();
    }
}
