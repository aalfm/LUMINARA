package gradleproject;

import gradleproject.dao.EventDAO;
import gradleproject.dao.OrganizerDAO;
import gradleproject.dao.TicketDAO;
import gradleproject.models.Event; 
import gradleproject.models.OrganizerProfile;
import java.io.InputStream;
import java.util.List;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

public class ManajemenAcaraView extends HBox {

    private final StackPane contentArea; 
    private VBox berandaContent;
    private VBox listDetailAcaraContent;
    private TambahAcaraView tambahAcaraContent; 
    
    // Variabel View Terpisah
    private PesertaView pesertaContent; 
    private PendapatanView pendapatanView;
    private ProfilView profilView;

    private Label lblTiketTerjual = new Label("0");
    private Label lblPendapatan = new Label("Rp0");
    private Label lblPesertaAktif = new Label("0");
    

    // Komponen Sidebar
    private Label currentActiveMenu;
    private Label currentActiveSubMenu;
    private Label subTambahAcara;
    private final VBox subMenuContainer; 
    private final VBox subMenuProfilContainer; 
    private final Label menuAcara;
    private VBox dataTableContainer;
    
    // Simpan referensi menu sebagai field agar bisa diakses dari method action
    private Label menuBeranda;
    private Label menuPeserta;
    private Label menuPendapatan;
    private Label menuProfil;
    private Label btnSubEditProfil;
    private Label btnSubLihatUlasan;

    // Field baru untuk integrasi Controller & Session Database
    private final int currentUserId;
    private OrganizerProfile currentOrganizer;
    private Label lblAdmin; 

