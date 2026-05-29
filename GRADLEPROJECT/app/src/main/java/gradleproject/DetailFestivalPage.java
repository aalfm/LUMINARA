package gradleproject;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
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
import javafx.stage.Stage;

public class DetailFestivalPage {

    public void start(Stage primaryStage) {
        // Root StackPane untuk menampung latar belakang gambar pelabuhan
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

        // Kotak Menu Navigasi (Festival diset aktif/true karena bagian dari menu festival)
        VBox menuBox = new VBox(8);
        Button btnTentang = createMenuButton("Tentang Kami", false, primaryStage);
        Button btnBudaya = createMenuButton("Budaya", false, primaryStage);
        Button btnFestival = createMenuButton("Festival", true, primaryStage);
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

        // ==================== 2. MAIN CONTENT AREA (KANAN) ====================
        // Kontainer kanan menampung bingkai luar detail box agar posisinya pas di tengah-tengah
        StackPane rightAreaContainer = new StackPane();
        rightAreaContainer.setPadding(new Insets(40, 50, 40, 50));
        HBox.setHgrow(rightAreaContainer, Priority.ALWAYS);

        // Struktur Utama Box Detail (Gabungan Label Oranye Atas + Bingkai Biru)
        VBox detailWrapper = new VBox();
        detailWrapper.setAlignment(Pos.TOP_LEFT);
        detailWrapper.setMaxWidth(680);
        detailWrapper.setMaxHeight(580);

        // Tag Label Kategori Oranye "Festival" di pojok atas kiri
        Label lblCategoryTag = new Label("Festival");
        lblCategoryTag.getStyleClass().add("detail-category-tag");

        // Bingkai Melengkung Biru Tua (Kotak Besar)
        VBox blueContainer = new VBox();
        blueContainer.getStyleClass().add("detail-blue-container");
        VBox.setVgrow(blueContainer, Priority.ALWAYS);

        // Kartu Dalam Putih yang menampung artikel & foto lengkap
        VBox innerWhiteCard = new VBox(15);
        innerWhiteCard.getStyleClass().add("detail-inner-white-card");
        VBox.setVgrow(innerWhiteCard, Priority.ALWAYS);

        // ScrollPane dalam kotak putih agar info pelengkap aman dibaca jika layar kecil
        ScrollPane internalScroll = new ScrollPane();
        internalScroll.getStyleClass().add("detail-internal-scroll");
        internalScroll.setFitToWidth(true);
        VBox.setVgrow(internalScroll, Priority.ALWAYS);

        VBox scrollContent = new VBox(15);
        scrollContent.setPadding(new Insets(5, 5, 5, 5));

        // 1. Gambar Utama Festival
        StackPane topImageHeader = new StackPane();
        topImageHeader.setPrefHeight(180);
        topImageHeader.setMinHeight(180);
        topImageHeader.getStyleClass().add("detail-image-header");
        try {
            String imgStyle = "-fx-background-image: url('" + getClass().getResource("/aset/gambarLuminara/img-fest2.png").toExternalForm() + "'); " +
                              "-fx-background-repeat: no-repeat; " +
                              "-fx-background-size: cover; " +
                              "-fx-background-position: center center; " +
                              "-fx-background-radius: 16px;";
            topImageHeader.setStyle(imgStyle);
        } catch (Exception e) {
            topImageHeader.setStyle("-fx-background-color: #A0AEC0; -fx-background-radius: 16px;");
        }

        // 2. Judul Artikel Detail
        Label lblTitle = new Label("Makassar Traditional Costume Showcase");
        lblTitle.getStyleClass().add("detail-main-title");
        lblTitle.setWrapText(true);

        // 3. Deskripsi Narasi Panjang
        Label lblDescription = new Label(
            "Pementasan budaya yang menampilkan keindahan, filosofi, dan identitas masyarakat Makassar melalui pakaian adat tradisional Sulawesi Selatan. Acara ini berfokus pada visualisasi busana, kain sutra, dan aksesoris tradisional yang merepresentasikan nilai budaya, status sosial, serta kehormatan masyarakat lokal. Busana yang ditampilkan meliputi Baju Bodo untuk wanita, Baju Bella Dada untuk pria, serta Passapu sebagai penutup kepala khas Makassar. Showcase ini juga menampilkan keindahan kain sutra Lipa' Sabbe dan perhiasan emas tradisional yang memperkuat estetika budaya Makassar. Dalam konsep modern, pementasan dikemas dalam bentuk parade atau pertunjukan teatrikal dengan iringan musik tradisional dan akustik, sehingga menghadirkan pengalaman budaya yang elegan, interaktif, dan penuh makna bagi penonton."
        );
        lblDescription.getStyleClass().add("detail-main-desc");
        lblDescription.setWrapText(true);

        // 4. Grid Metadata Informasi (Lokasi, Tanggal, Harga, Kuota)
        GridPane infoGrid = new GridPane();
        infoGrid.setHgap(60);
        infoGrid.setVgap(15);
        infoGrid.setPadding(new Insets(10, 0, 10, 0));

        VBox itemLokasi = createMetaItem("Lokasi:", "Trans Studio Mall Makassar");
        VBox itemTanggal = createMetaItem("Tanggal:", "20-22 Mei 2026");
        VBox itemHarga = createMetaItem("Harga:", "Rp25.000");
        VBox itemKuota = createMetaItem("Kuota:", "100 orang");

        infoGrid.add(itemLokasi, 0, 0);
        infoGrid.add(itemTanggal, 1, 0);
        infoGrid.add(itemHarga, 0, 1);
        infoGrid.add(itemKuota, 1, 1);

        // 5. Baris Tombol Kembali Jingga di pojok kanan bawah
        HBox footerRow = new HBox();
        footerRow.setAlignment(Pos.BOTTOM_RIGHT);
        Button btnKembali = new Button("Kembali");
        btnKembali.getStyleClass().add("detail-btn-kembali");
        btnKembali.setCursor(javafx.scene.Cursor.HAND);
        
        // Ketika tombol Kembali diklik, balikkan pengunjung ke halaman Festival Preview
        btnKembali.setOnAction(e -> {
            new FestivalPage().start(primaryStage);
        });
        footerRow.getChildren().add(btnKembali);

        // Menyusun komponen ke dalam Scroll Konten
        scrollContent.getChildren().addAll(topImageHeader, lblTitle, lblDescription, infoGrid);
        internalScroll.setContent(scrollContent);
        
        innerWhiteCard.getChildren().addAll(internalScroll, footerRow);
        blueContainer.getChildren().add(innerWhiteCard);
        
        // Memasukkan tag oranye dan kontainer biru ke wrapper utama
        detailWrapper.getChildren().addAll(lblCategoryTag, blueContainer);
        rightAreaContainer.getChildren().add(detailWrapper);

        mainLayout.getChildren().addAll(sidebar, rightAreaContainer);
        root.getChildren().add(mainLayout);

        Scene scene = new Scene(root, 1024, 720);
        try {
            scene.getStylesheets().add(getClass().getResource("/style/guest/intro.css").toExternalForm());
        } catch (Exception e) {}

        primaryStage.setTitle("Luminara - Detail Festival");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

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
            } else if (text.equals("Festival")) {
                new FestivalPage().start(stage);
            }
        });

        return btn;
    }

    private VBox createMetaItem(String label, String value) {
        VBox metaBox = new VBox(3);
        Label lblLabel = new Label(label);
        lblLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #002B49;");
        Label lblValue = new Label(value);
        lblValue.setStyle("-fx-font-size: 12px; -fx-text-fill: #555555;");
        metaBox.getChildren().addAll(lblLabel, lblValue);
        return metaBox;
    }
}
