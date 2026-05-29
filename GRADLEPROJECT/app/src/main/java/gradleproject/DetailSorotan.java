package gradleproject;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

public class DetailSorotan {

    // 👉 PERBAIKAN 1: Root utama diganti dari ScrollPane ke VBox agar header tetap sticky di atas
    private VBox view;

    public DetailSorotan() {
        // Kontainer vertikal utama
        view = new VBox(25);
        view.setPadding(new Insets(30, 40, 30, 60)); 
        view.setAlignment(Pos.TOP_LEFT);
        view.setStyle("-fx-background-color: #F8F9FA;");

        // =====================================================================
        // 1. HEADER WELCOME (Mengunci Diam di Atas)
        // =====================================================================
        VBox welcomeHeader = new VBox(2);
        Label lblTitle = new Label("Halo, Sobat Luminara");
        lblTitle.setStyle("-fx-font-family: 'Poppins'; -fx-font-weight: bold; -fx-font-size: 28px; -fx-text-fill: #0A3B5C;");
        Label lblSubtitle = new Label("Siap menjelajahi event budaya di Kota Makassar?");
        lblSubtitle.setStyle("-fx-font-family: 'Poppins'; -fx-font-size: 14px; -fx-text-fill: #5A7184;");
        welcomeHeader.getChildren().addAll(lblTitle, lblSubtitle);

        // =====================================================================
        // 2. SEKSYEN BINGKAI UTAMA
        // =====================================================================
        VBox sectionDetail = new VBox(0);
        sectionDetail.setMaxWidth(800);
        VBox.setVgrow(sectionDetail, Priority.ALWAYS); // Memaksa seksyen mengambil sisa tinggi layar bawah

        HBox tabSorotan = new HBox();
        Label lblTabSorotan = new Label("Sorotan Budaya");
        lblTabSorotan.setStyle("-fx-background-color: #FF9800; -fx-text-fill: white; -fx-font-family: 'Poppins'; -fx-font-weight: bold; -fx-font-size: 13px; -fx-background-radius: 10 10 0 0; -fx-padding: 6 22;");
        tabSorotan.getChildren().add(lblTabSorotan);

        // Wadah Besar Biru Gelap
        VBox boxBlueContainer = new VBox(0);
        boxBlueContainer.setStyle("-fx-background-color: #0A3B5C; -fx-background-radius: 0 15 15 15; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.08), 10, 0, 0, 4);");
        boxBlueContainer.setPadding(new Insets(25));
        VBox.setVgrow(boxBlueContainer, Priority.ALWAYS); // Membuat kotak biru elastis mengikuti tinggi screen

        // =====================================================================
        // 3. KARTU PUTIH INTI DETAIL ARTIKEL
        // =====================================================================
        VBox whiteCard = new VBox(15);
        whiteCard.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 15; -fx-padding: 20;");
        whiteCard.setMaxWidth(Double.MAX_VALUE);

        // A. Banner Gambar Atas
        StackPane imagePane = new StackPane();
        imagePane.setPrefHeight(200);
        imagePane.setStyle("-fx-background-color: #E2E8F0; -fx-background-radius: 12;");
        
        ImageView ivBanner = new ImageView();
        try {
            Image img = new Image(getClass().getResourceAsStream("/aset/gambarLuminara/sorotan-budaya.png"));
            ivBanner.setImage(img);
            ivBanner.setFitWidth(710); 
            ivBanner.setFitHeight(200);
            
            Rectangle clip = new Rectangle(710, 200);
            clip.setArcWidth(25);
            clip.setArcHeight(25);
            ivBanner.setClip(clip);
        } catch (Exception e) {
            System.out.println("⚠️ Gagal memuat gambar utama detail sorotan!");
        }
        imagePane.getChildren().add(ivBanner);

        // B. Judul Artikel/Event Budaya
        Label lblEventTitle = new Label("Makassar Traditional Costume Showcase");
        lblEventTitle.setStyle("-fx-font-family: 'Poppins'; -fx-font-weight: bold; -fx-font-size: 18px; -fx-text-fill: #0A3B5C;");
        lblEventTitle.setPadding(new Insets(5, 0, 0, 0));

        // C. Paragraf Narasi Panjang (Fit to Shape otomatis)
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

        // D. Baris Tombol Aksi
        HBox actionRow = new HBox();
        actionRow.setAlignment(Pos.CENTER_RIGHT);
        actionRow.setPadding(new Insets(20, 0, 0, 0)); 

        Button btnKembali = new Button("Kembali");
        btnKembali.setStyle("-fx-background-color: #FF9800; -fx-text-fill: white; -fx-font-family: 'Poppins'; -fx-font-size: 11px; -fx-font-weight: bold; -fx-background-radius: 12; -fx-padding: 6 25;");
        btnKembali.setCursor(javafx.scene.Cursor.HAND);
        
        btnKembali.setOnAction(event -> {
            if (DashboardUser.getInstance() != null) {
                DashboardUser.getInstance().pindahKeSorotanBudayaPenuh();
            }
        });

        actionRow.getChildren().add(btnKembali);
        whiteCard.getChildren().addAll(imagePane, lblEventTitle, lblDescription, actionRow);

        // 👉 PERBAIKAN 2: ScrollPane hanya membungkus whiteCard di bagian dalam kotak biru
        ScrollPane scrollInner = new ScrollPane(whiteCard);
        scrollInner.setFitToWidth(true);
        scrollInner.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); 
        scrollInner.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollInner.setStyle("-fx-background: transparent; -fx-background-color: transparent; -fx-border-color: transparent;");
        VBox.setVgrow(scrollInner, Priority.ALWAYS);

        // Masukkan struktur komponen ke dalam hirarki layout baru
        boxBlueContainer.getChildren().add(scrollInner);
        sectionDetail.getChildren().addAll(tabSorotan, boxBlueContainer);

        // 👉 PERBAIKAN 3: Komponen utama langsung dimasukkan ke root VBox tanpa ScrollPane luar
        view.getChildren().addAll(welcomeHeader, sectionDetail);
    }

    public Parent getView() {
        return view;
    }
}