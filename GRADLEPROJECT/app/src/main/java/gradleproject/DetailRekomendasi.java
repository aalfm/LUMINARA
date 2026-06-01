package gradleproject;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

public class DetailRekomendasi {

    private VBox view; // Menggunakan VBox agar header atas tetap mengunci diam (Sticky)

    public DetailRekomendasi(gradleproject.models.Event acara) {
        // Kontainer vertikal utama
        view = new VBox(25);
        view.setPadding(new Insets(30, 40, 30, 60)); 
        view.setAlignment(Pos.TOP_LEFT);
        view.setStyle("-fx-background-color: #F8F9FA;");

        // =====================================================================
        // 1. HEADER WELCOME (Mengunci Diam di Atas)
        // =====================================================================
        VBox welcomeHeader = new VBox(2);
        Label lblTitle = new Label("Halo, Sobat Luminara");
        lblTitle.setStyle("-fx-font-family: 'Poppins'; -fx-font-weight: bold; -fx-font-size: 28px; -fx-text-fill: #0A3B5C;");
        Label lblSubtitle = new Label("Siap menjelajahi event budaya di Kota Makassar?");
        lblSubtitle.setStyle("-fx-font-family: 'Poppins'; -fx-font-size: 14px; -fx-text-fill: #5A7184;");
        welcomeHeader.getChildren().addAll(lblTitle, lblSubtitle);

        // =====================================================================
        // 2. SEKSYEN BINGKAI UTAMA
        // =====================================================================
        VBox sectionDetail = new VBox(12); 
        sectionDetail.setMaxWidth(800);
        VBox.setVgrow(sectionDetail, Priority.ALWAYS); // Memaksa seksyen fleksibel mengikuti tinggi layar

        // Garis aksen oranye vertikal di sebelah kiri
        Label lblTabRekomendasi = new Label("Rekomendasi Kegiatan");
        lblTabRekomendasi.setStyle(
            "-fx-border-color: transparent transparent transparent #FF9800; " + 
            "-fx-border-width: 0 0 0 4; " + 
            "-fx-padding: 2 0 2 12; " + 
            "-fx-font-family: 'Poppins'; " +
            "-fx-font-weight: bold; " +
            "-fx-font-size: 16px; " +
            "-fx-text-fill: #0A3B5C;"
        );

        // Wadah Besar Biru Gelap
        VBox boxBlueContainer = new VBox(0);
        boxBlueContainer.setStyle("-fx-background-color: #0A3B5C; -fx-background-radius: 15; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.08), 10, 0, 0, 4);");
        boxBlueContainer.setPadding(new Insets(25));
        VBox.setVgrow(boxBlueContainer, Priority.ALWAYS); // Kotak biru otomatis memanjang elastis ke bawah

        // =====================================================================
        // 3. KARTU PUTIH INTI DETAIL EVENT
        // =====================================================================
        VBox whiteCard = new VBox(15);
        whiteCard.setStyle("-fx-background-color: #F8F7F4; -fx-background-radius: 15; -fx-padding: 20;");
        whiteCard.setMaxWidth(Double.MAX_VALUE);

        // A. Banner Gambar (Dinamis)
        StackPane imagePane = new StackPane();
        imagePane.setPrefHeight(200);
        ImageView ivBanner = new ImageView();
        try {
            // Ambil path gambar dari database
            String pathGambar = (acara.getImagePath() != null && !acara.getImagePath().isEmpty()) 
                                ? acara.getImagePath() : "/aset/gambarLuminara/event1.png";
            
            // Cek apakah itu file lokal atau resource
            Image img = (pathGambar.startsWith("C:") || pathGambar.startsWith("D:")) ? new Image(new java.io.File(pathGambar).toURI().toString()) 
                                                                                     : new Image(getClass().getResourceAsStream(pathGambar));
            ivBanner.setImage(img);
            ivBanner.setFitWidth(710); 
            ivBanner.setFitHeight(200);
            
            Rectangle clip = new Rectangle(710, 200);
            clip.setArcWidth(25);
            clip.setArcHeight(25);
            ivBanner.setClip(clip);
        } catch (Exception e) { /* handle error */ }
        imagePane.getChildren().add(ivBanner);

        // B. Judul Event (Dinamis)
        Label lblEventTitle = new Label(acara.getTitle());
        lblEventTitle.setStyle("-fx-font-family: 'Poppins'; -fx-font-weight: bold; -fx-font-size: 18px; -fx-text-fill: #0A3B5C;");

        // C. Deskripsi (Dinamis)
        Label lblDescription = new Label(acara.getDescription());
        lblDescription.setStyle("-fx-font-family: 'Poppins'; -fx-font-size: 11px; -fx-text-fill: #0A3B5C; -fx-line-spacing: 1.6;");
        lblDescription.setWrapText(true);
        lblDescription.setTextAlignment(javafx.scene.text.TextAlignment.JUSTIFY);

        // D. Metadata Grid (Dinamis dan Dirapikan)
        java.text.SimpleDateFormat formatTanggal = new java.text.SimpleDateFormat("dd MMM yyyy, HH:mm");
        String tanggalTampil = (acara.getEventDate() != null) ? formatTanggal.format(acara.getEventDate()) : "-";
        
        // 👉 PERBAIKAN: Deklarasi variabel hargaAsli agar bisa dipakai di UI dan Tombol
        double hargaAsli = acara.getPrice() != null ? acara.getPrice() : 0;
        String hargaTampilUI = (hargaAsli == 0) ? "Gratis" : "Rp" + String.format(java.util.Locale.forLanguageTag("id-ID"), "%,.0f", hargaAsli);

        GridPane gridInfo = new GridPane();
        gridInfo.setHgap(80); gridInfo.setVgap(12);
        gridInfo.add(createMetaBlock("Lokasi:", acara.getLocation()), 0, 0);
        gridInfo.add(createMetaBlock("Tanggal:", tanggalTampil), 1, 0);
        gridInfo.add(createMetaBlock("Harga:", hargaTampilUI), 0, 1);
        gridInfo.add(createMetaBlock("Kuota:", acara.getQuota() + " orang"), 1, 1);

        // E. Baris Tombol Aksi Kanan Bawah
        HBox actionRow = new HBox(12);
        actionRow.setAlignment(Pos.CENTER_RIGHT);
        actionRow.setPadding(new Insets(5, 0, 0, 0));

        Button btnBeli = new Button("Beli Tiket");
        btnBeli.setStyle("-fx-background-color: #FF9800; -fx-text-fill: white; -fx-font-family: 'Poppins'; -fx-font-size: 11px; -fx-font-weight: bold; -fx-background-radius: 12; -fx-padding: 6 22;");
        btnBeli.setCursor(javafx.scene.Cursor.HAND);
        
        // 👉 PERBAIKAN: Mengoper objek acara dan harga aktual ke halaman pemesanan tiket
        btnBeli.setOnAction(event -> {
        if (DashboardUser.getInstance() != null) {
            // Casting ke int akan memotong desimal (25000.0 -> 25000)
            int hargaInt = (int) (acara.getPrice() != null ? acara.getPrice() : 0);
            String hargaTiketUntukSistem = String.valueOf(hargaInt);
            
            DashboardUser.getInstance().pindahKePesanTiket(acara, hargaTiketUntukSistem);
        }
    });

        Button btnKembali = new Button("Kembali");
        btnKembali.setStyle("-fx-background-color: #FF9800; -fx-text-fill: white; -fx-font-family: 'Poppins'; -fx-font-size: 11px; -fx-font-weight: bold; -fx-background-radius: 12; -fx-padding: 6 22;");
        btnKembali.setCursor(javafx.scene.Cursor.HAND);
        
        btnKembali.setOnAction(event -> {
            if (DashboardUser.getInstance() != null) {
                DashboardUser.getInstance().pindahKeRekomendasiKegiatanPenuh();
            }
        });

        actionRow.getChildren().addAll(btnBeli, btnKembali);
        whiteCard.getChildren().addAll(imagePane, lblEventTitle, lblDescription, gridInfo, actionRow);

        ScrollPane scrollInner = new ScrollPane(whiteCard);
        scrollInner.setFitToWidth(true);
        scrollInner.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); // Sembunyikan scrollbar sistem yang tebal
        scrollInner.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollInner.setStyle("-fx-background: transparent; -fx-background-color: transparent; -fx-border-color: transparent;");
        VBox.setVgrow(scrollInner, Priority.ALWAYS);

        // Rakit komponen ke dalam struktur kontainer luar secara berurutan
        boxBlueContainer.getChildren().add(scrollInner);
        sectionDetail.getChildren().addAll(lblTabRekomendasi, boxBlueContainer); 

        view.getChildren().addAll(welcomeHeader, sectionDetail);
    }

    // --- METHOD HELPER: Membuat Blok Teks Metadata Vertikal Kunci-Nilai ---
    private VBox createMetaBlock(String title, String value) {
        VBox block = new VBox(2);
        Label lblTitle = new Label(title);
        lblTitle.setStyle("-fx-font-family: 'Poppins'; -fx-font-weight: bold; -fx-font-size: 13px; -fx-text-fill: #0A3B5C;");
        
        Label lblValue = new Label(value);
        lblValue.setStyle("-fx-font-family: 'Poppins'; -fx-font-size: 11px; -fx-text-fill: #0A3B5C;");
        
        block.getChildren().addAll(lblTitle, lblValue);
        return block;
    }

    public Parent getView() {
        return view;
    }
}