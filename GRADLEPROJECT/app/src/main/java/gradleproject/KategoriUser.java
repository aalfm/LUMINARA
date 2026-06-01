package gradleproject;

import gradleproject.dao.EventDAO;
import gradleproject.models.Event;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

import java.io.File;
import java.util.List;

public class KategoriUser {

    private VBox view; 
    private HBox tabBudaya, tabFestival, tabLokakarya, tabMusik;
    private GridPane cardsGrid;

    public KategoriUser() {
        view = new VBox(25);
        view.setPadding(new Insets(30, 40, 30, 60)); 
        view.setAlignment(Pos.TOP_LEFT);
        view.setStyle("-fx-background-color: #F8F9FA;"); 

        // 1. HEADER WELCOME
        VBox welcomeHeader = new VBox(2);
        Label lblTitle = new Label("Halo, Sobat Luminara");
        lblTitle.setStyle("-fx-font-family: 'Poppins'; -fx-font-weight: bold; -fx-font-size: 28px; -fx-text-fill: #0A3B5C;");
        Label lblSubtitle = new Label("Siap menjelajahi event budaya di Kota Makassar?");
        lblSubtitle.setStyle("-fx-font-family: 'Poppins'; -fx-font-size: 14px; -fx-text-fill: #5A7184;");
        welcomeHeader.getChildren().addAll(lblTitle, lblSubtitle);

        // 2. KAPSUL NAVIGASI HORIZONTAL KATEGORI
        HBox categoryBar = new HBox(0); 
        categoryBar.setAlignment(Pos.CENTER_LEFT);
        categoryBar.setMaxWidth(800);
        categoryBar.setPrefHeight(45);
        categoryBar.setStyle("-fx-background-color: #0A3B5C; -fx-background-radius: 20; -fx-padding: 4 10 4 10;");

        tabBudaya = createCategoryTab("Budaya", true); 
        tabFestival = createCategoryTab("Festival", false);
        tabLokakarya = createCategoryTab("Lokakarya", false);
        tabMusik = createCategoryTab("Musik", false);

        HBox.setHgrow(tabBudaya, Priority.ALWAYS);
        HBox.setHgrow(tabFestival, Priority.ALWAYS);
        HBox.setHgrow(tabLokakarya, Priority.ALWAYS);
        HBox.setHgrow(tabMusik, Priority.ALWAYS);

        categoryBar.getChildren().addAll(tabBudaya, tabFestival, tabLokakarya, tabMusik);

        // Aksi Klik Tab: Ubah warna & Tarik data sesuai kategori
        tabBudaya.setOnMouseClicked(e -> selectTab(tabBudaya, "Budaya"));
        tabFestival.setOnMouseClicked(e -> selectTab(tabFestival, "Festival"));
        tabLokakarya.setOnMouseClicked(e -> selectTab(tabLokakarya, "Lokakarya"));
        tabMusik.setOnMouseClicked(e -> selectTab(tabMusik, "Musik"));

        // 3. WADAH UTAMA BIRU GELAP
        VBox boxBlueContainer = new VBox(0);
        boxBlueContainer.setStyle("-fx-background-color: #0A3B5C; -fx-background-radius: 15; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.08), 10, 0, 0, 4);"); 
        boxBlueContainer.setPadding(new Insets(20));
        boxBlueContainer.setMaxWidth(800);
        VBox.setVgrow(boxBlueContainer, Priority.ALWAYS); 

        cardsGrid = new GridPane();
        cardsGrid.setHgap(20);
        cardsGrid.setVgap(20);
        cardsGrid.setStyle("-fx-background-color: transparent;");

        ScrollPane scrollInner = new ScrollPane(cardsGrid);
        scrollInner.setFitToWidth(true);
        scrollInner.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); 
        scrollInner.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollInner.setStyle("-fx-background: transparent; -fx-background-color: transparent; -fx-border-color: transparent;");
        VBox.setVgrow(scrollInner, Priority.ALWAYS);

        boxBlueContainer.getChildren().add(scrollInner);
        view.getChildren().addAll(welcomeHeader, categoryBar, boxBlueContainer);

