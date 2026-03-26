import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.LinkedList;

public class UseCase11ConcurrentBookingSimulation {

    static Map<String, Integer> roomInventory = new HashMap<>();
    static Queue<String[]> bookingQueue = new LinkedList<>();

    static {
        roomInventory.put("STANDARD", 5);
        roomInventory.put("DELUXE", 3);
        roomInventory.put("SUITE", 2);
    }

    public static synchronized boolean bookRoom(String guest, String roomType) {
        int available = roomInventory.getOrDefault(roomType, 0);
        if (available > 0) {
            roomInventory.put(roomType, available - 1);
            System.out.println("Booking successful for " + guest + " in " + roomType);
            return true;
        } else {
            System.out.println("Booking failed for " + guest + ". No rooms available in " + roomType);
            return false;
        }
    }

    public static synchronized void displayInventory() {
        System.out.println("\nCurrent Room Inventory:");
        for (String room : roomInventory.keySet()) {
            System.out.println(room + " : " + roomInventory.get(room));
        }
    }

    static class BookingThread extends Thread {
        public void run() {
            while (true) {
                String[] booking;
                synchronized (bookingQueue) {
                    if (bookingQueue.isEmpty()) break;
                    booking = bookingQueue.poll();
                }
                bookRoom(booking[0], booking[1]);
            }
        }
    }

    public static void main(String[] args) {
        bookingQueue.add(new String[]{"Alice", "DELUXE"});
        bookingQueue.add(new String[]{"Bob", "SUITE"});
        bookingQueue.add(new String[]{"Charlie", "STANDARD"});
        bookingQueue.add(new String[]{"Dave", "DELUXE"});
        bookingQueue.add(new String[]{"Eve", "SUITE"});
        bookingQueue.add(new String[]{"Frank", "STANDARD"});

        Thread t1 = new BookingThread();
        Thread t2 = new BookingThread();
        Thread t3 = new BookingThread();

        t1.start();
        t2.start();
        t3.start();

        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        displayInventory();
    }
}