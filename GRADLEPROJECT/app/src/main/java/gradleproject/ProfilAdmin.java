package gradleproject;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField; // Import TextField baru
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

public class ProfilAdmin {

    private VBox view;
    
    // 👉 KUNCI 1: Deklarasikan Input Field secara global agar bisa diakses oleh tombol aksi
    private TextField txtNama;
    private TextField txtEmail;
    private TextField txtNoTelepon;
    private TextField txtPassword;
    private Button btnEdit;
    
    // Status penanda apakah admin sedang mengedit atau hanya melihat
    private boolean isEditMode = false;

    public ProfilAdmin() {
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
        // 2. SEKSYEN AVATAR & INFO SINGKAT (OVERLAP)
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

        Label lblLetter = new Label("V");
        lblLetter.setStyle("-fx-font-family: 'Poppins'; -fx-font-weight: bold; -fx-font-size: 44px; -fx-text-fill: white;");
        avatarNode.getChildren().add(lblLetter);

        VBox metaBox = new VBox(2);
        metaBox.setAlignment(Pos.TOP_LEFT);

        Label lblKamiAdmin = new Label("Kami admin.");
        lblKamiAdmin.setStyle("-fx-font-family: 'Poppins'; -fx-font-weight: bold; -fx-font-size: 16px; -fx-text-fill: #1A3C5A;");

        Label lblLocation = new Label("📍 Makassar, Sulawesi Selatan");
        lblLocation.setStyle("-fx-font-family: 'Poppins'; -fx-font-size: 12px; -fx-text-fill: #5A7184;");

        Label lblSince = new Label("📅 Sejak November 2025");
        lblSince.setStyle("-fx-font-family: 'Poppins'; -fx-font-size: 12px; -fx-text-fill: #5A7184;");

        metaBox.getChildren().addAll(lblKamiAdmin, lblLocation, lblSince);

        Region headerSpacer = new Region();
        HBox.setHgrow(headerSpacer, Priority.ALWAYS);

        // Tombol Edit Profil Utama
        btnEdit = new Button("Edit profil");
        btnEdit.setStyle(
            "-fx-background-color: #FF9800; " +
            "-fx-text-fill: white; " +
            "-fx-font-family: 'Poppins'; " +
            "-fx-font-size: 11px; " +
            "-fx-font-weight: bold; " +
            "-fx-background-radius: 12; " +
            "-fx-padding: 6 20;"
        );
        btnEdit.setCursor(javafx.scene.Cursor.HAND);

        // 👉 KUNCI 2: LOGIKA INTERAKTIF TOMBOL TOGGLE EDIT/SIMPAN
        btnEdit.setOnAction(event -> {
            if (!isEditMode) {
                // Berubah ke Mode Edit (Membuka Kunci Input)
                isEditMode = true;
                btnEdit.setText("Simpan");
                btnEdit.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-family: 'Poppins'; -fx-font-size: 11px; -fx-font-weight: bold; -fx-background-radius: 12; -fx-padding: 6 25;"); // Ganti Hijau Elegan
                
                txtNama.setEditable(true);
                txtEmail.setEditable(true);
                txtNoTelepon.setEditable(true);
                txtPassword.setEditable(true);
                
                txtNama.requestFocus(); // Letakkan kursor otomatis di kolom nama
            } else {
                // Berubah ke Mode Lihat (Mengunci Kembali Input)
                isEditMode = false;
                btnEdit.setText("Edit profil");
                btnEdit.setStyle("-fx-background-color: #FF9800; -fx-text-fill: white; -fx-font-family: 'Poppins'; -fx-font-size: 11px; -fx-font-weight: bold; -fx-background-radius: 12; -fx-padding: 6 20;"); // Balik Oranye
                
                txtNama.setEditable(false);
                txtEmail.setEditable(false);
                txtNoTelepon.setEditable(false);
                txtPassword.setEditable(false);
                
                // Di sini kamu bisa menambahkan fungsi cetak log atau query simpan data ke database
                System.out.println("💾 Profil Berhasil Diperbarui: " + txtNama.getText());
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
            "-fx-border-width: 0 0 0 4; " +
            "-fx-padding: 0 0 0 10; " +
            "-fx-font-family: 'Poppins'; " +
            "-fx-font-weight: bold; " +
            "-fx-font-size: 15px; " +
            "-fx-text-fill: #1A3C5A;"
        );

        // =====================================================================
        // 4. GRID FORM INPUT FIELD (DUA KOLOM BERAKSI)
        // =====================================================================
        GridPane infoGrid = new GridPane();
        infoGrid.setHgap(30); 
        infoGrid.setVgap(15); 
        infoGrid.setMaxWidth(770);

        // Inisialisasi Input Field secara resmi
        txtNama = new TextField("Rafly aja");
        txtEmail = new TextField("rafly.organizer@gmail.com");
        txtNoTelepon = new TextField("0812-3321-1234");
        txtPassword = new TextField("rafly.organizer@gmail.com"); // Sesuai isian mockup tulisan teks biasa

        // Susun form ke dalam kotak grid dua kolom rapi
        infoGrid.add(createFieldBlock("Nama", txtNama, true), 0, 0);
        infoGrid.add(createFieldBlock("Email", txtEmail, false), 1, 0);
        infoGrid.add(createFieldBlock("No Telepon", txtNoTelepon, false), 0, 1);
        infoGrid.add(createFieldBlock("Password", txtPassword, false), 1, 1);

        infoSectionWrapper.getChildren().addAll(lblInfoTitle, infoGrid);
        view.getChildren().addAll(bannerPane, profileHeaderRow, infoSectionWrapper);
    }

    // --- METHOD HELPER: Membuat Isian Form Berbingkai Abu-Abu dengan TextField Transparan ---
    private VBox createFieldBlock(String labelText, TextField textField, boolean hasEditIcon) {
        VBox block = new VBox(5);
        GridPane.setHgrow(block, Priority.ALWAYS);

        Label lblField = new Label(labelText);
        lblField.setStyle("-fx-font-family: 'Poppins'; -fx-font-size: 11px; -fx-text-fill: #1A3C5A; -fx-font-weight: bold;");

        HBox fieldContainer = new HBox(10);
        fieldContainer.setAlignment(Pos.CENTER_LEFT);
        fieldContainer.setPrefWidth(370); 
        fieldContainer.setStyle(
            "-fx-background-color: #F8F9FA; " + // Abu-abu terang background mockup
            "-fx-border-color: #Cdd5De; " +
            "-fx-border-radius: 10; " +
            "-fx-background-radius: 10; " +
            "-fx-padding: 6 15 6 15;" // Padding disesuaikan agar tidak tebal saat mengetik
        );

        // 👉 KUNCI 3: Hilangkan border & background bawaan asli dari TextField JavaFX
        textField.setStyle(
            "-fx-background-color: transparent; " +
            "-fx-background-insets: 0; " +
            "-fx-background-radius: 0; " +
            "-fx-padding: 4 0 4 0; " +
            "-fx-font-family: 'Poppins'; " +
            "-fx-font-size: 12px; " +
            "-fx-text-fill: #1A3C5A;"
        );
        textField.setEditable(false); // Mode awal dikunci aman
        HBox.setHgrow(textField, Priority.ALWAYS); // Memenuhi sisa HBox secara elastis

        fieldContainer.getChildren().add(textField);

        // Tambahkan ikon pulpen edit jika baris tersebut membutuhkannya
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