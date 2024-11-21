import java.util.Scanner;
//import bank.*;
// Payment Mode Enum
enum PaymentMode {
    CASH, INTERNET_TRANSACTION
}

// Passenger Class
class Passenger {
    String name;
    String phoneNumber;
    String locatedCity;
    String dateOfBirth;

    public Passenger(String name, String phoneNumber, String locatedCity, String dateOfBirth) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.locatedCity = locatedCity;
        this.dateOfBirth = dateOfBirth;
    }
}

// Train Class
class Train {
    String trainNumber;
    String name;
    String fromCity;
    String toCity;
    String scheduledTime;
    String departureTime;
    double ticketPrice;

    public Train(String trainNumber, String name, String fromCity, String toCity, String scheduledTime, String departureTime, double ticketPrice) {
        this.trainNumber = trainNumber;
        this.name = name;
        this.fromCity = fromCity;
        this.toCity = toCity;
        this.scheduledTime = scheduledTime;
        this.departureTime = departureTime;
        this.ticketPrice = ticketPrice;
    }
}

// TrainTicketReservationSystem Class
public class TrainTicketReservationSystem {
    private static final int MAX_PASSENGERS = 5;
    private Passenger[] passengers = new Passenger[MAX_PASSENGERS];
    private Train[] trains = {
        new Train("101", "Express", "CityA", "CityB", "08:00 AM", "08:15 AM", 250.0),
        new Train("102", "SuperFast", "CityC", "CityD", "09:00 AM", "09:20 AM", 300.0),
        new Train("103", "Local", "CityE", "CityF", "10:00 AM", "10:30 AM", 150.0),
        new Train("104", "Rajdhani", "CityG", "CityH", "11:00 AM", "11:15 AM", 500.0),
        new Train("105", "Intercity", "CityI", "CityJ", "12:00 PM", "12:10 PM", 200.0)
    };
    private int passengerCount = 0;

    public TrainTicketReservationSystem() {
        System.out.println("Welcome to the Train Ticket Reservation System!");
    }

    public void addPassenger(String name, String phoneNumber, String locatedCity, String dateOfBirth) {
        if (passengerCount < MAX_PASSENGERS) {
            passengers[passengerCount++] = new Passenger(name, phoneNumber, locatedCity, dateOfBirth);
            System.out.println("Passenger added successfully!");
        } else {
            System.out.println("Passenger list is full. Cannot add more passengers.");
        }
    }

    public boolean searchPassenger(String input) {
        for (Passenger p : passengers) {
            if (p != null && (p.phoneNumber.equals(input) || p.dateOfBirth.equals(input))) {
                System.out.println("Passenger Found: " + p.name);
                return true;
            }
        }
        return false;
    }

    public void displayTrains() {
        System.out.println("\nAvailable Trains:");
        System.out.printf("%-10s %-15s %-10s %-10s %-15s %-15s %-10s\n", 
                          "Train No", "Name", "From", "To", "Scheduled Time", "Departure Time", "Price");
        for (Train train : trains) {
            System.out.printf("%-10s %-15s %-10s %-10s %-15s %-15s %-10.2f\n",
                train.trainNumber, train.name, train.fromCity, train.toCity, train.scheduledTime, train.departureTime, train.ticketPrice);
        }
    }

    public Train findTrain(String trainNumber) {
        for (Train train : trains) {
            if (train.trainNumber.equals(trainNumber)) {
                return train;
            }
        }
        return null;
    }

    public void bookTicket(Train train) {
        System.out.println("Do you wish to continue booking on Train " + train.trainNumber + "? (yes/no)");
    }

    public void processPayment(double amount, PaymentMode mode, BankService bankService, Scanner scanner) {
        if (mode == PaymentMode.CASH) {
            System.out.println("Booking completed successfully!");
        } else if (mode == PaymentMode.INTERNET_TRANSACTION) {
            System.out.println("Enter your Account Number: ");
            String accountNumber = scanner.next();
            if (bankService.processTransaction(accountNumber, amount)) {
                System.out.println("Transaction successful! Booking completed.");
            } else {
                System.out.println("Insufficient balance or invalid account. Transaction failed.");
            }
        }
    }

    public static void main(String[] args) {
        TrainTicketReservationSystem system = new TrainTicketReservationSystem();
        BankService bankService = new BankService();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nEnter Phone Number or Date of Birth to Search: ");
            String input = scanner.next();

            if (!system.searchPassenger(input)) {
                System.out.println("Passenger not found. Registering new passenger.");
                System.out.println("Enter Passenger Details (Name, Phone Number, City, DOB): ");
                String name = scanner.next();
                String phoneNumber = scanner.next();
                String city = scanner.next();
                String dob = scanner.next();
                system.addPassenger(name, phoneNumber, city, dob);
            }

            // Display Trains
            system.displayTrains();

            // Book Ticket
            System.out.println("Enter Train Number to Book Ticket: ");
            String trainNumber = scanner.next();
            Train selectedTrain = system.findTrain(trainNumber);

            if (selectedTrain != null) {
                system.bookTicket(selectedTrain);
                String confirm = scanner.next();
                if (confirm.equalsIgnoreCase("yes")) {
                    double price = selectedTrain.ticketPrice;
                    System.out.println("Ticket Price: " + price);
                    System.out.println("Choose Payment Mode (1 for Cash, 2 for Internet Transaction): ");
                    int paymentModeChoice = scanner.nextInt();

                    PaymentMode mode = (paymentModeChoice == 1) ? PaymentMode.CASH : PaymentMode.INTERNET_TRANSACTION;
                    system.processPayment(price, mode, bankService, scanner);
                } else {
                    System.out.println("Booking Cancelled.");
                }
            } else {
                System.out.println("Invalid Train Number.");
            }

            System.out.println("Do you want to book another ticket? (yes/no)");
            String continueBooking = scanner.next();
            if (!continueBooking.equalsIgnoreCase("yes")) {
                break;
            }
        }

        scanner.close();
        System.out.println("Thank you for using the Train Ticket Reservation System!");
    }
}
