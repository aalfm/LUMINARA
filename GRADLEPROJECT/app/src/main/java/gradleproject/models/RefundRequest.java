package gradleproject.models;

import java.sql.Timestamp;

public class RefundRequest extends BaseEntity {
    private int ticketId;
    private String reason;
    private String status; // 'Pending', 'Approved', 'Rejected'
    private Timestamp requestedAt;
    private Timestamp resolvedAt;

    public RefundRequest() {}

    public RefundRequest(int id, int ticketId, String reason, String status, Timestamp requestedAt, Timestamp resolvedAt) {
        super(id);
        this.ticketId = ticketId;
        this.reason = reason;
        this.status = status;
        this.requestedAt = requestedAt;
        this.resolvedAt = resolvedAt;
    }

    // Getters and Setters
    public int getTicketId() { return ticketId; }
    public void setTicketId(int ticketId) { this.ticketId = ticketId; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Timestamp getRequestedAt() { return requestedAt; }
    public void setRequestedAt(Timestamp requestedAt) { this.requestedAt = requestedAt; }

    public Timestamp getResolvedAt() { return resolvedAt; }
    public void setResolvedAt(Timestamp resolvedAt) { this.resolvedAt = resolvedAt; }
}