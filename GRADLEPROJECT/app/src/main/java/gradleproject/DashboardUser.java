package gradleproject;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class DashboardUser {

    private BorderPane root;
    private ScrollPane homeContent; 

    private HBox mnuBeranda;
    private HBox mnuKegiatan;
    private HBox mnuProfil;

    private VBox submenuBerandaBox;
    private HBox mnuTiketSaya, mnuRekomendasiKegiatan, mnuSorotanBudaya;

    private HBox mnuKategori;
    private HBox mnuBiaya;
    private VBox submenuKegiatanBox;

    private HBox mnuEditProfil;
    private HBox mnuRiwayatKegiatan;
    private HBox mnuKeluar;
    private VBox submenuProfilBox;

    private static DashboardUser instance;

    // Jalur navigasi menuju detail tiket saya penuh
    public void pindahKeTiketSaya() {
        TiketSaya halTiket = new TiketSaya();
        root.setCenter(halTiket.getView());
        
        // Otomatis sorot submenu Tiket Saya menjadi oranye aktif di sidebar
        setMenuSelection(mnuTiketSaya);
    }

    // Jalur navigasi menuju daftar rekomendasi kegiatan grid penuh
    public void pindahKeRekomendasiKegiatanPenuh() {
        RekomendasiKegiatan halRekomendasi = new RekomendasiKegiatan();
        root.setCenter(halRekomendasi.getView());
        
        // Tandai otomatis submenu Rekomendasi Kegiatan menjadi aktif di sidebar kiri
        setMenuSelection(mnuRekomendasiKegiatan);
    }

    // Jalur navigasi untuk memuat tampilan Detail Deskripsi Rekomendasi Event Budaya
    public void pindahKeDetailRekomendasi() {
        DetailRekomendasi halDetail = new DetailRekomendasi();
        root.setCenter(halDetail.getView());
        
        // Tetap nyalakan warna aktif menu Rekomendasi Kegiatan di sidebar kiri
        setMenuSelection(mnuRekomendasiKegiatan);
    }

    // Jalur rute untuk membuka halaman galeri Sorotan Budaya penuh di tengah screen
    public void pindahKeSorotanBudayaPenuh() {
        SorotanBudaya halSorotan = new SorotanBudaya();
        root.setCenter(halSorotan.getView());
        
        // Nyalakan warna aktif menu Sorotan Budaya di sidebar kiri
        setMenuSelection(mnuSorotanBudaya);
    }

    // Jalur rute untuk memuat halaman Detail Sorotan Budaya penuh di area tengah screen
    public void pindahKeDetailSorotan() {
        DetailSorotan halDetailSorotan = new DetailSorotan();
        root.setCenter(halDetailSorotan.getView());
        
        // Tetap kunci warna aktif menu Sorotan Budaya di sidebar kiri
        setMenuSelection(mnuSorotanBudaya);
    }

    public void pindahKeKategoriUser() {
        KategoriUser halKategori = new KategoriUser();
        root.setCenter(halKategori.getView());
        setMenuSelection(mnuKategori); // Sorot submenu Kategori menjadi oranye aktif
    }

    public void pindahKeDetailKategori(String namaKategori) {
        DetailKategori halDetailKat = new DetailKategori(namaKategori);
        root.setCenter(halDetailKat.getView());
        setMenuSelection(mnuKategori); // Tetap kunci sorotan aktif di submenu Kategori
    }

    public void pindahKeBiayaUser() {
        BiayaUser halBiaya = new BiayaUser();
        root.setCenter(halBiaya.getView());
        setMenuSelection(mnuBiaya); // Mengunci sorotan aktif pada submenu Biaya
    }

    // Jalur rute untuk membuka halaman Detail dari menu Biaya Kegiatan
    public void pindahKeDetailBiaya(String statusBiaya) {
        DetailBiaya halDetailBiaya = new DetailBiaya(statusBiaya);
        root.setCenter(halDetailBiaya.getView());
        setMenuSelection(mnuBiaya); // Tetap mengunci sorotan aktif pada submenu Biaya
    }

    public void pindahKePesanTiket() {
        PesanTiketUser halPesan = new PesanTiketUser();
        root.setCenter(halPesan.getView());
        setMenuSelection(null); // 🎯 Set null agar menu sidebar meredup semua saat user fokus mengisi form
    }

    // Jalur rute untuk memuat Halaman Input Nominal Konfirmasi Pembayaran
    public void pindahKePembayaran(String totalHarga) {
        PembayaranUser halBayar = new PembayaranUser(totalHarga);
        root.setCenter(halBayar.getView());
        setMenuSelection(null); // Tetap padamkan sidebar agar pengguna fokus bertransaksi
    }

    public void pindahKeBeranda() {
        // 1. Kembalikan konten tengah ke halaman beranda awal
        root.setCenter(homeContent);
        
        // 2. Pastikan submenu Beranda terbuka rapi kembali saat pulang ke beranda
        if (submenuBerandaBox != null) {
            submenuBerandaBox.setVisible(true);
            submenuBerandaBox.setManaged(true);
        }
        
        // 3. Tutup otomatis submenu Kegiatan agar sidebar tidak kepenuhan
        if (submenuKegiatanBox != null) {
            submenuKegiatanBox.setVisible(false);
            submenuKegiatanBox.setManaged(false);
        }
        
        // 4. Nyalakan kembali kapsul sorotan aktif pada menu Beranda
        setMenuSelection(mnuBeranda);
    }

    // Jalur rute pembantu untuk memuat halaman Riwayat Kegiatan milik pengguna
    public void pindahKeRiwayatKegiatan() {
        RiwayatKegiatanUser halRiwayat = new RiwayatKegiatanUser();
        root.setCenter(halRiwayat.getView());
        setMenuSelection(mnuRiwayatKegiatan); // Nyalakan warna aktif jingga pada submenu riwayat
    }

    // Jalur rute untuk memuat Halaman Formulir Pengisian Ulasan Kegiatan
    public void pindahKeUlasan(String namaKegiatan) {
        UlasanUser halUlasan = new UlasanUser(namaKegiatan);
        root.setCenter(halUlasan.getView());
        setMenuSelection(mnuRiwayatKegiatan); // Tetap kunci sorotan menyala jingga di submenu Riwayat Kegiatan
    }

    public DashboardUser() {
        instance = this; 
        root = new BorderPane();
        
        // Membuka halaman BerandaUser bawaan saat pertama kali aplikasi dibuka
        BerandaUser halBeranda = new BerandaUser();
        homeContent = (ScrollPane) halBeranda.getView();
        root.setCenter(homeContent);

        createNavbar(); 
    }

    public static DashboardUser getInstance() {
        return instance;
    }

    private void createNavbar() {
        VBox navbar = new VBox(15); 
        navbar.setPadding(new Insets(40, 20, 30, 20));
        navbar.setAlignment(Pos.TOP_LEFT);
        navbar.setStyle("-fx-background-color: #0A3B6C; -fx-background-radius: 0 30 30 0;");

        navbar.setPrefWidth(260); 
        navbar.setMinWidth(260);
        navbar.setMaxWidth(260);

        ImageView logoView = new ImageView();
        try {
            Image logoImage = new Image(getClass().getResourceAsStream("/aset/gambarLuminara/luminara-textWhite.png"));
            logoView.setImage(logoImage);
            logoView.setFitWidth(125); 
            logoView.setPreserveRatio(true);
        } catch (Exception e) {
            System.out.println("⚠️ Gagal memuat logo utama!");
        }

        VBox brandBox = new VBox(5);
        brandBox.setAlignment(Pos.TOP_LEFT);
        brandBox.setPadding(new Insets(0, 0, 15, 0)); 

        Label lblRole = new Label("Pengguna");
        lblRole.setStyle("-fx-text-fill: #A0A9B5; -fx-font-family: 'Poppins'; -fx-font-size: 12px;");
        brandBox.getChildren().addAll(logoView, lblRole);

        VBox menuBox = new VBox(5); 
        
        // Pembuatan Menu Utama Sesuai Gambar Mockup User
        mnuBeranda = createMenuItem("/aset/iconLuminara/icon-beranda.png", "/aset/iconLuminara/branda-biru.png", "Beranda", true); 
        mnuKegiatan = createMenuItem("/aset/iconLuminara/acara-putih.png", "/aset/iconLuminara/icon-manajemen-acara.png", "Kegiatan", false);
        mnuProfil = createMenuItem("/aset/iconLuminara/icon-user.png", "/aset/iconLuminara/profil-biru.png", "Profil", false);

        // Pembuatan Submenu Beranda (Tiket Saya, Rekomendasi, Sorotan)
        submenuBerandaBox = new VBox(3);
        mnuTiketSaya = createSubmenuItem("/aset/iconLuminara/icon-tiket.png", "/aset/iconLuminara/tiket-biru.png", "Tiket Saya");
        mnuRekomendasiKegiatan = createSubmenuItem("/aset/iconLuminara/acara-putih.png", "/aset/iconLuminara/icon-manajemen-acara.png", "Rekomendasi");
        mnuSorotanBudaya = createSubmenuItem("/aset/iconLuminara/budaya-puith.png", "/aset/iconLuminara/icon-budaya.png", "Sorotan");
        
        submenuBerandaBox.getChildren().addAll(mnuTiketSaya, mnuRekomendasiKegiatan, mnuSorotanBudaya);
        
        // Set default terbuka rapi seperti pada gambar mockup
        submenuBerandaBox.setVisible(true);
        submenuBerandaBox.setManaged(true);

        // Pengaturan Aksi Navigasi Klik
        mnuBeranda.setOnMouseClicked(event -> {
            boolean isExpanded = submenuBerandaBox.isVisible();
            submenuBerandaBox.setVisible(!isExpanded);
            submenuBerandaBox.setManaged(!isExpanded);
            
            root.setCenter(homeContent); 
            setMenuSelection(mnuBeranda); 
        });
        mnuBeranda.setCursor(javafx.scene.Cursor.HAND);

        mnuTiketSaya.setOnMouseClicked(event -> {
            pindahKeTiketSaya(); // Panggil fungsi jembatan pemindah halaman tengah
        }); 

        mnuRekomendasiKegiatan.setOnMouseClicked(event -> {
            pindahKeRekomendasiKegiatanPenuh();
        });

        mnuSorotanBudaya.setOnMouseClicked(event -> {
            pindahKeSorotanBudayaPenuh();
        });

        // Pembuatan Submenu Kegiatan (Kategori & Biaya)
        submenuKegiatanBox = new VBox(3);
        mnuKategori = createSubmenuItem("/aset/iconLuminara/kategori-putih.png", "/aset/iconLuminara/kategori-biru.png", "Kategori");
        mnuBiaya = createSubmenuItem("/aset/iconLuminara/biaya-putih.png", "/aset/iconLuminara/biaya-biru.png", "Biaya");
        
        submenuKegiatanBox.getChildren().addAll(mnuKategori, mnuBiaya);
        submenuKegiatanBox.setVisible(false); // Default tertutup aman
        submenuKegiatanBox.setManaged(false);

        // Atur Aksi Klik pada Menu Utama Kegiatan
        mnuKegiatan.setOnMouseClicked(event -> {
            boolean isExpanded = submenuKegiatanBox.isVisible();
            submenuKegiatanBox.setVisible(!isExpanded);
            submenuKegiatanBox.setManaged(!isExpanded);
            
            // Tutup submenu beranda jika menu kegiatan dibuka
            if (submenuBerandaBox != null) { submenuBerandaBox.setVisible(false); submenuBerandaBox.setManaged(false); }
            
            // Tampilkan halaman utama KegiatanUser di tengah screen
            KegiatanUser halKegiatan = new KegiatanUser();
            root.setCenter(halKegiatan.getView());
            setMenuSelection(mnuKegiatan);
        });
        mnuKegiatan.setCursor(javafx.scene.Cursor.HAND);

        // Atur Aksi Klik pada Submenu Anak
        mnuKategori.setOnMouseClicked(event -> {
            pindahKeKategoriUser();
        });
        mnuKategori.setCursor(javafx.scene.Cursor.HAND);

        mnuBiaya.setOnMouseClicked(event -> {
            pindahKeBiayaUser();
        });
        mnuBiaya.setCursor(javafx.scene.Cursor.HAND);

        // Inisialisasi Submenu Bersarang khusus Profil
        submenuProfilBox = new VBox(3);
        mnuEditProfil = createSubmenuItem("/aset/iconLuminara/tiket-putih.png", "/aset/iconLuminara/tiket-biru.png", "Edit Profil");
        mnuRiwayatKegiatan = createSubmenuItem("/aset/iconLuminara/rekomendasi-putih.png", "/aset/iconLuminara/rekomendasi-biru.png", "Riwayat Kegiatan");
        mnuKeluar = createSubmenuItem("/aset/iconLuminara/sorotan-putih.png", "/aset/iconLuminara/sorotan-biru.png", "Keluar");
        submenuProfilBox.getChildren().addAll(mnuEditProfil, mnuRiwayatKegiatan, mnuKeluar);
        submenuProfilBox.setVisible(false); // Default tertutup aman di awal program
        submenuProfilBox.setManaged(false);

        // Atur Aksi Klik Menu Utama Profil
        // Saat menu utama Profil diklik -> Amankan menjadi FALSE (Tombol HILANG)
        mnuProfil.setOnMouseClicked(event -> {
            boolean isExpanded = submenuProfilBox.isVisible();
            submenuProfilBox.setVisible(!isExpanded);
            submenuProfilBox.setManaged(!isExpanded);
            
            if (submenuBerandaBox != null) { submenuBerandaBox.setVisible(false); submenuBerandaBox.setManaged(false); }
            if (submenuKegiatanBox != null) { submenuKegiatanBox.setVisible(false); submenuKegiatanBox.setManaged(false); }
            
            ProfilUser halProfil = new ProfilUser(false); // 🎯 FALSE = Sembunyikan tombol
            root.setCenter(halProfil.getView());
            setMenuSelection(mnuProfil);
        });

        // Saat anak menu Edit Profil diklik -> Atur menjadi TRUE (Tombol MUNCUL)
        mnuEditProfil.setOnMouseClicked(event -> {
            ProfilUser halProfil = new ProfilUser(true); // 🎯 TRUE = Munculkan tombol simpan
            root.setCenter(halProfil.getView());
            setMenuSelection(mnuEditProfil);
        });
        
        mnuRiwayatKegiatan.setOnMouseClicked(event -> setMenuSelection(mnuRiwayatKegiatan));
        
        mnuKeluar.setOnMouseClicked(event -> {
            System.out.println("User berhasil keluar dari sistem Luminara.");
            // Skenario: Anda dapat menambahkan logika penutupan stage jendela login kembali di sini
        });

        // PENGAMAN ABSOLUT: Masukkan komponen ke dalam Box satu per satu (Anti-Null)
        if (mnuBeranda != null) menuBox.getChildren().add(mnuBeranda);
        if (submenuBerandaBox != null) menuBox.getChildren().add(submenuBerandaBox);

        if (mnuKegiatan != null) menuBox.getChildren().add(mnuKegiatan);
        if (submenuKegiatanBox != null) menuBox.getChildren().add(submenuKegiatanBox);

        if (mnuProfil != null) menuBox.getChildren().add(mnuProfil);
        if (submenuProfilBox != null) menuBox.getChildren().add(submenuProfilBox);

        // Penampung Logo Kembang Putih di Sudut Paling Bawah
        HBox bottomLogoBox = new HBox();
        bottomLogoBox.setAlignment(Pos.CENTER);
        ImageView smallLogoView = new ImageView();
        try {
            Image smallLogo = new Image(getClass().getResourceAsStream("/aset/gambarLuminara/luminara-logoWhite.png"));
            smallLogoView.setImage(smallLogo);
            smallLogoView.setFitWidth(45);
            smallLogoView.setPreserveRatio(true);
        } catch (Exception e) { }
        bottomLogoBox.getChildren().add(smallLogoView);

        ScrollPane scrollMenu = new ScrollPane(menuBox);
        scrollMenu.setFitToWidth(true);
        scrollMenu.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollMenu.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollMenu.setStyle("-fx-background-color: transparent; -fx-background: transparent; -fx-border-color: transparent;");
        
        scrollMenu.setPrefWidth(220);
        scrollMenu.setMaxWidth(220);
        VBox.setVgrow(scrollMenu, Priority.ALWAYS);

        navbar.getChildren().addAll(brandBox, scrollMenu, bottomLogoBox);
        root.setLeft(navbar);
    }

    private void setMenuSelection(HBox selectedMenu) {
        // Tutup otomatis submenu Beranda jika menu utama lain yang diklik oleh user
        if (selectedMenu != mnuBeranda && selectedMenu != mnuTiketSaya && selectedMenu != mnuRekomendasiKegiatan && selectedMenu != mnuSorotanBudaya) {
            if (submenuBerandaBox != null) {
                submenuBerandaBox.setVisible(false);
                submenuBerandaBox.setManaged(false);
            }
        }

        if (selectedMenu != mnuKegiatan && selectedMenu != mnuKategori && selectedMenu != mnuBiaya) {
            if (submenuKegiatanBox != null) {
                submenuKegiatanBox.setVisible(false);
                submenuKegiatanBox.setManaged(false);
            }
        }

        HBox[] allMenus = {
            mnuBeranda, mnuKegiatan, mnuProfil,
            mnuTiketSaya, mnuRekomendasiKegiatan, mnuSorotanBudaya,
            mnuKategori, mnuBiaya,
            mnuEditProfil, mnuRiwayatKegiatan, mnuKeluar
        };
        
        for (HBox menu : allMenus) {
            if (menu == null) continue;
            
            // Setel ulang latar belakang menu tidak terpilih
            menu.setStyle("-fx-background-color: transparent; -fx-background-radius: 10;");
            
            if (!menu.getChildren().isEmpty() && menu.getChildren().get(0) instanceof StackPane) {
                StackPane container = (StackPane) menu.getChildren().get(0);
                if (!container.getChildren().isEmpty() && container.getChildren().get(0) instanceof ImageView) {
                    ImageView imgView = (ImageView) container.getChildren().get(0);
                    String pathPutih = (String) menu.getProperties().get("iconPutih");
                    if (pathPutih != null) {
                        try {
                            imgView.setImage(new Image(getClass().getResourceAsStream(pathPutih)));
                            int size = menu.getStyleClass().contains("submenu-item") ? 22 : 20;
                            imgView.setFitWidth(size);
                            imgView.setFitHeight(size);
                        } catch(Exception e) { }
                    }
                }
            }
            
            for (javafx.scene.Node node : menu.getChildren()) {
                if (node instanceof Label) {
                    Label lbl = (Label) node;
                    lbl.setStyle("-fx-text-fill: #FFFFFF; -fx-font-family: 'Poppins'; -fx-font-size: " + (menu.getStyleClass().contains("submenu-item") ? "12px;" : "13px;"));
                }
            }
        }
        
        // Memasang style aktif pada elemen yang dipilih
        if (selectedMenu != null) {
            if (selectedMenu.getStyleClass().contains("submenu-item")) {
                // Style Teks Menyala Oranye saat Submenu Aktif dipilih
                for (javafx.scene.Node node : selectedMenu.getChildren()) {
                    if (node instanceof Label) {
                        Label lbl = (Label) node;
                        lbl.setStyle("-fx-text-fill: #FF9800; -fx-font-family: 'Poppins'; -fx-font-weight: bold; -fx-font-size: 12px;");
                    }
                }
            } else {
                // Kapsul Abu Terang Aktif untuk Menu Utama yang dipilih
                selectedMenu.setStyle("-fx-background-color: #D3D9DE; -fx-background-radius: 10;");
                
                if (!selectedMenu.getChildren().isEmpty() && selectedMenu.getChildren().get(0) instanceof StackPane) {
                    StackPane container = (StackPane) selectedMenu.getChildren().get(0);
                    if (!container.getChildren().isEmpty() && container.getChildren().get(0) instanceof ImageView) {
                        ImageView imgView = (ImageView) container.getChildren().get(0);
                        String pathBiru = (String) selectedMenu.getProperties().get("iconBiru");
                        if (pathBiru != null) {
                            try {
                                imgView.setImage(new Image(getClass().getResourceAsStream(pathBiru)));
                                imgView.setFitWidth(20);
                                imgView.setFitHeight(20);
                            } catch(Exception e) { }
                        }
                    }
                }
                for (javafx.scene.Node node : selectedMenu.getChildren()) {
                    if (node instanceof Label) {
                        Label lbl = (Label) node;
                        lbl.setStyle("-fx-text-fill: #0A3B5C; -fx-font-family: 'Poppins'; -fx-font-weight: bold; -fx-font-size: 13px;");
                    }
                }
            }
        }

        if (selectedMenu != mnuProfil && selectedMenu != mnuEditProfil && selectedMenu != mnuRiwayatKegiatan && selectedMenu != mnuKeluar) {
            if (submenuProfilBox != null) {
                submenuProfilBox.setVisible(false);
                submenuProfilBox.setManaged(false);
            }
        }
    }

    

    private HBox createMenuItem(String iconPutihPath, String iconBiruPath, String text, boolean isActive) {
        HBox menuItem = new HBox(10); 
        menuItem.setAlignment(Pos.CENTER_LEFT);
        menuItem.setPadding(new Insets(0, 10, 0, 15));
        menuItem.setMinHeight(45);
        menuItem.setMaxHeight(45);

        menuItem.getProperties().put("iconPutih", iconPutihPath);
        menuItem.getProperties().put("iconBiru", iconBiruPath);

        ImageView iconView = new ImageView();
        try {
            String pathAwal = isActive ? iconBiruPath : iconPutihPath;
            Image iconImage = new Image(getClass().getResourceAsStream(pathAwal));
            iconView.setImage(iconImage);
            iconView.setFitWidth(20);  
            iconView.setFitHeight(20);
            iconView.setPreserveRatio(true);
        } catch (Exception e) { }

        StackPane iconContainer = new StackPane(iconView);
        iconContainer.setAlignment(Pos.CENTER); 
        iconContainer.setPrefWidth(30);         
        iconContainer.setMinWidth(30);
        iconContainer.setMaxWidth(30);

        Label lblText = new Label(text);
        lblText.setStyle("-fx-text-fill: " + (isActive ? "#0A3B5C;" : "#FFFFFF;") + " -fx-font-family: 'Poppins'; -fx-font-size: 13px;" + (isActive ? " -fx-font-weight: bold;" : ""));
        lblText.setMaxWidth(140); 

        menuItem.getChildren().addAll(iconContainer, lblText);
        
        if (isActive) {
            menuItem.setStyle("-fx-background-color: #D3D9DE; -fx-background-radius: 10;");
        } else {
            menuItem.setStyle("-fx-background-color: transparent;");
        }
        
        return menuItem;
    }

    private HBox createSubmenuItem(String iconPutihPath, String iconBiruPath, String text) {
        HBox subItem = new HBox(15); 
        subItem.setAlignment(Pos.CENTER_LEFT); 
        subItem.getStyleClass().add("submenu-item");
        subItem.setPadding(new Insets(8, 20, 8, 45)); 
        subItem.setCursor(javafx.scene.Cursor.HAND);

        subItem.setMinHeight(40);
        subItem.setMaxHeight(40);

        subItem.getProperties().put("iconPutih", iconPutihPath);
        subItem.getProperties().put("iconBiru", iconBiruPath);

        ImageView iconView = new ImageView();
        try {
            Image iconImage = new Image(getClass().getResourceAsStream(iconPutihPath));
            iconView.setImage(iconImage);
            iconView.setFitWidth(22); 
            iconView.setFitHeight(22);
            iconView.setPreserveRatio(true);
        } catch (Exception e) { }

        StackPane iconContainer = new StackPane(iconView);
        iconContainer.setAlignment(Pos.CENTER); 
        iconContainer.setPrefWidth(35);         
        iconContainer.setMinWidth(35);
        iconContainer.setMaxWidth(35);

        Label lblText = new Label(text);
        lblText.setStyle("-fx-text-fill: #FFFFFF; -fx-font-family: 'Poppins'; -fx-font-size: 12px;");
        lblText.setMaxWidth(180); 
        lblText.setAlignment(Pos.CENTER_LEFT); 

        subItem.getChildren().addAll(iconContainer, lblText);
        return subItem;
    }

    public Parent getView() {
        return root;
    }
}