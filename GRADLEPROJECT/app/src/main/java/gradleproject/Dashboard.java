package gradleproject;

import java.io.InputStream;
import java.util.List;
import gradleproject.models.Event;
import gradleproject.models.OrganizerProfile;
import gradleproject.dao.EventDAO;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;


public class Dashboard extends HBox {
    private Stage primaryStage;
    private Runnable onManajemenAcaraClicked;
    
    // Simpan referensi komponen utama agar nilainya bisa diubah dinamis oleh controller
    private Label lblTiketTerjual;
    private Label lblTotalPendapatan;
    private Label lblPesertaAktif;
    private VBox eventSection;

    private OrganizerProfile currentOrganizer;
    private int organizerId;

    public Dashboard(Stage primaryStage, int organizerId) { // Tambahkan parameter int
        this.primaryStage = primaryStage;
        this.organizerId = organizerId;
        
        // 🎯 TAMBAHKAN INI UNTUK MEMASTIKAN ID BENAR
        refreshDashboardData();
        
        loadOrganizerData(); 
        
        this.getStyleClass().add("root");

        // ==========================================
        // 1. SIDEBAR IMPLEMENTATION
        // ==========================================
        VBox sidebar = new VBox(25); 
        sidebar.getStyleClass().add("sidebar"); // Ini tetap dipertahankan
        
        // ---> TAMBAHKAN BARIS INI UNTUK MEMPERBAIKI ASET YANG TERPOTONG <---
        sidebar.setStyle(
            "-fx-background-position: center bottom; " + 
            "-fx-background-size: contain; " + 
            "-fx-background-repeat: no-repeat;"
        );

        sidebar.setPrefWidth(260);
        sidebar.setPadding(new Insets(40, 20, 40, 20));

        // Container Logo Atas (Luminara Text + Subtitle)
        VBox brandBox = new VBox(10);
        brandBox.setAlignment(Pos.CENTER_LEFT);
        brandBox.setPadding(new Insets(25, 5, 10, 10)); 

        ImageView logoView = new ImageView();
        try {
            InputStream logoStream = getClass().getResourceAsStream("/aset/gambarLuminara/luminara-textWhite.png");
            if (logoStream != null) {
                Image logoImage = new Image(logoStream);
                logoView.setImage(logoImage);
                logoView.setFitWidth(150);
                logoView.setPreserveRatio(true);
            }
        } catch (Exception e) {
            System.out.println("Gagal memuat logo utama!");
        }

        Label lblAdmin = new Label("Penyelenggara"); 
        lblAdmin.setStyle("-fx-text-fill: #CCD5DA; -fx-font-size: 16px;");
        brandBox.getChildren().addAll(logoView, lblAdmin);

        // Container Item Menu Utama
        VBox menuContainer = new VBox(10);
        Label menuBeranda = createMenuItem("Beranda", "/aset/iconLuminara/branda-biru.png", true);
        Label menuAcara = createMenuItem("Manajemen Acara", "/aset/iconLuminara/acara-putih.png", false);
        Label menuPeserta = createMenuItem("Peserta", "/aset/iconLuminara/icon-komunitas.png", false);
        Label menuPendapatan = createMenuItem("Pendapatan", "/aset/iconLuminara/icon-pendapatan.png", false);
        Label menuProfil = createMenuItem("Profil", "/aset/iconLuminara/icon-user.png", false);
        Label menuKeluar = createMenuItem("Keluar", "/aset/iconLuminara/icon-masuk-keluar.png", false);

        // ==========================================
        // CONTROLLER BINDING: Aksi Navigasi Sidebar
        // ==========================================
        menuBeranda.setOnMouseClicked(e -> handleMenuBeranda());
        menuAcara.setOnMouseClicked(e -> handleMenuManajemenAcara());
        menuPeserta.setOnMouseClicked(e -> handleMenuPeserta());
        menuPendapatan.setOnMouseClicked(e -> handleMenuPendapatan());
        menuProfil.setOnMouseClicked(e -> handleMenuProfil());
        menuKeluar.setOnMouseClicked(e -> handleMenuKeluar());

        menuContainer.getChildren().addAll(menuBeranda, menuAcara, menuPeserta, menuPendapatan, menuProfil, menuKeluar);

        // ScrollPane untuk menu sidebar
        ScrollPane scrollMenu = new ScrollPane(menuContainer);
        scrollMenu.setFitToWidth(true);
        scrollMenu.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollMenu.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollMenu.setStyle(
            "-fx-background-color: transparent;" +
            "-fx-background: transparent;"
        );

        VBox.setVgrow(scrollMenu, Priority.ALWAYS);

        Region spacerBawah = new Region();

        sidebar.getChildren().addAll(brandBox, scrollMenu, spacerBawah);

        // ==========================================
        // 2. MAIN CONTENT IMPLEMENTATION
        // ==========================================
        VBox mainContent = new VBox(20);
        mainContent.setPadding(new Insets(20, 60, 40, 60));
        // Hapus HBox.setHgrow(mainContent, Priority.ALWAYS); // Kita tidak pakai ini lagi

        VBox headerArea = new VBox(7);
        Label greeting = new Label("Hai, Zahwa Dwi Putri."); // Saya sesuaikan dengan nama di screenshot
        greeting.getStyleClass().add("heading");
        Label subGreeting = new Label("Ingat untuk atur kinerja acara kamu . . .");
        subGreeting.getStyleClass().add("subheading");
        headerArea.getChildren().addAll(greeting, subGreeting);

        // Cards Statistik
        HBox statsContainer = new HBox(30);
        statsContainer.setAlignment(Pos.CENTER_LEFT);
        // ... (kode createStatCard tetap sama)
        lblTiketTerjual = new Label("1,250");
        lblTotalPendapatan = new Label("37jt");
        lblPesertaAktif = new Label("102");
        statsContainer.getChildren().addAll(
            createStatCard("TIKET TERJUAL", lblTiketTerjual, "/aset/iconLuminara/icon-tiket.png"),
            createStatCard("TOTAL PENDAPATAN", lblTotalPendapatan, "/aset/iconLuminara/pendapatan-biru.png"),
            createStatCard("PESERTA AKTIF", lblPesertaAktif, "/aset/iconLuminara/icon-komunitas.png")
        );

        // Bagian Tabel
        eventSection = new VBox(8);
        Label sectionTitle = new Label("Acara Mendatang");
        sectionTitle.getStyleClass().add("event-section-title");
        // ... (kode tableHeader tetap sama)
        eventSection.getChildren().addAll(sectionTitle, headerArea);
        
        // Refresh Data
        refreshDashboardData();

        // 🎯 MEMBUNGKUS SELURUH KONTEN KE DALAM SCROLLPANE
        mainContent.getChildren().addAll(
            headerArea,
            statsContainer,
            eventSection // eventSection sekarang sudah berisi sectionTitle dan tabel
        );

        // 2. Bungkus mainContent ke dalam satu ScrollPane besar
        ScrollPane mainScrollPane = new ScrollPane(mainContent);
        mainScrollPane.setFitToWidth(true); // Penting agar konten tidak melebar ke samping
        mainScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        mainScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        mainScrollPane.setStyle("-fx-background-color: transparent; -fx-background: transparent;");
        HBox.setHgrow(mainScrollPane, Priority.ALWAYS);

        // 3. Masukkan sidebar dan mainScrollPane ke HBox utama
        this.getChildren().addAll(sidebar, mainScrollPane);
        
        System.out.println("DEBUG: Dashboard telah dimuat sepenuhnya.");
        }

