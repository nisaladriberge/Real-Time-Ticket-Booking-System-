import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class TicketPool {
    private List<Ticket> tickets;
    private int maxTicketCapacity;
    private int IDGenerator;

    public TicketPool(int totalTickets, int maxTicketCapacity) {
        this.tickets = Collections.synchronizedList(new LinkedList<>());
        this.maxTicketCapacity = maxTicketCapacity;
        this.IDGenerator = 1;

        // Initialize the ticket pool
        for (int i = 0; i < totalTickets; i++) {
            tickets.add(new Ticket(IDGenerator++, 1000.0)); // Default ticket price
        }
    }

    public synchronized void addTicket(String vendorName, int ticketReleaseRate, int ticketsToAdd) {
        for (int i = 0; i < ticketsToAdd; i++) {
            while (IDGenerator > maxTicketCapacity) {
                try {
                    System.out.println("[INFO] Ticket pool is full. Waiting for space...");
                    wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return; // Exit if interrupted
                }
            }

            try {
                Ticket ticket = new Ticket(IDGenerator++, 1000.0); // Default ticket price
                tickets.add(ticket);
                Thread.sleep(600 / ticketReleaseRate); // Rate-controlled addition
                System.out.println("[SUCCESS] Vendor " + vendorName + " added a ticket. Ticket Details: " + ticket);
                notifyAll();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return; // Exit if interrupted
            }
        }
    }

    public synchronized boolean removeTicket(int customerRetrievalRate, String customerName, int ticketAmount) {
        while (tickets.isEmpty()) {
            try {
                System.out.println("[INFO] All tickets are sold. Waiting for new tickets...");
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return false;
            }
        }

        if (tickets.size() >= ticketAmount) {
            for (int i = 0; i < ticketAmount; i++) {
                try {
                    Ticket ticket = tickets.remove(0);
                    ticket.purchaseTicket(customerName);
                    Thread.sleep(600 / customerRetrievalRate); // Simulate retrieval delay
                    System.out.println("[SUCCESS] Customer " + customerName + " purchased a ticket. Ticket Details: " + ticket);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return false;
                }
            }
            return true;
       // } else {
          //  try{
      //      System.out.println("[WARN] Not enough tickets available for " + customerName + ".");
          //  wait();
          //  } catch (InterruptedException e) {
         //       throw new RuntimeException(e);
            }
            return false;
        }
    }
//}
