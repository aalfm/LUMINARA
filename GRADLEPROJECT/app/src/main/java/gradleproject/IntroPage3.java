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

        // 3. Konten Utama (VBox Vertikal) - Menggunakan Spacing Distribusi Manual
        VBox mainContainer = new VBox();
        mainContainer.setAlignment(Pos.TOP_CENTER);
        mainContainer.setPadding(new Insets(30, 50, 30, 50));

        // --- ATAS: Pagination Dots (Diposisikan Pojok Kanan Atas Secara Rapi) ---
        HBox topBar = new HBox();
        topBar.setAlignment(Pos.TOP_RIGHT);
        
        HBox dotsBox = new HBox(8);
        dotsBox.setAlignment(Pos.CENTER_RIGHT);
        for (int i = 0; i < 3; i++) {
            StackPane dot = new StackPane();
            dot.setPrefSize(10, 10); // Menentukan ukuran titik bulat
            if (i == 2) {
                // Dot ke-3 Aktif (Warna Putih Bersinar)
                dot.setStyle("-fx-background-color: white; -fx-background-radius: 50%;");
            } else {
                // Dot 1 & 2 Inaktif (Putih Transparan)
                dot.setStyle("-fx-background-color: rgba(255, 255, 255, 0.4); -fx-background-radius: 50%;");
            }
            dotsBox.getChildren().add(dot);
        }
        topBar.getChildren().add(dotsBox);

        // --- TENGAH: Konten Utama (Logo + Judul + Tombol) ---
        VBox centerContent = new VBox(25); // Spacing antar baris komponen tengah
        centerContent.setAlignment(Pos.CENTER);
        
        // Spacer Atas untuk mendorong area tengah ke posisi ideal (tengah layar)
        Region topSpacer = new Region();
        VBox.setVgrow(topSpacer, Priority.ALWAYS);

        // 1. Logo Bulan Sabit Atas
        ImageView logoView = new ImageView();
        try {
            logoView.setImage(new Image(getClass().getResourceAsStream("/aset/gambarLuminara/luminara-logoWhite.png")));
            logoView.setFitWidth(100); // Ukuran proporsional sesuai screenshot
            logoView.setFitHeight(100);
            logoView.setPreserveRatio(true);
        } catch (Exception e) {}

        // 2. Judul Kombinasi Teks + Logo Gambar Tulisan
        VBox titleContainer = new VBox(5);
        titleContainer.setAlignment(Pos.CENTER);

        Label titleLine1 = new Label("Siap memulai perjalanan");
        titleLine1.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-text-fill: white;");

        HBox titleLine2 = new HBox(12); // Jarak horizontal antara teks dan logo teks
        titleLine2.setAlignment(Pos.CENTER);

        Label titleTextPart = new Label("budaya bersama");
        titleTextPart.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-text-fill: white;");

        ImageView logoTextImage = new ImageView();
        try {
            Image imgLogo = new Image(getClass().getResourceAsStream("/aset/gambarLuminara/luminara-textWhite.png"));
            logoTextImage.setImage(imgLogo);
            logoTextImage.setFitHeight(38); // Menyamakan tinggi logo dengan ukuran font teks
            logoTextImage.setPreserveRatio(true);
            logoTextImage.setTranslateY(2); // Penyeimbang posisi vertikal
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

        Button btnKembali = new Button("Kembali");
        btnKembali.setPrefSize(140, 45);
        btnKembali.setStyle("-fx-background-color: white; -fx-text-fill: #002B49; -fx-font-weight: bold; -fx-background-radius: 8px; -fx-cursor: hand;");
        btnKembali.setOnAction(e -> new IntroPage2().start(primaryStage));

        Button btnPengunjung = new Button("Pengunjung");
        btnPengunjung.setPrefSize(140, 45);
        btnPengunjung.setStyle("-fx-background-color: #FF9800; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 8px; -fx-cursor: hand;");
        btnPengunjung.setOnAction(e -> {
            DashboardPage dashboardPage = new DashboardPage();
            dashboardPage.start(primaryStage);
        });

        Button btnPenyelenggara = new Button("Penyelenggara");
        btnPenyelenggara.setPrefSize(140, 45);
        btnPenyelenggara.setStyle("-fx-background-color: #FF9800; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 8px; -fx-cursor: hand;");

        Button btnAdmin = new Button("Admin");
        btnAdmin.setPrefSize(140, 45);
        btnAdmin.setStyle("-fx-background-color: #FF9800; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 8px; -fx-cursor: hand;");

        roleButtonsBox.getChildren().addAll(btnKembali, btnPengunjung, btnPenyelenggara, btnAdmin);
        
        // Satukan elemen ke dalam Container Tengah
        centerContent.getChildren().addAll(logoView, titleContainer, subLabel, roleButtonsBox);

        // Spacer Bawah untuk menyeimbangkan posisi konten tengah dan mendorong footer ke dasar
        Region bottomSpacer = new Region();
        VBox.setVgrow(bottomSpacer, Priority.ALWAYS);

        // --- BAWAH: Footer Tagline ---
        Label footerTagline = new Label("Cahaya budaya, perjalanan yang menyenangkan.");
        footerTagline.setStyle("-fx-font-size: 13px; -fx-text-fill: #FFA726; -fx-font-style: italic;");

        // Susun tata letak urutan dari atas ke bawah ke dalam container utama
        mainContainer.getChildren().addAll(topBar, topSpacer, centerContent, bottomSpacer, footerTagline);

        root.getChildren().addAll(overlay, mainContainer);

        // 4. Scene Setup
        Scene scene = new Scene(root, 1024, 720);
        try {
            scene.getStylesheets().add(getClass().getResource("/style/guest/intro.css").toExternalForm());
        } catch (Exception e) {}

        primaryStage.setTitle("Luminara - Pilih Peran");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}