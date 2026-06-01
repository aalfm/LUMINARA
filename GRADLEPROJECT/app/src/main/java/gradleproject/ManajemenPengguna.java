package gradleproject;

import gradleproject.config.DbConnect;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ManajemenPengguna {

    private VBox view;

    private static class UserData {
        String name, email, createdAt, status;
        UserData(String name, String email, String createdAt, String status) {
            this.name = name; this.email = email; this.createdAt = createdAt; this.status = status;
        }
    }

    public ManajemenPengguna() {
        view = new VBox(25);
        view.setPadding(new Insets(20, 20, 20, 80));
        view.setAlignment(Pos.TOP_LEFT);

        // 1. Ambil data
        List<UserData> users = ambilDataPengguna();

        // =====================================================================
        // 🎯 FIX LOGIKA FILTER: Gunakan "Banned" sesuai database
        // =====================================================================
        // Hitung yang diblokir (status = 'Banned')
        long totalDiblokir = users.stream()
                .filter(u -> u.status != null && u.status.equalsIgnoreCase("Banned"))
                .count();
                
        // Hitung pengguna aktif (Total semua user DIKURANGI user yang diblokir)
        long totalPengguna = users.size() - totalDiblokir;

        // 2. HEADER
        VBox header = new VBox(-5);
        Label lblHi = new Label("Hai, admin.");
        lblHi.getStyleClass().add("greeting-title");
        Label lblSub = new Label("Pantau pengguna ya . . .");
        lblSub.getStyleClass().add("greeting-subtitle");
        header.getChildren().addAll(lblHi, lblSub);

        // 3. MINI SUMMARY CARDS
        HBox cardsRow = new HBox(20);
        cardsRow.setAlignment(Pos.TOP_LEFT);

        // 🎯 FIX TOMBOL LIHAT: Tambahkan aksi navigasi
        VBox cardTotal = createMiniCard("👤", "TOTAL PENGGUNA", String.valueOf(totalPengguna), "box-gray", event -> {
            if (DashboardAdmin.getInstance() != null) DashboardAdmin.getInstance().pindahKeDaftarPengguna();
        });
        
        VBox cardBlokir = createMiniCard("🚫", "AKUN DIBLOKIR", String.valueOf(totalDiblokir), "box-orange", event -> {
            if (DashboardAdmin.getInstance() != null) DashboardAdmin.getInstance().pindahKeDaftarBlokirPengguna();
        });

        cardsRow.getChildren().addAll(cardTotal, cardBlokir);

        // 4. TABLE
        VBox tableBox = new VBox(0);
        tableBox.setStyle("-fx-background-color: #F8F7F4; -fx-background-radius: 8; -fx-border-color: #D3D9DE; -fx-border-radius: 8;");
        VBox.setVgrow(tableBox, Priority.ALWAYS);

        // Header Tabel
        HBox tableHeader = new HBox();
        tableHeader.setPadding(new Insets(12, 25, 12, 25));
        tableHeader.setStyle("-fx-background-color: #D3D9DE; -fx-background-radius: 8 8 0 0;");
        Label h1 = new Label("Nama"); h1.setPrefWidth(300);
        Label h2 = new Label("Tanggal Bergabung"); h2.setPrefWidth(270);
        Label h3 = new Label("Status"); h3.setPrefWidth(150);
        tableHeader.getChildren().addAll(h1, h2, h3);

        VBox tableBody = new VBox(15);
        tableBody.setPadding(new Insets(20, 25, 20, 25));

        // =====================================================================
        // 🎯 FIX LOGIKA TABEL: Ubah "Blocked" menjadi "Banned"
        // =====================================================================
        for (UserData u : users) {
            String statusLabel = "Diterima"; // Default
            String dotColor = "#4CAF50";     // Hijau

            if (u.status != null && u.status.equalsIgnoreCase("Banned")) { 
                statusLabel = "Diblokir"; 
                dotColor = "#FF9800"; // Oranye
            }

            tableBody.getChildren().add(createTableRow(u.name, u.email, u.createdAt, statusLabel, dotColor));
        }

        ScrollPane scrollTable = new ScrollPane(tableBody);
        scrollTable.setFitToWidth(true);
        scrollTable.setStyle("-fx-background: transparent; -fx-background-color: transparent;");

        tableBox.getChildren().addAll(tableHeader, scrollTable);
        view.getChildren().addAll(header, cardsRow, tableBox);
    }

    // =========================================================================
    // Query ke tabel users — hanya ambil role 'User' (bukan Admin/Organizer)
    // =========================================================================
    private List<UserData> ambilDataPengguna() {
        List<UserData> list = new ArrayList<>();
        String sql = "SELECT username, email, created_at, account_status, role FROM users";

        try (Connection conn = DbConnect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                String role = rs.getString("role");

                // FILTER DI JAVA
                if (role != null && role.equalsIgnoreCase("user")) {
                    list.add(new UserData(
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("created_at"),
                        rs.getString("account_status")
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error ambil data pengguna: " + e.getMessage());
        }
        return list;
    }

    // =========================================================================
    // Helper: buat mini card summary
    // =========================================================================
    private VBox createMiniCard(String iconText, String title, String number, String boxColorClass,
                                javafx.event.EventHandler<javafx.event.ActionEvent> onAction) {

        VBox card = new VBox(0);
        card.getStyleClass().add("dashboard-card");
        card.setPadding(new Insets(0));
        card.setPrefSize(160, 95);
        card.setMinWidth(160);

        Label icon = new Label(iconText);
        icon.getStyleClass().add("card-icon");
        icon.setStyle("-fx-font-size: 13px;");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button btnLihat = new Button("Lihat");
        btnLihat.getStyleClass().add("btn-mini-lihat");
        if (onAction != null) btnLihat.setOnAction(onAction);

        HBox topRow = new HBox(icon, spacer, btnLihat);
        topRow.setAlignment(Pos.CENTER_LEFT);
        topRow.setPadding(new Insets(8, 12, 0, 12));

        Label lblTitle = new Label(title);
        lblTitle.getStyleClass().add("card-title");
        lblTitle.setStyle("-fx-font-size: 9px;");
        VBox.setMargin(lblTitle, new Insets(3, 10, 3, 12));

        Label lblNumber = new Label(number);
        lblNumber.getStyleClass().add("card-number-text");
        lblNumber.setStyle("-fx-font-size: 18px;");

        StackPane numberBox = new StackPane(lblNumber);
        numberBox.getStyleClass().addAll("card-number-box", boxColorClass);
        numberBox.setMaxWidth(Double.MAX_VALUE);
        VBox.setVgrow(numberBox, Priority.ALWAYS);

        card.getChildren().addAll(topRow, lblTitle, numberBox);
        card.setCursor(javafx.scene.Cursor.HAND);
        return card;
    }

    // =========================================================================
    // Helper: buat satu baris tabel
    // =========================================================================
    private HBox createTableRow(String name, String email, String date,
                                String status, String dotColor) {
        HBox row = new HBox();
        row.setAlignment(Pos.CENTER_LEFT);
        row.setPadding(new Insets(5, 0, 5, 0));

        VBox nameBox = new VBox(2);
        nameBox.setPrefWidth(300);
        Label lblName = new Label(name);
        lblName.getStyleClass().add("table-row-name");
        Label lblEmail = new Label(email);
        lblEmail.getStyleClass().add("table-row-email");
        nameBox.getChildren().addAll(lblName, lblEmail);

        Label lblDate = new Label(date);
        lblDate.getStyleClass().add("table-row-date");
        lblDate.setPrefWidth(270);

        HBox statusBox = new HBox(8);
        statusBox.setAlignment(Pos.CENTER_LEFT);
        statusBox.setPrefWidth(150);
        Circle dot = new Circle(4);
        dot.setStyle("-fx-fill: " + dotColor + ";");
        Label lblStatus = new Label(status);
        lblStatus.getStyleClass().add("table-row-status");
        statusBox.getChildren().addAll(dot, lblStatus);

        row.getChildren().addAll(nameBox, lblDate, statusBox);
        return row;
    }

    public Parent getView() {
        return view;
    }
}