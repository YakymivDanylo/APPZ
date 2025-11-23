package org.example.app;

import org.example.domain.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

// SRP: тільки джерело даних про товари
// KISS: просто in-memory список
public class ProductRepository {

    private final List<Product> products = new ArrayList<>();

    public ProductRepository() {
        // Тестові товари
        products.add(new TShirt("T1", "Футболка Basic", 600));
        products.add(new TShirt("T2", "Футболка Oversize Black", 750));
        products.add(new Jeans("J1", "Джинси Slim", 1200));
        products.add(new Shoes("S1", "Кросівки Street", 2100));
    }

    public List<Product> findAll() {
        return Collections.unmodifiableList(products);
    }

    public Optional<Product> findById(String id) {
        return products.stream()
                .filter(p -> p.getId().equalsIgnoreCase(id))
                .findFirst();
    }
}
