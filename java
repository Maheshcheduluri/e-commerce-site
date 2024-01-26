class Product:
    def __init__(self, product_id, name, price, stock_quantity):
        self.product_id = product_id
        self.name = name
        self.price = price
        self.stock_quantity = stock_quantity

    def display_product_info(self):
        print(f"Product ID: {self.product_id}")
        print(f"Name: {self.name}")
        print(f"Price: ${self.price}")
        print(f"Stock Quantity: {self.stock_quantity}\n")


class ShoppingCart:
    def __init__(self):
        self.items = []

    def add_to_cart(self, product, quantity):
        if product.stock_quantity >= quantity:
            self.items.append({"product": product, "quantity": quantity})
            product.stock_quantity -= quantity
            print(f"{quantity} {product.name}(s) added to the cart.")
        else:
            print("Insufficient stock quantity.")

    def remove_from_cart(self, product, quantity):
        for item in self.items:
            if item["product"] == product:
                if item["quantity"] >= quantity:
                    item["quantity"] -= quantity
                    product.stock_quantity += quantity
                    print(f"{quantity} {product.name}(s) removed from the cart.")
                    if item["quantity"] == 0:
                        self.items.remove(item)
                else:
                    print("Invalid quantity to remove.")
                return
        print("Product not found in the cart.")

    def display_cart(self):
        total_cost = 0
        print("Shopping Cart:")
        for item in self.items:
            product = item["product"]
            quantity = item["quantity"]
            print(f"{quantity} {product.name}(s) - ${product.price * quantity}")
            total_cost += product.price * quantity
        print(f"Total Cost: ${total_cost}\n")


class User:
    def __init__(self, user_id, username, password):
        self.user_id = user_id
        self.username = username
        self.password = password
        self.shopping_cart = ShoppingCart()
        self.order_history = []

    def login(self, entered_password):
        return entered_password == self.password

    def place_order(self):
        order_details = {"cart": self.shopping_cart.items.copy(), "total_cost": 0}
        for item in order_details["cart"]:
            product = item["product"]
            quantity = item["quantity"]
            order_details["total_cost"] += product.price * quantity
        self.order_history.append(order_details)
        self.shopping_cart.items = []  # Clear the cart after placing the order
        print("Order placed successfully!\n")


# Example Usage:

# Create Products
product1 = Product(1, "Laptop", 800, 10)
product2 = Product(2, "Phone", 400, 15)

# Create User
user1 = User(101, "user123", "password")

# Login
entered_password = input("Enter your password: ")
if user1.login(entered_password):
    print("Login successful!\n")
else:
    print("Incorrect password. Exiting...\n")
    exit()

# Add products to the cart
user1.shopping_cart.add_to_cart(product1, 2)
user1.shopping_cart.add_to_cart(product2, 3)

# Display the cart
user1.shopping_cart.display_cart()

# Remove products from the cart
user1.shopping_cart.remove_from_cart(product1, 1)
user1.shopping_cart.remove_from_cart(product2, 2)

# Display the updated cart
user1.shopping_cart.display_cart()

# Place an order
user1.place_order()

# Display order history
print("Order History:")
for order in user1.order_history:
    print(f"Order Details: {order['cart']}")
    print(f"Total Cost: ${order['total_cost']}\n")
