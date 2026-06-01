package gradleproject.dao;

import gradleproject.config.DbConnect;
import gradleproject.models.Sorotan;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SorotanDAO {

    // ... (Metode getAllSorotan() yang sebelumnya tetap ada di sini) ...

    // 👉 TAMBAHAN: Method untuk menyimpan data sorotan baru dari Admin
    public boolean insertSorotan(Sorotan sorotan) {
        String sql = "INSERT INTO sorotan_budaya (judul, deskripsi_singkat, deskripsi_detail, image_path) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DbConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, sorotan.getJudul());
            ps.setString(2, sorotan.getDeskripsiSingkat());
            ps.setString(3, sorotan.getDeskripsiDetail());
            ps.setString(4, sorotan.getImagePath());
            
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
            
        } catch (Exception e) {
            System.err.println("Gagal menyimpan sorotan budaya: " + e.getMessage());
            return false;
        }
    }


    public List<Sorotan> getAllSorotan() {
        List<Sorotan> listSorotan = new ArrayList<>();
        String sql = "SELECT id, judul, deskripsi_singkat, deskripsi_detail, image_path FROM sorotan_budaya ORDER BY created_at DESC";

        try (Connection conn = DbConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                listSorotan.add(new Sorotan(
                        rs.getInt("id"),
                        rs.getString("judul"),
                        rs.getString("deskripsi_singkat"),
                        rs.getString("deskripsi_detail"),
                        rs.getString("image_path")
                ));
            }
        } catch (Exception e) {
            System.err.println("Gagal memuat sorotan budaya: " + e.getMessage());
        }
        return listSorotan;
    }
}