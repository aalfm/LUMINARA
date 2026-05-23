package gradleproject.dao;

import gradleproject.config.DbConnect;
import gradleproject.models.Transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TransactionDAO {

    // Jika Anda memiliki tabel transactions terpisah
    public boolean insertTransaction(Transaction transaction) {
        String sql = "INSERT INTO transactions (amount, transaction_date, status) VALUES (?, ?, ?)";
        try (Connection conn = DbConnect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setDouble(1, transaction.getAmount());
            pstmt.setTimestamp(2, transaction.getTransactionDate());
            pstmt.setString(3, transaction.getStatus());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error insert transaction: " + e.getMessage());
            return false;
        }
    }

    // Mengkalkulasi pendapatan Organizer menggunakan JOIN relasional
    public double getRevenueByOrganizer(int organizerId) {
        String sql = """
            SELECT SUM(tt.price) AS total_revenue 
            FROM tickets t
            JOIN ticket_tiers tt ON t.ticket_tier_id = tt.id
            JOIN events e ON t.event_id = e.id
            WHERE e.organizer_id = ? AND t.payment_status = 'Paid'
            """;
        
        try (Connection conn = DbConnect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, organizerId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("total_revenue");
            }
        } catch (SQLException e) {
            System.err.println("Error get revenue by organizer: " + e.getMessage());
        }
        return 0.0;
    }

    // Mengkalkulasi total pendapatan seluruh platform
    public double getTotalRevenue() {
        String sql = """
            SELECT SUM(tt.price) AS grand_total 
            FROM tickets t
            JOIN ticket_tiers tt ON t.ticket_tier_id = tt.id
            WHERE t.payment_status = 'Paid'
            """;
            
        try (Connection conn = DbConnect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            if (rs.next()) {
                return rs.getDouble("grand_total");
            }
        } catch (SQLException e) {
            System.err.println("Error get total revenue: " + e.getMessage());
        }
        return 0.0;
    }
}