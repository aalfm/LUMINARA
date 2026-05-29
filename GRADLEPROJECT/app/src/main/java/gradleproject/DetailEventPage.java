package gradleproject;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DetailEventPage {

    private final String eventTitle;
    private final String eventDesc;
    private final String imagePath;
    private final String category; // Menyimpan konteks: "Festival", "Lokakarya", atau "Musik"

    public DetailEventPage(String title, String desc, String imagePath, String category) {
        this.eventTitle = title;
        this.eventDesc = desc;
        this.imagePath = imagePath;
        this.category = category;
    }

    public void start(Stage primaryStage) {
        StackPane root = new StackPane();
        root.getStyleClass().add("dashboard-root");

        try {
            String bgPath = getClass().getResource("/aset/gambarLuminara/gambar-bg.png").toExternalForm();
            root.setStyle("-fx-background-image: url('" + bgPath + "'); " +
                          "-fx-background-repeat: no-repeat; " +
                          "-fx-background-size: cover; " +
                          "-fx-background-position: center center;");
        } catch (Exception e) {}

        HBox mainLayout = new HBox();
        mainLayout.setAlignment(Pos.CENTER_LEFT);

        // Sidebar tetap mengikuti kategori asal halaman
        VBox sidebar = SidebarHelper.createSidebar(this.category, primaryStage);

        // Kontainer Utama Kanan (Tempat Panel Melayang Berada)
        StackPane rightContentArea = new StackPane();
        rightContentArea.setPadding(new Insets(50, 60, 50, 60));
        HBox.setHgrow(rightContentArea, Priority.ALWAYS);

        // VBox Luar Panel Berwarna Biru Dongker Transparan (Sesuai Mockup)
        VBox overlayBox = new VBox();
        overlayBox.getStyleClass().add("detail-overlay-container");
        overlayBox.setAlignment(Pos.TOP_LEFT);
        overlayBox.setMaxWidth(680);
        overlayBox.setMaxHeight(550);

        // Label Badge Oranye Dinamis (Menampilkan teks "Festival", "Lokakarya", atau "Musik")
        Label lblBadge = new Label(this.category);
        lblBadge.getStyleClass().add("detail-badge-label");

        // Kartu Putih Utama Di Dalam Kotak
        VBox whiteCard = new VBox(18);
        whiteCard.getStyleClass().add("detail-white-card");
        VBox.setVgrow(whiteCard, Priority.ALWAYS);

        // Wadah Gambar Preview Utama Atas
        StackPane topImageHolder = new StackPane();
        topImageHolder.setPrefHeight(150);
        try {
            String imgStyle = "-fx-background-image: url('" + getClass().getResource(this.imagePath).toExternalForm() + "'); " +
                              "-fx-background-repeat: no-repeat; " +
                              "-fx-background-size: cover; " +
                              "-fx-background-position: center center; " +
                              "-fx-background-radius: 12px;";
            topImageHolder.setStyle(imgStyle);
        } catch (Exception e) {
            topImageHolder.setStyle("-fx-background-color: #718096; -fx-background-radius: 12px;");
        }

        // Teks Deskripsi Informasi Event Lengkap
        Label lblTitle = new Label(this.eventTitle);
        lblTitle.getStyleClass().add("detail-main-title");
        lblTitle.setWrapText(true);

        Label lblDescription = new Label(this.eventDesc);
        lblDescription.getStyleClass().add("detail-main-desc");
        lblDescription.setWrapText(true);

        // Blok Grid Metadata (Lokasi, Tanggal, Harga, Kuota) di baris bawah
        GridPane metaGrid = new GridPane();
        metaGrid.setHgap(100);
        metaGrid.setVgap(12);

        VBox boxLokasi = new VBox(2, new Label("Lokasi:") {{ getStyleClass().add("detail-meta-title"); }}, new Label("Trans Studio Mall Makassar") {{ getStyleClass().add("detail-meta-value"); }});
        VBox boxTanggal = new VBox(2, new Label("Tanggal:") {{ getStyleClass().add("detail-meta-title"); }}, new Label("20-22 Mei 2026") {{ getStyleClass().add("detail-meta-value"); }});
        VBox boxHarga = new VBox(2, new Label("Harga:") {{ getStyleClass().add("detail-meta-title"); }}, new Label("Rp25.000") {{ getStyleClass().add("detail-meta-value"); }});
        VBox boxKuota = new VBox(2, new Label("Kuota:") {{ getStyleClass().add("detail-meta-title"); }}, new Label("100 orang") {{ getStyleClass().add("detail-meta-value"); }});

        metaGrid.add(boxLokasi, 0, 0);
        metaGrid.add(boxTanggal, 1, 0);
        metaGrid.add(boxHarga, 0, 1);
        metaGrid.add(boxKuota, 1, 1);

        // Baris Tombol Kembali
        HBox actionRow = new HBox();
        actionRow.setAlignment(Pos.BOTTOM_RIGHT);
        Button btnKembali = new Button("Kembali");
        btnKembali.getStyleClass().add("detail-btn-kembali");
        btnKembali.setCursor(javafx.scene.Cursor.HAND);
        
        btnKembali.setOnAction(e -> {
            // Mengarahkan kembali dengan benar sesuai asal kategori halaman
            if (this.category.equals("Festival")) new FestivalPage().start(primaryStage);
            else if (this.category.equals("Musik")) new MusikPage().start(primaryStage);
            else new LokakaryaPage().start(primaryStage);
        });
        actionRow.getChildren().add(btnKembali);

        whiteCard.getChildren().addAll(topImageHolder, lblTitle, lblDescription, metaGrid, actionRow);
        overlayBox.getChildren().addAll(lblBadge, whiteCard);
        rightContentArea.getChildren().add(overlayBox);

        mainLayout.getChildren().addAll(sidebar, rightContentArea);
        root.getChildren().add(mainLayout);

        Scene scene = new Scene(root, 1024, 720);
        scene.getStylesheets().add(getClass().getResource("/style/guest/intro.css").toExternalForm());

        primaryStage.setTitle("Luminara - Detail Event");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}