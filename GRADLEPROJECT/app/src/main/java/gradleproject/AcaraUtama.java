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
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class AcaraUtama {

    private ScrollPane view;

    public AcaraUtama() {
        VBox rootBox = new VBox(20);
        rootBox.setPadding(new Insets(20, 20, 20, 80)); 
        rootBox.setAlignment(Pos.TOP_LEFT);

        // 1. HEADER HALAMAN
        VBox header = new VBox(-5);
        Label lblHi = new Label("Hai, admin.");
        lblHi.getStyleClass().add("greeting-title");
        Label lblSub = new Label("Pantau dan kelola acara Luminara ya . . .");
        lblSub.getStyleClass().add("greeting-subtitle");
        header.getChildren().addAll(lblHi, lblSub);

        // 2. WADAH DAFTAR KARTU ACARA
        VBox cardsContainer = new VBox(20);
        cardsContainer.setAlignment(Pos.TOP_LEFT);
        cardsContainer.setMaxWidth(770);

        // Membuat 4 Kartu Besar sesuai kategori yang diminta
        HBox cardFestival = createEventCard("/aset/iconLuminara/icon-fest.png", "Festival", "12");
        HBox cardLokakarya = createEventCard("/aset/iconLuminara/icon-workshop.png", "Lokakarya", "5");
        HBox cardBudaya = createEventCard("/aset/iconLuminara/icon-budaya.png", "Budaya", "3");
        HBox cardMusik = createEventCard("/aset/iconLuminara/icon-musik.png", "Musik", "8");

        cardsContainer.getChildren().addAll(cardFestival, cardLokakarya, cardBudaya, cardMusik);

        rootBox.getChildren().addAll(header, cardsContainer);

        // 3. BUNGKUS DENGAN SCROLLPANE (Agar fleksibel jika layar kecil)
        view = new ScrollPane(rootBox);
        view.setFitToWidth(true);
        view.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        view.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        view.setStyle("-fx-background-color: transparent; -fx-background: transparent; -fx-border-color: transparent;");
    }

    // Method helper untuk membuat komponen Kartu Acara secara dinamis
// Method helper untuk membuat komponen Kartu Acara secara dinamis
    private HBox createEventCard(String iconPath, String title, String count) {
        HBox card = new HBox(25);
        card.getStyleClass().add("event-card");
        card.setAlignment(Pos.CENTER_LEFT);
        card.setPrefWidth(770);
        card.setMaxWidth(770);

        // 1. Bagian Kiri: Kotak Ikon Kontras
        VBox iconBox = new VBox();
        iconBox.getStyleClass().add("event-icon-box");
        iconBox.setAlignment(Pos.CENTER);
        
        ImageView iv = new ImageView();
        try {
            Image img = new Image(getClass().getResourceAsStream(iconPath));
            iv.setImage(img);
            iv.setFitWidth(35);
            iv.setFitHeight(35);
            iv.setPreserveRatio(true);
        } catch (Exception e) {
            System.out.println("⚠️ Gagal memuat ikon kategori: " + title);
        }
        iconBox.getChildren().add(iv);

        // 2. Bagian Tengah: Judul Kategori Acara
        Label lblTitle = new Label(title.toUpperCase());
        lblTitle.getStyleClass().add("event-category-text");
        lblTitle.setPrefWidth(200); // Agar garis sejajar rata untuk semua kartu

        // 3. Garis Pembatas Putus-putus (Dashed)
        Region divider = new Region();
        divider.getStyleClass().add("event-divider");
        divider.setPrefHeight(55);
        divider.setMinHeight(55);
        divider.setMaxHeight(55);

        // 4. SPACER KIRI: Mendorong angka menjauh dari garis putus-putus
        Region leftSpacer = new Region();
        HBox.setHgrow(leftSpacer, Priority.ALWAYS); 

        // 5. Bagian Angka: Akan otomatis berada di tengah berkat dua spacer
        Label lblCount = new Label(count);
        lblCount.getStyleClass().add("event-count-text");

        // 6. SPACER KANAN: Mendorong tombol ke ujung, menahan angka di tengah
        Region rightSpacer = new Region();
        HBox.setHgrow(rightSpacer, Priority.ALWAYS);
        
        // 6. Bagian Kanan Bawah: Wadah khusus Tombol Detail
        VBox rightBox = new VBox();
        rightBox.setAlignment(Pos.BOTTOM_RIGHT);
        VBox.setVgrow(rightBox, Priority.ALWAYS); // Memaksa wadah mengisi tinggi maksimal
        
        Button btnDetail = new Button("Detail");
        btnDetail.setStyle(
            "-fx-background-color: #FF9800; " +
            "-fx-text-fill: white; " +
            "-fx-background-radius: 20; " +
            "-fx-padding: 5 15; " +
            "-fx-font-family: 'Poppins'; " +
            "-fx-font-size: 12px; " +
            "-fx-effect: dropshadow(three-pass-box, rgba(255,152,0,0.6), 8, 0, 0, 3);"
        );
        
        btnDetail.setOnAction(event -> {
            System.out.println("➤ Menuju detail sub-kategori: " + title);
            
            if (Dashboard.getInstance() != null) {
                // Jika judul kartunya "Festival", panggil jembatan Festival
                if (title.equalsIgnoreCase("Festival")) {
                    Dashboard.getInstance().pindahKeAcaraFestival();
                } else if (title.equalsIgnoreCase("Lokakarya")) { 
                    Dashboard.getInstance().pindahKeAcaraLokakarya();
                } else if (title.equalsIgnoreCase("Musik")) {
                    Dashboard.getInstance().pindahKeAcaraMusik();
                } else if (title.equalsIgnoreCase("Budaya")) { // <--- TAMBAHKAN INI
                    Dashboard.getInstance().pindahKeAcaraBudaya();
                }
                // Nanti kamu bisa tambahkan else if (title.equalsIgnoreCase("Lokakarya")) dst..
            }
        });

        rightBox.getChildren().add(btnDetail);

        // =================================================================
        // SUSUNAN FINAL:
        // Ikon -> Judul -> Garis -> [ SPACER ] -> Angka -> Tombol
        // =================================================================
        card.getChildren().clear(); 
        card.getChildren().addAll(iconBox, lblTitle, divider, leftSpacer, lblCount, rightSpacer, rightBox);
        card.setCursor(javafx.scene.Cursor.HAND);
        
        return card;
    }

    public Parent getView() {
        return view;
    }
}