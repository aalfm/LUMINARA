package gradleproject;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

public class ProfilUser {

    private VBox view; 
    private TextField txtNama, txtEmail, txtTelepon, txtPassword;
    private Button btnSimpan;

    public ProfilUser(boolean showSaveButton) {
        view = new VBox(0); 
        // 👉 KALIBRASI: Set padding atas menjadi 5px agar spanduk langsung naik pol ke atas layar
        view.setPadding(new Insets(5, 40, 30, 40)); 
        view.setAlignment(Pos.TOP_LEFT);
        view.setStyle("-fx-background-color: #F8F9FA;");

        // 🎯 FIX: TULISAN HEADER "HALO SOBAT LUMINARA" SUDAH DIHAPUS TOTAL DARI KELAS INI 

        VBox profileContentBox = new VBox(20);
        profileContentBox.setStyle("-fx-background-color: transparent;");
        profileContentBox.setMaxWidth(800);

        // 1. STRUKTUR BANNER & AVATAR OVERLAPPING
        StackPane headerLayout = new StackPane();
        headerLayout.setAlignment(Pos.TOP_LEFT);
        headerLayout.setPrefHeight(220); // Disesuaikan tingginya agar pas pasca-geser naik

        ImageView ivBanner = new ImageView();
        try {
            Image imgBanner = new Image(getClass().getResourceAsStream("/aset/gambarLuminara/banner-makassar.png"));
            ivBanner.setImage(imgBanner);
            ivBanner.setFitWidth(800);
            ivBanner.setFitHeight(150);
            
            Rectangle clip = new Rectangle(800, 150);
            clip.setArcWidth(25);
            clip.setArcHeight(25);
            ivBanner.setClip(clip);
        } catch (Exception e) { }

        StackPane avatarCircle = new StackPane();
        avatarCircle.setMaxSize(110, 110);
        avatarCircle.setMinSize(110, 110);
        avatarCircle.setStyle(
            "-fx-background-color: linear-gradient(to bottom, #FFCC80, #FF9800); " +
            "-fx-background-radius: 55; " +
            "-fx-border-color: #FFFFFF; " +
            "-fx-border-radius: 55; " +
            "-fx-border-width: 4; " +
            "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 8, 0, 0, 3);"
        );
        
        Label lblInitial = new Label("V"); 
        lblInitial.setStyle("-fx-font-family: 'Poppins'; -fx-font-weight: bold; -fx-font-size: 42px; -fx-text-fill: #FFFFFF;");
        avatarCircle.getChildren().add(lblInitial);

        StackPane.setAlignment(avatarCircle, Pos.BOTTOM_LEFT);
        StackPane.setMargin(avatarCircle, new Insets(0, 0, 0, 0)); // Rata kiri lurus

        headerLayout.getChildren().addAll(ivBanner, avatarCircle);

        // 2. BLOK DESKRIPSI RINGKAS & PARAMETER DATA
        VBox detailsBox = new VBox(8);
        detailsBox.setPadding(new Insets(0, 0, 10, 0)); 

        Label lblTagline = new Label("Kami hadir, menghubungkan komunitas dan budaya melalui event yang inspiratif.");
        lblTagline.setStyle("-fx-font-family: 'Poppins'; -fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: #0A3B5C;");

        HBox metaRow = new HBox(25);
        metaRow.setAlignment(Pos.CENTER_LEFT);

        Label lblLokasi = new Label("📍  Makassar, Sulawesi Selatan");
        lblLokasi.setStyle("-fx-font-family: 'Poppins'; -fx-font-size: 12px; -fx-text-fill: #5A7184;");

        Label lblSejak = new Label("📅  Sejak November 2025");
        lblSejak.setStyle("-fx-font-family: 'Poppins'; -fx-font-size: 12px; -fx-text-fill: #5A7184;");

        metaRow.getChildren().addAll(lblLokasi, lblSejak);
        detailsBox.getChildren().addAll(lblTagline, metaRow);

        // 3. SEKSYEN FORMULIR INFORMASI ORGANISASI
        VBox sectionForm = new VBox(15);
        
        Label lblSectionTitle = new Label("Informasi Organisasi");
        lblSectionTitle.setStyle(
            "-fx-border-color: transparent transparent transparent #FF9800; " + 
            "-fx-border-width: 0 0 0 4; " +
            "-fx-padding: 2 0 2 12; " +
            "-fx-font-family: 'Poppins'; " +
            "-fx-font-weight: bold; " +
            "-fx-font-size: 16px; " +
            "-fx-text-fill: #0A3B5C;"
        );

        GridPane gridForm = new GridPane();
        gridForm.setHgap(30);
        gridForm.setVgap(15);
        gridForm.setPadding(new Insets(5, 0, 0, 0)); 

        txtNama = createStyledTextField("Rafly aja");
        VBox fieldNama = createFieldBlock("Nama", txtNama, showSaveButton); 
        gridForm.add(fieldNama, 0, 0);

        txtEmail = createStyledTextField("rafly.organizer@gmail.com");
        VBox fieldEmail = createFieldBlock("Email", txtEmail, false);
        gridForm.add(fieldEmail, 1, 0);

        txtTelepon = createStyledTextField("0812-3321-1234");
        VBox fieldTelepon = createFieldBlock("No Telepon", txtTelepon, false);
        gridForm.add(fieldTelepon, 0, 1);

        txtPassword = createStyledTextField("rafly.organizer@gmail.com"); 
        VBox fieldPassword = createFieldBlock("Password", txtPassword, false);
        gridForm.add(fieldPassword, 1, 1);

        GridPane.setHgrow(fieldNama, Priority.ALWAYS);
        GridPane.setHgrow(fieldEmail, Priority.ALWAYS);

        sectionForm.getChildren().addAll(lblSectionTitle, gridForm);

        // Pengkondisian tombol simpan perubahan kustom
        if (showSaveButton) {
            HBox actionRow = new HBox();
            actionRow.setAlignment(Pos.CENTER_RIGHT);
            actionRow.setPadding(new Insets(10, 0, 0, 0));

            btnSimpan = new Button("Simpan Perubahan");
            btnSimpan.setStyle("-fx-background-color: #FF9800; -fx-text-fill: white; -fx-font-family: 'Poppins'; -fx-font-size: 11px; -fx-font-weight: bold; -fx-background-radius: 10; -fx-padding: 6 22;");
            btnSimpan.setCursor(javafx.scene.Cursor.HAND);
            actionRow.getChildren().add(btnSimpan);
            
            sectionForm.getChildren().add(actionRow);
        }

        profileContentBox.getChildren().addAll(headerLayout, detailsBox, sectionForm);

        ScrollPane scrollInner = new ScrollPane(profileContentBox);
        scrollInner.setFitToWidth(true);
        scrollInner.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); 
        scrollInner.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollInner.setStyle("-fx-background: transparent; -fx-background-color: transparent; -fx-border-color: transparent;");
        VBox.setVgrow(scrollInner, Priority.ALWAYS);

