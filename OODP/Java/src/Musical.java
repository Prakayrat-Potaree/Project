import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.*;

interface Rentable {
    void Rent();
    void ReturnInstrument();
    String toString();
}

class Musical implements Rentable {
    private String TypeofInstrument;
    private String Instrument;
    private double PricePerDay;
    private boolean isAvailable;

    public Musical(String TypeofInstrument, String Instrument, double PricePerDay) {
        this.TypeofInstrument = TypeofInstrument;
        this.Instrument = Instrument;
        this.PricePerDay = PricePerDay;
        this.isAvailable = true;
    }

    public String getTypeofInstrument() {
        return TypeofInstrument;
    }

    public String getInstrument() {
        return Instrument;
    }

    public String getInstrumentId() {
        return TypeofInstrument;
    }

    public String getInstrumentName() {
        return Instrument;
    }

    public double CalculatePricePerDay(int RentalDays) {
        return PricePerDay * RentalDays;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    @Override
    public void Rent() {
        isAvailable = false;
    }

    @Override
    public void ReturnInstrument() {
        isAvailable = true;
    }

    @Override
    public String toString() {
        return "Instrument ID: " + TypeofInstrument + ", Instrument Name: " + Instrument + ", Price per day: " + PricePerDay;
    }
}

class Customer {
    private String CustomerId;
    private String CustomerName;

    public Customer(String CustomerId, String CustomerName) {
        this.CustomerId = CustomerId;
        this.CustomerName = CustomerName;
    }

    public String getCustomerId() {
        return CustomerId;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerId(String customerId) {
        this.CustomerId = customerId;
    }

    @Override
    public String toString() {
        return "Customer ID: " + CustomerId + ", Name: " + CustomerName;
    }
}

class Rental {
    private Musical instrument;
    private Customer customer;
    private int days;

    public Rental(Musical inMusical, Customer customer, int days) {
        this.instrument = inMusical;
        this.customer = customer;
        this.days = days;
    }

    public Musical getInstrument() {
        return instrument;
    }

    public Customer getCustomer() {
        return customer;
    }

    public int getDays() {
        return days;
    }

    @Override
    public String toString() {
        return "Instrument: " + instrument.getInstrumentName() + ", Days: " + days;
    }
}

class FileHandler {
    public static void writeCustomerSummariesToFile(String filename, List<Rental> rentals) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (Rental rental : rentals) {
                // เขียนข้อมูลการเช่าลงในไฟล์
                Customer customer = rental.getCustomer();
                Musical instrument = rental.getInstrument();
                int rentalDays = rental.getDays();
                double totalPrice = instrument.CalculatePricePerDay(rentalDays);

                writer.println(customer.getCustomerId() + " , " + " Customer Name : " + customer.getCustomerName() + " , " +
                        " Instrument Name : " + instrument.getInstrumentName() + " , " +
                        " Number of rent day : " + rentalDays + " , " +
                        " Total price : " + totalPrice + " Bath");
            }
            System.out.println("Customer summaries have been written to file: " + filename);
        } catch (IOException e) {
            System.out.println("An error occurred while writing customer summaries to file: " + filename);
            e.printStackTrace();
        }
    }

    public static List<Customer> readCustomersFromFile(String filename) {
        List<Customer> customers = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    customers.add(new Customer(parts[0].trim(), parts[1].trim()));
                }
            }
            System.out.println("Customer data has been read from file: " + filename);
        } catch (IOException e) {
            System.out.println("An error occurred while reading customer data from file: " + filename);
            e.printStackTrace();
        }

        return customers;
    }
}

class MusicalRentalSystem {
    private List<Musical> instruments;
    private List<Customer> customers;
    private int nextCustomerId = 1;
    public  List<Rental> rentals;
    List<Rental> rentalsToRemove = new ArrayList<>();

    public MusicalRentalSystem() {
        instruments = new ArrayList<>();
        customers = new ArrayList<>();
        rentals = new ArrayList<>();
    }

    public void addInstrument(Musical instrument) {
        instruments.add(instrument);
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }
    public List<Rental> getRentals() {
        return rentals;
    }
        
