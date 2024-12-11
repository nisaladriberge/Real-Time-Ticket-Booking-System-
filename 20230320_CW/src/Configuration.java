import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import java.io.FileReader;
import java.io.IOException;

public class Configuration {
    // Fields for storing configuration properties
    private int totalTickets;// Total number of tickets available
    private int ticketReleaseRate;// Rate at which tickets are released
    private int customerRetrievalRate;// Rate at which customers retrieve tickets
    private int maxTicketCapacity;// Maximum ticket capacity of the system

    // Constructor to initialize the configuration object
    public Configuration(int totalTickets, int ticketReleaseRate, int customerRetrievalRate, int maxTicketCapacity) {
        this.totalTickets = totalTickets;
        this.ticketReleaseRate = ticketReleaseRate;
        this.customerRetrievalRate = customerRetrievalRate;
        this.maxTicketCapacity = maxTicketCapacity;
    }
    // Serialize the Configuration object into a JSON string
    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);  // in this i used toJson method to convert the object to json file..  serialization approach..
    }
    // Deserialize JSON back into an object (Deserialization)
    public static Configuration fromJson(String filename) {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(filename)) {
            return gson.fromJson(reader, Configuration.class);  // Convert JSON string back to SystemConfiguration object
        } catch (IOException | JsonSyntaxException e) {
            System.out.println("Error reading JSON: " + e.getMessage());
            return null;
        }
    }

    public int getTotalTickets() {
        return totalTickets;
    }

    public void setTotalTickets(int totalTickets) {
        this.totalTickets = totalTickets;
    }

    public int getTicketReleaseRate() {
        return ticketReleaseRate;
    }

    public void setTicketReleaseRate(int ticketReleaseRate) {
        this.ticketReleaseRate = ticketReleaseRate;
    }

    public int getCustomerRetrievalRate() {
        return customerRetrievalRate;
    }

    public void setCustomerRetrievalRate(int customerRetrievalRate) {
        this.customerRetrievalRate = customerRetrievalRate;
    }

    public int getMaxTicketCapacity() {
        return maxTicketCapacity;
    }

    public void setMaxTicketCapacity(int maxTicketCapacity) {
        this.maxTicketCapacity = maxTicketCapacity;
    }
}
