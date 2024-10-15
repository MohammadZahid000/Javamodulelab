package CinemaSystem;

import java.io.*;
import java.util.*;

// Movie class to store movie details
class Movie implements Serializable {
    private int movieId;
    private String title;

    public Movie(int movieId, String title) {
        this.movieId = movieId;
        this.title = title;
    }

    public int getMovieId() {
        return movieId;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "Movie ID: " + movieId + ", Title: " + title;
    }
}

// Seat class to represent seat availability in a showtime
class Seat implements Serializable {
    private int seatId;
    private boolean available;

    public Seat(int seatId) {
        this.seatId = seatId;
        this.available = true;  // By default, the seat is available
    }

    public int getSeatId() {
        return seatId;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return "Seat ID: " + seatId + ", Available: " + (available ? "Yes" : "No");
    }
}

// Showtime class to store showtime details and manage seat bookings
class Showtime implements Serializable {
    private int showtimeId;
    private Movie movie;
    private List<Seat> seats;

    public Showtime(int showtimeId, Movie movie, int totalSeats) {
        this.showtimeId = showtimeId;
        this.movie = movie;
        this.seats = new ArrayList<>();

        // Initialize seats for the showtime
        for (int i = 1; i <= totalSeats; i++) {
            seats.add(new Seat(i));
        }
    }

    public int getShowtimeId() {
        return showtimeId;
    }

    public Movie getMovie() {
        return movie;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public boolean bookSeats(List<Integer> seatIds) throws Exception {
        // Check if the requested seats are available
        for (int seatId : seatIds) {
            if (!seats.get(seatId - 1).isAvailable()) {
                throw new Exception("Seat " + seatId + " is already booked.");
            }
        }

        // Book the seats
        for (int seatId : seatIds) {
            seats.get(seatId - 1).setAvailable(false);
        }
        return true;
    }

    public void displaySeats() {
        System.out.println("Showtime ID: " + showtimeId + " for Movie: " + movie.getTitle());
        for (Seat seat : seats) {
            System.out.println(seat);
        }
    }

    @Override
    public String toString() {
        return "Showtime ID: " + showtimeId + ", Movie: " + movie.getTitle();
    }
}

// Booking class to manage customer bookings
class Booking implements Serializable {
    private int bookingId;
    private Showtime showtime;
    private List<Integer> bookedSeats;

    public Booking(int bookingId, Showtime showtime, List<Integer> bookedSeats) {
        this.bookingId = bookingId;
        this.showtime = showtime;
        this.bookedSeats = new ArrayList<>(bookedSeats);
    }

    @Override
    public String toString() {
        return "Booking ID: " + bookingId + ", Showtime: " + showtime + ", Booked Seats: " + bookedSeats;
    }
}

// CinemaSystem class to manage movies, showtimes, and bookings
public class CinemaSystem {
    private static List<Movie> movies = new ArrayList<>();
    private static List<Showtime> showtimes = new ArrayList<>();
    private static List<Booking> bookings = new ArrayList<>();
    private static int bookingCounter = 1;

    private static final String LOG_FILE = "booking_log.txt";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- Cinema Booking System ---");
            System.out.println("1. Add New Movie");
            System.out.println("2. Add Showtime for Movie");
            System.out.println("3. View Showtimes");
            System.out.println("4. Check Seat Availability");
            System.out.println("5. Book Seats");
            System.out.println("6. View Bookings");
            System.out.println("7. Exit");

            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();

            try {
                switch (choice) {
                    case 1:
                        addMovie(sc);
                        break;
                    case 2:
                        addShowtime(sc);
                        break;
                    case 3:
                        viewShowtimes();
                        break;
                    case 4:
                        checkSeatAvailability(sc);
                        break;
                    case 5:
                        bookSeats(sc);
                        break;
                    case 6:
                        viewBookings();
                        break;
                    case 7:
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

    // Add a new movie to the system
    private static void addMovie(Scanner sc) {
        System.out.print("Enter movie ID: ");
        int movieId = sc.nextInt();
        System.out.print("Enter movie title: ");
        sc.nextLine(); // consume newline
        String title = sc.nextLine();

        movies.add(new Movie(movieId, title));
        System.out.println("Movie added successfully.");
    }

    // Add a new showtime for a movie
    private static void addShowtime(Scanner sc) {
        System.out.print("Enter showtime ID: ");
        int showtimeId = sc.nextInt();
        System.out.print("Enter movie ID for this showtime: ");
        int movieId = sc.nextInt();
        System.out.print("Enter total number of seats for this showtime: ");
        int totalSeats = sc.nextInt();

        Movie movie = findMovieById(movieId);
        if (movie == null) {
            System.out.println("Movie not found!");
            return;
        }

        showtimes.add(new Showtime(showtimeId, movie, totalSeats));
        System.out.println("Showtime added successfully.");
    }

    // View all showtimes
    private static void viewShowtimes() {
        if (showtimes.isEmpty()) {
            System.out.println("No showtimes available.");
        } else {
            System.out.println("--- Available Showtimes ---");
            for (Showtime showtime : showtimes) {
                System.out.println(showtime);
            }
        }
    }

    // Check seat availability for a showtime
    private static void checkSeatAvailability(Scanner sc) {
        System.out.print("Enter showtime ID: ");
        int showtimeId = sc.nextInt();

        Showtime showtime = findShowtimeById(showtimeId);
        if (showtime == null) {
            System.out.println("Showtime not found!");
            return;
        }

        showtime.displaySeats();
    }

    // Book seats for a showtime
    private static void bookSeats(Scanner sc) throws IOException {
        System.out.print("Enter showtime ID: ");
        int showtimeId = sc.nextInt();

        Showtime showtime = findShowtimeById(showtimeId);
        if (showtime == null) {
            System.out.println("Showtime not found!");
            return;
        }

        System.out.print("Enter the number of seats to book: ");
        int numSeats = sc.nextInt();
        List<Integer> seatIds = new ArrayList<>();

        System.out.println("Enter seat IDs to book:");
        for (int i = 0; i < numSeats; i++) {
            seatIds.add(sc.nextInt());
        }

        try {
            showtime.bookSeats(seatIds);
            bookings.add(new Booking(bookingCounter++, showtime, seatIds));
            logBooking(new Booking(bookingCounter - 1, showtime, seatIds));
            System.out.println("Seats booked successfully.");
        } catch (Exception e) {
            System.out.println("Error booking seats: " + e.getMessage());
        }
    }

    // View all bookings
    private static void viewBookings() {
        if (bookings.isEmpty()) {
            System.out.println("No bookings available.");
        } else {
            System.out.println("--- Booking History ---");
            for (Booking booking : bookings) {
                System.out.println(booking);
            }
        }
    }

    // Find a movie by its ID
    private static Movie findMovieById(int movieId) {
        for (Movie movie : movies) {
            if (movie.getMovieId() == movieId) {
                return movie;
            }
        }
        return null;
    }

    // Find a showtime by its ID
    private static Showtime findShowtimeById(int showtimeId) {
        for (Showtime showtime : showtimes) {
            if (showtime.getShowtimeId() == showtimeId) {
                return showtime;
            }
        }
        return null;
    }

    // Log the booking details to a file
    private static void logBooking(Booking booking) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE, true))) {
            writer.write(booking.toString());
            writer.newLine();
        }
    }
}
 