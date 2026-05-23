package gradleproject.dao;

import gradleproject.config.DbConnect;
import gradleproject.models.TicketTier;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TicketTierDAO {

    public boolean insert(TicketTier ticketTier) {
        String sql = "INSERT INTO ticket_tiers (event_id, ticket_type, price, quota) VALUES (?, ?, ?, ?)";
        try (Connection conn = DbConnect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, ticketTier.getEventId());
            pstmt.setString(2, ticketTier.getTicketType());
            pstmt.setDouble(3, ticketTier.getPrice());
            pstmt.setInt(4, ticketTier.getQuota());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error insert ticket tier: " + e.getMessage());
            return false;
        }
    }

    public List<TicketTier> findByEventId(int eventId) {
        String sql = "SELECT * FROM ticket_tiers WHERE event_id = ?";
        List<TicketTier> tiers = new ArrayList<>();
        
        try (Connection conn = DbConnect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, eventId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                TicketTier tier = new TicketTier(
                    rs.getInt("id"),
                    rs.getInt("event_id"),
                    rs.getString("ticket_type"),
                    rs.getDouble("price"),
                    rs.getInt("quota")
                );
                tiers.add(tier);
            }
        } catch (SQLException e) {
            System.err.println("Error get ticket tiers by event: " + e.getMessage());
        }
        return tiers;
    }
}