    public void showAvailableInstruments() {
        System.out.println("\nAvailable Instruments:");
        for (Musical instrument : instruments) {
            if (instrument.isAvailable()) {
                System.out.println(instrument.getTypeofInstrument() + " - " + instrument.getInstrument() + " - " + instrument.CalculatePricePerDay(1) + " Bath");
            }
        }
    }

    public void summarizeCustomerRentals(String customerId) {
        double totalAmount = 0.0;
        boolean foundRental = false;
    
        for (Rental rental : rentals) {
            if (rental.getCustomer().getCustomerId().equals(customerId)) {
                foundRental = true;
                Musical instrument = rental.getInstrument();
                double rentalPrice = instrument.CalculatePricePerDay(rental.getDays());
                totalAmount += rentalPrice;
                System.out.println("\nSummary of rent : " + instrument.getInstrumentName() +
                        " [Days: " + rental.getDays() + "]" +
                        " [Price per day: " + instrument.CalculatePricePerDay(1) + "]" +
                        " || Total: " + rentalPrice);
            }
        }
        if (foundRental) {
            System.out.println("\n# Total amount for Customer ID " + customerId + " ---> " + totalAmount + " Bath");
        } else {
            System.out.println("No rental transactions have been made yet");
        }
        System.out.print("*************************************************************************\n");
    }

    public Customer getCustomerInput(Scanner scanner) {
        System.out.print("Enter customer name: ");
        String customerName = scanner.nextLine();

        // Assign ID to the customer
        String customerId = "CUS" + nextCustomerId;

        // Increment nextCustomerId for the next customer
        nextCustomerId++;

        return new Customer(customerId, customerName);
    }
   
    public Rental getRentalInput(Scanner scanner) {
        int instrumentId = 0;
        int rentalDays = 0;
    
        while (true) {
            try {
                System.out.print("Enter instrument ID: ");
                instrumentId = Integer.parseInt(scanner.nextLine());
                break; // Exit the loop
    
            } catch (NumberFormatException e) {
                System.out.println("Invalid input for instrument ID. Please enter a valid integer value.");
            }
        }
    
        while (true) {
            try {
                System.out.print("Enter number of rental days: ");
                rentalDays = Integer.parseInt(scanner.nextLine());
                break; // Exit the loop
    
            } catch (NumberFormatException e) {
                System.out.println("Invalid input for rental days. Please enter a valid integer value.");
            }
        }
    
        // Create and return the Rental object
        return new Rental(new Musical(Integer.toString(instrumentId), "", 0), null, rentalDays);
    }

    public void cancelRental(Scanner scanner, String customerId) {
        boolean foundRental = false;

        System.out.println("Customer's Rentals for Customer ID " + customerId + ":");
        for (Rental rental : rentals) {
            if (rental.getCustomer().getCustomerId().equals(customerId)) {
                Musical instrument = rental.getInstrument();
                System.out.println("\nInstrument ID: " + instrument.getInstrumentId() + ", Instrument Name: " + instrument.getInstrumentName());
                System.out.print("Do you want to cancel this rental? (yes/no): ");
                String choice = scanner.nextLine();

                if (choice.equalsIgnoreCase("yes")) {
                    instrument.ReturnInstrument();
                    double refundAmount = instrument.CalculatePricePerDay(rental.getDays());
                    rentalsToRemove.add(rental);
                    System.out.println("Rental for Instrument ID " + instrument.getInstrumentId() + " has been canceled.");
                    System.out.println("Refund amount for this rental: " + refundAmount + " Bath #");
                    foundRental = true;
                }
            }
        }
        if (foundRental) {
            rentals.removeAll(rentalsToRemove);
        } else {
            System.out.println("No rentals found for Customer ID " + customerId + " or no rentals were canceled.");
        }
    }

    public void returnInstrument(Scanner scanner, String customerId) {
        boolean foundRental = false;
        for (Rental rental : rentals) {
            if (rental.getCustomer().getCustomerId().equals(customerId)) {
                Musical instrument = rental.getInstrument();
                System.out.println("\nCustomer has rent - Instrument ID: " + instrument.getInstrumentId() + " , Instrument Name: " + instrument.getInstrumentName());
                System.out.print("Do you want to return this instrument? (yes/no): ");
                String choice = scanner.nextLine();

                if (choice.equalsIgnoreCase("yes")) {
                    instrument.ReturnInstrument();
                    rentalsToRemove.add(rental);
                    System.out.println("Instrument with ID " + instrument.getInstrumentId() + " has been returned.");
                    foundRental = true;
                }
            }
        }
        if (foundRental) {
            rentals.removeAll(rentalsToRemove);
        } else {
            System.out.println("No rentals found for Customer ID " + customerId + " or no instrument was returned.");
        }
    }

