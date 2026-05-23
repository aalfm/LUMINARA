package gradleproject.services;

import gradleproject.dao.EventDAO;
import gradleproject.dao.TransactionDAO;
import gradleproject.dao.UserDAO;
import gradleproject.dao.AuditLogDAO;
import gradleproject.dao.RefundRequestDAO;
import gradleproject.dao.TicketDAO;
import gradleproject.models.AuditLog;
import gradleproject.models.RefundRequest;

import java.util.List;

public class AdminModerationService {
    private EventDAO eventDAO;
    private UserDAO userDAO;
    private TransactionDAO transactionDAO;
    private AuditLogDAO auditLogDAO;
    private RefundRequestDAO refundRequestDAO;
    private TicketDAO ticketDAO;

    public AdminModerationService() {
        this.eventDAO = new EventDAO();
        this.userDAO = new UserDAO();
        this.transactionDAO = new TransactionDAO();
        this.auditLogDAO = new AuditLogDAO();
        this.refundRequestDAO = new RefundRequestDAO();
        this.ticketDAO = new TicketDAO();
    }

    // 1. Event Moderation + Audit Log
    public boolean approveEvent(int adminId, int eventId) {
        boolean success = eventDAO.updateStatus(eventId, "Active");
        if (success) {
            // Mencatat aktivitas ke tabel audit_logs
            auditLogDAO.insert(new AuditLog(0, adminId, "APPROVE_EVENT", "Admin menyetujui Event ID: " + eventId, null));
            System.out.println("Event ID " + eventId + " telah di-Approve.");
        }
        return success;
    }

    public boolean rejectEvent(int adminId, int eventId) {
        boolean success = eventDAO.updateStatus(eventId, "Draft");
        if (success) {
            // Mencatat aktivitas ke tabel audit_logs
            auditLogDAO.insert(new AuditLog(0, adminId, "REJECT_EVENT", "Admin menolak Event ID: " + eventId, null));
            System.out.println("Event ID " + eventId + " telah di-Reject.");
        }
        return success;
    }

    // 2. User Management + Audit Log
    public boolean changeUserRole(int adminId, int userId, String newRole) {
        boolean success = userDAO.updateRole(userId, newRole);
        if (success) {
            auditLogDAO.insert(new AuditLog(0, adminId, "CHANGE_ROLE", "Mengubah role User ID " + userId + " menjadi " + newRole, null));
        }
        return success;
    }

    public boolean banUserAccount(int adminId, int userId) {
        // Menggunakan metode update status langsung melalui eksekusi internal atau via dao jika ada
        // Mengikuti logic awal Anda yang melakukan update langsung di service:
        String sql = "UPDATE users SET account_status = 'Banned' WHERE id = ?";
        try (java.sql.Connection conn = gradleproject.config.DbConnect.getConnection();
             java.sql.PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            boolean success = pstmt.executeUpdate() > 0;
            if (success) {
                // Catat log ban
                auditLogDAO.insert(new AuditLog(0, adminId, "BAN_USER", "Mem-banned User ID: " + userId, null));
                System.out.println("User ID " + userId + " berhasil di-banned.");
            }
            return success;
        } catch (java.sql.SQLException e) {
            System.err.println("Gagal membanned user: " + e.getMessage());
            return false;
        }
    }

    // 3. Refund Moderation (Fitur Baru)
    public List<RefundRequest> getPendingRefunds() {
        return refundRequestDAO.findAllPending();
    }

    public boolean resolveRefundRequest(int adminId, int refundRequestId, String status) {
        // Ambil data refund terlebih dahulu untuk mengetahui ID tiketnya
        // Karena findByTicketId mencari lewat ticket, kita buat pencarian ideal atau langsung update status
        boolean success = refundRequestDAO.updateStatus(refundRequestId, status);
        
        if (success) {
            auditLogDAO.insert(new AuditLog(0, adminId, "RESOLVE_REFUND", "Memproses Refund ID " + refundRequestId + " dengan status: " + status, null));
            System.out.println("Refund ID " + refundRequestId + " berhasil di-" + status);
        }
        return success;
    }
    
    // Metode alternatif spesifik jika Refund disetujui (Mengubah status tiket menjadi 'Cancelled')
    public boolean approveRefund(int adminId, int refundRequestId, int ticketId) {
        // 1. Update status di tabel refund_requests menjadi 'Approved'
        boolean refundUpdated = refundRequestDAO.updateStatus(refundRequestId, "Approved");
        
        // 2. Ubah status pembayaran tiket menjadi 'Cancelled' agar kuota kembali/tiket hangus
        boolean ticketUpdated = ticketDAO.updatePaymentStatus(ticketId, "Cancelled");
        
        if (refundUpdated && ticketUpdated) {
            auditLogDAO.insert(new AuditLog(0, adminId, "APPROVE_REFUND", "Menyetujui pengembalian dana untuk Tiket ID: " + ticketId, null));
            return true;
        }
        return false;
    }

    // 4. System Analytics
    public void printSystemDashboard() {
        double systemRevenue = transactionDAO.getTotalRevenue();
        System.out.println("=== Dashboard Admin ===");
        System.out.println("Total Pendapatan Platform: Rp " + systemRevenue);
    }
}