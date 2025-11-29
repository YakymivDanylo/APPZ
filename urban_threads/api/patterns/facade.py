from api.models import Cart, CartItem, Product


class CartFacade:
    @staticmethod
    def add_item(user, product_id, quantity):
        product = Product.objects.get(id=product_id)
        if product.stock < quantity:
            raise Exception("Not enough items in stock")

        cart, _ = Cart.objects.get_or_create(user=user)
        cart_item, created = CartItem.objects.get_or_create(cart=cart, product=product)

        if not created:
            cart_item.quantity += quantity
        else:
            cart_item.quantity = quantity
        cart_item.save()
        return cart