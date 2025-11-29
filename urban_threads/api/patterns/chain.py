class FilterHandler:
    def __init__(self, next_handler=None):
        self._next_handler = next_handler

    def handle(self, queryset, params):
        queryset = self.apply_filter(queryset, params)
        if self._next_handler:
            return self._next_handler.handle(queryset, params)
        return queryset

    def apply_filter(self, queryset, params):
        return queryset

class PriceFilter(FilterHandler):
    def apply_filter(self, queryset, params):
        if 'min_price' in params:
            return queryset.filter(price__gte=params['min_price'])
        return queryset

class CategoryFilter(FilterHandler):
    def apply_filter(self, queryset, params):
        if 'category' in params:
            return queryset.filter(category=params['category'])
        return queryset