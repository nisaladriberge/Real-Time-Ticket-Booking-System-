public class Vendor implements Runnable {
    private TicketPool ticketPool;
    private int ticketReleaseRate;
    private String vendorName;
    private int totalTickets;

    public Vendor(TicketPool ticketPool, int ticketReleaseRate, String vendorName, int totalTickets) {
        this.ticketPool = ticketPool;
        this.ticketReleaseRate = ticketReleaseRate;
        this.vendorName = vendorName;
        this.totalTickets = totalTickets;
    }

    @Override
    public void run() {
        while (true) {
            ticketPool.addTicket(vendorName, ticketReleaseRate, totalTickets);
            try {
                Thread.sleep(500); // Pause between adding tickets
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