    public ManajemenAcaraView(int userId) {
        this.currentUserId = userId;
        this.getStyleClass().add("root");

        // ==========================================
        // CONTROLLER INITIALIZATION
        // ==========================================
        loadOrganizerData();
        loadDashboardStats();

        // ==========================================
        // SIDEBAR LAYOUT GENERATION
        // ==========================================
        VBox sidebar = new VBox(25); 
        sidebar.getStyleClass().add("sidebar"); 
        
        sidebar.setStyle(
            "-fx-background-position: center bottom; " + 
            "-fx-background-size: contain; " + 
            "-fx-background-repeat: no-repeat;"
        );

        sidebar.setPrefWidth(260);
        sidebar.setPadding(new Insets(40, 20, 40, 20));

        // Brand Logo
        VBox brandBox = new VBox(5);
        brandBox.setAlignment(Pos.CENTER_LEFT);
        brandBox.setPadding(new Insets(0, 0, 10, 10)); 
        ImageView logoView = new ImageView();
        
        try (InputStream logoStream = getClass().getResourceAsStream("/aset/gambarLuminara/luminara-textWhite.png")) {
            if (logoStream != null) {
                logoView.setImage(new Image(logoStream));
                logoView.setFitWidth(150); 
                logoView.setPreserveRatio(true);
            }
        } catch (Exception e) {
            System.err.println("Gagal memuat logo: " + e.getMessage());
        }

        String namaDisplay = (currentOrganizer != null) ? currentOrganizer.getName() : "Penyelenggara";
        lblAdmin = new Label(namaDisplay); 
        lblAdmin.setStyle("-fx-text-fill: #CCD5DA; -fx-font-size: 14px; -fx-font-family: 'Poppins'; -fx-font-weight: bold;");
        brandBox.getChildren().addAll(logoView, lblAdmin);

        // Menu Items
        VBox menuContainer = new VBox(10);
        
        menuBeranda = createMenuItem("Beranda", "/aset/iconLuminara/icon-beranda.png", true);
        currentActiveMenu = menuBeranda;

        menuAcara = createMenuItem("Manajemen Acara", "/aset/iconLuminara/acara-putih.png", false);
        
        subMenuContainer = new VBox();
        subMenuContainer.getStyleClass().add("submenu-container");
        subMenuContainer.setManaged(false); subMenuContainer.setVisible(false); 
        
        Label subListAcara = createSubMenuItem("List Detail Acara", "/aset/iconLuminara/icon-tiket.png");
        subTambahAcara = createSubMenuItem("Tambah Acara", "/aset/iconLuminara/icon-tiket.png");
        subMenuContainer.getChildren().addAll(subListAcara, subTambahAcara);

        menuPeserta = createMenuItem("Peserta", "/aset/iconLuminara/icon-komunitas.png", false);
        menuPendapatan = createMenuItem("Pendapatan", "/aset/iconLuminara/icon-pendapatan.png", false);
        menuProfil = createMenuItem("Profil", "/aset/iconLuminara/icon-user.png", false);
        
        subMenuProfilContainer = new VBox();
        subMenuProfilContainer.getStyleClass().add("submenu-container");
        subMenuProfilContainer.setManaged(false); 
        subMenuProfilContainer.setVisible(false); 
        
        btnSubEditProfil = createSubMenuItem("Edit Profil", "/aset/iconLuminara/icon-masuk-keluar.png");
        btnSubLihatUlasan = createSubMenuItem("Lihat Ulasan", "/aset/iconLuminara/ulasan-biru.png");
        subMenuProfilContainer.getChildren().addAll(btnSubEditProfil, btnSubLihatUlasan);

        Label menuKeluar = createMenuItem("Keluar", "/aset/iconLuminara/icon-masuk-keluar.png", false);
        menuKeluar.setOnMouseClicked(e -> handleLogoutSystem());

        menuContainer.getChildren().addAll(
            menuBeranda, menuAcara, subMenuContainer, menuPeserta, 
            menuPendapatan, menuProfil, subMenuProfilContainer, menuKeluar
        );

        ScrollPane scrollMenu = new ScrollPane(menuContainer);
        scrollMenu.setFitToWidth(true);
        scrollMenu.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollMenu.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollMenu.setStyle("-fx-background-color: transparent; -fx-background: transparent; -fx-padding: 0;");
        
        VBox.setVgrow(scrollMenu, Priority.ALWAYS);

        // Logo bawah
        HBox bottomLogoBox = new HBox(); 
        bottomLogoBox.setAlignment(Pos.CENTER_LEFT); 
        bottomLogoBox.setPadding(new Insets(10, 30, 0, 80));
        ImageView smallLogoView = new ImageView();
        
        try (InputStream smallLogoStream = getClass().getResourceAsStream("/aset/gambarLuminara/luminara-logoWhite.png")) {
            if (smallLogoStream != null) { 
                smallLogoView.setImage(new Image(smallLogoStream)); 
                smallLogoView.setFitWidth(40); 
                smallLogoView.setPreserveRatio(true); 
            }
        } catch (Exception e) {}
        
        bottomLogoBox.getChildren().add(smallLogoView);
        
        sidebar.getChildren().addAll(brandBox, scrollMenu, bottomLogoBox);

        // CENTRAL CONTENT SWITCHER
        contentArea = new StackPane();
        HBox.setHgrow(contentArea, Priority.ALWAYS);
        switchContent(getBerandaContent());

        this.getChildren().addAll(sidebar, contentArea);

        // ==========================================
        // CALL HELPER METHODS
        // ==========================================
        initViews();
        setupNavigationActions(subListAcara);
        
        changeMenuState(menuBeranda);

        try {
        String css = this.getClass().getResource("/style/organizer/beranda.css").toExternalForm();
        this.getStylesheets().add(css);
        } catch (Exception e) {
            System.err.println("❌ Gagal memuat CSS: " + e.getMessage());
        }
    }

    private void loadOrganizerData() {
        System.out.println("Controller: Mencari profil dengan User ID: " + currentUserId);
        OrganizerDAO organizerDAO = new OrganizerDAO();
        
        // Pastikan findByUserId(currentUserId) di dalam OrganizerDAO 
        // melakukan query: SELECT * FROM organizers WHERE user_id = ?
        this.currentOrganizer = organizerDAO.findByUserId(currentUserId);
        
        if (currentOrganizer == null) {
            System.out.println("⚠️ Profil tidak ditemukan.");
        } else {
            // Cek ID yang didapat
            System.out.println("✅ Data Ditemukan: " + currentOrganizer.getName() + " dengan Organizer ID: " + currentOrganizer.getId());
        }
    }

