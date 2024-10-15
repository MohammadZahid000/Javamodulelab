package HotelSystem;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

// Room class to manage room details
class Room implements Serializable {
    private int roomId;
    private String roomType;
    private double pricePerNight;
    private boolean available;

    public Room(int roomId, String roomType, double pricePerNight) {
        this.roomId = roomId;
        this.roomType = roomType;
        this.pricePerNight = pricePerNight;
        this.available = true; // Initially, all rooms are available
    }

    public int getRoomId() {
        return roomId;
    }

    public String getRoomType() {
        return roomType;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return "Room ID: " + roomId + ", Type: " + roomType + ", Price/Night: $" + pricePerNight
                + ", Available: " + (available ? "Yes" : "No");
    }
}

// Customer class to manage customer details
class Customer implements Serializable {
    private int customerId;
    private String customerName;
    private String contactNumber;

    public Customer(int customerId, String customerName, String contactNumber) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.contactNumber = contactNumber;
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    @Override
    public String toString() {
        return "Customer ID: " + customerId + ", Name: " + customerName + ", Contact: " + contactNumber;
    }
}

// Reservation class to manage room bookings
class Reservation implements Serializable {
    private int reservationId;
    private int roomId;
    private int customerId;
    private Date checkInDate;
    private Date checkOutDate;
    private double totalCost;

    public Reservation(int reservationId, int roomId, int customerId, Date checkInDate, Date checkOutDate, double totalCost) {
        this.reservationId = reservationId;
        this.roomId = roomId;
        this.customerId = customerId;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.totalCost = totalCost;
    }

    public int getReservationId() {
        return reservationId;
    }

    public int getRoomId() {
        return roomId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    public double getTotalCost() {
        return totalCost;
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return "Reservation ID: " + reservationId + ", Room ID: " + roomId + ", Customer ID: " + customerId
                + ", Check-in: " + sdf.format(checkInDate) + ", Check-out: " + sdf.format(checkOutDate)
                + ", Total Cost: $" + totalCost;
    }
}

// HotelSystem class to manage rooms, customers, and reservations
public class HotelSystem {
    private static List<Room> rooms = new ArrayList<>();
    private static List<Customer> customers = new ArrayList<>();
    private static List<Reservation> reservations = new ArrayList<>();
    private static int reservationCounter = 1;

