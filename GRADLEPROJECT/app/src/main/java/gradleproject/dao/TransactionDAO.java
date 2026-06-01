package gradleproject.dao;

import gradleproject.config.DbConnect;
import gradleproject.models.Transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransactionDAO {

    public List<Transaction> getRecentTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        
        // 🎯 MENGGUNAKAN JOIN UNTUK MENGAMBIL NAMA ACARA & PESERTA
        // Asumsi: Tabel tickets memiliki event_id dan user_id
        String sql = "SELECT t.id, e.title as event_name, u.username as participant_name, t.amount, t.transaction_date " +
                     "FROM transactions t " +
                     "JOIN tickets tk ON t.ticket_id = tk.id " +
                     "JOIN events e ON tk.event_id = e.id " +
                     "JOIN users u ON tk.user_id = u.id " +
                     "ORDER BY t.transaction_date DESC LIMIT 5";

        try (Connection conn = DbConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            while (rs.next()) {
                transactions.add(new Transaction(
                        rs.getInt("id"),
                        rs.getString("event_name"),
                        rs.getString("participant_name"),
                        rs.getDouble("amount"), // 🎯 Sesuaikan dengan nama kolom Anda
                        sdf.parse(rs.getString("transaction_date"))
                ));
            }
        } catch (Exception e) {
            System.err.println("Gagal memuat transaksi: " + e.getMessage());
        }
        return transactions;
    }

    public double getRevenueByOrganizer(int organizerId) {
        double totalRevenue = 0.0;
        
        // Melakukan relasi dari transaksi -> tiket -> acara untuk mengetahui siapa organizernya
        // Asumsi: Tabel events memiliki kolom 'organizer_id'
        String sql = "SELECT SUM(t.amount) as total_revenue " +
                     "FROM transactions t " +
                     "JOIN tickets tk ON t.ticket_id = tk.id " +
                     "JOIN events e ON tk.event_id = e.id " +
                     "WHERE e.organizer_id = ? AND t.status = 'Success'";

        try (Connection conn = DbConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, organizerId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                totalRevenue = rs.getDouble("total_revenue");
            }
        } catch (SQLException e) {
            System.err.println("Gagal menghitung pendapatan organizer: " + e.getMessage());
        }
        return totalRevenue;
    }

    // =========================================================================
    // Menghitung Total Pendapatan Keseluruhan Sistem (Platform)
    // =========================================================================
    public double getTotalRevenue() {
        double totalRevenue = 0.0;
        
        // Menjumlahkan semua transaksi yang sukses tanpa filter organizer
        String sql = "SELECT SUM(amount) as total_revenue FROM transactions WHERE status = 'Success'";

        try (Connection conn = DbConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                totalRevenue = rs.getDouble("total_revenue");
            }
        } catch (SQLException e) {
            System.err.println("Gagal menghitung total pendapatan sistem: " + e.getMessage());
        }
        return totalRevenue;
    }

    public Map<Integer, Double> getMonthlyTaxRevenue(int year) {
        Map<Integer, Double> monthlyTax = new HashMap<>();
        
        // 🎯 Menggunakan 'amount' bukan 'total_amount'
        String sql = "SELECT cast(strftime('%m', transaction_date) as integer) as month, " +
                     "SUM(amount * 0.10) as tax_income " +
                     "FROM transactions " +
                     "WHERE strftime('%Y', transaction_date) = ? AND status = 'Success' " +
                     "GROUP BY month";

        try (Connection conn = DbConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
             
            ps.setString(1, String.valueOf(year));
            ResultSet rs = ps.executeQuery();

            for (int i = 1; i <= 12; i++) {
                monthlyTax.put(i, 0.0);
            }

            while (rs.next()) {
                monthlyTax.put(rs.getInt("month"), rs.getDouble("tax_income"));
            }
        } catch (SQLException e) {
            System.err.println("Gagal memuat laporan pendapatan: " + e.getMessage());
        }
        return monthlyTax;
    }
}