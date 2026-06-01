package gradleproject;

import gradleproject.models.User;
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

    private TextField txtNama;
    private TextField txtEmail;
    private TextField txtPhone;
    private PasswordField txtPassword;
    private Label lblStatus; 
    private String rolePendaftar; 

    public SignUpPage(String role) {
        this.rolePendaftar = role; 
    }

    public void start(Stage primaryStage) {
        // 🎯 FIX 1: Hilangkan padding luar agar gambar bisa menempel ke tepi layar
        HBox mainRoot = new HBox();
        mainRoot.setPadding(new Insets(0));
        mainRoot.setStyle("-fx-background-color: #FDFBF7; -fx-padding: 0;");

        // ==================== SISI KIRI: BANNER GAMBAR TENUN ====================
        StackPane leftBanner = new StackPane();
        leftBanner.setPrefWidth(480);
        leftBanner.setMinWidth(480);

        // 🎯 FIX 2: Radius disesuaikan hanya di sisi kanan (0 30 30 0)
        try {
            String bgPath = getClass().getResource("/aset/gambarLuminara/gambar-sign.png").toExternalForm();
            leftBanner.setStyle("-fx-background-image: url('" + bgPath + "'); " +
                                "-fx-background-repeat: no-repeat; " +
                                "-fx-background-size: cover; " +
                                "-fx-background-position: center center; " +
                                "-fx-background-radius: 0 30 30 0;");
        } catch (Exception e) {
            leftBanner.setStyle("-fx-background-color: #1A365D; -fx-background-radius: 0 30 30 0;");
        }

        Region bannerOverlay = new Region();
        bannerOverlay.setStyle("-fx-background-color: rgba(10, 37, 64, 0.4); -fx-background-radius: 0 30 30 0;");

        VBox leftContent = new VBox(10);
        leftContent.setAlignment(Pos.BOTTOM_LEFT);
        leftContent.setPadding(new Insets(50, 40, 50, 40));

        Label leftTitle = new Label("Siap Berjelajah?");
        leftTitle.setStyle("-fx-font-family: 'Poppins'; -fx-font-weight: bold; -fx-font-size: 34px; -fx-text-fill: white;");

        Label leftSub = new Label("Bergabunglah dan temukan\npengalaman budaya yang\npaling sesuai untukmu.");
        leftSub.setStyle("-fx-font-family: 'Poppins'; -fx-font-size: 14px; -fx-text-fill: #E2E8F0; -fx-line-spacing: 5px;");
        leftSub.setWrapText(true);

        ImageView logoWhite = new ImageView();
        try {
            // 🎯 FIX 3: Ganti dengan textWhite agar logonya rapi
            logoWhite.setImage(new Image(getClass().getResourceAsStream("/aset/gambarLuminara/luminara-textWhite.png")));
            logoWhite.setFitWidth(160);
            logoWhite.setPreserveRatio(true);
        } catch (Exception e) {}

        Region leftSpacer = new Region();
        VBox.setVgrow(leftSpacer, Priority.ALWAYS);
        
        Region innerSpacer = new Region();
        innerSpacer.setPrefHeight(40);

        leftContent.getChildren().addAll(leftSpacer, leftTitle, leftSub, innerSpacer, logoWhite);
        leftBanner.getChildren().addAll(bannerOverlay, leftContent);

        // ==================== SISI KANAN: FORMULIR DAFTAR ====================
        VBox rightContent = new VBox(15);
        rightContent.setAlignment(Pos.CENTER_LEFT); 
        // Jarak form kanan agar tidak menempel ke tepi layar
        rightContent.setPadding(new Insets(20, 80, 20, 80));
        HBox.setHgrow(rightContent, Priority.ALWAYS);

        Button btnKembali = new Button("‹ Kembali");
        btnKembali.setStyle("-fx-background-color: transparent; -fx-text-fill: #64748B; -fx-font-family: 'Poppins'; -fx-font-weight: bold; -fx-font-size: 14px; -fx-cursor: hand; -fx-padding: 0 0 10 0;");
        btnKembali.setOnAction(e -> {
            IntroPage3 introPage = new IntroPage3();
            introPage.start(primaryStage);
        });

        VBox headerBox = new VBox(8);
        Label formTitle = new Label("Daftar (" + rolePendaftar + ")");
        formTitle.setStyle("-fx-font-family: 'Poppins'; -fx-font-weight: bold; -fx-font-size: 32px; -fx-text-fill: #003A6C;");
        Label formSub = new Label("Buat akun untuk menjelajahi cerita dan karya budaya bersama Luminara.");
        formSub.setStyle("-fx-font-family: 'Poppins'; -fx-font-size: 14px; -fx-text-fill: #495057;");
        formSub.setWrapText(true);
        headerBox.getChildren().addAll(formTitle, formSub);

        txtNama = new TextField();
        txtEmail = new TextField();
        txtPhone = new TextField();
        txtPassword = new PasswordField();
        
        lblStatus = new Label();
        lblStatus.setStyle("-fx-font-family: 'Poppins'; -fx-font-size: 13px; -fx-font-weight: bold;");

        VBox fieldsContainer = new VBox(12);
        fieldsContainer.setAlignment(Pos.TOP_LEFT);

        // 🎯 FIX 4: Form yang menggunakan desain dari createInputField terbaru
        VBox nameBox = createInputField("Nama", txtNama, "Masukkan nama kamu");
        VBox emailBox = createInputField("Email", txtEmail, "Masukkan email kamu");
        VBox phoneBox = createInputField("Nomor telepon", txtPhone, "Masukkan nomor kamu");
        VBox passwordBox = createInputField("Password", txtPassword, "Masukkan password kamu");

        fieldsContainer.getChildren().addAll(nameBox, emailBox, phoneBox, passwordBox);

        Button btnSignUp = new Button("Buat Akun");
        btnSignUp.setStyle("-fx-background-color: #FF9800; -fx-text-fill: white; -fx-font-family: 'Poppins'; -fx-font-weight: bold; -fx-font-size: 14px; -fx-background-radius: 8px; -fx-padding: 12px; -fx-cursor: hand;");
        btnSignUp.setMaxWidth(Double.MAX_VALUE); // Memanjang full menyesuaikan container

        // =====================================================================
        // LOGIKA PENDAFTARAN DATABASE (Tidak diubah)
        // =====================================================================
        btnSignUp.setOnAction(event -> {
            String nama = txtNama.getText().trim();
            String email = txtEmail.getText().trim();
            String telepon = txtPhone.getText().trim();
            String password = txtPassword.getText().trim();

            if (nama.isEmpty() || email.isEmpty() || telepon.isEmpty() || password.isEmpty()) {
                lblStatus.setText("⚠️ Semua kolom data wajib diisi!");
                lblStatus.setStyle("-fx-text-fill: #E53E3E;");
                return;
            }

            User userBaru = new User();
            userBaru.setUsername(nama);
            userBaru.setEmail(email);
            userBaru.setPassword(password); 
            userBaru.setPhoneNumber(telepon);
            userBaru.setRole(this.rolePendaftar); 

            gradleproject.services.AuthService authService = new gradleproject.services.AuthService();
            String statusDaftar = authService.register(userBaru);

            if (statusDaftar.equals("SUKSES")) {
                System.out.println("🎉 Data berhasil disimpan secara aman!");
                lblStatus.setText("✅ Akun berhasil dibuat! Mengalihkan...");
                lblStatus.setStyle("-fx-text-fill: #38A169;");
                
                if (this.rolePendaftar != null && this.rolePendaftar.equalsIgnoreCase("Penyelenggara")) {
                    new Main(0).start(primaryStage); 
                } else {
                    new MainUser().start(primaryStage); 
                }
                
            } else {
                lblStatus.setText("❌ " + statusDaftar);
                lblStatus.setStyle("-fx-text-fill: #E53E3E;");
            }
        });

        HBox dividerRow = new HBox(10);
        dividerRow.setAlignment(Pos.CENTER);
        dividerRow.setMaxWidth(Double.MAX_VALUE);
        
        StackPane lineLeft = new StackPane(); 
        lineLeft.setStyle("-fx-background-color: #E2E8F0; -fx-pref-height: 1px;"); 
        HBox.setHgrow(lineLeft, Priority.ALWAYS);
        
        Label labelOr = new Label("atau"); 
        labelOr.setStyle("-fx-text-fill: #94A3B8; -fx-font-family: 'Poppins'; -fx-font-size: 12px;");
        
        StackPane lineRight = new StackPane(); 
        lineRight.setStyle("-fx-background-color: #E2E8F0; -fx-pref-height: 1px;"); 
        HBox.setHgrow(lineRight, Priority.ALWAYS);
        
        dividerRow.getChildren().addAll(lineLeft, labelOr, lineRight);

        HBox loginRedirectBox = new HBox(5);
        loginRedirectBox.setAlignment(Pos.CENTER);
        loginRedirectBox.setMaxWidth(Double.MAX_VALUE);
        
        Label lblHaveAccount = new Label("Sudah punya akun?");
        lblHaveAccount.setStyle("-fx-font-family: 'Poppins'; -fx-text-fill: #1A3C5A; -fx-font-weight: bold;");
        
        Hyperlink linkLogin = new Hyperlink("Login di sini!");
        linkLogin.setStyle("-fx-font-family: 'Poppins'; -fx-text-fill: #003A6C; -fx-font-weight: bold; -fx-underline: true; -fx-padding: 0; -fx-border-color: transparent;");
        
        loginRedirectBox.getChildren().addAll(lblHaveAccount, linkLogin);
        linkLogin.setOnAction(e -> {
            SignInPage signInPage = new SignInPage(this.rolePendaftar); 
            signInPage.start(primaryStage);
        });

        rightContent.getChildren().addAll(btnKembali, headerBox, fieldsContainer, lblStatus, btnSignUp, dividerRow, loginRedirectBox);
        mainRoot.getChildren().addAll(leftBanner, rightContent);

        Scene scene = new Scene(mainRoot, 1280, 650);
        try {
            scene.getStylesheets().add(getClass().getResource("/style/guest/intro.css").toExternalForm());
        } catch (Exception e) {}

        primaryStage.setTitle("Luminara - Daftar Akun Baru");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // 🎯 FIX 5: Metode Styling Kolom Inputan yang diperbarui
    private VBox createInputField(String labelText, javafx.scene.control.TextInputControl inputField, String placeholderText) {
        VBox fieldBox = new VBox(6);
        fieldBox.setAlignment(Pos.TOP_LEFT);
        
        Label inputLabel = new Label(labelText);
        inputLabel.setStyle("-fx-font-family: 'Poppins'; -fx-font-size: 13px; -fx-text-fill: #003A6C; -fx-font-weight: bold;");
        
        inputField.setPromptText(placeholderText);
        inputField.setMaxWidth(Double.MAX_VALUE); 
        inputField.setStyle("-fx-background-color: #F8F9FA; -fx-border-color: #CBD5E1; -fx-border-radius: 8px; -fx-background-radius: 8px; -fx-padding: 12px 15px; -fx-font-family: 'Poppins'; -fx-font-size: 13px;");
        
        fieldBox.getChildren().addAll(inputLabel, inputField);
        return fieldBox;
    }
}