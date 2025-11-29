from django.shortcuts import render

# api/views.py
from rest_framework.views import APIView
from rest_framework.response import Response
from rest_framework.permissions import IsAuthenticated, AllowAny
from .models import Product, CartItem, Order
from .serializers import ProductSerializer
# Імпорт патернів
from .patterns.factory import UserFactory
from .patterns.chain import PriceFilter, CategoryFilter
from .patterns.builder import OrderBuilder
from .patterns.facade import CartFacade
from .patterns.strategy import PercentageDiscount, NoDiscount


# 1. Реєстрація (Factory Method)
class RegisterView(APIView):
    permission_classes = [AllowAny]

    def post(self, request):
        data = request.data
        UserFactory.create_user(data['username'], data['email'], data['password'])
        return Response({"message": "User created via Factory"}, status=201)


# 2. Список товарів (Chain of Responsibility)
class ProductListView(APIView):
    def get(self, request):
        products = Product.objects.all()
        # Ланцюжок: Спочатку ціна, потім категорія
        chain = PriceFilter(CategoryFilter())
        filtered_products = chain.handle(products, request.query_params)

        serializer = ProductSerializer(filtered_products, many=True)
        return Response(serializer.data)


# 3. Оформлення замовлення (Builder)
class CheckoutView(APIView):
    permission_classes = [IsAuthenticated]

    def post(self, request):
        # Використовуємо Builder для створення складного об'єкта
        cart_items = CartItem.objects.filter(cart__user=request.user)
        builder = OrderBuilder(request.user)
        order = (builder
                 .add_items_from_cart(cart_items)
                 .set_shipping_address(request.data.get('address', ''))
                 .set_payment_method(request.data.get('payment', 'Cash'))
                 .build())
        return Response({"order_id": order.id, "status": "Created via Builder"}, status=201)


# 4. Додавання в кошик (Facade)
class AddToCartView(APIView):
    permission_classes = [IsAuthenticated]

    def post(self, request):
        try:
            CartFacade.add_item(
                request.user,
                request.data['product_id'],
                int(request.data.get('quantity', 1))
            )
            return Response({"status": "Item added via Facade"})
        except Exception as e:
            return Response({"error": str(e)}, status=400)


# 5. Розрахунок знижки (Strategy)
class DiscountView(APIView):
    def post(self, request):
        code = request.data.get('promo_code')
        total = float(request.data.get('total', 0))

        if code == 'SALE20':
            strategy = PercentageDiscount(20)
        else:
            strategy = NoDiscount()

        final_price = strategy.calculate(total)
        return Response({"final_price": final_price, "strategy": strategy.__class__.__name__})


# 6. Зміна статусу замовлення (Observer Trigger)
class OrderStatusUpdateView(APIView):
    def post(self, request, pk):
        try:
            order = Order.objects.get(pk=pk)
            new_status = request.data.get('status')

            if new_status in dict(Order.STATUS_CHOICES):
                order.status = new_status
                order.save()  # <-- Саме тут спрацює Signal (Observer) з api/signals.py
                return Response({"message": f"Status updated to {new_status}"})
            else:
                return Response({"error": "Invalid status"}, status=400)
        except Order.DoesNotExist:
            return Response({"error": "Order not found"}, status=404)