package org.example.discount;

// OCP: нові знижки додаються через нові реалізації
// DIP: абстракція, від якої залежать сервіси
public interface DiscountPolicy {
    double apply(double originalPrice);
    String getName();
}
