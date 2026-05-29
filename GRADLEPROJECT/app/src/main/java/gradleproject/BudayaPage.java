package gradleproject;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class BudayaPage {

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

        // ==================== 1. FIXED SIDEBAR (MENGGUNAKAN HELPER) ====================
        // Menghapus total tumpukan menu ganda yang lama dengan memanggil SidebarHelper tunggal
        VBox sidebar = SidebarHelper.createSidebar("Budaya", primaryStage);

        // ==================== 2. MAIN CONTENT AREA (RIGHT) ====================
        VBox rightArea = new VBox(20);
        rightArea.setPadding(new Insets(35, 40, 20, 40));
        HBox.setHgrow(rightArea, Priority.ALWAYS);

        Label lblHeaderTitle = new Label("Budaya Preview");
        lblHeaderTitle.getStyleClass().add("budaya-header-title"); 

        ScrollPane contentScroll = new ScrollPane();
        contentScroll.getStyleClass().add("budaya-scroll-pane");
        contentScroll.setFitToWidth(true);
        VBox.setVgrow(contentScroll, Priority.ALWAYS);

        GridPane cardsGrid = new GridPane();
        cardsGrid.setHgap(25);
        cardsGrid.setVgap(20);
        cardsGrid.setAlignment(Pos.TOP_CENTER);

        // Mengisi ulang 4 kartu konten budaya asli sesuai tampilan mockup kamu
        VBox card1 = FestivalPage.createEventCard(
            "Makassar Traditional Costume Showcase", 
            "Pementasan yang menampilkan busana adat Makassar dan Sulawesi Selatan. Acara ini menonjolkan Baju Bodo, Baju Bella Dada, passapu, serta kain sutra dan aksesoris tradisional sebagai simbol identitas, status sosial, dan filosofi budaya dalam bentuk parade atau runway budaya.",
            "/aset/gambarLuminara/img-fest2.png", "Budaya", primaryStage
        );

        VBox card2 = FestivalPage.createEventCard(
            "Legenda Makassar Storytelling Corner", 
            "Ruang pementasan cerita rakyat dan sejarah Makassar melalui seni tutur lisan yang interaktif. Menggabungkan sinrilik, musik tradisional, dan storytelling modern, kegiatan ini menghadirkan kisah-kisah legenda secara dekat, hangat, dan edukatif bagi generasi muda.",
            "/aset/gambarLuminara/img-fest1.png", "Budaya", primaryStage
        );

        VBox card3 = FestivalPage.createEventCard(
            "Ekshibisi Manuskrip I La Galigo", 
            "Pameran budaya I La Galigo yang menampilkan manuskrip epik Bugis-Makassar berstatus UNESCO, dengan visualisasi Lontara, narasi kisah, dan pengalaman digital interaktif tentang sejarah dan filosofi Sulawesi Selatan.",
            "/aset/gambarLuminara/img-fest.png", "Budaya", primaryStage
        );

        VBox card4 = FestivalPage.createEventCard(
            "Makassar Traditional Costume Showcase", 
            "Pementasan yang menampilkan busana adat Makassar dan Sulawesi Selatan. Acara ini menonjolkan Baju Bodo, Baju Bella Dada, passapu, serta kain sutra...",
            "/aset/gambarLuminara/img-fest2.png", "Budaya", primaryStage
        );

        cardsGrid.add(card1, 0, 0);
        cardsGrid.add(card2, 1, 0);
        cardsGrid.add(card3, 0, 1);
        cardsGrid.add(card4, 1, 1);

        contentScroll.setContent(cardsGrid);
        rightArea.getChildren().addAll(lblHeaderTitle, contentScroll);

        mainLayout.getChildren().addAll(sidebar, rightArea);
        root.getChildren().add(mainLayout);

        Scene scene = new Scene(root, 1024, 720);
        try {
            scene.getStylesheets().add(getClass().getResource("/style/guest/intro.css").toExternalForm());
        } catch (Exception e) {}

        primaryStage.setTitle("Luminara - Budaya Preview");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}