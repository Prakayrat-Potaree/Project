import java.util.List;

public class Main {
    public static void main(String[] args) {
        MusicalRentalSystem rentalSystem = new MusicalRentalSystem();

        Musical inMusical1 = new Musical("1", "Flute", 80);
        Musical inMusical2 = new Musical("2", "Violin", 100);
        Musical inMusical3 = new Musical("3", "Guitar", 120);
        Musical inMusical4 = new Musical("4", "Bass", 120);
        Musical inMusical5 = new Musical("5", "Drums", 250);

        rentalSystem.addInstrument(inMusical1);
        rentalSystem.addInstrument(inMusical2);
        rentalSystem.addInstrument(inMusical3);
        rentalSystem.addInstrument(inMusical4);
        rentalSystem.addInstrument(inMusical5);

        rentalSystem.menu();

        String customerSummariesFile = "customer_summaries.txt";

        // เขียนข้อมูลลูกค้าลงในไฟล์
        List<Rental> rentals = rentalSystem.getRentals();
        FileHandler.writeCustomerSummariesToFile(customerSummariesFile, rentals);

        // อ่านข้อมูลลูกค้าจากไฟล์
        List<Customer> customersFromFile = FileHandler.readCustomersFromFile("customer_summaries.txt");
        System.out.println("\nCustomers read from file:");
        for (Customer customer : customersFromFile) {
            rentalSystem.addCustomer(customer); // เพิ่มข้อมูลลูกค้าเข้าระบบ
            System.out.println(customer);
        }
    }
}
