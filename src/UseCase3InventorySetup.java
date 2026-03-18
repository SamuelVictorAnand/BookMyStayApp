import java.util.HashMap;
import java.util.Map;

class RoomBaseUC3 {
    protected String roomType;
    protected int beds;
    protected double price;

    public RoomBaseUC3(String roomType, int beds, double price) {
        this.roomType = roomType;
        this.beds = beds;
        this.price = price;
    }

    public void displayDetails() {
        System.out.println("Room Type: " + roomType);
        System.out.println("Beds: " + beds);
        System.out.println("Price: " + price);
    }
}

class RoomInventoryUC3 {
    private Map<String, Integer> inventory;

    public RoomInventoryUC3() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 2);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public void updateAvailability(String roomType, int count) {
        inventory.put(roomType, count);
    }

    public void displayInventory() {
        for (String type : inventory.keySet()) {
            System.out.println(type + " Available: " + inventory.get(type));
        }
    }
}

public class UseCase3InventorySetup {
    public static void main(String[] args) {
        System.out.println("===== Book My Stay App =====");
        System.out.println("Version: 3.1\n");

        RoomBaseUC3 single = new RoomBaseUC3("Single Room", 1, 1000);
        RoomBaseUC3 doubleRoom = new RoomBaseUC3("Double Room", 2, 2000);
        RoomBaseUC3 suite = new RoomBaseUC3("Suite Room", 3, 5000);

        RoomInventoryUC3 inventory = new RoomInventoryUC3();

        single.displayDetails();
        System.out.println("Available: " + inventory.getAvailability("Single Room") + "\n");

        doubleRoom.displayDetails();
        System.out.println("Available: " + inventory.getAvailability("Double Room") + "\n");

        suite.displayDetails();
        System.out.println("Available: " + inventory.getAvailability("Suite Room") + "\n");

        System.out.println("Full Inventory:");
        inventory.displayInventory();
    }
}