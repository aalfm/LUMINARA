package gradleproject;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Memanggil Dashboard yang sudah kita buat
        Dashboard dashboard = new Dashboard();
        
        // Mengatur ukuran dasar jendela aplikasi (misal: 1100 x 700)
        Scene scene = new Scene(dashboard.getView(), 1100, 700);
        
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
        primaryStage.setMinWidth(1024); // Batas minimal agar tidak terlalu kecil
        primaryStage.setMinHeight(700);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}