package gradleproject;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SidebarHelper {

    public static VBox createSidebar(String activeMenu, Stage stage) {
        VBox sidebar = new VBox(15);
        sidebar.getStyleClass().add("sidebar");
        sidebar.setPrefWidth(280);
        sidebar.setMinWidth(280);
        sidebar.setPadding(new Insets(35, 20, 35, 25));
        // ==================== LOGO BAGIAN BAWAH SIDEBAR ====================
        ImageView bottomLogoView = new ImageView();
        try {
        Image logoImg = new Image(SidebarHelper.class.getResourceAsStream("/aset/gambarLuminara/logo.png")); // Sesuaikan nama file logomu
        bottomLogoView.setImage(logoImg);
        bottomLogoView.setFitWidth(55);  // Ukuran proporsional sesuai maket
        bottomLogoView.setFitHeight(55);
        bottomLogoView.setPreserveRatio(true);
        } catch (Exception e) {
        System.out.println("Logo bawah tidak ditemukan.");
        }

// Pembungkus Logo menggunakan HBox agar posisinya bisa digeser ke kanan
HBox bottomLogoContainer = new HBox(bottomLogoView);
bottomLogoContainer.setAlignment(Pos.CENTER_LEFT);

/* * KUNCI PERGESERAN: Menggunakan Insets(Atas, Kanan, Bawah, Kiri)
 * Kita beri jarak 35px dari kiri agar sejajar dengan ikon menu, 
 * dan 20px dari bawah agar tidak terlalu tenggelam di pojok screen.
 */
VBox.setMargin(bottomLogoContainer, new Insets(20, 0, 20, 35)); 

// Pasang container logo ke dalam layout utama sidebar
sidebar.getChildren().add(bottomLogoContainer);

        // Header Logo Aplikasi
        VBox sidebarHeader = new VBox(5);
        ImageView imgLogo = new ImageView();
        try {
            imgLogo.setImage(new Image(SidebarHelper.class.getResourceAsStream("/aset/gambarLuminara/luminara-textWhite.png")));
            imgLogo.setFitWidth(140);
            imgLogo.setPreserveRatio(true);
        } catch (Exception e) {}
        
        Label lblRole = new Label("Pengunjung");
        lblRole.getStyleClass().add("sidebar-role-text");
        sidebarHeader.getChildren().addAll(imgLogo, lblRole);
        sidebarHeader.setPadding(new Insets(0, 0, 15, 0));

        // Kumpulan Tombol Navigasi
        VBox menuBox = new VBox(8);
        menuBox.getChildren().addAll(
            createMenuButton("Beranda", activeMenu.equals("Beranda"), stage),
            createMenuButton("Budaya", activeMenu.equals("Budaya"), stage),
            createMenuButton("Festival", activeMenu.equals("Festival"), stage),
            createMenuButton("Lokakarya", activeMenu.equals("Lokakarya"), stage),
            createMenuButton("Musik", activeMenu.equals("Musik"), stage),
            createMenuButton("Ulasan", activeMenu.equals("Ulasan"), stage),
            createMenuButton("Mulai Eksplorasi", activeMenu.equals("Mulai Eksplorasi"), stage)
        );

        Region sidebarSpacer = new Region();
        VBox.setVgrow(sidebarSpacer, Priority.ALWAYS);

        // Logo Mini Bunga Bagian Bawah
        ImageView imgMiniLogo = new ImageView();
        try {
            imgMiniLogo.setImage(new Image(SidebarHelper.class.getResourceAsStream("/aset/gambarLuminara/luminara-logoWhite.png")));
            imgMiniLogo.setFitWidth(50);
            imgMiniLogo.setPreserveRatio(true);
        } catch (Exception e) {}
        HBox miniLogoContainer = new HBox(imgMiniLogo);
        miniLogoContainer.setAlignment(Pos.BOTTOM_LEFT);
        miniLogoContainer.setPadding(new Insets(0, 0, 0, 35));

        sidebar.getChildren().addAll(sidebarHeader, menuBox, sidebarSpacer, miniLogoContainer);
        return sidebar;
    }

    private static Button createMenuButton(String text, boolean isActive, Stage stage) {
        Button btn = new Button(text);
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setAlignment(Pos.CENTER_LEFT);
        btn.setPadding(new Insets(12, 15, 12, 25)); 
        btn.setCursor(javafx.scene.Cursor.HAND);
        btn.setGraphicTextGap(15); 

        // 👉 PERBAIKAN: Pemetaan langsung menggunakan full path asset kustom sesuai instruksi
        String iconPath = "";
        if (isActive) {
        // 👉 JIKA MENU AKTIF: Gunakan ikon versi berwarna (Biru / Gambar aktif)
            switch (text) {
                case "Beranda":           iconPath = "/aset/iconLuminara/branda-biru.png"; break;
                case "Budaya":            iconPath = "/aset/iconLuminara/icon-budaya.png"; break;
                case "Festival":          iconPath = "/aset/iconLuminara/icon-fest.png"; break;
                case "Lokakarya":         iconPath = "/aset/iconLuminara/icon-workshop.png"; break;
                case "Musik":             iconPath = "/aset/iconLuminara/icon-musik.png"; break;
                case "Ulasan":            iconPath = "/aset/iconLuminara/icon-ulasan.png"; break; 
                case "Mulai Eksplorasi":  iconPath = "/aset/iconLuminara/eksplor-biru.png"; break;
            }
        } else {
            // 👉 JIKA MENU TIDAK AKTIF: Gunakan ikon versi putih biasa
            switch (text) {
                case "Beranda":           iconPath = "/aset/iconLuminara/icon-beranda.png"; break;
                case "Budaya":            iconPath = "/aset/iconLuminara/budaya-puith.png"; break;
                case "Festival":          iconPath = "/aset/iconLuminara/fest-putih.png"; break;
                case "Lokakarya":         iconPath = "/aset/iconLuminara/lokakarya-putih.png"; break;
                case "Musik":             iconPath = "/aset/iconLuminara/musik-putih.png"; break;
                case "Ulasan":            iconPath = "/aset/iconLuminara/icon-tiket.png"; break;
                case "Mulai Eksplorasi":  iconPath = "/aset/iconLuminara/icon-eksplorasi.png"; break;
            }
        }

        // Memuat gambar ikon secara aman menggunakan ImageView JavaFX
        try {
            javafx.scene.image.Image img = new javafx.scene.image.Image(
                SidebarHelper.class.getResourceAsStream(iconPath)
            );
            javafx.scene.image.ImageView iconView = new javafx.scene.image.ImageView(img);
            
            iconView.setFitWidth(18);  
            iconView.setFitHeight(18); 
            iconView.setPreserveRatio(true);
            iconView.setSmooth(true);
            
            btn.setGraphic(iconView); 
        } catch (Exception e) {
            System.out.println("⚠️ Gagal memuat ikon sidebar: " + iconPath);
        }
        
        // Memberikan style CSS berdasarkan status halaman aktif
        if (isActive) {
            btn.getStyleClass().add("sidebar-btn-active");
        } else {
            btn.getStyleClass().add("sidebar-btn");
        }

        // Logika perpindahan halaman saat menu samping diklik
        btn.setOnAction(e -> {
            switch (text) {
                case "Beranda":           new DashboardPage().start(stage); break;
                case "Budaya":            new BudayaPage().start(stage); break;
                case "Festival":          new FestivalPage().start(stage); break;
                case "Lokakarya":         new LokakaryaPage().start(stage); break;
                case "Musik":             new MusikPage().start(stage); break;
                case "Ulasan":            new UlasanPage().start(stage); break;
                case "Mulai Eksplorasi":  new MulaiEksplorasiPage().start(stage); break;
            }
        });

        return btn;
    }
}