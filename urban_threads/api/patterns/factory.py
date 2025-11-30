from django.contrib.auth.models import User

class UserFactory:
    @staticmethod
    def create_user(username, email, password, role='customer'):
        user = User.objects.create_user(username=username, email=email, password=password)
        return user