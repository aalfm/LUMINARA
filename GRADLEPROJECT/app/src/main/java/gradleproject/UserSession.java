package gradleproject;

public class UserSession {

    private static UserSession instance;

    private String username;
    private int userId;
    private String role;

    private UserSession() {
    }

    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    public void setUser(String username, int userId, String role) {
        this.username = username;
        this.userId = userId;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getUserId() {
        return userId;
    }

    public String getRole() {
        return role;
    }

    public void logout() {
        userId = 0;
        role = null;
    }
}