    private void handleLogoutSystem() {
        System.out.println("Controller: User ID " + currentUserId + " keluar dari sistem.");
        
        try {
            javafx.stage.Stage jendelaBaru = new javafx.stage.Stage();
            IntroPage3 introPage = new IntroPage3();
            introPage.start(jendelaBaru); 
            
            javafx.stage.Stage jendelaSaatIni = (javafx.stage.Stage) this.getScene().getWindow();
            if (jendelaSaatIni != null) {
                jendelaSaatIni.close(); 
            }
            
            System.out.println("✅ Logout berhasil. Layar awal ditampilkan.");
        } catch (Exception e) {
            System.out.println("⚠️ Gagal logout: ");
            e.printStackTrace();
        }
    }

    public void refreshSidebarName(String namaBaru) {
        if (lblAdmin != null) {
            lblAdmin.setText(namaBaru);
        }
    }

    public OrganizerProfile getCurrentOrganizer() {
        return this.currentOrganizer;
    }

    public int getCurrentUserId() {
        return this.currentUserId;
    }

    private void initViews() {
        this.pesertaContent = new PesertaView(this);
        this.pendapatanView = new PendapatanView(this);
        this.profilView = new ProfilView(this);
    }

    private void setupNavigationActions(Label subListAcara) {
        menuBeranda.setOnMouseClicked(e -> {
            changeMenuState(menuBeranda); 
            hideSubMenus(); 
            switchContent(getBerandaContent());
        });

        menuAcara.setOnMouseClicked(e -> {
            toggleSubMenuAcara();
            switchContent(getListDetailAcaraContent());
            
            if (subMenuContainer.isVisible()) {
                changeSubMenuState(subListAcara);
            } else {
                changeMenuState(menuAcara);
                if (currentActiveSubMenu != null) {
                    currentActiveSubMenu.getStyleClass().remove("submenu-active");
                    currentActiveSubMenu = null;
                }
            }
        });

        subListAcara.setOnMouseClicked(e -> {
            changeSubMenuState(subListAcara); 
            switchContent(getListDetailAcaraContent());
        });

        subTambahAcara.setOnMouseClicked(e -> {
            changeSubMenuState(subTambahAcara);
            
            int idOrganizer = (currentOrganizer != null) ? currentOrganizer.getId() : 0;
            
            // TAMBAHKAN INI UNTUK DEBUGGING:
            System.out.println("DEBUG: Nilai idOrganizer yang dikirim ke TambahAcaraView adalah: " + idOrganizer);
            
            tambahAcaraContent = new TambahAcaraView(idOrganizer); 
            switchContent(tambahAcaraContent);
        });

        menuPeserta.setOnMouseClicked(e -> {
            changeMenuState(menuPeserta);
            hideSubMenus();
            pesertaContent.tampilkanOverview(); 
            switchContent(pesertaContent);
        });

        menuPendapatan.setOnMouseClicked(e -> {
            changeMenuState(menuPendapatan);
            hideSubMenus();
            pendapatanView.tampilkanOverview(); 
            switchContent(pendapatanView);
        });

        menuProfil.setOnMouseClicked(e -> {
            toggleSubMenuProfil(); 
            profilView.tampilkanLihatProfil(); 
            switchContent(profilView);
            
            changeMenuState(menuProfil);
            if (currentActiveSubMenu != null) {
                currentActiveSubMenu.getStyleClass().remove("submenu-active");
                currentActiveSubMenu = null;
            }
        });

        btnSubEditProfil.setOnMouseClicked(e -> {
            changeMenuState(menuProfil);
            if (currentActiveSubMenu != null) currentActiveSubMenu.getStyleClass().remove("submenu-active");
            btnSubEditProfil.getStyleClass().add("submenu-active");
            currentActiveSubMenu = btnSubEditProfil;
            
            profilView.tampilkanEditProfil(); 
            switchContent(profilView);  
        });

        btnSubLihatUlasan.setOnMouseClicked(e -> {
            changeMenuState(menuProfil);
            if (currentActiveSubMenu != null) {
                currentActiveSubMenu.getStyleClass().remove("submenu-active");
            }
            btnSubLihatUlasan.getStyleClass().add("submenu-active");
            currentActiveSubMenu = btnSubLihatUlasan;
            
            profilView.tampilkanHalamanSemuaUlasan(); 
            switchContent(profilView);
        });
    }

