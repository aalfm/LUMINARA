package gradleproject;

import gradleproject.models.Event;
import gradleproject.models.Ticket;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import gradleproject.config.DbConnect;
import gradleproject.dao.TicketDAO;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class PembayaranUser {

    private StackPane view; 
    private TextField txtNominalTagihan, txtInputBayar;
    private Button btnKirim;
    
    private VBox overlayNotif;
    private Label lblNotifTitle;   
    private Label lblNotifMessage;

    private String displayHarga;

    // 👉 PERBAIKAN: Konstruktor sekarang menerima objek Event acara dan String totalHarga
    public PembayaranUser(Event acara, String totalHarga) {
        view = new StackPane();
        view.setStyle("-fx-background-color: #F8F9FA;");

        boolean isGratis = totalHarga == null || totalHarga.trim().equals("0") || totalHarga.trim().equals("0.0") || totalHarga.trim().equalsIgnoreCase("Gratis");

        displayHarga = "0";

        if (!isGratis && totalHarga != null) {
            if (totalHarga.endsWith(".0")) {
                displayHarga = totalHarga.substring(0, totalHarga.length() - 2);
            } else {
                displayHarga = totalHarga;
            }
        }

        VBox contentBox = new VBox(25);
        contentBox.setPadding(new Insets(30, 40, 30, 60)); 
        contentBox.setAlignment(Pos.TOP_LEFT);

        // 1. HEADER WELCOME
        VBox welcomeHeader = new VBox(2);
        Label lblTitle = new Label("Halo, Sobat Luminara");
        lblTitle.setStyle("-fx-font-family: 'Poppins'; -fx-font-weight: bold; -fx-font-size: 28px; -fx-text-fill: #0A3B5C;");
        Label lblSubtitle = new Label("Siap menjelajahi event budaya di Kota Makassar?");
        lblSubtitle.setStyle("-fx-font-family: 'Poppins'; -fx-font-size: 14px; -fx-text-fill: #5A7184;");
        welcomeHeader.getChildren().addAll(lblTitle, lblSubtitle);

        // 2. SEKSYEN BINGKAI UTAMA PEMBAYARAN
        VBox sectionBayar = new VBox(0); 
        sectionBayar.setMaxWidth(800);
        VBox.setVgrow(sectionBayar, Priority.ALWAYS);

        HBox tabBayar = new HBox();
        Label lblTabBayar = new Label("Bayar");
        lblTabBayar.setStyle(
            "-fx-background-color: #FF9800; " + 
            "-fx-text-fill: white; " +
            "-fx-font-family: 'Poppins'; " +
            "-fx-font-weight: bold; " +
            "-fx-font-size: 13px; " +
            "-fx-background-radius: 10 10 0 0; " + 
            "-fx-padding: 6 35;"
        );
        tabBayar.getChildren().add(lblTabBayar);

        VBox boxBlueContainer = new VBox(20); 
        boxBlueContainer.setStyle("-fx-background-color: #0A3B5C; -fx-background-radius: 0 15 15 15; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.08), 10, 0, 0, 4);"); 
        boxBlueContainer.setPadding(new Insets(30, 25, 25, 25));
        VBox.setVgrow(boxBlueContainer, Priority.ALWAYS);

        VBox fieldsBox = new VBox(15);
        fieldsBox.setStyle("-fx-background-color: transparent;");

        // Card Nominal Tagihan
        txtNominalTagihan = createStyledTextField(displayHarga);
        txtNominalTagihan.setEditable(false); 
        txtNominalTagihan.setStyle(txtNominalTagihan.getStyle() + " -fx-opacity: 0.85; -fx-cursor: default;");
        VBox cardNominal = createFormCard("Nominal", txtNominalTagihan);

        // Card Input Bayar
        txtInputBayar = createStyledTextField(isGratis ? "0" : ""); 
        if (isGratis) {
            // Jika tiket gratis, kunci kolom input agar user tidak perlu mengetik
            txtInputBayar.setEditable(false);
            txtInputBayar.setStyle(txtInputBayar.getStyle() + " -fx-opacity: 0.85; -fx-cursor: default;");
        } else {
            txtInputBayar.setPromptText("Masukkan nominal pembayaran kamu");
        }
        VBox cardInputBayar = createFormCard("Input Nominal Pembayaran", txtInputBayar);

        fieldsBox.getChildren().addAll(cardNominal, cardInputBayar);

        HBox actionRow = new HBox();
        actionRow.setAlignment(Pos.CENTER_RIGHT);
        actionRow.setPadding(new Insets(10, 0, 0, 0));

        btnKirim = new Button("Kirim");
        btnKirim.setStyle("-fx-background-color: #FF9800; -fx-text-fill: white; -fx-font-family: 'Poppins'; -fx-font-size: 11px; -fx-font-weight: bold; -fx-background-radius: 10; -fx-padding: 6 25;");
        btnKirim.setCursor(javafx.scene.Cursor.HAND);
        actionRow.getChildren().add(btnKirim);

        ScrollPane scrollInner = new ScrollPane(fieldsBox);
        scrollInner.setFitToWidth(true);
        scrollInner.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); 
        scrollInner.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollInner.setStyle("-fx-background: transparent; -fx-background-color: transparent; -fx-border-color: transparent;");
        VBox.setVgrow(scrollInner, Priority.ALWAYS);

        boxBlueContainer.getChildren().addAll(scrollInner, actionRow);
        sectionBayar.getChildren().addAll(tabBayar, boxBlueContainer);
        contentBox.getChildren().addAll(welcomeHeader, sectionBayar);

        view.getChildren().add(contentBox);

        // 3. RAKIT OVERLAY NOTIFIKASI
        createCustomNotificationOverlay();
        
        // =====================================================================
        // LOGIKA INTERAKTIF TOMBOL KIRIM & SIMPAN DATABASE
        // =====================================================================
        btnKirim.setOnAction(event -> {
        String inputUser = txtInputBayar.getText().trim();
        
        // Validasi Dasar
        if (inputUser.isEmpty()) {
            System.out.println("DEBUG: Input kosong"); // Tambahkan log
            lblNotifTitle.setText("Peringatan"); 
            lblNotifMessage.setText("Kolom input nominal tidak boleh kosong!");
            overlayNotif.setVisible(true);
            return;
        }

        try {
            // Cek UserSession
            if (UserSession.getInstance() == null || UserSession.getInstance().getUserId() == 0) {
                throw new Exception("UserSession tidak aktif! Harap login ulang.");
            }

            // Siapkan Data
            Ticket tiketBaru = new Ticket();
            tiketBaru.setEventId(acara.getId()); 
            tiketBaru.setUserId(UserSession.getInstance().getUserId());
            
            int tierId = getTierIdDynamic(acara.getId());
            tiketBaru.setTicketTierId(tierId); 
            tiketBaru.setPaymentStatus("Paid"); 

            System.out.println("DEBUG: Mencoba simpan tiket untuk User: " + tiketBaru.getUserId());

            // Simpan ke database
            TicketDAO ticketDAO = new TicketDAO();
            boolean berhasil = ticketDAO.bookTicket(tiketBaru);

            if (berhasil) {
                lblNotifTitle.setText("Sukses");
                lblNotifMessage.setText("Pembayaran selesai! Tiket berhasil ditambahkan.");
                overlayNotif.setVisible(true);
            } else {
                lblNotifTitle.setText("Gagal");
                lblNotifMessage.setText("Gagal menyimpan tiket di database.");
                overlayNotif.setVisible(true);
            }
        } catch (Exception e) {
            // 🎯 INI YANG PALING PENTING: Menampilkan error asli jika ada crash
            e.printStackTrace(); 
            
            lblNotifTitle.setText("Error Sistem");
            lblNotifMessage.setText("Terjadi kesalahan: " + e.getMessage());
            overlayNotif.setVisible(true);
        }
    });
    }

    private int getTierIdDynamic(int eventId) {
    String sql = "SELECT id FROM ticket_tiers WHERE event_id = ?";

    try (Connection conn = DbConnect.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setInt(1, eventId);

        java.sql.ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            int id = rs.getInt("id");
            System.out.println("Tier ditemukan: " + id);
            return id;
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    throw new RuntimeException(
        "Tidak ada ticket tier untuk event ID = " + eventId
    );
}

    private void createCustomNotificationOverlay() {
        overlayNotif = new VBox(0);
        overlayNotif.setMaxSize(420, 160); 
        overlayNotif.setStyle(
            "-fx-background-color: #FFFFFF; " +
            "-fx-background-radius: 8; " +
            "-fx-border-color: #B1BBC6; " +
            "-fx-border-radius: 8; " +
            "-fx-border-width: 1; " +
            "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.15), 15, 0, 0, 5);"
        );
        overlayNotif.setVisible(false); 

        HBox headerRow = new HBox();
        headerRow.setAlignment(Pos.CENTER_LEFT);
        headerRow.setPadding(new Insets(8, 12, 8, 15));
        headerRow.setStyle("-fx-background-color: #F1F3F5; -fx-background-radius: 7 7 0 0; -fx-border-color: transparent transparent #D3D9DE transparent; -fx-border-width: 0 0 1 0;");

        lblNotifTitle = new Label(""); 
        lblNotifTitle.setStyle("-fx-font-family: 'Poppins'; -fx-font-size: 13px; -fx-text-fill: #333333; -fx-font-weight: bold;");
        
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label btnCloseX = new Label("✕");
        btnCloseX.setStyle("-fx-font-family: 'Poppins'; -fx-font-weight: bold; -fx-font-size: 13px; -fx-text-fill: #888888;");
        btnCloseX.setCursor(javafx.scene.Cursor.HAND);
        btnCloseX.setOnMouseClicked(e -> overlayNotif.setVisible(false)); 

        headerRow.getChildren().addAll(lblNotifTitle, spacer, btnCloseX);

        HBox bodyRow = new HBox(15);
        bodyRow.setAlignment(Pos.CENTER_LEFT);
        bodyRow.setPadding(new Insets(18, 20, 15, 20));

        Label iconInfo = new Label("i");
        iconInfo.setAlignment(Pos.CENTER);
        iconInfo.setStyle(
            "-fx-font-family: 'Georgia'; -fx-font-weight: bold; -fx-font-size: 16px; " +
            "-fx-text-fill: #FFFFFF; -fx-background-color: #1976D2; " +
            "-fx-background-radius: 20; -fx-min-width: 32px; -fx-max-width: 32px; " +
            "-fx-min-height: 32px; -fx-max-height: 32px;"
        );

        lblNotifMessage = new Label("");
        lblNotifMessage.setWrapText(true);
        lblNotifMessage.setStyle("-fx-font-family: 'Poppins'; -fx-font-size: 11px; -fx-text-fill: #333333; -fx-line-spacing: 1.4;");
        lblNotifMessage.setMaxWidth(330);

        bodyRow.getChildren().addAll(iconInfo, lblNotifMessage);

        HBox bottomRow = new HBox();
        bottomRow.setAlignment(Pos.CENTER_RIGHT);
        bottomRow.setPadding(new Insets(0, 20, 15, 20));

        Button btnOk = new Button("OK");
        btnOk.setStyle("-fx-background-color: #D3D9DE; -fx-border-color: #B1BBC6; -fx-border-radius: 4; -fx-background-radius: 4; -fx-text-fill: #333333; -fx-font-family: 'Poppins'; -fx-font-size: 12px; -fx-padding: 3 18;");
        btnOk.setCursor(javafx.scene.Cursor.HAND);
        
        btnOk.setOnAction(e -> {
            overlayNotif.setVisible(false);
            // Jalur pulang ke Beranda jika input pembayaran valid dan terisi sukses
            if (lblNotifTitle.getText().equals("Sukses") && DashboardUser.getInstance() != null) {
                DashboardUser.getInstance().pindahKeBeranda();
            }
        });
        bottomRow.getChildren().add(btnOk);

        overlayNotif.getChildren().addAll(headerRow, bodyRow, bottomRow);

        StackPane.setAlignment(overlayNotif, Pos.BOTTOM_LEFT);
        StackPane.setMargin(overlayNotif, new Insets(0, 0, 45, 85)); 

        view.getChildren().add(overlayNotif);
    }

    private VBox createFormCard(String labelTitle, TextField inputField) {
        VBox card = new VBox(8);
        card.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 15; -fx-padding: 15 20 18 20;");
        card.setMaxWidth(Double.MAX_VALUE);

        Label lblField = new Label(labelTitle);
        lblField.setStyle("-fx-font-family: 'Poppins'; -fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: #0A3B5C;");

        card.getChildren().addAll(lblField, inputField);
        return card;
    }

    private TextField createStyledTextField(String textValue) {
        TextField tf = new TextField(textValue);
        tf.setStyle(
            "-fx-background-color: #F8F9FA; " +
            "-fx-border-color: #D3D9DE; " +
            "-fx-border-radius: 10; " +
            "-fx-background-radius: 10; " +
            "-fx-padding: 10; " +
            "-fx-font-family: 'Poppins'; " +
            "-fx-font-size: 13px; " +
            "-fx-text-fill: #0A3B5C;"
        );
        return tf;
    }

    public Parent getView() {
        return view;
    }
}