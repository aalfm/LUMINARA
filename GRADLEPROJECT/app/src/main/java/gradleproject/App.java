package gradleproject;

import javafx.application.Application;
import javafx.stage.Stage;
import gradleproject.config.DbConnect;
import gradleproject.config.DbInitialization;

public class App extends Application {

    public void start (Stage stage){
        DbConnect.getConnection();
        stage.show();
    }
    public String getGreeting() {
        return "Hello World!";
    }

    public static void main(String[] args) {
        DbInitialization.initDatabase();
        System.out.println("App started");
        launch ();
    }
}