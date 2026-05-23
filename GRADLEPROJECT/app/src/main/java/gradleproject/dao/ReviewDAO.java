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
}