        view.getChildren().addAll(scrollInner);
    }

    private VBox createFieldBlock(String labelTitle, TextField inputField, boolean hasEditIcon) {
        VBox block = new VBox(6);
        Label lbl = new Label(labelTitle);
        lbl.setStyle("-fx-font-family: 'Poppins'; -fx-font-weight: bold; -fx-font-size: 13px; -fx-text-fill: #0A3B5C;");

        if (hasEditIcon) {
            StackPane inputStack = new StackPane();
            inputStack.setAlignment(Pos.CENTER_RIGHT);
            
            ImageView ivEdit = new ImageView();
            try {
                ivEdit.setImage(new Image(getClass().getResourceAsStream("/aset/iconLuminara/icon-edit.png"))); 
                ivEdit.setFitWidth(16);
                ivEdit.setFitHeight(16);
                ivEdit.setPreserveRatio(true);
            } catch (Exception e) {}
            
            inputStack.getChildren().addAll(inputField, ivEdit);
            StackPane.setMargin(ivEdit, new Insets(0, 15, 0, 0));
            block.getChildren().addAll(lbl, inputStack);
        } else {
            block.getChildren().addAll(lbl, inputField);
        }
        return block;
    }

    private TextField createStyledTextField(String valueText) {
        TextField tf = new TextField(valueText);
        tf.setPrefHeight(40);
        tf.setStyle(
            "-fx-background-color: #F8F9FA; " +
            "-fx-border-color: #0A3B5C; " + 
            "-fx-border-radius: 10; " +
            "-fx-background-radius: 10; " +
            "-fx-padding: 0 35 0 15; " + 
            "-fx-font-family: 'Poppins'; " +
            "-fx-font-size: 12px; " +
            "-fx-text-fill: #0A3B5C;"
        );
        return tf;
    }

    public Parent getView() {
        return view;
    }
}