    public static void main(String[] args) {
        loadFiles(); // Load room, customer, and reservation data from files if available
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- Hotel Reservation System ---");
            System.out.println("1. Add Room");
            System.out.println("2. View Available Rooms");
            System.out.println("3. Register Customer");
            System.out.println("4. Book Room");
            System.out.println("5. View Reservations");
            System.out.println("6. Generate Invoice");
            System.out.println("7. Exit");

            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();

            try {
                switch (choice) {
                    case 1:
                        addRoom(sc);
                        break;
                    case 2:
                        viewAvailableRooms();
                        break;
                    case 3:
                        registerCustomer(sc);
                        break;
                    case 4:
                        bookRoom(sc);
                        break;
                    case 5:
                        viewReservations();
                        break;
                    case 6:
                        generateInvoice(sc);
                        break;
                    case 7:
                        saveFiles(); // Save room, customer, and reservation data to files
                        System.out.println("Exiting...");
                        return;
                    default:
                        System.out.println("Invalid choice! Please try again.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    // Add a new room to the system
    private static void addRoom(Scanner sc) {
        System.out.print("Enter room ID: ");
        int roomId = sc.nextInt();
        System.out.print("Enter room type: ");
        sc.nextLine(); // consume newline
        String roomType = sc.nextLine();
        System.out.print("Enter price per night: ");
        double pricePerNight = sc.nextDouble();

        rooms.add(new Room(roomId, roomType, pricePerNight));
        System.out.println("Room added successfully.");
    }

    // View available rooms
    private static void viewAvailableRooms() {
        boolean hasAvailableRooms = false;
        System.out.println("--- Available Rooms ---");
        for (Room room : rooms) {
            if (room.isAvailable()) {
                System.out.println(room);
                hasAvailableRooms = true;
            }
        }
        if (!hasAvailableRooms) {
            System.out.println("No available rooms.");
        }
    }

    // Register a new customer
    private static void registerCustomer(Scanner sc) {
        System.out.print("Enter customer ID: ");
        int customerId = sc.nextInt();
        System.out.print("Enter customer name: ");
        sc.nextLine(); // consume newline
        String customerName = sc.nextLine();
        System.out.print("Enter contact number: ");
        String contactNumber = sc.nextLine();

        customers.add(new Customer(customerId, customerName, contactNumber));
        System.out.println("Customer registered successfully.");
    }

    // Book a room for a customer
    private static void bookRoom(Scanner sc) throws ParseException {
        System.out.print("Enter customer ID: ");
        int customerId = sc.nextInt();
        Customer customer = findCustomerById(customerId);
        if (customer == null) {
            System.out.println("Customer not found!");
            return;
        }

        System.out.print("Enter room ID: ");
        int roomId = sc.nextInt();
        Room room = findRoomById(roomId);
        if (room == null || !room.isAvailable()) {
            System.out.println("Room not available!");
            return;
        }

        System.out.print("Enter check-in date (dd/MM/yyyy): ");
        sc.nextLine(); // consume newline
        String checkInStr = sc.nextLine();
        System.out.print("Enter check-out date (dd/MM/yyyy): ");
        String checkOutStr = sc.nextLine();
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date checkInDate = sdf.parse(checkInStr);
        Date checkOutDate = sdf.parse(checkOutStr);

        long diff = checkOutDate.getTime() - checkInDate.getTime();
        int numOfNights = (int) (diff / (1000 * 60 * 60 * 24));

        if (numOfNights <= 0) {
            System.out.println("Invalid check-out date!");
            return;
        }

        double totalCost = room.getPricePerNight() * numOfNights;

        // Mark room as booked
        room.setAvailable(false);

        // Create a reservation
        reservations.add(new Reservation(reservationCounter++, roomId, customerId, checkInDate, checkOutDate, totalCost));
        System.out.println("Room booked successfully. Total cost: $" + totalCost);
    }

    // View all reservations
    private static void viewReservations() {
        if (reservations.isEmpty()) {
            System.out.println("No reservations made.");
            return;
        }

        System.out.println("--- Reservation History ---");
        for (Reservation reservation : reservations) {
            System.out.println(reservation);
        }
    }

    // Generate an invoice for a specific reservation
    private static void generateInvoice(Scanner sc) {
        System.out.print("Enter reservation ID: ");
        int reservationId = sc.nextInt();

        Reservation reservation = findReservationById(reservationId);
        if (reservation == null) {
            System.out.println("Reservation not found!");
            return;
        }

        System.out.println("--- Invoice ---");
        System.out.println("Reservation ID: " + reservation.getReservationId());
        System.out.println("Room ID: " + reservation.getRoomId());
        System.out.println("Customer ID: " + reservation.getCustomerId());
        System.out.println("Check-in Date: " + reservation.getCheckInDate());
        System.out.println("Check-out Date: " + reservation.getCheckOutDate());
        System.out.println("Total Cost: $" + reservation.getTotalCost());
    }

    // Find a room by ID
    private static Room findRoomById(int roomId) {
        for (Room room : rooms) {
            if (room.getRoomId() == roomId) {
                return room;
            }
        }
        return null;
    }

    // Find a customer by ID
    private static Customer findCustomerById(int customerId) {
        for (Customer customer : customers) {
            if (customer.getCustomerId() == customerId) {
                return customer;
            }
        }
        return null;
    }

    // Find a reservation by ID
    private static Reservation findReservationById(int reservationId) {
        for (Reservation reservation : reservations) {
            if (reservation.getReservationId() == reservationId) {
                return reservation;
            }
        }
        return null;
    }

    // Save data to files before exiting
    private static void saveFiles() {
        try (ObjectOutputStream oosRooms = new ObjectOutputStream(new FileOutputStream("rooms.dat"));
             ObjectOutputStream oosCustomers = new ObjectOutputStream(new FileOutputStream("customers.dat"));
             ObjectOutputStream oosReservations = new ObjectOutputStream(new FileOutputStream("reservations.dat"))) {

            oosRooms.writeObject(rooms);
            oosCustomers.writeObject(customers);
            oosReservations.writeObject(reservations);
        } catch (IOException e) {
            System.out.println("Error saving data.");
        }
    }

    // Load data from files if available
    private static void loadFiles() {
        try (ObjectInputStream oisRooms = new ObjectInputStream(new FileInputStream("rooms.dat"));
             ObjectInputStream oisCustomers = new ObjectInputStream(new FileInputStream("customers.dat"));
             ObjectInputStream oisReservations = new ObjectInputStream(new FileInputStream("reservations.dat"))) {

            rooms = (List<Room>) oisRooms.readObject();
            customers = (List<Customer>) oisCustomers.readObject();
            reservations = (List<Reservation>) oisReservations.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("No previous data found. Starting fresh.");
        }
    }
}
