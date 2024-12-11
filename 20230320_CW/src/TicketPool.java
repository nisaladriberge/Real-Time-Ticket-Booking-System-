import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class TicketPool {
    // Synchronized list to store tickets
    private List<Ticket> tickets;
    private int maxTicketCapacity;// Maximum capacity of the ticket pool
    private int IDGenerator;// Unique ID generator for tickets

    // Constructor to initialize the ticket pool
    public TicketPool(int totalTickets, int maxTicketCapacity) {
        this.tickets = Collections.synchronizedList(new LinkedList<>());
        this.maxTicketCapacity = maxTicketCapacity;
        this.IDGenerator = 1;// Start ticket IDs from 1

        // Initialize the ticket pool
        for (int i = 0; i < totalTickets; i++) {
            tickets.add(new Ticket(IDGenerator++, 1000.0)); // Default ticket price
        }
    }
    // Method to add tickets to the pool by a vendor
    public synchronized void addTicket(String vendorName, int ticketReleaseRate, int ticketsToAdd) {
        for (int i = 0; i < ticketsToAdd; i++) {
            while (IDGenerator > maxTicketCapacity) { // Wait if the pool is at maximum capacity
                try {
                    System.out.println("[INFO] Ticket pool is full. Waiting for space...");
                    wait(); // Wait until space is available
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();// Handle interruption
                    return; // Exit if interrupted
                }
            }

            try {
                Ticket ticket = new Ticket(IDGenerator++, 1000.0); // Default ticket price
                tickets.add(ticket);
                Thread.sleep(600 / ticketReleaseRate); // Rate-controlled addition
                System.out.println("[SUCCESS] Vendor " + vendorName + " added a ticket. Ticket Details: " + ticket);
                notifyAll();// Notify waiting threads that a ticket has been added
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return; // Exit if interrupted
            }
        }
    }
    // Method to remove tickets from the pool by a customer
    public synchronized boolean removeTicket(int customerRetrievalRate, String customerName, int ticketAmount) {
        while (tickets.isEmpty()) {  // Wait if the pool is empty
            try {
                System.out.println("[INFO] All tickets are sold. Waiting for new tickets...");
                wait();// Wait until new tickets are available
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();// Handle interruption
                return false; // Exit method
            }
        }

        if (tickets.size() >= ticketAmount) { // Check if enough tickets are available
            for (int i = 0; i < ticketAmount; i++) {
                try {
                    Ticket ticket = tickets.remove(0);// Remove a ticket from the pool
                    ticket.purchaseTicket(customerName);// Mark the ticket as purchased by the customer
                    Thread.sleep(600 / customerRetrievalRate); // Simulate retrieval delay
                    System.out.println("[SUCCESS] Customer " + customerName + " purchased a ticket. Ticket Details: " + ticket);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();// Handle interruption
                    return false;// Exit method
                }
            }
            return true;// Tickets successfully retrieved
       // } else {
          //  try{
      //      System.out.println("[WARN] Not enough tickets available for " + customerName + ".");
          //  wait();
          //  } catch (InterruptedException e) {
         //       throw new RuntimeException(e);
            }
            return false;// Not enough tickets available
        }
    }
//}
