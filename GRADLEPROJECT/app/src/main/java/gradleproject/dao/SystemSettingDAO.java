package gradleproject.dao;

import gradleproject.config.DbConnect;
import gradleproject.models.SystemSetting;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SystemSettingDAO {

    public SystemSetting getSetting(String key) {
        String sql = "SELECT * FROM system_settings WHERE setting_key = ?";
        try (Connection conn = DbConnect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, key);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return new SystemSetting(
                    rs.getString("setting_key"),
                    rs.getString("setting_value"),
                    rs.getString("description")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error get system setting: " + e.getMessage());
        }
        return null;
    }

    public boolean updateSetting(String key, String value) {
        String sql = "UPDATE system_settings SET setting_value = ?, updated_at = CURRENT_TIMESTAMP WHERE setting_key = ?";
        try (Connection conn = DbConnect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, value);
            pstmt.setString(2, key);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error update system setting: " + e.getMessage());
            return false;
        }
    }
}