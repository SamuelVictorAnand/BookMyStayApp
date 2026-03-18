import java.util.HashMap;
import java.util.Map;

class RoomUC4 {
    private String type;
    private int beds;
    private double price;

    public RoomUC4(String type, int beds, double price) {
        this.type = type;
        this.beds = beds;
        this.price = price;
    }

    public void display() {
        System.out.println("Room Type: " + type);
        System.out.println("Beds: " + beds);
        System.out.println("Price: " + price);
    }

    public String getType() {
        return type;
    }
}

class RoomInventoryUC4 {
    private Map<String, Integer> inventory;

    public RoomInventoryUC4() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 0);
        inventory.put("Suite Room", 2);
    }

    public int getAvailability(String type) {
        return inventory.getOrDefault(type, 0);
    }

    public Map<String, Integer> getAll() {
        return inventory;
    }
}

class RoomSearchServiceUC4 {
    private RoomInventoryUC4 inventory;
    private Map<String, RoomUC4> roomData;

    public RoomSearchServiceUC4(RoomInventoryUC4 inventory) {
        this.inventory = inventory;
        roomData = new HashMap<>();
        roomData.put("Single Room", new RoomUC4("Single Room", 1, 1000));
        roomData.put("Double Room", new RoomUC4("Double Room", 2, 2000));
        roomData.put("Suite Room", new RoomUC4("Suite Room", 3, 5000));
    }

    public void search() {
        for (String type : inventory.getAll().keySet()) {
            int count = inventory.getAvailability(type);
            if (count > 0) {
                RoomUC4 room = roomData.get(type);
                room.display();
                System.out.println("Available: " + count);
                System.out.println("----------------------");
            }
        }
    }
}

public class UseCase4RoomSearch {
    public static void main(String[] args) {
        System.out.println("===== Book My Stay App =====");
        System.out.println("Version: 4.0\n");

        RoomInventoryUC4 inventory = new RoomInventoryUC4();
        RoomSearchServiceUC4 searchService = new RoomSearchServiceUC4(inventory);

        searchService.search();
    }
}