        // Muat data awal secara otomatis (Kategori: Budaya)
        selectTab(tabBudaya, "Budaya");
    }

    // --- METHOD HELPER: Menarik Data dari Database ---
    private void loadEventsByCategory(String kategoriDipilih) {
        cardsGrid.getChildren().clear(); // Bersihkan kartu lama
        
        EventDAO eventDAO = new EventDAO();
        List<Event> semuaAcara = eventDAO.getAllEvents(); // Pastikan method ini ada di EventDAO Anda
        
        int kolom = 0;
        int baris = 0;
        boolean adaData = false;

        for (Event acara : semuaAcara) {
            // Filter berdasarkan kategori (Abaikan besar/kecil huruf)
            if (acara.getCategory() != null && acara.getCategory().equalsIgnoreCase(kategoriDipilih)) {
                adaData = true;
                
                // Buat kartu event berdasarkan data asli dari database
                VBox kartuEvent = createEventCard(acara);
                cardsGrid.add(kartuEvent, kolom, baris);
                
                kolom++;
                if (kolom == 2) { // Maksimal 2 kolom per baris
                    kolom = 0;
                    baris++;
                }
            }
        }

        // Jika kosong, tampilkan pesan
        if (!adaData) {
            Label lblKosong = new Label("Belum ada event untuk kategori " + kategoriDipilih + " saat ini.");
            lblKosong.setStyle("-fx-text-fill: #A0A9B5; -fx-font-family: 'Poppins'; -fx-font-size: 14px; -fx-font-style: italic;");
            cardsGrid.add(lblKosong, 0, 0);
        }
    }

    private HBox createCategoryTab(String text, boolean isActive) {
        HBox tab = new HBox();
        tab.setAlignment(Pos.CENTER);
        tab.setCursor(javafx.scene.Cursor.HAND);
        
        Label lbl = new Label(text);
        lbl.setStyle("-fx-font-family: 'Poppins'; -fx-font-size: 14px;");
        tab.getChildren().add(lbl);

        if (isActive) {
            tab.setStyle("-fx-background-color: #FF9800; -fx-background-radius: 20; -fx-padding: 6 0 6 0;");
            lbl.setStyle(lbl.getStyle() + " -fx-text-fill: #FFFFFF; -fx-font-weight: bold;");
        } else {
            tab.setStyle("-fx-background-color: transparent; -fx-padding: 6 0 6 0;");
            lbl.setStyle(lbl.getStyle() + " -fx-text-fill: #FFFFFF;");
        }
        return tab;
    }

    private void selectTab(HBox selectedTab, String categoryName) {
        HBox[] tabs = {tabBudaya, tabFestival, tabLokakarya, tabMusik};
        for (HBox tab : tabs) {
            Label lbl = (Label) tab.getChildren().get(0);
            if (tab == selectedTab) {
                tab.setStyle("-fx-background-color: #FF9800; -fx-background-radius: 20; -fx-padding: 6 0 6 0;");
                lbl.setStyle("-fx-font-family: 'Poppins'; -fx-font-size: 14px; -fx-text-fill: #FFFFFF; -fx-font-weight: bold;");
            } else {
                tab.setStyle("-fx-background-color: transparent; -fx-padding: 6 0 6 0;");
                lbl.setStyle("-fx-font-family: 'Poppins'; -fx-font-size: 14px; -fx-text-fill: #FFFFFF;");
            }
        }
        // Panggil penarik data setelah warna tab berubah
        loadEventsByCategory(categoryName);
    }

    // 👉 PERBAIKAN: Parameter sekarang menerima objek Event asli
    private VBox createEventCard(Event acara) {
        VBox card = new VBox(10);
        card.setPrefWidth(360);
        card.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 15; -fx-padding: 15; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.05), 5, 0, 0, 2);");

        StackPane imagePane = new StackPane();
        imagePane.setPrefSize(330, 130);
        imagePane.setStyle("-fx-background-color: #E2E8F0; -fx-background-radius: 12;");

        ImageView iv = new ImageView();
        try {
            Image img;
            String imgPath = acara.getImagePath();
            // Cek apakah gambar berasal dari file lokal (Upload PC) atau resource dalam (Mockup)
            if (imgPath != null && (imgPath.contains(":\\") || imgPath.contains(":/"))) {
                img = new Image(new File(imgPath).toURI().toString());
            } else {
                img = new Image(getClass().getResourceAsStream(imgPath != null ? imgPath : "/aset/gambarLuminara/event1.png"));
            }
            iv.setImage(img);
            iv.setFitWidth(330);
            iv.setFitHeight(130);
            Rectangle clip = new Rectangle(330, 130);
            clip.setArcWidth(20);
            clip.setArcHeight(20);
            iv.setClip(clip);
            imagePane.getChildren().add(0, iv);
        } catch (Exception e) {
            Label lblPlaceholder = new Label("🖼️ Pamflet Acara");
            lblPlaceholder.setStyle("-fx-text-fill: #A0A9B5; -fx-font-family: 'Poppins';");
            imagePane.getChildren().add(lblPlaceholder);
        }

        Label lblTitle = new Label(acara.getTitle());
        lblTitle.setStyle("-fx-font-family: 'Poppins'; -fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: #0A3B5C;");
        lblTitle.setWrapText(true);

        Label lblDesc = new Label(acara.getDescription());
        lblDesc.setStyle("-fx-font-family: 'Poppins'; -fx-font-size: 10px; -fx-text-fill: #5A7184; -fx-line-spacing: 1.5;");
        lblDesc.setWrapText(true);
        lblDesc.setMaxHeight(45);

        HBox bottomRow = new HBox(8);
        bottomRow.setAlignment(Pos.CENTER_LEFT);

        VBox labelBox = new VBox(0);
        boolean isFree = (acara.getPrice() == 0);
        String labelHargaText = isFree ? "Gratis" : "Berbayar";
        
        Label lblPrice = new Label(labelHargaText);
        lblPrice.setStyle("-fx-font-family: 'Poppins'; -fx-font-weight: bold; -fx-font-size: 11px; -fx-text-fill: " + (isFree ? "#FF9800;" : "#E53935;"));
        
        Label lblCat = new Label(acara.getCategory());
        lblCat.setStyle("-fx-font-family: 'Poppins'; -fx-font-size: 11px; -fx-text-fill: #A0A9B5;");
        labelBox.getChildren().addAll(lblPrice, lblCat);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button btnBeli = new Button("Beli Tiket");
        btnBeli.setStyle("-fx-background-color: #FF9800; -fx-text-fill: white; -fx-font-family: 'Poppins'; -fx-font-size: 9px; -fx-font-weight: bold; -fx-background-radius: 10; -fx-padding: 4 12;");
        
        double hargaAsli = acara.getPrice() != null ? acara.getPrice() : 0;
        // 👉 WAJIB: Kirim data Event ke halaman Pemesanan Tiket
        btnBeli.setOnAction(event -> {
            if (DashboardUser.getInstance() != null) {
                // Casting ke int akan otomatis membuang desimal (misal 25000.0 menjadi 25000)
                // Lalu diubah ke String agar bisa dikirim ke halaman selanjutnya
                String hargaTiketUntukSistem = String.valueOf((Double) hargaAsli);
                
                DashboardUser.getInstance().pindahKePesanTiket(acara, hargaTiketUntukSistem);
            }
        });

        Button btnDetail = new Button("Lihat Detail");
        btnDetail.setStyle("-fx-background-color: #FF9800; -fx-text-fill: white; -fx-font-family: 'Poppins'; -fx-font-size: 9px; -fx-font-weight: bold; -fx-background-radius: 10; -fx-padding: 4 12;");
        btnDetail.setCursor(javafx.scene.Cursor.HAND);

        btnDetail.setOnAction(event -> {
            if (DashboardUser.getInstance() != null) {
                DashboardUser.getInstance().pindahKeDetailKategori(acara.getCategory()); 
            }
        });

        bottomRow.getChildren().addAll(labelBox, spacer, btnBeli, btnDetail);
        card.getChildren().addAll(imagePane, lblTitle, lblDesc, bottomRow);
        return card;
    }

    public Parent getView() {
        return view;
    }
}