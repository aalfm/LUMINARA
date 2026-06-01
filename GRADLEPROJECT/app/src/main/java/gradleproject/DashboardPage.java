package gradleproject;

// IMPORT UTAMA UNTUK FIX ERROR GARIS MERAH
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;       // Pastikan ini ada
import javafx.scene.image.ImageView;   // Pastikan ini ada
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

        // Latar Belakang Gambar Utama Aplikasi
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

        // 1. SIDEBAR KIRI
        VBox sidebar = SidebarHelper.createSidebar("Tentang Kami", primaryStage);

        // 2. AREA UTAMA KANAN
        VBox rightArea = new VBox(20);
        rightArea.setPadding(new Insets(40, 50, 40, 50));
        HBox.setHgrow(rightArea, Priority.ALWAYS);
        rightArea.setAlignment(Pos.TOP_LEFT);

        // Judul Besar "Selamat datang,"
        VBox welcomeHeader = new VBox(5);
        Label lblWelcome = new Label("Selamat datang,");
        lblWelcome.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-text-fill: white;");
        Label lblSubWelcome = new Label("Jelajahi budaya lokal dengan pengalaman yang lebih interaktif");
        lblSubWelcome.setStyle("-fx-font-size: 14px; -fx-text-fill: #E2E8F0;");
        welcomeHeader.getChildren().addAll(lblWelcome, lblSubWelcome);

        // ==================== 3. KARTU PUTIH UTAMA ====================
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

        // Baris Statistik
        HBox statsRow = new HBox(40);
        statsRow.setAlignment(Pos.CENTER);
        statsRow.setPadding(new Insets(15, 0, 15, 0));

        VBox stat1 = createStatBox("20+ Kegiatan", "/aset/iconLuminara/icon-guest2.png"); 
        VBox stat2 = createStatBox("10+ Komunitas Lokal", "/aset/iconLuminara/icon-guest.png");
        VBox stat3 = createStatBox("200+ Peserta", "/aset/iconLuminara/icon-guest1.png");
        statsRow.getChildren().addAll(stat1, stat2, stat3);

        // Judul Bagian Kategori
        Label lblKategoriTitle = new Label("Kategori");
        lblKategoriTitle.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #002B49;");

        // =====================================================================
        // 👉 FIX KATEGORI: MEMASANG 1 ASSET GAMBAR GABUNGAN PADA INDUK HBOX
        // =====================================================================
        HBox categoriesRow = new HBox(0); // Spacing 0 agar menempel sempurna
        categoriesRow.setAlignment(Pos.CENTER);
        categoriesRow.setPrefSize(740, 110); 
        categoriesRow.setMinSize(740, 110);
        categoriesRow.setMaxSize(740, 110);

        // Pasang gambar background HBox induk
        try {
            String stripPath = getClass().getResource("/aset/gambarLuminara/Kategori.png").toExternalForm();
            categoriesRow.setStyle("-fx-background-image: url('" + stripPath + "'); " +
                                  "-fx-background-repeat: no-repeat; " +
                                  "-fx-background-size: cover; " +
                                  "-fx-background-position: center center;");
        } catch (Exception e) {
            // Fallback jika path salah atau asset belum terbaca gradle
            categoriesRow.setStyle("-fx-background-color: #002B49;");
            System.out.println("Gagal memuat background gabungan Kategori.png");
        }

        // Potong ujung luar kiri-kanan HBox agar membentuk satu kapsul melengkung utuh
        Rectangle rowClip = new Rectangle(740, 110); 
        rowClip.setArcWidth(25); 
        rowClip.setArcHeight(25);
        categoriesRow.setClip(rowClip);

        // Bagi lebar 740px menjadi 4 bagian kartu transparan (740 / 4 = 185px)
        StackPane catBudaya = createTransparentCategoryCard("Budaya", primaryStage);
        StackPane catFestival = createTransparentCategoryCard("Festival", primaryStage);
        StackPane catLokakarya = createTransparentCategoryCard("Lokakarya", primaryStage);
        StackPane catMusik = createTransparentCategoryCard("Musik", primaryStage);
        
        categoriesRow.getChildren().addAll(catBudaya, catFestival, catLokakarya, catMusik);

        // Masukkan semua komponen ke dalam boks putih utama
        whiteCard.getChildren().addAll(lblTentang, lblDesc, statsRow, lblKategoriTitle, categoriesRow);

        rightArea.getChildren().addAll(welcomeHeader, whiteCard);
        mainLayout.getChildren().addAll(sidebar, rightArea);
        root.getChildren().add(mainLayout);

        Scene scene = new Scene(root, 1280, 650);
        try {
            scene.getStylesheets().add(getClass().getResource("/style/guest/intro.css").toExternalForm());
        } catch (Exception e) {}

        primaryStage.setTitle("Luminara - Dashboard Pengunjung");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

   // Fungsi Statistik yang sudah diperbaiki path-nya agar fleksibel
    private VBox createStatBox(String text, String iconPath) {
        VBox box = new VBox(8);
        box.setAlignment(Pos.CENTER);
        box.setPrefWidth(160);

        try {
            // LANGSUNG GUNAKAN iconPath tanpa ditambah-tambah teks "/aset/ikon/" lagi
            java.io.InputStream is = getClass().getResourceAsStream(iconPath);
            if (is != null) {
                Image img = new Image(is);
                ImageView iconView = new ImageView(img);
                iconView.setFitWidth(40);
                iconView.setFitHeight(40);
                iconView.setPreserveRatio(true);
                box.getChildren().add(iconView);
            } else {
                throw new Exception("File tidak ditemukan di path: " + iconPath);
            }
        } catch (Exception e) {
            // Jika gagal, kotak biru placeholder ini yang akan muncul
            StackPane placeholder = new StackPane();
            placeholder.setPrefSize(40, 40);
            placeholder.setStyle("-fx-background-color: #002B49; -fx-background-radius: 5px;");
            box.getChildren().add(placeholder);
            System.out.println("⚠️ " + e.getMessage());
        }

        Label lblText = new Label(text);
        lblText.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-text-fill: #002B49;");
        box.getChildren().add(lblText);

        return box;
    }

    // Pembuat kartu transparan agar gambar panjang dari boks induk terlihat langsung
    private StackPane createTransparentCategoryCard(String title, Stage stage) {
        StackPane card = new StackPane();
        card.setPrefSize(185, 110); 
        card.setMinSize(185, 110);
        card.setMaxSize(185, 110);
        card.setCursor(javafx.scene.Cursor.HAND);

        // Lapisan transparan gelap (overlay) agar teks putih gampang dibaca di atas gambar
        StackPane overlay = new StackPane();
        overlay.setStyle("-fx-background-color: rgba(0, 43, 73, 0.45);"); 
        card.getChildren().add(overlay);

        // Efek Hover: Menyorot boks kategori yang sedang didekati kursor
        card.setOnMouseEntered(e -> overlay.setStyle("-fx-background-color: rgba(0, 43, 73, 0.2);"));
        card.setOnMouseExited(e -> overlay.setStyle("-fx-background-color: rgba(0, 43, 73, 0.45);"));

        // Judul Kategori
        Label lblTitle = new Label(title);
        lblTitle.setStyle("-fx-font-family: 'Poppins'; -fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: white;");
        card.getChildren().add(lblTitle);

        // Navigasi halaman saat diklik
        card.setOnMouseClicked(e -> {
            if (title.equals("Budaya")) new BudayaPage().start(stage);
            else if (title.equals("Festival")) new FestivalPage().start(stage);
            else if (title.equals("Lokakarya")) new LokakaryaPage().start(stage);
            else if (title.equals("Musik")) new MusikPage().start(stage);
        });

        return card;
    }
}