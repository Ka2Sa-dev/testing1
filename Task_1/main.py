from models.user import Customer, Admin
from models.event import Event
from models.order import Order
from services.booking_service import BookingService
from services.payment_service import CreditCardPayment
from services.queue_service import QueueService
import cli_ui


def main():
    admin = Admin("Alice")
    event = Event("Concert")
    admin.create_event(event)

    # VIP queue
    queue = QueueService()
    queue.add_user(Customer("Bob", True), 1)   # VIP
    queue.add_user(Customer("Tom", False), 5)

    booking_service = BookingService()

    while True:
        user = queue.get_next_user()
        if not user:
            break

        print(f"\nNow serving: {user.get_name()}")

        order = Order(user)

        cli_ui.show_menu()
        choice = input("Choose: ")

        if choice == "1":
            event.get_seat_manager().display_seats()
            row, col = cli_ui.choose_seat()

            ticket = booking_service.book_ticket(user, event, row, col)
            if ticket:
                order.add_ticket(ticket)

                payment = CreditCardPayment()
                payment.pay(100)

                order.show_order()

        elif choice == "2":
            row, col = cli_ui.choose_seat()
            booking_service.cancel_ticket(event, row, col)

        elif choice == "3":
            break


if __name__ == "__main__":
    main()