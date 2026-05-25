package gradleproject.models;

import java.sql.Timestamp;

public class OrganizerProfile extends BaseEntity {
    private int userId; // Relasi ke User
    private String name;
    private String description;
    private String logoUrl;
    private String socialMediaLinks; 
    private String approvalStatus;
    private Timestamp createdAt;

    public OrganizerProfile() {}

    public OrganizerProfile(int id, int userId, String name, String description, String logoUrl, String socialMediaLinks, String approvalStatus, Timestamp createdAt) {
        super(id);
        this.userId = userId;
        this.name = name;
        this.description = description;
        this.logoUrl = logoUrl;
        this.socialMediaLinks = socialMediaLinks;
        this.approvalStatus = approvalStatus;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getLogoUrl() { return logoUrl; }
    public void setLogoUrl(String logoUrl) { this.logoUrl = logoUrl; }

    public String getSocialMediaLinks() { return socialMediaLinks; }
    public void setSocialMediaLinks(String socialMediaLinks) { this.socialMediaLinks = socialMediaLinks; }

    public String getApprovalStatus() { return approvalStatus; }
    public void setApprovalStatus(String approvalStatus) { this.approvalStatus = approvalStatus; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
}