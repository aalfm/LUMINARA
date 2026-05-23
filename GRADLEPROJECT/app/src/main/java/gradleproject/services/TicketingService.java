package gradleproject.services;

import gradleproject.config.DbConnect;
import gradleproject.dao.TicketDAO;
import gradleproject.dao.RefundRequestDAO;
import gradleproject.models.Ticket;
import gradleproject.models.RefundRequest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TicketingService {
    private TicketDAO ticketDAO;
    private RefundRequestDAO refundRequestDAO; // <-- Tambahkan DAO Refund

    public TicketingService() {
        this.ticketDAO = new TicketDAO();
        this.refundRequestDAO = new RefundRequestDAO(); // <-- Inisialisasi
    }

    public boolean bookTicket(int userId, int eventId, int ticketTierId) {
        if (!isQuotaAvailable(ticketTierId)) {
            System.out.println("Pemesanan Gagal: Kuota tiket sudah habis!");
            return false;
        }

        Ticket newTicket = new Ticket(0, eventId, userId, ticketTierId, "TBA", "Pending");
        boolean success = ticketDAO.bookTicket(newTicket);
        
        if (success) {
            System.out.println("Pemesanan Berhasil. Silakan lanjutkan ke pembayaran.");
        }
        return success;
    }

    public boolean processPayment(int ticketId) {
        boolean isPaid = ticketDAO.updatePaymentStatus(ticketId, "Paid");
        if (isPaid) {
            System.out.println("Pembayaran berhasil dikonfirmasi!");
        }
        return isPaid;
    }

    /**
     * Fitur Baru: Mengajukan Refund oleh Pengguna
     */
    public boolean requestRefund(int ticketId, String reason) {
        // Validasi: Pastikan tiket tersebut tidak sedang dalam status refund atau sudah di-refund
        if (refundRequestDAO.findByTicketId(ticketId) != null) {
            System.out.println("Pengajuan Gagal: Tiket ini sudah pernah diajukan refund sebelumnya.");
            return false;
        }

        // Buat objek pengajuan baru (Status default di DB adalah 'Pending')
        RefundRequest newRequest = new RefundRequest(0, ticketId, reason, "Pending", null, null);
        
        boolean success = refundRequestDAO.insert(newRequest);
        if (success) {
            System.out.println("Pengajuan refund berhasil dikirim. Menunggu persetujuan admin.");
        }
        return success;
    }

    private boolean isQuotaAvailable(int ticketTierId) {
        String sql = """
            SELECT 
                (SELECT quota FROM ticket_tiers WHERE id = ?) - 
                (SELECT COUNT(*) FROM tickets WHERE ticket_tier_id = ? AND payment_status != 'Cancelled') AS sisa_kuota
            """;
            
        try (Connection conn = DbConnect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, ticketTierId);
            pstmt.setInt(2, ticketTierId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt("sisa_kuota") > 0;
            }
        } catch (SQLException e) {
            System.err.println("Error cek kuota: " + e.getMessage());
        }
        return false;
    }
}