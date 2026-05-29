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
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

public class SorotanBudaya {

    private VBox view;

    public SorotanBudaya() {
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

        // 2. SEKSYEN UTAMA KARTU GRID
        VBox sectionSorotan = new VBox(0);
        sectionSorotan.setMaxWidth(800);
        VBox.setVgrow(sectionSorotan, Priority.ALWAYS);

        HBox tabSorotan = new HBox();
        Label lblTabSorotan = new Label("Sorotan Budaya");
        lblTabSorotan.setStyle("-fx-background-color: #FF9800; -fx-text-fill: white; -fx-font-family: 'Poppins'; -fx-font-weight: bold; -fx-font-size: 13px; -fx-background-radius: 10 10 0 0; -fx-padding: 6 22;");
        tabSorotan.getChildren().add(lblTabSorotan);

        // Wadah Biru Gelap Frame Utama
        VBox boxBlueContainer = new VBox(0);
        boxBlueContainer.setStyle("-fx-background-color: #0A3B5C; -fx-background-radius: 0 15 15 15; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.08), 10, 0, 0, 4);");
        boxBlueContainer.setPadding(new Insets(20, 20, 20, 20));
        VBox.setVgrow(boxBlueContainer, Priority.ALWAYS);

        // Grid 2 Kolom Rapi penampung ubin kartu putih
        GridPane cardsGrid = new GridPane();
        cardsGrid.setHgap(20); 
        cardsGrid.setVgap(20); 
        cardsGrid.setStyle("-fx-background-color: transparent;");

        String descDummy = "Pementasan busana adat Makassar dan Sulawesi Selatan yang menampilkan Baju Bodo, passapu, kain sutra, dan aksesoris tradisional dalam parade budaya modern.";
        
        // Memasukkan 4 data kartu sorotan sesuai gambar mockup
        cardsGrid.add(createSorotanCard("/aset/gambarLuminara/sorotan1.png", "Makassar Traditional\nCostume Showcase", descDummy), 0, 0);
        cardsGrid.add(createSorotanCard("/aset/gambarLuminara/sorotan2.png", "Makassar Traditional\nCostume Showcase", descDummy), 1, 0);
        cardsGrid.add(createSorotanCard("/aset/gambarLuminara/sorotan3.png", "Makassar Traditional\nCostume Showcase", descDummy), 0, 1);
        cardsGrid.add(createSorotanCard("/aset/gambarLuminara/sorotan4.png", "Makassar Traditional\nCostume Showcase", descDummy), 1, 1);

        // Konstruksi ScrollPane bagian dalam agar judul halaman tetap terkunci diam di atas
        ScrollPane scrollInner = new ScrollPane(cardsGrid);
        scrollInner.setFitToWidth(true);
        scrollInner.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollInner.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollInner.setStyle("-fx-background: transparent; -fx-background-color: transparent; -fx-border-color: transparent;");
        VBox.setVgrow(scrollInner, Priority.ALWAYS);

        boxBlueContainer.getChildren().add(scrollInner);
        sectionSorotan.getChildren().addAll(tabSorotan, boxBlueContainer);

        view.getChildren().addAll(welcomeHeader, sectionSorotan);
    }

    // --- METHOD HELPER: Membuat Cetakan Kartu Sorotan Budaya (Hanya Tombol Lihat Detail) ---
    private VBox createSorotanCard(String imagePath, String title, String description) {
        VBox card = new VBox(10);
        card.setPrefWidth(360); 
        card.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 15; -fx-padding: 15; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.05), 5, 0, 0, 2);");

        // Foto Sampul Atas Kartu
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
            Label lblPlaceholder = new Label("🖼️ Gambar Sorotan");
            lblPlaceholder.setStyle("-fx-text-fill: #A0A9B5; -fx-font-family: 'Poppins';");
            imagePane.getChildren().add(lblPlaceholder);
        }
        imagePane.getChildren().add(0, iv);

        // Judul Artikel Budaya
        Label lblTitle = new Label(title);
        lblTitle.setStyle("-fx-font-family: 'Poppins'; -fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: #0A3B5C;");
        lblTitle.setWrapText(true);

        // Deskripsi Narasi
        Label lblDesc = new Label(description);
        lblDesc.setStyle("-fx-font-family: 'Poppins'; -fx-font-size: 10px; -fx-text-fill: #5A7184; -fx-line-spacing: 1.5;");
        lblDesc.setWrapText(true);
        lblDesc.setMaxHeight(45);

        // Baris Tombol Aksi Bawah (Hanya Ada Tombol Lihat Detail pushed ke kanan)
        HBox bottomRow = new HBox();
        bottomRow.setAlignment(Pos.CENTER_RIGHT);
        bottomRow.setPadding(new Insets(5, 0, 0, 0));

        Button btnDetail = new Button("Lihat Detail");
        btnDetail.setStyle("-fx-background-color: #FF9800; -fx-text-fill: white; -fx-font-family: 'Poppins'; -fx-font-size: 9px; -fx-font-weight: bold; -fx-background-radius: 10; -fx-padding: 4 15;");
        btnDetail.setCursor(javafx.scene.Cursor.HAND);

        btnDetail.setOnAction(event -> {
            if (DashboardUser.getInstance() != null) {
                DashboardUser.getInstance().pindahKeDetailSorotan();
            }
        });

        bottomRow.getChildren().add(btnDetail);
        card.getChildren().addAll(imagePane, lblTitle, lblDesc, bottomRow);
        return card;
    }

    public Parent getView() {
        return view;
    }
}