package org.example.service;

import org.example.discount.DiscountPolicy;
import org.example.domain.Cart;

// SRP: тільки розрахунки цін
// DRY: вся логіка в одному місці
// DIP: залежить від DiscountPolicy (абстракція)
public class PricingService {

    private DiscountPolicy discountPolicy;

    public PricingService(DiscountPolicy discountPolicy) {
        this.discountPolicy = discountPolicy;
    }

    public void setDiscountPolicy(DiscountPolicy discountPolicy) {
        this.discountPolicy = discountPolicy;
    }

    public double calculateProductsTotal(Cart cart) {
        double rawTotal = cart.getTotal();
        return discountPolicy.apply(rawTotal);
    }

    public String getDiscountName() {
        return discountPolicy.getName();
    }
}
