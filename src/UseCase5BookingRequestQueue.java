import java.util.LinkedList;
import java.util.Queue;

class ReservationUC5 {
    private String guestName;
    private String roomType;

    public ReservationUC5(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public void display() {
        System.out.println("Guest: " + guestName + " | Room: " + roomType);
    }
}

class BookingQueueUC5 {
    private Queue<ReservationUC5> queue;

    public BookingQueueUC5() {
        queue = new LinkedList<>();
    }

    public void addRequest(ReservationUC5 reservation) {
        queue.offer(reservation);
    }

    public void displayQueue() {
        for (ReservationUC5 r : queue) {
            r.display();
        }
    }
}

public class UseCase5BookingRequestQueue {
    public static void main(String[] args) {
        System.out.println("===== Book My Stay App =====");
        System.out.println("Version: 5.0\n");

        BookingQueueUC5 bookingQueue = new BookingQueueUC5();

        bookingQueue.addRequest(new ReservationUC5("Alice", "Single Room"));
        bookingQueue.addRequest(new ReservationUC5("Bob", "Double Room"));
        bookingQueue.addRequest(new ReservationUC5("Charlie", "Suite Room"));

        System.out.println("Booking Requests (FIFO Order):\n");
        bookingQueue.displayQueue();
    }
}