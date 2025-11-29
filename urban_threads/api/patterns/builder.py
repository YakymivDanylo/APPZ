from rest_framework.response import Response
from rest_framework.views import APIView
from api.models import Order, OrderItem, CartItem


class OrderBuilder:
    def __init__(self, user):
        self.order = Order(user=user, status='draft')

    def add_items_from_cart(self, cart_items):
        # Логіка перенесення товарів з кошика в замовлення
        self.items = cart_items
        return self

    def set_shipping_address(self, address):
        self.order.shipping_address = address
        return self

    def set_payment_method(self, method):
        self.order.payment_method = method
        return self

    def build(self):
        self.order.save()
        for item in self.items:
            OrderItem.objects.create(order=self.order, product=item.product, qty=item.quantity)
        return self.order


# views.py
class CheckoutView(APIView):
    def post(self, request):
        builder = OrderBuilder(request.user)
        cart_items = CartItem.objects.filter(user=request.user)

        order = (builder
                 .add_items_from_cart(cart_items)
                 .set_shipping_address(request.data['address'])
                 .set_payment_method(request.data['payment_method'])
                 .build())

        return Response({"order_id": order.id, "status": "Created"}, status=201)