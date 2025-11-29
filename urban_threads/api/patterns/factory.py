from django.contrib.auth.models import User

class UserFactory:
    @staticmethod
    def create_user(username, email, password, role='customer'):
        user = User.objects.create_user(username=username, email=email, password=password)
        # Тут можна додати логіку для створення розширених профілів (Manager, Admin)
        # Наприклад: if role == 'manager': ManagerProfile.objects.create(user=user)
        return user