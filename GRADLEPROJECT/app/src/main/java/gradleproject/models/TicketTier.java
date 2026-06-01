package gradleproject.models;

public class TicketTier extends BaseEntity {
    private int eventId;
    private String ticketType; // "PAID" / "FREE"
    private double price;
    private int quota;

    public TicketTier() {}

    public TicketTier(int eventId, String ticketType, double price, int quota) {
        this.eventId = eventId;
        this.ticketType = ticketType;
        this.price = price;
        this.quota = quota;
    }

    public TicketTier(int id, int eventId, String ticketType, double price, int quota) {
        super(id);
        this.eventId = eventId;
        this.ticketType = ticketType;
        this.price = price;
        this.quota = quota;
    }

    // getter setter tetap

    public int getEventId() { return eventId; }
    public void setEventId(int eventId) { this.eventId = eventId; }

    public String getTicketType() { return ticketType; }
    public void setTicketType(String ticketType) { this.ticketType = ticketType; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getQuota() { return quota; }
    public void setQuota(int quota) { this.quota = quota; }
}