package gradleproject.dao;

import gradleproject.config.DbConnect;
import gradleproject.models.OrganizerProfile;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrganizerDAO {

    public boolean insert(OrganizerProfile organizer) {
        String sql = "INSERT INTO organizers (user_id, name, description, logo_url, approval_status) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DbConnect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, organizer.getUserId());
            pstmt.setString(2, organizer.getName());
            pstmt.setString(3, organizer.getDescription());
            pstmt.setString(4, organizer.getLogoUrl());
            pstmt.setString(5, organizer.getApprovalStatus()); 
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error insert organizer: " + e.getMessage());
            return false;
        }
    }

    public OrganizerProfile findByUserId(int userId) {
        OrganizerProfile profile = null;

        // 🎯 LANGKAH 1: Ambil data mutlak dari tabel USERS
        String sqlUser = "SELECT * FROM users WHERE id = ?";
        try (Connection conn = DbConnect.getConnection();
             PreparedStatement psUser = conn.prepareStatement(sqlUser)) {
            
            psUser.setInt(1, userId);
            ResultSet rsUser = psUser.executeQuery();
            
            if (rsUser.next()) {
                profile = new OrganizerProfile();
                profile.setUserId(rsUser.getInt("id"));
                profile.setName(rsUser.getString("username")); // Username jadi nama awal
                profile.setEmail(rsUser.getString("email"));
                profile.setPhoneNumber(rsUser.getString("phone_number"));
                profile.setRole(rsUser.getString("role"));     // 🎯 Ambil role dari tabel users
                profile.setApprovalStatus("Pending");
            }
        } catch (SQLException e) {
            System.err.println("Error mencari data user: " + e.getMessage());
        }

        if (profile == null) {
            System.err.println("PERINGATAN: User ID " + userId + " tidak ditemukan.");
            return null;
        }

        // 🎯 LANGKAH 2: Tumpuk dengan data tabel ORGANIZERS (JIKA ADA)
        String sqlOrg = "SELECT * FROM organizers WHERE user_id = ?";
        try (Connection conn = DbConnect.getConnection();
             PreparedStatement psOrg = conn.prepareStatement(sqlOrg)) {
            
            psOrg.setInt(1, userId);
            ResultSet rsOrg = psOrg.executeQuery();
            
            if (rsOrg.next()) {
                profile.setId(rsOrg.getInt("id"));
                
                String orgName = rsOrg.getString("name");
                if (orgName != null && !orgName.trim().isEmpty()) {
                    profile.setName(orgName); 
                }
                
                profile.setDescription(rsOrg.getString("description"));
                profile.setLogoUrl(rsOrg.getString("logo_url"));
                profile.setApprovalStatus(rsOrg.getString("approval_status"));
                profile.setCreatedAt(rsOrg.getTimestamp("created_at"));
            }
        } catch (SQLException e) {
            System.err.println("Error mencari tambahan data organizer: " + e.getMessage());
        }

        return profile;
    }

    public boolean updateApprovalStatus(int id, String status) {
        String sql = "UPDATE organizers SET approval_status = ? WHERE id = ?";
        try (Connection conn = DbConnect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, status);
            pstmt.setInt(2, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error update organizer status: " + e.getMessage());
            return false;
        }
    }

    public boolean updateProfile(OrganizerProfile org) {
        if (org == null) return false;

        String sqlUpdateUser = "UPDATE users SET email = ?, phone_number = ? ";
        
        boolean updatePassword = (org.getPassword() != null && !org.getPassword().equals("********") && !org.getPassword().trim().isEmpty());
        if (updatePassword) {
            sqlUpdateUser += ", password = ? ";
        }
        sqlUpdateUser += "WHERE id = ?";

        try (Connection conn = DbConnect.getConnection()) {
            conn.setAutoCommit(false); 

            try {
                try (PreparedStatement psUser = conn.prepareStatement(sqlUpdateUser)) {
                    psUser.setString(1, org.getEmail());
                    psUser.setString(2, org.getPhoneNumber());
                    
                    if (updatePassword) {
                        psUser.setString(3, org.getPassword());
                        psUser.setInt(4, org.getUserId());
                    } else {
                        psUser.setInt(3, org.getUserId());
                    }
                    psUser.executeUpdate();
                }

                boolean isOrgExist = false;
                try (PreparedStatement psCheck = conn.prepareStatement("SELECT id FROM organizers WHERE user_id = ?")) {
                    psCheck.setInt(1, org.getUserId());
                    ResultSet rsCheck = psCheck.executeQuery();
                    isOrgExist = rsCheck.next();
                }

                if (isOrgExist) {
                    try (PreparedStatement psUpdateOrg = conn.prepareStatement("UPDATE organizers SET name = ? WHERE user_id = ?")) {
                        psUpdateOrg.setString(1, org.getName());
                        psUpdateOrg.setInt(2, org.getUserId());
                        psUpdateOrg.executeUpdate();
                    }
                } else {
                    try (PreparedStatement psInsertOrg = conn.prepareStatement("INSERT INTO organizers (user_id, name, approval_status) VALUES (?, ?, 'Pending')")) {
                        psInsertOrg.setInt(1, org.getUserId());
                        psInsertOrg.setString(2, org.getName());
                        psInsertOrg.executeUpdate();
                    }
                }

                conn.commit(); 
                return true;
                
            } catch (SQLException ex) {
                conn.rollback();
                System.err.println("Gagal menyimpan profil, dibatalkan: " + ex.getMessage());
                return false;
            }
        } catch (SQLException e) {
            System.err.println("Error koneksi update profile: " + e.getMessage());
            return false;
        }
    }
}