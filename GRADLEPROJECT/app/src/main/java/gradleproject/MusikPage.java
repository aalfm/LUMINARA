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

public class MusikPage {

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

        VBox sidebar = SidebarHelper.createSidebar("Musik", primaryStage);

        VBox rightArea = new VBox(20);
        rightArea.setPadding(new Insets(35, 40, 20, 40));
        HBox.setHgrow(rightArea, Priority.ALWAYS);

        Label lblHeaderTitle = new Label("Musik Preview");
        lblHeaderTitle.getStyleClass().add("budaya-header-title"); 

        ScrollPane contentScroll = new ScrollPane();
        contentScroll.getStyleClass().add("budaya-scroll-pane");
        contentScroll.setFitToWidth(true);
        VBox.setVgrow(contentScroll, Priority.ALWAYS);

        GridPane cardsGrid = new GridPane();
        cardsGrid.setHgap(25);
        cardsGrid.setVgap(20);
        cardsGrid.setAlignment(Pos.TOP_CENTER);

        // Mengisi Data Kartu Spesifik Musik Tradisional
        VBox card1 = FestivalPage.createEventCard("Gendang Bulo Interaktif Performance",
            "Pertunjukan musik bambu tradisional Makassar yang ceria, dipadukan dengan teatrikal jenaka serta dialog interaktif bersama penonton. Sangat cocok dinikmati segala usia.",
            "/aset/gambarLuminara/img-musik2.png", "Musik", primaryStage);

        VBox card2 = FestivalPage.createEventCard("Legenda Makassar Melodi Losari",
            "Simfoni petikan instrumen kecapi lokal berlatar deburan ombak pantai Losari. Menghadirkan alunan melodi magis perpaduan masa lalu dan aransemen modern yang menenangkan hati.",
            "/aset/gambarLuminara/img-musik1.png", "Musik", primaryStage);

        VBox card3 = FestivalPage.createEventCard("Akustik Cerita Tanah Makassar",
            "Panggung senandung lagu daerah yang dikemas secara minimalis dengan instrumen akustik hangat. Membawa kisah perjuangan, cinta, dan kearifan lokal melalui lirik lagu asli.",
            "/aset/gambarLuminara/img-musik.png", "Musik", primaryStage);

        VBox card4 = FestivalPage.createEventCard("Makassar Traditional Costume Showcase",
            "Pementasan busana adat yang diiringi dengan live orchestra tabuhan instrumen perkusi etnik Sulawesi Selatan yang megah dan bersemangat.",
            "/aset/gambarLuminara/img-fest2.png", "Musik", primaryStage);

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

        primaryStage.setTitle("Luminara - Musik Preview");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}