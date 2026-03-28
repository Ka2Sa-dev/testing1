import heapq

class QueueService:
    def __init__(self):
        self.queue = []

    def add_user(self, user, priority):
        heapq.heappush(self.queue, (priority, user))

    def get_next_user(self):
        if self.queue:
            return heapq.heappop(self.queue)[1]
        return None