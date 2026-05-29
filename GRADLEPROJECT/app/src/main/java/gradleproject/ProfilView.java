package gradleproject;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class ProfilView extends StackPane {

    public ManajemenAcaraView mainDashboard;

    public ProfilView(ManajemenAcaraView dashboard) {
        this.mainDashboard = dashboard;
        // Tampilan awal saat menu Profil diklik adalah halaman ringkasan profil & ulasan
        tampilkanLihatProfil();
    }

    // =========================================================
    // SUB-MENU 1: TAMPILAN LIHAT PROFIL & ULASAN
    // =========================================================
    public void tampilkanLihatProfil() {
        this.getChildren().clear();

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent; -fx-padding: 0;");

        VBox rootContent = new VBox(25);
        rootContent.setPadding(new Insets(30, 40, 30, 40));

        // 1. Pembuatan Banner Atas & Foto Profil V
        AnchorPane bannerAnchor = createProfileBanner();

        // 2. Tagline Slogan, Lokasi, dan Tanggal Bergabung
        VBox metaBox = new VBox(10);
        Label lblTagline = new Label("Kami hadir, menghubungkan komunitas dan budaya melalui event yang inspiratif.");
        lblTagline.setStyle("-fx-font-family: 'Poppins'; -fx-font-weight: bold; -fx-text-fill: #002B5B; -fx-font-size: 14px;");
        
        Label lblLokasi = new Label("Makassar, Sulawesi Selatan");
        lblLokasi.setStyle("-fx-font-family: 'Poppins'; -fx-text-fill: #556B83; -fx-font-size: 13px;");
        
        Label lblSejak = new Label("Sejak November 2025");
        lblSejak.setStyle("-fx-font-family: 'Poppins'; -fx-text-fill: #556B83; -fx-font-size: 13px;");
        
        metaBox.getChildren().addAll(lblTagline, lblLokasi, lblSejak);

        // 3. Bagian Bawah: Informasi Organisasi (Kiri) & Ulasan (Kanan)
        HBox bottomLayout = new HBox(40);
        HBox.setHgrow(bottomLayout, Priority.ALWAYS);

        // --- Sisi Kiri: Informasi Organisasi ---
        VBox infoOrganisasiBox = new VBox(15);
        HBox.setHgrow(infoOrganisasiBox, Priority.ALWAYS);
        
        Label lblSectionInfo = new Label("Informasi Organisasi");
        lblSectionInfo.setStyle("-fx-font-family: 'Poppins'; -fx-font-weight: bold; -fx-font-size: 18px; -fx-text-fill: #002B5B; -fx-border-color: transparent transparent transparent #FF9914; -fx-border-width: 0 0 0 4; -fx-padding: 0 0 0 10;");

        VBox fieldNama = createDisabledField("Nama", "Ra - Fly Organizer");
        VBox fieldTelepon = createDisabledField("No Telepon", "0812-3321-1234");
        VBox fieldEmail = createDisabledField("Email", "rafly.organizer@gmail.com");

        infoOrganisasiBox.getChildren().addAll(lblSectionInfo, fieldNama, fieldTelepon, fieldEmail);

        // --- Sisi Kanan: Ulasan ---
        VBox ulasanBox = new VBox(15);
        ulasanBox.setPrefWidth(380);
        
        Label lblSectionUlasan = new Label("Ulasan");
        lblSectionUlasan.setStyle("-fx-font-family: 'Poppins'; -fx-font-weight: bold; -fx-font-size: 18px; -fx-text-fill: #002B5B; -fx-border-color: transparent transparent transparent #FF9914; -fx-border-width: 0 0 0 4; -fx-padding: 0 0 0 10;");

        VBox ulasanCardContainer = new VBox(12);
        ulasanCardContainer.setStyle("-fx-background-color: #F8FAFC; -fx-background-radius: 12px; -fx-border-color: #E2E8F0; -fx-border-radius: 12px; -fx-padding: 15px;");
        
        // Loop render item ulasan sesuai mockup gambar kamu
        for (int i = 0; i < 4; i++) {
            ulasanCardContainer.getChildren().add(createUlasanItem("tsaqif", "Eventnya seru dan terorganisasi dengan baik. Keren"));
        }

        Button btnLihatLainnya = new Button("Lihat lainnya");
        btnLihatLainnya.setStyle("-fx-background-color: #FF9914; -fx-text-fill: white; -fx-font-family: 'Poppins'; -fx-font-weight: bold; -fx-font-size: 12px; -fx-background-radius: 8px; -fx-padding: 6px 20px; -fx-cursor: hand;");
        
        // KUNCI HUBUNGAN: Jika tombol di dalam profil klik, panggil fungsi ulasan penuh
        btnLihatLainnya.setOnAction(e -> {
            tampilkanHalamanSemuaUlasan();
        });
        
        HBox btnContainer = new HBox(btnLihatLainnya);
        btnContainer.setAlignment(Pos.CENTER_RIGHT);
        ulasanCardContainer.getChildren().add(btnContainer);

        ulasanBox.getChildren().addAll(lblSectionUlasan, ulasanCardContainer);

        bottomLayout.getChildren().addAll(infoOrganisasiBox, ulasanBox);

        // Satukan semuanya ke dalam root layout
        rootContent.getChildren().addAll(bannerAnchor, metaBox, bottomLayout);
        scrollPane.setContent(rootContent);
        this.getChildren().add(scrollPane);
    }

    // =========================================================
    // SUB-MENU 2: TAMPILAN EDIT PROFIL
    // =========================================================
    public void tampilkanEditProfil() {
        this.getChildren().clear();

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent; -fx-padding: 0;");

        VBox rootContent = new VBox(25);
        rootContent.setPadding(new Insets(30, 40, 30, 40));

        // 1. Reuse Banner
        AnchorPane bannerAnchor = createProfileBanner();
        // Sembunyikan tombol orange edit di kanan atas khusus di halaman edit ini
        if(bannerAnchor.getChildren().size() > 2) {
            bannerAnchor.getChildren().get(2).setVisible(false);
        }

        // 2. Tagline meta dekoratif tetap dimunculkan agar konsisten
        VBox metaBox = new VBox(10);
        Label lblTagline = new Label("Kami hadir, menghubungkan komunitas dan budaya melalui event yang inspiratif.");
        lblTagline.setStyle("-fx-font-family: 'Poppins'; -fx-font-weight: bold; -fx-text-fill: #002B5B; -fx-font-size: 14px;");
        Label lblLokasi = new Label("📍   Makassar, Sulawesi Selatan   |   📅   Sejak November 2025");
        lblLokasi.setStyle("-fx-font-family: 'Poppins'; -fx-text-fill: #556B83; -fx-font-size: 13px;");
        metaBox.getChildren().addAll(lblTagline, lblLokasi);

        // 3. Form Input Edit Profil (Grid Pane 2 Kolom)
        VBox formSectionBox = new VBox(15);
        Label lblSectionForm = new Label("Informasi Organisasi");
        lblSectionForm.setStyle("-fx-font-family: 'Poppins'; -fx-font-weight: bold; -fx-font-size: 18px; -fx-text-fill: #002B5B; -fx-border-color: transparent transparent transparent #FF9914; -fx-border-width: 0 0 0 4; -fx-padding: 0 0 0 10;");
        
        GridPane formGrid = new GridPane();
        formGrid.setHgap(40);
        formGrid.setVgap(20);
        
        ColumnConstraints col = new ColumnConstraints();
        col.setPercentWidth(50);
        formGrid.getColumnConstraints().addAll(col, col);

        // Baris 1: Nama & Email
        VBox inputNama = createInputField("Nama", "Rafly aja", true);
        VBox inputEmail = createInputField("Email", "rafly.organizer@gmail.com", false);
        formGrid.add(inputNama, 0, 0);
        formGrid.add(inputEmail, 1, 0);

        // Baris 2: No Telepon & Password
        VBox inputTelepon = createInputField("No Telepon", "0812-3321-1234", false);
        VBox inputPassword = createInputField("Password", "rafly.organizer@gmail.com", false); 
        formGrid.add(inputTelepon, 0, 1);
        formGrid.add(inputPassword, 1, 1);

        // Baris 3: Email baris bawah tambahan
        VBox inputEmailTambahan = createInputField("Email", "rafly.organizer@gmail.com", false);
        formGrid.add(inputEmailTambahan, 0, 2);

        // 4. Tombol Simpan Perubahan di Pojok Kanan Bawah
        Button btnSimpan = new Button("Simpan Perubahan");
        btnSimpan.setStyle("-fx-background-color: #FF9914; -fx-text-fill: white; -fx-font-family: 'Poppins'; -fx-font-weight: bold; -fx-font-size: 13px; -fx-background-radius: 8px; -fx-padding: 8px 25px; -fx-cursor: hand;");
        btnSimpan.setOnAction(e -> {
            tampilkanLihatProfil();
        });
        
        HBox actionRow = new HBox(btnSimpan);
        actionRow.setAlignment(Pos.CENTER_RIGHT);
        actionRow.setPadding(new Insets(15, 0, 0, 0));

        formSectionBox.getChildren().addAll(lblSectionForm, formGrid, actionRow);

        rootContent.getChildren().addAll(bannerAnchor, metaBox, formSectionBox);
        scrollPane.setContent(rootContent);
        this.getChildren().add(scrollPane);
    }

    // =========================================================================
    // KUNCI TAMBAHAN: SCENE ULASAN PENUH (Sesuai image_00cad2.jpg)
    // =========================================================================
    public void tampilkanHalamanSemuaUlasan() {
        this.getChildren().clear();

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent; -fx-padding: 0;");

        VBox rootContent = new VBox(25);
        rootContent.setPadding(new Insets(30, 40, 30, 40));

        // 1. Judul Atas Utama
        Label lblJudulUlasan = new Label("Ulasan");
        lblJudulUlasan.setStyle("-fx-font-family: 'Poppins'; -fx-font-weight: bold; -fx-font-size: 24px; -fx-text-fill: #003A6C;");

        // 2. Kontainer Box Besar Abu-abu melengkung pembungkus seluruh daftar ulasan
        VBox boxDaftarUlasan = new VBox(15);
        boxDaftarUlasan.setStyle("-fx-background-color: #F8FAFC; -fx-background-radius: 16px; -fx-border-color: #E2E8F0; -fx-border-radius: 16px; -fx-padding: 25px;");
        HBox.setHgrow(boxDaftarUlasan, Priority.ALWAYS);

        // Menambahkan daftar ulasan di dalamnya secara berurutan berderet ke bawah
        boxDaftarUlasan.getChildren().addAll(
            createUlasanItemFullWidth("tsaqif", "Eventnya seru dan terorganisasi dengan baik. Keren"),
            new Separator(),
            createUlasanItemFullWidth("raihana", "Sangat informatif! Penyelenggaraannya tepat waktu dan fasilitasnya memuaskan."),
            new Separator(),
            createUlasanItemFullWidth("nama", "Saran saja, mungkin sound system-nya bisa ditingkatkan lagi di event berikutnya. Overall bagus!"),
            new Separator(),
            createUlasanItemFullWidth("raihana", "Sangat informatif! Penyelenggaraannya tepat waktu dan fasilitasnya memuaskan."),
            new Separator(),
            createUlasanItemFullWidth("nama", "Saran saja, mungkin sound system-nya bisa ditingkatkan lagi di event berikutnya. Overall bagus!"),
            new Separator(),
            createUlasanItemFullWidth("nama-lagi", "Sangat informatif! Penyelenggaraannya tepat waktu dan fasilitasnya memuaskan."),
            new Separator()
        );

        rootContent.getChildren().addAll(lblJudulUlasan, boxDaftarUlasan);
        scrollPane.setContent(rootContent);
        this.getChildren().add(scrollPane);
    }
    

    // =========================================================
    // HELPER COMPONENT BUILDERS (KUMPULAN FUNGSI KREATUR UI)
    // =========================================================

    private AnchorPane createProfileBanner() {
        AnchorPane anchor = new AnchorPane();
        anchor.setPrefHeight(180);

        Pane bannerBg = new Pane();
        bannerBg.setPrefHeight(140);
        bannerBg.setStyle("-fx-background-color: #002B5B; -fx-background-radius: 25px; -fx-background-image: url('/aset/banner-phinisi.jpg'); -fx-background-size: cover; -fx-background-position: center;");
        AnchorPane.setTopAnchor(bannerBg, 0.0);
        AnchorPane.setLeftAnchor(bannerBg, 0.0);
        AnchorPane.setRightAnchor(bannerBg, 0.0);

        Label lblAvatar = new Label("V");
        lblAvatar.setAlignment(Pos.CENTER);
        lblAvatar.setStyle("-fx-background-color: linear-gradient(to bottom right, #FFCC80, #FF9914); -fx-text-fill: white; -fx-font-size: 42px; -fx-font-weight: bold; -fx-background-radius: 60px; -fx-border-color: white; -fx-border-width: 5px; -fx-border-radius: 60px;");
        lblAvatar.setPrefSize(110, 110);
        lblAvatar.setMinSize(110, 110);
        
        AnchorPane.setTopAnchor(lblAvatar, 65.0);
        AnchorPane.setLeftAnchor(lblAvatar, 35.0);

        Button btnEditProfil = new Button("Edit profil");
        btnEditProfil.setStyle("-fx-background-color: #FF9914; -fx-text-fill: white; -fx-font-family: 'Poppins'; -fx-font-weight: bold; -fx-font-size: 12px; -fx-background-radius: 8px; -fx-padding: 6px 22px; -fx-cursor: hand;");
        btnEditProfil.setOnAction(e -> tampilkanEditProfil());
        
        AnchorPane.setTopAnchor(btnEditProfil, 150.0);
        AnchorPane.setRightAnchor(btnEditProfil, 10.0);

        anchor.getChildren().addAll(bannerBg, lblAvatar, btnEditProfil);
        return anchor;
    }

    private VBox createDisabledField(String labelName, String valueText) {
        VBox box = new VBox(6);
        Label lbl = new Label(labelName);
        lbl.setStyle("-fx-font-family: 'Poppins'; -fx-font-size: 13px; -fx-text-fill: #002B5B; -fx-font-weight: bold;");
        
        TextField tf = new TextField(valueText);
        tf.setEditable(false);
        tf.setStyle("-fx-background-color: #F1F5F9; -fx-background-radius: 10px; -fx-border-color: #CBD5E1; -fx-border-radius: 10px; -fx-padding: 12px 15px; -fx-font-family: 'Poppins'; -fx-text-fill: #334155; -fx-font-size: 13px;");
        
        box.getChildren().addAll(lbl, tf);
        return box;
    }

    private VBox createInputField(String labelName, String valueText, boolean hasEditIcon) {
        VBox box = new VBox(6);
        Label lbl = new Label(labelName);
        lbl.setStyle("-fx-font-family: 'Poppins'; -fx-font-size: 13px; -fx-text-fill: #002B5B; -fx-font-weight: bold;");
        
        StackPane inputPane = new StackPane();
        TextField tf = new TextField(valueText);
        tf.setStyle("-fx-background-color: #F8FAFC; -fx-background-radius: 10px; -fx-border-color: #CBD5E1; -fx-border-radius: 10px; -fx-padding: 12px 40px 12px 15px; -fx-font-family: 'Poppins'; -fx-text-fill: #334155; -fx-font-size: 13px;");
        
        inputPane.getChildren().add(tf);

        if (hasEditIcon) {
            Label iconEdit = new Label("✏️"); 
            iconEdit.setStyle("-fx-font-size: 12px; -fx-cursor: hand;");
            StackPane.setAlignment(iconEdit, Pos.CENTER_RIGHT);
            StackPane.setMargin(iconEdit, new Insets(0, 15, 0, 0));
            inputPane.getChildren().add(iconEdit);
        }
        
        box.getChildren().addAll(lbl, inputPane);
        return box;
    }

    private HBox createUlasanItem(String username, String comment) {
        HBox itemRow = new HBox(12);
        itemRow.setAlignment(Pos.TOP_LEFT);
        itemRow.setPadding(new Insets(5, 0, 5, 0));

        Label circleAvatar = new Label(username.substring(0, 1).toUpperCase());
        circleAvatar.setAlignment(Pos.CENTER);
        circleAvatar.setStyle("-fx-background-color: #99E9F2; -fx-text-fill: #0B7285; -fx-font-weight: bold; -fx-background-radius: 18px; -fx-min-width: 32px; -fx-min-height: 32px; -fx-max-width: 32px; -fx-max-height: 32px; -fx-font-size: 12px;");

        VBox textContent = new VBox(2);
        HBox userRow = new HBox(8);
        userRow.setAlignment(Pos.CENTER_LEFT);
        
        Label lblUser = new Label(username);
        lblUser.setStyle("-fx-font-family: 'Poppins'; -fx-font-weight: bold; -fx-font-size: 12px; -fx-text-fill: #002B5B;");
        
        Label lblStars = new Label("⭐⭐⭐⭐");
        lblStars.setStyle("-fx-font-size: 9px;");
        
        userRow.getChildren().addAll(lblUser, lblStars);

        Label lblComment = new Label(comment);
        lblComment.setWrapText(true);
        lblComment.setStyle("-fx-font-family: 'Poppins'; -fx-font-size: 11px; -fx-text-fill: #556B83;");

        textContent.getChildren().addAll(userRow, lblComment);
        itemRow.getChildren().addAll(circleAvatar, textContent);
        return itemRow;
    }

    // Fungsi tambahan pembentuk baris ulasan lebar penuh (Khusus Halaman Ulasan Utama)
    private HBox createUlasanItemFullWidth(String username, String comment) {
        HBox itemRow = createUlasanItem(username, comment);
        itemRow.setPadding(new Insets(8, 0, 8, 0));
        HBox.setHgrow(itemRow, Priority.ALWAYS);
        return itemRow;
    }
}