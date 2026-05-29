package gradleproject;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

public class BerandaUser {

    private ScrollPane view;

    public BerandaUser() {
        // Kontainer vertikal utama penampung dashboard
        VBox contentBox = new VBox(30); // Jarak antar seksyen dinaikkan ke 30px agar bernapas lega
        contentBox.setPadding(new Insets(30, 40, 30, 60)); 
        contentBox.setAlignment(Pos.TOP_LEFT);
        contentBox.setStyle("-fx-background-color: #F8F7F4;"); // Latar belakang krem terang mockup

        // =====================================================================
        // 1. HEADER WELCOME (HALO, SOBAT LUMINARA)
        // =====================================================================
        VBox welcomeHeader = new VBox(2);
        Label lblTitle = new Label("Halo, Sobat Luminara");
        lblTitle.setStyle("-fx-font-family: 'Poppins', 'Sans-Serif'; -fx-font-weight: bold; -fx-font-size: 28px; -fx-text-fill: #003A6C;");
        
        Label lblSubtitle = new Label("Siap menjelajahi event budaya di Kota Makassar?");
        lblSubtitle.setStyle("-fx-font-family: 'Poppins', 'Sans-Serif'; -fx-font-size: 14px; -fx-text-fill: #5A7184;");
        welcomeHeader.getChildren().addAll(lblTitle, lblSubtitle);

        // =====================================================================
        // 2. SEKSYEN KARTU: TIKET KAMU (UPDATE ACCENT BAR)
        // =====================================================================
        VBox sectionTiket = new VBox(12); // Beri jarak 12px antara judul dan kotak konten
        sectionTiket.setMaxWidth(800);

        // 👉 PERBAIKAN: Mengubah bentuk tab menjadi garis oranye vertikal memanjang ke bawah
        Label lblTabTiket = new Label("Tiket Kamu");
        lblTabTiket.setStyle(
            "-fx-border-color: transparent transparent transparent #FF9800; " + // Garis oranye di sebelah kiri
            "-fx-border-width: 0 0 0 4; " + // Ketebalan garis 4px
            "-fx-padding: 2 0 2 12; " + // Jarak teks dari garis kiri
            "-fx-font-family: 'Poppins'; " +
            "-fx-font-weight: bold; " +
            "-fx-font-size: 16px; " +
            "-fx-text-fill: #003A6C;"
        );
 
        lblTabTiket.setCursor(javafx.scene.Cursor.HAND);
        lblTabTiket.setOnMouseClicked(event -> {
            if (DashboardUser.getInstance() != null) {
                DashboardUser.getInstance().pindahKeTiketSaya();
            }
        });

        VBox boxTiketContainer = new VBox(12);
        // 👉 PERBAIKAN: Sudut background radius diatur melengkung 15px rata di semua sisi (lebih rapi)
        boxTiketContainer.setStyle("-fx-background-color: #003A6C; -fx-background-radius: 15; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.08), 10, 0, 0, 4);"); 
        boxTiketContainer.setPadding(new Insets(20, 25, 20, 25));

        boxTiketContainer.getChildren().addAll(
            createTicketRow("Nama Kegiatan", "Tanggal / Waktu"),
            createTicketRow("Nama Kegiatan", "Tanggal / Waktu")
        );
        sectionTiket.getChildren().addAll(lblTabTiket, boxTiketContainer);

        // =====================================================================
        // 3. SEKSYEN KARTU: REKOMENDASI KEGIATAN (UPDATE ACCENT BAR)
        // =====================================================================
        VBox sectionRekomendasi = new VBox(12);
        sectionRekomendasi.setMaxWidth(800);

        // 👉 PERBAIKAN: Garis aksen oranye vertikal memanjang ke bawah
        Label lblTabRekomendasi = new Label("Rekomendasi Kegiatan");
        lblTabRekomendasi.setStyle(
            "-fx-border-color: transparent transparent transparent #FF9800; " +
            "-fx-border-width: 0 0 0 4; " +
            "-fx-padding: 2 0 2 12; " +
            "-fx-font-family: 'Poppins'; " +
            "-fx-font-weight: bold; " +
            "-fx-font-size: 16px; " +
            "-fx-text-fill: #003A6C;"
        );

        lblTabRekomendasi.setCursor(javafx.scene.Cursor.HAND);
        lblTabRekomendasi.setOnMouseClicked(event -> {
            if (DashboardUser.getInstance() != null) {
                DashboardUser.getInstance().pindahKeRekomendasiKegiatanPenuh();
            }
        });

        StackPane bannerRekomendasi = new StackPane();
        bannerRekomendasi.setPrefHeight(160);
        bannerRekomendasi.setMaxWidth(800);
        // 👉 PERBAIKAN: Sudut radius diatur melengkung 15px rata di semua sisi
        bannerRekomendasi.setStyle("-fx-background-color: #F8F7F4; -fx-background-radius: 15; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.08), 10, 0, 0, 4);"); 
        
        ImageView imgRekomendasi = new ImageView();
        try {
            Image img = new Image(getClass().getResourceAsStream("/aset/gambarLuminara/rekomendasi-kegiatan.png"));
            imgRekomendasi.setImage(img);
            imgRekomendasi.setFitWidth(800);
            imgRekomendasi.setFitHeight(160);
            
            // Potongan sudut lengkung untuk file gambar gambar panoramik utuh
            Rectangle clip = new Rectangle(800, 160);
            clip.setArcWidth(25);
            clip.setArcHeight(25);
            imgRekomendasi.setClip(clip);
        } catch (Exception e) {
            System.out.println("⚠️ Gambar banner rekomendasi-kegiatan.png tidak ditemukan!");
        }
        bannerRekomendasi.getChildren().add(imgRekomendasi);
        sectionRekomendasi.getChildren().addAll(lblTabRekomendasi, bannerRekomendasi);

        // =====================================================================
        // 4. SEKSYEN KARTU: SOROTAN BUDAYA (UPDATE ACCENT BAR)
        // =====================================================================
        VBox sectionSorotan = new VBox(12);
        sectionSorotan.setMaxWidth(800);

        // 👉 PERBAIKAN: Garis aksen oranye vertikal memanjang ke bawah
        Label lblTabSorotan = new Label("Sorotan Budaya");
        lblTabSorotan.setStyle(
            "-fx-border-color: transparent transparent transparent #FF9800; " +
            "-fx-border-width: 0 0 0 4; " +
            "-fx-padding: 2 0 2 12; " +
            "-fx-font-family: 'Poppins'; " +
            "-fx-font-weight: bold; " +
            "-fx-font-size: 16px; " +
            "-fx-text-fill: #003A6C;"
        );
        
        lblTabSorotan.setCursor(javafx.scene.Cursor.HAND);
        lblTabSorotan.setOnMouseClicked(event -> {
            if (DashboardUser.getInstance() != null) {
                DashboardUser.getInstance().pindahKeSorotanBudayaPenuh();
            }
        });

        StackPane bannerSorotan = new StackPane();
        bannerSorotan.setPrefHeight(160);
        bannerSorotan.setMaxWidth(800);
        // 👉 PERBAIKAN: Sudut radius diatur melengkung 15px rata di semua sisi
        bannerSorotan.setStyle("-fx-background-color: #F8F7F4; -fx-background-radius: 15; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.08), 10, 0, 0, 4);");
        
        ImageView imgSorotan = new ImageView();
        try {
            Image img = new Image(getClass().getResourceAsStream("/aset/gambarLuminara/sorotan-budaya.png"));
            imgSorotan.setImage(img);
            imgSorotan.setFitWidth(800);
            imgSorotan.setFitHeight(160);
            
            Rectangle clip = new Rectangle(800, 160);
            clip.setArcWidth(25);
            clip.setArcHeight(25);
            imgSorotan.setClip(clip);
        } catch (Exception e) {
            System.out.println("⚠️ Gambar banner sorotan-budaya.png tidak ditemukan!");
        }
        bannerSorotan.getChildren().add(imgSorotan);
        sectionSorotan.getChildren().addAll(lblTabSorotan, bannerSorotan);

        // Gabungkan seluruh konstruksi komponen ke panel utama
        contentBox.getChildren().addAll(welcomeHeader, sectionTiket, sectionRekomendasi, sectionSorotan);

        view = new ScrollPane(contentBox);
        view.setFitToWidth(true);
        view.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        view.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        view.setStyle("-fx-background: transparent; -fx-background-color: transparent; -fx-border-color: transparent;");
    }

    // --- METHOD HELPER: Membuat Baris Putih Manis untuk Isian Data Tiket ---
    private HBox createTicketRow(String activityName, String dateTime) {
        HBox row = new HBox();
        row.setAlignment(Pos.CENTER_LEFT);
        row.setStyle("-fx-background-color: #F8F7F4; -fx-background-radius: 12; -fx-padding: 12 25 12 25;");
        
        Label lblActivity = new Label(activityName);
        lblActivity.setStyle("-fx-text-fill: #003A6C; -fx-font-weight: bold; -fx-font-family: 'Poppins'; -fx-font-size: 13px;");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS); 

        Label lblDateTime = new Label(dateTime);
        lblDateTime.setStyle("-fx-text-fill: #003A6C; -fx-font-family: 'Poppins'; -fx-font-size: 13px; -fx-font-weight: bold;");

        row.getChildren().addAll(lblActivity, spacer, lblDateTime);
        return row;
    }

    public Parent getView() {
        return view;
    }
}