    private void toggleSubMenuAcara() {
        if (subMenuContainer.isVisible()) {
            subMenuContainer.setVisible(false); 
            subMenuContainer.setManaged(false);
        } else {
            hideSubMenus(); 
            subMenuContainer.setVisible(true); 
            subMenuContainer.setManaged(true); 
        }
    }

    private void toggleSubMenuProfil() {
        if (subMenuProfilContainer.isVisible()) {
            subMenuProfilContainer.setVisible(false); 
            subMenuProfilContainer.setManaged(false);
        } else {
            hideSubMenus(); 
            subMenuProfilContainer.setVisible(true); 
            subMenuProfilContainer.setManaged(true);
        }
    }

    private void hideSubMenus() {
        subMenuContainer.setVisible(false); subMenuContainer.setManaged(false);
        subMenuProfilContainer.setVisible(false); subMenuProfilContainer.setManaged(false);
        
        if (currentActiveSubMenu != null) { 
            currentActiveSubMenu.getStyleClass().remove("submenu-active"); 
            currentActiveSubMenu = null; 
        }
    }

    private void changeMenuState(Label targetMenu) {
        if (currentActiveMenu != null) currentActiveMenu.getStyleClass().remove("menu-active");
        targetMenu.getStyleClass().add("menu-active"); 
        currentActiveMenu = targetMenu;

        Label[] semuaMenu = {menuBeranda, menuAcara, menuPeserta, menuPendapatan, menuProfil};
        
        String[] iconBluePaths = {
            "/aset/iconLuminara/branda-biru.png",            
            "/aset/iconLuminara/icon-manajemen-acara.png", 
            "/aset/iconLuminara/icon-peserta.png",
            "/aset/iconLuminara/pendapatan-biru.png", 
            "/aset/iconLuminara/profil-biru.png"
        };
        
        String[] iconWhitePaths = {
            "/aset/iconLuminara/icon-beranda.png",     
            "/aset/iconLuminara/acara-putih.png", 
            "/aset/iconLuminara/icon-komunitas.png", 
            "/aset/iconLuminara/icon-pendapatan.png", 
            "/aset/iconLuminara/icon-user.png"
        };

        for (int i = 0; i < semuaMenu.length; i++) {
            Label menu = semuaMenu[i];
            if (menu == null) continue;

            ImageView imgView = (ImageView) menu.getGraphic();
            if (imgView != null) {
                String pathTerpilih = (menu == targetMenu) ? iconBluePaths[i] : iconWhitePaths[i];
                try (InputStream stream = getClass().getResourceAsStream(pathTerpilih)) {
                    if (stream != null) {
                        imgView.setImage(new Image(stream));
                    }
                } catch (Exception e) {
                    System.out.println("Gagal memuat ikon ke-" + i + ": " + e.getMessage());
                }
            }
        }
    }

    private void changeSubMenuState(Label targetSubMenu) {
        if (currentActiveSubMenu != null) currentActiveSubMenu.getStyleClass().remove("submenu-active");
        targetSubMenu.getStyleClass().add("submenu-active"); currentActiveSubMenu = targetSubMenu;
        changeMenuState(menuAcara);
    }

    public void switchContent(Node newContent) {
        contentArea.getChildren().clear();
        contentArea.getChildren().add(newContent);
    }

