package gradleproject;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LokakaryaPage {

    public void start(Stage primaryStage) {
        StackPane root = new StackPane();
        root.getStyleClass().add("dashboard-root");

        try {
            String bgPath = getClass().getResource("/aset/gambarLuminara/gambar-bg.png").toExternalForm();
            root.setStyle("-fx-background-image: url('" + bgPath + "'); " +
                          "-fx-background-repeat: no-repeat; " +
                          "-fx-background-size: cover; " +
                          "-fx-background-position: center center;");
        } catch (Exception e) {
            root.setStyle("-fx-background-color: #0A2540;");
        }

        HBox mainLayout = new HBox();
        mainLayout.setAlignment(Pos.CENTER_LEFT);

        // ==================== 1. SIDEBAR NAVIGATION (Menggunakan Helper Tunggal) ====================
        // 👉 FIX UTAMA: Panggil langsung helper tunggal agar seluruh ikon kustom dan efek warnanya termuat otomatis!
        VBox sidebar = SidebarHelper.createSidebar("Lokakarya", primaryStage);

        // ==================== 2. MAIN CONTENT AREA ====================
        VBox rightArea = new VBox(20);
        rightArea.setPadding(new Insets(35, 40, 20, 40));
        HBox.setHgrow(rightArea, Priority.ALWAYS);

        Label lblHeaderTitle = new Label("Lokakarya Preview");
        lblHeaderTitle.getStyleClass().add("budaya-header-title"); 

        ScrollPane contentScroll = new ScrollPane();
        contentScroll.getStyleClass().add("budaya-scroll-pane");
        contentScroll.setFitToWidth(true);
        VBox.setVgrow(contentScroll, Priority.ALWAYS);

        GridPane cardsGrid = new GridPane();
        cardsGrid.setHgap(20);
        cardsGrid.setVgap(20);
        cardsGrid.setAlignment(Pos.TOP_CENTER);

        VBox card1 = createLokakaryaCard(
            "Workshop Pembuatan Lipa' Sabbe",
            "Pelatihan menenun kain sutra khas Makassar secara tradisional. Peserta akan diajarkan teknik dasar penggunaan alat tenun tradisional dan pengenalan motif filosofis Sulawesi Selatan.",
            "/aset/gambarLuminara/img-lokakarya2.png", primaryStage
        );

        VBox card2 = createLokakaryaCard(
            "Kelas Masak Kuliner Makassar",
            "Belajar rahasia bumbu otentik Coto Makassar dan Konro. Lokakarya ini menghadirkan chef lokal yang berpengalaman untuk membimbing peserta membuat hidangan khas yang lezat.",
            "/aset/gambarLuminara/img-lokakarya1.png", primaryStage
        );

        VBox card3 = createLokakaryaCard(
            "Seni Kerajinan Perak Kendari-Makassar",
            "Praktik langsung pembuatan aksesoris tradisional dari bahan perak. Peserta dapat membawa pulang hasil karyanya sendiri sebagai kenang-kenangan budaya yang berharga.",
            "/aset/gambarLuminara/img-lokakarya.png", primaryStage
        );

        VBox card4 = createLokakaryaCard(
            "Lokakarya Tari Tradisional",
            "Kelas intensif mempelajari gerakan dasar Tari Pakarena dan Tari Empat Etnis. Dibuka untuk umum guna melestarikan warisan gerak tubuh yang sarat akan makna kedamaian.",
            "/aset/gambarLuminara/img-fest2.png", primaryStage
        );

        cardsGrid.add(card1, 0, 0);
        cardsGrid.add(card2, 1, 0);
        cardsGrid.add(card3, 0, 1);
        cardsGrid.add(card4, 1, 1);

        contentScroll.setContent(cardsGrid);
        rightArea.getChildren().addAll(lblHeaderTitle, contentScroll);

        mainLayout.getChildren().addAll(sidebar, rightArea);
        root.getChildren().add(mainLayout);

        Scene scene = new Scene(root, 1280, 650);
        try {
            scene.getStylesheets().add(getClass().getResource("/style/guest/intro.css").toExternalForm());
        } catch (Exception e) {}

        primaryStage.setTitle("Luminara - Lokakarya Preview");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // ❌ Metode lama createMenuButton() yang duplikat sudah dihapus bersih dari sini ❌

    private VBox createLokakaryaCard(String title, String description, String imagePath, Stage stage) {
        VBox cardRoot = new VBox();
        cardRoot.getStyleClass().add("budaya-card");
        cardRoot.setPrefWidth(320);
        cardRoot.setMaxWidth(340);

        StackPane imageHolder = new StackPane();
        imageHolder.setPrefHeight(160);
        imageHolder.getStyleClass().add("budaya-card-image-box");

        try {
            String imgStyle = "-fx-background-image: url('" + getClass().getResource(imagePath).toExternalForm() + "'); " +
                              "-fx-background-repeat: no-repeat; " +
                              "-fx-background-size: cover; " +
                              "-fx-background-position: center center; " +
                              "-fx-background-radius: 16px 16px 0px 0px;";
            imageHolder.setStyle(imgStyle);
        } catch (Exception e) {
            imageHolder.setStyle("-fx-background-color: #A0AEC0; -fx-background-radius: 16px 16px 0px 0px;");
        }

        VBox infoContent = new VBox(10);
        infoContent.setPadding(new Insets(15, 15, 15, 15));
        infoContent.getStyleClass().add("budaya-card-info");

        Label lblTitle = new Label(title);
        lblTitle.getStyleClass().add("budaya-card-title");
        lblTitle.setWrapText(true);
        lblTitle.setPrefHeight(40);

        Label lblDesc = new Label(description);
        lblDesc.getStyleClass().add("budaya-card-desc");
        lblDesc.setWrapText(true);
        lblDesc.setPrefHeight(90);

        HBox actionRow = new HBox();
        actionRow.setAlignment(Pos.BOTTOM_RIGHT);
        Button btnDetail = new Button("Lihat Detail");
        btnDetail.getStyleClass().add("budaya-btn-detail");
        btnDetail.setCursor(javafx.scene.Cursor.HAND);
        
        btnDetail.setOnAction(e -> {
            new DetailLokakaryaPage().start(stage);
        });

        actionRow.getChildren().add(btnDetail);
        infoContent.getChildren().addAll(lblTitle, lblDesc, actionRow);
        cardRoot.getChildren().addAll(imageHolder, infoContent);

        return cardRoot;
    }
}