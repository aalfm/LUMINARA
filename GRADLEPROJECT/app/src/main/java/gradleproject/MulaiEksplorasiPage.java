package gradleproject;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MulaiEksplorasiPage {

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

        // Sidebar Aktif di menu "Mulai Eksplorasi"
        VBox sidebar = SidebarHelper.createSidebar("Mulai Eksplorasi", primaryStage);

        // Area Tengah Eksplorasi Teks Promosi
        VBox centerContent = new VBox(25);
        centerContent.setAlignment(Pos.CENTER);
        HBox.setHgrow(centerContent, Priority.ALWAYS);
        centerContent.setPadding(new Insets(40));

        VBox textGroup = new VBox(10);
        textGroup.setAlignment(Pos.CENTER);
        
        Label lblMainText = new Label("Buat akun dulu,\nbiar hidupmu gak cuma\nscroll doang.");
        lblMainText.getStyleClass().add("eksplorasi-main-title");
        lblMainText.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        lblMainText.setWrapText(true);

        Label lblSubText = new Label("Gabung dan temukan event budaya di sekitarmu");
        lblSubText.getStyleClass().add("eksplorasi-sub-title");
        lblSubText.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);

        textGroup.getChildren().addAll(lblMainText, lblSubText);

        // Baris Dua Tombol Kontrol Pilihan bawah
        HBox buttonRow = new HBox(30);
        buttonRow.setAlignment(Pos.CENTER);
        buttonRow.setPadding(new Insets(15, 0, 0, 0));

        Button btnKembali = new Button("Kembali");
        btnKembali.getStyleClass().add("eksplorasi-btn-selanjutnya"); // Menggunakan basis gaya oranye seragam
        btnKembali.setCursor(javafx.scene.Cursor.HAND);
        btnKembali.setOnAction(e -> {
            new MusikPage().start(primaryStage); // Mengalihkan ke halaman menu sebelumnya
        });

        Button btnSelanjutnya = new Button("Selanjutnya");
        btnSelanjutnya.getStyleClass().add("eksplorasi-btn-selanjutnya");
        btnSelanjutnya.setCursor(javafx.scene.Cursor.HAND);
        btnSelanjutnya.setOnAction(e -> {
            System.out.println("Navigasi ke form pembuatan akun / pendaftaran tamu...");
        });

        buttonRow.getChildren().addAll(btnKembali, btnSelanjutnya);
        centerContent.getChildren().addAll(textGroup, buttonRow);

        mainLayout.getChildren().addAll(sidebar, centerContent);
        root.getChildren().add(mainLayout);

        Scene scene = new Scene(root, 1024, 720);
        scene.getStylesheets().add(getClass().getResource("/style/guest/intro.css").toExternalForm());

        primaryStage.setTitle("Luminara - Mulai Eksplorasi");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
