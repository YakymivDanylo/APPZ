from django.test import TestCase
from django.urls import reverse
from rest_framework import status
from rest_framework.test import APITestCase
from django.contrib.auth.models import User
from .models import Product, Order, Cart, CartItem


class UrbanThreadsAPITests(APITestCase):

    def setUp(self):
        # Створення тестових даних перед кожним тестом
        self.user = User.objects.create_user(username='testuser', password='password123')
        self.client.force_authenticate(user=self.user)

        # Товари для тестів
        self.p1 = Product.objects.create(name="Summer T-Shirt", price=100, stock=10, category="Summer")
        self.p2 = Product.objects.create(name="Winter Coat", price=500, stock=5, category="Winter")

    # --- 1. Тест реєстрації (Factory Method) ---
    def test_register_factory(self):
        url = reverse('register')
        data = {
            "username": "newuser",
            "email": "new@test.com",
            "password": "pass"
        }
        response = self.client.post(url, data)
        self.assertEqual(response.status_code, status.HTTP_201_CREATED)
        self.assertTrue(User.objects.filter(username='newuser').exists())

    # --- 2. Тест фільтрації (Chain of Responsibility) ---
    def test_product_filter_chain(self):
        url = reverse('products')

        # Сценарій: Фільтр за ціною > 200 (має знайти тільки Winter Coat)
        response = self.client.get(url, {'min_price': 200})
        self.assertEqual(len(response.data), 1)
        self.assertEqual(response.data[0]['name'], "Winter Coat")

        # Сценарій: Фільтр за категорією 'Summer' (має знайти тільки T-Shirt)
        response_cat = self.client.get(url, {'category': 'Summer'})
        self.assertEqual(len(response_cat.data), 1)
        self.assertEqual(response_cat.data[0]['name'], "Summer T-Shirt")

    # --- 3. Тест додавання в кошик (Facade) ---
    def test_cart_facade(self):
        url = reverse('add-to-cart')
        data = {"product_id": self.p1.id, "quantity": 2}

        response = self.client.post(url, data)
        self.assertEqual(response.status_code, status.HTTP_200_OK)

        # Перевіряємо, чи фасад дійсно створив записи в БД
        cart = Cart.objects.get(user=self.user)
        cart_item = CartItem.objects.get(cart=cart, product=self.p1)
        self.assertEqual(cart_item.quantity, 2)

    # --- 4. Тест оформлення замовлення (Builder) ---
    def test_checkout_builder(self):
        # Спочатку додамо щось у кошик (підготовка)
        cart = Cart.objects.create(user=self.user)
        CartItem.objects.create(cart=cart, product=self.p1, quantity=1)

        url = reverse('checkout')
        data = {
            "address": "Kyiv, Khreshchatyk 1",
            "payment": "Credit Card"
        }
        response = self.client.post(url, data)
        self.assertEqual(response.status_code, status.HTTP_201_CREATED)

        # Перевіряємо, чи Builder правильно зібрав об'єкт Order
        order = Order.objects.latest('id')
        self.assertEqual(order.shipping_address, "Kyiv, Khreshchatyk 1")
        self.assertEqual(order.payment_method, "Credit Card")
        # Перевіряємо, чи перенеслись товари
        self.assertEqual(order.orderitem_set.first().product, self.p1)

    # --- 5. Тест знижок (Strategy) ---
    def test_discount_strategy(self):
        url = reverse('discount')

        # Сценарій: Промокод SALE20 (20% знижки)
        data_sale = {"total": 100, "promo_code": "SALE20"}
        resp_sale = self.client.post(url, data_sale)
        self.assertEqual(resp_sale.data['final_price'], 80.0)  # 100 - 20%

        # Сценарій: Без промокоду (Стратегія NoDiscount)
        data_none = {"total": 100}
        resp_none = self.client.post(url, data_none)
        self.assertEqual(resp_none.data['final_price'], 100.0)

    # --- 6. Тест сповіщення (Observer) ---
    def test_order_observer(self):
        # Створюємо замовлення
        order = Order.objects.create(user=self.user, status='new')

        # Використовуємо наш НОВИЙ endpoint для зміни статусу
        # Переконайтесь, що додали URL з ім'ям 'update-status' у попередньому кроці
        url = reverse('update-status', args=[order.id])
        data = {"status": "shipped"}

        # Для перевірки print-а (сигналу) можна використати mock,
        # але тут достатньо перевірити, що статус змінився успішно через API
        response = self.client.post(url, data)

        self.assertEqual(response.status_code, status.HTTP_200_OK)
        order.refresh_from_db()
        self.assertEqual(order.status, "shipped")
        # При зміні статусу в консолі сервера має з'явитись повідомлення від Observer
