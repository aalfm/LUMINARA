package gradleproject;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class IntroPage {

    public void start(Stage primaryStage) {
        // 1. Lapisan Utama
        StackPane root = new StackPane();
        root.getStyleClass().add("intro-container");

        // Memuat Gambar Background
        try {
            String bgPath = getClass().getResource("/aset/gambarLuminara/gambar-bg.png").toExternalForm();
            root.setStyle("-fx-background-image: url('" + bgPath + "'); " +
                          "-fx-background-repeat: no-repeat; " +
                          "-fx-background-size: cover; " +
                          "-fx-background-position: center center;");

        } catch (Exception e) {
            System.out.println("⚠️ Gagal memuat gambar background: " + e.getMessage());
        }

        // 2. Lapisan Overlay Gelap
        StackPane overlay = new StackPane();
        overlay.getStyleClass().add("background-overlay");

        // 3. Susunan Konten Vertikal
        VBox contentBox = new VBox(25);
        contentBox.setAlignment(Pos.CENTER);
        contentBox.getStyleClass().add("content-box");

        // Komponen Gambar Logo
        ImageView logoView = new ImageView();
        try {
            Image logoImage = new Image(getClass().getResourceAsStream("/aset/gambarLuminara/luminara-textWhite.png"));
            logoView.setImage(logoImage);
            logoView.setFitWidth(350);
            logoView.setPreserveRatio(true);
        } catch (Exception e) {
            System.out.println("⚠️ Gagal memuat logo.png di folder aset/gambarLuminara/");
        }

        // Komponen Teks Tagline
        Label taglineLabel = new Label("Nikmati cara baru untuk menjelajahi budaya dan komunitas lokal di Kota Makassar.");
        taglineLabel.getStyleClass().add("tagline-text");
        taglineLabel.setWrapText(true);
        taglineLabel.setMaxWidth(600);

        // Komponen Tombol Aksi
        Button exploreButton = new Button("Mulai Jelajahi");
        exploreButton.getStyleClass().add("explore-button");
        
        // PERBAIKAN: Aksi ketika tombol diklik
        exploreButton.setOnAction(e -> {
            // Pastikan menggunakan IntroPage2 sebagai tipe datanya
            IntroPage2 introPage2 = new IntroPage2();
            introPage2.start(primaryStage);
        });

        // Memasukkan semua elemen ke dalam VBox
        contentBox.getChildren().addAll(logoView, taglineLabel, exploreButton);

        // Menumpuk komponen ke dalam Root
        root.getChildren().addAll(overlay, contentBox);

        // 4. Inisialisasi Scene dan Menampilkan Window
        Scene scene = new Scene(root, 960, 640);
        
        // Memuat file style.css
        try {
            scene.getStylesheets().add(getClass().getResource("/style/guest/intro.css").toExternalForm());
        } catch (Exception e) {
            System.out.println("⚠️ Gagal memuat file style.css");
        }

        primaryStage.setTitle("Luminara - Intro Page");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}