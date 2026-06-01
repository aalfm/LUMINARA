package gradleproject;

import gradleproject.dao.ReviewDAO;
import gradleproject.models.Review;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class UlasanUser {

    private StackPane view; 
    private TextField txtNamaKegiatan;
    private TextArea txtAreaUlasan;
    private Button btnKirim;
    
    private VBox overlayNotif;
    private Label lblNotifTitle;
    private Label lblNotifMessage;

    private int eventId;

    // 🎯 FIX: Tangkap eventId dari halaman Riwayat
    public UlasanUser(int eventId, String namaKegiatan) {
        this.eventId = eventId; 
        
        view = new StackPane();
        view.setStyle("-fx-background-color: #F8F9FA;");

        VBox contentBox = new VBox(25);
        contentBox.setPadding(new Insets(30, 40, 30, 40)); 
        contentBox.setAlignment(Pos.TOP_LEFT);

        // 1. HEADER WELCOME
        VBox welcomeHeader = new VBox(2);
        Label lblTitle = new Label("Berikan Ulasan");
        lblTitle.setStyle("-fx-font-family: 'Poppins'; -fx-font-weight: bold; -fx-font-size: 28px; -fx-text-fill: #0A3B5C;");
        Label lblSubtitle = new Label("Bagikan pengalamanmu setelah mengikuti kegiatan ini");
        lblSubtitle.setStyle("-fx-font-family: 'Poppins'; -fx-font-size: 14px; -fx-text-fill: #5A7184;");
        welcomeHeader.getChildren().addAll(lblTitle, lblSubtitle);

        // =====================================================================
        // 2. SEKSYEN BINGKAI UTAMA ULASAN (KAPSUL TAB MENEMPEL)
        // =====================================================================
        VBox sectionUlasan = new VBox(0); 
        sectionUlasan.setMaxWidth(800);
        VBox.setVgrow(sectionUlasan, Priority.ALWAYS);

        // Kapsul Tab Ulasan Kamu Atas Box
        HBox tabUlasan = new HBox();
        Label lblTabUlasan = new Label("Ulasan Kamu");
        lblTabUlasan.setStyle(
            "-fx-background-color: #FF9800; " + 
            "-fx-text-fill: white; " +
            "-fx-font-family: 'Poppins'; " +
            "-fx-font-weight: bold; " +
            "-fx-font-size: 13px; " +
            "-fx-background-radius: 10 10 0 0; " + 
            "-fx-padding: 6 25;"
        );
        tabUlasan.getChildren().add(lblTabUlasan);

        // Wadah Besar Biru Gelap Luar
        VBox boxBlueContainer = new VBox(20); 
        boxBlueContainer.setStyle("-fx-background-color: #0A3B5C; -fx-background-radius: 0 15 15 15; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.08), 10, 0, 0, 4);");
        boxBlueContainer.setPadding(new Insets(30, 25, 25, 25));
        VBox.setVgrow(boxBlueContainer, Priority.ALWAYS);

        VBox fieldsBox = new VBox(15);
        fieldsBox.setStyle("-fx-background-color: transparent;");

        // A. Kartu 1: Nama Kegiatan (Read-Only)
        txtNamaKegiatan = new TextField(namaKegiatan);
        txtNamaKegiatan.setEditable(false);
        txtNamaKegiatan.setStyle(
            "-fx-background-color: #F8F9FA; -fx-border-color: #D3D9DE; -fx-border-radius: 10; " +
            "-fx-background-radius: 10; -fx-padding: 10; -fx-font-family: 'Poppins'; -fx-font-size: 13px; " +
            "-fx-text-fill: #0A3B5C; -fx-opacity: 0.85; -fx-cursor: default;"
        );
        VBox cardNamaKegiatan = createFormCard("Nama Kegiatan", txtNamaKegiatan);

        // B. Kartu 2: Kotak Input Tulisan Ulasan (TextArea)
        txtAreaUlasan = new TextArea();
        txtAreaUlasan.setPromptText("Berikan ulasanmu untuk kegiatan yang telah kamu ikuti");
        txtAreaUlasan.setWrapText(true);
        txtAreaUlasan.setPrefHeight(150); // Tinggi kotak ulasan yang lapang
        txtAreaUlasan.setStyle(
            "-fx-control-inner-background: #F8F9FA; " +
            "-fx-background-color: #F8F9FA; " +
            "-fx-border-color: #D3D9DE; " +
            "-fx-border-radius: 10; " +
            "-fx-background-radius: 10; " +
            "-fx-font-family: 'Poppins'; " +
            "-fx-font-size: 13px; " +
            "-fx-text-fill: #0A3B5C;"
        );
        VBox cardKetikUlasan = createFormCard("Ketik Ulasan mu", txtAreaUlasan);

        fieldsBox.getChildren().addAll(cardNamaKegiatan, cardKetikUlasan);

        // C. Baris Tombol Kirim Kanan Bawah
        HBox actionRow = new HBox();
        actionRow.setAlignment(Pos.CENTER_RIGHT);
        actionRow.setPadding(new Insets(10, 0, 0, 0));

        btnKirim = new Button("Kirim");
        btnKirim.setStyle("-fx-background-color: #FF9800; -fx-text-fill: white; -fx-font-family: 'Poppins'; -fx-font-size: 11px; -fx-font-weight: bold; -fx-background-radius: 10; -fx-padding: 6 25;");
        btnKirim.setCursor(javafx.scene.Cursor.HAND);
        actionRow.getChildren().add(btnKirim);

        // Inner ScrollPane pembungkus kolom formulir
        ScrollPane scrollInner = new ScrollPane(fieldsBox);
        scrollInner.setFitToWidth(true);
        scrollInner.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); 
        scrollInner.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollInner.setStyle("-fx-background: transparent; -fx-background-color: transparent; -fx-border-color: transparent;");
        VBox.setVgrow(scrollInner, Priority.ALWAYS);

        boxBlueContainer.getChildren().addAll(scrollInner, actionRow);
        sectionUlasan.getChildren().addAll(tabUlasan, boxBlueContainer);
        contentBox.getChildren().addAll(welcomeHeader, sectionUlasan);

        view.getChildren().add(contentBox);

        // 3. RAKIT JENDELA NOTIFIKASI OVERLAY TERKUNCI
        createCustomNotificationOverlay();

        // Logika Klik Validasi Tombol Kirim Ulasan
        // Ubah logika btnKirim.setOnAction menjadi ini:
        btnKirim.setOnAction(event -> {
            String ulasanTeks = txtAreaUlasan.getText().trim();
            if (ulasanTeks.isEmpty()) {
                lblNotifTitle.setText("Peringatan");
                lblNotifMessage.setText("Kotak teks ulasan tidak boleh kosong! Silakan berikan kesan pengalamanmu.");
                overlayNotif.setVisible(true);
            } else {
                int userId = UserSession.getInstance().getUserId();
                
                // 🎯 FIX: Memanfaatkan ReviewDAO bawaan Anda
                ReviewDAO reviewDAO = new ReviewDAO();
                
                // CATATAN: Pastikan Anda memiliki 'eventId' dari halaman sebelumnya. 
                // Jika UI Anda belum punya rating bintang, kita set default 5 dulu.
                // Format model Review: (id, event_id, user_id, rating, comment)
                Review ulasanBaru = new Review(0, eventId, userId, 5, ulasanTeks);
                
                boolean sukses = reviewDAO.insertReview(ulasanBaru);

                if (sukses) {
                    lblNotifTitle.setText("Sukses");
                    lblNotifMessage.setText("Terima kasih! Ulasan kamu berhasil dikirim.");
                } else {
                    lblNotifTitle.setText("Gagal");
                    lblNotifMessage.setText("Maaf, terjadi kesalahan saat menyimpan ulasan.");
                }
                overlayNotif.setVisible(true);
            }
        });
    }

    private void createCustomNotificationOverlay() {
        overlayNotif = new VBox(0);
        overlayNotif.setMaxSize(420, 160); 
        overlayNotif.setStyle(
            "-fx-background-color: #FFFFFF; -fx-background-radius: 8; -fx-border-color: #B1BBC6; -fx-border-radius: 8; " +
            "-fx-border-width: 1; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.15), 15, 0, 0, 5);"
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
            "-fx-font-family: 'Georgia'; -fx-font-weight: bold; -fx-font-size: 16px; -fx-text-fill: #FFFFFF; " +
            "-fx-background-color: #1976D2; -fx-background-radius: 20; -fx-min-width: 32px; -fx-max-width: 32px; -fx-min-height: 32px; -fx-max-height: 32px;"
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
            // Kalau sukses kirim, otomatis antar balik user ke list Riwayat Kegiatan sebelumnya
            if (lblNotifTitle.getText().equals("Sukses") && DashboardUser.getInstance() != null) {
                DashboardUser.getInstance().pindahKeRiwayatKegiatan();
            }
        });
        bottomRow.getChildren().add(btnOk);

        overlayNotif.getChildren().addAll(headerRow, bodyRow, bottomRow);

        // Kunci letak koordinat mengambang pas di atas boks formulir bawah
        StackPane.setAlignment(overlayNotif, Pos.BOTTOM_LEFT);
        StackPane.setMargin(overlayNotif, new Insets(0, 0, 45, 85)); 

        view.getChildren().add(overlayNotif);
    }

    private VBox createFormCard(String labelTitle, javafx.scene.Node inputNode) {
        VBox card = new VBox(8);
        card.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 15; -fx-padding: 15 20 18 20;");
        card.setMaxWidth(Double.MAX_VALUE);

        Label lblField = new Label(labelTitle);
        lblField.setStyle("-fx-font-family: 'Poppins'; -fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: #0A3B5C;");

        card.getChildren().addAll(lblField, inputNode);
        return card;
    }

    public int geteventId() {
        return eventId;
    }

    public Parent getView() {
        return view;
    }
}