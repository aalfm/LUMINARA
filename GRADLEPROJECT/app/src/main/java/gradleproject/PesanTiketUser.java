package gradleproject;

import java.util.List;

import gradleproject.dao.TicketTierDAO;
import gradleproject.models.Event;
import gradleproject.models.TicketTier;
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

public class PesanTiketUser {

    // 👉 PERBAIKAN 1: Root container diubah menjadi StackPane untuk menumpuk lapisan form dan notifikasi
    private StackPane view; 
    private TextField txtNama, txtKontak, txtEmail;
    private Button btnBayar;

    // Elemen Komponen Kustom Jendela Notifikasi
    private VBox overlayNotif;
    private Label lblNotifTitle;
    private Label lblNotifMessage;


    // 👉 TAMBAHAN 2: Minta objek acara dan harga tiket saat halaman ini dipanggil
    public PesanTiketUser(Event acara, String hargaTiket) {
        
        view = new StackPane();
        view.setStyle("-fx-background-color: #F8F9FA;");

        // Kontainer Vertikal Konten Dasar Utama Form
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

        // =====================================================================
        // 2. SEKSYEN BINGKAI UTAMA FORMULIR (KAPSUL TAB MENEMPEL)
        // =====================================================================
        VBox sectionForm = new VBox(0); 
        sectionForm.setMaxWidth(800);
        VBox.setVgrow(sectionForm, Priority.ALWAYS);

        HBox tabPesanTiket = new HBox();
        Label lblTabPesanTiket = new Label("Pesan Tiket");
        lblTabPesanTiket.setStyle(
            "-fx-background-color: #FF9800; " + 
            "-fx-text-fill: white; " +
            "-fx-font-family: 'Poppins'; " +
            "-fx-font-weight: bold; " +
            "-fx-font-size: 13px; " +
            "-fx-background-radius: 10 10 0 0; " + 
            "-fx-padding: 6 22;"
        );
        tabPesanTiket.getChildren().add(lblTabPesanTiket);

        // Wadah Besar Biru Gelap Luar
        VBox boxBlueContainer = new VBox(20); 
        boxBlueContainer.setStyle("-fx-background-color: #0A3B5C; -fx-background-radius: 0 15 15 15; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.08), 10, 0, 0, 4);");
        boxBlueContainer.setPadding(new Insets(30, 25, 25, 25));
        VBox.setVgrow(boxBlueContainer, Priority.ALWAYS);

        VBox formFieldsBox = new VBox(15);
        formFieldsBox.setStyle("-fx-background-color: transparent;");

        // A. Kartu Input 1: Nama Kamu
        txtNama = createStyledTextField("Zahwa Dwi Putri");
        VBox cardNama = createFormCard("Nama Kamu", txtNama);

        // B. Kartu Input 2: Nomor Kontak Kamu
        txtKontak = createStyledTextField("081234567891");
        VBox cardKontak = createFormCard("Nomor Kontak Kamu", txtKontak);

        // C. Kartu Input 3: Email Kamu
        txtEmail = createStyledTextField("hyn@gmail.com");
        VBox cardEmail = createFormCard("Email Kamu", txtEmail);

        formFieldsBox.getChildren().addAll(cardNama, cardKontak, cardEmail);

        // Baris Tombol Aksi Kanan Bawah
        HBox actionRow = new HBox();
        actionRow.setAlignment(Pos.CENTER_RIGHT);
        actionRow.setPadding(new Insets(10, 0, 0, 0));

        btnBayar = new Button("Bayar");
        btnBayar.setStyle("-fx-background-color: #FF9800; -fx-text-fill: white; -fx-font-family: 'Poppins'; -fx-font-size: 11px; -fx-font-weight: bold; -fx-background-radius: 10; -fx-padding: 6 25;");
        btnBayar.setCursor(javafx.scene.Cursor.HAND);

        actionRow.getChildren().add(btnBayar);

        ScrollPane scrollInner = new ScrollPane(formFieldsBox);
        scrollInner.setFitToWidth(true);
        scrollInner.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); 
        scrollInner.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollInner.setStyle("-fx-background: transparent; -fx-background-color: transparent; -fx-border-color: transparent;");
        VBox.setVgrow(scrollInner, Priority.ALWAYS);

        boxBlueContainer.getChildren().addAll(scrollInner, actionRow);
        sectionForm.getChildren().addAll(tabPesanTiket, boxBlueContainer);
        contentBox.getChildren().addAll(welcomeHeader, sectionForm);

        // Masukkan Lapisan Konten Utama ke StackPane dasar
        view.getChildren().add(contentBox);

