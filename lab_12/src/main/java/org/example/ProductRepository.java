package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// GRASP: Pure Fabrication (штучний сервісний клас для роботи з товарами),
// Low Coupling (інші класи не знають, як саме зберігаються товари)
public class ProductRepository {

    private final List<Product> products = new ArrayList<>();

    public ProductRepository() {
        // Тестові дані
        products.add(new TShirt("T1", "Футболка Urban Basic", 600, "M"));
        products.add(new TShirt("T2", "Футболка Oversize Black", 750, "L"));
        products.add(new Jeans("J1", "Джинси Slim Fit", 1200, "32"));
        products.add(new Shoes("S1", "Кросівки Street Runner", 2000, "42"));
    }

    public List<Product> findAll() {
        return new ArrayList<>(products);
    }

    public Optional<Product> findById(String id) {
        return products.stream()
                .filter(p -> p.getId().equalsIgnoreCase(id))
                .findFirst();
    }
}