    private VBox getBerandaContent() {
        if (berandaContent != null) return berandaContent;
        berandaContent = new VBox(10); berandaContent.setPadding(new Insets(20, 40, 20, 40));

        VBox greetingBox = new VBox(5);
        String sapaan = (currentOrganizer != null) ? "Hai, " + currentOrganizer.getName() + "." : "Hai, tim.";
        Label greeting = new Label(sapaan); greeting.getStyleClass().add("heading");
        Label subGreeting = new Label("Ingat untuk atur kinerja acara kamu . . ."); subGreeting.getStyleClass().add("subheading");
        greetingBox.getChildren().addAll(greeting, subGreeting);

        HBox statCardsBox = new HBox(30);

        // Ganti bagian statCardsBox di getBerandaContent menjadi:
        VBox cardTiket = createStatCard("TIKET TERJUAL", lblTiketTerjual, "/aset/iconLuminara/icon-tiket.png");
        VBox cardPendapatan = createStatCard("TOTAL PENDAPATAN", lblPendapatan, "/aset/iconLuminara/pendapatan-biru.png");
        VBox cardPeserta = createStatCard("PESERTA AKTIF", lblPesertaAktif, "/aset/iconLuminara/icon-peserta.png");

        statCardsBox.getChildren().addAll(
                cardTiket,
                cardPendapatan,
                cardPeserta
        );

        Label sectionTitle = new Label("Acara Mendatang"); sectionTitle.getStyleClass().add("event-section-title"); 

        GridPane miniTableHeader = new GridPane(); miniTableHeader.getStyleClass().add("event-table-header");
        miniTableHeader.setPadding(new Insets(15, 20, 15, 20)); setupMiniTableConstraints(miniTableHeader);
        Label colMini1 = new Label("Nama Acara"); Label colMini2 = new Label("Tanggal & Waktu"); Label colMini3 = new Label("Status");
        colMini1.getStyleClass().add("header-label"); colMini2.getStyleClass().add("header-label"); colMini3.getStyleClass().add("header-label");
        miniTableHeader.add(colMini1, 0, 0); miniTableHeader.add(colMini2, 1, 0); miniTableHeader.add(colMini3, 2, 0);

    VBox miniTableRows = new VBox(7);

    EventDAO dao = new EventDAO();

    List<Event> events =
            dao.findByOrganizerId(currentOrganizer.getId());

    for (Event e : events) {

        String statusDB = e.getStatus();

        String status;

        if ("Draft".equalsIgnoreCase(statusDB)
                || "Pending".equalsIgnoreCase(statusDB)) {

            status = "Belum dikonfirmasi";

        } else if ("Active".equalsIgnoreCase(statusDB)
                || "Approved".equalsIgnoreCase(statusDB)) {

            status = "Telah dikonfirmasi";

        } else {

            status = "Selesai";
        }

        String waktu =
                e.getEventDate() != null
                ? e.getEventDate().toString()
                : "-";

        GridPane row = createMiniTableRow(
                e.getTitle() + "\n" + e.getLocation(),
                waktu,
                status
        );

        miniTableRows.getChildren().add(row);
    }

        berandaContent.getChildren().addAll(greetingBox, statCardsBox, sectionTitle, miniTableHeader, miniTableRows);
        return berandaContent;
    }

