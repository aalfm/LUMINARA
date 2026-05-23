package gradleproject.models;

public class Review extends BaseEntity {
    private int eventId;
    private int userId;
    private int rating;
    private String comment;

    public Review() {}

    public Review(int id, int eventId, int userId, int rating, String comment) {
        super(id);
        this.eventId = eventId;
        this.userId = userId;
        this.rating = rating;
        this.comment = comment;
    }

    public int getEventId() { return eventId; }
    public void setEventId(int eventId) { this.eventId = eventId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }
}