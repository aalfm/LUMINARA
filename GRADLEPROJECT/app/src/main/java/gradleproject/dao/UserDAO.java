package gradleproject.dao;

import gradleproject.config.DbConnect;
import gradleproject.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserDAO {

    public boolean insert(User user) {
        // 👉 FIX: Menggunakan nama kolom yang sesuai dengan SQLite (name dan no_telepon)
        String sql = "INSERT INTO users (username, email, password, phone_number, role) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DbConnect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, user.getPassword());
            pstmt.setString(4, user.getPhoneNumber());
            pstmt.setString(5, user.getRole());
            
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        user.setId(generatedKeys.getInt(1)); 
                    }
                }
                return true;
            }
            return false;
            
        } catch (SQLException e) {
            System.err.println("❌ Error insert user di UserDAO: " + e.getMessage());
            return false;
        }
    }

    public User findByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        try (Connection conn = DbConnect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                // 👉 FIX: Membaca nama kolom yang benar (name dan no_telepon)
                return new User(
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getString("phone_number"),
                    rs.getString("role"),
                    rs.getString("account_status"),
                    rs.getTimestamp("created_at")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error find user by email: " + e.getMessage());
        }
        return null; 
    }

    public User findById(int id) {
        User user = null;
        String sql = "SELECT * FROM users WHERE id = ?"; 
        
        try (Connection conn = DbConnect.getConnection(); 
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                // 👉 FIX: Disamakan urutannya (8 Parameter) seperti findByEmail & login!
                user = new User(
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getString("phone_number"),
                    rs.getString("role"),
                    rs.getString("account_status"),
                    rs.getTimestamp("created_at")
                );
            }
        } catch (SQLException e) {
            System.out.println("⚠️ Error cari user berdasarkan ID: " + e.getMessage());
        }
        return user;
    }

    public User login(String email, String password) {
        String sql = "SELECT * FROM users WHERE email = ? AND password = ?";
        try (Connection conn = DbConnect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, email);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                // 👉 FIX: Membaca nama kolom yang benar (name dan no_telepon)
                // Contoh yang benar berdasarkan screenshot DB Anda:
                return new User(
                    rs.getInt("id"),
                    rs.getString("username"),    // Pastikan ini "username", bukan "name"
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getString("phone_number"), // Pastikan ini "phone_number", bukan "no_telepon"
                    rs.getString("role"),
                    rs.getString("account_status"),
                    rs.getTimestamp("created_at")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error login user: " + e.getMessage());
        }
        return null;
    }

    public boolean updateRole(int id, String role) {
        String sql = "UPDATE users SET role = ? WHERE id = ?";
        try (Connection conn = DbConnect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, role);
            pstmt.setInt(2, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error update user role: " + e.getMessage());
            return false;
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM users WHERE id = ?";
        try (Connection conn = DbConnect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error delete user: " + e.getMessage());
            return false;
        }
    }

    public boolean updateUserProfile(int id, String name, String email, String phone, String password) {
    String sql = "UPDATE users SET username = ?, email = ?, phone_number = ?, password = ? WHERE id = ?";
    try (Connection conn = DbConnect.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        
        pstmt.setString(1, name);
        pstmt.setString(2, email);
        pstmt.setString(3, phone);
        pstmt.setString(4, password);
        pstmt.setInt(5, id);
        
        return pstmt.executeUpdate() > 0;
    } catch (SQLException e) {
        System.err.println("Error update profil: " + e.getMessage());
        return false;
    }
}
}