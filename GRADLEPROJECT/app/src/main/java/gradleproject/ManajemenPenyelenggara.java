package gradleproject;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;

// 👉 1. TAMBAHKAN IMPORT SQL & WAKTU DI SINI
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import gradleproject.config.DbConnect;

public class ManajemenPenyelenggara {

    private VBox view;

    public ManajemenPenyelenggara() {
        view = new VBox(25);
        view.setPadding(new Insets(20, 20, 20, 80)); 
        view.setAlignment(Pos.TOP_LEFT);

        // 1. HEADER HALAMAN
        VBox header = new VBox(-5);
        Label lblHi = new Label("Hai, admin.");
        lblHi.getStyleClass().add("greeting-title");
        Label lblSub = new Label("Pantau penyelenggara acara ya . . .");
        lblSub.getStyleClass().add("greeting-subtitle");
        header.getChildren().addAll(lblHi, lblSub);

        // =====================================================================
        // 👉 2. AMBIL DATA JUMLAH (COUNT) DARI DATABASE (Sinkron dgn Dashboard)
        // =====================================================================
        int totalPenyelenggara = getDatabaseCount("SELECT COUNT(*) \r\n" + //
                        "FROM users \r\n" + //
                        "WHERE (UPPER(role) = 'ORGANIZER' OR UPPER(role) = 'PENYELENGGARA')\r\n" + //
                        "AND (account_status IS NULL OR UPPER(account_status) != 'BANNED')");

        int akunDiblokir = getDatabaseCount("SELECT COUNT(*) \r\n" + //
                        "FROM users \r\n" + //
                        "WHERE (UPPER(role) = 'ORGANIZER' OR UPPER(role) = 'PENYELENGGARA')\r\n" + //
                        "AND UPPER(account_status) = 'BANNED'");

        // 2. MINI SUMMARY CARDS SECTION
        HBox cardsRow = new HBox(20);
        cardsRow.setAlignment(Pos.TOP_LEFT);

        // Kartu Total Penyelenggara (Angka Dinamis)
        VBox cardTotal = createMiniCard("🏢", "TOTAL PENYELENGGARA", String.valueOf(totalPenyelenggara), "box-gray", event -> {
            if (DashboardAdmin.getInstance() != null) {
                DashboardAdmin.getInstance().pindahKeDaftarPenyelenggara(); 
            }
        });
        
        // Kartu Penyelenggara Diblokir (Angka Dinamis)
        VBox cardBlokir = createMiniCard("🚫", "AKUN DIBLOKIR", String.valueOf(akunDiblokir), "box-orange", event -> {
            if (DashboardAdmin.getInstance() != null) {
                DashboardAdmin.getInstance().pindahKeDaftarBlokirPenyelenggara(); 
            }
        });
        
        cardsRow.getChildren().addAll(cardTotal, cardBlokir);

        // 3. TABLE CONTAINER (Wadah Tabel Utama)
        VBox tableBox = new VBox(0);
        tableBox.getStyleClass().add("management-table-container");
        tableBox.setMaxWidth(770);
        tableBox.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 8; -fx-border-color: #D3D9DE; -fx-border-radius: 8;");
        VBox.setVgrow(tableBox, Priority.ALWAYS); 

        HBox tableHeader = new HBox();
        tableHeader.getStyleClass().add("management-table-header");
        tableHeader.setAlignment(Pos.CENTER_LEFT);
        tableHeader.setPadding(new Insets(12, 25, 12, 25)); 
        tableHeader.setStyle("-fx-background-color: #D3D9DE; -fx-background-radius: 8 8 0 0;");

        Label colNama = new Label("Nama Organisasi");
        colNama.getStyleClass().add("table-header-text");
        colNama.setPrefWidth(300); 

        Label colTanggal = new Label("Tanggal Bergabung");
        colTanggal.getStyleClass().add("table-header-text");
        colTanggal.setPrefWidth(270); 

        Label colStatus = new Label("Status");
        colStatus.getStyleClass().add("table-header-text");
        colStatus.setPrefWidth(150); 

        tableHeader.getChildren().addAll(colNama, colTanggal, colStatus);

        // Baris Isi Data Tabel (Body)
        VBox tableBody = new VBox(15);
        tableBody.setPadding(new Insets(20, 25, 20, 25));
        tableBody.setStyle("-fx-background-color: transparent;");

        // =====================================================================
        // 👉 3. AMBIL DATA BARIS TABEL DARI DATABASE
        // =====================================================================
        String queryTabel = "SELECT username, email, account_status, created_at FROM users WHERE UPPER(role) = 'ORGANIZER' OR UPPER(role) = 'PENYELENGGARA' ORDER BY id DESC";
        
        try (Connection conn = DbConnect.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(queryTabel)) {
             
            while (rs.next()) {
                String nama = rs.getString("username");
                String email = rs.getString("email") != null ? rs.getString("email") : "-";
                String statusAsli = rs.getString("account_status");
                String tglMentah = rs.getString("created_at");
                
                // Format tanggal menjadi lebih rapi (cth: 2026, Mei 02)
                String tglRapi = tglMentah; 
                try {
                    Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(tglMentah);
                    tglRapi = new SimpleDateFormat("yyyy, MMMM dd").format(date);
                } catch(Exception ignored) {}

                // Menentukan Teks Status & Warna Titik (Dot)
                String statusTeks = "Diterima"; // Default hijau (Active)
                String warnaDot = "#4CAF50"; 
                
                if (statusAsli != null && statusAsli.equalsIgnoreCase("Banned")) {
                    statusTeks = "Diblokir";
                    warnaDot = "#FF9800"; // Orange
                } else if (statusAsli != null && statusAsli.equalsIgnoreCase("Pending")) {
                    statusTeks = "Menunggu";
                    warnaDot = "#FFC107"; // Kuning
                }

                // Tambahkan baris baru ke tabel secara dinamis
                tableBody.getChildren().add(createTableRow(nama, email, tglRapi, statusTeks, warnaDot));
            }
        } catch (Exception e) {
            System.out.println("⚠️ Gagal memuat tabel penyelenggara: " + e.getMessage());
        }

        // =====================================================================
        // 4. SCROLL PANE
        // =====================================================================
        ScrollPane scrollTable = new ScrollPane(tableBody);
        scrollTable.setFitToWidth(true); 
        scrollTable.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); 
        scrollTable.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); 
        scrollTable.setStyle("-fx-background: transparent; -fx-background-color: transparent; -fx-border-color: transparent; -fx-background-radius: 0 0 8 8;");
        VBox.setVgrow(scrollTable, Priority.ALWAYS);

        tableBox.getChildren().addAll(tableHeader, scrollTable);
        view.getChildren().addAll(header, cardsRow, tableBox);
    }

    // 👉 FUNGSI BANTUAN UNTUK MENGAMBIL JUMLAH (COUNT)
    private int getDatabaseCount(String query) {
        int count = 0;
        try (Connection conn = DbConnect.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) { count = rs.getInt(1); }
        } catch (Exception e) { System.out.println("⚠️ Error hitung DB: " + e.getMessage()); }
        return count;
    }

    // Method pembuat kartu mini
    private VBox createMiniCard(String iconText, String title, String number, String boxColorClass, javafx.event.EventHandler<javafx.event.ActionEvent> onAction) {
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
        
        if (onAction != null) {
            btnLihat.setOnAction(onAction);
        }

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

    // Method pembuat baris tabel
    private HBox createTableRow(String name, String email, String date, String status, String dotColor) {
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