package gradleproject;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane; // 🎯 Menggunakan ScrollPane
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane; // 🎯 Menggunakan StackPane untuk gambar
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat; 

public class DetailAcaraMusik {

    private ScrollPane view; // 🎯 Diubah menjadi ScrollPane agar bisa di-scroll

    // 🎯 KONSTRUKTOR: Menerima objek Event secara dinamis dari database
    public DetailAcaraMusik(gradleproject.models.Event acara) {
        // Kontainer dalam penampung seluruh UI komponen
        VBox contentBox = new VBox(25);
        contentBox.setPadding(new Insets(20, 20, 30, 80)); 
        contentBox.setAlignment(Pos.TOP_LEFT);
        contentBox.setStyle("-fx-background-color: transparent;");

        // 1. HEADER
        VBox header = new VBox(-5);
        Label lblHi = new Label("Hai, admin.");
        lblHi.getStyleClass().add("greeting-title");
        Label lblSub = new Label("Pantau pengguna ya . . ."); 
        lblSub.getStyleClass().add("greeting-subtitle");
        header.getChildren().addAll(lblHi, lblSub);

        // 2. WADAH KONTEN UTAMA
        VBox contentContainer = new VBox(0);
        contentContainer.setMaxWidth(770);
        contentContainer.setPadding(new Insets(10, 0, 0, 0));

        // Tab Kategori atas (Dinamis ditarik dari database)
        Label lblTab = new Label(acara.getCategory());
        lblTab.setStyle("-fx-background-color: #FFC074; -fx-text-fill: #1A3C5A; -fx-font-family: 'Poppins'; -fx-font-weight: bold; -fx-font-size: 14px; -fx-padding: 8 40; -fx-background-radius: 15 15 0 0;");

        // Bingkai Abu-abu tebal
        VBox grayFrame = new VBox();
        grayFrame.setStyle("-fx-background-color: #D3D9DE; -fx-background-radius: 0 15 15 15;");
        grayFrame.setPadding(new Insets(25));

        // Kartu Putih di dalam Bingkai Abu-abu
        VBox whiteCard = new VBox(15);
        whiteCard.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 15; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 5);");

        // =====================================================================
        // 🎯 LOGIKA GAMBAR DINAMIS (Mencegah Gambar Hilang / Putih)
        // =====================================================================
        // --- BAGIAN GAMBAR ---
        StackPane imagePane = new StackPane();
        imagePane.setPrefSize(720, 200);
        imagePane.setMaxSize(720, 200);
        imagePane.setStyle("-fx-background-color: #E2E8F0; -fx-background-radius: 15 15 0 0;");
        ImageView imgEvent = new ImageView();
        Image img = null;
        String imgPath = acara.getImagePath(); // Mengambil string "C:\Users\..." dari DB

        try {
            if (imgPath != null && !imgPath.trim().isEmpty()) {
                File fileGambar = new File(imgPath);
                
                // Cek apakah file benar-benar ada di komputer
                if (fileGambar.exists()) {
                    // 🎯 KUNCI: Konversi ke URI agar JavaFX bisa membaca file lokal
                    img = new Image(fileGambar.toURI().toString(), false);
                } else {
                    // Jika file tidak ditemukan di komputer, coba cari di aset internal
                    InputStream is = getClass().getResourceAsStream(imgPath);
                    if (is != null) {
                        img = new Image(is);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("⚠️ Error saat memuat gambar: " + e.getMessage());
        }

        // Fallback: Jika gambar tetap null (gagal semua), pakai gambar default
        if (img == null || img.isError()) {
            try {
                img = new Image(getClass().getResourceAsStream("/aset/gambarLuminara/event1.png"));
            } catch (Exception e) {
                System.out.println("⚠️ Gambar default pun tidak ditemukan!");
            }
        }

        // Terapkan ke ImageView
        if (img != null) {
            imgEvent.setImage(img);
            imgEvent.setFitWidth(720);
            imgEvent.setFitHeight(200); 
            imgEvent.setPreserveRatio(false);

            Rectangle clip = new Rectangle(720, 200);
            clip.setArcWidth(30);
            clip.setArcHeight(30);
            imgEvent.setClip(clip);

            imagePane.getChildren().add(imgEvent);
        }

        // --- BAGIAN TEKS INFORMASI (DINAMIS) ---
        VBox textInfoBox = new VBox(15);
        textInfoBox.setPadding(new Insets(10, 25, 25, 25)); 

        Label lblTitle = new Label(acara.getTitle()); 
        lblTitle.setStyle("-fx-font-family: 'Poppins'; -fx-font-weight: bold; -fx-font-size: 18px; -fx-text-fill: #1A3C5A;");

        Label lblDesc = new Label(acara.getDescription());
        lblDesc.setWrapText(true); 
        lblDesc.setStyle("-fx-font-family: 'Poppins'; -fx-font-size: 12px; -fx-text-fill: #1A3C5A; -fx-line-spacing: 0.5em;");

        // --- GRID SPESIFIKASI (DINAMIS) ---
        GridPane gridInfo = new GridPane();
        gridInfo.setHgap(150); 
        gridInfo.setVgap(15);  

        // Format Teks Uang Tiket
        String hargaTeks = "Gratis";
        if (acara.getTicketType() != null && !acara.getTicketType().equalsIgnoreCase("Free")) {
            hargaTeks = String.format("Rp %,.0f", acara.getPrice());
        }
        
        // Format Tanggal & Waktu
        String tanggalTeks = "TBA";
        if (acara.getEventDate() != null) {
            tanggalTeks = new SimpleDateFormat("dd MMMM yyyy, HH:mm").format(acara.getEventDate());
        }

        gridInfo.add(createGridItem("Lokasi:", acara.getCategory()), 0, 0); // Asumsi diisi kategori/lokasi
        gridInfo.add(createGridItem("Harga:", hargaTeks), 0, 1);
        gridInfo.add(createGridItem("Tanggal:", tanggalTeks), 1, 0);
        gridInfo.add(createGridItem("Kuota:", acara.getQuota() + " orang"), 1, 1);

        textInfoBox.getChildren().addAll(lblTitle, lblDesc, gridInfo);
        
        // Gabungkan elemen ke dalam struktur kartu putih dan bingkai abu
        whiteCard.getChildren().addAll(imagePane, textInfoBox);
        grayFrame.getChildren().add(whiteCard);
        contentContainer.getChildren().addAll(lblTab, grayFrame);

        contentBox.getChildren().addAll(header, contentContainer);

        // =====================================================================
        // 3. BUNGKUS DENGAN SCROLLPANE UTAMA
        // =====================================================================
        view = new ScrollPane(contentBox);
        view.setFitToWidth(true);
        view.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        view.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        view.setStyle("-fx-background: transparent; -fx-background-color: transparent; -fx-border-color: transparent;");
    }

    private VBox createGridItem(String header, String value) {
        VBox box = new VBox(2);
        Label lblHeader = new Label(header);
        lblHeader.setStyle("-fx-font-family: 'Poppins'; -fx-font-weight: bold; -fx-font-size: 12px; -fx-text-fill: #1A3C5A;");
        
        Label lblValue = new Label(value);
        lblValue.setStyle("-fx-font-family: 'Poppins'; -fx-font-size: 11px; -fx-text-fill: #1A3C5A;");
        
        box.getChildren().addAll(lblHeader, lblValue);
        return box;
    }

    public Parent getView() {
        return view;
    }
}