    private VBox getListDetailAcaraContent() {
        if (listDetailAcaraContent != null) return listDetailAcaraContent;
        listDetailAcaraContent = new VBox(10); 
        listDetailAcaraContent.setPadding(new Insets(40, 60, 40, 60));

        HBox headerArea = new HBox(); 
        headerArea.setAlignment(Pos.CENTER_LEFT);
        VBox greetingBox = new VBox(5);
        String sapaan = (currentOrganizer != null) ? "Hai, " + currentOrganizer.getName() + "." : "Hai, tim.";
        Label greeting = new Label(sapaan); greeting.getStyleClass().add("heading");
        Label subGreeting = new Label("Bagaimana kabar acaranya . . . ?"); subGreeting.getStyleClass().add("subheading");
        greetingBox.getChildren().addAll(greeting, subGreeting);
        
        Region headerSpacer = new Region(); HBox.setHgrow(headerSpacer, Priority.ALWAYS);
        Button btnTambahAcaraTop = new Button("+   Tambah Acara"); 
        btnTambahAcaraTop.getStyleClass().add("btn-tambah-acara");
        btnTambahAcaraTop.setOnAction(e -> {
            if (subTambahAcara != null) changeSubMenuState(subTambahAcara);
    
            int idOrganizer = (currentOrganizer != null) ? currentOrganizer.getId() : 0;
            tambahAcaraContent = new TambahAcaraView(idOrganizer); 
            switchContent(tambahAcaraContent);
        });
        headerArea.getChildren().addAll(greetingBox, headerSpacer, btnTambahAcaraTop);

        HBox tabContainer = new HBox(25); 
        Label tabAktif = new Label("Aktif"); tabAktif.getStyleClass().addAll("tab-item", "tab-active");
        Label tabMendatang = new Label("Mendatang"); tabMendatang.getStyleClass().add("tab-item");
        Label tabBerlalu = new Label("Berlalu"); tabBerlalu.getStyleClass().add("tab-item");
        tabContainer.getChildren().addAll(tabAktif, tabMendatang, tabBerlalu);

        GridPane tableHeader = new GridPane(); tableHeader.getStyleClass().add("event-table-header");
        tableHeader.setPadding(new Insets(15, 20, 15, 20)); setupFullTableConstraints(tableHeader);
        Label colFull1 = new Label("Detail Acara"); Label colFull2 = new Label("Waktu"); Label colFull3 = new Label("Penjualan Tiket"); Label colFull4 = new Label("Status");
        colFull1.getStyleClass().add("header-label"); colFull2.getStyleClass().add("header-label"); colFull3.getStyleClass().add("header-label"); colFull4.getStyleClass().add("header-label");
        tableHeader.add(colFull1, 0, 0); tableHeader.add(colFull2, 1, 0); tableHeader.add(colFull3, 2, 0); tableHeader.add(colFull4, 3, 0);

        dataTableContainer = new VBox(10); 
        loadAktifData(); 

        ScrollPane innerScroll = new ScrollPane(dataTableContainer);
        innerScroll.setFitToWidth(true); 
        innerScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); 
        innerScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED); 
        innerScroll.setStyle("-fx-background-color: transparent; -fx-background: transparent; -fx-padding: 0;");
        
        VBox.setVgrow(innerScroll, Priority.ALWAYS);

        HBox paginationBox = new HBox(12); paginationBox.setAlignment(Pos.CENTER_RIGHT);
        Button btnPrev = new Button("<"); btnPrev.getStyleClass().add("btn-page-nav");
        Button btnNext = new Button(">"); btnNext.getStyleClass().add("btn-page-action");
        paginationBox.getChildren().addAll(btnPrev, btnNext);

        Label[] tabs = {tabAktif, tabMendatang, tabBerlalu};
        for (Label tab : tabs) {
            tab.setOnMouseClicked(e -> {
                tabAktif.getStyleClass().remove("tab-active"); tabMendatang.getStyleClass().remove("tab-active"); tabBerlalu.getStyleClass().remove("tab-active");
                tab.getStyleClass().add("tab-active"); dataTableContainer.getChildren().clear();
                if (tab.getText().equals("Aktif")) { subGreeting.setText("Bagaimana kabar acaranya . . . ?"); loadAktifData(); } 
                else if (tab.getText().equals("Mendatang")) { subGreeting.setText("Pantau terus acaranya ya . . ."); loadMendatangData(); } 
                else if (tab.getText().equals("Berlalu")) { subGreeting.setText("Acara sudah selesai, ada yang baru . . . ?"); loadBerlaluData(); }
            });
        }

        listDetailAcaraContent.getChildren().addAll(headerArea, tabContainer, tableHeader, innerScroll, paginationBox);
        
        return listDetailAcaraContent;
    }

    private void loadAcaraByStatus(String tabName) {
        dataTableContainer.getChildren().clear();
        EventDAO dao = new EventDAO();
        
        // Pastikan DAO memanggil query yang benar berdasarkan organizer_id (1)
        List<Event> allEvents = dao.findByOrganizerId(currentOrganizer.getId()); 

        if (allEvents != null) {
            for (Event e : allEvents) {
                String statusDB = e.getStatus(); // Status dari DB (misal: "Draft")
                boolean isMatch = false;

                // 🎯 LOGIKA FILTER DIPERBAIKI (Case-insensitive & lebih luas)
                if (tabName.equals("Aktif")) {
                    // Acara yang sudah berjalan atau disetujui
                    if ("Active".equalsIgnoreCase(statusDB) || "Approved".equalsIgnoreCase(statusDB)) isMatch = true;
                } 
                else if (tabName.equals("Mendatang")) {
                    // Acara yang baru dibuat (Draft) atau menunggu proses
                    if ("Draft".equalsIgnoreCase(statusDB) || "Pending".equalsIgnoreCase(statusDB)) isMatch = true;
                } 
                else if (tabName.equals("Berlalu")) {
                    // Acara yang sudah selesai atau ditolak
                    if ("Past".equalsIgnoreCase(statusDB) || "Rejected".equalsIgnoreCase(statusDB)) isMatch = true;
                }

                if (isMatch) {
                    // ... (tampilkan ke UI)
                    GridPane row = createFullTableRow(e.getTitle(), e.getLocation(), 
                                                    e.getEventDate().toString(), "Kuota: " + e.getQuota(), statusDB);
                    dataTableContainer.getChildren().add(row);
                }
            }
        }
    }

