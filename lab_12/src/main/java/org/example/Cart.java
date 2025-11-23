package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// GRASP:
// - Creator (створює CartItem, бо агрегує їх)
// - Information Expert (знає всі позиції, тому рахує загальну суму)
// - High Cohesion (відповідає лише за кошик)
public class Cart {
    private final List<CartItem> items = new ArrayList<>();

    public void addProduct(Product product, int quantity) {
        // Creator: саме Cart створює CartItem
        Optional<CartItem> existing = items.stream()
                .filter(i -> i.getProduct().getId().equals(product.getId()))
                .findFirst();

        if (existing.isPresent()) {
            existing.get().increase(quantity);
        } else {
            items.add(new CartItem(product, quantity));
        }
    }

    public List<CartItem> getItems() {
        return new ArrayList<>(items);
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public double getTotal() {
        // Information Expert: Cart знає всі CartItem, тому вміє рахувати загальну суму
        return items.stream()
                .mapToDouble(CartItem::getLineTotal)
                .sum();
    }

    public void clear() {
        items.clear();
    }
}
