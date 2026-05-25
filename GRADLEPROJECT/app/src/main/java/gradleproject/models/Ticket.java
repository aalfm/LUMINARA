package gradleproject.models;

public class Ticket extends BaseEntity {
    private int eventId;
    private int userId;
    private int ticketTierId; // Ditambahkan agar selaras dengan skema database
    private String ticketType; // Ditambahkan untuk menampung data "TBA" / hasil JOIN
    private String paymentStatus; // Pending, Paid, Cancelled

    public Ticket() {}

    public Ticket(int id, int eventId, int userId, int ticketTierId, String ticketType, String paymentStatus) {
        super(id); // ID dikelola oleh BaseEntity
        this.eventId = eventId;
        this.userId = userId;
        this.ticketTierId = ticketTierId;
        this.ticketType = ticketType;
        this.paymentStatus = paymentStatus;
    }

    // Getters and Setters
    public int getEventId() { return eventId; }
    public void setEventId(int eventId) { this.eventId = eventId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public int getTicketTierId() { return ticketTierId; }
    public void setTicketTierId(int ticketTierId) { this.ticketTierId = ticketTierId; }

    public String getTicketType() { return ticketType; }
    public void setTicketType(String ticketType) { this.ticketType = ticketType; }

    public String getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }
    
    // Catatan: getTicketId() & setTicketId() dihapus. 
    // Gunakan getId() & setId() bawaan dari class BaseEntity.
}