package gradleproject.dao;

import gradleproject.config.DbConnect;
import gradleproject.models.OrganizerProfile;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrganizerDAO {

    public boolean insert(OrganizerProfile organizer) {
        String sql = "INSERT INTO organizers (user_id, name, description, logo_url, approval_status) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DbConnect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, organizer.getUserId());
            pstmt.setString(2, organizer.getName());
            pstmt.setString(3, organizer.getDescription());
            pstmt.setString(4, organizer.getLogoUrl());
            pstmt.setString(5, organizer.getApprovalStatus()); // 'Pending', 'Approved', dll
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error insert organizer: " + e.getMessage());
            return false;
        }
    }

    public OrganizerProfile findByUserId(int userId) {
        String sql = "SELECT * FROM organizers WHERE user_id = ?";
        try (Connection conn = DbConnect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return new OrganizerProfile(
                    rs.getInt("id"),
                    rs.getInt("user_id"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getString("logo_url"),
                    null, // kolom socialMediaLinks tidak ada di schema DB awal Anda
                    rs.getString("approval_status"),
                    rs.getTimestamp("created_at")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error find organizer by user id: " + e.getMessage());
        }
        return null;
    }

    public boolean updateApprovalStatus(int id, String status) {
        String sql = "UPDATE organizers SET approval_status = ? WHERE id = ?";
        try (Connection conn = DbConnect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, status);
            pstmt.setInt(2, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error update organizer status: " + e.getMessage());
            return false;
        }
    }
}