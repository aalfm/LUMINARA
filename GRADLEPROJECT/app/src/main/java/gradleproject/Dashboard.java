package gradleproject;

import java.io.InputStream;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

public class Dashboard extends HBox {
    private Runnable onManajemenAcaraClicked;

    public Dashboard() {
        // Menggunakan stylesheet root utama
        this.getStyleClass().add("root");

        // ==========================================
        // 1. SIDEBAR IMPLEMENTATION
        // ==========================================
        VBox sidebar = new VBox(25); 
        sidebar.getStyleClass().add("sidebar");
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
        VBox menuContainer = new VBox(12);
        Label menuBeranda = createMenuItem("Beranda", "/aset/iconLuminara/branda-biru.png", true);
        Label menuAcara = createMenuItem("Manajemen Acara", "/aset/iconLuminara/acara-putih.png", false);
        menuAcara.setOnMouseClicked(e -> {
            if (onManajemenAcaraClicked != null) {
                onManajemenAcaraClicked.run();
            }
        });
        Label menuPeserta = createMenuItem("Peserta", "/aset/iconLuminara/icon-komunitas.png", false);
        Label menuPendapatan = createMenuItem("Pendapatan", "/aset/iconLuminara/icon-pendapatan.png", false);
        Label menuProfil = createMenuItem("Profil", "/aset/iconLuminara/icon-user.png", false);
        Label menuKeluar = createMenuItem("Keluar", "/aset/iconLuminara/icon-masuk-keluar.png", false);

        menuContainer.getChildren().addAll(menuBeranda, menuAcara, menuPeserta, menuPendapatan, menuProfil, menuKeluar);

        // Spacer diletakkan setelah Menu Keluar untuk mendorong Logo ke paling bawah
        Region spacerBawah = new Region();
        VBox.setVgrow(spacerBawah, Priority.ALWAYS);

        // Container Logo Kecil di Bagian Paling Bawah Sidebar (Sesuai Mockup)
        HBox bottomLogoBox = new HBox();
        bottomLogoBox.setAlignment(Pos.CENTER_LEFT);
        bottomLogoBox.setPadding(new Insets(10, 0, 30,80));
        ImageView smallLogoView = new ImageView();
        try {
            InputStream smallLogoStream = getClass().getResourceAsStream("/aset/gambarLuminara/luminara-logoWhite.png");
            if (smallLogoStream != null) {
                Image smallLogo = new Image(smallLogoStream);
                smallLogoView.setImage(smallLogo);
                smallLogoView.setFitWidth(40);
                smallLogoView.setPreserveRatio(true);
            }
        } catch (Exception e) {
            System.out.println("⚠️ Gagal memuat logo kecil bawah!");
        }
        bottomLogoBox.getChildren().add(smallLogoView);

        // Susun komponen sidebar
        sidebar.getChildren().addAll(brandBox, menuContainer, spacerBawah, bottomLogoBox);

        // ==========================================
        // 2. MAIN CONTENT IMPLEMENTATION
        // ==========================================
        VBox mainContent = new VBox(20);
        mainContent.setPadding(new Insets(20, 60, 40, 60));
        HBox.setHgrow(mainContent, Priority.ALWAYS);

        VBox headerArea = new VBox(7);
        Label greeting = new Label("Hai, tim.");
        greeting.getStyleClass().add("heading");
        Label subGreeting = new Label("Ingat untuk atur kinerja acara kamu . . .");
        subGreeting.getStyleClass().add("subheading");
        headerArea.getChildren().addAll(greeting, subGreeting);

        HBox statsContainer = new HBox(30);
        statsContainer.setAlignment(Pos.CENTER_LEFT);

        VBox card1 = createStatCard("TIKET TERJUAL", "1,250", "/aset/iconLuminara/icon-tiket.png"); 
        VBox card2 = createStatCard("TOTAL PENDAPATAN", "37jt", "/aset/iconLuminara/pendapatan-biru.png"); 
        VBox card3 = createStatCard("PESERTA AKTIF", "102", "/aset/iconLuminara/icon-peserta.png"); 

        statsContainer.getChildren().addAll(card1, card2, card3);

        VBox eventSection = new VBox(8);
        Label sectionTitle = new Label("Acara Mendatang");
        sectionTitle.getStyleClass().add("event-section-title");

        GridPane tableHeader = new GridPane();
        tableHeader.getStyleClass().add("event-table-header");
        setupRowConstraints(tableHeader);
        
        Label col1 = new Label("Nama Acara");
        Label col2 = new Label("Tanggal & Waktu");
        Label col3 = new Label("Status");
        col1.getStyleClass().add("header-label");
        col2.getStyleClass().add("header-label");
        col3.getStyleClass().add("header-label");
        tableHeader.add(col1, 0, 0);
        tableHeader.add(col2, 1, 0);
        tableHeader.add(col3, 2, 0);

        GridPane row1 = createEventRow("Makassar Traditional Costume Showcase", "Trans Studio Mall Makassar", "2026, Mei 26-27\n19:00:00 - 22:00:00", "Telah dikonfirmasi");
        GridPane row2 = createEventRow("Akustik: Cerita Tanah Makassar", "Trans Studio Mall Makassar", "2026, Juni 2\n19:00:00 - 22:00:00", "Belum dikonfirmasi");
        GridPane row3 = createEventRow("Akustik: Cerita Tanah Makassar", "Trans Studio Mall Makassar", "2026, Juni 2\n19:00:00 - 22:00:00", "Belum dikonfirmasi");

        eventSection.getChildren().addAll(sectionTitle, tableHeader, row1, row2, row3);
        mainContent.getChildren().addAll(headerArea, statsContainer, eventSection);

        this.getChildren().addAll(sidebar, mainContent);
    }

