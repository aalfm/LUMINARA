package gradleproject.config;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;


public class DbConnect {
    private static final String DB_URL = "jdbc:sqlite:db/db_user.db";

    private static Connection connection;

    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(DB_URL);
                System.out.println("Database Connected");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection; 
    }
}
