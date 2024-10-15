package firstcode;


import java.util.ArrayList;



public class BookingSystem {
    private Flight flight;
    private ArrayList<Passenger> passengers;

    public BookingSystem(Flight flight) {
        this.flight = flight;
        this.passengers = new ArrayList<>();
    }

    public void bookTicket(String name, int seatNumber) {
        if (flight.isSeatAvailable(seatNumber)) {
            Passenger passenger = new Passenger(name, seatNumber);
            flight.bookSeat(seatNumber);
            passengers.add(passenger);
            System.out.println("Seat " + seatNumber + " booked successfully for " + name + ".");
        } else {
            System.out.println("Seat " + seatNumber + " is not available.");
        }
    }

    public void cancelTicket(String name) {
        Passenger passengerToCancel = null;
        for (Passenger passenger : passengers) {
            if (passenger.getName().equals(name)) {
                passengerToCancel = passenger;
                break;
            }
        }

        if (passengerToCancel != null) {
            flight.cancelSeat(passengerToCancel.getSeatNumber());
            passengers.remove(passengerToCancel);
            System.out.println("Booking canceled for " + name + ".");
        } else {
            System.out.println("No booking found for " + name + ".");
        }
    }

    public void displayAvailableSeats() {
        System.out.println("Available seats for flight " + flight.getFlightNumber() + ":");
        boolean hasAvailableSeats = false;
        for (int i = 1; i <= flight.getTotalSeats(); i++) {
            if (flight.isSeatAvailable(i)) {
                System.out.print(i + " ");
                hasAvailableSeats = true;
            }
        }
        if (!hasAvailableSeats) {
            System.out.println("No available seats.");
        } else {
            System.out.println();
        }
    }
}



public class Passenger {

    private String name;
    private int seatNumber;

    public Passenger(String name, int seatNumber) {
        this.name = name;
        this.seatNumber = seatNumber;
    }

    public String getName() {
        return name;
    }

    public int getSeatNumber() {
        return seatNumber;
    }
}


public class Flight {
    private String flightNumber;
    private int totalSeats;
    private ArrayList<Integer> bookedSeats;

    public Flight(String flightNumber, int totalSeats) {
        this.flightNumber = flightNumber;
        this.totalSeats = totalSeats;
        this.bookedSeats = new ArrayList<>();
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public ArrayList<Integer> getBookedSeats() {
        return bookedSeats;
    }

    public boolean isSeatAvailable(int seatNumber) {
        return !bookedSeats.contains(seatNumber) && seatNumber <= totalSeats && seatNumber > 0;
    }

    public void bookSeat(int seatNumber) {
        bookedSeats.add(seatNumber);
    }

    public void cancelSeat(int seatNumber) {
        bookedSeats.remove((Integer) seatNumber);
    }
}



public class AirlineBookingSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Flight flight = new Flight("AI101", 10);
        BookingSystem bookingSystem = new BookingSystem(flight);

        while (true) {
            System.out.println("\nAirline Booking System");
            System.out.println("1. Book a Ticket");
            System.out.println("2. Cancel a Ticket");
            System.out.println("3. Display Available Seats");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
            case 1:
                System.out.print("Enter passenger name: ");
                String name = scanner.next();
                System.out.print("Enter seat number to book (1-10): ");
                int seatNumber = scanner.nextInt();
                bookingSystem.bookTicket(name, seatNumber);
                break;

            case 2:
                System.out.print("Enter passenger name to cancel: ");
                String cancelName = scanner.next();
                bookingSystem.cancelTicket(cancelName);
                break;

            case 3:
                bookingSystem.displayAvailableSeats();
                break;

            case 4:
                System.out.println("Exiting the system...");
                scanner.close();
                return;

            default:
                System.out.println("Invalid choice, please try again.");
            }
        }
    }
}