    // ==========================================
    // LOGIKA CONTROLLER (INTERNAL METHOD)
    // ==========================================
    
    /**
     * Memuat atau memperbarui data statis dan baris tabel dinamis
     */
    private void refreshDashboardData() {
    // 1. Reset tampilan
    System.out.println("DEBUG: Memulai refreshDashboardData..."); // Tambahkan ini
    
    // Hapus baris lama
    if (eventSection.getChildren().size() > 2) {
        eventSection.getChildren().remove(2, eventSection.getChildren().size());
    }

    // Cek apakah organizer sudah dimuat
    if (this.currentOrganizer == null) {
        System.out.println("DEBUG: GAGAL REFRESH - currentOrganizer masih NULL");
        return;
    }

    System.out.println("DEBUG: Mencari acara untuk Organizer ID: " + this.currentOrganizer.getId());
    
    EventDAO eventDAO = new EventDAO();
    List<Event> daftarAcara = eventDAO.findByOrganizerId(this.currentOrganizer.getId());

    System.out.println("DEBUG: Acara ditemukan: " + (daftarAcara != null ? daftarAcara.size() : "NULL"));

    if (daftarAcara != null) {
        for (Event e : daftarAcara) {
            System.out.println("DEBUG: Menambahkan acara ke UI: " + e.getTitle() + " | Status: " + e.getStatus());
    }

    if (daftarAcara != null) {
        for (Event e : daftarAcara) {
            String status = e.getStatus(); // "Draft", "Active", "Past"
            String displayStatus = "";
            
            // 🎯 LOGIKA: Ubah status DB menjadi teks yang manusiawi
            if ("Draft".equalsIgnoreCase(status) || "Pending".equalsIgnoreCase(status)) {
                displayStatus = "Belum dikonfirmasi";
            } else if ("Active".equalsIgnoreCase(status) || "Approved".equalsIgnoreCase(status)) {
                displayStatus = "Telah dikonfirmasi";
            } else {
                displayStatus = "Selesai";
            }

            // Tampilkan baris
            String waktu = (e.getEventDate() != null) ? e.getEventDate().toString() : "-";
            GridPane row = createEventRow(e.getTitle(), e.getLocation(), waktu, displayStatus);
            eventSection.getChildren().add(row);
            }
        }
    }
}

