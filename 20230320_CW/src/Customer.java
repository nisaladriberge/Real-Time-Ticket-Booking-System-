public class Customer extends Thread {
    // Fields to store the ticket pool, customer name, retrieval rate, and ticket amount
    private TicketPool ticketPool;
    private String customerName;
    private int customerRetrievalRate;
    private int ticketAmount;

    // Constructor to initialize customer properties
    public Customer(TicketPool ticketPool, String customerName, int customerRetrievalRate, int ticketAmount) {
        this.ticketPool = ticketPool;
        this.customerName = customerName;
        this.customerRetrievalRate = customerRetrievalRate;
        this.ticketAmount = ticketAmount;
    }

    // Overriding the run method to define the behavior of the customer thread
    @Override
    public void run() {
        while (true) {
            if (ticketAmount >= 5) {// If the customer wants to retrieve 5 or more tickets
                // Attempt to retrieve tickets from the ticket pool
                ticketPool.removeTicket(customerRetrievalRate, customerName, ticketAmount);
                try {
                    // Notify and pause for 10 seconds after buying 5 tickets
                    System.out.println(customerName + " has bought 5 tickets in a row. Please wait 10 seconds...");
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    // Handle thread interruption and exit
                    Thread.currentThread().interrupt();
                }
            } else { // For fewer than 5 tickets
                // Attempt to retrieve tickets from the ticket pool
                ticketPool.removeTicket(customerRetrievalRate, customerName, ticketAmount);
                try {
                    // Pause for 400 milliseconds before the next retrieval
                    Thread.sleep(400);
                } catch (InterruptedException e) {
                    // Handle thread interruption and break the loop
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
    }
}
