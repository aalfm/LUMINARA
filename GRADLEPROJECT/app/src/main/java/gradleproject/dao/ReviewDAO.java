package gradleproject.dao;

import gradleproject.config.DbConnect;
import gradleproject.models.Review;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReviewDAO {

    public boolean insertReview(Review review) {
        String sql = "INSERT INTO reviews (event_id, user_id, rating, review_text) VALUES (?, ?, ?, ?)";
        try (Connection conn = DbConnect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, review.getEventId());
            pstmt.setInt(2, review.getUserId());
            pstmt.setInt(3, review.getRating());
            pstmt.setString(4, review.getComment());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error insert review: " + e.getMessage());
            return false;
        }
    }

    // Tambahkan method ini di dalam ReviewDAO.java
    public List<String> getReviewsByOrganizer(int organizerId) {
        List<String> listUlasan = new ArrayList<>();
        // JOIN 3 Tabel: reviews, events, dan users untuk mendapat nama asli pengulas
        String sql = "SELECT r.review_text, u.username as reviewer_name, e.title as event_title " +
                     "FROM reviews r " +
                     "JOIN events e ON r.event_id = e.id " +
                     "JOIN users u ON r.user_id = u.id " +
                     "WHERE e.organizer_id = ? " +
                     "ORDER BY r.id DESC";
                     
        try (Connection conn = DbConnect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, organizerId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                String teks = rs.getString("review_text");
                // Ganti "u.username" dengan "u.name" di query atas jika kolom DB Anda bernama 'name'
                String reviewer = rs.getString("reviewer_name"); 
                String event = rs.getString("event_title");
                
                listUlasan.add(reviewer + " (" + event + "):\n\"" + teks + "\"");
            }
        } catch (SQLException e) {
            System.err.println("Error get reviews for organizer: " + e.getMessage());
        }
        return listUlasan;
    }

    public List<Review> getReviewsByEvent(int eventId) {
        String sql = "SELECT * FROM reviews WHERE event_id = ?";
        List<Review> reviews = new ArrayList<>();
        
        try (Connection conn = DbConnect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, eventId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Review review = new Review(
                    rs.getInt("id"),
                    rs.getInt("event_id"),
                    rs.getInt("user_id"),
                    rs.getInt("rating"),
                    rs.getString("review_text")
                );
                reviews.add(review);
            }
        } catch (SQLException e) {
            System.err.println("Error get reviews by event: " + e.getMessage());
        }
        return reviews;
    }

    public boolean hasUserReviewedTicket(int userId, int ticketId) {
        // 🎯 FIX: Query SQL sekarang mengecek berdasarkan ticket_id, bukan event_id
        String sql = "SELECT COUNT(*) FROM reviews WHERE user_id = ? AND ticket_id = ?";
        
        try (Connection conn = DbConnect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
            pstmt.setInt(1, userId);
            // 🎯 FIX: Memasukkan nilai ticketId ke parameter kedua
            pstmt.setInt(2, ticketId);
            
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0; // Mengembalikan true jika count > 0 (tiket ini sudah diulas)
            }
        } catch (SQLException e) {
            System.err.println("Error check user review status: " + e.getMessage());
        }
        return false;
    }
}