    public void handleMenuBeranda() {
        System.out.println("Sistem: Memuat ulang Dashboard Beranda...");
        
        // 🎯 Cukup panggil method refreshDashboardData() secara langsung
        // Karena method ini berada di dalam class yang sama (Dashboard)
        this.refreshDashboardData();
    }

    private void handleMenuManajemenAcara() {
        System.out.println("Sistem: Membuka Manajemen Acara...");
        if (onManajemenAcaraClicked != null) {
            onManajemenAcaraClicked.run();
        }
    }

    private void handleMenuPeserta() {
        System.out.println("Sistem: Mengalihkan ke Halaman Manajemen Peserta...");
        // Tambahkan inisialisasi halaman peserta Anda di sini jika sudah ada
    }

    private void handleMenuPendapatan() {
        System.out.println("Sistem: Mengalihkan ke Halaman Laporan Pendapatan...");
    }

    private void handleMenuProfil() {
        System.out.println("Sistem: Mengalihkan ke Pengaturan Profil...");
    }

    private void handleMenuKeluar() {
        System.out.println("Sistem: Memproses logout menggunakan primaryStage...");
        
        try {
            // 1. Buat jendela (Stage) baru khusus untuk halaman Intro
            Stage jendelaBaru = new Stage();
            IntroPage3 introPage = new IntroPage3();
            introPage.start(jendelaBaru); 
            
            // 2. GUNAKAN primaryStage untuk MENUTUP dashboard lama
            if (this.primaryStage != null) {
                this.primaryStage.close(); 
            } else {
                // Cadangan otomatis jika ternyata primaryStage bernilai null
                Stage jendelaSaatIni = (Stage) this.getScene().getWindow();
                if (jendelaSaatIni != null) {
                    jendelaSaatIni.close();
                }
            }
            
            System.out.println("✅ Logout berhasil. Layar awal ditampilkan.");
        } catch (Exception e) {
            System.out.println("⚠️ Gagal logout: ");
            e.printStackTrace();
        }
    }

    // ==========================================
    // UI HELPER METHODS
    // ==========================================
    
