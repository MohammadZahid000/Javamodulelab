package vehiclepack;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;
class Vehicle {
    private String model;
    private String licensePlate;
    private boolean available;
    private double rentalRate;

    // Constructor
    public Vehicle(String model, String licensePlate, double rentalRate) {
        this.model = model;
        this.licensePlate = licensePlate;
        this.available = true;  // Initially, all vehicles are available
        this.rentalRate = rentalRate;
    }

    // Getters
    public String getModel() {
        return model;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public boolean isAvailable() {
        return available;
    }

    public double getRentalRate() {
        return rentalRate;
    }

    // Setters
    public void setAvailable(boolean available) {
        this.available = available;
    }

    // Display vehicle information
    public void displayVehicle() {
        String availabilityStatus = available ? "Available" : "Rented";
        System.out.println("Model: " + model + " | License Plate: " + licensePlate + " | Rate: $" + rentalRate + " | " + availabilityStatus);
    }
}
class Customer {
    private String name;
    private String contact;
    private ArrayList<Rental> rentals;

    // Constructor
    public Customer(String name, String contact) {
        this.name = name;
        this.contact = contact;
        this.rentals = new ArrayList<>();
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getContact() {
        return contact;
    }

    // Add rental to customer's history
    public void addRental(Rental rental) {
        rentals.add(rental);
    }

    // Display all rentals made by the customer
    public void displayRentals() {
        System.out.println("Customer: " + name + " | Contact: " + contact);
        System.out.println("Rentals:");
        for (Rental rental : rentals) {
            rental.displayRental();
        }
    }
}
class Rental {
    private Vehicle vehicle;
    private int rentalDays;

    // Constructor
    public Rental(Vehicle vehicle, int rentalDays) {
        this.vehicle = vehicle;
        this.rentalDays = rentalDays;
    }

    // Display details of the rental
    public void displayRental() {
        System.out.println("Vehicle: " + vehicle.getModel() + " | Days: " + rentalDays + " | Total: $" + (vehicle.getRentalRate() * rentalDays));
    }

    public double getRentalTotal() {
        return vehicle.getRentalRate() * rentalDays;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }
}


class RentalService {
    private HashMap<String, Vehicle> vehicles;  // License plate -> Vehicle
    private HashMap<String, Customer> customers; // Contact -> Customer

    public RentalService() {
        this.vehicles = new HashMap<>();
        this.customers = new HashMap<>();
    }

    // Add vehicle to rental service
    public void addVehicle(Vehicle vehicle) {
        vehicles.put(vehicle.getLicensePlate(), vehicle);
    }

    // Add customer to rental service
    public void addCustomer(Customer customer) {
        customers.put(customer.getContact(), customer);
    }

    // Get customer by contact
    public Customer getCustomer(String contact) {
        return customers.get(contact);
    }

    // Get vehicle by license plate
    public Vehicle getVehicle(String licensePlate) {
        return vehicles.get(licensePlate);
    }

    // Display available vehicles
    public void displayAvailableVehicles() {
        System.out.println("Available Vehicles:");
        for (Vehicle vehicle : vehicles.values()) {
            if (vehicle.isAvailable()) {
                vehicle.displayVehicle();
            }
        }
    }

    // Process rental
    public boolean rentVehicle(String customerContact, String licensePlate, int days) {
        Customer customer = customers.get(customerContact);
        Vehicle vehicle = vehicles.get(licensePlate);

        if (customer == null) {
            System.out.println("Customer not found.");
            return false;
        }

        if (vehicle == null) {
            System.out.println("Vehicle not found.");
            return false;
        }

        if (!vehicle.isAvailable()) {
            System.out.println("Vehicle is already rented.");
            return false;
        }

        // Create rental, mark vehicle as rented, and add rental to customer's history
        Rental rental = new Rental(vehicle, days);
        vehicle.setAvailable(false);
        customer.addRental(rental);

        System.out.println("Rental successful.");
        return true;
    }

    // Process vehicle return
    public boolean returnVehicle(String customerContact, String licensePlate) {
        Customer customer = customers.get(customerContact);
        Vehicle vehicle = vehicles.get(licensePlate);

        if (customer == null || vehicle == null) {
            System.out.println("Invalid customer or vehicle.");
            return false;
        }

        if (vehicle.isAvailable()) {
            System.out.println("This vehicle is already available.");
            return false;
        }

        // Mark vehicle as available
        vehicle.setAvailable(true);
        System.out.println("Vehicle returned successfully.");
        return true;
    }

    // Display customer rentals
    public void displayCustomerRentals(String customerContact) {
        Customer customer = customers.get(customerContact);
        if (customer != null) {
            customer.displayRentals();
        } else {
            System.out.println("Customer not found.");
        }
    }
}

public class VehicleSystem {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        RentalService rentalService = new RentalService();

        // Add initial vehicles
        rentalService.addVehicle(new Vehicle("Toyota Camry", "ABC123", 50.00));
        rentalService.addVehicle(new Vehicle("Honda Civic", "XYZ456", 45.00));
        rentalService.addVehicle(new Vehicle("Tesla Model S", "TES789", 100.00));

        boolean running = true;
        while (running) {
            System.out.println("\n1. Add Customer");
            System.out.println("2. Display Available Vehicles");
            System.out.println("3. Rent Vehicle");
            System.out.println("4. Return Vehicle");
            System.out.println("5. View Customer Rentals");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    // Add customer
                    System.out.print("Enter customer name: ");
                    String name = sc.next();
                    System.out.print("Enter customer contact: ");
                    String contact = sc.next();
                    rentalService.addCustomer(new Customer(name, contact));
                    System.out.println("Customer added successfully.");
                    break;

                case 2:
                    // Display available vehicles
                    rentalService.displayAvailableVehicles();
                    break;

                case 3:
                    // Rent vehicle
                    System.out.print("Enter customer contact: ");
                    String customerContact = sc.next();
                    System.out.print("Enter vehicle license plate: ");
                    String licensePlate = sc.next();
                    System.out.print("Enter rental days: ");
                    int days = sc.nextInt();
                    rentalService.rentVehicle(customerContact, licensePlate, days);
                    break;

                case 4:
                    // Return vehicle
                    System.out.print("Enter customer contact: ");
                    String returnCustomerContact = sc.next();
                    System.out.print("Enter vehicle license plate: ");
                    String returnLicensePlate = sc.next();
                    rentalService.returnVehicle(returnCustomerContact, returnLicensePlate);
                    break;

                case 5:
                    // View customer rentals
                    System.out.print("Enter customer contact: ");
                    String rentalContact = sc.next();
                    rentalService.displayCustomerRentals(rentalContact);
                    break;

                case 6:
                    // Exit
                    running = false;
                    System.out.println("Exiting...");
                    break;

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }

        sc.close();
    }
}