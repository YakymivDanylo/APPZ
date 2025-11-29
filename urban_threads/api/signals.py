from django.db.models.signals import post_save
from django.dispatch import receiver
from api.models import Order

@receiver(post_save, sender=Order)
def order_status_changed(sender, instance, created, **kwargs):
    if not created:
        print(f"NOTIFICATION: Order #{instance.id} status changed to {instance.status}")