    private Label createMenuItem(String text, String iconPath, boolean isActive) {
        Label menuItem = new Label(text);
        menuItem.setMaxWidth(Double.MAX_VALUE);
        menuItem.getStyleClass().add("menu-item");
        menuItem.setCursor(javafx.scene.Cursor.HAND);
        
        if (isActive) {
            menuItem.getStyleClass().add("menu-active");
        }

        try {
            InputStream iconStream = getClass().getResourceAsStream(iconPath);
            if (iconStream != null) {
                Image iconImg = new Image(iconStream);
                ImageView iconView = new ImageView(iconImg);
                iconView.setFitWidth(18);
                iconView.setFitHeight(18);
                iconView.setPreserveRatio(true);
                menuItem.setGraphic(iconView);
            }
        } catch (Exception e) {
            System.out.println("Gagal memuat icon menu: " + text);
        }
       
        menuItem.setOnMouseEntered(e -> {
            if (!isActive) {
                menuItem.setStyle("-fx-background-color: rgba(204, 213, 218, 0.15); -fx-text-fill: #FFFFFF;");
            }
        });
        
        menuItem.setOnMouseExited(e -> {
            if (!isActive) {
                menuItem.setStyle(""); 
            }
        });
        
        return menuItem;
    }

    private VBox createStatCard(String labelText, Label numberLabel, String iconPath) {
        VBox card = new VBox(12); 
        card.getStyleClass().add("stat-card");
        card.setPrefWidth(240);

        HBox labelBox = new HBox(8); 
        labelBox.setAlignment(Pos.CENTER_LEFT);

        ImageView iconView = new ImageView();
        try {
            InputStream iconStream = getClass().getResourceAsStream(iconPath);
            if (iconStream != null) {
                Image iconImg = new Image(iconStream);
                iconView.setImage(iconImg);
                iconView.setFitWidth(16);  
                iconView.setFitHeight(16);
                iconView.setPreserveRatio(true);
                labelBox.getChildren().add(iconView);
            }
        } catch (Exception e) {
            System.out.println("⚠️ Gagal memuat ikon stat: " + labelText);
        }

        Label label = new Label(labelText);
        label.getStyleClass().add("stat-label");
        labelBox.getChildren().add(label);

        HBox subCardBox = new HBox();
        subCardBox.getStyleClass().add("stat-subcard");
        subCardBox.setMaxWidth(Double.MAX_VALUE); 
        subCardBox.setAlignment(Pos.CENTER); 

        // Hubungkan label text style dari stylesheet eksternal
        numberLabel.getStyleClass().add("stat-number");
        subCardBox.getChildren().add(numberLabel);

        card.getChildren().addAll(labelBox, subCardBox);
        return card;
    }

    private GridPane createEventRow(String title, String location, String dateTime, String status) {
        GridPane row = new GridPane();
        row.getStyleClass().add("event-card");
        setupRowConstraints(row);

        VBox eventDetail = new VBox(4);
        Label lblTitle = new Label(title);
        lblTitle.getStyleClass().add("event-text-main");
        
        Label lblLocation = new Label(location);
        lblLocation.getStyleClass().add("event-text-sub");
        eventDetail.getChildren().addAll(lblTitle, lblLocation);

        Label lblDateTime = new Label(dateTime);
        lblDateTime.getStyleClass().add("event-text-sub");

        Label lblStatus = new Label(status);
        lblStatus.getStyleClass().add("event-status");

        row.add(eventDetail, 0, 0);
        row.add(lblDateTime, 1, 0);
        row.add(lblStatus, 2, 0);

        return row;
    }

    private void setupRowConstraints(GridPane grid) {
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(50);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(30);
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setPercentWidth(20);
        grid.getColumnConstraints().addAll(col1, col2, col3);
    }

    public void setOnManajemenAcaraClicked(Runnable callback) {
        this.onManajemenAcaraClicked = callback;
    }

    private void loadOrganizerData() {
        System.out.println("DEBUG: Memulai loadOrganizerData...");
        
        gradleproject.dao.OrganizerDAO organizerDAO = new gradleproject.dao.OrganizerDAO();
        this.currentOrganizer = organizerDAO.findByUserId(this.organizerId);
        
        if (this.currentOrganizer == null) {
            System.out.println("⚠️ ERROR: currentOrganizer tetap NULL. Cek database!");
        } else {
            System.out.println("✅ SUKSES: currentOrganizer berhasil dimuat. ID: " + this.currentOrganizer.getId());
        }
    }


    public Scene createScene() {
        return new Scene(this, 1280, 650);
    }

    
}