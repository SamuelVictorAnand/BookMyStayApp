import java.util.HashMap;
import java.util.Map;

class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

public class UseCase9ErrorHandlingValidation {

    static Map<String, Integer> roomInventory = new HashMap<>();

    static {
        roomInventory.put("STANDARD", 5);
        roomInventory.put("DELUXE", 3);
        roomInventory.put("SUITE", 2);
    }

    public static void validateBooking(String roomType, int roomsRequested)
            throws InvalidBookingException {

        if (roomType == null || roomType.isEmpty()) {
            throw new InvalidBookingException("Room type cannot be empty");
        }

        if (!roomInventory.containsKey(roomType)) {
            throw new InvalidBookingException("Invalid room type: " + roomType);
        }

        if (roomsRequested <= 0) {
            throw new InvalidBookingException("Rooms must be greater than 0");
        }

        int available = roomInventory.get(roomType);

        if (roomsRequested > available) {
            throw new InvalidBookingException(
                    "Only " + available + " rooms available for " + roomType
            );
        }
    }

    public static void bookRoom(String roomType, int roomsRequested) {
        try {
            validateBooking(roomType, roomsRequested);

            int available = roomInventory.get(roomType);
            roomInventory.put(roomType, available - roomsRequested);

            System.out.println("Booking successful for " + roomType +
                    " (" + roomsRequested + " rooms)");

        } catch (InvalidBookingException e) {
            System.out.println("Booking failed: " + e.getMessage());
        }
    }

    public static void displayInventory() {
        System.out.println("\nCurrent Room Availability:");
        for (String room : roomInventory.keySet()) {
            System.out.println(room + " : " + roomInventory.get(room));
        }
    }

    public static void main(String[] args) {

        displayInventory();

        bookRoom("DELUXE", 2);
        bookRoom("PRESIDENTIAL", 1);
        bookRoom("STANDARD", -1);
        bookRoom("SUITE", 5);

        displayInventory();
    }
}