package gradleproject.dao;

import gradleproject.config.DbConnect;
import gradleproject.models.Ticket;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TicketDAO {

    public boolean bookTicket(Ticket ticket) {
        String sql = "INSERT INTO tickets (event_id, user_id, ticket_tier_id, payment_status) VALUES (?, ?, ?, ?)";
        try (Connection conn = DbConnect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, ticket.getEventId());
            pstmt.setInt(2, ticket.getUserId());
            pstmt.setInt(3, ticket.getTicketTierId()); // Sekarang diambil dari object model
            pstmt.setString(4, ticket.getPaymentStatus());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error book ticket: " + e.getMessage());
            return false;
        }
    }

    public List<Ticket> findByUserId(int userId) {
        return getTicketsByQuery("SELECT * FROM tickets WHERE user_id = ?", userId);
    }

    public List<Ticket> findByEventId(int eventId) {
        return getTicketsByQuery("SELECT * FROM tickets WHERE event_id = ?", eventId);
    }

    public boolean updatePaymentStatus(int id, String paymentStatus) {
        String sql = "UPDATE tickets SET payment_status = ? WHERE id = ?";
        try (Connection conn = DbConnect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, paymentStatus);
            pstmt.setInt(2, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error update ticket payment status: " + e.getMessage());
            return false;
        }
    }

    private List<Ticket> getTicketsByQuery(String sql, int parameter) {
        List<Ticket> tickets = new ArrayList<>();
        try (Connection conn = DbConnect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, parameter);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Ticket ticket = new Ticket(
                    rs.getInt("id"),
                    rs.getInt("event_id"),
                    rs.getInt("user_id"),
                    rs.getInt("ticket_tier_id"), // Mengambil mapping dari DB
                    "TBA", // ticketType diambil via JOIN jika diperlukan nanti
                    rs.getString("payment_status")
                );
                tickets.add(ticket);
            }
        } catch (SQLException e) {
            System.err.println("Error fetch tickets: " + e.getMessage());
        }
        return tickets;
    }
}