        // =====================================================================
        // 3. MERAKIT BOX OVERLAY NOTIFIKASI KUSTOM TERKUNCI (SAMA DENGAN BAYAR)
        // =====================================================================
        createCustomNotificationOverlay();

        // =====================================================================
        // LOGIKA VALIDASI INTERAKTIF TOMBOL BAYAR
        // =====================================================================
        btnBayar.setOnAction(event -> {

    String nama = txtNama.getText().trim();
    String kontak = txtKontak.getText().trim();
    String email = txtEmail.getText().trim();

    if (nama.isEmpty() || kontak.isEmpty() || email.isEmpty()) {
        lblNotifTitle.setText("Peringatan");
        lblNotifMessage.setText("Semua kolom harus diisi!");
        overlayNotif.setVisible(true);
        return;
    }

    try {
        TicketTierDAO tierDAO = new TicketTierDAO();
        List<TicketTier> daftarTier = tierDAO.findByEventId(acara.getId());

        TicketTier tier;

        // =========================
        // JIKA TIDAK ADA TIER
        // =========================
        if (daftarTier.isEmpty()) {
            TicketTier newTier = new TicketTier(
                    acara.getId(),
                    acara.getTicketType(),
                    acara.getPrice(),
                    acara.getQuota()
            );

            boolean sukses = tierDAO.insert(newTier);

            if (!sukses) {
                showNotif("Error", "Gagal membuat ticket tier");
                return;
            }

            // karena insert() tidak return ID → kita ambil lagi dari DB
            daftarTier = tierDAO.findByEventId(acara.getId());

            if (daftarTier.isEmpty()) {
                showNotif("Error", "Ticket tier tidak terbaca setelah insert");
                return;
            }

            tier = daftarTier.get(0);

        } else {
            tier = daftarTier.get(0);
        }

        // =========================
        // KE PEMBAYARAN
        // =========================
        if (DashboardUser.getInstance() != null) {
            DashboardUser.getInstance().pindahKePembayaran(
                    acara,
                    String.valueOf(tier.getPrice())
            );
        }

    } catch (Exception e) {
        e.printStackTrace();
        showNotif("Error", "Terjadi kesalahan sistem");
    }
});
} 

     private void showNotif(String title, String message) {
         lblNotifTitle.setText(title);
         lblNotifMessage.setText(message);
         overlayNotif.setVisible(true);
     }

    // --- METHOD HELPER 1: Membuat Desain Cetakan Boks Dialog Peringatan Terkunci ---
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
        overlayNotif.setVisible(false); // Sembunyikan di awal program

        // Baris Atas Header Box (Judul + Tombol Silang X)
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

        // Baris Isi Tengah (Ikon Info Biru + Kalimat Pesan)
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

        // Baris Bawah (Tombol OK)
        HBox bottomRow = new HBox();
        bottomRow.setAlignment(Pos.CENTER_RIGHT);
        bottomRow.setPadding(new Insets(0, 20, 15, 20));

        Button btnOk = new Button("OK");
        btnOk.setStyle("-fx-background-color: #D3D9DE; -fx-border-color: #B1BBC6; -fx-border-radius: 4; -fx-background-radius: 4; -fx-text-fill: #333333; -fx-font-family: 'Poppins'; -fx-font-size: 12px; -fx-padding: 3 18;");
        btnOk.setCursor(javafx.scene.Cursor.HAND);
        btnOk.setOnAction(e -> overlayNotif.setVisible(false)); // Klik OK untuk menutup
        
        bottomRow.getChildren().add(btnOk);
        overlayNotif.getChildren().addAll(headerRow, bodyRow, bottomRow);

        // 👉 KUNCI POSISI: Diturunkan pas di atas boks (posisi margin bawah 45 seperti halaman Bayar)
        StackPane.setAlignment(overlayNotif, Pos.BOTTOM_LEFT);
        StackPane.setMargin(overlayNotif, new Insets(0, 0, 45, 85)); 

        // Masukkan boks notifikasi kustom ke StackPane sebagai lapisan paling atas
        view.getChildren().add(overlayNotif);
    }

    // --- METHOD HELPER 2: Membuat Kartu Putih Pembungkus Input ---
    private VBox createFormCard(String labelTitle, TextField inputField) {
        VBox card = new VBox(8);
        card.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 15; -fx-padding: 15 20 18 20;");
        card.setMaxWidth(Double.MAX_VALUE);

        Label lblField = new Label(labelTitle);
        lblField.setStyle("-fx-font-family: 'Poppins'; -fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: #0A3B5C;");

        card.getChildren().addAll(lblField, inputField);
        return card;
    }

    // --- METHOD HELPER 3: Desain Kolom Input ---
    private TextField createStyledTextField(String placeholder) {
        TextField tf = new TextField();
        tf.setPromptText(placeholder);
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