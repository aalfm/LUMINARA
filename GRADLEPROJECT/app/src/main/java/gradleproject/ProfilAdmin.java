package gradleproject;

import gradleproject.config.DbConnect;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ProfilAdmin {

    private VBox view;
    
    private TextField txtNama;
    private TextField txtEmail;
    private TextField txtNoTelepon;
    private TextField txtPassword;
    private Button btnEdit;
    
    // Label dinamis untuk header
    private Label lblLetter;
    private Label lblKamiAdmin;
    
    private boolean isEditMode = false;
    
    // Asumsi ID admin yang sedang login. Sesuaikan jika Anda menggunakan UserSession.
    private int adminId = 1; 

    public ProfilAdmin() {
        // Coba ambil ID dari session jika ada
        try {
            if (UserSession.getInstance() != null) {
                adminId = UserSession.getInstance().getUserId();
            }
        } catch (Exception ignored) {}

        view = new VBox(15); 
        view.setPadding(new Insets(20, 20, 20, 80)); 
        view.setAlignment(Pos.TOP_LEFT);
        view.setMaxWidth(850);

        // =====================================================================
        // 1. SEKSYEN BANNER ATAS
        // =====================================================================
        StackPane bannerPane = new StackPane();
        bannerPane.setPrefSize(770, 160);
        bannerPane.setMaxSize(770, 160);
        bannerPane.setStyle("-fx-background-color: #0A3B5C; -fx-background-radius: 20;"); 

        ImageView imgBanner = new ImageView();
        try {
            Image img = new Image(getClass().getResourceAsStream("/aset/gambarLuminara/banner-makassar.png"));
            imgBanner.setImage(img);
            imgBanner.setFitWidth(770);
            imgBanner.setFitHeight(160);
            
            Rectangle clip = new Rectangle(770, 160);
            clip.setArcWidth(30);
            clip.setArcHeight(30);
            imgBanner.setClip(clip);
        } catch (Exception e) {
            System.out.println("⚠️ Gambar banner tidak ditemukan, menggunakan warna solid.");
        }
        bannerPane.getChildren().add(imgBanner);

        // =====================================================================
        // 2. SEKSYEN AVATAR & INFO SINGKAT
        // =====================================================================
        HBox profileHeaderRow = new HBox(20);
        profileHeaderRow.setMaxWidth(770);
        profileHeaderRow.setAlignment(Pos.BOTTOM_LEFT);
        profileHeaderRow.setPadding(new Insets(0, 20, 0, 20));

        StackPane avatarNode = new StackPane();
        avatarNode.setPrefSize(110, 110);
        avatarNode.setMaxSize(110, 110);
        avatarNode.setStyle(
            "-fx-background-color: linear-gradient(from 0% 0% to 100% 100%, #FFE0B2, #FF9800); " + 
            "-fx-background-radius: 55; " +
            "-fx-border-color: #FFFFFF; " + 
            "-fx-border-width: 4; " +
            "-fx-border-radius: 55; " +
            "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.15), 10, 0, 0, 5);"
        );
        avatarNode.setTranslateY(-35); 

        lblLetter = new Label("A"); // Default, akan ditimpa database
        lblLetter.setStyle("-fx-font-family: 'Poppins'; -fx-font-weight: bold; -fx-font-size: 44px; -fx-text-fill: white;");
        avatarNode.getChildren().add(lblLetter);

        VBox metaBox = new VBox(2);
        metaBox.setAlignment(Pos.TOP_LEFT);

        lblKamiAdmin = new Label("Admin"); // Default
        lblKamiAdmin.setStyle("-fx-font-family: 'Poppins'; -fx-font-weight: bold; -fx-font-size: 16px; -fx-text-fill: #1A3C5A;");

        Label lblLocation = new Label("📍 Makassar, Sulawesi Selatan");
        lblLocation.setStyle("-fx-font-family: 'Poppins'; -fx-font-size: 12px; -fx-text-fill: #5A7184;");

        Label lblSince = new Label("📅 Sejak November 2025");
        lblSince.setStyle("-fx-font-family: 'Poppins'; -fx-font-size: 12px; -fx-text-fill: #5A7184;");

        metaBox.getChildren().addAll(lblKamiAdmin, lblLocation, lblSince);

        Region headerSpacer = new Region();
        HBox.setHgrow(headerSpacer, Priority.ALWAYS);

        btnEdit = new Button("Edit profil");
        btnEdit.setStyle(
            "-fx-background-color: #FF9800; -fx-text-fill: white; -fx-font-family: 'Poppins'; " +
            "-fx-font-size: 11px; -fx-font-weight: bold; -fx-background-radius: 12; -fx-padding: 6 20;"
        );
        btnEdit.setCursor(javafx.scene.Cursor.HAND);

        // 👉 LOGIKA INTERAKTIF TOMBOL TOGGLE EDIT/SIMPAN
        btnEdit.setOnAction(event -> {
            if (!isEditMode) {
                // Berubah ke Mode Edit (Membuka Kunci Input)
                isEditMode = true;
                btnEdit.setText("Simpan");
                btnEdit.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-family: 'Poppins'; -fx-font-size: 11px; -fx-font-weight: bold; -fx-background-radius: 12; -fx-padding: 6 25;"); 
                
                txtNama.setEditable(true);
                txtEmail.setEditable(true);
                txtNoTelepon.setEditable(true);
                txtPassword.setEditable(true);
                
                txtNama.requestFocus(); 
            } else {
                // 👉 SIMPAN KE DATABASE
                boolean isSaved = saveDataAdmin();
                
                if (isSaved) {
                    // Berubah ke Mode Lihat (Mengunci Kembali Input)
                    isEditMode = false;
                    btnEdit.setText("Edit profil");
                    btnEdit.setStyle("-fx-background-color: #FF9800; -fx-text-fill: white; -fx-font-family: 'Poppins'; -fx-font-size: 11px; -fx-font-weight: bold; -fx-background-radius: 12; -fx-padding: 6 20;"); 
                    
                    txtNama.setEditable(false);
                    txtEmail.setEditable(false);
                    txtNoTelepon.setEditable(false);
                    txtPassword.setEditable(false);
                    
                    // Update header secara live
                    String namaBaru = txtNama.getText();
                    lblKamiAdmin.setText(namaBaru);
                    if (!namaBaru.isEmpty()) {
                        lblLetter.setText(namaBaru.substring(0, 1).toUpperCase());
                    }
                    
                    showAlert(Alert.AlertType.INFORMATION, "Sukses", "Profil berhasil diperbarui!");
                }
            }
        });

        profileHeaderRow.getChildren().addAll(avatarNode, metaBox, headerSpacer, btnEdit);

        VBox infoSectionWrapper = new VBox(15);
        infoSectionWrapper.setPadding(new Insets(-15, 0, 0, 0));

        // =====================================================================
        // 3. JUDUL INFORMASI ADMIN
        // =====================================================================
        Label lblInfoTitle = new Label("Informasi Admin");
        lblInfoTitle.setStyle(
            "-fx-border-color: transparent transparent transparent #FF9800; " + 
            "-fx-border-width: 0 0 0 4; -fx-padding: 0 0 0 10; " +
            "-fx-font-family: 'Poppins'; -fx-font-weight: bold; -fx-font-size: 15px; -fx-text-fill: #1A3C5A;"
        );

        // =====================================================================
        // 4. GRID FORM INPUT FIELD
        // =====================================================================
        GridPane infoGrid = new GridPane();
        infoGrid.setHgap(30); 
        infoGrid.setVgap(15); 
        infoGrid.setMaxWidth(770);

        txtNama = new TextField();
        txtEmail = new TextField();
        txtNoTelepon = new TextField();
        txtPassword = new TextField();

        infoGrid.add(createFieldBlock("Nama", txtNama, true), 0, 0);
        infoGrid.add(createFieldBlock("Email", txtEmail, false), 1, 0);
        infoGrid.add(createFieldBlock("No Telepon", txtNoTelepon, false), 0, 1);
        infoGrid.add(createFieldBlock("Password", txtPassword, false), 1, 1);

        infoSectionWrapper.getChildren().addAll(lblInfoTitle, infoGrid);
        view.getChildren().addAll(bannerPane, profileHeaderRow, infoSectionWrapper);
        
        // 🔥 LOAD DATA SAAT HALAMAN DIBUKA
        loadDataAdmin();
    }

    // =====================================================================
    // 👉 FUNGSI DATABASE: LOAD DATA
    // =====================================================================
    private void loadDataAdmin() {
        String sql = "SELECT username, email, phone_number, password FROM users WHERE id = ?";
        
        try (Connection conn = DbConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
             
            ps.setInt(1, adminId);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                String nama = rs.getString("username");
                txtNama.setText(nama);
                txtEmail.setText(rs.getString("email"));
                
                String phone = rs.getString("phone_number");
                txtNoTelepon.setText(phone != null ? phone : "-");
                
                txtPassword.setText(rs.getString("password"));
                
                // Update avatar dan nama header
                lblKamiAdmin.setText(nama);
                if (nama != null && !nama.isEmpty()) {
                    lblLetter.setText(nama.substring(0, 1).toUpperCase());
                }
            }
        } catch (Exception e) {
            System.err.println("Gagal memuat profil admin: " + e.getMessage());
        }
    }

    // =====================================================================
    // 👉 FUNGSI DATABASE: SAVE DATA
    // =====================================================================
    private boolean saveDataAdmin() {
        String nama = txtNama.getText().trim();
        String email = txtEmail.getText().trim();
        String phone = txtNoTelepon.getText().trim();
        String password = txtPassword.getText().trim();

        if (nama.isEmpty() || email.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Peringatan", "Nama, Email, dan Password tidak boleh kosong.");
            return false;
        }

        String sql = "UPDATE users SET username = ?, email = ?, phone_number = ?, password = ? WHERE id = ?";
        
        try (Connection conn = DbConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
             
            ps.setString(1, nama);
            ps.setString(2, email);
            ps.setString(3, phone);
            ps.setString(4, password);
            ps.setInt(5, adminId);
            
            int rowsAffected = ps.executeUpdate();
            
            // Jika berhasil disimpan, perbarui juga session yang sedang berjalan
            if (rowsAffected > 0) {
                try {
                    if (UserSession.getInstance() != null) {
                        UserSession.getInstance().setUsername(nama);
                    }
                } catch (Exception ignored) {}
            }
            
            return rowsAffected > 0;
            
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Kesalahan", "Gagal menyimpan data: " + e.getMessage());
            return false;
        }
    }

    // --- METHOD HELPER: Alert GUI ---
    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    // --- METHOD HELPER: Membuat Isian Form ---
    private VBox createFieldBlock(String labelText, TextField textField, boolean hasEditIcon) {
        VBox block = new VBox(5);
        GridPane.setHgrow(block, Priority.ALWAYS);

        Label lblField = new Label(labelText);
        lblField.setStyle("-fx-font-family: 'Poppins'; -fx-font-size: 11px; -fx-text-fill: #1A3C5A; -fx-font-weight: bold;");

        HBox fieldContainer = new HBox(10);
        fieldContainer.setAlignment(Pos.CENTER_LEFT);
        fieldContainer.setPrefWidth(370); 
        fieldContainer.setStyle(
            "-fx-background-color: #F8F9FA; -fx-border-color: #Cdd5De; " +
            "-fx-border-radius: 10; -fx-background-radius: 10; -fx-padding: 6 15 6 15;" 
        );

        textField.setStyle(
            "-fx-background-color: transparent; -fx-background-insets: 0; " +
            "-fx-background-radius: 0; -fx-padding: 4 0 4 0; " +
            "-fx-font-family: 'Poppins'; -fx-font-size: 12px; -fx-text-fill: #1A3C5A;"
        );
        textField.setEditable(false); 
        HBox.setHgrow(textField, Priority.ALWAYS); 

        fieldContainer.getChildren().add(textField);

        if (hasEditIcon) {
            ImageView editIcon = new ImageView();
            try {
                Image img = new Image(getClass().getResourceAsStream("/aset/iconLuminara/icon-edit-pen.png"));
                editIcon.setImage(img);
                editIcon.setFitWidth(14);
                editIcon.setFitHeight(14);
                editIcon.setPreserveRatio(true);
            } catch(Exception e) {
                Label lblFallback = new Label("📝");
                lblFallback.setStyle("-fx-font-size: 11px;");
                editIcon.setUserData(lblFallback);
            }
            fieldContainer.getChildren().add(editIcon);
        }

        block.getChildren().addAll(lblField, fieldContainer);
        return block;
    }

    public Parent getView() {
        return view;
    }
}