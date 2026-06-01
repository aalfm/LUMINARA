package gradleproject;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainAdmin extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Memanggil Dashboard yang sudah kita buat
        DashboardAdmin dashboard = new DashboardAdmin();
        
        // Mengatur ukuran dasar jendela aplikasi (misal: 1100 x 700)
        Scene scene = new Scene(dashboard.getView(), 1280, 720);
        
        // Memasukkan file CSS ke dalam Scene
        try {
            String css = getClass().getResource("/style/admin/beranda.css").toExternalForm();
            scene.getStylesheets().add(css);
        } catch (NullPointerException e) {
            System.out.println("⚠️ Peringatan: File beranda.css tidak ditemukan di dalam folder resources!");
        }

        // Konfigurasi Jendela Utama
        primaryStage.setTitle("Luminara App");
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(1280); // Batas minimal agar tidak terlalu kecil
        primaryStage.setMinHeight(650);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}