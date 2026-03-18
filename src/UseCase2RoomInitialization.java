abstract class RoomBase {
    protected String roomType;
    protected int beds;
    protected double price;
    public RoomBase(String roomType, int beds, double price) {
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
class SingleRoomType extends RoomBase {
    public SingleRoomType() {
        super("Single Room", 1, 1000);
    }
}
class DoubleRoomType extends RoomBase {
    public DoubleRoomType() {
        super("Double Room", 2, 2000);
    }
}
class SuiteRoomType extends RoomBase {
    public SuiteRoomType() {
        super("Suite Room", 3, 5000);
    }
}
public class UseCase2RoomInitialization {
    public static void main(String[] args) {
        System.out.println("===== Book My Stay App =====");
        System.out.println("Version: 2.1\n");
        RoomBase single = new SingleRoomType();
        RoomBase doubleRoom = new DoubleRoomType();
        RoomBase suite = new SuiteRoomType();
        int singleAvailable = 5;
        int doubleAvailable = 3;
        int suiteAvailable = 2;
        single.displayDetails();
        System.out.println("Available: " + singleAvailable + "\n");
        doubleRoom.displayDetails();
        System.out.println("Available: " + doubleAvailable + "\n");
        suite.displayDetails();
        System.out.println("Available: " + suiteAvailable);
    }
}