package gradleproject;

import gradleproject.dao.UserDAO;
import gradleproject.models.User;
import gradleproject.services.AuthService;

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

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


public class SignInPage {
    
    private Stage primaryStage;
    private String roleKonteks; 
    private TextField emailField;
    private PasswordField passwordField;
    private Label lblStatus; 
    
    public SignInPage(String roleKonteks) {
        this.roleKonteks = roleKonteks;
    }
    
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        
        HBox mainRoot = new HBox();
        mainRoot.setPadding(new Insets(0)); 
        mainRoot.setStyle("-fx-background-color: #FDFBF7; -fx-padding: 0;"); 
        
        // ==================== SISI KIRI: BANNER GAMBAR TENUN ====================
        StackPane leftBanner = new StackPane();
        leftBanner.setPrefWidth(480); 
        leftBanner.setMinWidth(480);
        
        try {
            String bgPath = getClass().getResource("/aset/gambarLuminara/gambar-sign.png").toExternalForm();
            leftBanner.setStyle("-fx-background-image: url('" + bgPath + "'); " +
            "-fx-background-repeat: no-repeat; " +
            "-fx-background-size: cover; " +
            "-fx-background-position: center center; " +
            "-fx-background-radius: 0 30 30 0;"); 
        } catch (Exception e) {}
        
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

        // ==================== SISI KANAN: FORMULIR MASUK ====================
        VBox rightContent = new VBox(25); 
        rightContent.setAlignment(Pos.CENTER_LEFT);
        rightContent.setPadding(new Insets(40, 80, 40, 80)); 
        HBox.setHgrow(rightContent, Priority.ALWAYS);

        Button btnKembali = new Button("‹ Kembali");
        btnKembali.setStyle("-fx-background-color: transparent; -fx-text-fill: #64748B; -fx-font-family: 'Poppins'; -fx-font-weight: bold; -fx-font-size: 14px; -fx-cursor: hand; -fx-padding: 0 0 10 0;");
        
        btnKembali.setOnAction(e -> {
            IntroPage3 introPage = new IntroPage3();
            introPage.start(primaryStage);
        });

        VBox headerBox = new VBox(8);
        Label formTitle = new Label("Masuk (" + roleKonteks + ")");
        formTitle.setStyle("-fx-font-family: 'Poppins'; -fx-font-weight: bold; -fx-font-size: 32px; -fx-text-fill: #003A6C;");
        
        Label formSub = new Label("Masuk untuk melanjutkan perjalanan budaya bersama Luminara.");
        formSub.setStyle("-fx-font-family: 'Poppins'; -fx-font-size: 14px; -fx-text-fill: #495057;");
        formSub.setWrapText(true);
        headerBox.getChildren().addAll(formTitle, formSub);
        
        emailField = new TextField();
        passwordField = new PasswordField();
        
        lblStatus = new Label();
        lblStatus.setStyle("-fx-font-family: 'Poppins'; -fx-font-size: 13px; -fx-font-weight: bold;");
        
        VBox fieldsContainer = new VBox(18);
        fieldsContainer.setAlignment(Pos.TOP_LEFT);
        
        VBox emailBox = createInputField("Email", emailField, "Masukkan email kamu");
        VBox passwordBox = createInputField("Password", passwordField, "Masukkan password kamu");
        
        fieldsContainer.getChildren().addAll(emailBox, passwordBox);
        
        Button btnSignIn = new Button("Masuk");
        btnSignIn.setStyle("-fx-background-color: #FF9800; -fx-text-fill: white; -fx-font-family: 'Poppins'; -fx-font-weight: bold; -fx-font-size: 14px; -fx-background-radius: 8px; -fx-padding: 12px; -fx-cursor: hand;");
        btnSignIn.setMaxWidth(Double.MAX_VALUE); 

