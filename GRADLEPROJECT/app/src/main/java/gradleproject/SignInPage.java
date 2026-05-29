package gradleproject;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SignInPage {

    public void start(Stage primaryStage) {
        // Kontainer Utama menggunakan HBox (Kiri: Banner, Kanan: Form)
        HBox mainRoot = new HBox();
        mainRoot.getStyleClass().add("signin-root");

        // ==================== SISI KIRI: BANNER GAMBAR TENUN ====================
        StackPane leftBanner = new StackPane();
        leftBanner.getStyleClass().add("signin-left-banner");
        
        // Mengunci rasio dimensi agar konsisten dengan halaman daftar
        leftBanner.setPrefWidth(440);
        leftBanner.setMinWidth(440);

        // Memuat Gambar Background via Java
        try {
            String bgPath = getClass().getResource("/aset/gambarLuminara/gambar-sign.png").toExternalForm();
            leftBanner.setStyle("-fx-background-image: url('" + bgPath + "'); " +
                                "-fx-background-repeat: no-repeat; " +
                                "-fx-background-size: cover; " +
                                "-fx-background-position: center center; " +
                                "-fx-background-radius: 24px;");
        } catch (Exception e) {
            leftBanner.setStyle("-fx-background-color: #1A365D; -fx-background-radius: 24px;");
        }

        // Overlay Gelap di dalam Banner
        Region bannerOverlay = new Region();
        bannerOverlay.setStyle("-fx-background-color: rgba(10, 37, 64, 0.5); -fx-background-radius: 24px;");

        // Konten teks di dalam Banner Kiri
        VBox leftContent = new VBox(15);
        leftContent.setAlignment(Pos.BOTTOM_LEFT);
        leftContent.setPadding(new Insets(40));
        leftContent.getStyleClass().add("signin-left-content");

        Label leftTitle = new Label("Selamat Datang!");
        leftTitle.getStyleClass().add("signin-left-title");

        Label leftSub = new Label("Ayo berjelajahi dan temukan pengalaman budaya yang paling sesuai untukmu.");
        leftSub.getStyleClass().add("signin-left-sub");
        leftSub.setWrapText(true);

        // Logo Putih Transparan Luminara di bagian bawah
        ImageView logoWhite = new ImageView();
        try {
            logoWhite.setImage(new Image(getClass().getResourceAsStream("/aset/gambarLuminara/luminara-logoWhite.png")));
            logoWhite.setFitWidth(180);
            logoWhite.setPreserveRatio(true);
        } catch (Exception e) {}

        // Spacer pendorong vertikal
        Region leftSpacer = new Region();
        VBox.setVgrow(leftSpacer, Priority.ALWAYS);

        leftContent.getChildren().addAll(leftSpacer, leftTitle, leftSub, logoWhite);
        leftBanner.getChildren().addAll(bannerOverlay, leftContent);


        // ==================== SISI KANAN: FORMULIR MASUK ====================
        VBox rightContent = new VBox(25); // Jarak antar grup elemen vertikal sedikit diperlebar karena input lebih sedikit
        rightContent.getStyleClass().add("signin-right-content");
        rightContent.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(rightContent, Priority.ALWAYS);

        // Judul Form Masuk
        VBox headerBox = new VBox(8);
        Label formTitle = new Label("Masuk");
        formTitle.getStyleClass().add("signin-form-title");
        Label formSub = new Label("Masuk untuk melanjutkan perjalanan budaya bersama Luminara.");
        formSub.getStyleClass().add("signin-form-sub");
        formSub.setWrapText(true);
        headerBox.getChildren().addAll(formTitle, formSub);

        // Area Kumpulan Input Box (Hanya Email & Password)
        VBox fieldsContainer = new VBox(15);
        fieldsContainer.setAlignment(Pos.TOP_LEFT);

        VBox emailBox = createInputField("Email", "Masukkan email kamu", false);
        VBox passwordBox = createInputField("Password", "Masukkan password kamu", true);

        fieldsContainer.getChildren().addAll(emailBox, passwordBox);

        // Tombol Masuk
        Button btnSignIn = new Button("Masuk  ›");
        btnSignIn.getStyleClass().add("btn-signin-submit");
        btnSignIn.setCursor(javafx.scene.Cursor.HAND);
        btnSignIn.setMaxWidth(440); // Selaras dengan kotak isian teks

        // Buka SignInPage.java dan pasang kode ini di setOnAction tombol Masuk:
        btnSignIn.setOnAction(e -> {
        System.out.println("Login berhasil, mengalihkan ke Dashboard Utama...");
    
        DashboardPage dashboard = new DashboardPage();
        dashboard.start(primaryStage);
});

        // Garis Pembatas "atau"
        HBox dividerRow = new HBox(10);
        dividerRow.setAlignment(Pos.CENTER);
        dividerRow.setMaxWidth(440);
        StackPane lineLeft = new StackPane(); lineLeft.getStyleClass().add("form-line"); HBox.setHgrow(lineLeft, Priority.ALWAYS);
        Label labelOr = new Label("atau"); labelOr.getStyleClass().add("label-or");
        StackPane lineRight = new StackPane(); lineRight.getStyleClass().add("form-line"); HBox.setHgrow(lineRight, Priority.ALWAYS);
        dividerRow.getChildren().addAll(lineLeft, labelOr, lineRight);

        // Teks Footer Form: Belum punya akun? Daftar Sekarang
        HBox signUpRedirectBox = new HBox(5);
        signUpRedirectBox.setAlignment(Pos.CENTER);
        signUpRedirectBox.setMaxWidth(440);
        Label lblNoAccount = new Label("Belum punya akun?");
        lblNoAccount.getStyleClass().add("lbl-no-account");
        Hyperlink linkSignUp = new Hyperlink("Daftar Sekarang");
        linkSignUp.getStyleClass().add("link-signup-redirect");
        
        // Logika pengalihan ke halaman daftar jika tautan diklik
        linkSignUp.setOnAction(e -> {
            SignUpPage signUpPage = new SignUpPage();
            signUpPage.start(primaryStage);
        });

        signUpRedirectBox.getChildren().addAll(lblNoAccount, linkSignUp);

        // Satukan komponen kanan
        rightContent.getChildren().addAll(headerBox, fieldsContainer, btnSignIn, dividerRow, signUpRedirectBox);

        // Satukan Kiri & Kanan ke Root Kontainer
        mainRoot.getChildren().addAll(leftBanner, rightContent);

        // Setup Jendela Aplikasi (Resolusi 1024 x 720)
        Scene scene = new Scene(mainRoot, 1024, 720);
        try {
            scene.getStylesheets().add(getClass().getResource("/style/guest/intro.css").toExternalForm());
        } catch (Exception e) {
            System.out.println("⚠️ Gagal memuat intro.css di SignInPage");
        }

        primaryStage.setTitle("Luminara - Masuk Akun");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Fungsi pembantu pembuatan baris komponen input teks
    private VBox createInputField(String labelText, String placeholderText, boolean isPassword) {
        VBox fieldBox = new VBox(6);
        fieldBox.setAlignment(Pos.TOP_LEFT);

        Label inputLabel = new Label(labelText);
        inputLabel.getStyleClass().add("form-field-label");

        if (isPassword) {
            PasswordField passwordField = new PasswordField();
            passwordField.setPromptText(placeholderText);
            passwordField.getStyleClass().add("form-text-input");
            passwordField.setMaxWidth(440);
            fieldBox.getChildren().addAll(inputLabel, passwordField);
        } else {
            TextField textField = new TextField();
            textField.setPromptText(placeholderText);
            textField.getStyleClass().add("form-text-input");
            textField.setMaxWidth(440);
            fieldBox.getChildren().addAll(inputLabel, textField);
        }

        return fieldBox;
    }
}
