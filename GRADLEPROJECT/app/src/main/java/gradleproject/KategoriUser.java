package gradleproject;

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

public class KategoriUser {

    // 👉 PERBAIKAN 1: Root utama diganti dari ScrollPane ke VBox agar header tidak ikut ter-scroll
    private VBox view; 
    private HBox tabBudaya, tabFestival, tabLokakarya, tabMusik;
    private GridPane cardsGrid;

    public KategoriUser() {
        // Kontainer vertikal utama
        view = new VBox(25);
        view.setPadding(new Insets(30, 40, 30, 60)); 
        view.setAlignment(Pos.TOP_LEFT);
        view.setStyle("-fx-background-color: #F8F9FA;"); // Latar belakang krem terang mockup

        // =====================================================================
        // 1. HEADER WELCOME (Tetap Diam di Atas)
        // =====================================================================
        VBox welcomeHeader = new VBox(2);
        Label lblTitle = new Label("Halo, Sobat Luminara");
        lblTitle.setStyle("-fx-font-family: 'Poppins'; -fx-font-weight: bold; -fx-font-size: 28px; -fx-text-fill: #0A3B5C;");
        Label lblSubtitle = new Label("Siap menjelajahi event budaya di Kota Makassar?");
        lblSubtitle.setStyle("-fx-font-family: 'Poppins'; -fx-font-size: 14px; -fx-text-fill: #5A7184;");
        welcomeHeader.getChildren().addAll(lblTitle, lblSubtitle);

        // =====================================================================
        // 2. KAPSUL NAVIGASI HORIZONTAL KATEGORI (Tetap Diam di Atas)
        // =====================================================================
        HBox categoryBar = new HBox(0); 
        categoryBar.setAlignment(Pos.CENTER_LEFT);
        categoryBar.setMaxWidth(800);
        categoryBar.setPrefHeight(45);
        categoryBar.setStyle("-fx-background-color: #0A3B5C; -fx-background-radius: 20; -fx-padding: 4 10 4 10;");

        tabBudaya = createCategoryTab("Budaya", true); // Default Aktif
        tabFestival = createCategoryTab("Festival", false);
        tabLokakarya = createCategoryTab("Lokakarya", false);
        tabMusik = createCategoryTab("Musik", false);

        HBox.setHgrow(tabBudaya, Priority.ALWAYS);
        HBox.setHgrow(tabFestival, Priority.ALWAYS);
        HBox.setHgrow(tabLokakarya, Priority.ALWAYS);
        HBox.setHgrow(tabMusik, Priority.ALWAYS);

        categoryBar.getChildren().addAll(tabBudaya, tabFestival, tabLokakarya, tabMusik);

        tabBudaya.setOnMouseClicked(e -> selectTab(tabBudaya));
        tabFestival.setOnMouseClicked(e -> selectTab(tabFestival));
        tabLokakarya.setOnMouseClicked(e -> selectTab(tabLokakarya));
        tabMusik.setOnMouseClicked(e -> selectTab(tabMusik));

        // =====================================================================
        // 3. WADAH UTAMA BIRU GELAP (Diberi Vgrow agar Fleksibel Mengisi Layar)
        // =====================================================================
        VBox boxBlueContainer = new VBox(0);
        boxBlueContainer.setStyle("-fx-background-color: #0A3B5C; -fx-background-radius: 15; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.08), 10, 0, 0, 4);"); 
        boxBlueContainer.setPadding(new Insets(20));
        boxBlueContainer.setMaxWidth(800);
        
        // Paksa wadah biru memanjang ke bawah menghabiskan sisa tinggi jendela screen
        VBox.setVgrow(boxBlueContainer, Priority.ALWAYS); 

        // Pembuatan Grid Ubin Kartu
        cardsGrid = new GridPane();
        cardsGrid.setHgap(20);
        cardsGrid.setVgap(20);
        cardsGrid.setStyle("-fx-background-color: transparent;");

        String descDummy = "Pementasan busana adat Makassar dan Sulawesi Selatan yang menampilkan Baju Bodo, passapu, kain sutra, dan aksesoris tradisional dalam parade budaya modern.";
        
        cardsGrid.add(createEventCard("/aset/gambarLuminara/event1.png", "Makassar Traditional\nCostume Showcase", descDummy, "Gratis", "Budaya"), 0, 0);
        cardsGrid.add(createEventCard("/aset/gambarLuminara/event2.png", "Makassar Traditional\nCostume Showcase", descDummy, "Berbayar", "Budaya"), 1, 0);
        cardsGrid.add(createEventCard("/aset/gambarLuminara/event3.png", "Makassar Traditional\nCostume Showcase", descDummy, "Paid", "Budaya"), 0, 1);
        cardsGrid.add(createEventCard("/aset/gambarLuminara/event4.png", "Makassar Traditional\nCostume Showcase", descDummy, "Free", "Budaya"), 1, 1);

        // 👉 PERBAIKAN 2: ScrollPane diletakkan di SINI (Bagian Dalam Wadah Biru)
        ScrollPane scrollInner = new ScrollPane(cardsGrid);
        scrollInner.setFitToWidth(true);
        scrollInner.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); // Sembunyikan scrollbar yang kasar
        scrollInner.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        // Set transparan penuh agar warna dasar biru dari boxBlueContainer terlihat menembus rapi
        scrollInner.setStyle("-fx-background: transparent; -fx-background-color: transparent; -fx-border-color: transparent;");
        VBox.setVgrow(scrollInner, Priority.ALWAYS);

