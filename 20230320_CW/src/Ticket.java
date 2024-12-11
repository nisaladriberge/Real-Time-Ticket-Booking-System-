public class Ticket {
    // Fields to store ticket properties
    private int ticketId;// Unique identifier for the ticket
    private String customerName;// Name of the customer who purchased the ticket
    private double ticketPrice;// Price of the ticket
    private boolean isPurchased;// Flag to indicate if the ticket has been purchased

    // Constructor to initialize a ticket with ID and price
    public Ticket(int ticketId, double ticketPrice) {
        this.ticketId = ticketId;
        this.ticketPrice = ticketPrice;
        this.isPurchased = false;// Initially, the ticket is not purchased
    }
    // Method to purchase the ticket by assigning a customer name and marking it as purchased
    public void purchaseTicket(String customerName) {
        this.customerName = customerName;// Assign the customer name to the ticket
        this.isPurchased = true;// Mark the ticket as purchased
    }
    // Overriding the toString method to provide a string representation of the ticket
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
