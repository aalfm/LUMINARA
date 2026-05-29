package gradleproject;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DetailBudayaPage {

    public void start(Stage primaryStage) {
        // Root menggunakan StackPane untuk menampung Background Utama Pelabuhan
        StackPane root = new StackPane();
        root.getStyleClass().add("dashboard-root");

        try {
            String bgPath = getClass().getResource("/aset/gambarLuminara/gambar-bg.png").toExternalForm();
            root.setStyle("-fx-background-image: url('" + bgPath + "'); " +
                          "-fx-background-repeat: no-repeat; " +
                          "-fx-background-size: cover; " +
                          "-fx-background-position: center center;");
        } catch (Exception e) {
            root.setStyle("-fx-background-color: #0A2540;");
        }

        HBox mainLayout = new HBox();
        mainLayout.setAlignment(Pos.CENTER_LEFT);

        // ==================== 1. SIDEBAR NAVIGATION (KIRI) ====================
        VBox sidebar = new VBox(15);
        sidebar.getStyleClass().add("sidebar");
        sidebar.setPrefWidth(280);
        sidebar.setMinWidth(280);
        sidebar.setPadding(new Insets(35, 20, 35, 25));

        // Header Sidebar
        VBox sidebarHeader = new VBox(5);
        ImageView imgLogo = new ImageView();
        try {
            imgLogo.setImage(new Image(getClass().getResourceAsStream("/aset/gambarLuminara/luminara-textWhite.png")));
            imgLogo.setFitWidth(140);
            imgLogo.setPreserveRatio(true);
        } catch (Exception e) {}
        
        Label lblRole = new Label("Pengunjung");
        lblRole.getStyleClass().add("sidebar-role-text");
        sidebarHeader.getChildren().addAll(imgLogo, lblRole);
        sidebarHeader.setPadding(new Insets(0, 0, 15, 0));

        // Menu Utama Box
        VBox menuBox = new VBox(8);
        Button btnTentang = createMenuButton("Tentang Kami", false, primaryStage);
        Button btnBudaya = createMenuButton("Budaya", true, primaryStage); // Tetap aktif di kategori Budaya
        Button btnFestival = createMenuButton("Festival", false, primaryStage);
        Button btnLokakarya = createMenuButton("Lokakarya", false, primaryStage);
        Button btnMusik = createMenuButton("Musik", false, primaryStage);
        Button btnUlasan = createMenuButton("Ulasan", false, primaryStage);
        Button btnEksplorasi = createMenuButton("Mulai Eksplorasi", false, primaryStage);
        menuBox.getChildren().addAll(btnTentang, btnBudaya, btnFestival, btnLokakarya, btnMusik, btnUlasan, btnEksplorasi);

        Region sidebarSpacer = new Region();
        VBox.setVgrow(sidebarSpacer, Priority.ALWAYS);

        ImageView imgMiniLogo = new ImageView();
        try {
            imgMiniLogo.setImage(new Image(getClass().getResourceAsStream("/aset/gambarLuminara/luminara-logoWhite.png")));
            imgMiniLogo.setFitWidth(50);
            imgMiniLogo.setPreserveRatio(true);
        } catch (Exception e) {}
        HBox miniLogoContainer = new HBox(imgMiniLogo);
        miniLogoContainer.setAlignment(Pos.BOTTOM_LEFT);
        miniLogoContainer.setPadding(new Insets(0, 0, 0, 35));

        sidebar.getChildren().addAll(sidebarHeader, menuBox, sidebarSpacer, miniLogoContainer);

        // ==================== 2. AREA KONTEN UTAMA DETAIL (KANAN) ====================
        StackPane rightContentArea = new StackPane();
        rightContentArea.setPadding(new Insets(40, 50, 40, 50));
        HBox.setHgrow(rightContentArea, Priority.ALWAYS);

        // --- KARTU BESAR BIRU TUA (Outer Frame Card) ---
        VBox outerBlueCard = new VBox();
        outerBlueCard.getStyleClass().add("detail-outer-card");
        outerBlueCard.setPadding(new Insets(45, 40, 40, 40)); // Memberi ruang bagi inner card putih

        // --- KARTU DALAM PUTIH BERSIH (Inner Content Card) ---
        VBox innerWhiteCard = new VBox(18);
        innerWhiteCard.getStyleClass().add("detail-inner-card");
        VBox.setVgrow(innerWhiteCard, Priority.ALWAYS);

        // A. Gambar Banner Kegiatan (Atas)
        StackPane eventImageBanner = new StackPane();
        eventImageBanner.setPrefHeight(190);
        eventImageBanner.setMinHeight(190);
        eventImageBanner.getStyleClass().add("detail-image-banner");
        try {
            String imgStyle = "-fx-background-image: url('" + getClass().getResource("/aset/gambarLuminara/img-fest2.png").toExternalForm() + "'); " +
                              "-fx-background-repeat: no-repeat; " +
                              "-fx-background-size: cover; " +
                              "-fx-background-position: center center; " +
                              "-fx-background-radius: 16px;";
            eventImageBanner.setStyle(imgStyle);
        } catch (Exception e) {
            eventImageBanner.setStyle("-fx-background-color: #A0AEC0; -fx-background-radius: 16px;");
        }

        // B. Deskripsi Teks Detail Artikel
        VBox textContentBox = new VBox(10);
        textContentBox.setPadding(new Insets(0, 25, 0, 25));

        Label lblTitle = new Label("Makassar Traditional Costume Showcase");
        lblTitle.getStyleClass().add("detail-content-title");

        Label lblDescription = new Label(
            "Pementasan budaya yang menampilkan keindahan, filosofi, dan identitas masyarakat " +
            "Makassar melalui pakaian adat tradisional Sulawesi Selatan. Acara ini berfokus pada " +
            "visualisasi busana, kain sutra, dan aksesoris tradisional yang merepresentasikan nilai " +
            "budaya, status sosial, serta kehormatan masyarakat lokal. Busana yang ditampilkan " +
            "meliputi Baju Bodo untuk wanita, Baju Bella Dada untuk pria, serta Passapu sebagai penutup " +
            "kepala khas Makassar. Showcase ini juga menampilkan keindahan kain sutra Lipa' Sabbe " +
            "dan perhiasan emas tradisional yang memperkuat estetika budaya Makassar. Dalam " +
            "konsep modern, pementasan dikemas dalam bentuk parade atau pertunjukan teatrikal " +
            "dengan iringan musik tradisional dan akustik, sehingga menghadirkan pengalaman " +
            "budaya yang elegan, interaktif, dan penuh makna bagi penonton."
        );
        lblDescription.getStyleClass().add("detail-content-desc");
        lblDescription.setWrapText(true);
        textContentBox.getChildren().addAll(lblTitle, lblDescription);

        // C. Grid Meta Informasi Event (Lokasi, Tanggal, Harga, Kuota)
        GridPane infoGrid = new GridPane();
        infoGrid.setHgap(80);
        infoGrid.setVgap(15);
        infoGrid.setPadding(new Insets(5, 25, 10, 25));

        infoGrid.add(createMetaItem("Lokasi:", "Trans Studio Mall Makassar"), 0, 0);
        infoGrid.add(createMetaItem("Tanggal:", "20-22 Mei 2026"), 1, 0);
        infoGrid.add(createMetaItem("Harga:", "Rp25.000"), 0, 1);
        infoGrid.add(createMetaItem("Kuota:", "100 orang"), 1, 1);

        // D. Baris Tombol Kembali (Paling Bawah)
        HBox actionRow = new HBox();
        actionRow.setAlignment(Pos.BOTTOM_RIGHT);
        actionRow.setPadding(new Insets(0, 25, 15, 0));
        
        Button btnKembali = new Button("Kembali");
        btnKembali.getStyleClass().add("detail-btn-back");
        btnKembali.setCursor(javafx.scene.Cursor.HAND);
        
        // Klik tombol kembali untuk kembali ke halaman daftar preview budaya
        btnKembali.setOnAction(e -> {
            new BudayaPage().start(primaryStage);
        });
        actionRow.getChildren().add(btnKembali);

        // Satukan komponen ke kartu putih
        innerWhiteCard.getChildren().addAll(eventImageBanner, textContentBox, infoGrid, actionRow);
        outerBlueCard.getChildren().add(innerWhiteCard);

        // --- BADGE LABEL ORANYE MELAYANG "Budaya" ---
        Label lblBadge = new Label("Budaya");
        lblBadge.getStyleClass().add("detail-floating-badge");
        StackPane.setAlignment(lblBadge, Pos.TOP_LEFT);
        StackPane.setMargin(lblBadge, new Insets(15, 0, 0, 25)); // Menyesuaikan penempatan menumpuk pas di atas kartu biru

        // Masukkan kartu dan badge melayang ke dalam area kanan
        rightContentArea.getChildren().addAll(outerBlueCard, lblBadge);

        // Satukan struktur penuh layout utama
        mainLayout.getChildren().addAll(sidebar, rightContentArea);
        root.getChildren().add(mainLayout);

        Scene scene = new Scene(root, 1024, 720);
        try {
            scene.getStylesheets().add(getClass().getResource("/style/guest/intro.css").toExternalForm());
        } catch (Exception e) {}

        primaryStage.setTitle("Luminara - Detail Event Budaya");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Fungsi pembantu pembuatan item metadata informasi event
    private VBox createMetaItem(String title, String value) {
        VBox metaBox = new VBox(3);
        Label lblTitle = new Label(title);
        lblTitle.getStyleClass().add("detail-meta-title");
        
        Label lblValue = new Label(value);
        lblValue.getStyleClass().add("detail-meta-value");
        
        metaBox.getChildren().addAll(lblTitle, lblValue);
        return metaBox;
    }

    // Fungsi pembantu tombol navigasi sidebar
    private Button createMenuButton(String text, boolean isActive, Stage stage) {
        Button btn = new Button(text);
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setAlignment(Pos.CENTER_LEFT);
        btn.setPadding(new Insets(12, 15, 12, 20));
        btn.setCursor(javafx.scene.Cursor.HAND);
        
        if (isActive) {
            btn.getStyleClass().add("sidebar-btn-active");
        } else {
            btn.getStyleClass().add("sidebar-btn");
        }

        btn.setOnAction(e -> {
            if (text.equals("Tentang Kami")) {
                new DashboardPage().start(stage);
            } else if (text.equals("Budaya")) {
                new BudayaPage().start(stage);
            }
        });

        return btn;
    }
}
