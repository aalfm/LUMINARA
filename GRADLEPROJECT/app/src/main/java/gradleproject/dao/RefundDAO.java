package gradleproject.dao;

import gradleproject.config.DbConnect;
import gradleproject.models.RefundRequest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RefundDAO {

    // Menghitung jumlah permintaan refund yang berstatus "Pending"
    public int getPendingRefundCount() {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM refund_requests WHERE status = 'Pending'";

        try (Connection conn = DbConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (Exception e) {
            System.err.println("Gagal menghitung refund: " + e.getMessage());
        }
        return count;
    }

    public boolean updateStatus(int id, String status) {
        String sql = "UPDATE refunds SET status = ? WHERE id = ?";
        try (Connection conn = DbConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public List<RefundRequest> getPendingRefunds() {
    List<RefundRequest> list = new ArrayList<>();
    // JOIN dari transaksi -> tiket -> event -> user
    String sql = """
        SELECT t.id, e.title as event_name, u.username as user_name, t.amount
        FROM transactions t
        JOIN tickets ti ON t.ticket_id = ti.id
        JOIN events e ON ti.event_id = e.id
        JOIN users u ON ti.user_id = u.id
        WHERE t.status = 'Pending'
    """;

    try (Connection conn = DbConnect.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
            RefundRequest r = new RefundRequest();
            r.setId(rs.getInt("id"));
            r.setEventName(rs.getString("event_name"));
            r.setUserName(rs.getString("user_name"));
            r.setTotal(rs.getDouble("amount"));
            list.add(r);
        }
    } catch (SQLException e) { e.printStackTrace(); }
    return list;
}
}