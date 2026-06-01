package gradleproject;

import gradleproject.config.DbConnect;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DaftarPengguna {

    private VBox view;

    // =========================================================================
    // Inner class ringan untuk menampung data dari DB
    // =========================================================================
    private static class UserData {
        int id;
        String name, createdAt;

        UserData(int id, String name, String createdAt) {
            this.id = id;
            this.name = name;
            this.createdAt = createdAt;
        }
    }

    public DaftarPengguna(boolean dariBeranda) {

        view = new VBox(20);
        view.setPadding(new Insets(20, 20, 20, 80));
        view.setAlignment(Pos.TOP_LEFT);

        // 1. HEADER
        VBox header = new VBox(-5);
        Label lblHi = new Label("Hai, admin.");
        lblHi.getStyleClass().add("greeting-title");
        Label lblSub = new Label("Gimana hari ini . . .");
        lblSub.getStyleClass().add("greeting-subtitle");
        header.getChildren().addAll(lblHi, lblSub);

        // 2. JUDUL HALAMAN
        Label lblPageTitle = new Label("Daftar Pengguna");
        lblPageTitle.getStyleClass().add("section-title");

        // 3. WADAH DAFTAR
        VBox listContainer = new VBox(12);
        listContainer.setMaxWidth(770);
        listContainer.setStyle("-fx-background-color: transparent;");

        // 🎯 Ambil data nyata dari database
        List<UserData> users = ambilDataPengguna();

        if (users.isEmpty()) {
            Label lblKosong = new Label("Tidak ada data pengguna.");
            lblKosong.setStyle(
                "-fx-font-family: 'Poppins'; -fx-text-fill: #A0A9B5; -fx-font-style: italic;"
            );
            listContainer.getChildren().add(lblKosong);
        } else {
            for (UserData u : users) {
                String joinTime = formatJoinTime(u.createdAt);
                listContainer.getChildren().add(createUserRow(u.id, u.name, joinTime));
            }
        }

        // 4. SCROLL PANE
        ScrollPane scrollTable = new ScrollPane(listContainer);
        scrollTable.setFitToWidth(true);
        scrollTable.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollTable.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollTable.setStyle(
            "-fx-background: transparent; -fx-background-color: transparent; -fx-border-color: transparent;"
        );
        VBox.setVgrow(scrollTable, Priority.ALWAYS);

        view.getChildren().addAll(header, lblPageTitle, scrollTable);
    }

    // =========================================================================
    // Query ke tabel users — hanya role 'User', diurutkan terbaru di atas
    // =========================================================================
    private List<UserData> ambilDataPengguna() {
        List<UserData> list = new ArrayList<>();
        String sql = "SELECT id, username, created_at FROM users WHERE role = 'User' ORDER BY created_at DESC";

        try (Connection conn = DbConnect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

        while (rs.next()) {
            list.add(new UserData(
                rs.getInt("id"),
                rs.getString("username"),
                rs.getString("created_at")
            ));
        }
        } catch (SQLException e) {
            System.err.println("Error ambil daftar pengguna: " + e.getMessage());
        }
        return list;
    }

    // =========================================================================
    // Format "2026-05-31 19:41:20"  →  kalimat bergabung relatif
    //   • Hari ini  : "Bergabung hari ini pukul 19.41 WITA"
    //   • Kemarin   : "Bergabung kemarin pukul 19.41 WITA"
    //   • Lainnya   : "Bergabung pada 2026, Mei 31"
    // =========================================================================
    private String formatJoinTime(String rawDate) {
        if (rawDate == null || rawDate.isEmpty()) return "Bergabung pada -";
        try {
            LocalDateTime dt = LocalDateTime.parse(
                rawDate, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            );
            LocalDate joinDate = dt.toLocalDate();
            LocalDate today    = LocalDate.now();

            String waktu = String.format("%02d.%02d WITA", dt.getHour(), dt.getMinute());

            if (joinDate.equals(today)) {
                return "Bergabung hari ini pukul " + waktu;
            } else if (joinDate.equals(today.minusDays(1))) {
                return "Bergabung kemarin pukul " + waktu;
            } else {
                String[] bulan = {
                    "", "Januari","Februari","Maret","April","Mei","Juni",
                    "Juli","Agustus","September","Oktober","November","Desember"
                };
                return "Bergabung pada " + joinDate.getYear()
                        + ", " + bulan[joinDate.getMonthValue()]
                        + " " + joinDate.getDayOfMonth();
            }
        } catch (Exception e) {
            return "Bergabung pada " + rawDate;
        }
    }

    // =========================================================================
    // Helper: buat satu baris pengguna
    // =========================================================================
    private HBox createUserRow(int id, String name, String joinTime) {
        HBox row = new HBox(15);
        row.setAlignment(Pos.CENTER_LEFT);
        row.getStyleClass().add("user-row-box");
        row.setPadding(new Insets(10, 15, 10, 15));

        Label icon = new Label("👤");
        icon.setStyle("-fx-font-size: 16px; -fx-text-fill: #003A6C;");

        Label lblName = new Label(name);
        lblName.getStyleClass().add("user-name-text");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label lblTime = new Label(joinTime);
        lblTime.getStyleClass().add("join-time-text");

        Button btnDetail = new Button("Detail");
        btnDetail.getStyleClass().add("btn-lihat");
        btnDetail.setCursor(javafx.scene.Cursor.HAND);

        btnDetail.setOnAction(e -> {
        System.out.println("CLICK OK");

        System.out.println("INSTANCE = " + DashboardAdmin.getInstance());

        if (DashboardAdmin.getInstance() != null) {
            DashboardAdmin.getInstance().pindahKeDetailPengguna(id);
        }
    });

        row.getChildren().addAll(icon, lblName, spacer, lblTime, btnDetail);
        return row;
    }

    public Parent getView() {
        return view;
    }
}