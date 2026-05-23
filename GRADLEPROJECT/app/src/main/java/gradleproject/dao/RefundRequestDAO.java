package gradleproject.dao;

import gradleproject.config.DbConnect;
import gradleproject.models.RefundRequest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RefundRequestDAO {

    // Menyimpan pengajuan refund baru dari User
    public boolean insert(RefundRequest request) {
        String sql = "INSERT INTO refund_requests (ticket_id, reason) VALUES (?, ?)";
        try (Connection conn = DbConnect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, request.getTicketId());
            pstmt.setString(2, request.getReason());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error insert refund request: " + e.getMessage());
            return false;
        }
    }

    // Menampilkan semua refund yang berstatus 'Pending' (Dibutuhkan oleh Admin)
    public List<RefundRequest> findAllPending() {
        String sql = "SELECT * FROM refund_requests WHERE status = 'Pending' ORDER BY requested_at ASC";
        List<RefundRequest> requests = new ArrayList<>();
        
        try (Connection conn = DbConnect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                RefundRequest request = new RefundRequest(
                    rs.getInt("id"),
                    rs.getInt("ticket_id"),
                    rs.getString("reason"),
                    rs.getString("status"),
                    rs.getTimestamp("requested_at"),
                    rs.getTimestamp("resolved_at")
                );
                requests.add(request);
            }
        } catch (SQLException e) {
            System.err.println("Error fetch pending refunds: " + e.getMessage());
        }
        return requests;
    }

    // Mengecek status refund berdasarkan ID Tiket (Dibutuhkan oleh User)
    public RefundRequest findByTicketId(int ticketId) {
        String sql = "SELECT * FROM refund_requests WHERE ticket_id = ?";
        try (Connection conn = DbConnect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, ticketId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return new RefundRequest(
                    rs.getInt("id"),
                    rs.getInt("ticket_id"),
                    rs.getString("reason"),
                    rs.getString("status"),
                    rs.getTimestamp("requested_at"),
                    rs.getTimestamp("resolved_at")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error find refund by ticket id: " + e.getMessage());
        }
        return null;
    }

    // Memperbarui status refund (Approved/Rejected) dan otomatis mencatat waktu penyelesaian
    public boolean updateStatus(int id, String status) {
        String sql = "UPDATE refund_requests SET status = ?, resolved_at = CURRENT_TIMESTAMP WHERE id = ?";
        try (Connection conn = DbConnect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, status);
            pstmt.setInt(2, id);
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error update refund status: " + e.getMessage());
            return false;
        }
    }
}