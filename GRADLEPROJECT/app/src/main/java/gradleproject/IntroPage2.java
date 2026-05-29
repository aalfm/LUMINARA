package gradleproject;

import java.io.InputStream;
import java.net.URL;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class IntroPage2 {

    public void start(Stage primaryStage) {
        // Main Container Utama - Bersih dari padding luar
        HBox mainRoot = new HBox();
        mainRoot.getStyleClass().add("scene2-root");
        mainRoot.setStyle("-fx-padding: 0px; -fx-margin: 0px; -fx-background-color: #FDFBF7;"); 

        // ==================== Kiri: Banner Gambar Latar Kapal Phinisi ====================
        StackPane leftBanner = new StackPane();
        
        // 🎯 Mempertahankan lebar ideal rujukan Anda (260px)
        leftBanner.setMinWidth(260);
        leftBanner.setPrefWidth(260); 
        leftBanner.setMaxWidth(260);

        HBox.setHgrow(leftBanner, Priority.NEVER); 
        VBox.setVgrow(leftBanner, Priority.ALWAYS); 
        leftBanner.setStyle("-fx-padding: 0px; -fx-background-insets: 0px;");

        // 🎯 FIX UTAMA: Menggunakan background-size: cover agar gambar proporsional (tidak gepeng)
        try {
            String bgPath = getClass().getResource("/aset/gambarLuminara/gambar-bg.png").toExternalForm();
            leftBanner.setStyle(
                "-fx-background-image: url('" + bgPath + "'); " +
                "-fx-background-repeat: no-repeat; " +
                "-fx-background-size: cover; " + // 💡 Mengunci aspek rasio gambar secara otomatis
                "-fx-background-position: center center;"
            );
        } catch (Exception e) {
            leftBanner.setStyle("-fx-background-color: #002B49;"); 
        }

        // =====================================================================
        // KOREKSI MATEMATIKA KLIPING AGAR LENGKUNGAN KANAN MUNCUL & PAS
        // =====================================================================
        Rectangle clipShape = new Rectangle();
        clipShape.setX(-30); // Geser koordinat awal 30px ke kiri luar layar
        clipShape.setY(0);
        
        // Tambahkan tepat 30px (sesuai nilai X) agar batas kanan kliping berhenti PAS di ujung kanan banner
        clipShape.widthProperty().bind(leftBanner.widthProperty().add(30)); 
        clipShape.heightProperty().bind(leftBanner.heightProperty());
        
        // Set diameter lengkungan sudut (Menciptakan efek round corner di sisi kanan)
        clipShape.setArcWidth(60);  
        clipShape.setArcHeight(60); 
        
        leftBanner.setClip(clipShape);
        StackPane.setMargin(leftBanner, Insets.EMPTY);

        // Wadah konten teks dan logo di dalam banner kiri
        VBox leftContent = new VBox(12);
        leftContent.setAlignment(Pos.CENTER);
        leftContent.setPadding(new Insets(20));
        
        ImageView logoLeft = new ImageView();
        InputStream logoStream = getClass().getResourceAsStream("/aset/gambarLuminara/luminara-textWhite.png");
        if (logoStream != null) {
            logoLeft.setImage(new Image(logoStream));
            // Logo disesuaikan aman di angka 180px
            logoLeft.setFitWidth(180); 
            logoLeft.setPreserveRatio(true);
        }

        Label leftTagline = new Label("Cahaya budaya, perjalanan yang menyenangkan.");
        leftTagline.setStyle("-fx-text-fill: #FFA726; -fx-font-family: 'Poppins'; -fx-font-size: 11px; -fx-font-style: italic; -fx-text-alignment: center;");
        leftTagline.setWrapText(true);

        leftContent.getChildren().addAll(logoLeft, leftTagline);
        // 🎯 FIX: Cukup masukkan leftContent saja karena gambar sudah di-handle oleh styling CSS leftBanner
        leftBanner.getChildren().add(leftContent);

        // ==================== Kanan: Area Konten Informasi Utama ====================
        VBox rightContent = new VBox(25);
        rightContent.setPadding(new Insets(45, 50, 35, 45)); 
        rightContent.setAlignment(Pos.TOP_LEFT);
        HBox.setHgrow(rightContent, Priority.ALWAYS);

        // Barisan Atas: Logo Mini Biru & Navigasi Titik
        HBox topHeaderBar = new HBox();
        topHeaderBar.setAlignment(Pos.TOP_LEFT); 
        
        ImageView miniIcon = new ImageView();
        InputStream miniIconStream = getClass().getResourceAsStream("/aset/gambarLuminara/luminara-logo(blue).png");
        if (miniIconStream != null) {
            miniIcon.setImage(new Image(miniIconStream));
            miniIcon.setFitWidth(35);
            miniIcon.setPreserveRatio(true);
        }

        HBox dotsBox = new HBox(8);
        dotsBox.setAlignment(Pos.TOP_RIGHT); 
        HBox.setHgrow(dotsBox, Priority.ALWAYS);
        
        for (int i = 0; i < 3; i++) {
            StackPane dot = new StackPane();
            dot.setPrefSize(10, 10); 
            dot.setMinSize(10, 10);
            if (i == 1) {
                dot.setStyle("-fx-background-color: #002B49; -fx-background-radius: 50%;");
            } else {
                dot.setStyle("-fx-background-color: transparent; -fx-border-color: #002B49; -fx-border-width: 1.5px; -fx-background-radius: 50%;");
            }
            dotsBox.getChildren().add(dot);
        }
        topHeaderBar.getChildren().addAll(miniIcon, dotsBox);

        // Tipografi Judul Besar & Keterangan Subjudul
        VBox titleContainer = new VBox(10);
        Label mainTitle = new Label("Temukan pengalaman\nbudaya yang inspiratif dan\nbermakna.");
        mainTitle.setStyle("-fx-font-family: 'Poppins'; -fx-font-size: 32px; -fx-font-weight: bold; -fx-text-fill: #002B49; -fx-line-spacing: -2px;");
        
        Label subTitle = new Label("Jelajahi event, bangun komunitas, dan lestarikan budaya lokal bersama Luminara.");
        subTitle.setStyle("-fx-font-family: 'Poppins'; -fx-font-size: 13px; -fx-text-fill: #4A5568;");
        subTitle.setWrapText(true);
        titleContainer.getChildren().addAll(mainTitle, subTitle);

        // Kategori Tag Berbentuk Kapsul Premium Menggunakan File Ikon Asli
        HBox tagsBox = new HBox(10);
        tagsBox.getChildren().addAll(
            createTag("Budaya", "/aset/iconLuminara/icon-budaya.png"),
            createTag("Festival", "/aset/iconLuminara/icon-manajemen-acara.png"),
            createTag("Lokakarya", "/aset/iconLuminara/kategori-biru.png"),
            createTag("Musik", "/aset/iconLuminara/icon-manajemen-acara.png")
        );

        // Blok Bagian Review / Testimoni Pengunjung
        VBox testimonialSection = new VBox(12);
        Label testimonialHeading = new Label("Untuk Luminara . . .");
        testimonialHeading.setStyle("-fx-font-family: 'Poppins'; -fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: #FF9412;");

        HBox cardsContainer = new HBox(12);
        cardsContainer.setAlignment(Pos.CENTER_LEFT);
        cardsContainer.getChildren().addAll(
            createCard("Zahwa", "⭐⭐⭐⭐", "Luminara memudahkan aku menemukan event budaya yang sebelumnya jarang aku tahu"),
            createCard("Faiqh", "⭐⭐⭐⭐⭐", "Bukan cuma aplikasi event, tapi tempat menemukan pengalaman baru."),
            createCard("Syarief", "⭐⭐⭐⭐⭐", "Luminara membantu komunitas dan budaya lokal terasa lebih dekat.")
        );
        testimonialSection.getChildren().addAll(testimonialHeading, cardsContainer);

        VBox dynamicSpacer = new VBox();
        VBox.setVgrow(dynamicSpacer, Priority.ALWAYS);

        // Kelompok Tombol Aksi Kendali Utama (Back & Next)
        HBox actionControlBox = new HBox();
        actionControlBox.setAlignment(Pos.CENTER_LEFT);
        
        Button btnBack = new Button("Kembali");
        btnBack.setPrefSize(110, 38);
        btnBack.setStyle("-fx-background-color: white; -fx-text-fill: #4A5568; -fx-border-color: #CBD5E0; -fx-border-radius: 6px; -fx-background-radius: 6px; -fx-cursor: hand; -fx-font-family: 'Poppins'; -fx-font-size: 13px;");

        btnBack.setOnAction(e -> {
            IntroPage intro1 = new IntroPage();
            intro1.start(primaryStage);
        });

        HBox nextRightWrapper = new HBox();
        nextRightWrapper.setAlignment(Pos.CENTER_RIGHT);
        HBox.setHgrow(nextRightWrapper, Priority.ALWAYS);

        Button btnNext = new Button("Lanjut Jelajahi");
        btnNext.setPrefSize(140, 38);
        btnNext.setStyle("-fx-background-color: #FF9800; -fx-text-fill: white; -fx-font-family: 'Poppins'; -fx-font-weight: bold; -fx-background-radius: 6px; -fx-cursor: hand; -fx-font-size: 13px;");
        nextRightWrapper.getChildren().add(btnNext);

        btnNext.setOnAction(e -> {
            IntroPage3 scene3 = new IntroPage3();
            scene3.start(primaryStage);
        });

        actionControlBox.getChildren().addAll(btnBack, nextRightWrapper);

        rightContent.getChildren().addAll(topHeaderBar, titleContainer, tagsBox, testimonialSection, dynamicSpacer, actionControlBox);
        mainRoot.getChildren().addAll(leftBanner, rightContent);

        Scene scene = new Scene(mainRoot, 1024, 720); 
        
        URL cssUrl = getClass().getResource("/style/guest/intro.css");
        if (cssUrl != null) {
            scene.getStylesheets().add(cssUrl.toExternalForm());
        }

        primaryStage.setTitle("Luminara - Eksplorasi");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private HBox createTag(String text, String iconPath) {
        HBox tagCapsule = new HBox(6);
        tagCapsule.setAlignment(Pos.CENTER);
        tagCapsule.setPadding(new Insets(6, 14, 6, 14));
        tagCapsule.setStyle("-fx-background-color: #FFE0B2; -fx-background-radius: 15px;"); 
        
        try {
            Image img = new Image(getClass().getResourceAsStream(iconPath));
            ImageView iconView = new ImageView(img);
            iconView.setFitWidth(14);
            iconView.setFitHeight(14);
            iconView.setPreserveRatio(true);
            tagCapsule.getChildren().add(iconView);
        } catch (Exception e) {}
        
        Label lblText = new Label(text);
        lblText.setStyle("-fx-text-fill: #002B49; -fx-font-family: 'Poppins'; -fx-font-weight: bold; -fx-font-size: 12px;");
        
        tagCapsule.getChildren().add(lblText);
        return tagCapsule;
    }

    private VBox createCard(String name, String stars, String review) {
        VBox cardContainer = new VBox(8);
        cardContainer.setPadding(new Insets(12));
        cardContainer.setPrefSize(165, 140); 
        cardContainer.setStyle("-fx-background-color: #002B49; -fx-background-radius: 10px;");
        HBox.setHgrow(cardContainer, Priority.ALWAYS);

        HBox profileHeader = new HBox(8);
        profileHeader.setAlignment(Pos.CENTER_LEFT);
        
        StackPane avatarCircle = new StackPane();
        avatarCircle.setPrefSize(30, 30);
        avatarCircle.setMinSize(30, 30);
        avatarCircle.setStyle("-fx-background-color: white; -fx-background-radius: 15;"); 
        
        String firstLetter = name.isEmpty() ? "G" : name.substring(0, 1).toUpperCase();
        Label lblInitial = new Label(firstLetter);
        lblInitial.setStyle("-fx-font-family: 'Poppins'; -fx-font-weight: bold; -fx-font-size: 12px; -fx-text-fill: #002B49;");
        avatarCircle.getChildren().add(lblInitial);
        
        VBox identityBox = new VBox(1);
        Label lblName = new Label(name);
        lblName.setStyle("-fx-text-fill: white; -fx-font-family: 'Poppins'; -fx-font-weight: bold; -fx-font-size: 12px;");
        Label lblStars = new Label(stars);
        lblStars.setStyle("-fx-text-fill: #FFB300; -fx-font-size: 8px;");
        identityBox.getChildren().addAll(lblName, lblStars);
        
        profileHeader.getChildren().addAll(avatarCircle, identityBox);

        Label lblReview = new Label(review);
        lblReview.setStyle("-fx-font-family: 'Poppins'; -fx-text-fill: #E2E8F0; -fx-font-size: 10.5px; -fx-line-spacing: 2px;");
        lblReview.setWrapText(true);

        cardContainer.getChildren().addAll(profileHeader, lblReview);
        return cardContainer;
    }
}