        // =================================================================================
        // 🎯 LOGIKA LOGIN UTAMA
        // =================================================================================
        btnSignIn.setOnAction(e -> {
        String email = emailField.getText().trim();
        String password = passwordField.getText().trim();

        // 1. Cek user
        User userCheck = new UserDAO().findByEmail(email);

        if (userCheck == null) {
            lblStatus.setText("❌ Email tidak terdaftar.");
            return;
        }

        // 2. Cek apakah diblokir
        if ("Banned".equalsIgnoreCase(userCheck.getAccountStatus())) {
            showErrorAlert("Akun Diblokir", "Akun Anda telah dinonaktifkan oleh Admin.");
            return;
        }

        // 3. Login
        AuthService auth = new AuthService();
        User user = auth.login(email, password);

        if (user != null) {

            if (!user.getRole().equalsIgnoreCase(roleKonteks)) {
                lblStatus.setText("❌ Akun bukan untuk role " + roleKonteks);
                return;
            }

            // Simpan session user
            UserSession.getInstance().setUser(
                user.getUsername(),
                user.getId(),
                user.getRole()
            );

            pindahDashboard(user.getRole(), user.getId());

        } else {
            lblStatus.setText("❌ Password salah.");
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

        HBox signUpRedirectBox = new HBox(5);
        signUpRedirectBox.setAlignment(Pos.CENTER);
        signUpRedirectBox.setMaxWidth(Double.MAX_VALUE);
        
        Label lblNoAccount = new Label("Belum punya akun?");
        lblNoAccount.setStyle("-fx-font-family: 'Poppins'; -fx-text-fill: #1A3C5A; -fx-font-weight: bold;");
        
        Hyperlink linkSignUp = new Hyperlink("Daftar di sini!");
        linkSignUp.setStyle("-fx-font-family: 'Poppins'; -fx-text-fill: #003A6C; -fx-font-weight: bold; -fx-underline: true; -fx-padding: 0; -fx-border-color: transparent;");

        linkSignUp.setOnAction(e -> {
            SignUpPage signUpPage = new SignUpPage(this.roleKonteks); 
            signUpPage.start(primaryStage);
        });
        signUpRedirectBox.getChildren().addAll(lblNoAccount, linkSignUp);
        
        if (roleKonteks.equalsIgnoreCase("ADMIN")) {
            dividerRow.setVisible(false);
            dividerRow.setManaged(false);
            signUpRedirectBox.setVisible(false);
            signUpRedirectBox.setManaged(false);
        }

        rightContent.getChildren().addAll(btnKembali, headerBox, fieldsContainer, lblStatus, btnSignIn, dividerRow, signUpRedirectBox);
        
        mainRoot.getChildren().addAll(leftBanner, rightContent);
        
        Scene scene = new Scene(mainRoot, 1280, 650);
        try {
            scene.getStylesheets().add(getClass().getResource("/style/guest/intro.css").toExternalForm());
        } catch (Exception e) {}

        primaryStage.setTitle("Luminara - Masuk Akun");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    private void showErrorAlert(String title, String content) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null); 
        alert.setContentText(content);
        alert.showAndWait(); 
    }

    // 🎯 FIX: Method ini sekarang butuh "userId" untuk dikirim ke halaman selanjutnya
    private void pindahDashboard(String role, int userId) {
        if (role.equalsIgnoreCase("ADMIN")) {
            // 🎯 Hapus tanda // agar halamannya berpindah!
            // Pastikan kamu memang punya class bernama MainAdmin.
            MainAdmin adminPage = new MainAdmin(); 
            adminPage.start(primaryStage);
            
        } else if (role.equalsIgnoreCase("ORGANIZER") || role.equalsIgnoreCase("PENYELENGGARA")) {
            ManajemenAcaraView dashboard = new ManajemenAcaraView(userId);
            Scene dashboardScene = new Scene(dashboard, 1280, 650); 
            primaryStage.setScene(dashboardScene);
            
        } else {
            // Lakukan hal yang sama untuk User biasa jika sudah ada halamannya
            MainUser userPage = new MainUser();
            userPage.start(primaryStage);
        }
    }
    

    private VBox createInputField(String labelText, javafx.scene.control.TextInputControl inputField, String placeholderText) {
        VBox fieldBox = new VBox(8);
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
