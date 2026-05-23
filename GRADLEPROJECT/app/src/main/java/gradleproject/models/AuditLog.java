package gradleproject.models;

import java.sql.Timestamp;

public class AuditLog extends BaseEntity {
    private int adminId;
    private String actionType; // Contoh: "APPROVE_EVENT", "BAN_USER", "UPDATE_SETTING"
    private String description;
    private Timestamp createdAt;

    public AuditLog() {}

    public AuditLog(int id, int adminId, String actionType, String description, Timestamp createdAt) {
        super(id);
        this.adminId = adminId;
        this.actionType = actionType;
        this.description = description;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public int getAdminId() { return adminId; }
    public void setAdminId(int adminId) { this.adminId = adminId; }

    public String getActionType() { return actionType; }
    public void setActionType(String actionType) { this.actionType = actionType; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
}