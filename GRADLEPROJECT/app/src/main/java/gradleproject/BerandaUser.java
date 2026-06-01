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
        VBox contentBox = new VBox(30); 
        contentBox.setPadding(new Insets(30, 40, 30, 60)); 
        contentBox.setAlignment(Pos.TOP_LEFT);
        contentBox.setStyle("-fx-background-color: #F8F7F4;"); 

        // =====================================================================
        // 1. HEADER WELCOME 
        // =====================================================================
        VBox welcomeHeader = new VBox(2);
        Label lblTitle = new Label("Halo, Sobat Luminara");
        lblTitle.setStyle("-fx-font-family: 'Poppins', 'Sans-Serif'; -fx-font-weight: bold; -fx-font-size: 28px; -fx-text-fill: #003A6C;");
        
        Label lblSubtitle = new Label("Siap menjelajahi event budaya di Kota Makassar?");
        lblSubtitle.setStyle("-fx-font-family: 'Poppins', 'Sans-Serif'; -fx-font-size: 14px; -fx-text-fill: #5A7184;");
        welcomeHeader.getChildren().addAll(lblTitle, lblSubtitle);

        // =====================================================================
        // 2. SEKSYEN KARTU: TIKET KAMU (SUDAH DIRAPIKAN)
        // =====================================================================
        VBox sectionTiket = new VBox(15);
        sectionTiket.setPadding(new Insets(10, 0, 20, 0));

        // Label Judul dengan Garis Oranye di Kiri (Bisa di-klik)
        Label lblTabTiket = new Label("Tiket Kamu");
        lblTabTiket.setStyle(
            "-fx-border-color: transparent transparent transparent #FF9800; " + 
            "-fx-border-width: 0 0 0 4; " + 
            "-fx-padding: 2 0 2 12; " + 
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

        // Kotak Biru Gelap
        VBox boxTiketContainer = new VBox(12);
        boxTiketContainer.setStyle("-fx-background-color: #0A3B5C; -fx-background-radius: 15; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.08), 10, 0, 0, 4);"); 
        boxTiketContainer.setPadding(new Insets(20, 25, 20, 25));
        boxTiketContainer.setMaxWidth(800);

        // Panggil method database (Sudah tidak akan error merah lagi)
        int idUserAktif = (UserSession.getInstance() != null) ? UserSession.getInstance().getUserId() : 1;
        muatDataTiketUntukBeranda(boxTiketContainer, idUserAktif);

        sectionTiket.getChildren().addAll(lblTabTiket, boxTiketContainer);

        // =====================================================================
        // 3. SEKSYEN KARTU: REKOMENDASI KEGIATAN 
        // =====================================================================
        VBox sectionRekomendasi = new VBox(12);
        sectionRekomendasi.setMaxWidth(800);

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
        bannerRekomendasi.setStyle("-fx-background-color: #F8F7F4; -fx-background-radius: 15; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.08), 10, 0, 0, 4);"); 
        
        ImageView imgRekomendasi = new ImageView();
        try {
            Image img = new Image(getClass().getResourceAsStream("/aset/gambarLuminara/rekomendasi-kegiatan.png"));
            imgRekomendasi.setImage(img);
            imgRekomendasi.setFitWidth(800);
            imgRekomendasi.setFitHeight(160);
            
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
        // 4. SEKSYEN KARTU: SOROTAN BUDAYA 
        // =====================================================================
        VBox sectionSorotan = new VBox(12);
        sectionSorotan.setMaxWidth(800);

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

        // Gabungkan seluruh konstruksi
        contentBox.getChildren().addAll(welcomeHeader, sectionTiket, sectionRekomendasi, sectionSorotan);

        view = new ScrollPane(contentBox);
        view.setFitToWidth(true);
        view.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        view.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        view.setStyle("-fx-background: transparent; -fx-background-color: transparent; -fx-border-color: transparent;");
    }

    // =====================================================================
    // METHOD PENARIK DATABASE (DIPINDAHKAN KE SINI)
    // =====================================================================
    private void muatDataTiketUntukBeranda(VBox container, int userId) {
        container.getChildren().clear(); 

        gradleproject.dao.TicketDAO ticketDAO = new gradleproject.dao.TicketDAO();
        gradleproject.dao.EventDAO eventDAO = new gradleproject.dao.EventDAO();

        java.util.List<gradleproject.models.Ticket> daftarTiket = ticketDAO.findByUserId(userId);

        if (daftarTiket.isEmpty()) {
            Label lblKosong = new Label("Kamu belum memiliki tiket acara apa pun saat ini.");
            lblKosong.setStyle("-fx-text-fill: #FFFFFF; -fx-font-family: 'Poppins'; -fx-font-size: 13px; -fx-font-style: italic;");
            container.getChildren().add(lblKosong);
            return;
        }

        java.text.SimpleDateFormat formatTanggal = new java.text.SimpleDateFormat("dd MMM yyyy / HH:mm");
        int batasTampil = Math.min(daftarTiket.size(), 2);

        for (int i = 0; i < batasTampil; i++) {
            gradleproject.models.Ticket tiket = daftarTiket.get(i);
            gradleproject.models.Event acara = eventDAO.findById(tiket.getEventId());

            String namaAcara = (acara != null && acara.getTitle() != null) ? acara.getTitle() : "Acara Tidak Tersedia";
            String tanggalWaktu = "TBA";
            if (acara != null && acara.getEventDate() != null) {
                tanggalWaktu = formatTanggal.format(acara.getEventDate());
            }

            // Memanfaatkan method helper di bawah untuk UI Kapsul Putih
            HBox itemTiket = createTicketRow(namaAcara, tanggalWaktu);
            container.getChildren().add(itemTiket);
        }
    }

    // --- METHOD HELPER: Membuat Baris Putih Manis ---
    private HBox createTicketRow(String activityName, String dateTime) {
        HBox row = new HBox();
        row.setAlignment(Pos.CENTER_LEFT);
        row.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 8; -fx-padding: 12 20;");
        
        Label lblActivity = new Label(activityName);
        lblActivity.setStyle("-fx-font-family: 'Poppins'; -fx-font-weight: bold; -fx-font-size: 13px; -fx-text-fill: #0A3B5C;");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS); 

        Label lblDateTime = new Label(dateTime);
        lblDateTime.setStyle("-fx-font-family: 'Poppins'; -fx-font-weight: bold; -fx-font-size: 13px; -fx-text-fill: #0A3B5C;");

        row.getChildren().addAll(lblActivity, spacer, lblDateTime);
        return row;
    }

    public Parent getView() {
        return view;
    }
}