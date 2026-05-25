package gradleproject.dao;

import gradleproject.config.DbConnect;
import gradleproject.models.AuditLog;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AuditLogDAO {

    // Menyimpan log aktivitas baru
    public boolean insert(AuditLog log) {
        String sql = "INSERT INTO audit_logs (admin_id, action_type, description) VALUES (?, ?, ?)";
        try (Connection conn = DbConnect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, log.getAdminId());
            pstmt.setString(2, log.getActionType());
            pstmt.setString(3, log.getDescription());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error insert audit log: " + e.getMessage());
            return false;
        }
    }

    // Menampilkan semua log aktivitas (biasanya untuk halaman Super Admin)
    public List<AuditLog> findAll() {
        String sql = "SELECT * FROM audit_logs ORDER BY created_at DESC";
        List<AuditLog> logs = new ArrayList<>();
        
        try (Connection conn = DbConnect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                AuditLog log = new AuditLog(
                    rs.getInt("id"),
                    rs.getInt("admin_id"),
                    rs.getString("action_type"),
                    rs.getString("description"),
                    rs.getTimestamp("created_at")
                );
                logs.add(log);
            }
        } catch (SQLException e) {
            System.err.println("Error fetch audit logs: " + e.getMessage());
        }
        return logs;
    }

    // Menampilkan log aktivitas berdasarkan Admin tertentu
    public List<AuditLog> findByAdminId(int adminId) {
        String sql = "SELECT * FROM audit_logs WHERE admin_id = ? ORDER BY created_at DESC";
        List<AuditLog> logs = new ArrayList<>();
        
        try (Connection conn = DbConnect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, adminId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                AuditLog log = new AuditLog(
                    rs.getInt("id"),
                    rs.getInt("admin_id"),
                    rs.getString("action_type"),
                    rs.getString("description"),
                    rs.getTimestamp("created_at")
                );
                logs.add(log);
            }
        } catch (SQLException e) {
            System.err.println("Error fetch audit logs by admin: " + e.getMessage());
        }
        return logs;
    }
}