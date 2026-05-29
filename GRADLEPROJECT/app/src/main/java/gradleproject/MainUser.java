package gradleproject;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainUser extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Memanggil Dashboard baru khusus Role User
        DashboardUser dashboardUser = new DashboardUser();
        
        // Mengatur resolusi dasar jendela aplikasi (1100 x 700)
        Scene scene = new Scene(dashboardUser.getView(), 1100, 700);
        
        // Sistem Pengisian CSS Fallback Aman
        try {
            String css = getClass().getResource("/style/admin/beranda.css").toExternalForm();
            scene.getStylesheets().add(css);
        } catch (Exception e) {
            System.out.println("ℹ️ Info: Sistem berjalan penuh menggunakan konfigurasi inline styling.");
        }

        // Konfigurasi Jendela Jendela Utama User
        primaryStage.setTitle("Luminara App - Portal Pengguna");
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(1024); 
        primaryStage.setMinHeight(700);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}