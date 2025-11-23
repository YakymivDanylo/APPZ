package org.example.discount;

public class NoDiscount implements DiscountPolicy {
    @Override
    public double apply(double originalPrice) {
        return originalPrice;
    }

    @Override
    public String getName() {
        return "Без знижки";
    }
}
