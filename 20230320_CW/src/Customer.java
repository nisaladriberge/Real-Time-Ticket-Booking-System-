public class Customer extends Thread {
    private TicketPool ticketPool;
    private String customerName;
    private int customerRetrievalRate;
    private int ticketAmount;

    public Customer(TicketPool ticketPool, String customerName, int customerRetrievalRate, int ticketAmount) {
        this.ticketPool = ticketPool;
        this.customerName = customerName;
        this.customerRetrievalRate = customerRetrievalRate;
        this.ticketAmount = ticketAmount;
    }

    @Override
    public void run() {
        while (true) {
            if (ticketAmount >= 5) {
                ticketPool.removeTicket(customerRetrievalRate, customerName, ticketAmount);
                try {
                    System.out.println(customerName + " has bought 5 tickets in a row. Please wait 10 seconds...");
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            } else {
                ticketPool.removeTicket(customerRetrievalRate, customerName, ticketAmount);
                try {
                    Thread.sleep(400);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
    }
}
