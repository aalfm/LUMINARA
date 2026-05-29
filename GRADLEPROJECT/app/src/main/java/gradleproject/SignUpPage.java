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

public class SignUpPage {

    public void start(Stage primaryStage) {
        // Kontainer Utama menggunakan HBox (Kiri: Banner, Kanan: Form)
        HBox mainRoot = new HBox();
        mainRoot.getStyleClass().add("signup-root");

        // ==================== SISI KIRI: BANNER GAMBAR TENUN ====================
        StackPane leftBanner = new StackPane();
        leftBanner.getStyleClass().add("signup-left-banner");
        
        // Mengatur rasio lebar agar seimbang (Banner mengambil bagian proporsional)
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

        // Overlay Gelap di dalam Banner agar teks putih terbaca jelas
        Region bannerOverlay = new Region();
        bannerOverlay.setStyle("-fx-background-color: rgba(10, 37, 64, 0.5); -fx-background-radius: 24px;");

        // Konten teks di dalam Banner Kiri
        VBox leftContent = new VBox(15);
        leftContent.setAlignment(Pos.BOTTOM_LEFT);
        leftContent.setPadding(new Insets(40));
        leftContent.getStyleClass().add("signup-left-content");

        Label leftTitle = new Label("Siap Berjelajahi?");
        leftTitle.getStyleClass().add("signup-left-title");

        Label leftSub = new Label("Bergabunglah dan temukan pengalaman budaya yang paling sesuai untukmu.");
        leftSub.getStyleClass().add("signup-left-sub");
        leftSub.setWrapText(true);

        // Logo Putih Transparan Luminara di bagian bawah
        ImageView logoWhite = new ImageView();
        try {
            logoWhite.setImage(new Image(getClass().getResourceAsStream("/aset/gambarLuminara/luminara-logoWhite.png")));
            logoWhite.setFitWidth(180);
            logoWhite.setPreserveRatio(true);
        } catch (Exception e) {}

        // Spacer untuk mendorong teks ke bawah banner
        Region leftSpacer = new Region();
        VBox.setVgrow(leftSpacer, Priority.ALWAYS);

        leftContent.getChildren().addAll(leftSpacer, leftTitle, leftSub, logoWhite);
        leftBanner.getChildren().addAll(bannerOverlay, leftContent);


        // ==================== SISI KANAN: FORMULIR DAFTAR ====================
        VBox rightContent = new VBox(20);
        rightContent.getStyleClass().add("signup-right-content");
        rightContent.setAlignment(Pos.CENTER_LEFT); // Menjaga isi form tetap rapi di tengah vertikal
        HBox.setHgrow(rightContent, Priority.ALWAYS);

        // Mini Logo di Atas Form
        ImageView miniLogo = new ImageView();
        try {
            miniLogo.setImage(new Image(getClass().getResourceAsStream("/aset/gambarLuminara/logo.png")));
            miniLogo.setFitWidth(40);
            miniLogo.setPreserveRatio(true);
        } catch (Exception e) {}

        // Judul Form
        VBox headerBox = new VBox(8);
        Label formTitle = new Label("Daftar");
        formTitle.getStyleClass().add("signup-form-title");
        Label formSub = new Label("Buat akun untuk menjelajahi cerita dan karya budaya bersama Luminara.");
        formSub.getStyleClass().add("signup-form-sub");
        formSub.setWrapText(true);
        headerBox.getChildren().addAll(formTitle, formSub);

        // Area Kumpulan Input Box
        VBox fieldsContainer = new VBox(12);
        fieldsContainer.setAlignment(Pos.TOP_LEFT);

        VBox nameBox = createInputField("Nama", "Masukkan nama kamu", false);
        VBox emailBox = createInputField("Email", "Masukkan email kamu", false);
        VBox phoneBox = createInputField("Nomor telepon", "Masukkan nomor kamu", false);
        VBox passwordBox = createInputField("Password", "Masukkan password kamu", true);

        fieldsContainer.getChildren().addAll(nameBox, emailBox, phoneBox, passwordBox);

        // Tombol Buat Akun
        Button btnSignUp = new Button("Buat Akun  ›");
        btnSignUp.getStyleClass().add("btn-signup-submit");
        btnSignUp.setCursor(javafx.scene.Cursor.HAND);
        btnSignUp.setMaxWidth(440); // Mengunci lebar maksimal tombol agar sejajar kotak input

        // Garis Pembatas "atau"
        HBox dividerRow = new HBox(10);
        dividerRow.setAlignment(Pos.CENTER);
        dividerRow.setMaxWidth(440);
        StackPane lineLeft = new StackPane(); lineLeft.getStyleClass().add("form-line"); HBox.setHgrow(lineLeft, Priority.ALWAYS);
        Label labelOr = new Label("atau"); labelOr.getStyleClass().add("label-or");
        StackPane lineRight = new StackPane(); lineRight.getStyleClass().add("form-line"); HBox.setHgrow(lineRight, Priority.ALWAYS);
        dividerRow.getChildren().addAll(lineLeft, labelOr, lineRight);

        // Teks Footer Form
        HBox loginRedirectBox = new HBox(5);
        loginRedirectBox.setAlignment(Pos.CENTER);
        loginRedirectBox.setMaxWidth(440);
        Label lblHaveAccount = new Label("Sudah punya akun?");
        lblHaveAccount.getStyleClass().add("lbl-have-account");
        Hyperlink linkLogin = new Hyperlink("Login di sini!");
        linkLogin.getStyleClass().add("link-login-redirect");
        
        loginRedirectBox.getChildren().addAll(lblHaveAccount, linkLogin);
        linkLogin.setOnAction(e -> {
        SignInPage signInPage = new SignInPage();
        signInPage.start(primaryStage);
});

        // Satukan komponen kanan
        rightContent.getChildren().addAll(miniLogo, headerBox, fieldsContainer, btnSignUp, dividerRow, loginRedirectBox);

        // Satukan Kiri & Kanan
        mainRoot.getChildren().addAll(leftBanner, rightContent);

        // Setup Scene disesuaikan dengan ukuran resolusi layar Anda (1024 x 720)
        Scene scene = new Scene(mainRoot, 1024, 720);
        try {
            // PERBAIKAN PATH: Mengikuti lokasi folder stylesheet Anda
            scene.getStylesheets().add(getClass().getResource("/style/guest/intro.css").toExternalForm());
        } catch (Exception e) {
            System.out.println("⚠️ Gagal memuat intro.css dari folder /style/guest/");
        }

        primaryStage.setTitle("Luminara - Daftar Akun Baru");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private VBox createInputField(String labelText, String placeholderText, boolean isPassword) {
        VBox fieldBox = new VBox(6);
        fieldBox.setAlignment(Pos.TOP_LEFT);

        Label inputLabel = new Label(labelText);
        inputLabel.getStyleClass().add("form-field-label");

        if (isPassword) {
            PasswordField passwordField = new PasswordField();
            passwordField.setPromptText(placeholderText);
            passwordField.getStyleClass().add("form-text-input");
            passwordField.setMaxWidth(440); // Mengunci lebar input agar tidak meluap keluar jendela
            fieldBox.getChildren().addAll(inputLabel, passwordField);
        } else {
            TextField textField = new TextField();
            textField.setPromptText(placeholderText);
            textField.getStyleClass().add("form-text-input");
            textField.setMaxWidth(440); // Mengunci lebar input
            fieldBox.getChildren().addAll(inputLabel, textField);
        }

        return fieldBox;
    }
}
