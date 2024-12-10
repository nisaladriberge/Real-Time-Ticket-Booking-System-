public class Ticket {
    private int ticketId;
    private String customerName;
    private double ticketPrice;
    private boolean isPurchased;

    public Ticket(int ticketId, double ticketPrice) {
        this.ticketId = ticketId;
        this.ticketPrice = ticketPrice;
        this.isPurchased = false;
    }

    public void purchaseTicket(String customerName) {
        this.customerName = customerName;
        this.isPurchased = true;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "ticketId=" + ticketId +
                ", customerName='" + (customerName != null ? customerName : "Not Purchased") + '\'' +
                ", price=" + ticketPrice +
                ", isPurchased=" + isPurchased +
                '}';
    }
}
