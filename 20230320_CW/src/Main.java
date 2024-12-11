import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class Main { // Main class of the ticket management system
    private static volatile boolean isRunning = true; // Shared flag to control program execution
    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        int totalTickets = 0, ticketReleaseRate = 0, customerRetrievalRate = 0, maxTicketCapacity = 0;

        System.out.println(    // Display welcome message
                "==============================================\n" +
                        "   WELCOME TO THE TICKET MANAGEMENT SYSTEM   \n" +
                        "==============================================");
        // Prompt user to choose between new or existing configuration
        System.out.println("Y - Add new configuration settings...\nN - Use previous configuration settings");

        String choice = scanner.nextLine().toUpperCase();

        if (choice.equals("Y")) {
            // Gather new configuration settings from the user
            totalTickets = getValidatedInput(scanner, "Enter the total tickets each vendor will release: ");
            ticketReleaseRate = getValidatedInput(scanner, "Enter the ticket release rate (tickets per minute): ");
            customerRetrievalRate = getValidatedInput(scanner, "Enter the customer retrieval rate (tickets per minute): ");
            maxTicketCapacity = getValidatedInput(scanner, "Enter the maximum ticket capacity: ");

            System.out.println("System configuration completed!");

            // Save configuration to a JSON file
            Configuration config = new Configuration(totalTickets, ticketReleaseRate, customerRetrievalRate, maxTicketCapacity);
            String json = config.toJson();

            try (FileWriter writer = new FileWriter("config.json")) {
                writer.write(json);
                System.out.println("Configuration saved to config.json");
            } catch (IOException e) {
                System.err.println("Error saving configuration to file: " + e.getMessage());
            }
        } else if (choice.equals("N")) {
            // Load previous configuration settings
            Configuration config = Configuration.fromJson("config.json");
            if (config != null) {
                totalTickets = config.getTotalTickets();
                ticketReleaseRate = config.getTicketReleaseRate();
                customerRetrievalRate = config.getCustomerRetrievalRate();
                maxTicketCapacity = config.getMaxTicketCapacity();

                System.out.println("Loaded configuration from config.json:");
                System.out.println("Total Tickets: " + totalTickets);
                System.out.println("Ticket Release Rate: " + ticketReleaseRate);
                System.out.println("Customer Retrieval Rate: " + customerRetrievalRate);
                System.out.println("Max Ticket Capacity: " + maxTicketCapacity);
            } else {
                // Prompt user to enter new configuration if loading fails
                System.out.println("Error loading previous configuration. Please enter new settings.");
                totalTickets = getValidatedInput(scanner, "Enter the total tickets each vendor will release: ");
                ticketReleaseRate = getValidatedInput(scanner, "Enter the ticket release rate (tickets per minute): ");
                customerRetrievalRate = getValidatedInput(scanner, "Enter the customer retrieval rate (tickets per minute): ");
                maxTicketCapacity = getValidatedInput(scanner, "Enter the maximum ticket capacity: ");
            }
        } else {
            // Handle invalid input and terminate the program
            System.out.println("Invalid input. Exiting...");
            scanner.close();
            return;
        }

        // Initialize the ticket pool
        TicketPool ticketPool = new TicketPool(totalTickets, maxTicketCapacity);

        // Vendor parameters
        System.out.println("\nVendor Configuration:");
        System.out.print("Enter Vendor 1 name: ");
        scanner.nextLine(); // Consume the newline left by the previous nextInt()
        String vendor1Name = scanner.nextLine();
        int vendor1Tickets = getValidatedInput(scanner, "Enter tickets to add for Vendor 1: ");

        System.out.print("Enter Vendor 2 name: ");
        scanner.nextLine();
        String vendor2Name = scanner.nextLine();
        int vendor2Tickets = getValidatedInput(scanner, "Enter tickets to add for Vendor 2: ");


        // Customer parameters
        System.out.println("\nCustomer Configuration:");
        System.out.print("Enter Customer 1 name: ");
        scanner.nextLine();
        String customer1Name = scanner.nextLine();
        int customer1Tickets = getValidatedInput(scanner, "Enter tickets to retrieve for Customer 1: ");

        System.out.print("Enter Customer 2 name: ");
        scanner.nextLine();
        String customer2Name = scanner.nextLine();
        int customer2Tickets = getValidatedInput(scanner, "Enter tickets to retrieve for Customer 2: ");

        // Creating Vendor threads
        Thread vendor1 = new Thread(new Vendor(ticketPool, ticketReleaseRate, vendor1Name, vendor1Tickets));
        Thread vendor2 = new Thread(new Vendor(ticketPool, ticketReleaseRate, vendor2Name, vendor2Tickets));

        // Creating Customer threads
        Thread customer1 = new Customer(ticketPool, customer1Name, customerRetrievalRate, customer1Tickets);
        Thread customer2 = new Customer(ticketPool, customer2Name, customerRetrievalRate, customer2Tickets);

        System.out.println("\nStarting Ticket System Simulation...\n");

        // Starting threads
        vendor1.start();
        Thread.sleep(200); // Small delay for demonstration purposes
        vendor2.start();

        customer1.start();
        customer2.start();

        // User input loop to stop the simulation
        while (isRunning) {
            System.out.println("Enter 'stop' to terminate the program or press any key to continue...");
            String command = scanner.nextLine().toLowerCase();

            if (command.equals("stop")) {
                System.out.println("Stopping the program...");
                isRunning = false;
            }
        }

        // Interrupt threads and clean up
        vendor1.interrupt();
        vendor2.interrupt();
        customer1.interrupt();
        customer2.interrupt();

        System.out.println("Simulation stopped. Exiting...");
        scanner.close();
        System.exit(0);
    }

    // Method to get validated integer input
    private static int getValidatedInput(Scanner scanner, String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return scanner.nextInt();
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                scanner.nextLine(); // Clear invalid input
            }
        }
    }
}
