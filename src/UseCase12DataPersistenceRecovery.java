import java.io.*;
import java.util.*;

class Booking implements Serializable {
    private static final long serialVersionUID = 1L;
    String guestName;
    String roomNumber;
    Date checkIn;
    Date checkOut;

    public Booking(String guestName, String roomNumber, Date checkIn, Date checkOut) {
        this.guestName = guestName;
        this.roomNumber = roomNumber;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }

    @Override
    public String toString() {
        return guestName + " - Room " + roomNumber + " from " + checkIn + " to " + checkOut;
    }
}

class Inventory implements Serializable {
    private static final long serialVersionUID = 1L;
    Map<String, Boolean> rooms = new HashMap<>();

    public Inventory(List<String> roomNumbers) {
        for (String room : roomNumbers) {
            rooms.put(room, true);
        }
    }

    public boolean isAvailable(String roomNumber) {
        return rooms.getOrDefault(roomNumber, false);
    }

    public void bookRoom(String roomNumber) {
        rooms.put(roomNumber, false);
    }

    public void releaseRoom(String roomNumber) {
        rooms.put(roomNumber, true);
    }

    @Override
    public String toString() {
        return rooms.toString();
    }
}

class PersistenceService {
    private static final String FILE_PATH = "hotelData.ser";

    public static void saveData(List<Booking> bookings, Inventory inventory) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(bookings);
            oos.writeObject(inventory);
            System.out.println("System state saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving system state: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Object> loadData() {
        Map<String, Object> data = new HashMap<>();
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            System.out.println("No previous state found. Starting fresh.");
            data.put("bookings", new ArrayList<Booking>());
            data.put("inventory", new Inventory(Arrays.asList("101", "102", "103", "104")));
            return data;
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            List<Booking> bookings = (List<Booking>) ois.readObject();
            Inventory inventory = (Inventory) ois.readObject();
            data.put("bookings", bookings);
            data.put("inventory", inventory);
            System.out.println("System state restored successfully.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading system state: " + e.getMessage());
            data.put("bookings", new ArrayList<Booking>());
            data.put("inventory", new Inventory(Arrays.asList("101", "102", "103", "104")));
        }
        return data;
    }
}

public class UseCase12DataPersistenceRecovery {
    public static void main(String[] args) {
        Map<String, Object> data = PersistenceService.loadData();
        List<Booking> bookings = (List<Booking>) data.get("bookings");
        Inventory inventory = (Inventory) data.get("inventory");

        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to Book My Stay App!");
        boolean running = true;

        while (running) {
            System.out.println("\n1. Book Room\n2. Show Bookings\n3. Exit");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Guest Name: ");
                    String guest = sc.nextLine();
                    System.out.print("Room Number: ");
                    String room = sc.nextLine();
                    if (inventory.isAvailable(room)) {
                        Date now = new Date();
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(now);
                        cal.add(Calendar.DATE, 2);
                        Booking b = new Booking(guest, room, now, cal.getTime());
                        bookings.add(b);
                        inventory.bookRoom(room);
                        System.out.println("Booking confirmed!");
                    } else {
                        System.out.println("Room not available.");
                    }
                    break;
                case 2:
                    System.out.println("Current Bookings:");
                    for (Booking b : bookings) {
                        System.out.println(b);
                    }
                    break;
                case 3:
                    PersistenceService.saveData(bookings, inventory);
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }

        sc.close();
        System.out.println("Application exited.");
    }
}