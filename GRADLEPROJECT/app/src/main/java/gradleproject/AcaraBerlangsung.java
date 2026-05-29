package gradleproject;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane; // <--- Import ScrollPane
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

public class AcaraBerlangsung {

    private VBox view;

    public AcaraBerlangsung() {
        view = new VBox(15);
        view.setPadding(new Insets(20, 20, 20, 80)); // Jarak presisi sejajar dashboard
        view.setAlignment(Pos.TOP_LEFT);

        // 1. HEADER HALAMAN
        VBox header = new VBox(-5);
        Label lblHi = new Label("Hai, admin.");
        lblHi.getStyleClass().add("greeting-title");
        Label lblSub = new Label("Gimana hari ini . . .");
        lblSub.getStyleClass().add("greeting-subtitle");
        header.getChildren().addAll(lblHi, lblSub);

        // 2. WADAH UTAMA KONTEN (Tab Oranye + Box Biru)
        VBox contentContainer = new VBox(0);
        contentContainer.setMaxWidth(770);
        contentContainer.setPadding(new Insets(15, 0, 0, 0));
        VBox.setVgrow(contentContainer, Priority.ALWAYS); // Memaksa wadah meluas ke bawah

        // Tab Badge Oranye di bagian atas
        Label lblTab = new Label("Acara Berlangsung");
        lblTab.getStyleClass().add("event-orange-tab");

        // Wadah Besar Biru Gelap
        VBox blueBox = new VBox(15);
        blueBox.getStyleClass().add("event-blue-container");
        VBox.setVgrow(blueBox, Priority.ALWAYS); // Background biru mengisi sisa layar

        // Wadah khusus untuk menampung kartu-kartu acara (Agar bisa di-scroll)
        VBox cardContainer = new VBox(15);
        cardContainer.setStyle("-fx-background-color: transparent;");
        cardContainer.setPadding(new Insets(0, 5, 0, 0)); // Sedikit padding kanan untuk area scrollbar

        // Masukkan kartu-kartu acara (Diulang 4 kali agar bisa langsung dicoba scroll-nya)
        for (int i = 0; i < 4; i++) {
            cardContainer.getChildren().addAll(
                createEventCard("/aset/gambarLuminara/img-fest2.png", 
                                "Makassar Traditional Costume Showcase", 
                                "Trans Studio Mall Makassar", 
                                "20-22 Mei 2026 / 19:00 - 22:00"),
                createEventCard("/aset/gambarLuminara/img-fest1.png", 
                                "Makassar Traditional Costume Showcase", 
                                "Trans Studio Mall Makassar", 
                                "20-22 Mei 2026 / 19:00 - 22:00")
            );
        }

        // =====================================================================
        // 3. SCROLL PANE (Di dalam Wadah Biru)
        // =====================================================================
        ScrollPane scrollTable = new ScrollPane(cardContainer);
        scrollTable.setFitToWidth(true);
        scrollTable.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollTable.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        // Style transparan agar background biru dari blueBox tetap terlihat
        scrollTable.setStyle("-fx-background: transparent; -fx-background-color: transparent; -fx-border-color: transparent;");
        
        VBox.setVgrow(scrollTable, Priority.ALWAYS);
        // =====================================================================

        // Masukkan ScrollPane ke dalam blueBox
        blueBox.getChildren().add(scrollTable);

        // Masukkan Tab dan blueBox ke kontainer utama
        contentContainer.getChildren().addAll(lblTab, blueBox);

        // Masukkan ke view utama (Paginasi dihapus)
        view.getChildren().addAll(header, contentContainer);
    }

    private HBox createEventCard(String imagePath, String title, String location, String dateTime) {
        HBox card = new HBox(20);
        card.getStyleClass().add("event-white-card");
        card.setAlignment(Pos.CENTER_LEFT);

        // Foto Acara (Kiri)
        ImageView imgEvent = new ImageView();
        try {
            Image img = new Image(getClass().getResourceAsStream(imagePath));
            imgEvent.setImage(img);
        } catch (Exception e) {
            System.out.println("⚠️ Gambar acara tidak ditemukan, menggunakan placeholder putih.");
        }
        imgEvent.setFitWidth(220);
        imgEvent.setFitHeight(120);
        
        // Trik Potong Sudut Gambar agar melengkung rapi (Radius 15)
        Rectangle clip = new Rectangle(220, 120);
        clip.setArcWidth(15);
        clip.setArcHeight(15);
        imgEvent.setClip(clip);

        // Detail Teks Informasi (Kanan)
        VBox infoBox = new VBox(3);
        infoBox.setAlignment(Pos.CENTER_LEFT);

        // Field 1: Nama Kegiatan
        Label hTitle = new Label("Nama Kegiatan");
        hTitle.getStyleClass().add("event-field-header");
        Label vTitle = new Label(title);
        vTitle.getStyleClass().add("event-field-value");
        vTitle.setWrapText(true);

        // Field 2: Lokasi
        Label hLocation = new Label("Lokasi");
        hLocation.getStyleClass().add("event-field-header");
        Label vLocation = new Label(location);
        vLocation.getStyleClass().add("event-field-value");

        // Field 3: Tanggal / Waktu
        Label hDateTime = new Label("Tanggal / Waktu");
        hDateTime.getStyleClass().add("event-field-header");
        Label vDateTime = new Label(dateTime);
        vDateTime.getStyleClass().add("event-field-value");

        infoBox.getChildren().addAll(hTitle, vTitle, hLocation, vLocation, hDateTime, vDateTime);

        card.getChildren().addAll(imgEvent, infoBox);
        HBox.setHgrow(infoBox, Priority.ALWAYS);
        
        return card;
    }

    public Parent getView() {
        return view;
    }
}