package gradleproject;

import gradleproject.config.DbConnect;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.sql.*;

public class ProfilPengguna {

    private ScrollPane view;
    // Label untuk data dinamis
    private Label lblNama, lblEmail, lblTelepon, btnStatus;

    public ProfilPengguna(int userId) {
        VBox content = new VBox(25);
        content.setPadding(new Insets(20, 20, 20, 80));
        content.setAlignment(Pos.TOP_LEFT);

        // Header
        VBox headerBox = new VBox(-5);
        Label lblHi = new Label("Hai, admin.");
        lblHi.getStyleClass().add("greeting-title");
        Label lblSub = new Label("Gimana hari ini . . .");
        lblSub.getStyleClass().add("greeting-subtitle");
        headerBox.getChildren().addAll(lblHi, lblSub);

        // UI Layout
        VBox orangeCard = new VBox();
        orangeCard.getStyleClass().add("card-orange-wrapper");
        orangeCard.setMaxWidth(770);

        Label lblDetailTitle = new Label("Detail Pengguna");
        lblDetailTitle.getStyleClass().add("detail-card-title");
        VBox.setMargin(lblDetailTitle, new Insets(15, 20, 15, 25));

        VBox blueCard = new VBox();
        blueCard.getStyleClass().add("card-blue-wrapper");
        
        VBox whiteCard = new VBox();
        whiteCard.getStyleClass().add("card-white-content");

        // Data Dinamis
        lblNama = new Label("-");
        lblEmail = new Label("-");
        lblTelepon = new Label("-");


        // Layout Grid
        GridPane grid = new GridPane();
        grid.setVgap(15);
        // ... (sisanya sesuaikan dengan grid yang sudah Anda buat sebelumnya) ...
        
        // Memuat data dari database
        loadDataFromDB(userId);

        whiteCard.getChildren().addAll(lblNama, lblEmail, lblTelepon, btnStatus); // Contoh penyusunan
        blueCard.getChildren().add(whiteCard);
        orangeCard.getChildren().addAll(lblDetailTitle, blueCard);
        content.getChildren().addAll(headerBox, orangeCard);

        view = new ScrollPane(content);
        view.setFitToWidth(true);
    }

    private void loadDataFromDB(int userId) {
        // 🎯 SESUAIKAN Nama kolom dengan DB Anda (username/email/phone_number/status)
        String sql = "SELECT username, email, phone_number, status FROM users WHERE id = ?";
        try (Connection conn = DbConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                lblNama.setText(rs.getString("username"));
                lblEmail.setText(rs.getString("email"));
                lblTelepon.setText(rs.getString("phone_number"));
                btnStatus.setText(rs.getString("status"));
            }
        } catch (SQLException e) {
            System.err.println("Gagal memuat profil: " + e.getMessage());
        }
    }

    public ScrollPane getView() { return view; }
}