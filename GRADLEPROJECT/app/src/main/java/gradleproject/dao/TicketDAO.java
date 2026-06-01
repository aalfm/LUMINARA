package gradleproject.dao;

import gradleproject.config.DbConnect;
import gradleproject.models.Ticket;
import gradleproject.models.TicketTier;

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
         PreparedStatement ps = conn.prepareStatement(sql)) {
        
        ps.setInt(1, ticket.getEventId());
        ps.setInt(2, ticket.getUserId());
        ps.setInt(3, ticket.getTicketTierId());
        ps.setString(4, ticket.getPaymentStatus());
        
        return ps.executeUpdate() > 0;
    } catch (SQLException e) {
        e.printStackTrace(); // 🎯 Penting agar error tampil di konsol
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
            System.err.println("Error update ticket: " + e.getMessage());
            return false;
        }
    }

    // --- FIX: Metode ini sekarang memiliki penutup kurung yang benar ---
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
                    rs.getInt("ticket_tier_id"),
                    "TBA",
                    rs.getString("payment_status")
                );
                tickets.add(ticket);
            }
        } catch (SQLException e) {
            System.err.println("Error fetch tickets: " + e.getMessage());
        }
        return tickets;
    }

    // --- FIX: Metode ini juga sudah memiliki isi query yang benar ---
    public List<Ticket> getTicketsByEventId(int eventId) {
        List<Ticket> tickets = new ArrayList<>();
        // Cek method getTicketsByEventId di TicketDAO.java Anda, dan pastikan SQL-nya seperti ini:
       String sql = "SELECT t.*, u.username as user_name, u.email as user_email, u.phone_number as user_phone " +
             "FROM tickets t JOIN users u ON t.user_id = u.id WHERE t.event_id = ?";
        
        try (Connection conn = DbConnect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, eventId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
            Ticket t = new Ticket(
                rs.getInt("id"),
                rs.getInt("event_id"),
                rs.getInt("user_id"),
                rs.getInt("ticket_tier_id"),
                "TBA",
                rs.getString("payment_status")
            );
            t.setUserName(rs.getString("user_name"));
            t.setUserEmail(rs.getString("user_email"));
            t.setUserPhone(rs.getString("user_phone"));
            
            // 🎯 TAMBAHKAN BARIS INI: Agar aplikasi ingat status kehadirannya
            t.setIsAttended(rs.getInt("is_attended")); 
            
            tickets.add(t);

            }
        } catch (SQLException e) {
            System.err.println("Error fetch tickets with join: " + e.getMessage());
        }
        return tickets;
    }

    public int countTicketsByEventId(int eventId) {
    String sql = "SELECT COUNT(*) FROM tickets WHERE event_id = ?";
    try (Connection conn = DbConnect.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
         
        pstmt.setInt(1, eventId);
        ResultSet rs = pstmt.executeQuery();
        
        if (rs.next()) {
            return rs.getInt(1);
        }
    } catch (SQLException e) {
        System.err.println("Error menghitung tiket per event: " + e.getMessage());
    }
    return 0;
}

    // Tambahkan ini di TicketDAO.java
    public int countTicketsByOrganizer(int organizerId) {
        String sql = "SELECT COUNT(*) FROM tickets t JOIN events e ON t.event_id = e.id WHERE e.organizer_id = ?";
        try (Connection conn = DbConnect.getConnection(); 
            PreparedStatement psmt = conn.prepareStatement(sql)){
            psmt.setInt(1, organizerId);
            ResultSet rs = psmt.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) { e.printStackTrace(); }
        return 0;
    }

    public double getTotalRevenueByOrganizer(int organizerId) {
    String sql = "SELECT SUM(tt.price) " +
                 "FROM tickets t " +
                 "JOIN ticket_tiers tt ON t.ticket_tier_id = tt.id " +
                 "JOIN events e ON tt.event_id = e.id " +
                 "WHERE e.organizer_id = ? AND t.payment_status = 'Paid'";

    try (Connection conn = DbConnect.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        
        ps.setInt(1, organizerId);
        ResultSet rs = ps.executeQuery();
        
        if (rs.next()) {
            return rs.getDouble(1); // Mengambil hasil SUM
        }
    } catch (SQLException e) {
        System.err.println("Error menghitung pendapatan: " + e.getMessage());
        e.printStackTrace();
    }
    return 0.0;
}

public int countUniqueParticipantsByOrganizer(int organizerId) {
    // COUNT(DISTINCT user_id) akan memastikan user yang membeli tiket berkali-kali 
    // hanya dihitung sebagai 1 peserta unik.
    String sql = "SELECT COUNT(DISTINCT t.user_id) " +
                 "FROM tickets t " +
                 "JOIN events e ON t.event_id = e.id " +
                 "WHERE e.organizer_id = ?";
    
    try (Connection conn = DbConnect.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        
        ps.setInt(1, organizerId);
        ResultSet rs = ps.executeQuery();
        
        if (rs.next()) {
            return rs.getInt(1); // Mengambil hasil count
        }
    } catch (SQLException e) {
        System.err.println("Error menghitung peserta unik: " + e.getMessage());
        e.printStackTrace();
    }
    return 0;
}

// 1. Method untuk mengubah status kehadiran (Dipanggil oleh Organizer)
    public boolean updateKehadiran(int ticketId, int isAttended) {
        String sql = "UPDATE tickets SET is_attended = ? WHERE id = ?";
        try (Connection conn = DbConnect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, isAttended);
            pstmt.setInt(2, ticketId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error update kehadiran: " + e.getMessage());
            return false;
        }
    }

    public List<TicketTier> getTiersByEventId(int eventId) {
    List<TicketTier> tiers = new ArrayList<>();
    String sql = "SELECT * FROM ticket_tiers WHERE event_id = ?";
    try (Connection conn = DbConnect.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, eventId);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            tiers.add(new TicketTier(
                rs.getInt("id"),
                rs.getInt("event_id"),
                rs.getString("tier_name"),
                rs.getDouble("price"),
                rs.getInt("stock")
            ));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return tiers;
}

    // 2. Method untuk mengambil riwayat kegiatan User (Dipanggil oleh User)
    public List<Ticket> getRiwayatKegiatanUser(int userId) {
        List<Ticket> riwayat = new ArrayList<>();
        // Mengambil tiket yang sudah 'Paid' DAN sudah dihadiri (is_attended = 1)
        String sql = "SELECT t.*, e.title as event_title " +
                     "FROM tickets t JOIN events e ON t.event_id = e.id " +
                     "WHERE t.user_id = ? AND t.is_attended = 1 AND t.payment_status = 'Paid'";
        
        try (Connection conn = DbConnect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Ticket t = new Ticket(
                    rs.getInt("id"), rs.getInt("event_id"), rs.getInt("user_id"),
                    rs.getInt("ticket_tier_id"), "TBA", rs.getString("payment_status")
                );
                // Kita pinjam method setUserName sementara untuk menyimpan event_title 
                // (Atau Anda bisa membuat field 'eventTitle' baru di model Ticket.java)
                t.setUserName(rs.getString("event_title")); 
                riwayat.add(t);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return riwayat;
    }
}