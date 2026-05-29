package gradleproject;

import java.io.InputStream;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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

    // Komponen Sidebar
    private Label currentActiveMenu;
    private Label currentActiveSubMenu;
    private Label subTambahAcara;
    private final VBox subMenuContainer; // Diperbaiki: Deklarasi variabel dipisahkan dari inisialisasi objek
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

    public ManajemenAcaraView() {
        this.getStyleClass().add("root");

        // ==========================================
        // SIDEBAR LAYOUT GENERATION
        // ==========================================
        VBox sidebar = new VBox(20); 
        sidebar.getStyleClass().add("sidebar");
        sidebar.setPrefWidth(260); sidebar.setMinWidth(260);
        sidebar.setPadding(new Insets(40, 20, 20, 20));

        // Brand Logo
        VBox brandBox = new VBox(5);
        brandBox.setAlignment(Pos.CENTER_LEFT);
        brandBox.setPadding(new Insets(0, 0, 10, 10)); 
        ImageView logoView = new ImageView();
        try {
            InputStream logoStream = getClass().getResourceAsStream("/aset/gambarLuminara/luminara-textWhite.png");
            if (logoStream != null) {
                logoView.setImage(new Image(logoStream));
                logoView.setFitWidth(150); logoView.setPreserveRatio(true);
            }
        } catch (Exception e) {}
        Label lblAdmin = new Label("Penyelenggara"); 
        lblAdmin.setStyle("-fx-text-fill: #CCD5DA; -fx-font-size: 14px; -fx-font-family: 'Poppins';");
        brandBox.getChildren().addAll(logoView, lblAdmin);

        // Menu Items
        VBox menuContainer = new VBox(10);
        
        // FAIL-SAFE INITIALIZATION: Samakan dengan daftar iconWhitePaths di bawah
        menuBeranda = createMenuItem("Beranda", "/aset/iconLuminara/icon-beranda.png", true);
        currentActiveMenu = menuBeranda;

        menuAcara = createMenuItem("Manajemen Acara", "/aset/iconLuminara/acara-putih.png", false);
        
        // Sub-menu Manajemen Acara (Ditambahkan Ikon)
        subMenuContainer = new VBox();
        subMenuContainer.getStyleClass().add("submenu-container");
        subMenuContainer.setManaged(false); subMenuContainer.setVisible(false); 
        
        Label subListAcara = createSubMenuItem("List Detail Acara", "/aset/iconLuminara/icon-tiket.png");
        subTambahAcara = createSubMenuItem("Tambah Acara", "/aset/iconLuminara/icon-tiket.png");
        subMenuContainer.getChildren().addAll(subListAcara, subTambahAcara);

        menuPeserta = createMenuItem("Peserta", "/aset/iconLuminara/icon-komunitas.png", false);
        menuPendapatan = createMenuItem("Pendapatan", "/aset/iconLuminara/icon-pendapatan.png", false);
        menuProfil = createMenuItem("Profil", "/aset/iconLuminara/icon-user.png", false);
        
        // Sub-menu Profil (Ditambahkan Ikon agar tidak error Method Mismatch)
        subMenuProfilContainer = new VBox();
        subMenuProfilContainer.getStyleClass().add("submenu-container");
        subMenuProfilContainer.setManaged(false); 
        subMenuProfilContainer.setVisible(false); 
        
        btnSubEditProfil = createSubMenuItem("Edit Profil", "/aset/iconLuminara/icon-masuk-keluar.png");
        btnSubLihatUlasan = createSubMenuItem("Lihat Ulasan", "/aset/iconLuminara/ulasan-biru.png");
        subMenuProfilContainer.getChildren().addAll(btnSubEditProfil, btnSubLihatUlasan);

        Label menuKeluar = createMenuItem("Keluar", "/aset/iconLuminara/icon-masuk-keluar.png", false);
        menuKeluar.setOnMouseClicked(e -> javafx.application.Platform.exit());

        menuContainer.getChildren().addAll(
            menuBeranda, menuAcara, subMenuContainer, menuPeserta, 
            menuPendapatan, menuProfil, subMenuProfilContainer, menuKeluar
        );

        Region spacerBawah = new Region(); VBox.setVgrow(spacerBawah, Priority.ALWAYS);
        HBox bottomLogoBox = new HBox(); bottomLogoBox.setAlignment(Pos.CENTER_LEFT); bottomLogoBox.setPadding(new Insets(10, 30, 0, 80));
        ImageView smallLogoView = new ImageView();
        try {
            InputStream smallLogoStream = getClass().getResourceAsStream("/aset/gambarLuminara/luminara-logoWhite.png");
            if (smallLogoStream != null) { 
                smallLogoView.setImage(new Image(smallLogoStream)); 
                smallLogoView.setFitWidth(40); smallLogoView.setPreserveRatio(true); 
            }
        } catch (Exception e) {}
        bottomLogoBox.getChildren().add(smallLogoView);
        sidebar.getChildren().addAll(brandBox, menuContainer, spacerBawah, bottomLogoBox);

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
        
        // Trigger state awal agar Beranda langsung berwarna biru saat aplikasi dibuka
        changeMenuState(menuBeranda);
    }

    private void initViews() {
        this.pesertaContent = new PesertaView(this);
        this.pendapatanView = new PendapatanView(this);
        this.profilView = new ProfilView(this);
    }

    private void setupNavigationActions(Label subListAcara) {
        // Beranda Action
        menuBeranda.setOnMouseClicked(e -> {
            changeMenuState(menuBeranda); 
            hideSubMenus(); 
            switchContent(getBerandaContent());
        });

        // Manajemen Acara Action Toggle
        menuAcara.setOnMouseClicked(e -> toggleSubMenuAcara());

        subListAcara.setOnMouseClicked(e -> {
            changeSubMenuState(subListAcara); 
            switchContent(getListDetailAcaraContent());
        });

        subTambahAcara.setOnMouseClicked(e -> {
            changeSubMenuState(subTambahAcara);
            if (tambahAcaraContent == null) tambahAcaraContent = new TambahAcaraView();
            switchContent(tambahAcaraContent);
        });

        // Peserta Action
        menuPeserta.setOnMouseClicked(e -> {
            changeMenuState(menuPeserta);
            hideSubMenus();
            pesertaContent.tampilkanOverview(); 
            switchContent(pesertaContent);
        });

        // Pendapatan Action
        menuPendapatan.setOnMouseClicked(e -> {
            changeMenuState(menuPendapatan);
            hideSubMenus();
            pendapatanView.tampilkanOverview(); 
            switchContent(pendapatanView);
        });

        // Profil Utama Action Toggle
        menuProfil.setOnMouseClicked(e -> {
            toggleSubMenuProfil(); 
            profilView.tampilkanLihatProfil(); 
            switchContent(profilView);
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
            subMenuContainer.setVisible(false); subMenuContainer.setManaged(false);
            changeMenuState(currentActiveMenu);
        } else {
            hideSubMenus(); 
            subMenuContainer.setVisible(true); subMenuContainer.setManaged(true); 
            changeMenuState(menuAcara);
        }
    }

    private void toggleSubMenuProfil() {
        if (subMenuProfilContainer.isVisible()) {
            subMenuProfilContainer.setVisible(false); 
            subMenuProfilContainer.setManaged(false);
            changeMenuState(currentActiveMenu);
        } else {
            hideSubMenus(); 
            subMenuProfilContainer.setVisible(true); 
            subMenuProfilContainer.setManaged(true);
            changeMenuState(menuProfil);
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
                try {
                    String pathTerpilih = (menu == targetMenu) ? iconBluePaths[i] : iconWhitePaths[i];
                    InputStream stream = getClass().getResourceAsStream(pathTerpilih);
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

    // =========================================================
    // LAYOUT KONTEN NYATA & SELEBIHNYA
    // =========================================================
    private VBox getBerandaContent() {
        if (berandaContent != null) return berandaContent;
        berandaContent = new VBox(10); berandaContent.setPadding(new Insets(20, 40, 20, 40));

        VBox greetingBox = new VBox(5);
        Label greeting = new Label("Hai, tim."); greeting.getStyleClass().add("heading");
        Label subGreeting = new Label("Ingat untuk atur kinerja acara kamu . . ."); subGreeting.getStyleClass().add("subheading");
        greetingBox.getChildren().addAll(greeting, subGreeting);

        HBox statCardsBox = new HBox(30);
        VBox cardTiket = createStatCard("TIKET TERJUAL", "1,250", "/aset/iconLuminara/icon-tiket.png"); 
        VBox cardPendapatan = createStatCard("TOTAL PENDAPATAN", "37jt", "/aset/iconLuminara/pendapatan-biru.png"); 
        VBox cardPeserta = createStatCard("PESERTA AKTIF", "102", "/aset/iconLuminara/icon-peserta.png"); 
        statCardsBox.getChildren().addAll(cardTiket, cardPendapatan, cardPeserta);

        Label sectionTitle = new Label("Acara Mendatang"); sectionTitle.getStyleClass().add("event-section-title"); 

        GridPane miniTableHeader = new GridPane(); miniTableHeader.getStyleClass().add("event-table-header");
        miniTableHeader.setPadding(new Insets(15, 20, 15, 20)); setupMiniTableConstraints(miniTableHeader);
        Label colMini1 = new Label("Nama Acara"); Label colMini2 = new Label("Tanggal & Waktu"); Label colMini3 = new Label("Status");
        colMini1.getStyleClass().add("header-label"); colMini2.getStyleClass().add("header-label"); colMini3.getStyleClass().add("header-label");
        miniTableHeader.add(colMini1, 0, 0); miniTableHeader.add(colMini2, 1, 0); miniTableHeader.add(colMini3, 2, 0);

        VBox miniTableRows = new VBox(7);
        GridPane mRow1 = createMiniTableRow("Makassar Traditional Costume Showcase\nTrans Studio Mall Makassar", "2026, Mei 26-27\n19:00:00 - 22:00:00", "Telah dikonfirmasi");
        GridPane mRow2 = createMiniTableRow("Akustik: Cerita Tanah Makassar\nTrans Studio Mall Makassar", "2026, Juni 2\n19:00:00 - 22:00:00", "Belum dikonfirmasi");
        GridPane mRow3 = createMiniTableRow("Akustik: Cerita Tanah Makassar\nTrans Studio Mall Makassar", "2026, Juni 2\n19:00:00 - 22:00:00", "Belum dikonfirmasi");
        miniTableRows.getChildren().addAll(mRow1, mRow2, mRow3);

        berandaContent.getChildren().addAll(greetingBox, statCardsBox, sectionTitle, miniTableHeader, miniTableRows);
        return berandaContent;
    }

    private VBox getListDetailAcaraContent() {
        if (listDetailAcaraContent != null) return listDetailAcaraContent;
        listDetailAcaraContent = new VBox(10); listDetailAcaraContent.setPadding(new Insets(40, 60, 40, 60));

        HBox headerArea = new HBox(); headerArea.setAlignment(Pos.CENTER_LEFT);
        VBox greetingBox = new VBox(5);
        Label greeting = new Label("Hai, tim."); greeting.getStyleClass().add("heading");
        Label subGreeting = new Label("Bagaimana kabar acaranya . . . ?"); subGreeting.getStyleClass().add("subheading");
        greetingBox.getChildren().addAll(greeting, subGreeting);
        
        Region headerSpacer = new Region(); HBox.setHgrow(headerSpacer, Priority.ALWAYS);
        Button btnTambahAcaraTop = new Button("+   Tambah Acara"); btnTambahAcaraTop.getStyleClass().add("btn-tambah-acara");
        btnTambahAcaraTop.setOnAction(e -> {
            if (subTambahAcara != null) changeSubMenuState(subTambahAcara); 
            tambahAcaraContent = new TambahAcaraView(); switchContent(tambahAcaraContent);
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

        dataTableContainer = new VBox(10); loadAktifData(); 

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

        listDetailAcaraContent.getChildren().addAll(headerArea, tabContainer, tableHeader, dataTableContainer, paginationBox);
        return listDetailAcaraContent;
    }

    private void loadAktifData() {
        GridPane row1 = createFullTableRow("Makassar Traditional Costume Showcase", "Trans Studio Mall Makassar", "2026, Mei 20-22\n19:00:00 - 22:00:00", "11/100\n11%", "Tersedia", true);
        GridPane row2 = createFullTableRow("Legenda Makassar Storytelling Corner", "Trans Studio Mall Makassar", "2026, Mei 19-21\n10:00:00 - 14:00:00", "50/50\n100%", "Full", false);
        GridPane row3 = createFullTableRow("Pappaseng Culture Fest", "Kawasan Center Point of Indonesia (CPI)", "2026, Mei 20-21\n16:00:00 - 20:00:00", "43/50\n86%", "Tersedia", true);
        GridPane row4 = createFullTableRow("Pelatihan Berbicara Bahasa Makassar", "Benteng Rotterdam", "2026, Mei 20\n09:00:00 - 11:00:00", "15/20\n75%", "Tersedia", true);
        dataTableContainer.getChildren().addAll(row1, row2, row3, row4);
    }

    private void loadMendatangData() {
        GridPane row1 = createFullTableRow("Pameran Budaya I Lagaligo", "Trans Studio Mall Makassar", "2026, Juni 15-17\n19:00:00 - 22:00:00", "90/100\n90%", "Tersedia", true);
        GridPane row2 = createFullTableRow("Gowa Heritage Festival\n& Kingdom Festival", "Trans Studio Mall Makassar", "2026, Juni 19-21\n20:00:00 - 23:30:00", "150/150\n100%", "Full", false);
        dataTableContainer.getChildren().addAll(row1, row2);
    }

    private void loadBerlaluData() {
        GridPane row1 = createFullTableRow("Kecapi & Sulawesi Traditional Ensemble", "Kawasan Anjungan Pantai Losari", "2026, April 20-22\n19:00:00 - 22:00:00", "100/100\n100%", "Full", false);
        GridPane row2 = createFullTableRow("Makassar Islamic & Tradisi Festival", "Trans Studio Mall Makassar", "2026, Maret 18\n10:00:00 - 14:00:00", "50/50\n100%", "Full", false);
        GridPane row3 = createFullTableRow("Gandrang Bulo Rhythm Performance", "Kawasan Center Point of Indonesia (CPI)", "2026, Januari 10-11\n16:00:00 - 20:00:00", "100/200\n50%", "Tersedia", true);
        GridPane row4 = createFullTableRow("Pelatihan Berbicara Bahasa Makassar", "Benteng Rotterdam", "2025, Desember 30-31\n09:00:00 - 11:00:00", "15/20\n75%", "Tersedia", true);
        dataTableContainer.getChildren().addAll(row1, row2, row3, row4);
    }

    private Label createMenuItem(String text, String iconPath, boolean isActive) {
        Label menuItem = new Label(text); menuItem.setMaxWidth(Double.MAX_VALUE); menuItem.getStyleClass().add("menu-item");
        if (isActive) menuItem.getStyleClass().add("menu-active");
        try {
            InputStream iconStream = getClass().getResourceAsStream(iconPath);
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
        
        try {
            InputStream iconStream = getClass().getResourceAsStream(iconPath);
            if (iconStream != null) {
                ImageView iconView = new ImageView(new Image(iconStream));
                // Ukuran ikon submenu dibuat sedikit lebih kecil (14px) agar ada hierarki visual yang rapi
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

    private VBox createStatCard(String labelStr, String numberStr, String iconPath) {
        VBox card = new VBox(10); card.getStyleClass().add("stat-card");
        HBox headerCardBox = new HBox(8); headerCardBox.setAlignment(Pos.CENTER_LEFT);
        try {
            InputStream iconStream = getClass().getResourceAsStream(iconPath);
            if (iconStream != null) {
                ImageView iconView = new ImageView(new Image(iconStream));
                iconView.setFitWidth(16); iconView.setFitHeight(16); iconView.setPreserveRatio(true);
                headerCardBox.getChildren().add(iconView);
            }
        } catch (Exception e) {}
        Label lbl = new Label(labelStr); lbl.getStyleClass().add("stat-label"); headerCardBox.getChildren().add(lbl);
        VBox subCard = new VBox(); subCard.getStyleClass().add("stat-subcard");
        Label num = new Label(numberStr); num.getStyleClass().add("stat-number"); subCard.getChildren().add(num);
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

    private GridPane createFullTableRow(String title, String location, String time, String ticket, String status, boolean avail) {
        GridPane row = new GridPane(); row.getStyleClass().add("event-card");
        row.setPadding(new Insets(15, 20, 15, 20)); row.setMouseTransparent(true);
        setupFullTableConstraints(row);
        VBox dBox = new VBox(4);
        Label t = new Label(title); t.getStyleClass().add("event-text-main");
        Label l = new Label(location); l.getStyleClass().add("event-text-sub");
        dBox.getChildren().addAll(t, l);
        Label lblTime = new Label(time); lblTime.getStyleClass().add("event-text-sub");
        Label lblTick = new Label(ticket); lblTick.getStyleClass().add("event-text-sub");
        Label lblStat = new Label(status); lblStat.setAlignment(Pos.CENTER);
        lblStat.getStyleClass().add(avail ? "badge-status-available" : "badge-status-full");
        row.add(dBox, 0, 0); row.add(lblTime, 1, 0); row.add(lblTick, 2, 0); row.add(lblStat, 3, 0);
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
    
    public static class AcaraMock {
        public String nama, tanggal, waktu, lokasi, terdaftar, kuota;
        public AcaraMock(String n, String t, String w, String l, String terdap, String kuo) {
            this.nama = n; this.tanggal = t; this.waktu = w; this.lokasi = l; this.terdaftar = terdap; this.kuota = kuo;
        }
    }
}