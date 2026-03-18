import java.util.*;

class ReservationUC6 {
    private String guestName;
    private String roomType;

    public ReservationUC6(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

class BookingQueueUC6 {
    private Queue<ReservationUC6> queue;

    public BookingQueueUC6() {
        queue = new LinkedList<>();
    }

    public void addRequest(ReservationUC6 r) {
        queue.offer(r);
    }

    public ReservationUC6 getNextRequest() {
        return queue.poll();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}

class InventoryServiceUC6 {
    private Map<String, Integer> inventory;

    public InventoryServiceUC6() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 1);
    }

    public boolean isAvailable(String type) {
        return inventory.getOrDefault(type, 0) > 0;
    }

    public void reduceRoom(String type) {
        inventory.put(type, inventory.get(type) - 1);
    }
}

class RoomAllocationServiceUC6 {
    private Set<String> allocatedRoomIds;
    private Map<String, Set<String>> allocationMap;
    private InventoryServiceUC6 inventory;

    public RoomAllocationServiceUC6(InventoryServiceUC6 inventory) {
        this.inventory = inventory;
        allocatedRoomIds = new HashSet<>();
        allocationMap = new HashMap<>();
    }

    public void process(BookingQueueUC6 queue) {
        while (!queue.isEmpty()) {
            ReservationUC6 r = queue.getNextRequest();
            String type = r.getRoomType();

            if (inventory.isAvailable(type)) {
                String roomId = generateRoomId(type);

                allocatedRoomIds.add(roomId);
                allocationMap.putIfAbsent(type, new HashSet<>());
                allocationMap.get(type).add(roomId);

                inventory.reduceRoom(type);

                System.out.println("Confirmed: " + r.getGuestName() + " -> " + roomId);
            } else {
                System.out.println("Rejected: " + r.getGuestName() + " (No rooms available)");
            }
        }
    }

    private String generateRoomId(String type) {
        String id;
        do {
            id = type.substring(0, 2).toUpperCase() + (int)(Math.random() * 1000);
        } while (allocatedRoomIds.contains(id));
        return id;
    }
}

public class UseCase6RoomAllocationService {
    public static void main(String[] args) {
        System.out.println("===== Book My Stay App =====");
        System.out.println("Version: 6.0\n");

        BookingQueueUC6 queue = new BookingQueueUC6();
        queue.addRequest(new ReservationUC6("Alice", "Single Room"));
        queue.addRequest(new ReservationUC6("Bob", "Single Room"));
        queue.addRequest(new ReservationUC6("Charlie", "Single Room"));
        queue.addRequest(new ReservationUC6("David", "Suite Room"));

        InventoryServiceUC6 inventory = new InventoryServiceUC6();
        RoomAllocationServiceUC6 service = new RoomAllocationServiceUC6(inventory);

        service.process(queue);
    }
}