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
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

public class RekomendasiKegiatan {

    private VBox view;

    public RekomendasiKegiatan() {
        view = new VBox(25);
        view.setPadding(new Insets(30, 40, 30, 60)); 
        view.setAlignment(Pos.TOP_LEFT);
        view.setStyle("-fx-background-color: #F8F9FA;");

        // 1. HEADER WELCOME
        VBox welcomeHeader = new VBox(2);
        Label lblTitle = new Label("Halo, Sobat Luminara");
        lblTitle.setStyle("-fx-font-family: 'Poppins'; -fx-font-weight: bold; -fx-font-size: 28px; -fx-text-fill: #0A3B5C;");
        Label lblSubtitle = new Label("Siap menjelajahi event budaya di Kota Makassar?");
        lblSubtitle.setStyle("-fx-font-family: 'Poppins'; -fx-font-size: 14px; -fx-text-fill: #5A7184;");
        welcomeHeader.getChildren().addAll(lblTitle, lblSubtitle);

        // 2. SEKSYEN UTAMA KARTU REKOMENDASI
        VBox sectionRekomendasi = new VBox(0);
        sectionRekomendasi.setMaxWidth(800);
        VBox.setVgrow(sectionRekomendasi, Priority.ALWAYS);

        HBox tabRekomendasi = new HBox();
        Label lblTabRekomendasi = new Label("Rekomendasi Kegiatan");
        lblTabRekomendasi.setStyle("-fx-background-color: #FF9800; -fx-text-fill: white; -fx-font-family: 'Poppins'; -fx-font-weight: bold; -fx-font-size: 13px; -fx-background-radius: 10 10 0 0; -fx-padding: 6 22;");
        tabRekomendasi.getChildren().add(lblTabRekomendasi);

        // Wadah Biru Gelap Frame Utama
        VBox boxBlueContainer = new VBox(0);
        boxBlueContainer.setStyle("-fx-background-color: #0A3B5C; -fx-background-radius: 0 15 15 15; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.08), 10, 0, 0, 4);");
        boxBlueContainer.setPadding(new Insets(20, 20, 20, 20));
        VBox.setVgrow(boxBlueContainer, Priority.ALWAYS);

        // 👉 KUNCI UI: Menggunakan GRID dua kolom untuk menyeimbangkan letak kartu putih kanan-kiri
        GridPane cardsGrid = new GridPane();
        cardsGrid.setHgap(20); // Jarak horizontal antar kartu
        cardsGrid.setVgap(20); // Jarak vertikal antar kartu
        cardsGrid.setStyle("-fx-background-color: transparent;");

        // Looping untuk setiap acara yang ditemukan di database
        // Perbaiki cara pemanggilan DAO dan method-nya
        gradleproject.dao.EventDAO eventDAO = new gradleproject.dao.EventDAO();
        java.util.List<gradleproject.models.Event> daftarAcara = eventDAO.findByStatus("Active"); // Tanpa 'status:'

        int kolom = 0;
        int baris = 0;

        for (gradleproject.models.Event acara : daftarAcara) {

            VBox eventCard = createEventCard(acara);

            cardsGrid.add(eventCard, kolom, baris);

            kolom++;

            if (kolom == 2) {
                kolom = 0;
                baris++;
            }
        }

        // Opsional: Jika database masih kosong, tampilkan pesan kosong yang elegan
        if (daftarAcara.isEmpty()) {
            Label lblKosong = new Label("Belum ada kegiatan yang tersedia saat ini.");
            lblKosong.setStyle("-fx-font-family: 'Poppins'; -fx-text-fill: #A0A9B5; -fx-font-style: italic;");
            cardsGrid.add(lblKosong, 0, 0);
        }

        // 👉 KUNCI SCROLL: Dibungkus ScrollPane transparan bagian dalam agar sticky header atas tetap diam
        ScrollPane scrollInner = new ScrollPane(cardsGrid);
        scrollInner.setFitToWidth(true);
        scrollInner.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollInner.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollInner.setStyle("-fx-background: transparent; -fx-background-color: transparent; -fx-border-color: transparent;");
        VBox.setVgrow(scrollInner, Priority.ALWAYS);

        boxBlueContainer.getChildren().add(scrollInner);
        sectionRekomendasi.getChildren().addAll(tabRekomendasi, boxBlueContainer);

        view.getChildren().addAll(welcomeHeader, sectionRekomendasi);
    }

