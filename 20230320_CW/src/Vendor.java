public class Vendor implements Runnable {
    // Fields to store vendor-specific properties
    private TicketPool ticketPool;// Reference to the shared ticket pool
    private int ticketReleaseRate; // Rate at which tickets are released by the vendor
    private String vendorName; // Name of the vendor
    private int totalTickets;// Total number of tickets to be added by the vendor

    // Constructor to initialize vendor properties
    public Vendor(TicketPool ticketPool, int ticketReleaseRate, String vendorName, int totalTickets) {
        this.ticketPool = ticketPool;
        this.ticketReleaseRate = ticketReleaseRate;
        this.vendorName = vendorName;
        this.totalTickets = totalTickets;
    }
    // Overriding the run method to define the behavior of the vendor thread
    @Override
    public void run() {
        while (true) { // Infinite loop to continuously add tickets
            ticketPool.addTicket(vendorName, ticketReleaseRate, totalTickets); // Add tickets to the pool
            try {
                Thread.sleep(500); // Pause between adding tickets
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();// Handle thread interruption
                break;// Exit the loop when interrupted
            }
        }
    }
}
