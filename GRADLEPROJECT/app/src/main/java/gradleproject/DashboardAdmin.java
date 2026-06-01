package gradleproject;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import gradleproject.config.DbConnect;

public class DashboardAdmin {

    private BorderPane root;
    private VBox homeContent; 

    private HBox mnuBeranda;
    private HBox mnuPengguna;
    private HBox mnuPenyelenggara;
    private HBox mnuAcara;
    private HBox mnuProfil;
    private HBox mnuKeluar;

    private HBox mnuDaftarPengguna;
    private HBox mnuDaftarBlokir;
    private VBox submenuPenggunaBox; 

    private HBox mnuDaftarPenyelenggara;
    private HBox mnuDaftarBlokirPenyelenggara;
    private VBox submenuPenyelenggaraBox;

    private VBox submenuAcaraBox;
    private HBox mnuFestival, mnuLokakarya, mnuMusik, mnuBudaya;

    private VBox submenuProfilBox;
    private HBox mnuSorotanBudaya;

    private static DashboardAdmin instance;

    public DashboardAdmin() {
        instance = this; // 🎯 KUNCI UTAMA: Baris ini wajib ada di urutan pertama!
        root = new BorderPane(); 
        
        createNavbar();
        createContent(); 
    }
    public static DashboardAdmin getInstance() {
        return instance;
    }

    public void pindahKeDaftarPengguna() {
        DaftarPengguna halDaftar = new DaftarPengguna(false); 
        root.setCenter(halDaftar.getView());
   
        if (submenuPenggunaBox != null) {
            submenuPenggunaBox.setVisible(true);
            submenuPenggunaBox.setManaged(true);
        }
        setMenuSelection(mnuDaftarPengguna);
    }

    public void pindahKeProfilPengguna(int id) {
        ProfilPengguna view = new ProfilPengguna(id);
        root.setCenter(view.getView());
    }

    public void pindahKeDetailPengguna(int userId) {
        DetailPengguna halDetail = new DetailPengguna();
        root.setCenter(halDetail.getView());
    }

    public void pindahKeProfilPenyelenggara() {
        ProfilPenyelenggara halProfilPenyelenggara = new ProfilPenyelenggara();
        root.setCenter(halProfilPenyelenggara.getView());
    }

    public void pindahKeDaftarPenyelenggara() {
        DaftarPenyelenggara halDaftar = new DaftarPenyelenggara(false); 
        root.setCenter(halDaftar.getView());
        
        if (submenuPenyelenggaraBox != null) {
            submenuPenyelenggaraBox.setVisible(true);
            submenuPenyelenggaraBox.setManaged(true);
        }
        setMenuSelection(mnuDaftarPenyelenggara);
    }

    public void pindahKeDetailPenyelenggara() {
        DetailPenyelenggara halTabelPenyelenggara = new DetailPenyelenggara();
        root.setCenter(halTabelPenyelenggara.getView());
    }

    public void pindahKeDaftarBlokirPengguna() {
        DaftarBlokirPengguna halBlokirUser = new DaftarBlokirPengguna(); 
        root.setCenter(halBlokirUser.getView());
        
        if (submenuPenggunaBox != null) {
            submenuPenggunaBox.setVisible(true);
            submenuPenggunaBox.setManaged(true);
        }
        setMenuSelection(mnuDaftarBlokir); 
    }

    public void pindahKeDaftarBlokirPenyelenggara() {
        DaftarBlokirPenyelenggara halBlokirVendor = new DaftarBlokirPenyelenggara();
        root.setCenter(halBlokirVendor.getView());
        
        if (submenuPenyelenggaraBox != null) {
            submenuPenyelenggaraBox.setVisible(true);
            submenuPenyelenggaraBox.setManaged(true);
        }
        setMenuSelection(mnuDaftarBlokirPenyelenggara); 
    }

    public void pindahKeAcaraFestival() {
        gradleproject.AcaraFestival halamanFestival = new gradleproject.AcaraFestival();
        root.setCenter(halamanFestival.getView()); 
    }

    public void pindahKeAcaraLokakarya() {
        AcaraLokakarya halLokakarya = new AcaraLokakarya();
        root.setCenter(halLokakarya.getView());
        
        if (submenuAcaraBox != null) {
            submenuAcaraBox.setVisible(true);
            submenuAcaraBox.setManaged(true);
        }
        setMenuSelection(mnuLokakarya); 
    }

    public void pindahKeAcaraMusik() {
        AcaraMusik halMusik = new AcaraMusik();
        root.setCenter(halMusik.getView());
        
        if (submenuAcaraBox != null) {
            submenuAcaraBox.setVisible(true);
            submenuAcaraBox.setManaged(true);
        }
        setMenuSelection(mnuMusik);
    }

