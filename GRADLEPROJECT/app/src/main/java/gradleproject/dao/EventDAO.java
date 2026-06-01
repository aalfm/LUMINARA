package gradleproject.dao;

import gradleproject.config.DbConnect;
import gradleproject.models.Event;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EventDAO {

    // 🎯 PERUBAHAN: Ubah menjadi 'void' dan tambahkan 'throws SQLException'
    // 🎯 PERUBAHAN: Ubah menjadi 'void' dan tambahkan 'throws SQLException'
    public void insert(Event event) throws SQLException {
    // SQL: 8 kolom pertama '?', kolom ke-9 '?'(image), kolom ke-10 'Preview', kolom ke-11 '?', kolom ke-12 '?'
    String sql = "INSERT INTO events (organizer_id, title, detail_description, category, ticket_type, status, event_date, price, image_url, preview_text, location, kuota) " +
             "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, 'Preview', ?, ?)";
    
    try (Connection conn = DbConnect.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        
        pstmt.setInt(1, event.getOrganizerId());
        pstmt.setString(2, event.getTitle());
        pstmt.setString(3, event.getDescription());
        pstmt.setString(4, event.getCategory());
        pstmt.setString(5, event.getTicketType());
        pstmt.setString(6, event.getStatus());
        pstmt.setTimestamp(7, event.getEventDate());
        pstmt.setDouble(8, event.getPrice());
        pstmt.setString(9, event.getImagePath());
        pstmt.setString(10, event.getLocation());
        pstmt.setInt(11, event.getQuota());
        pstmt.executeUpdate(); 
    }
}

    /**
     * Mencari acara spesifik berdasarkan ID.
     * Dibutuhkan oleh TicketingService untuk mengecek detail acara sebelum dibeli.
     */
    public Event findById(int id) {
    String sql = "SELECT * FROM events WHERE id = ?";
    try (Connection conn = DbConnect.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        
        pstmt.setInt(1, id);
        ResultSet rs = pstmt.executeQuery();
        
        if (rs.next()) {
            // 🎯 WAJIB ADA RETURN!
            return new Event(
                rs.getInt("id"),
                rs.getInt("organizer_id"),
                rs.getString("title"),
                rs.getString("detail_description"),
                rs.getString("category"),
                rs.getString("ticket_type"),
                rs.getString("status"),
                rs.getInt("kuota"),
                rs.getDouble("price"),
                rs.getTimestamp("event_date"),
                rs.getString("location"),
                rs.getString("image_url")
            );
        }
    } catch (SQLException e) {
        System.err.println("Error find event by id: " + e.getMessage());
    }
    return null; // Akan return null jika ID tidak ada
}

    /**
     * Mencari semua acara yang dibuat oleh Organizer tertentu.
     * Dibutuhkan oleh OrganizerManagementService untuk menampilkan daftar acara (My Events).
     */
    public List<Event> findByOrganizerId(int organizerId) {
        return getEventsByQuery("SELECT * FROM events WHERE organizer_id = ?", String.valueOf(organizerId));
    }
    
    public List<Event> findByCategory(String category) {
        return getEventsByQuery("SELECT * FROM events WHERE category = ?", category);
    }

    public List<Event> findByStatus(String status) {
        return getEventsByQuery("SELECT * FROM events WHERE status = ?", status);
    }

    public boolean updateStatus(int id, String status) {
        String sql = "UPDATE events SET status = ? WHERE id = ?";
        try (Connection conn = DbConnect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, status);
            pstmt.setInt(2, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error update event status: " + e.getMessage());
            return false;
        }
    }

    // Helper method untuk mengurangi duplikasi kode pada query SELECT
    private List<Event> getEventsByQuery(String sql, String parameter) {
        List<Event> events = new ArrayList<>();
        try (Connection conn = DbConnect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, parameter);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Event event = new Event(
                    rs.getInt("id"),
                    rs.getInt("organizer_id"),
                    rs.getString("title"),
                    rs.getString("detail_description"),
                    rs.getString("category"),
                    rs.getString("ticket_type"),
                    rs.getString("status"),
                    rs.getInt("kuota"),
                    rs.getDouble("price"),
                    rs.getTimestamp("event_date"), 
                    rs.getString("location"),
                    rs.getString("image_url")
                );
                events.add(event);
            }
        } catch (SQLException e) {
            System.err.println("Error get events: " + e.getMessage());
        }
        return events;
    }

    /**
     * Mencari semua acara yang ada di database.
     * Dibutuhkan oleh KategoriUser untuk menampilkan daftar event secara dinamis.
     */
    public List<Event> getAllEvents() {
        List<Event> events = new ArrayList<>();
        String sql = "SELECT * FROM events"; // Ambil semua tanpa syarat (WHERE)
        
        try (Connection conn = DbConnect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                Event event = new Event(
                    rs.getInt("id"),
                    rs.getInt("organizer_id"),
                    rs.getString("title"),
                    rs.getString("detail_description"),
                    rs.getString("category"),
                    rs.getString("ticket_type"),
                    rs.getString("status"),
                    rs.getInt("kuota"),
                    rs.getDouble("price"),
                    rs.getTimestamp("event_date"), 
                    rs.getString("location"),
                    rs.getString("image_url")
                );
                events.add(event);
            }
        } catch (SQLException e) {
            System.err.println("Error get all events: " + e.getMessage());
        }
        return events;
    }

    public List<Event> getEventsByOrganizer(int organizerId) {
    List<Event> events = new ArrayList<>();
    // Sesuaikan nama tabel dan kolom dengan skema database Anda
    String sql = "SELECT * FROM events WHERE organizer_id = ?";
    
    try (Connection conn = DbConnect.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        
        pstmt.setInt(1, organizerId);
        ResultSet rs = pstmt.executeQuery();
        
        while (rs.next()) {
            // Sesuaikan constructor dengan model Event Anda
            events.add(new Event(
                 rs.getInt("id"),
                rs.getInt("organizer_id"),
                rs.getString("title"),
                rs.getString("detail_description"),
                rs.getString("category"),
                rs.getString("ticket_type"),
                rs.getString("status"),
                rs.getInt("kuota"),
                rs.getDouble("price"),
                rs.getTimestamp("event_date"), 
                rs.getString("location"),
                rs.getString("image_url")
            ));
        }
    } catch (SQLException e) {
        System.err.println("Error getEventsByOrganizer: " + e.getMessage());
    }
    return events;
}
}