package gradleproject;

import java.io.InputStream;

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
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class IntroPage2 {

    public void start(Stage primaryStage) {
        HBox mainRoot = new HBox();
        mainRoot.setStyle("-fx-padding: 0px; -fx-background-color: #FDFBF7;"); 

        // ==================== Kiri: Banner ====================
        StackPane leftBanner = new StackPane();
        leftBanner.setMinWidth(420);
        leftBanner.setPrefWidth(420); 
        leftBanner.setMaxWidth(420);

        // Pasang Background Image
        try {
            String bgPath = getClass().getResource("/aset/gambarLuminara/gambar-bg.png").toExternalForm();
            leftBanner.setStyle(
                "-fx-background-image: url('" + bgPath + "'); " +
                "-fx-background-repeat: no-repeat; " +
                "-fx-background-size: cover; " + 
                "-fx-background-position: center center;"
            );
        } catch (Exception e) {
            leftBanner.setStyle("-fx-background-color: #003A6C;"); 
        }

        // 🎯 FIX: Clipping agar lengkungan hanya di sisi KANAN
        Rectangle clip = new Rectangle();
        clip.widthProperty().bind(leftBanner.widthProperty());
        clip.heightProperty().bind(leftBanner.heightProperty());
        clip.setArcWidth(90);  // Radius lengkungan
        clip.setArcHeight(90);
        // Geser ke kiri sebesar setengah ArcWidth agar sisi kiri tetap kotak
        clip.setX(-45); 
        leftBanner.setClip(clip);

        VBox leftContent = new VBox(15);
        leftContent.setAlignment(Pos.CENTER);
        leftContent.setPadding(new Insets(20));
        
        ImageView logoLeft = new ImageView();
        try {
            InputStream logoStream = getClass().getResourceAsStream("/aset/gambarLuminara/luminara-textWhite.png");
            if (logoStream != null) {
                logoLeft.setImage(new Image(logoStream));
                logoLeft.setFitWidth(220);
                logoLeft.setPreserveRatio(true);
            }
        } catch(Exception e) {}

        Label leftTagline = new Label("Cahaya budaya, perjalanan yang menyenangkan.");
        leftTagline.setStyle("-fx-text-fill: #FFA726; -fx-font-family: 'Poppins'; -fx-font-size: 12px; -fx-font-style: italic; -fx-text-alignment: center;");
        leftTagline.setWrapText(true);

        leftContent.getChildren().addAll(logoLeft, leftTagline);
        leftBanner.getChildren().add(leftContent);

        // ==================== Kanan: Area Konten Utama ====================
        VBox rightContent = new VBox(25);
        rightContent.setPadding(new Insets(45, 50, 35, 45)); 
        rightContent.setAlignment(Pos.TOP_LEFT);
        HBox.setHgrow(rightContent, Priority.ALWAYS);

        // Barisan Atas (Logo Mini + Dots)
        HBox topHeaderBar = new HBox();
        topHeaderBar.setAlignment(Pos.CENTER_LEFT); 
        topHeaderBar.setPadding(new Insets(10, 10, 0, 0));
        
        ImageView miniIcon = new ImageView();
        try {
            InputStream miniIconStream = getClass().getResourceAsStream("/aset/gambarLuminara/luminara-logo(blue).png");
            if (miniIconStream != null) {
                miniIcon.setImage(new Image(miniIconStream));
                miniIcon.setFitWidth(35);
                miniIcon.setFitHeight(35);
                miniIcon.setPreserveRatio(true);
            }
        } catch(Exception e) {}

        // Wrapper Dots agar selalu di kanan
        StackPane dotsWrapper = new StackPane();
        HBox.setHgrow(dotsWrapper, Priority.ALWAYS);
        dotsWrapper.setAlignment(Pos.TOP_RIGHT);
        dotsWrapper.setPadding(new Insets(2, 0, 0, 0)); 

        HBox dotsBox = new HBox(8);
        dotsBox.setAlignment(Pos.CENTER_RIGHT);
        
        Circle dot1 = new Circle(4.5, Color.TRANSPARENT);
        dot1.setStroke(Color.web("#003A6C"));
        dot1.setStrokeWidth(1.5);
        Circle dot2 = new Circle(4.5, Color.web("#003A6C")); 
        Circle dot3 = new Circle(4.5, Color.TRANSPARENT);
        dot3.setStroke(Color.web("#003A6C"));
        dot3.setStrokeWidth(1.5);

        dotsBox.getChildren().addAll(dot1, dot2, dot3);
        dotsWrapper.getChildren().add(dotsBox);
        topHeaderBar.getChildren().addAll(miniIcon, dotsWrapper);

        // Konten Teks & Komponen Lain
        VBox titleContainer = new VBox(15);
        Label mainTitle = new Label("Temukan pengalaman\nbudaya yang inspiratif dan\nbermakna.");
        mainTitle.setStyle("-fx-font-family: 'Poppins'; -fx-font-size: 36px; -fx-font-weight: bold; -fx-text-fill: #003A6C; -fx-line-spacing: -2px;");
        
        Label subTitle = new Label("Jelajahi event, bangun komunitas, dan lestarikan budaya lokal bersama Luminara.");
        subTitle.setStyle("-fx-font-family: 'Poppins'; -fx-font-size: 14px; -fx-text-fill: #003A6C;");
        titleContainer.getChildren().addAll(mainTitle, subTitle);

        HBox tagsBox = new HBox(15);
        tagsBox.getChildren().addAll(
            createTag("Budaya", "/aset/iconLuminara/icon-budaya.png"),
            createTag("Festival", "/aset/iconLuminara/icon-manajemen-acara.png"),
            createTag("Lokakarya", "/aset/iconLuminara/kategori-biru.png"),
            createTag("Musik", "/aset/iconLuminara/icon-manajemen-acara.png")
        );

        VBox testimonialSection = new VBox(15);
        Label testimonialHeading = new Label("Untuk Luminara . . .");
        testimonialHeading.setStyle("-fx-font-family: 'Poppins'; -fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: #FF9412;");

        HBox cardsContainer = new HBox(15);
        cardsContainer.getChildren().addAll(
            createCard("Zahwa", "★★★★", "Luminara memudahkan aku menemukan event budaya yang sebelumnya jarang aku tahu"),
            createCard("Faiqh", "★★★★★", "Bukan cuma aplikasi event, tapi tempat menemukan pengalaman baru."),
            createCard("Syarief", "★★★★★", "Luminara membantu komunitas dan budaya lokal terasa lebih dekat.")
        );
        testimonialSection.getChildren().addAll(testimonialHeading, cardsContainer);

        VBox dynamicSpacer = new VBox();
        VBox.setVgrow(dynamicSpacer, Priority.ALWAYS);

        HBox actionControlBox = new HBox();
        Button btnBack = new Button("Kembali");
        btnBack.setPrefSize(110, 38);
        btnBack.setStyle("-fx-background-color: white; -fx-text-fill: #003A6C; -fx-border-color: #CBD5E0; -fx-border-radius: 6px; -fx-background-radius: 6px; -fx-cursor: hand; -fx-font-family: 'Poppins'; -fx-font-size: 13px;");
        btnBack.setOnAction(e -> new IntroPage().start(primaryStage));

        HBox nextRightWrapper = new HBox();
        nextRightWrapper.setAlignment(Pos.CENTER_RIGHT);
        HBox.setHgrow(nextRightWrapper, Priority.ALWAYS);

        Button btnNext = new Button("Lanjut Jelajahi");
        btnNext.setPrefSize(140, 38);
        btnNext.setStyle("-fx-background-color: #FF9800; -fx-text-fill: white; -fx-font-family: 'Poppins'; -fx-font-weight: bold; -fx-background-radius: 6px; -fx-cursor: hand; -fx-font-size: 13px; -fx-effect: dropshadow(gaussian, rgba(255, 152, 0, 0.4), 8, 0, 0, 3);");
        btnNext.setOnAction(e -> new IntroPage3().start(primaryStage));
        nextRightWrapper.getChildren().add(btnNext);
        actionControlBox.getChildren().addAll(btnBack, nextRightWrapper);

        rightContent.getChildren().addAll(topHeaderBar, titleContainer, tagsBox, testimonialSection, dynamicSpacer, actionControlBox);
        mainRoot.getChildren().addAll(leftBanner, rightContent);

        Scene scene = new Scene(mainRoot, 1280, 650);
        primaryStage.setTitle("Luminara - Eksplorasi");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private HBox createTag(String text, String iconPath) {
        HBox tagCapsule = new HBox(6);
        tagCapsule.setAlignment(Pos.CENTER);
        tagCapsule.setPadding(new Insets(6, 14, 6, 14));
        // Tambahan bayangan tipis pada tag
        tagCapsule.setStyle("-fx-background-color: #FFE0B2; -fx-background-radius: 15px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 4, 0, 0, 2);"); 
        
        try {
            Image img = new Image(getClass().getResourceAsStream(iconPath));
            ImageView iconView = new ImageView(img);
            iconView.setFitWidth(14);
            iconView.setFitHeight(14);
            iconView.setPreserveRatio(true);
            tagCapsule.getChildren().add(iconView);
        } catch (Exception e) {}
        
        Label lblText = new Label(text);
        lblText.setStyle("-fx-text-fill: #003A6C; -fx-font-family: 'Poppins'; -fx-font-weight: bold; -fx-font-size: 12px;");
        
        tagCapsule.getChildren().add(lblText);
        return tagCapsule;
    }

    private VBox createCard(String name, String stars, String review) {
        VBox cardContainer = new VBox(8);
        cardContainer.setPadding(new Insets(12, 15, 12, 15));
        
        // 🎯 PERBAIKAN 4: Menghapus HBox.setHgrow dan menetapkan lebar absolut agar kartu tidak meregang
        cardContainer.setPrefSize(230, 130); 
        cardContainer.setMinSize(230, 130);
        cardContainer.setStyle("-fx-background-color: #003A6C; -fx-background-radius: 10px;");

        HBox profileHeader = new HBox(10);
        profileHeader.setAlignment(Pos.CENTER_LEFT);
        
        // 🎯 PERBAIKAN 5: Avatar bundar putih polos sesuai Gambar 2
        StackPane avatarCircle = new StackPane();
        avatarCircle.setPrefSize(32, 32);
        avatarCircle.setMinSize(32, 32);
        avatarCircle.setStyle("-fx-background-color: white; -fx-background-radius: 16;"); 
        
        VBox identityBox = new VBox(1);
        Label lblName = new Label(name);
        lblName.setStyle("-fx-text-fill: white; -fx-font-family: 'Poppins'; -fx-font-weight: bold; -fx-font-size: 12px;");
        Label lblStars = new Label(stars);
        lblStars.setStyle("-fx-text-fill: #FF9412; -fx-font-size: 9px;");
        identityBox.getChildren().addAll(lblName, lblStars);
        
        profileHeader.getChildren().addAll(avatarCircle, identityBox);

        Label lblReview = new Label(review);
        lblReview.setStyle("-fx-font-family: 'Poppins'; -fx-text-fill: #E2E8F0; -fx-font-size: 11px; -fx-line-spacing: 2px;");
        lblReview.setWrapText(true);

        cardContainer.getChildren().addAll(profileHeader, lblReview);
        return cardContainer;
    }
}