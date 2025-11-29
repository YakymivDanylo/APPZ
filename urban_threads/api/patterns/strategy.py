from abc import ABC, abstractmethod

class DiscountStrategy(ABC):
    @abstractmethod
    def calculate(self, total_price):
        pass

class PercentageDiscount(DiscountStrategy):
    def __init__(self, percent):
        self.percent = percent
    def calculate(self, total_price):
        return float(total_price) * (1 - self.percent / 100)

class NoDiscount(DiscountStrategy):
    def calculate(self, total_price):
        return float(total_price)