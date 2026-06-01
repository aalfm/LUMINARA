package gradleproject;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class IntroPage3 {

    public void start(Stage primaryStage) {
        // 1. Root Container (StackPane untuk background)
        StackPane root = new StackPane();
        root.getStyleClass().add("intro3-root");

        // Memuat Gambar Background (Latar Pelabuhan/Phinisi)
        try {
            String bgPath = getClass().getResource("/aset/gambarLuminara/gambar-bg.png").toExternalForm();
            root.setStyle("-fx-background-image: url('" + bgPath + "'); " +
                          "-fx-background-repeat: no-repeat; " +
                          "-fx-background-size: cover; " +
                          "-fx-background-position: center center;");
        } catch (Exception e) {
            root.setStyle("-fx-background-color: #2C3E50;"); // Fallback warna gelap
        }

        // 2. Overlay Gelap (Agar teks terbaca)
        Region overlay = new Region();
        overlay.setStyle("-fx-background-color: rgba(0, 0, 0, 0.4);");

        // 3. Konten Utama (VBox Vertikal)
        VBox mainContainer = new VBox();
        mainContainer.setAlignment(Pos.TOP_CENTER);
        mainContainer.setPadding(new Insets(30, 50, 30, 50));

        // --- ATAS: Pagination Dots ---
        HBox topBar = new HBox();
        topBar.setAlignment(Pos.TOP_RIGHT);
        
        HBox dotsBox = new HBox(8);
        dotsBox.setAlignment(Pos.CENTER_RIGHT);
        
        // 🎯 PERBAIKAN: Menggunakan Circle agar konsisten dengan IntroPage 1 & 2
        Circle dot1 = new Circle(4.5, javafx.scene.paint.Color.WHITE); // Halaman 1 (Pasif)
        Circle dot2 = new Circle(4.5, javafx.scene.paint.Color.WHITE); // Halaman 2 (Pasif)
        
        // Titik ketiga (halaman saat ini) di-set aktif menggunakan warna biru Luminara
        Circle dot3 = new Circle(4.5, javafx.scene.paint.Color.web("#003A6C")); 
        
        /* * Catatan: Jika kamu lebih suka titik aktifnya berwarna oranye agar senada 
         * dengan tombol-tombol di bawahnya, kamu bisa ganti kode dot3 di atas menjadi:
         * Circle dot3 = new Circle(4.5, javafx.scene.paint.Color.web("#FF9800"));
         */

        dotsBox.getChildren().addAll(dot1, dot2, dot3);
        topBar.getChildren().add(dotsBox);

        // --- TENGAH: Konten Utama (Logo + Judul + Tombol) ---
        VBox centerContent = new VBox(25);
        centerContent.setAlignment(Pos.CENTER);
        
        Region topSpacer = new Region();
        VBox.setVgrow(topSpacer, Priority.ALWAYS);

        // 1. Logo Bulan Sabit Atas
        ImageView logoView = new ImageView();
        try {
            logoView.setImage(new Image(getClass().getResourceAsStream("/aset/gambarLuminara/luminara-logoWhite.png")));
            logoView.setFitWidth(100);
            logoView.setFitHeight(100);
            logoView.setPreserveRatio(true);
        } catch (Exception e) {}

        // 2. Judul Kombinasi Teks + Logo Gambar Tulisan
        VBox titleContainer = new VBox(5);
        titleContainer.setAlignment(Pos.CENTER);

        Label titleLine1 = new Label("Siap memulai perjalanan");
        titleLine1.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-text-fill: white;");

        HBox titleLine2 = new HBox(12);
        titleLine2.setAlignment(Pos.CENTER);

        Label titleTextPart = new Label("budaya bersama");
        titleTextPart.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-text-fill: white;");

        ImageView logoTextImage = new ImageView();
        try {
            Image imgLogo = new Image(getClass().getResourceAsStream("/aset/gambarLuminara/luminara-textWhite.png"));
            logoTextImage.setImage(imgLogo);
            logoTextImage.setFitHeight(38);
            logoTextImage.setPreserveRatio(true);
            logoTextImage.setTranslateY(2);
        } catch (Exception e) {}

        titleLine2.getChildren().addAll(titleTextPart, logoTextImage);
        titleContainer.getChildren().addAll(titleLine1, titleLine2);

        // 3. Subtitle / Petunjuk Peran
        Label subLabel = new Label("Pilih peran kamu untuk melanjutkan");
        subLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #E2E8F0;");

        // 4. Baris Tombol-Tombol Peran (Grid Horizontal)
        HBox roleButtonsBox = new HBox(15);
        roleButtonsBox.setAlignment(Pos.CENTER);
        roleButtonsBox.setPadding(new Insets(10, 0, 0, 0));

        // Tombol Kembali ke Intro Halaman 2
        Button btnKembali = new Button("Kembali");
        btnKembali.setPrefSize(140, 45);
        btnKembali.setStyle("-fx-background-color: white; -fx-text-fill: #002B49; -fx-font-weight: bold; -fx-background-radius: 8px; -fx-cursor: hand;");
        btnKembali.setOnAction(e -> new IntroPage2().start(primaryStage));

        // 🔘 1. TOMBOL PENGUNJUNG
        Button btnPengunjung = new Button("Pengunjung");
        btnPengunjung.setPrefSize(140, 45);
        btnPengunjung.setStyle("-fx-background-color: #FF9800; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 8px; -fx-cursor: hand;");
        
        // 🎯 FIX UTAMA: Kirim context "Pengunjung" ke SignInPage
        btnPengunjung.setOnAction(e -> {
            System.out.println("Masuk ke Guest Mode...");
            
            // Panggil halaman utama pengunjung (Sesuaikan dengan nama kelas halamanmu)
            DashboardPage dashboardPage = new DashboardPage();
            dashboardPage.start(primaryStage);
        });

        // 🔘 2. TOMBOL PENYELENGGARA
        Button btnPenyelenggara = new Button("Penyelenggara");
        btnPenyelenggara.setPrefSize(140, 45);
        btnPenyelenggara.setStyle("-fx-background-color: #FF9800; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 8px; -fx-cursor: hand;");
        
        // 🎯 FIX UTAMA: Kirim context "Penyelenggara" ke SignInPage
        btnPenyelenggara.setOnAction(e -> new SignInPage("Penyelenggara").start(primaryStage));

        // 🔘 3. TOMBOL ADMIN
        Button btnAdmin = new Button("Admin");
        btnAdmin.setPrefSize(140, 45);
        btnAdmin.setStyle("-fx-background-color: #FF9800; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 8px; -fx-cursor: hand;");
        
        // 🎯 FIX UTAMA: Kirim context "ADMIN" ke SignInPage
        btnAdmin.setOnAction(e -> new SignInPage("ADMIN").start(primaryStage));

        // Masukkan semua tombol ke baris pilihan
        roleButtonsBox.getChildren().addAll(btnKembali, btnPengunjung, btnPenyelenggara, btnAdmin);
        centerContent.getChildren().addAll(logoView, titleContainer, subLabel, roleButtonsBox);

        Region bottomSpacer = new Region();
        VBox.setVgrow(bottomSpacer, Priority.ALWAYS);

        // --- BAWAH: Footer Tagline ---
        Label footerTagline = new Label("Cahaya budaya, perjalanan yang menyenangkan.");
        footerTagline.setStyle("-fx-font-size: 13px; -fx-text-fill: #FFA726; -fx-font-style: italic;");

        mainContainer.getChildren().addAll(topBar, topSpacer, centerContent, bottomSpacer, footerTagline);
        root.getChildren().addAll(overlay, mainContainer);

        // Scene Setup
        Scene scene = new Scene(root, 1280, 650); // Disesuaikan dengan resolusi standar halaman login Anda
        try {
            scene.getStylesheets().add(getClass().getResource("/style/guest/intro.css").toExternalForm());
        } catch (Exception e) {}

        primaryStage.setTitle("Luminara - Pilih Peran");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}