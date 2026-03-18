import java.util.*;

class Service {
    private String name;
    private double cost;
    public Service(String name, double cost) { this.name = name; this.cost = cost; }
    public String getName() { return name; }
    public double getCost() { return cost; }
}

class Reservation {
    private String reservationId;
    private String guestName;
    public Reservation(String reservationId, String guestName) { this.reservationId = reservationId; this.guestName = guestName; }
    public String getReservationId() { return reservationId; }
    public String getGuestName() { return guestName; }
}

class AddOnServiceManager {
    private Map<String, List<Service>> reservationServices = new HashMap<>();
    public void addServiceToReservation(String reservationId, Service service) {
        reservationServices.computeIfAbsent(reservationId, k -> new ArrayList<>()).add(service);
    }
    public List<Service> getServicesForReservation(String reservationId) {
        return reservationServices.getOrDefault(reservationId, new ArrayList<>());
    }
    public double calculateTotalCost(String reservationId) {
        return reservationServices.getOrDefault(reservationId, new ArrayList<>()).stream().mapToDouble(Service::getCost).sum();
    }
}

public class UseCase7AddOnServiceSelection {
    public static void main(String[] args) {
        Reservation r1 = new Reservation("R001", "Alice");
        Reservation r2 = new Reservation("R002", "Bob");
        Service breakfast = new Service("Breakfast", 15.0);
        Service spa = new Service("Spa Access", 50.0);
        Service airport = new Service("Airport Pickup", 30.0);
        AddOnServiceManager manager = new AddOnServiceManager();
        manager.addServiceToReservation(r1.getReservationId(), breakfast);
        manager.addServiceToReservation(r1.getReservationId(), spa);
        manager.addServiceToReservation(r2.getReservationId(), airport);
        System.out.println("Reservation " + r1.getReservationId() + " services:");
        manager.getServicesForReservation(r1.getReservationId()).forEach(s -> System.out.println(s.getName() + " - $" + s.getCost()));
        System.out.println("Total additional cost: $" + manager.calculateTotalCost(r1.getReservationId()));
        System.out.println("Reservation " + r2.getReservationId() + " services:");
        manager.getServicesForReservation(r2.getReservationId()).forEach(s -> System.out.println(s.getName() + " - $" + s.getCost()));
        System.out.println("Total additional cost: $" + manager.calculateTotalCost(r2.getReservationId()));
    }
}