    public void pindahKeAcaraBudaya() {
        gradleproject.AcaraBudaya halamanBudaya = new gradleproject.AcaraBudaya();
        root.setCenter(halamanBudaya.getView()); 
    }

    public void pindahKeDetailAcaraBudaya(gradleproject.models.Event acara) {
        DetailAcaraBudaya halamanDetail = new DetailAcaraBudaya(acara);
        root.setCenter(halamanDetail.getView());

        if (submenuAcaraBox != null) {
            submenuAcaraBox.setVisible(true);
            submenuAcaraBox.setManaged(true);
        }
        setMenuSelection(mnuBudaya);
    }

    public void pindahKeDetailAcaraFestival(gradleproject.models.Event acara) {
        DetailAcaraFestival halamanDetail = new DetailAcaraFestival(acara);
        root.setCenter(halamanDetail.getView());

        if (submenuAcaraBox != null) {
            submenuAcaraBox.setVisible(true);
            submenuAcaraBox.setManaged(true);
        }
        setMenuSelection(mnuFestival);
    }

    public void pindahKeDetailAcaraLokakarya(gradleproject.models.Event acara) {
        DetailAcaraLokakarya halamanDetail = new DetailAcaraLokakarya(acara);
        root.setCenter(halamanDetail.getView());

        if (submenuAcaraBox != null) {
            submenuAcaraBox.setVisible(true);
            submenuAcaraBox.setManaged(true);
        }
        setMenuSelection(mnuLokakarya);
    }

    public void pindahKeDetailAcaraMusik(gradleproject.models.Event acara) {
        DetailAcaraMusik halamanDetail = new DetailAcaraMusik(acara);
        root.setCenter(halamanDetail.getView());

        if (submenuAcaraBox != null) {
            submenuAcaraBox.setVisible(true);
            submenuAcaraBox.setManaged(true);
        }
        setMenuSelection(mnuMusik);
    }

    public void pindahKeProfilAdmin() {
        ProfilAdmin halProfil = new ProfilAdmin();
        root.setCenter(halProfil.getView());
        
        if (submenuProfilBox != null) {
            submenuProfilBox.setVisible(true);
            submenuProfilBox.setManaged(true);
        }
        setMenuSelection(mnuProfil); 
    }

    public void pindahKeSorotanBudaya() {
        SorotanBudayaView halSorotan = new SorotanBudayaView();
        root.setCenter(halSorotan); 
        
        if (submenuProfilBox != null) {
            submenuProfilBox.setVisible(true);
            submenuProfilBox.setManaged(true);
        }
        setMenuSelection(mnuSorotanBudaya);
    }

    public void pindahKeDetailPendapatan() {
        DetailPendapatanAdmin halDetail = new DetailPendapatanAdmin();
        this.root.setCenter(halDetail.getView()); 
        setMenuSelection(mnuBeranda); 
    }

