def show_menu():
    print("1. Book Ticket")
    print("2. Cancel Ticket")
    print("3. Exit")


def choose_seat():
    row = input("Enter row (A-E): ")
    col = int(input("Enter column (0-4): "))
    return ord(row.upper()) - 65, col