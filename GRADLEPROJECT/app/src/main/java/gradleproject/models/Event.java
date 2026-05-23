package gradleproject.models;

import java.sql.Timestamp;

public class Event extends BaseEntity {
    private int organizerId;
    private String title;
    private String description; // Sesuai permintaan (gabungan preview_text/detail_description)
    private String category; // Festival, Lokakarya, Musik, Kultural
    private String ticketType; // Free, Paid
    private String status; // Draft, Active, Past, Pending
    private int quota;
    private Timestamp eventDate;

    public Event() {}

    public Event(int id, int organizerId, String title, String description, String category, String ticketType, String status, int quota, Timestamp eventDate) {
        super(id);
        this.organizerId = organizerId;
        this.title = title;
        this.description = description;
        this.category = category;
        this.ticketType = ticketType;
        this.status = status;
        this.quota = quota;
        this.eventDate = eventDate;
    }

    // Getters and Setters
    public int getOrganizerId() { return organizerId; }
    public void setOrganizerId(int organizerId) { this.organizerId = organizerId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getTicketType() { return ticketType; }
    public void setTicketType(String ticketType) { this.ticketType = ticketType; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public int getQuota() { return quota; }
    public void setQuota(int quota) { this.quota = quota; }

    public Timestamp getEventDate() { return eventDate; }
    public void setEventDate(Timestamp eventDate) { this.eventDate = eventDate; }
}