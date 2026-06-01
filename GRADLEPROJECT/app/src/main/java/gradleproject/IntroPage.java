package gradleproject;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class IntroPage {

    public void start(Stage primaryStage) {
        // 1. Lapisan Utama
        StackPane root = new StackPane();
        root.getStyleClass().add("intro-container");

        // Memuat Gambar Background
        try {
            String bgPath = getClass().getResource("/aset/gambarLuminara/gambar-bg.png").toExternalForm();
            root.setStyle("-fx-background-image: url('" + bgPath + "'); " +
                          "-fx-background-repeat: no-repeat; " +
                          "-fx-background-size: cover; " +
                          "-fx-background-position: center center;");

        } catch (Exception e) {
            System.out.println("⚠️ Gagal memuat gambar background: " + e.getMessage());
        }

        // 2. Lapisan Overlay Gelap
        StackPane overlay = new StackPane();
        overlay.getStyleClass().add("background-overlay");

        // ==========================================
        // 🎯 PERBAIKAN 1: Indikator Titik (Dots) Kanan Atas
        // ==========================================
        HBox dotIndicator = new HBox(8); // Jarak antar titik
        dotIndicator.setAlignment(Pos.TOP_RIGHT);
        dotIndicator.setPadding(new Insets(30, 40, 0, 0)); // Margin dari atas dan kanan
        dotIndicator.setPickOnBounds(false); // Agar area transparan HBox tidak menghalangi klik

        // Membuat 3 titik (1 biru aktif, 2 putih pasif)
        Circle dot1 = new Circle(4.5, Color.web("#004e92")); // Warna biru Luminara
        Circle dot2 = new Circle(4.5, Color.WHITE);
        Circle dot3 = new Circle(4.5, Color.WHITE);

        dotIndicator.getChildren().addAll(dot1, dot2, dot3);

        // ==========================================
        // 🎯 PERBAIKAN 2: Mengurangi Jarak VBox menjadi 15 (sebelumnya 25)
        // ==========================================
        VBox contentBox = new VBox(15);
        contentBox.setAlignment(Pos.CENTER);
        contentBox.getStyleClass().add("content-box");

        // Komponen Gambar Logo
        ImageView logoView = new ImageView();
        try {
            Image logoImage = new Image(getClass().getResourceAsStream("/aset/gambarLuminara/luminara-textWhite.png"));
            logoView.setImage(logoImage);
            logoView.setFitWidth(350);
            logoView.setPreserveRatio(true);
            VBox.setMargin(logoView, new javafx.geometry.Insets(50, 0, 0, 0)); 

        } catch (Exception e) {
            System.out.println("⚠️ Gagal memuat logo.png di folder aset/gambarLuminara/");
        }

        // ==========================================
        // 🎯 PERBAIKAN 3: Melebarkan Tagline agar satu baris
        // ==========================================
        Label taglineLabel = new Label("Nikmati cara baru untuk menjelajahi budaya dan komunitas lokal di Kota Makassar.");
        taglineLabel.getStyleClass().add("tagline-text");
        taglineLabel.setWrapText(true);
        taglineLabel.setMaxWidth(900); // Dilebarkan dari 600 ke 900 agar muat satu baris

        taglineLabel.setAlignment(Pos.CENTER); // Menempatkan konten label di tengah
        taglineLabel.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        VBox.setMargin(taglineLabel, new javafx.geometry.Insets(20, 0, 0, 0)); 
        
        // Komponen Tombol Aksi
        Button exploreButton = new Button("Mulai Jelajahi");
        exploreButton.getStyleClass().add("explore-button");
        
        // ---> TAMBAHKAN BARIS INI <---
        // Memberikan jarak ekstra (margin) di bagian ATAS tombol sebesar 30 piksel
        VBox.setMargin(exploreButton, new javafx.geometry.Insets(100, 0, 0, 0)); 
        
        exploreButton.setOnAction(e -> {
            IntroPage2 introPage2 = new IntroPage2();
            introPage2.start(primaryStage);
        });

        // Memasukkan semua elemen ke dalam VBox
        contentBox.getChildren().addAll(logoView, taglineLabel, exploreButton);

        // Menumpuk komponen ke dalam Root (Tambahkan dotIndicator)
        root.getChildren().addAll(overlay, contentBox, dotIndicator);

        // 4. Inisialisasi Scene dan Menampilkan Window
        Scene scene = new Scene(root, 1280, 650);
        
        // Memuat file style.css
        try {
            scene.getStylesheets().add(getClass().getResource("/style/guest/intro.css").toExternalForm());
        } catch (Exception e) {
            System.out.println("⚠️ Gagal memuat file style.css");
        }

        primaryStage.setTitle("Luminara - Intro Page");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}