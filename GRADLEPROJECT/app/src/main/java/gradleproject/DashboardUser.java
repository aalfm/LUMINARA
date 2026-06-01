package gradleproject;

import gradleproject.dao.UserDAO;
import gradleproject.models.Event;
import gradleproject.models.User;

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

    // =====================================================================
    // FUNGSI NAVIGASI MENU (JEMBATAN ANTAR HALAMAN)
    // =====================================================================

    public void pindahKeTiketSaya() {
        TiketSaya halTiket = new TiketSaya(); 
        root.setCenter(halTiket.getView());
    }

    public void pindahKeRekomendasiKegiatanPenuh() {
        RekomendasiKegiatan halRekomendasi = new RekomendasiKegiatan();
        root.setCenter(halRekomendasi.getView());
        setMenuSelection(mnuRekomendasiKegiatan);
    }

    public void pindahKeDetailRekomendasi(Event acara) {
        DetailRekomendasi detail = new DetailRekomendasi(acara);
        root.setCenter(detail.getView());
        setMenuSelection(mnuRekomendasiKegiatan);
    }

    public void pindahKeSorotanBudayaPenuh() {
        SorotanBudaya halSorotan = new SorotanBudaya();
        root.setCenter(halSorotan.getView());
        setMenuSelection(mnuSorotanBudaya);
    }

    public void pindahKeDetailSorotan() {
        DetailSorotan halDetailSorotan = new DetailSorotan();
        root.setCenter(halDetailSorotan.getView());
        setMenuSelection(mnuSorotanBudaya);
    }

    public void pindahKeKategoriUser() {
        KategoriUser halKategori = new KategoriUser();
        root.setCenter(halKategori.getView());
        setMenuSelection(mnuKategori);
    }

    public void pindahKeDetailKategori(String namaKategori) {
        DetailKategori halDetailKat = new DetailKategori(namaKategori);
        root.setCenter(halDetailKat.getView());
        setMenuSelection(mnuKategori); 
    }

    public void pindahKeBiayaUser() {
        BiayaUser halBiaya = new BiayaUser();
        root.setCenter(halBiaya.getView());
        setMenuSelection(mnuBiaya); 
    }

    public void pindahKeDetailBiaya(String statusBiaya) {
        DetailBiaya halDetailBiaya = new DetailBiaya(statusBiaya);
        root.setCenter(halDetailBiaya.getView());
        setMenuSelection(mnuBiaya); 
    }

    // ---------------------------------------------------------------------
    // JALUR PEMESANAN & PEMBAYARAN 
    // ---------------------------------------------------------------------

    public void pindahKePesanTiket() {
        Event acaraDummy = new Event(); 
        acaraDummy.setId(1);
        pindahKePesanTiket();
    }

    public void pindahKePesanTiket(String totalHarga) {
        Event acaraDummy = new Event(); 
        acaraDummy.setId(1);
        pindahKePesanTiket(acaraDummy, totalHarga);
    }

    public void pindahKePesanTiket(Event acara, String totalHarga) {
        PesanTiketUser halPesan = new PesanTiketUser(acara, totalHarga);
        root.setCenter(halPesan.getView());
        setMenuSelection(null); 
    }

    public void pindahKePembayaran(String totalHarga) {
        Event acaraDummy = new Event(); 
        acaraDummy.setId(1);
        pindahKePembayaran(acaraDummy, totalHarga);
    }

    public void pindahKePembayaran(Event acara, String totalHarga) {
        PembayaranUser halBayar = new PembayaranUser(acara, totalHarga);
        root.setCenter(halBayar.getView());
        setMenuSelection(null); 
    }
    // ---------------------------------------------------------------------

    public void pindahKeBeranda() {
        BerandaUser halBeranda = new BerandaUser();
        homeContent = (ScrollPane) halBeranda.getView();
        
        root.setCenter(homeContent);
        
        if (submenuBerandaBox != null) {
            submenuBerandaBox.setVisible(true);
            submenuBerandaBox.setManaged(true);
        }
        
        if (submenuKegiatanBox != null) {
            submenuKegiatanBox.setVisible(false);
            submenuKegiatanBox.setManaged(false);
        }
        
        setMenuSelection(mnuBeranda);
    }

    public void pindahKeRiwayatKegiatan() {
        RiwayatKegiatanUser halRiwayat = new RiwayatKegiatanUser();
        root.setCenter(halRiwayat.getView());
        setMenuSelection(mnuRiwayatKegiatan);
    }

    public void pindahKeUlasan(int eventId, String namaKegiatan) {
        UlasanUser halamanUlasan = new UlasanUser(eventId, namaKegiatan);
        root.setCenter(halamanUlasan.getView()); // Ganti mainBorderPane menjadi root
    }

    // =====================================================================
    // KONSTRUKTOR & PEMBUATAN UI DASHBOARD
    // =====================================================================

    public DashboardUser() {
        instance = this; 
        root = new BorderPane();
        
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

        // 👉 PERBAIKAN: Menarik Data Nama untuk disapa di Sidebar Dashboard
        String sapaanSidebar = "Pengguna";
        if (UserSession.getInstance() != null) {
            UserDAO userDAO = new UserDAO();
            User currentUser = userDAO.findById(UserSession.getInstance().getUserId());
            if (currentUser != null && currentUser.getUsername() != null) {
                // Ambil kata pertama saja agar rapi di sidebar (misal "Andi Alifah" jadi "Andi")
                sapaanSidebar = currentUser.getUsername().split(" ")[0]; 
            }
        }

        Label lblRole = new Label("Halo, " + sapaanSidebar);
        lblRole.setStyle("-fx-text-fill: #A0A9B5; -fx-font-family: 'Poppins'; -fx-font-size: 12px;");
        brandBox.getChildren().addAll(logoView, lblRole);

        VBox menuBox = new VBox(5); 
        
        mnuBeranda = createMenuItem("/aset/iconLuminara/icon-beranda.png", "/aset/iconLuminara/branda-biru.png", "Beranda", true); 
        mnuKegiatan = createMenuItem("/aset/iconLuminara/acara-putih.png", "/aset/iconLuminara/icon-manajemen-acara.png", "Kegiatan", false);
        mnuProfil = createMenuItem("/aset/iconLuminara/icon-user.png", "/aset/iconLuminara/profil-biru.png", "Profil", false);

        submenuBerandaBox = new VBox(3);
        mnuTiketSaya = createSubmenuItem("/aset/iconLuminara/icon-tiket.png", "/aset/iconLuminara/tiket-biru.png", "Tiket Saya");
        mnuRekomendasiKegiatan = createSubmenuItem("/aset/iconLuminara/acara-putih.png", "/aset/iconLuminara/icon-manajemen-acara.png", "Rekomendasi");
        mnuSorotanBudaya = createSubmenuItem("/aset/iconLuminara/budaya-puith.png", "/aset/iconLuminara/icon-budaya.png", "Sorotan");
        
        submenuBerandaBox.getChildren().addAll(mnuTiketSaya, mnuRekomendasiKegiatan, mnuSorotanBudaya);
        submenuBerandaBox.setVisible(true);
        submenuBerandaBox.setManaged(true);

        mnuBeranda.setOnMouseClicked(event -> {
            boolean isExpanded = submenuBerandaBox.isVisible();
            submenuBerandaBox.setVisible(!isExpanded);
            submenuBerandaBox.setManaged(!isExpanded);
            
            root.setCenter(homeContent); 
            setMenuSelection(mnuBeranda); 
        });
        mnuBeranda.setCursor(javafx.scene.Cursor.HAND);

        mnuTiketSaya.setOnMouseClicked(event -> pindahKeTiketSaya()); 
        mnuRekomendasiKegiatan.setOnMouseClicked(event -> pindahKeRekomendasiKegiatanPenuh());
        mnuSorotanBudaya.setOnMouseClicked(event -> pindahKeSorotanBudayaPenuh());

        submenuKegiatanBox = new VBox(3);
        mnuKategori = createSubmenuItem("/aset/iconLuminara/kategori-putih.png", "/aset/iconLuminara/kategori-biru.png", "Kategori");
        mnuBiaya = createSubmenuItem("/aset/iconLuminara/biaya-putih.png", "/aset/iconLuminara/biaya-biru.png", "Biaya");
        
        submenuKegiatanBox.getChildren().addAll(mnuKategori, mnuBiaya);
        submenuKegiatanBox.setVisible(false);
        submenuKegiatanBox.setManaged(false);

        mnuKegiatan.setOnMouseClicked(event -> {
            boolean isExpanded = submenuKegiatanBox.isVisible();
            submenuKegiatanBox.setVisible(!isExpanded);
            submenuKegiatanBox.setManaged(!isExpanded);
            
            if (submenuBerandaBox != null) { submenuBerandaBox.setVisible(false); submenuBerandaBox.setManaged(false); }
            
            KegiatanUser halKegiatan = new KegiatanUser();
            root.setCenter(halKegiatan.getView());
            setMenuSelection(mnuKegiatan);
        });
        mnuKegiatan.setCursor(javafx.scene.Cursor.HAND);

        mnuKategori.setOnMouseClicked(event -> pindahKeKategoriUser());
        mnuKategori.setCursor(javafx.scene.Cursor.HAND);

        mnuBiaya.setOnMouseClicked(event -> pindahKeBiayaUser());
        mnuBiaya.setCursor(javafx.scene.Cursor.HAND);

        submenuProfilBox = new VBox(3);
        mnuEditProfil = createSubmenuItem("/aset/iconLuminara/tiket-putih.png", "/aset/iconLuminara/tiket-biru.png", "Edit Profil");
        mnuRiwayatKegiatan = createSubmenuItem("/aset/iconLuminara/rekomendasi-putih.png", "/aset/iconLuminara/rekomendasi-biru.png", "Riwayat Kegiatan");
        mnuKeluar = createSubmenuItem("/aset/iconLuminara/sorotan-putih.png", "/aset/iconLuminara/sorotan-biru.png", "Keluar");
        submenuProfilBox.getChildren().addAll(mnuEditProfil, mnuRiwayatKegiatan, mnuKeluar);
        submenuProfilBox.setVisible(false); 
        submenuProfilBox.setManaged(false);

        mnuProfil.setOnMouseClicked(event -> {
            boolean isExpanded = submenuProfilBox.isVisible();

            submenuProfilBox.setVisible(!isExpanded);
            submenuProfilBox.setManaged(!isExpanded);

            if (submenuBerandaBox != null) {
                submenuBerandaBox.setVisible(false);
                submenuBerandaBox.setManaged(false);
            }

            if (submenuKegiatanBox != null) {
                submenuKegiatanBox.setVisible(false);
                submenuKegiatanBox.setManaged(false);
            }

            root.setCenter(new ProfilUser(false).getView());
            setMenuSelection(mnuProfil);
        });

        mnuEditProfil.setOnMouseClicked(event -> {
            // 1. Buat instance baru dengan parameter 'true'
            ProfilUser halProfil = new ProfilUser(true); 
            
            // 2. Pastikan Anda mengambil .getView() dari instance halProfil tersebut
            root.setCenter(halProfil.getView()); 
            
            setMenuSelection(mnuEditProfil);
        });
        
        mnuRiwayatKegiatan.setOnMouseClicked(event -> pindahKeRiwayatKegiatan());
        
        mnuKeluar.setOnMouseClicked(event -> {
            System.out.println("Sistem: User berhasil keluar dari sistem Luminara.");
            try {
                javafx.stage.Stage jendelaBaru = new javafx.stage.Stage();
                IntroPage3 introPage = new IntroPage3();
                introPage.start(jendelaBaru); 
                
                javafx.stage.Stage jendelaSaatIni = (javafx.stage.Stage) root.getScene().getWindow();
                if (jendelaSaatIni != null) jendelaSaatIni.close(); 
                
                System.out.println("✅ Logout berhasil. Layar awal ditampilkan.");
            } catch (Exception e) {
                System.out.println("⚠️ Gagal logout: ");
                e.printStackTrace();
            }
        });

        if (mnuBeranda != null) menuBox.getChildren().add(mnuBeranda);
        if (submenuBerandaBox != null) menuBox.getChildren().add(submenuBerandaBox);

        if (mnuKegiatan != null) menuBox.getChildren().add(mnuKegiatan);
        if (submenuKegiatanBox != null) menuBox.getChildren().add(submenuKegiatanBox);

        if (mnuProfil != null) menuBox.getChildren().add(mnuProfil);
        if (submenuProfilBox != null) menuBox.getChildren().add(submenuProfilBox);

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
        
        if (selectedMenu != null) {
            if (selectedMenu.getStyleClass().contains("submenu-item")) {
                for (javafx.scene.Node node : selectedMenu.getChildren()) {
                    if (node instanceof Label) {
                        Label lbl = (Label) node;
                        lbl.setStyle("-fx-text-fill: #FF9800; -fx-font-family: 'Poppins'; -fx-font-weight: bold; -fx-font-size: 12px;");
                    }
                }
            } else {
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