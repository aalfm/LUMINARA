package gradleproject;

import javafx.application.Application;
import javafx.stage.Stage;
import gradleproject.config.DbConnect;
import gradleproject.config.DbInitialization;


public class App extends Application {

    @Override
    public void start(Stage stage) {
        // 1. Memastikan koneksi database aktif saat aplikasi mendesain UI
        DbConnect.getConnection();
        DbInitialization.initDatabase();

        
        IntroPage3 introPage3 = new IntroPage3();
        introPage3.start(stage);

        IntroPage2 introPage2 = new IntroPage2();
        introPage2.start(stage);

        IntroPage introPage = new IntroPage();
        introPage.start(stage);

    }

    public String getGreeting() {
        return "Hello World!";
    }

    public static void main(String[] args) {
        
        System.out.println("App started");
        // 2. Luncurkan UI JavaFX
        launch(args);
    }
}