    private void createNavbar() {
        VBox navbar = new VBox(15); 
        navbar.setPadding(new Insets(40, 20, 30, 20));
        navbar.setAlignment(Pos.TOP_LEFT);
        navbar.getStyleClass().add("sidebar_admin");

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

        Label lblAdmin = new Label("Admin");
        lblAdmin.getStyleClass().add("admin-subtitle");
        brandBox.getChildren().addAll(logoView, lblAdmin);

        VBox menuBox = new VBox(5); 
        
        mnuBeranda = createMenuItem("/aset/iconLuminara/icon-beranda.png", "/aset/iconLuminara/branda-biru.png", "Beranda", true); 
        mnuPengguna = createMenuItem("/aset/iconLuminara/icon-komunitas.png", "/aset/iconLuminara/icon-peserta.png", "Manajemen Pengguna", false);
        mnuPenyelenggara = createMenuItem("/aset/iconLuminara/icon-komunitas.png", "/aset/iconLuminara/icon-peserta.png", "Manajemen Penyelenggara", false);
        mnuAcara = createMenuItem("/aset/iconLuminara/acara-putih.png", "/aset/iconLuminara/icon-manajemen-acara.png", "Acara", false);
        mnuProfil = createMenuItem("/aset/iconLuminara/icon-user.png", "/aset/iconLuminara/profil-biru.png", "Profil", false);
        mnuKeluar = createMenuItem("/aset/iconLuminara/icon-masuk-keluar.png", "/aset/iconLuminara/icon-masuk-keluar.png", "Keluar", false);

        submenuPenggunaBox = new VBox(3);
        mnuDaftarPengguna = createSubmenuItem("/aset/iconLuminara/icon-komunitas.png", "/aset/iconLuminara/icon-komunitas-biru.png", "Pengguna");
        mnuDaftarBlokir = createSubmenuItem("/aset/iconLuminara/Blokir-Putih.png", "/aset/iconLuminara/icon-masuk-keluar.png", "Daftar Blokir");
        
        submenuPenggunaBox.getChildren().addAll(mnuDaftarPengguna, mnuDaftarBlokir);
        submenuPenggunaBox.setVisible(false);
        submenuPenggunaBox.setManaged(false);

        mnuBeranda.setOnMouseClicked(event -> {
            createContent(); 
            root.setCenter(homeContent); 
            setMenuSelection(mnuBeranda); 
        });

        mnuPengguna.setOnMouseClicked(event -> {
            boolean isExpanded = submenuPenggunaBox.isVisible();
            submenuPenggunaBox.setVisible(!isExpanded);
            submenuPenggunaBox.setManaged(!isExpanded);
            
            if (submenuPenyelenggaraBox != null) { submenuPenyelenggaraBox.setVisible(false); submenuPenyelenggaraBox.setManaged(false); }
            if (submenuAcaraBox != null) { submenuAcaraBox.setVisible(false); submenuAcaraBox.setManaged(false); }
            if (submenuProfilBox != null) { submenuProfilBox.setVisible(false); submenuProfilBox.setManaged(false); }
            
            ManajemenPengguna halManajemen = new ManajemenPengguna();
            root.setCenter(halManajemen.getView());
            setMenuSelection(mnuPengguna); 
        });
        mnuPengguna.setCursor(javafx.scene.Cursor.HAND);

        mnuDaftarPengguna.setOnMouseClicked(event -> {
            DaftarPengguna halDaftar = new DaftarPengguna(false); 
            root.setCenter(halDaftar.getView());
            setMenuSelection(mnuDaftarPengguna);
        });
        
        mnuDaftarBlokir.setOnMouseClicked(event -> {
            pindahKeDaftarBlokirPengguna(); 
            setMenuSelection(mnuDaftarBlokir); 
        });

        submenuPenyelenggaraBox = new VBox(3);
        mnuDaftarPenyelenggara = createSubmenuItem("/aset/iconLuminara/icon-komunitas.png", "/aset/iconLuminara/icon-komunitas-biru.png", "Penyelenggara");
        mnuDaftarBlokirPenyelenggara = createSubmenuItem("/aset/iconLuminara/Blokir-Putih.png", "/aset/iconLuminara/icon-masuk-keluar.png", "Daftar Blokir");

        submenuPenyelenggaraBox.getChildren().addAll(mnuDaftarPenyelenggara, mnuDaftarBlokirPenyelenggara);
        submenuPenyelenggaraBox.setVisible(false);
        submenuPenyelenggaraBox.setManaged(false);

        mnuPenyelenggara.setOnMouseClicked(event -> {
            boolean isExpanded = submenuPenyelenggaraBox.isVisible();
            submenuPenyelenggaraBox.setVisible(!isExpanded);
            submenuPenyelenggaraBox.setManaged(!isExpanded);

            if (submenuPenggunaBox != null) { submenuPenggunaBox.setVisible(false); submenuPenggunaBox.setManaged(false); }
            if (submenuAcaraBox != null) { submenuAcaraBox.setVisible(false); submenuAcaraBox.setManaged(false); }
            if (submenuProfilBox != null) { submenuProfilBox.setVisible(false); submenuProfilBox.setManaged(false); }
            
            ManajemenPenyelenggara halManajemen = new ManajemenPenyelenggara();
            root.setCenter(halManajemen.getView());
            setMenuSelection(mnuPenyelenggara); 
        });
        mnuPenyelenggara.setCursor(javafx.scene.Cursor.HAND);

        mnuDaftarPenyelenggara.setOnMouseClicked(event -> {
            DaftarPenyelenggara hal = new DaftarPenyelenggara(false);
            root.setCenter(hal.getView());
            setMenuSelection(mnuDaftarPenyelenggara);
        });
        
        mnuDaftarBlokirPenyelenggara.setOnMouseClicked(event -> {
            pindahKeDaftarBlokirPenyelenggara();
            setMenuSelection(mnuDaftarBlokirPenyelenggara);
        });

        submenuAcaraBox = new VBox(3);
        mnuFestival = createSubmenuItem("/aset/iconLuminara/fest-putih.png", "/aset/iconLuminara/icon-festival-biru.png", "Festival");
        mnuFestival.setOnMouseClicked(event -> pindahKeAcaraFestival());

        mnuLokakarya = createSubmenuItem("/aset/iconLuminara/lokakarya-putih.png", "/aset/iconLuminara/icon-lokakarya-biru.png", "Lokakarya");
        mnuLokakarya.setOnMouseClicked(event -> pindahKeAcaraLokakarya());

        mnuMusik = createSubmenuItem("/aset/iconLuminara/musik-putih.png", "/aset/iconLuminara/icon-musik-biru.png", "Musik");
        mnuMusik.setOnMouseClicked(event -> pindahKeAcaraMusik());

        mnuBudaya = createSubmenuItem("/aset/iconLuminara/budaya-puith.png", "/aset/iconLuminara/icon-budaya-biru.png", "Budaya");
        mnuBudaya.setOnMouseClicked(event -> pindahKeAcaraBudaya());

        submenuAcaraBox.getChildren().addAll(mnuFestival, mnuLokakarya, mnuMusik, mnuBudaya);
        submenuAcaraBox.setVisible(false);
        submenuAcaraBox.setManaged(false);

        mnuAcara.setOnMouseClicked(event -> {
            boolean isExpanded = submenuAcaraBox.isVisible();
            submenuAcaraBox.setVisible(!isExpanded);
            submenuAcaraBox.setManaged(!isExpanded);
            
            if (submenuPenggunaBox != null) { submenuPenggunaBox.setVisible(false); submenuPenggunaBox.setManaged(false); }
            if (submenuPenyelenggaraBox != null) { submenuPenyelenggaraBox.setVisible(false); submenuPenyelenggaraBox.setManaged(false); }
            if (submenuProfilBox != null) { submenuProfilBox.setVisible(false); submenuProfilBox.setManaged(false); }
            
            AcaraUtama halAcara = new AcaraUtama();
            root.setCenter(halAcara.getView());
            setMenuSelection(mnuAcara); 
        });

        submenuProfilBox = new VBox(3);
        mnuSorotanBudaya = createSubmenuItem("/aset/iconLuminara/budaya-puith.png", "/aset/iconLuminara/icon-budaya-biru.png", "Sorotan Budaya");
        
        submenuProfilBox.getChildren().add(mnuSorotanBudaya);
        submenuProfilBox.setVisible(false);
        submenuProfilBox.setManaged(false);

        mnuProfil.setOnMouseClicked(event -> {
            boolean isExpanded = submenuProfilBox.isVisible();
            submenuProfilBox.setVisible(!isExpanded);
            submenuProfilBox.setManaged(!isExpanded);
            
            if (submenuPenggunaBox != null) { submenuPenggunaBox.setVisible(false); submenuPenggunaBox.setManaged(false); }
            if (submenuPenyelenggaraBox != null) { submenuPenyelenggaraBox.setVisible(false); submenuPenyelenggaraBox.setManaged(false); }
            if (submenuAcaraBox != null) { submenuAcaraBox.setVisible(false); submenuAcaraBox.setManaged(false); }

            pindahKeProfilAdmin();
            setMenuSelection(mnuProfil);
        });
        mnuProfil.setCursor(javafx.scene.Cursor.HAND);

        mnuSorotanBudaya.setOnMouseClicked(event -> {
            pindahKeSorotanBudaya();
        });

        mnuKeluar.setOnMouseClicked(event -> {
            javafx.stage.Stage primaryStage = (javafx.stage.Stage) mnuKeluar.getScene().getWindow();
            IntroPage3 intro3 = new IntroPage3();
            try {
                intro3.start(primaryStage);
            } catch (Exception ex) {
                System.out.println("Gagal memuat halaman keluar: " + ex.getMessage());
            }
        });

        menuBox.getChildren().addAll(
            mnuBeranda, mnuPengguna, submenuPenggunaBox,  
            mnuPenyelenggara, submenuPenyelenggaraBox,
            mnuAcara, submenuAcaraBox, 
            mnuProfil, submenuProfilBox, 
            mnuKeluar
        );

        HBox bottomLogoBox = new HBox();
        bottomLogoBox.setAlignment(Pos.CENTER);
        ImageView smallLogoView = new ImageView();
        try {
            Image smallLogo = new Image(getClass().getResourceAsStream("/aset/gambarLuminara/luminara-logoWhite.png"));
            smallLogoView.setImage(smallLogo);
            smallLogoView.setFitWidth(45);
            smallLogoView.setPreserveRatio(true);
        } catch (Exception e) {
            System.out.println("⚠️ Gagal memuat logo kecil!");
        }
        bottomLogoBox.getChildren().add(smallLogoView);

        ScrollPane scrollMenu = new ScrollPane(menuBox);
        scrollMenu.setFitToWidth(true);
        scrollMenu.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollMenu.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollMenu.setStyle("-fx-background-color: transparent; -fx-background: transparent; -fx-border-color: transparent;");
        
        scrollMenu.setPrefWidth(220);
        scrollMenu.setMaxWidth(220);
        VBox.setVgrow(scrollMenu, Priority.ALWAYS);

        navbar.getChildren().clear(); 
        navbar.getChildren().addAll(brandBox, scrollMenu, bottomLogoBox);
        root.setLeft(navbar);
    }

