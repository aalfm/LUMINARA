package gradleproject.models;

import java.sql.Timestamp;

public class UserProfile extends User {
    private String activityHistory;
    private String preferences;

    public UserProfile() {}

    public UserProfile(int id, String username, String email, String password, String phoneNumber, String role, String accountStatus, Timestamp createdAt, String activityHistory, String preferences) {
        super(id, username, email, password, phoneNumber, role, accountStatus, createdAt);
        this.activityHistory = activityHistory;
        this.activityHistory = activityHistory;
        this.preferences = preferences;
    }

    public String getActivityHistory() { return activityHistory; }
    public void setActivityHistory(String activityHistory) { this.activityHistory = activityHistory; }

    public String getPreferences() { return preferences; }
    public void setPreferences(String preferences) { this.preferences = preferences; }
}