    private Label createMenuItem(String text, String iconPath, boolean isActive) {
        Label menuItem = new Label(text);
        menuItem.setMaxWidth(Double.MAX_VALUE);
        menuItem.getStyleClass().add("menu-item");
        
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
            } else {
                System.out.println("⚠️ Icon tidak ditemukan: " + iconPath);
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

    private VBox createStatCard(String labelText, String numberText, String iconPath) {
        VBox card = new VBox(12); // Spacing antar elemen di dalam kartu
    card.getStyleClass().add("stat-card");
    card.setPrefWidth(240);

    // 2. Membuat bagian Label atas + Ikon (HBox horizontal)
    HBox labelBox = new HBox(8); // Jarak 8px antara ikon dan teks
    labelBox.setAlignment(Pos.CENTER_LEFT);

    // Load Ikon Statistik
    ImageView iconView = new ImageView();
    try {
        InputStream iconStream = getClass().getResourceAsStream(iconPath);
        if (iconStream != null) {
            Image iconImg = new Image(iconStream);
            iconView.setImage(iconImg);
            iconView.setFitWidth(16);  // Ukuran ikon disesuaikan agar proporsional
            iconView.setFitHeight(16);
            iconView.setPreserveRatio(true);
            labelBox.getChildren().add(iconView);
        }
    } catch (Exception e) {
        System.out.println("⚠️ Gagal memuat ikon stat: " + labelText);
    }

    // Teks Label Statistik
    Label label = new Label(labelText);
    label.getStyleClass().add("stat-label");
    labelBox.getChildren().add(label);

    // 3. Membuat Kotak Biru Muda / Abu-Abu untuk Angka (.stat-subcard)
    HBox subCardBox = new HBox();
    subCardBox.getStyleClass().add("stat-subcard");
    subCardBox.setMaxWidth(Double.MAX_VALUE); // Memenuhi lebar kartu stat-card
    subCardBox.setAlignment(Pos.CENTER); // Memastikan teks angka berada di tengah kotak

    // Teks Angka Statistik
    Label number = new Label(numberText);
    number.getStyleClass().add("stat-number");
    
    // Masukkan label angka ke dalam subcard box
    subCardBox.getChildren().add(number);

    // 4. Susun LabelBox dan SubCardBox ke dalam kartu utama
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
}