private void loadDashboardStats() {
    if (currentOrganizer == null) return;
    
    TicketDAO tDao = new TicketDAO();
    int organizerId = currentOrganizer.getId();
    
    // 1. Ambil data
    int jumlahTiket = tDao.countTicketsByOrganizer(organizerId);
    int pesertaUnik = tDao.countUniqueParticipantsByOrganizer(organizerId);
    double totalPendapatan = tDao.getTotalRevenueByOrganizer(organizerId); // <--- Ambil data pendapatan
    
    // 2. Update UI
    lblTiketTerjual.setText(String.valueOf(jumlahTiket));
    lblPesertaAktif.setText(String.valueOf(pesertaUnik));
    
    // 3. Format ke Rupiah (misal: Rp 1.000.000)
    String formatPendapatan = String.format("Rp %,.0f", totalPendapatan).replace(",", ".");
    lblPendapatan.setText(formatPendapatan);
}

    private void loadAktifData() { loadAcaraByStatus("Aktif"); }
    private void loadMendatangData() { loadAcaraByStatus("Mendatang"); }
    private void loadBerlaluData() { loadAcaraByStatus("Berlalu"); }

    private Label createMenuItem(String text, String iconPath, boolean isActive) {
        Label menuItem = new Label(text); menuItem.setMaxWidth(Double.MAX_VALUE); menuItem.getStyleClass().add("menu-item");
        if (isActive) menuItem.getStyleClass().add("menu-active");
        
        try (InputStream iconStream = getClass().getResourceAsStream(iconPath)) {
            if (iconStream != null) {
                ImageView iconView = new ImageView(new Image(iconStream));
                iconView.setFitWidth(18); iconView.setFitHeight(18); iconView.setPreserveRatio(true);
                menuItem.setGraphic(iconView);
            }
        } catch (Exception e) {}
        return menuItem;
    }

    private Label createSubMenuItem(String text, String iconPath) {
        Label subItem = new Label(text); 
        subItem.setMaxWidth(Double.MAX_VALUE); 
        subItem.getStyleClass().add("submenu-item");
        
        try (InputStream iconStream = getClass().getResourceAsStream(iconPath)) {
            if (iconStream != null) {
                ImageView iconView = new ImageView(new Image(iconStream));
                iconView.setFitWidth(14); 
                iconView.setFitHeight(14); 
                iconView.setPreserveRatio(true);
                subItem.setGraphic(iconView);
            }
        } catch (Exception e) {
            System.out.println("Gagal memuat ikon submenu: " + e.getMessage());
        }
        return subItem;
    }

    private VBox createStatCard(String labelStr, Label valueLabel, String iconPath) {
    // 1. Inisialisasi VBox utama
    VBox card = new VBox(10); 
    card.getStyleClass().add("stat-card");

    // 2. DEKLARASIKAN headerCardBox di sini (ini yang kurang di kode Anda)
    HBox headerCardBox = new HBox(8); 
    headerCardBox.setAlignment(Pos.CENTER_LEFT);

    // 3. Tambahkan Icon ke dalam headerCardBox
    try (java.io.InputStream iconStream = getClass().getResourceAsStream(iconPath)) {
        if (iconStream != null) {
            ImageView iconView = new ImageView(new javafx.scene.image.Image(iconStream));
            iconView.setFitWidth(16); 
            iconView.setFitHeight(16); 
            iconView.setPreserveRatio(true);
            headerCardBox.getChildren().add(iconView);
        }
    } catch (Exception e) {
        System.out.println("Gagal memuat ikon stat card: " + e.getMessage());
    }

    // 4. Tambahkan Label Judul ke headerCardBox
    Label lbl = new Label(labelStr); 
    lbl.getStyleClass().add("stat-label"); 
    headerCardBox.getChildren().add(lbl);

    // 5. Tambahkan Value (Angka) ke subCard
    VBox subCard = new VBox(); 
    subCard.getStyleClass().add("stat-subcard");
    valueLabel.getStyleClass().add("stat-number"); 
    subCard.getChildren().add(valueLabel);

    // 6. Masukkan keduanya ke dalam card
    card.getChildren().addAll(headerCardBox, subCard);
    
    return card;
}



    private GridPane createMiniTableRow(String titleAndLocation, String time, String status) {
        GridPane row = new GridPane(); row.getStyleClass().add("event-card");
        row.setPadding(new Insets(15, 20, 15, 20)); row.setMouseTransparent(true);
        setupMiniTableConstraints(row);
        VBox dBox = new VBox(4); String[] parts = titleAndLocation.split("\n");
        Label t = new Label(parts[0]); t.getStyleClass().add("event-text-main");
        Label l = new Label(parts.length > 1 ? parts[1] : ""); l.getStyleClass().add("event-text-sub");
        dBox.getChildren().addAll(t, l);
        Label lblTime = new Label(time); lblTime.getStyleClass().add("event-text-sub");
        Label lblStat = new Label(status); lblStat.getStyleClass().add("event-text-sub");
        row.add(dBox, 0, 0); row.add(lblTime, 1, 0); row.add(lblStat, 2, 0);
        return row;
    }

    private GridPane createFullTableRow(String title, String location, String time, String ticket, String statusDB) {
        GridPane row = new GridPane(); 
        row.getStyleClass().add("event-card");
        row.setPadding(new Insets(15, 20, 15, 20)); 
        setupFullTableConstraints(row);
        
        VBox dBox = new VBox(4);
        Label t = new Label(title != null ? title : "Tidak ada judul"); t.getStyleClass().add("event-text-main");
        Label l = new Label(location != null ? location : "-"); l.getStyleClass().add("event-text-sub");
        dBox.getChildren().addAll(t, l);
        
        Label lblTime = new Label(time); lblTime.getStyleClass().add("event-text-sub");
        Label lblTick = new Label(ticket); lblTick.getStyleClass().add("event-text-sub");
        
        Label lblStat = new Label(statusDB);
        lblStat.setAlignment(Pos.CENTER);
        
        // 🎯 FIX: Memastikan styling CSS merespons teks dari database (Active, Draft, Past)
        if ("Active".equalsIgnoreCase(statusDB) || "Approved".equalsIgnoreCase(statusDB)) {
            lblStat.getStyleClass().add("badge-status-approved"); 
        } else if ("Draft".equalsIgnoreCase(statusDB) || "Pending".equalsIgnoreCase(statusDB)) {
            lblStat.getStyleClass().add("badge-status-pending");  
        } else {
            // Untuk "Past", "Rejected", "Ditolak" (akan diwarnai merah/abu)
            lblStat.getStyleClass().add("badge-status-rejected"); 
        }

        row.add(dBox, 0, 0); 
        row.add(lblTime, 1, 0); 
        row.add(lblTick, 2, 0); 
        row.add(lblStat, 3, 0);
        return row;
    }

    private void setupMiniTableConstraints(GridPane grid) {
        ColumnConstraints c1 = new ColumnConstraints(); c1.setPercentWidth(50);
        ColumnConstraints c2 = new ColumnConstraints(); c2.setPercentWidth(30);
        ColumnConstraints c3 = new ColumnConstraints(); c3.setPercentWidth(20);
        grid.getColumnConstraints().setAll(c1, c2, c3);
    }

    private void setupFullTableConstraints(GridPane grid) {
        ColumnConstraints c1 = new ColumnConstraints(); c1.setPercentWidth(40);
        ColumnConstraints c2 = new ColumnConstraints(); c2.setPercentWidth(25);
        ColumnConstraints c3 = new ColumnConstraints(); c3.setPercentWidth(20);
        ColumnConstraints c4 = new ColumnConstraints(); c4.setPercentWidth(15);
        grid.getColumnConstraints().setAll(c1, c2, c3, c4);
    }
}