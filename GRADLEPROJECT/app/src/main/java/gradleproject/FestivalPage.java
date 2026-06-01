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

public class FestivalPage {

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

        // Memanggil Helper Sidebar (Aktif pada menu "Festival")
        VBox sidebar = SidebarHelper.createSidebar("Festival", primaryStage);

        // Area Isi Konten Kanan
        VBox rightArea = new VBox(20);
        rightArea.setPadding(new Insets(35, 40, 20, 40));
        HBox.setHgrow(rightArea, Priority.ALWAYS);

        Label lblHeaderTitle = new Label("Festival Preview");
        lblHeaderTitle.getStyleClass().add("budaya-header-title"); 

        ScrollPane contentScroll = new ScrollPane();
        contentScroll.getStyleClass().add("budaya-scroll-pane");
        contentScroll.setFitToWidth(true);
        VBox.setVgrow(contentScroll, Priority.ALWAYS);

        GridPane cardsGrid = new GridPane();
        cardsGrid.setHgap(25);
        cardsGrid.setVgap(20);
        cardsGrid.setAlignment(Pos.TOP_CENTER);

        // Mengisi Kartu Festival Sesuai Gambar Referensi
        VBox card1 = createEventCard("Makassar Traditional Costume Showcase", 
            "Pementasan yang menampilkan busana adat Makassar dan Sulawesi Selatan. Acara ini menonjolkan Baju Bodo, Baju Bella Dada, passapu, serta kain sutra dan aksesoris tradisional sebagai simbol identitas, status sosial, dan filosofi budaya dalam bentuk parade atau runway budaya.",
            "/aset/gambarLuminara/Rekomendasi-Kegiatan.png", "Festival", primaryStage);

        VBox card2 = createEventCard("Legenda Makassar Storytelling Corner", 
            "Ruang pementasan cerita rakyat dan sejarah Makassar melalui seni tutur lisan yang interaktif. Menggabungkan sinrilik, musik tradisional, dan storytelling modern, kegiatan ini menghadirkan kisah-kisah legenda secara dekat, hangat, dan edukatif bagi generasi muda.",
            "/aset/gambarLuminara/fest-story.png", "Festival", primaryStage);

        // Controller akan otomatis menangani ketidaklengkapan nama file ".png" di bawah ini secara aman
        VBox card3 = createEventCard("Makassar Traditional Costume Showcase", 
            "Pementasan yang menampilkan busana adat Makassar dan Sulawesi Selatan. Acara ini menonjolkan Baju Bodo, Baju Bella Dada, passapu, serta kain sutra...",
            "/aset/gambarLuminara/.png", "Festival", primaryStage);

        VBox card4 = createEventCard("Makassar Traditional Costume Showcase", 
            "Pementasan yang menampilkan busana adat Makassar dan Sulawesi Selatan. Acara ini menonjolkan Baju Bodo, Baju Bella Dada, passapu, serta kain sutra...",
            "/aset/gambarLuminara/img-fest2.png", "Festival", primaryStage);

        cardsGrid.add(card1, 0, 0);
        cardsGrid.add(card2, 1, 0);
        cardsGrid.add(card3, 0, 1);
        cardsGrid.add(card4, 1, 1);

        contentScroll.setContent(cardsGrid);
        rightArea.getChildren().addAll(lblHeaderTitle, contentScroll);

        mainLayout.getChildren().addAll(sidebar, rightArea);
        root.getChildren().add(mainLayout);

        Scene scene = new Scene(root, 1280, 650);
        scene.getStylesheets().add(getClass().getResource("/style/guest/intro.css").toExternalForm());

        primaryStage.setTitle("Luminara - Festival Preview");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // =========================================================================
    // LOGIKA CONTROLLER (INTERNAL METHODS)
    // =========================================================================

    /**
     * Mengontrol aksi navigasi saat kartu event ditekan menuju halaman Detail
     */
    private static void handleLihatDetail(String title, String description, String imagePath, String contextCategory, Stage stage) {
        System.out.println("Log Controller: Membuka detail item -> " + title);
        
        DetailEventPage detailPage = new DetailEventPage(title, description, imagePath, contextCategory);
        detailPage.start(stage);
    }

    // =========================================================================
    // UI BUILDER HELPER
    // =========================================================================

    /**
     * Fungsi Pengrajin Komponen Kartu yang Fleksibel dan Digunakan Bersama Oleh MusikPage
     */
    public static VBox createEventCard(String title, String description, String imagePath, String contextCategory, Stage stage) {
        VBox cardRoot = new VBox();
        cardRoot.getStyleClass().add("budaya-card");
        cardRoot.setPrefWidth(320);
        cardRoot.setMaxWidth(340);

        StackPane imageHolder = new StackPane();
        imageHolder.getStyleClass().add("budaya-card-image-box");

        // Controller Protection: Validasi string agar terhindar dari pemanggilan path aset ilegal/kosong
        if (imagePath != null && !imagePath.trim().isEmpty() && !imagePath.equals("/aset/gambarLuminara/.png")) {
            try {
                String imgStyle = "-fx-background-image: url('" + FestivalPage.class.getResource(imagePath).toExternalForm() + "'); " +
                                  "-fx-background-repeat: no-repeat; " +
                                  "-fx-background-size: cover; " +
                                  "-fx-background-position: center center; " +
                                  "-fx-background-radius: 16px 16px 0px 0px;";
                imageHolder.setStyle(imgStyle);
            } catch (Exception e) {
                // Fallback jika file tidak ditemukan secara fisik di folder resource
                imageHolder.setStyle("-fx-background-color: #CBD5E0; -fx-background-radius: 16px 16px 0px 0px;");
            }
        } else {
            // Fallback default color jika format teks path tidak valid atau kosong
            imageHolder.setStyle("-fx-background-color: #CBD5E0; -fx-background-radius: 16px 16px 0px 0px;");
        }

        VBox infoContent = new VBox(8);
        infoContent.setPadding(new Insets(15));
        infoContent.getStyleClass().add("budaya-card-info");

        Label lblTitle = new Label(title);
        lblTitle.getStyleClass().add("budaya-card-title");
        lblTitle.setWrapText(true);
        lblTitle.setPrefHeight(38);

        Label lblDesc = new Label(description);
        lblDesc.getStyleClass().add("budaya-card-desc");
        lblDesc.setWrapText(true);
        lblDesc.setPrefHeight(75);

        HBox actionRow = new HBox();
        actionRow.setAlignment(Pos.BOTTOM_RIGHT);
        Button btnDetail = new Button("Lihat Detail");
        btnDetail.getStyleClass().add("budaya-btn-detail");
        btnDetail.setCursor(javafx.scene.Cursor.HAND);
        
        // BINDING CONTROLLER: Menghubungkan klik tombol ke method handleLihatDetail
        btnDetail.setOnAction(e -> handleLihatDetail(title, description, imagePath, contextCategory, stage));

        actionRow.getChildren().add(btnDetail);
        infoContent.getChildren().addAll(lblTitle, lblDesc, actionRow);
        cardRoot.getChildren().addAll(imageHolder, infoContent);

        return cardRoot;
    }
}