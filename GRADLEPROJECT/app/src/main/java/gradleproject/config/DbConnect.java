package gradleproject.config;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;

public class DbConnect {
    private static final String DB_FOLDER = "db";
    private static final String DB_URL = "jdbc:sqlite:" + DB_FOLDER + "/db_user.db";

    public static Connection getConnection() {
        try {
            File folder = new File(DB_FOLDER);
            if (!folder.exists()) {
                folder.mkdirs();
            }
            // Murni langsung mengembalikan koneksi baru setiap kali dipanggil
            return DriverManager.getConnection(DB_URL);
        } catch (SQLException e) {
            System.err.println("❌ Gagal membuka database SQLite: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}