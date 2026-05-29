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

public class KegiatanUser {

    // 👉 PERBAIKAN 1: Root utama diubah menjadi VBox agar header & judul tidak ikut tergulung
    private VBox view;

    public KegiatanUser() {
        // Kontainer vertikal utama penampung halaman
        view = new VBox(30);
        view.setPadding(new Insets(30, 40, 30, 60)); 
        view.setAlignment(Pos.TOP_LEFT);
        view.setStyle("-fx-background-color: #F8F9FA;"); // Latar belakang abu-abu sangat terang

        // =====================================================================
        // 1. HEADER WELCOME (Mengunci Diam di Atas)
        // =====================================================================
        VBox welcomeHeader = new VBox(2);
        Label lblTitle = new Label("Halo, Sobat Luminara");
        lblTitle.setStyle("-fx-font-family: 'Poppins'; -fx-font-weight: bold; -fx-font-size: 28px; -fx-text-fill: #0A3B5C;");
        Label lblSubtitle = new Label("Siap menjelajahi event budaya di Kota Makassar?");
        lblSubtitle.setStyle("-fx-font-family: 'Poppins'; -fx-font-size: 14px; -fx-text-fill: #5A7184;");
        welcomeHeader.getChildren().addAll(lblTitle, lblSubtitle);

        // =====================================================================
        // 2. SEKSYEN UTAMA KEGIATAN
        // =====================================================================
        VBox sectionKegiatan = new VBox(12);
        sectionKegiatan.setMaxWidth(800);
        VBox.setVgrow(sectionKegiatan, Priority.ALWAYS); // Memaksa seksyen mengambil sisa tinggi layar bawah

        // Garis aksen oranye vertikal di sebelah kiri
        Label lblTabKegiatan = new Label("Kegiatan");
        lblTabKegiatan.setStyle(
            "-fx-border-color: transparent transparent transparent #FF9800; " +
            "-fx-border-width: 0 0 0 4; " +
            "-fx-padding: 2 0 2 12; " +
            "-fx-font-family: 'Poppins'; " +
            "-fx-font-weight: bold; " +
            "-fx-font-size: 16px; " +
            "-fx-text-fill: #0A3B5C;"
        );

        // Wadah Besar Biru Gelap Luar
        VBox boxBlueContainer = new VBox(0);
        boxBlueContainer.setStyle("-fx-background-color: #0A3B5C; -fx-background-radius: 15; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.08), 10, 0, 0, 4);");
        boxBlueContainer.setPadding(new Insets(20, 20, 20, 20));
        VBox.setVgrow(boxBlueContainer, Priority.ALWAYS); // Memaksa kotak biru elastis mengikuti tinggi screen

        // Grid 2 Kolom Rapi penampung ubin kartu
        GridPane cardsGrid = new GridPane();
        cardsGrid.setHgap(20);
        cardsGrid.setVgap(20);
        cardsGrid.setStyle("-fx-background-color: transparent;");

        String descDummy = "Pementasan busana adat Makassar dan Sulawesi Selatan yang menampilkan Baju Bodo, passapu, kain sutra, dan aksesoris tradisional dalam parade budaya modern.";
        
        // Memasukkan 4 data kartu kegiatan dummy
        cardsGrid.add(createEventCard("/aset/gambarLuminara/event1.png", "Makassar Traditional\nCostume Showcase", descDummy, "Free", "Budaya"), 0, 0);
        cardsGrid.add(createEventCard("/aset/gambarLuminara/event2.png", "Makassar Traditional\nCostume Showcase", descDummy, "Paid", "Festival"), 1, 0);
        cardsGrid.add(createEventCard("/aset/gambarLuminara/event3.png", "Makassar Traditional\nCostume Showcase", descDummy, "Free", "Budaya"), 0, 1);
        cardsGrid.add(createEventCard("/aset/gambarLuminara/event4.png", "Makassar Traditional\nCostume Showcase", descDummy, "Paid", "Festival"), 1, 1);

        // 👉 PERBAIKAN 2: ScrollPane hanya diletakkan di bagian dalam sini membungkus grid kartu putih
        ScrollPane scrollInner = new ScrollPane(cardsGrid);
        scrollInner.setFitToWidth(true);
        scrollInner.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); // Menyembunyikan bar scroll bawaan sistem
        scrollInner.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        // Set transparan total agar warna latar belakang biru gelap aslimu menembus rapi keluar
        scrollInner.setStyle("-fx-background: transparent; -fx-background-color: transparent; -fx-border-color: transparent;");
        VBox.setVgrow(scrollInner, Priority.ALWAYS);

        // Satukan komponen ke dalam form tata letak hierarki boks
        boxBlueContainer.getChildren().add(scrollInner);
        sectionKegiatan.getChildren().addAll(lblTabKegiatan, boxBlueContainer);

        // 👉 PERBAIKAN 3: Komponen langsung dimasukkan ke root VBox utama tanpa bungkus ScrollPane luar lagi
        view.getChildren().addAll(welcomeHeader, sectionKegiatan);
    }

    // --- METHOD HELPER: Membuat Cetakan Desain Kartu Putih Per Kegiatan ---
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
        lblPrice.setStyle("-fx-font-family: 'Poppins'; -fx-font-weight: bold; -fx-font-size: 11px; -fx-text-fill: " + (priceTag.equalsIgnoreCase("Free") ? "#FF9800;" : "#E53935;"));
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
                DashboardUser.getInstance().pindahKeDetailRekomendasi();
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