    // --- METHOD HELPER: Membuat Cetakan Desain Kartu Putih Per Kegiatan Eksklusif ---
    private VBox createEventCard(gradleproject.models.Event acara) {
        VBox card = new VBox(10);
        card.setPrefWidth(360); // Lebar proporsional setengah frame dari total 800px
        card.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 15; -fx-padding: 15; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.05), 5, 0, 0, 2);");

        // A. Foto Kegiatan Atas Card
        StackPane imagePane = new StackPane();
        imagePane.setPrefSize(330, 130);
        imagePane.setStyle("-fx-background-color: #E2E8F0; -fx-background-radius: 12;");

        String imagePath = acara.getImagePath();
        String title = acara.getTitle();
        String description = acara.getDescription();

        String typeTag = acara.getTicketType().equalsIgnoreCase("Free")
                ? "Gratis"
                : "Berbayar";

        String categoryTag = acara.getCategory();

        ImageView iv = new ImageView();
        try {
            // Jika path adalah file lokal di komputer (bukan di folder resource)
            if (imagePath.startsWith("C:") || imagePath.startsWith("/")) {
                iv.setImage(new Image(new java.io.File(imagePath).toURI().toString()));
            } else {
                // Jika path adalah file di dalam folder resource
                iv.setImage(new Image(getClass().getResourceAsStream(imagePath)));
            }
            iv.setFitWidth(330);
            iv.setFitHeight(130);
            
            Rectangle clip = new Rectangle(330, 130);
            clip.setArcWidth(20);
            clip.setArcHeight(20);
            iv.setClip(clip);
        } catch (Exception e) {
            Label lblPlaceholder = new Label("🖼️ Gambar Event");
            lblPlaceholder.setStyle("-fx-text-fill: #A0A9B5; -fx-font-family: 'Poppins';");
            imagePane.getChildren().add(lblPlaceholder);
        }
        imagePane.getChildren().add(0, iv);

        // B. Judul Kegiatan
        Label lblTitle = new Label(title);
        lblTitle.setStyle("-fx-font-family: 'Poppins'; -fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: #0A3B5C;");
        lblTitle.setWrapText(true);

        // C. Deskripsi Pendek
        Label lblDesc = new Label(description);
        lblDesc.setStyle("-fx-font-family: 'Poppins'; -fx-font-size: 10px; -fx-text-fill: #5A7184; -fx-line-spacing: 1.5;");
        lblDesc.setWrapText(true);
        lblDesc.setMaxHeight(45);

        // D. Baris Info Bawah (Tag Harga Kiri & Sepasang Tombol Kanan)
        HBox bottomRow = new HBox(8);
        bottomRow.setAlignment(Pos.CENTER_LEFT);
        bottomRow.setPadding(new Insets(5, 0, 0, 0));

        // Label Kiri (Misal: Gratis \n Budaya)
        VBox labelBox = new VBox(0);
        Label lblType = new Label(typeTag);
        lblType.setStyle("-fx-font-family: 'Poppins'; -fx-font-weight: bold; -fx-font-size: 11px; -fx-text-fill: " + (typeTag.equalsIgnoreCase("Gratis") ? "#FF9800;" : "#E53935;"));
        Label lblCategory = new Label(categoryTag);
        lblCategory.setStyle("-fx-font-family: 'Poppins'; -fx-font-size: 11px; -fx-text-fill: #A0A9B5;");
        labelBox.getChildren().addAll(lblType, lblCategory);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        double hargaAsli = acara.getPrice() != null ? acara.getPrice() : 0;

        // Dua Tombol Oranye Kanan
        Button btnBeli = new Button("Beli Tiket");
        btnBeli.setStyle("-fx-background-color: #FF9800; -fx-text-fill: white; -fx-font-family: 'Poppins'; -fx-font-size: 9px; -fx-font-weight: bold; -fx-background-radius: 10; -fx-padding: 4 12;");
        btnBeli.setOnAction(event -> {
            if (DashboardUser.getInstance() != null) {
                // Casting ke int akan otomatis membuang desimal (misal 25000.0 menjadi 25000)
                // Lalu diubah ke String agar bisa dikirim ke halaman selanjutnya
                String hargaTiketUntukSistem = String.valueOf((Double) hargaAsli);
                
                DashboardUser.getInstance().pindahKePesanTiket(acara, hargaTiketUntukSistem);
            }
        });
        
        Button btnDetail = new Button("Lihat Detail");
        btnDetail.setStyle("-fx-background-color: #FF9800; -fx-text-fill: white; -fx-font-family: 'Poppins'; -fx-font-size: 9px; -fx-font-weight: bold; -fx-background-radius: 10; -fx-padding: 4 12;");
        btnDetail.setCursor(javafx.scene.Cursor.HAND);
        // 👉 TAMBAHKAN BLOK BARIS AKSI INI:
        btnDetail.setOnAction(event -> {
            if (DashboardUser.getInstance() != null) {
                DashboardUser.getInstance().pindahKeDetailRekomendasi(acara);
            }
        });
        bottomRow.getChildren().addAll(labelBox, spacer, btnBeli, btnDetail);
        card.getChildren().addAll(imagePane, lblTitle, lblDesc, bottomRow);
        return card;
    }

    public Parent getView() {
        return view;
    }
}