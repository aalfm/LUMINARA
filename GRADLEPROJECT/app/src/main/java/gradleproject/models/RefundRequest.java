package gradleproject.models;

public class RefundRequest {
    private int id;
    private String eventName;
    private String userName;
    private double total;
    private String status;

    // Tambahkan konstruktor kosong
    public RefundRequest() {}

    // Tambahkan semua Getter & Setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getEventName() { return eventName; }
    public void setEventName(String eventName) { this.eventName = eventName; }
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}