    public void rentInstrument(String instrumentId, Customer customer, int rentalDays) {
        Musical selectedInstrument = null;
        for (Musical instrument : instruments) {
            if (instrument.getInstrumentId().equals(instrumentId) && instrument.isAvailable()) {
                selectedInstrument = instrument;
                break;
            }
        }
        if (selectedInstrument != null) {
            selectedInstrument.Rent();
            addCustomer(customer);
            rentals.add(new Rental(selectedInstrument, customer, rentalDays));

            double totalPrice = selectedInstrument.CalculatePricePerDay(rentalDays);
            System.out.println("\n== Rent Confirmation == ");
            System.out.println("Customer ID: " + customer.getCustomerId());
            System.out.println("Customer Name: " + customer.getCustomerName());
            System.out.println("Instrument Number: " + selectedInstrument.getInstrumentId());
            System.out.println("Instrument Name: " + selectedInstrument.getInstrumentName());
            System.out.println("Rental Days: " + rentalDays);
            System.out.println("Total Price: " + totalPrice + " Bath");
        } else {
            System.out.println("Selected instrument is not available for rent.");
        }
    }

    public void menu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n#### Musical Instrument Rental Shop ####");
            System.out.println("1. Rent a Musical Instrument ");
            System.out.println("2. Return a Musical Instrument ");
            System.out.println("3. Show Available Musical Instruments");
            System.out.println("4. Summarize Customer Rentals");
            System.out.println("5. Cancel a Musical Instrument");
            System.out.println("6. Exit");

            System.out.print("Select : ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (choice == 1) {
                System.out.println("\n== Rent a Musical Instrument ==\n");
                showAvailableInstruments();
                Rental newRental = getRentalInput(scanner);
                Customer newCustomer = getCustomerInput(scanner);
                if (newRental != null) {
                    rentInstrument(newRental.getInstrument().getInstrumentId(), newCustomer, newRental.getDays());
                    String customerId = newCustomer.getCustomerId();
                    while (true) {
                        System.out.println("\nWould you like to rent another musical instrument?");
                        System.out.print("Do you want to rent another instrument (yes/no) : ");
                        String rentAnother = scanner.nextLine();
                        if (rentAnother.equalsIgnoreCase("yes")) {
                            showAvailableInstruments();
                            newRental = getRentalInput(scanner);
                            if (newRental != null) {
                                rentInstrument(newRental.getInstrument().getInstrumentId(), newCustomer, newRental.getDays());
                            }
                        } else if (rentAnother.equalsIgnoreCase("no")) {
                            summarizeCustomerRentals(customerId);
                            break;
                        } else {
                            System.out.println("Invalid input. Please enter 'yes' or 'no'.");
                        }
                    }
                }
                
            } else if (choice == 2) {
                System.out.println("\n== Return a Musical Instrument ==\n");
                System.out.print("Enter customer ID: ");
                String customerId = scanner.nextLine();
                returnInstrument(scanner, customerId);
                continue;

            } else if (choice == 3) {
                showAvailableInstruments();

            } else if (choice == 4) {
                System.out.println("\n== Summarize Customer Rentals ==\n");
                System.out.print("Enter customer ID: ");
                String customerId = scanner.nextLine();
                summarizeCustomerRentals(customerId);
                

            }else if (choice == 5) {
                System.out.println("\n== Cancel a Musical Instrument ==\n");
                System.out.print("Enter customer ID: ");
                String customerId = scanner.nextLine();
                cancelRental(scanner, customerId);
                continue;

            } else if (choice == 6) {
                System.out.println("Exiting the system.");
                // เขียนข้อมูลลูกค้าลงในไฟล์ก่อนออกจากโปรแกรม
                FileHandler.writeCustomerSummariesToFile("customer_summaries.txt", rentals);
                System.exit(0);
            } else {
                System.out.println("Invalid input. Please try again.");

            }
        }
    }
}




