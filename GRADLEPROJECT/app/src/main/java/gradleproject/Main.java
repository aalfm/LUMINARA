package gradleproject;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    // 🎯 1. Buat wadah untuk menyimpan ID user yang sedang login
    private int loggedInUserId;

    // 🎯 2. Constructor default (Bawaan JavaFX saat aplikasi pertama kali di-run)
    public Main() {
        this.loggedInUserId = 0; // Fallback jika tidak ada ID
    }

    // 🎯 3. Constructor khusus (Dipakai oleh SignInPage untuk mengirim ID)
    public Main(int userId) {
        this.loggedInUserId = userId;
    }

    @Override
    public void start(Stage primaryStage) {
        
        // 🎯 4. Sekarang variabel loggedInUserId sudah sah dan berisi data!
        ManajemenAcaraView mainView = new ManajemenAcaraView(this.loggedInUserId); 

        // Tampilkan Scene Utama dengan ukuran 1280x720
        Scene scene = new Scene(mainView, 1280, 650);
        
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