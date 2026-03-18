import java.util.*;

class ReservationUC8 {
    private String reservationId;
    private String guestName;
    private String roomType;
    public ReservationUC8(String reservationId, String guestName, String roomType) { this.reservationId = reservationId; this.guestName = guestName; this.roomType = roomType; }
    public String getReservationId() { return reservationId; }
    public String getGuestName() { return guestName; }
    public String getRoomType() { return roomType; }
    public String toString() { return "ReservationID: " + reservationId + ", Guest: " + guestName + ", Room: " + roomType; }
}

class BookingHistory {
    private List<ReservationUC8> history = new ArrayList<>();
    public void addReservation(ReservationUC8 r) { history.add(r); }
    public List<ReservationUC8> getAllReservations() { return new ArrayList<>(history); }
}

class BookingReportService {
    private BookingHistory bookingHistory;
    public BookingReportService(BookingHistory bookingHistory) { this.bookingHistory = bookingHistory; }
    public void generateSummaryReport() {
        System.out.println("Booking Summary Report:");
        Map<String, Integer> roomCount = new HashMap<>();
        for(ReservationUC8 r : bookingHistory.getAllReservations()) { roomCount.put(r.getRoomType(), roomCount.getOrDefault(r.getRoomType(), 0)+1); }
        for(Map.Entry<String,Integer> entry : roomCount.entrySet()) { System.out.println("Room Type: " + entry.getKey() + ", Bookings: " + entry.getValue()); }
    }
    public void displayAllReservations() { bookingHistory.getAllReservations().forEach(System.out::println); }
}

public class UseCase8BookingHistoryReport {
    public static void main(String[] args) {
        BookingHistory history = new BookingHistory();
        ReservationUC8 r1 = new ReservationUC8("R001", "Alice", "Deluxe");
        ReservationUC8 r2 = new ReservationUC8("R002", "Bob", "Standard");
        ReservationUC8 r3 = new ReservationUC8("R003", "Charlie", "Deluxe");
        history.addReservation(r1);
        history.addReservation(r2);
        history.addReservation(r3);
        BookingReportService reportService = new BookingReportService(history);
        reportService.displayAllReservations();
        reportService.generateSummaryReport();
    }
}