import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

class InvalidCancellationException extends Exception {
    public InvalidCancellationException(String message) {
        super(message);
    }
}

public class UseCase10BookingCancellation {

    static Map<String, Integer> roomInventory = new HashMap<>();
    static Stack<String> cancelledRooms = new Stack<>();
    static Map<String, String> bookings = new HashMap<>();

    static {
        roomInventory.put("STANDARD", 5);
        roomInventory.put("DELUXE", 3);
        roomInventory.put("SUITE", 2);
    }

    public static void bookRoom(String guest, String roomType) {
        int available = roomInventory.getOrDefault(roomType, 0);
        if (available > 0) {
            roomInventory.put(roomType, available - 1);
            bookings.put(guest, roomType);
            System.out.println("Booking successful for " + guest + " in " + roomType);
        } else {
            System.out.println("Booking failed for " + guest + ". No rooms available in " + roomType);
        }
    }

    public static void cancelBooking(String guest) {
        try {
            if (!bookings.containsKey(guest)) {
                throw new InvalidCancellationException("No booking found for " + guest);
            }
            String roomType = bookings.get(guest);
            cancelledRooms.push(roomType);
            int available = roomInventory.get(roomType);
            roomInventory.put(roomType, available + 1);
            bookings.remove(guest);
            System.out.println("Booking cancelled for " + guest + " in " + roomType);
        } catch (InvalidCancellationException e) {
            System.out.println("Cancellation failed: " + e.getMessage());
        }
    }

    public static void displayInventory() {
        System.out.println("\nCurrent Room Inventory:");
        for (String room : roomInventory.keySet()) {
            System.out.println(room + " : " + roomInventory.get(room));
        }
    }

    public static void main(String[] args) {
        displayInventory();

        bookRoom("Alice", "DELUXE");
        bookRoom("Bob", "SUITE");
        bookRoom("Charlie", "STANDARD");
        bookRoom("Dave", "DELUXE");

        displayInventory();

        cancelBooking("Alice");
        cancelBooking("Eve");
        cancelBooking("Bob");

        displayInventory();
    }
}