package gradleproject;

import gradleproject.config.DbConnect;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Optional;
import java.sql.SQLException;

public class DetailPengguna {

    private VBox view;

    public DetailPengguna() {
        view = new VBox(25);
        view.setPadding(new Insets(20, 20, 20, 80));
        view.setAlignment(Pos.TOP_LEFT);

        // Header Halaman
        VBox headerBox = new VBox(-5);
        Label lblHi = new Label("Hai, admin.");
        lblHi.getStyleClass().add("greeting-title");
        Label lblSub = new Label("Pantau pengguna ya . . .");
        lblSub.getStyleClass().add("greeting-subtitle");
        headerBox.getChildren().addAll(lblHi, lblSub);

        Label lblDetailTitle = new Label("Detail Pengguna");
        lblDetailTitle.getStyleClass().add("section-title");

        VBox tableWrapper = new VBox(15); 
        tableWrapper.setMaxWidth(770);
        VBox.setVgrow(tableWrapper, Priority.ALWAYS);

        // Table Header
        HBox tableHeader = new HBox();
        tableHeader.getStyleClass().add("detail-header-container");
        tableHeader.setAlignment(Pos.CENTER_LEFT);
        tableHeader.setPadding(new Insets(15, 30, 15, 30));

        Label colNama = new Label("Nama");
        colNama.getStyleClass().add("detail-header-text");
        colNama.setPrefWidth(180);

        Label colEmail = new Label("Email");
        colEmail.getStyleClass().add("detail-header-text");
        colEmail.setPrefWidth(220);

        Label colTelepon = new Label("No. Telepon");
        colTelepon.getStyleClass().add("detail-header-text");
        colTelepon.setPrefWidth(180);

        Label colKet = new Label("Keterangan");
        colKet.getStyleClass().add("detail-header-text");
        colKet.setPrefWidth(100);
        colKet.setAlignment(Pos.CENTER);

        tableHeader.getChildren().addAll(colNama, colEmail, colTelepon, colKet);

        VBox tableBody = new VBox(15);
        tableBody.getStyleClass().add("detail-body-container");
        tableBody.setPadding(new Insets(25, 25, 25, 25));

        // =====================================================================
        // 👉 AMBIL DATA USER AKTIF DARI DATABASE
        // =====================================================================
        String query = "SELECT id, username, email, phone_number FROM users WHERE UPPER(role) = 'USER' AND UPPER(account_status) != 'BANNED' ORDER BY id DESC";
        
        try (Connection conn = DbConnect.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
             
            boolean adaData = false;
            while (rs.next()) {
                adaData = true;
                int id = rs.getInt("id");
                String nama = rs.getString("username");
                String email = rs.getString("email") != null ? rs.getString("email") : "-";
                String telepon = rs.getString("phone_number") != null ? rs.getString("phone_number") : "-";
                
                tableBody.getChildren().add(createDetailRow(id, nama, email, telepon));
            }

            if (!adaData) {
                Label lblKosong = new Label("Tidak ada pengguna aktif saat ini.");
                lblKosong.setStyle("-fx-font-family: 'Poppins'; -fx-text-fill: #FFFFFF; -fx-font-style: italic;");
                tableBody.getChildren().add(lblKosong);
            }

        } catch (Exception e) {
            System.out.println("⚠️ Gagal memuat detail pengguna: " + e.getMessage());
        }

        // ScrollPane
        ScrollPane scrollTable = new ScrollPane(tableBody);
        scrollTable.setFitToWidth(true);
        scrollTable.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollTable.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollTable.setStyle("-fx-background: transparent; -fx-background-color: transparent; -fx-border-color: transparent;");
        VBox.setVgrow(scrollTable, Priority.ALWAYS);

        tableWrapper.getChildren().addAll(tableHeader, scrollTable);
        view.getChildren().addAll(headerBox, lblDetailTitle, tableWrapper);
    }

    private HBox createDetailRow(int id, String name, String email, String phone) {
        HBox row = new HBox();
        row.getStyleClass().add("detail-row-card");
        row.setAlignment(Pos.CENTER_LEFT);
        row.setPadding(new Insets(15, 30, 15, 30));

        Label lblName = new Label(name);
        lblName.getStyleClass().add("detail-row-name");
        lblName.setPrefWidth(180);

        Label lblEmail = new Label(email);
        lblEmail.getStyleClass().add("detail-row-text");
        lblEmail.setPrefWidth(220);

        Label lblPhone = new Label(phone);
        lblPhone.getStyleClass().add("detail-row-text");
        lblPhone.setPrefWidth(180);

        Button btnBlokir = new Button("Blokir");
        btnBlokir.getStyleClass().add("btn-blokir");
        btnBlokir.setCursor(javafx.scene.Cursor.HAND);
        
        btnBlokir.setOnAction(e -> {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Konfirmasi Blokir");
            confirm.setContentText("Blokir pengguna '" + name + "'?");
            
            Optional<ButtonType> result = confirm.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                prosesBlokirAkun(id);
            }
        });

        row.getChildren().addAll(lblName, lblEmail, lblPhone, btnBlokir);
        return row;
    }

    private void prosesBlokirAkun(int userId) {
        String sql = "UPDATE users SET account_status = 'Banned' WHERE id = ?";
        try (Connection conn = DbConnect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.executeUpdate();
            
            // Refresh halaman
            if (DashboardAdmin.getInstance() != null) {
                DashboardAdmin.getInstance().pindahKeDetailPengguna(userId);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Parent getView() {
        return view;
    }
}