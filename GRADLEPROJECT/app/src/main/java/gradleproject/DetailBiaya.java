package gradleproject;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

public class DetailBiaya {

    private VBox view;

    public DetailBiaya(String statusBiaya) {
        view = new VBox(25);
        view.setPadding(new Insets(30, 40, 30, 60)); 
        view.setAlignment(Pos.TOP_LEFT);
        view.setStyle("-fx-background-color: #F8F9FA;");

        // 1. HEADER WELCOME
        VBox welcomeHeader = new VBox(2);
        Label lblTitle = new Label("Halo, Sobat Luminara");
        lblTitle.setStyle("-fx-font-family: 'Poppins'; -fx-font-weight: bold; -fx-font-size: 28px; -fx-text-fill: #0A3B5C;");
        Label lblSubtitle = new Label("Siap menjelajahi event budaya di Kota Makassar?");
        lblSubtitle.setStyle("-fx-font-family: 'Poppins'; -fx-font-size: 14px; -fx-text-fill: #5A7184;");
        welcomeHeader.getChildren().addAll(lblTitle, lblSubtitle);

        // 2. SEKSYEN BINGKAI UTAMA
        VBox sectionDetail = new VBox(0); 
        sectionDetail.setMaxWidth(800);
        VBox.setVgrow(sectionDetail, Priority.ALWAYS);

        // Kapsul Tab Menempel Atas (Dinamis: Berbayar / Gratis)
        HBox tabCost = new HBox();
        Label lblTabCost = new Label(statusBiaya);
        lblTabCost.setStyle("-fx-background-color: #FF9800; -fx-text-fill: white; -fx-font-family: 'Poppins'; -fx-font-weight: bold; -fx-font-size: 13px; -fx-background-radius: 10 10 0 0; -fx-padding: 6 22;");
        tabCost.getChildren().add(lblTabCost);

        // Wadah Besar Biru Gelap
        VBox boxBlueContainer = new VBox(0);
        boxBlueContainer.setStyle("-fx-background-color: #0A3B5C; -fx-background-radius: 0 15 15 15; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.08), 10, 0, 0, 4);");
        boxBlueContainer.setPadding(new Insets(25));
        VBox.setVgrow(boxBlueContainer, Priority.ALWAYS);

        // 3. KARTU PUTIH INTI DETAIL EVENT
        VBox whiteCard = new VBox(15);
        whiteCard.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 15; -fx-padding: 20;");
        whiteCard.setMaxWidth(Double.MAX_VALUE);

        StackPane imagePane = new StackPane();
        imagePane.setPrefHeight(200);
        imagePane.setStyle("-fx-background-color: #E2E8F0; -fx-background-radius: 12;");
        
        ImageView ivBanner = new ImageView();
        try {
            Image img = new Image(getClass().getResourceAsStream("/aset/gambarLuminara/rekomendasi-kegiatan.png"));
            ivBanner.setImage(img);
            ivBanner.setFitWidth(710); 
            ivBanner.setFitHeight(200);
            Rectangle clip = new Rectangle(710, 200);
            clip.setArcWidth(25);
            clip.setArcHeight(25);
            ivBanner.setClip(clip);
        } catch (Exception e) { }
        imagePane.getChildren().add(ivBanner);

        Label lblEventTitle = new Label("Makassar Traditional Costume Showcase");
        lblEventTitle.setStyle("-fx-font-family: 'Poppins'; -fx-font-weight: bold; -fx-font-size: 18px; -fx-text-fill: #0A3B5C;");

        String deskripsiTeks = "Pementasan budaya yang menampilkan keindahan, filosofi, dan identitas masyarakat "
                + "Makassar melalui pakaian adat tradisional Sulawesi Selatan. Acara ini berfokus pada "
                + "visualisasi busana, kain sutra, dan aksesoris tradisional yang merepresentasikan nilai "
                + "budaya, status sosial, serta kehormatan masyarakat lokal. Busana yang ditampilkan "
                + "meliputi Baju Bodo untuk wanita, Baju Bella Dada untuk pria, serta Passapu sebagai penutup "
                + "kepala khas Makassar. Showcase ini juga menampilkan keindahan kain sutra Lipa' Sabbe "
                + "dan perhiasan emas tradisional yang memperkuat estetika budaya Makassar. Dalam "
                + "konsep modern, pementasan dikemas dalam bentuk parade atau pertunjukan teatrikal "
                + "dengan iringan musik tradisional dan akustik, sehingga menghadirkan pengalaman "
                + "budaya yang elegan, interaktif, dan penuh makna bagi penonton.";
        
        Label lblDescription = new Label(deskripsiTeks);
        lblDescription.setStyle("-fx-font-family: 'Poppins'; -fx-font-size: 11px; -fx-text-fill: #0A3B5C; -fx-line-spacing: 1.6;");
        lblDescription.setWrapText(true);
        lblDescription.setTextAlignment(javafx.scene.text.TextAlignment.JUSTIFY);

        // 👉 CEK STATUS GRATIS ATAU BERBAYAR
        boolean isGratis = statusBiaya != null && (statusBiaya.equalsIgnoreCase("Gratis") || statusBiaya.equalsIgnoreCase("Free"));
        String labelHargaUI = isGratis ? "Gratis" : "Rp25.000";
        String nilaiHargaUntukSistem = isGratis ? "0" : "25000";

        GridPane gridInfo = new GridPane();
        gridInfo.setHgap(80); 
        gridInfo.setVgap(12); 
        gridInfo.setPadding(new Insets(10, 0, 10, 0));

        gridInfo.add(createMetaBlock("Lokasi:", "Trans Studio Mall Makassar"), 0, 0);
        gridInfo.add(createMetaBlock("Tanggal:", "20-22 Mei 2026"), 1, 0);
        gridInfo.add(createMetaBlock("Harga:", labelHargaUI), 0, 1); // Nominal harga di UI menjadi dinamis
        gridInfo.add(createMetaBlock("Kuota:", "100 orang"), 1, 1);

        HBox actionRow = new HBox(12);
        actionRow.setAlignment(Pos.CENTER_RIGHT);

        Button btnBeli = new Button("Beli Tiket");
        btnBeli.setStyle("-fx-background-color: #FF9800; -fx-text-fill: white; -fx-font-family: 'Poppins'; -fx-font-size: 11px; -fx-font-weight: bold; -fx-background-radius: 12; -fx-padding: 6 22;");
        btnBeli.setCursor(javafx.scene.Cursor.HAND);
        
        // 👉 PERBAIKAN: Mengirim nominal harga ke formulir pesanan
        btnBeli.setOnAction(event -> {
            if (DashboardUser.getInstance() != null) {
                DashboardUser.getInstance().pindahKePesanTiket(nilaiHargaUntukSistem);
            }
        });

        Button btnKembali = new Button("Kembali");
        btnKembali.setStyle("-fx-background-color: #FF9800; -fx-text-fill: white; -fx-font-family: 'Poppins'; -fx-font-size: 11px; -fx-font-weight: bold; -fx-background-radius: 12; -fx-padding: 6 22;");
        btnKembali.setCursor(javafx.scene.Cursor.HAND);
        
        btnKembali.setOnAction(event -> {
            if (DashboardUser.getInstance() != null) {
                DashboardUser.getInstance().pindahKeBiayaUser();
            }
        });

        actionRow.getChildren().addAll(btnBeli, btnKembali);
        whiteCard.getChildren().addAll(imagePane, lblEventTitle, lblDescription, gridInfo, actionRow);

        ScrollPane scrollInner = new ScrollPane(whiteCard);
        scrollInner.setFitToWidth(true);
        scrollInner.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); 
        scrollInner.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollInner.setStyle("-fx-background: transparent; -fx-background-color: transparent; -fx-border-color: transparent;");
        VBox.setVgrow(scrollInner, Priority.ALWAYS);

        boxBlueContainer.getChildren().add(scrollInner);
        sectionDetail.getChildren().addAll(tabCost, boxBlueContainer);

        view.getChildren().addAll(welcomeHeader, sectionDetail);
    }

    private VBox createMetaBlock(String title, String value) {
        VBox block = new VBox(2);
        Label lblTitle = new Label(title);
        lblTitle.setStyle("-fx-font-family: 'Poppins'; -fx-font-weight: bold; -fx-font-size: 13px; -fx-text-fill: #0A3B5C;");
        Label lblValue = new Label(value);
        lblValue.setStyle("-fx-font-family: 'Poppins'; -fx-font-size: 11px; -fx-text-fill: #0A3B5C;");
        block.getChildren().addAll(lblTitle, lblValue);
        return block;
    }

    public Parent getView() {
        return view;
    }
}