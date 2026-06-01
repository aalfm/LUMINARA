package gradleproject;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import java.text.SimpleDateFormat; // Untuk format tanggal

public class DetailAcaraBudaya {

    private VBox view;

    // 🎯 PERUBAHAN: Konstruktor sekarang meminta objek Event (acara)
    public DetailAcaraBudaya(gradleproject.models.Event acara) {
        view = new VBox(20);
        view.setPadding(new Insets(20, 20, 20, 80)); 
        view.setAlignment(Pos.TOP_LEFT);

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

        // Tab Oranye di atas (Ambil dari Kategori Event)
        Label lblTab = new Label(acara.getCategory());
        lblTab.setStyle("-fx-background-color: #FFC074; -fx-text-fill: #1A3C5A; -fx-font-family: 'Poppins'; -fx-font-weight: bold; -fx-font-size: 14px; -fx-padding: 8 40; -fx-background-radius: 15 15 0 0;");

        // Bingkai Abu-abu tebal
        VBox grayFrame = new VBox();
        grayFrame.setStyle("-fx-background-color: #D3D9DE; -fx-background-radius: 0 15 15 15;");
        grayFrame.setPadding(new Insets(25));
        VBox.setVgrow(grayFrame, Priority.ALWAYS);

        // Kartu Putih di dalam Bingkai Abu-abu
        VBox whiteCard = new VBox(15);
        whiteCard.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 15; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 5);");

        // --- BAGIAN GAMBAR ---
        ImageView imgEvent = new ImageView();
        try {
            String pathGambar = acara.getImagePath(); 
            Image img = null;
            
            // 1. Cek apakah ada teks lokasi gambar yang tersimpan
            if (pathGambar != null && !pathGambar.trim().isEmpty()) {
                // 🎯 PERBAIKAN: Gunakan java.io.File agar rute Windows terbaca dengan aman
                java.io.File fileGambar = new java.io.File(pathGambar);
                
                // Pastikan file gambarnya memang masih ada di laptop/komputer
                if (fileGambar.exists()) {
                    img = new Image(fileGambar.toURI().toString());
                } else {
                    System.out.println("⚠️ File gambar tidak ditemukan di komputer: " + pathGambar);
                }
            }
            
            // 2. Jika gambar gagal dimuat (atau organizer tidak upload)
            if (img == null) {
                // Kita gunakan icon yang sudah PASTI ADA di folder Anda (berdasarkan kode TambahAcaraView sebelumnya)
                java.io.InputStream defaultImgStream = getClass().getResourceAsStream("/aset/iconLuminara/icon-gambar.png");
                if (defaultImgStream != null) {
                    img = new Image(defaultImgStream);
                }
            }
            
            // 3. Pasang gambar ke layar
            if (img != null) {
                imgEvent.setImage(img);
            }
            
        } catch (Exception e) {
            System.out.println("⚠️ Error sistem saat memuat gambar: " + e.getMessage());
        }
        
        imgEvent.setFitWidth(720);
        imgEvent.setFitHeight(200); 
        
        Rectangle clip = new Rectangle(720, 200);
        clip.setArcWidth(15);
        clip.setArcHeight(15);
        imgEvent.setClip(clip);

        // --- BAGIAN TEKS INFORMASI ---
        VBox textInfoBox = new VBox(15);
        textInfoBox.setPadding(new Insets(10, 25, 25, 25)); 

        // 🎯 KODE DINAMIS: Ambil Judul dan Deskripsi
        Label lblTitle = new Label(acara.getTitle());
        lblTitle.setStyle("-fx-font-family: 'Poppins'; -fx-font-weight: bold; -fx-font-size: 18px; -fx-text-fill: #1A3C5A;");

        Label lblDesc = new Label(acara.getDescription());
        lblDesc.setWrapText(true); 
        lblDesc.setStyle("-fx-font-family: 'Poppins'; -fx-font-size: 12px; -fx-text-fill: #1A3C5A; -fx-line-spacing: 0.5em;");

        // --- GRID SPESIFIKASI ---
        GridPane gridInfo = new GridPane();
        gridInfo.setHgap(150); 
        gridInfo.setVgap(15);  

        // Format Harga
        String hargaTeks = acara.getTicketType().equalsIgnoreCase("Free") ? "Gratis" : String.format("Rp %,.0f", acara.getPrice());
        
        // Format Tanggal (Membuang detiknya agar lebih rapi)
        String tanggalTeks = new SimpleDateFormat("dd MMMM yyyy, HH:mm").format(acara.getEventDate());

        // 🎯 KODE DINAMIS: Ambil Data Spesifikasi
        gridInfo.add(createGridItem("Lokasi:", acara.getCategory()), 0, 0);
        gridInfo.add(createGridItem("Harga:", hargaTeks), 0, 1);
        gridInfo.add(createGridItem("Tanggal & Waktu:", tanggalTeks), 1, 0);
        
        // Pastikan Anda punya getKuota() di model Event. Jika tidak ada, ganti dengan "100 orang" sementara.
        gridInfo.add(createGridItem("Kuota:", acara.getQuota() + " orang"), 1, 1); 

        textInfoBox.getChildren().addAll(lblTitle, lblDesc, gridInfo);
        
        whiteCard.getChildren().addAll(imgEvent, textInfoBox);
        grayFrame.getChildren().add(whiteCard);
        contentContainer.getChildren().addAll(lblTab, grayFrame);

        view.getChildren().addAll(header, contentContainer);
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