package gradleproject;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Cukup panggil ManajemenAcaraView karena dia sudah menjadi kontainer utama 
        // yang menampung sidebar, beranda, dan halaman list detail acara sekaligus.
        ManajemenAcaraView mainView = new ManajemenAcaraView();

        // Tampilkan Scene Utama dengan ukuran 1280x720
        Scene scene = new Scene(mainView, 1280, 720);
        
        // Memuat file CSS agar semua style, hover, dan font bold teraplikasikan sempurna
        try {
            scene.getStylesheets().addAll(
                getClass().getResource("/style/organizer/beranda.css").toExternalForm());
        } catch (Exception e) {
            System.out.println("Gagal memuat file CSS! Periksa kembali struktur path foldernya.");
            e.printStackTrace();
        }

        primaryStage.setScene(scene);
        primaryStage.setTitle("Luminara Event Hub Management");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
