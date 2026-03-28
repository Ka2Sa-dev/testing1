class SeatManager:
    def __init__(self, rows=5, cols=5):
        self.rows = rows
        self.cols = cols
        self.seats = [[0 for _ in range(cols)] for _ in range(rows)]

    def display_seats(self):
        print("Seat Layout (0=Available, 1=Booked)")
        for i, row in enumerate(self.seats):
            print(chr(65+i), row)

    def book_seat(self, row, col):
        if self.seats[row][col] == 0:
            self.seats[row][col] = 1
            return True
        return False

    def cancel_seat(self, row, col):
        if self.seats[row][col] == 1:
            self.seats[row][col] = 0
            return True
        return False