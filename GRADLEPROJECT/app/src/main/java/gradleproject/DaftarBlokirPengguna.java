package gradleproject;

import gradleproject.config.DbConnect;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DaftarBlokirPengguna {

    private VBox view;

    public DaftarBlokirPengguna() {
        view = new VBox(20);
        view.setPadding(new Insets(20, 20, 20, 80)); 
        view.setAlignment(Pos.TOP_LEFT);

        // 1. HEADER
        VBox header = new VBox(-5);
        Label lblHi = new Label("Hai, admin.");
        lblHi.getStyleClass().add("greeting-title");
        Label lblSub = new Label("Daftar pengguna yang diblokir"); 
        lblSub.getStyleClass().add("greeting-subtitle");
        header.getChildren().addAll(lblHi, lblSub);

        // 2. JUDUL HALAMAN
        HBox titleBox = new HBox(10);
        titleBox.setAlignment(Pos.CENTER_LEFT);
        
        Label lblPageTitle = new Label("Daftar Blokir Pengguna");
        lblPageTitle.getStyleClass().add("section-title");
        
        titleBox.getChildren().addAll(lblPageTitle);

        // 3. WADAH DAFTAR BARIS
        VBox listContainer = new VBox(12);
        listContainer.setMaxWidth(770);
        listContainer.setStyle("-fx-background-color: transparent;");

        // =====================================================================
        // 👉 AMBIL DATA PENGGUNA (ROLE USER) YANG DIBLOKIR DARI DATABASE
        // =====================================================================
        // Perubahan di sini: Role diubah menjadi 'USER'
        String query = "SELECT username, created_at FROM users WHERE UPPER(role) = 'USER' AND UPPER(account_status) = 'BANNED' ORDER BY id DESC";
        
        try (Connection conn = DbConnect.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
             
            SimpleDateFormat dbFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat displayFormat = new SimpleDateFormat("'Diblokir pada' dd MMMM yyyy 'pukul' HH.mm 'WITA'");
            
            boolean adaData = false;
            
            while (rs.next()) {
                adaData = true;
                String nama = rs.getString("username");
                String tglMentah = rs.getString("created_at");
                String tglTampil = "Baru saja diblokir"; 
                
                try {
                    if (tglMentah != null) {
                        Date date = dbFormat.parse(tglMentah);
                        tglTampil = displayFormat.format(date);
                    }
                } catch(Exception ignored) {}
                
                listContainer.getChildren().add(createBlokirRow(nama, tglTampil));
            }
            
            if (!adaData) {
                Label lblKosong = new Label("Tidak ada pengguna yang diblokir saat ini.");
                lblKosong.setStyle("-fx-font-family: 'Poppins'; -fx-text-fill: #7F8C8D; -fx-font-style: italic;");
                listContainer.getChildren().add(lblKosong);
            }
            
        } catch (Exception e) {
            System.out.println("⚠️ Gagal memuat daftar blokir pengguna: " + e.getMessage());
        }

        // 4. SCROLL PANE
        ScrollPane scrollTable = new ScrollPane(listContainer);
        scrollTable.setFitToWidth(true); 
        scrollTable.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED); 
        scrollTable.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); 
        scrollTable.setStyle("-fx-background: transparent; -fx-background-color: transparent; -fx-border-color: transparent;");
        
        VBox.setVgrow(scrollTable, Priority.ALWAYS);

        view.getChildren().addAll(header, titleBox, scrollTable);
    }

    private HBox createBlokirRow(String name, String blockTime) {
        HBox row = new HBox(15);
        row.setAlignment(Pos.CENTER_LEFT);
        row.getStyleClass().add("user-row-box"); 
        row.setPadding(new Insets(10, 15, 10, 15)); 

        Label icon = new Label("👤");
        icon.setStyle("-fx-font-size: 16px; -fx-text-fill: #0A3B5C;");

        Label lblName = new Label(name);
        lblName.getStyleClass().add("user-name-text");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label lblTime = new Label(blockTime);
        lblTime.getStyleClass().add("join-time-text");

        row.getChildren().addAll(icon, lblName, spacer, lblTime);
        return row;
    }

    public Parent getView() {
        return view;
    }
}