    private void setMenuSelection(HBox selectedMenu) {
        if (selectedMenu != mnuPengguna && selectedMenu != mnuDaftarPengguna && selectedMenu != mnuDaftarBlokir) {
            if (submenuPenggunaBox != null) {
                submenuPenggunaBox.setVisible(false);
                submenuPenggunaBox.setManaged(false);
            }
        }

        if (selectedMenu != mnuPenyelenggara && selectedMenu != mnuDaftarPenyelenggara && selectedMenu != mnuDaftarBlokirPenyelenggara) {
            if (submenuPenyelenggaraBox != null) {
                submenuPenyelenggaraBox.setVisible(false);
                submenuPenyelenggaraBox.setManaged(false);
            }
        }

        if (selectedMenu != mnuAcara && selectedMenu != mnuFestival && selectedMenu != mnuLokakarya && selectedMenu != mnuMusik && selectedMenu != mnuBudaya) {
            if (submenuAcaraBox != null) {
                submenuAcaraBox.setVisible(false);
                submenuAcaraBox.setManaged(false);
            }
        }
        
        if (selectedMenu != mnuProfil && selectedMenu != mnuSorotanBudaya) {
            if (submenuProfilBox != null) {
                submenuProfilBox.setVisible(false);
                submenuProfilBox.setManaged(false);
            }
        }

        HBox[] allMenus = {
            mnuBeranda, mnuPengguna, mnuPenyelenggara, mnuAcara, mnuProfil, mnuKeluar,
            mnuDaftarPengguna, mnuDaftarBlokir,
            mnuDaftarPenyelenggara, mnuDaftarBlokirPenyelenggara,
            mnuFestival, mnuLokakarya, mnuMusik, mnuBudaya,
            mnuSorotanBudaya 
        };
        
        for (HBox menu : allMenus) {
            if (menu == null) continue;
            
            while (menu.getStyleClass().contains("menu-item-active")) {
                menu.getStyleClass().remove("menu-item-active");
            }
            while (menu.getStyleClass().contains("submenu-item-active")) {
                menu.getStyleClass().remove("submenu-item-active");
            }
            
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
                    while (lbl.getStyleClass().contains("menu-text-active")) {
                        lbl.getStyleClass().remove("menu-text-active");
                    }
                    while (lbl.getStyleClass().contains("submenu-text-active")) {
                        lbl.getStyleClass().remove("submenu-text-active");
                    }
                    if (menu.getStyleClass().contains("submenu-item")) {
                        if (!lbl.getStyleClass().contains("submenu-text")) lbl.getStyleClass().add("submenu-text");
                    } else {
                        if (!lbl.getStyleClass().contains("menu-text")) lbl.getStyleClass().add("menu-text");
                    }
                }
            }
        }
        
        if (selectedMenu != null) {
            if (selectedMenu.getStyleClass().contains("submenu-item")) {
                if (!selectedMenu.getStyleClass().contains("submenu-item-active")) {
                    selectedMenu.getStyleClass().add("submenu-item-active");
                }
                for (javafx.scene.Node node : selectedMenu.getChildren()) {
                    if (node instanceof Label) {
                        Label lbl = (Label) node;
                        lbl.getStyleClass().remove("submenu-text");
                        if (!lbl.getStyleClass().contains("submenu-text-active")) lbl.getStyleClass().add("submenu-text-active");
                    }
                }
            } else {
                if (!selectedMenu.getStyleClass().contains("menu-item-active")) {
                    selectedMenu.getStyleClass().add("menu-item-active");
                }
                
                if (!selectedMenu.getChildren().isEmpty() && selectedMenu.getChildren().get(0) instanceof StackPane) {
                    StackPane container = (StackPane) selectedMenu.getChildren().get(0);
                    if (!container.getChildren().isEmpty() && container.getChildren().get(0) instanceof ImageView) {
                        ImageView imgView = (ImageView) container.getChildren().get(0);
                        String pathBiru = (String) selectedMenu.getProperties().get("iconBiru");
                        if (pathBiru != null) {
                            try {
                                imgView.setImage(new Image(getClass().getResourceAsStream(pathBiru)));
                                int size = selectedMenu.getStyleClass().contains("submenu-item") ? 22 : 20;
                                    imgView.setFitWidth(size);
                                    imgView.setFitHeight(size);
                            } catch(Exception e) { }
                        }
                    }
                }
                for (javafx.scene.Node node : selectedMenu.getChildren()) {
                    if (node instanceof Label) {
                        Label lbl = (Label) node;
                        lbl.getStyleClass().remove("menu-text");
                        if (!lbl.getStyleClass().contains("menu-text-active")) lbl.getStyleClass().add("menu-text-active");
                    }
                }
            }
        }
    }

    private HBox createMenuItem(String iconPutihPath, String iconBiruPath, String text, boolean isActive) {
        HBox menuItem = new HBox(10); 
        menuItem.setAlignment(Pos.CENTER_LEFT);
        menuItem.getStyleClass().add("menu-item");

        menuItem.setMinHeight(45);
        menuItem.setMaxHeight(45);

        if (isActive) {
            menuItem.getStyleClass().add("menu-item-active");
        }

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
        if (isActive) {
            lblText.getStyleClass().add("menu-text-active");
        } else {
            lblText.getStyleClass().add("menu-text");
        }
        lblText.setMaxWidth(140); 

        menuItem.getChildren().addAll(iconContainer, lblText);
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
        lblText.getStyleClass().add("submenu-text");
        lblText.setMaxWidth(180); 
        lblText.setAlignment(Pos.CENTER_LEFT); 

        subItem.getChildren().addAll(iconContainer, lblText);
        return subItem;
    }

    private VBox createSummaryCard(String iconText, String title, String number, String boxColorClass, javafx.event.EventHandler<javafx.event.ActionEvent> onAction) {
        VBox card = new VBox(0); 
        card.getStyleClass().add("dashboard-card");
        card.setPadding(new Insets(0)); 
        card.setPrefSize(190, 110);
        card.setMinWidth(190);

        Label icon = new Label(iconText);
        icon.getStyleClass().add("card-icon");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button btnLihat = new Button(">");
        btnLihat.getStyleClass().add("btn-lihat");
        if (onAction != null) btnLihat.setOnAction(onAction);

        HBox topRow = new HBox(icon, spacer, btnLihat);
        topRow.setAlignment(Pos.CENTER_LEFT);
        topRow.setPadding(new Insets(10, 15, 0, 15)); 

        Label lblTitle = new Label(title);
        lblTitle.getStyleClass().add("card-title");
        lblTitle.setMaxWidth(Double.MAX_VALUE);
        lblTitle.setAlignment(Pos.CENTER_LEFT);
        VBox.setMargin(lblTitle, new Insets(5, 10, 5, 15)); 

        Label lblNumber = new Label(number);
        lblNumber.getStyleClass().add("card-number-text");

        StackPane numberBox = new StackPane(lblNumber);
        numberBox.getStyleClass().addAll("card-number-box", boxColorClass);
        numberBox.setMaxWidth(Double.MAX_VALUE); 
        VBox.setVgrow(numberBox, Priority.ALWAYS);

        card.getChildren().addAll(topRow, lblTitle, numberBox);
        return card;
    }

    private HBox createRevenueRow(String month, String amount) {
        HBox row = new HBox(10); 
        row.setAlignment(Pos.CENTER_LEFT);
        row.getStyleClass().add("revenue-row");
        row.setMaxWidth(Double.MAX_VALUE);

        Label lblMonth = new Label(month + ":");
        lblMonth.getStyleClass().add("revenue-month");

        Label lblAmount = new Label(amount);
        lblAmount.getStyleClass().add("revenue-amount");
        lblAmount.setFont(Font.font("Poppins", FontWeight.SEMI_BOLD, 12));

        Region pendorong = new Region();
        HBox.setHgrow(pendorong, Priority.ALWAYS);
        row.getChildren().addAll(lblMonth, lblAmount, pendorong);
        return row;
    }

    private HBox createActivityRow(String iconPath, String title, String subtitle, String time) {
        HBox row = new HBox(15); 
        row.setAlignment(Pos.CENTER_LEFT);
        row.getStyleClass().add("activity-row");
        row.setMaxWidth(Double.MAX_VALUE);

        ImageView iconView = new ImageView();
        try {
            Image icon = new Image(getClass().getResourceAsStream(iconPath));
            iconView.setImage(icon);
            iconView.setFitWidth(20);
            iconView.setFitHeight(20);
            iconView.setPreserveRatio(true);
        } catch (Exception e) { }

        VBox textContainer = new VBox(2);
        Label lblTitle = new Label(title);
        lblTitle.getStyleClass().add("activity-title");
        textContainer.getChildren().add(lblTitle);

        if (subtitle != null && !subtitle.isEmpty()) {
            Label lblSubtitle = new Label(subtitle);
            lblSubtitle.getStyleClass().add("activity-subtitle");
            textContainer.getChildren().add(lblSubtitle);
        }

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label lblTime = new Label(time);
        lblTime.getStyleClass().add("activity-time");

        row.getChildren().addAll(iconView, textContainer, spacer, lblTime);
        return row;
    }

    private void createContent() {
        homeContent = new VBox(25); 
        homeContent.setPadding(new Insets(20, 20, 20, 80)); 
        homeContent.setAlignment(Pos.TOP_LEFT); 

        VBox greetingBox = new VBox(-5); 
        Label hiLabel = new Label("Hai, admin.");
        hiLabel.getStyleClass().add("greeting-title");
        Label todayLabel = new Label("Pantau perkembangan hari ini . . .");
        todayLabel.getStyleClass().add("greeting-subtitle");
        greetingBox.getChildren().addAll(hiLabel, todayLabel);

        // =======================================================
        // 🎯 DATA DINAMIS DARI DATABASE UNTUK KARTU (CARD)
        // =======================================================
        int totalPengguna = getDatabaseCount("SELECT COUNT(*) FROM users WHERE UPPER(role) = 'USER' AND UPPER(account_status) != 'BANNED'");
        int totalPenyelenggara = getDatabaseCount("SELECT COUNT(*) FROM users WHERE (UPPER(role) = 'ORGANIZER' OR UPPER(role) = 'PENYELENGGARA') AND UPPER(account_status) != 'BANNED'");
        
        // 🎯 PERBAIKAN: Gunakan JOIN agar event yang diselenggarakan oleh Organizer Banned tidak ikut dihitung
        int acaraBerlangsung = getDatabaseCount(
            "SELECT COUNT(*) FROM events e " +
            "JOIN users u ON e.organizer_id = u.id " +
            "WHERE e.status = 'Active' AND (u.account_status IS NULL OR UPPER(u.account_status) != 'BANNED')"
        );
        
        int acaraPending = getDatabaseCount(
            "SELECT COUNT(*) FROM events e " +
            "JOIN users u ON e.organizer_id = u.id " +
            "WHERE e.status = 'Draft' AND (u.account_status IS NULL OR UPPER(u.account_status) != 'BANNED')"
        );

        GridPane cardsGrid = new GridPane();
        cardsGrid.setHgap(20); 
        cardsGrid.setVgap(20); 
        cardsGrid.setAlignment(Pos.TOP_LEFT);

        VBox card1 = createSummaryCard("👤", "TOTAL PENGGUNA", String.valueOf(totalPengguna), "box-gray", event -> {
            DaftarPengguna halDaftar = new DaftarPengguna(true); 
            root.setCenter(halDaftar.getView()); 
        });
            
        VBox card2 = createSummaryCard("🏢", "TOTAL PENYELENGGARA", String.valueOf(totalPenyelenggara), "box-gray", event -> {
            DaftarPenyelenggara halPenyelenggara = new DaftarPenyelenggara(true); 
            root.setCenter(halPenyelenggara.getView());
        });
        
        VBox card3 = createSummaryCard("📅", "SEDANG BERLANGSUNG", String.valueOf(acaraBerlangsung), "box-gray", event -> {
            AcaraBerlangsung halAcara = new AcaraBerlangsung();
            root.setCenter(halAcara.getView());
        });

        VBox card4 = createSummaryCard("⏱", "PENDING", String.valueOf(acaraPending), "box-orange", event -> {
            MenungguKonfirmasi halPending = new MenungguKonfirmasi();
            root.setCenter(halPending.getView());
        });

        cardsGrid.add(card1, 0, 0);
        cardsGrid.add(card2, 1, 0);
        cardsGrid.add(card3, 0, 1);
        cardsGrid.add(card4, 1, 1);

        // =======================================================
        // 2. KARTU RINGKASAN PENDAPATAN
        // =======================================================
        VBox ringkasanBox = new VBox(15); 
        ringkasanBox.getStyleClass().add("revenue-card"); 
        ringkasanBox.setPrefWidth(340);
        ringkasanBox.setMinWidth(340);
        ringkasanBox.setMaxWidth(340);
        ringkasanBox.setPrefHeight(240); 

        HBox headerRingkasan = new HBox(10);
        headerRingkasan.setAlignment(Pos.CENTER_LEFT);
        
        ImageView iconChart = new ImageView();
        try {
            Image imgChart = new Image(getClass().getResourceAsStream("/aset/iconLuminara/chart.png"));
            iconChart.setImage(imgChart);
            iconChart.setFitWidth(18);
            iconChart.setPreserveRatio(true);
        } catch (Exception e) { }
        
        Label lblRingkasanTitle = new Label("RINGKASAN PENDAPATAN");
        lblRingkasanTitle.getStyleClass().add("card-title");
        headerRingkasan.getChildren().addAll(iconChart, lblRingkasanTitle);

        VBox rowsBox = new VBox(10); 
        rowsBox.setMaxWidth(Double.MAX_VALUE); 
        rowsBox.getChildren().addAll(
            createRevenueRow("Jan", "Rp7.250.000"),
            createRevenueRow("Feb", "Rp10.000.000"),
            createRevenueRow("Mar", "Rp7.775.000"),
            createRevenueRow("Apr", "Rp4.500.000")
        );

        Region verticalSpacer = new Region();
        VBox.setVgrow(verticalSpacer, Priority.ALWAYS);

        HBox btnContainer = new HBox();
        btnContainer.setAlignment(Pos.CENTER_RIGHT);
        Button btnDetail = new Button("Lihat Detail");
        btnDetail.getStyleClass().add("btn-lihat"); 
        
        btnDetail.setOnAction(event -> {
            if (DashboardAdmin.getInstance() != null) {
                DashboardAdmin.getInstance().pindahKeDetailPendapatan();
            }
        });

        btnContainer.getChildren().add(btnDetail);
        ringkasanBox.getChildren().addAll(headerRingkasan, rowsBox, verticalSpacer, btnContainer);

        HBox middleSection = new HBox(30); 
        middleSection.setAlignment(Pos.TOP_LEFT);
        middleSection.getChildren().addAll(cardsGrid, ringkasanBox);

        // =======================================================
        // 3. AKTIVITAS TERBARU (Dinamis dari Tabel Users)
        // =======================================================
        VBox aktivitasSection = new VBox(12); 
        aktivitasSection.setPrefWidth(770);
        aktivitasSection.setMinWidth(770);
        aktivitasSection.setMaxWidth(770);

        Label lblAktivitasTitle = new Label("Aktivitas Terbaru");
        lblAktivitasTitle.getStyleClass().add("section-title");
        aktivitasSection.getChildren().add(lblAktivitasTitle);

        String queryAktivitas = "SELECT username, role, account_status, strftime('%H.%M', created_at) as wkt FROM users WHERE role != 'ADMIN' ORDER BY id DESC LIMIT 2";
        
        try (Connection conn = DbConnect.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(queryAktivitas)) {
             
            while (rs.next()) {
                String nama = rs.getString("username");
                String role = rs.getString("role"); 
                String status = rs.getString("account_status");
                String wkt = rs.getString("wkt") + " WITA"; 

                String roleTeks = "sebagai pengguna";
                if (role != null && (role.equalsIgnoreCase("ORGANIZER") || role.equalsIgnoreCase("PENYELENGGARA"))) {
                    roleTeks = "sebagai penyelenggara";
                }

                String title = "Pendaftar baru: " + nama;
                String subtitle = roleTeks; 
                String iconPath = "/aset/iconLuminara/profil.png";

                if (status != null && status.equalsIgnoreCase("Banned")) {
                    title = "User diblokir: " + nama;
                    subtitle = "oleh Admin";
                    iconPath = "/aset/iconLuminara/keluar.png";
                }

                HBox actRow = createActivityRow(iconPath, title, subtitle, wkt);
                aktivitasSection.getChildren().add(actRow);
            }
        } catch (Exception e) {
            System.out.println("⚠️ Gagal memuat aktivitas: " + e.getMessage());
        }

        HBox btnSemuaContainer = new HBox();
        Region pendorongTombol = new Region();
        HBox.setHgrow(pendorongTombol, Priority.ALWAYS);
        Button btnLihatSemua = new Button("Lihat Semuanya");
        btnLihatSemua.getStyleClass().add("btn-lihat"); 

        btnLihatSemua.setOnAction(event -> {
            AktivitasTerbaru halAktivitas = new AktivitasTerbaru();
            root.setCenter(halAktivitas.getView()); 
        });

        btnSemuaContainer.getChildren().addAll(pendorongTombol, btnLihatSemua);
        aktivitasSection.getChildren().add(btnSemuaContainer);

        homeContent.getChildren().addAll(greetingBox, middleSection, aktivitasSection);
        root.setCenter(homeContent);
    }

    private int getDatabaseCount(String query) {
        int count = 0;
        try (Connection conn = DbConnect.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
             
            if (rs.next()) { 
                count = rs.getInt(1); 
            }
        } catch (Exception e) { 
            System.out.println("⚠️ Gagal mengambil data dashboard: " + e.getMessage()); 
        }
        return count;
    }

    public Parent getView() {
        return root;
    }
}