        // Masukkan ScrollPane dalam ke kotak biru
        boxBlueContainer.getChildren().add(scrollInner);

        // Masukkan semua komponen berjejer ke atas ke dalam VBox view utama
        view.getChildren().addAll(welcomeHeader, categoryBar, boxBlueContainer);
    }

    // --- METHOD HELPER 1: Kapsul Tab Kategori ---
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

    // --- METHOD HELPER 2: Logika Warna Kapsul Aktif Klik ---
    private void selectTab(HBox selectedTab) {
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
    }

    // --- METHOD HELPER 3: Cetakan Kartu Putih Event ---
    private VBox createEventCard(String imagePath, String title, String description, String priceTag, String categoryTag) {
        VBox card = new VBox(10);
        card.setPrefWidth(360);
        card.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 15; -fx-padding: 15; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.05), 5, 0, 0, 2);");

        StackPane imagePane = new StackPane();
        imagePane.setPrefSize(330, 130);
        imagePane.setStyle("-fx-background-color: #E2E8F0; -fx-background-radius: 12;");

        ImageView iv = new ImageView();
        try {
            Image img = new Image(getClass().getResourceAsStream(imagePath));
            iv.setImage(img);
            iv.setFitWidth(330);
            iv.setFitHeight(130);
            Rectangle clip = new Rectangle(330, 130);
            clip.setArcWidth(20);
            clip.setArcHeight(20);
            iv.setClip(clip);
        } catch (Exception e) {
            Label lblPlaceholder = new Label("🖼️ Gambar Event");
            lblPlaceholder.setStyle("-fx-text-fill: #A0A9B5; -fx-font-family: 'Poppins';");
            imagePane.getChildren().add(lblPlaceholder);
        }
        imagePane.getChildren().add(0, iv);

        Label lblTitle = new Label(title);
        lblTitle.setStyle("-fx-font-family: 'Poppins'; -fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: #0A3B5C;");
        lblTitle.setWrapText(true);

        Label lblDesc = new Label(description);
        lblDesc.setStyle("-fx-font-family: 'Poppins'; -fx-font-size: 10px; -fx-text-fill: #5A7184; -fx-line-spacing: 1.5;");
        lblDesc.setWrapText(true);
        lblDesc.setMaxHeight(45);

        HBox bottomRow = new HBox(8);
        bottomRow.setAlignment(Pos.CENTER_LEFT);

        VBox labelBox = new VBox(0);
        Label lblPrice = new Label(priceTag);
        lblPrice.setStyle("-fx-font-family: 'Poppins'; -fx-font-weight: bold; -fx-font-size: 11px; -fx-text-fill: " + 
            (priceTag.equalsIgnoreCase("Free") || priceTag.equalsIgnoreCase("Gratis") ? "#FF9800;" : "#E53935;"));
        Label lblCat = new Label(categoryTag);
        lblCat.setStyle("-fx-font-family: 'Poppins'; -fx-font-size: 11px; -fx-text-fill: #A0A9B5;");
        labelBox.getChildren().addAll(lblPrice, lblCat);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button btnBeli = new Button("Beli Tiket");
        btnBeli.setStyle("-fx-background-color: #FF9800; -fx-text-fill: white; -fx-font-family: 'Poppins'; -fx-font-size: 9px; -fx-font-weight: bold; -fx-background-radius: 10; -fx-padding: 4 12;");
        btnBeli.setOnAction(event -> {
            if (DashboardUser.getInstance() != null) DashboardUser.getInstance().pindahKePesanTiket();
        });

        Button btnDetail = new Button("Lihat Detail");
        btnDetail.setStyle("-fx-background-color: #FF9800; -fx-text-fill: white; -fx-font-family: 'Poppins'; -fx-font-size: 9px; -fx-font-weight: bold; -fx-background-radius: 10; -fx-padding: 4 12;");
        btnDetail.setCursor(javafx.scene.Cursor.HAND);

        btnDetail.setOnAction(event -> {
            if (DashboardUser.getInstance() != null) {
                // Mengirimkan data string kategori "Budaya" secara aman ke layar detail
                DashboardUser.getInstance().pindahKeDetailKategori("Budaya"); 
            }
        });
        
        btnDetail.setOnAction(event -> {
            if (DashboardUser.getInstance() != null) {
                // Memanggil rute khusus kategori dengan membawa parameter teks "Budaya"
                DashboardUser.getInstance().pindahKeDetailKategori("Budaya"); 
            }
        });

        bottomRow.getChildren().addAll(labelBox, spacer, btnBeli, btnDetail);
        card.getChildren().addAll(imagePane, lblTitle, lblDesc, bottomRow);
        return card;
    }

    // 👉 PERBAIKAN REVISI: Mengembalikan VBox Parent secara langsung
    public Parent getView() {
        return view;
    }
}