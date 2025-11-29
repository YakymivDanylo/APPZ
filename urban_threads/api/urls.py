# api/urls.py
from django.urls import path
from .views import RegisterView, ProductListView, CheckoutView, AddToCartView, DiscountView, OrderStatusUpdateView

urlpatterns = [
    path('register/', RegisterView.as_view(), name='register'),
    path('products/', ProductListView.as_view(), name='products'),
    path('checkout/', CheckoutView.as_view(), name='checkout'),
    path('cart/add/', AddToCartView.as_view(), name='add-to-cart'),
    path('discount/', DiscountView.as_view(), name='discount'),
    path('orders/<int:pk>/status/', OrderStatusUpdateView.as_view(), name='update-status'),
]