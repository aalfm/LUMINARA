package gradleproject;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class RiwayatKegiatanUser {

    private VBox view; // Menggunakan VBox agar header atas mengunci diam (Sticky)

    public RiwayatKegiatanUser() {
        view = new VBox(25);
        view.setPadding(new Insets(30, 40, 30, 40)); // Kalibrasi rata kiri simetris 40px
        view.setAlignment(Pos.TOP_LEFT);
        view.setStyle("-fx-background-color: #F8F9FA;");

        // 1. HEADER WELCOME (Sesuai tulisan image_01c9b9.png)
        VBox welcomeHeader = new VBox(2);
        Label lblTitle = new Label("Berikan Ulasan");
        lblTitle.setStyle("-fx-font-family: 'Poppins'; -fx-font-weight: bold; -fx-font-size: 28px; -fx-text-fill: #0A3B5C;");
        Label lblSubtitle = new Label("Bagikan pengalamanmu setelah mengikuti kegiatan ini");
        lblSubtitle.setStyle("-fx-font-family: 'Poppins'; -fx-font-size: 14px; -fx-text-fill: #5A7184;");
        welcomeHeader.getChildren().addAll(lblTitle, lblSubtitle);

        // =====================================================================
        // 2. SEKSYEN BINGKAI UTAMA (KAPSUL TAB "RIWAYAT KEGIATAN")
        // =====================================================================
        VBox sectionRiwayat = new VBox(0); 
        sectionRiwayat.setMaxWidth(800);
        VBox.setVgrow(sectionRiwayat, Priority.ALWAYS);

        // Kapsul Tab Riwayat Kegiatan di Atas Box (Sesuai mockup image_01c9b9.png)
        HBox tabRiwayat = new HBox();
        Label lblTabRiwayat = new Label("Riwayat Kegiatan");
        lblTabRiwayat.setStyle(
            "-fx-background-color: #FF9800; " + 
            "-fx-text-fill: white; " +
            "-fx-font-family: 'Poppins'; " +
            "-fx-font-weight: bold; " +
            "-fx-font-size: 13px; " +
            "-fx-background-radius: 10 10 0 0; " + 
            "-fx-padding: 6 22;"
        );
        tabRiwayat.getChildren().add(lblTabRiwayat);

        // Wadah Besar Biru Gelap Luar
        VBox boxBlueContainer = new VBox(15); 
        boxBlueContainer.setStyle("-fx-background-color: #0A3B5C; -fx-background-radius: 0 15 15 15; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.08), 10, 0, 0, 4);");
        boxBlueContainer.setPadding(new Insets(25));
        VBox.setVgrow(boxBlueContainer, Priority.ALWAYS);

        // Kontainer Penampung Baris Kartu Putih
        VBox listCardsBox = new VBox(15);
        listCardsBox.setStyle("-fx-background-color: transparent;");

        // Memasukkan data list riwayat sesuai urutan gambar mockup [Selesai, Berikan Ulasan, Tidak Hadir]
        listCardsBox.getChildren().addAll(
            createHistoryCard("Nama Kegiatan", "Selesai"),
            createHistoryCard("Nama Kegiatan", "Berikan Ulasan"),
            createHistoryCard("Nama Kegiatan", "Tidak Hadir")
        );

        // KUNCI SCROLL: Hanya menggulung daftar kartu putih bagian dalam box biru saja
        ScrollPane scrollInner = new ScrollPane(listCardsBox);
        scrollInner.setFitToWidth(true);
        scrollInner.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); 
        scrollInner.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollInner.setStyle("-fx-background: transparent; -fx-background-color: transparent; -fx-border-color: transparent;");
        VBox.setVgrow(scrollInner, Priority.ALWAYS);

        boxBlueContainer.getChildren().add(scrollInner);
        sectionRiwayat.getChildren().addAll(tabRiwayat, boxBlueContainer);

        view.getChildren().addAll(welcomeHeader, sectionRiwayat);
    }

    // --- METHOD HELPER: Membuat Baris Kapsul Putih Riwayat Kegiatan ---
    private HBox createHistoryCard(String namaKegiatan, String statusText) {
        HBox card = new HBox();
        card.setAlignment(Pos.CENTER_LEFT);
        card.setPadding(new Insets(15, 25, 15, 25));
        card.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 15; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.02), 5, 0, 0, 1);");

        // Sisi Kiri: Nama Kegiatan
        Label lblNama = new Label(namaKegiatan);
        lblNama.setStyle("-fx-font-family: 'Poppins'; -fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: #0A3B5C;");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // Sisi Kanan: Status/Aksi Dinamis
        Label lblStatus = new Label(statusText);
        lblStatus.setStyle("-fx-font-family: 'Poppins'; -fx-font-weight: bold; -fx-font-size: 13px;");
        
        // Logika pewarnaan teks status sesuai peruntukan fungsinya
        if (statusText.equalsIgnoreCase("Berikan Ulasan")) {
            lblStatus.setStyle(lblStatus.getStyle() + " -fx-text-fill: #FF9800;"); // Oranye interaktif
            card.setCursor(javafx.scene.Cursor.HAND);
            card.setOnMouseClicked(e -> {
                if (DashboardUser.getInstance() != null) {
                    // Berhasil pindah dengan membawa nama kegiatan riil (misal: "Makassar Traditional Costume Showcase")
                    DashboardUser.getInstance().pindahKeUlasan(namaKegiatan); 
                }
            });
        } else if (statusText.equalsIgnoreCase("Selesai")) {
            lblStatus.setStyle(lblStatus.getStyle() + " -fx-text-fill: #0A3B5C;"); // Biru gelap bawaan selesai
        } else {
            lblStatus.setStyle(lblStatus.getStyle() + " -fx-text-fill: #A0A9B5;"); // Abu-abu pudar untuk tidak hadir
        }

        card.getChildren().addAll(lblNama, spacer, lblStatus);
        return card;
    }

    public Parent getView() {
        return view;
    }
}