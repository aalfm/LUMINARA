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

    public boolean insert(Event event) {
        // Menggunakan default value untuk beberapa kolom NOT NULL yang belum ada di model (seperti image_url, location)
        String sql = "INSERT INTO events (organizer_id, title, detail_description, category, ticket_type, status, event_date, image_url, preview_text, location) VALUES (?, ?, ?, ?, ?, ?, ?, 'default.png', 'Preview', 'Online')";
        try (Connection conn = DbConnect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, event.getOrganizerId());
            pstmt.setString(2, event.getTitle());
            pstmt.setString(3, event.getDescription());
            pstmt.setString(4, event.getCategory());
            pstmt.setString(5, event.getTicketType());
            pstmt.setString(6, event.getStatus());
            pstmt.setTimestamp(7, event.getEventDate());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error insert event: " + e.getMessage());
            return false;
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
                return new Event(
                    rs.getInt("id"),
                    rs.getInt("organizer_id"),
                    rs.getString("title"),
                    rs.getString("detail_description"),
                    rs.getString("category"),
                    rs.getString("ticket_type"),
                    rs.getString("status"),
                    0, // dummy kuota
                    rs.getTimestamp("event_date")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error find event by id: " + e.getMessage());
        }
        return null; // Kembalikan null jika acara tidak ditemukan
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
                    0, // Quota tidak ada langsung di events, ini dummy untuk model
                    rs.getTimestamp("event_date")
                );
                events.add(event);
            }
        } catch (SQLException e) {
            System.err.println("Error get events: " + e.getMessage());
        }
        return events;
    }
}