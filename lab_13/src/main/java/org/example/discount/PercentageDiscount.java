package org.example.discount;

// KISS: проста реалізація відсоткової знижки
public class PercentageDiscount implements DiscountPolicy {
    private final double percent; // 0..100

    public PercentageDiscount(double percent) {
        if (percent < 0 || percent > 100) {
            throw new IllegalArgumentException("Відсоток знижки має бути 0..100");
        }
        this.percent = percent;
    }

    @Override
    public double apply(double originalPrice) {
        return originalPrice * (1.0 - percent / 100.0);
    }

    @Override
    public String getName() {
        return "Знижка " + percent + "%";
    }
}
