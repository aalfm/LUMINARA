package gradleproject;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class DashboardPage {

    public void start(Stage primaryStage) {
        StackPane root = new StackPane();
        root.getStyleClass().add("dashboard-root");

        // Latar Belakang Gambar Utama Aplikasi (Pemandangan Perahu Tradisional)
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

        // 1. SIDEBAR KIRI (Menggunakan Helper Tunggal)
        VBox sidebar = SidebarHelper.createSidebar("Tentang Kami", primaryStage);

        // 2. AREA UTAMA KANAN (KONTEN TRANSPARAN DENGAN JUDUL SELAMAT DATANG)
        VBox rightArea = new VBox(20);
        rightArea.setPadding(new Insets(40, 50, 40, 50));
        HBox.setHgrow(rightArea, Priority.ALWAYS);
        rightArea.setAlignment(Pos.TOP_LEFT);

        // Judul Besar "Selamat datang," di luar kotak putih
        VBox welcomeHeader = new VBox(5);
        Label lblWelcome = new Label("Selamat datang,");
        lblWelcome.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-text-fill: white;");
        Label lblSubWelcome = new Label("Jelajahi budaya lokal dengan pengalaman yang lebih interaktif");
        lblSubWelcome.setStyle("-fx-font-size: 14px; -fx-text-fill: #E2E8F0;");
        welcomeHeader.getChildren().addAll(lblWelcome, lblSubWelcome);

        // ==================== 3. KARTU PUTIH UTAMA (CENTRAL CARD) ====================
        VBox whiteCard = new VBox(20);
        whiteCard.setPadding(new Insets(30, 35, 30, 35));
        whiteCard.setStyle("-fx-background-color: white; -fx-background-radius: 20px;");
        VBox.setVgrow(whiteCard, Priority.ALWAYS);

        // Judul "Tentang Kami"
        Label lblTentang = new Label("Tentang Kami");
        lblTentang.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #002B49;");

        // Deskripsi Platform
        Label lblDesc = new Label("Luminara adalah platform digital untuk menemukan event budaya, komunitas kreatif, dan pengalaman lokal di Kota Makassar secara lebih modern dan interaktif.");
        lblDesc.setWrapText(true);
        lblDesc.setStyle("-fx-font-size: 14px; -fx-text-fill: #002B49; -fx-line-spacing: 4px;");

        // Baris Baris Statistik dengan Ikon Ilustrasi Asli
        HBox statsRow = new HBox(40);
        statsRow.setAlignment(Pos.CENTER);
        statsRow.setPadding(new Insets(15, 0, 15, 0));

        VBox stat1 = createStatBox("20+ Kegiatan", "dashboard.png"); 
        VBox stat2 = createStatBox("10+ Komunitas Lokal", "lokakarya.png");
        VBox stat3 = createStatBox("200+ Peserta", "budaya.png");
        statsRow.getChildren().addAll(stat1, stat2, stat3);

        // Judul Bagian Kategori
        Label lblKategoriTitle = new Label("Kategori");
        lblKategoriTitle.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #002B49;");

        // =====================================================================
        // 👉 PERBAIKAN UTAMA: MERAKIT 4 KATEGORI MENJADI SATU KAPSUL UTUH
        // =====================================================================
        HBox categoriesRow = new HBox(0); // Set spacing menjadi 0 agar menempel rapat tanpa celah
        categoriesRow.setAlignment(Pos.CENTER);

        // Lebar tiap komponen disesuaikan (185px) agar total panjang pas menutup area tengah (185 * 4 = 740px)
        StackPane catBudaya = createCategoryCard("Budaya", "/aset/gambarLuminara/Kategori.png", primaryStage);
        StackPane catFestival = createCategoryCard("Festival", "/aset/gambarLuminara/fest-story.png", primaryStage);
        StackPane catLokakarya = createCategoryCard("Lokakarya", "/aset/gambarLuminara/fest-costume2.png", primaryStage);
        StackPane catMusik = createCategoryCard("Musik", "/aset/gambarLuminara/fest-costume3.png", primaryStage);
        
        categoriesRow.getChildren().addAll(catBudaya, catFestival, catLokakarya, catMusik);

        // 🎯 KUNCI UTAMA: Potong ujung luar kontainer induk HBox agar berbentuk satu kapsul melengkung utuh
        Rectangle rowClip = new Rectangle(740, 100); // Ukuran total baris (lebar 740, tinggi 100)
        rowClip.setArcWidth(20);                     // Kelengkungan sudut luar kiri-kanan
        rowClip.setArcHeight(20);
        categoriesRow.setClip(rowClip);

        // Masukkan komponen ke dalam kartu putih
        whiteCard.getChildren().addAll(lblTentang, lblDesc, statsRow, lblKategoriTitle, categoriesRow);

        // Satukan Judul Selamat Datang dan Kartu Putih ke Area Kanan
        rightArea.getChildren().addAll(welcomeHeader, whiteCard);

        // Gabungkan Sidebar Kiri dengan Area Konten Kanan
        mainLayout.getChildren().addAll(sidebar, rightArea);
        root.getChildren().add(mainLayout);

        Scene scene = new Scene(root, 1024, 720);
        try {
            scene.getStylesheets().add(getClass().getResource("/style/guest/intro.css").toExternalForm());
        } catch (Exception e) {}

        primaryStage.setTitle("Luminara - Dashboard Pengunjung");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Fungsi Pembantu Pembuat Item Statistik Beserta Ikon Gambar
    private VBox createStatBox(String text, String iconFile) {
        VBox box = new VBox(8);
        box.setAlignment(Pos.CENTER);
        box.setPrefWidth(160);

        try {
            Image img = new Image(getClass().getResourceAsStream("/aset/ikon/" + iconFile));
            ImageView iconView = new ImageView(img);
            iconView.setFitWidth(40);
            iconView.setFitHeight(40);
            iconView.setPreserveRatio(true);
            box.getChildren().add(iconView);
        } catch (Exception e) {
            StackPane placeholder = new StackPane();
            placeholder.setPrefSize(40, 40);
            placeholder.setStyle("-fx-background-color: #002B49; -fx-background-radius: 5px;");
            box.getChildren().add(placeholder);
        }

        Label lblText = new Label(text);
        lblText.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-text-fill: #002B49;");
        box.getChildren().add(lblText);

        return box;
    }

    // 👉 PERBAIKAN KEDUA: Rombak fungsi cetak kartu agar menyatu rapi tanpa sekat melengkung di dalam
    private StackPane createCategoryCard(String title, String imagePath, Stage stage) {
        StackPane card = new StackPane();
        card.setPrefSize(185, 100); // Tinggi dinaikkan ke 100px agar tulisan lebih lega di tengah
        card.setCursor(javafx.scene.Cursor.HAND);

        // 1. Komponen Gambar Latar Belakang
        try {
            Image img = new Image(getClass().getResourceAsStream(imagePath));
            ImageView imgView = new ImageView(img);
            imgView.setFitWidth(185);
            imgView.setFitHeight(100);
            imgView.setPreserveRatio(false); // Di-false agar gambar memenuhi boks secara penuh dan rapat
            
            // ❌ Potongan clip individu dihapus agar antar gambar bisa tersambung rata tanpa sekat melengkung internal
            
            card.getChildren().add(imgView);
        } catch (Exception e) {
            card.setStyle("-fx-background-color: #002B49;");
        }

        // 2. Lapisan Biru Transparan (Overlay) Supaya Teks Putih Mudah Dibaca
        StackPane overlay = new StackPane();
        overlay.setStyle("-fx-background-color: rgba(0, 43, 73, 0.5);");
        card.getChildren().add(overlay);

        // 3. Komponen Teks Judul Kategori (Ditampilkan di atas lapisan overlay)
        Label lblTitle = new Label(title);
        lblTitle.setStyle("-fx-font-family: 'Poppins'; -fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: white;");
        card.getChildren().add(lblTitle); // 🎯 FIX BUG: Sekarang teks sudah dimasukkan ke dalam layout card

        // Aksi Navigasi Klik Halaman Kategori
        card.setOnMouseClicked(e -> {
            if (title.equals("Budaya")) new BudayaPage().start(stage);
            else if (title.equals("Festival")) new FestivalPage().start(stage);
            else if (title.equals("Lokakarya")) new LokakaryaPage().start(stage);
            else if (title.equals("Musik")) new MusikPage().start(stage);
        });

        return card;
    }
}