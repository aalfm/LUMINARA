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
    private Double price;
    private Timestamp eventDate;
    private String location;
    private String imagePath;
    private String Name;


    public Event() {}

    public Event(int id, int organizerId, String title, String description, String category, String ticketType, 
                 String status, int quota, Double price, Timestamp eventDate, String location, String imagePath) {
        super(id);
        this.organizerId = organizerId;
        this.title = title;
        this.description = description;
        this.category = category;
        this.ticketType = ticketType;
        this.status = status;
        this.quota = quota;
        this.eventDate = eventDate;
        this.price = price;
        this.location = location;
        this.imagePath = imagePath;
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

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public Timestamp getEventDate() { return eventDate; }
    public void setEventDate(Timestamp eventDate) { this.eventDate = eventDate; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }

    public String getName() { return Name; }
}