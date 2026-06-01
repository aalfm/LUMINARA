package gradleproject;

import java.io.InputStream;
import java.util.List;

import gradleproject.dao.EventDAO;
import gradleproject.models.Event;

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
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

public class KegiatanUser {

    private VBox view;

    public KegiatanUser() {
        view = new VBox(30);
        view.setPadding(new Insets(30, 40, 30, 60)); 
        view.setAlignment(Pos.TOP_LEFT);
        view.setStyle("-fx-background-color: #F8F9FA;");

        // =====================================================================
        // 1. HEADER WELCOME
        // =====================================================================
        VBox welcomeHeader = new VBox(2);
        Label lblTitle = new Label("Halo, Sobat Luminara");
        lblTitle.setStyle("-fx-font-family: 'Poppins'; -fx-font-weight: bold; -fx-font-size: 28px; -fx-text-fill: #0A3B5C;");
        Label lblSubtitle = new Label("Siap menjelajahi event budaya di Kota Makassar?");
        lblSubtitle.setStyle("-fx-font-family: 'Poppins'; -fx-font-size: 14px; -fx-text-fill: #5A7184;");
        welcomeHeader.getChildren().addAll(lblTitle, lblSubtitle);

        // =====================================================================
        // 2. SEKSYEN UTAMA KEGIATAN
        // =====================================================================
        VBox sectionKegiatan = new VBox(12);
        sectionKegiatan.setMaxWidth(800);
        VBox.setVgrow(sectionKegiatan, Priority.ALWAYS);

        Label lblTabKegiatan = new Label("Kegiatan");
        lblTabKegiatan.setStyle(
            "-fx-border-color: transparent transparent transparent #FF9800; " +
            "-fx-border-width: 0 0 0 4; " +
            "-fx-padding: 2 0 2 12; " +
            "-fx-font-family: 'Poppins'; " +
            "-fx-font-weight: bold; " +
            "-fx-font-size: 16px; " +
            "-fx-text-fill: #0A3B5C;"
        );

        VBox boxBlueContainer = new VBox(0);
        boxBlueContainer.setStyle("-fx-background-color: #0A3B5C; -fx-background-radius: 15; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.08), 10, 0, 0, 4);");
        boxBlueContainer.setPadding(new Insets(20, 20, 20, 20));
        VBox.setVgrow(boxBlueContainer, Priority.ALWAYS);

        GridPane cardsGrid = new GridPane();
        cardsGrid.setHgap(20);
        cardsGrid.setVgap(20);
        cardsGrid.setStyle("-fx-background-color: transparent;");

        // =====================================================================
        // 🎯 LOGIKA MENGAMBIL DATA DARI DATABASE
        // =====================================================================
        EventDAO eventDAO = new EventDAO();
        List<Event> daftarAcara = eventDAO.findByStatus("Active"); 

        if (daftarAcara != null && !daftarAcara.isEmpty()) {
            int kolom = 0;
            int baris = 0;

            for (Event acara : daftarAcara) {
                VBox eventCard = createEventCard(acara);
                cardsGrid.add(eventCard, kolom, baris);

                kolom++;
                if (kolom == 2) { 
                    kolom = 0;
                    baris++;
                }
            }
        } else {
            Label lblKosong = new Label("Belum ada kegiatan yang tersedia saat ini.");
            lblKosong.setStyle("-fx-font-family: 'Poppins'; -fx-text-fill: #A0A9B5; -fx-font-style: italic;");
            cardsGrid.add(lblKosong, 0, 0);
        }

        // =====================================================================
        // SCROLL & RENDER
        // =====================================================================
        ScrollPane scrollInner = new ScrollPane(cardsGrid);
        scrollInner.setFitToWidth(true);
        scrollInner.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED); 
        scrollInner.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollInner.setStyle("-fx-background: transparent; -fx-background-color: transparent; -fx-border-color: transparent;");
        VBox.setVgrow(scrollInner, Priority.ALWAYS);

        boxBlueContainer.getChildren().add(scrollInner);
        sectionKegiatan.getChildren().addAll(lblTabKegiatan, boxBlueContainer);
        view.getChildren().addAll(welcomeHeader, sectionKegiatan);
    }

    // --- METHOD HELPER: Membuat Cetakan Desain Kartu Putih Per Kegiatan ---
    private VBox createEventCard(Event acara) {
        VBox card = new VBox(10);
        card.setPrefWidth(360);
        card.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 15; -fx-padding: 15; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.05), 5, 0, 0, 2);");

        StackPane imagePane = new StackPane();
        imagePane.setPrefSize(330, 130);
        imagePane.setStyle("-fx-background-color: #E2E8F0; -fx-background-radius: 12;");

        ImageView iv = new ImageView();
        
        // 🎯 FIX: Penanganan Gambar yang Jauh Lebih Aman
        try {
            // Catatan: Pastikan method di Event.java kamu bernama getImagePath() atau getImageUrl()
            String imagePath = acara.getImagePath(); 

            if (imagePath != null && !imagePath.trim().isEmpty()) {
                if (imagePath.startsWith("C:") || imagePath.startsWith("/") || imagePath.contains(":\\")) {
                    iv.setImage(new Image(new java.io.File(imagePath).toURI().toString()));
                } else {
                    // Pengecekan agar tidak NullPointerException
                    InputStream stream = getClass().getResourceAsStream(imagePath);
                    if (stream != null) {
                        iv.setImage(new Image(stream));
                    } else {
                        throw new Exception("Gambar tidak ditemukan di resource");
                    }
                }
            } else {
                throw new Exception("Path kosong");
            }
            
            iv.setFitWidth(330);
            iv.setFitHeight(130);

            Rectangle clip = new Rectangle(330, 130);
            clip.setArcWidth(20);
            clip.setArcHeight(20);
            iv.setClip(clip);
            imagePane.getChildren().add(0, iv);

        } catch (Exception e) {
            // Jika gambar gagal dimuat, tampilkan teks placeholder
            Label lblPlaceholder = new Label("🖼️ Gambar Event");
            lblPlaceholder.setStyle("-fx-text-fill: #A0A9B5; -fx-font-family: 'Poppins'; -fx-font-weight: bold;");
            imagePane.getChildren().add(lblPlaceholder);
        }

        Label lblTitle = new Label(acara.getTitle() != null ? acara.getTitle() : "Acara Luminara");
        lblTitle.setStyle("-fx-font-family: 'Poppins'; -fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: #0A3B5C;");
        lblTitle.setWrapText(true);

        Label lblDesc = new Label(acara.getDescription() != null ? acara.getDescription() : "");
        lblDesc.setStyle("-fx-font-family: 'Poppins'; -fx-font-size: 10px; -fx-text-fill: #5A7184; -fx-line-spacing: 1.5;");
        lblDesc.setWrapText(true);
        lblDesc.setMaxHeight(45);

        HBox bottomRow = new HBox(8);
        bottomRow.setAlignment(Pos.CENTER_LEFT);

        VBox labelBox = new VBox(0);

        // 🎯 FIX: Logika tampilan teks Gratis/Berbayar
        String rawTicket = acara.getTicketType();
        String displayTipeTiket = (rawTicket != null && rawTicket.equalsIgnoreCase("Free")) ? "Gratis" : "Berbayar";

        Label lblPrice = new Label(displayTipeTiket);
        lblPrice.setStyle(
            "-fx-font-family: 'Poppins';" +
            "-fx-font-weight: bold;" +
            "-fx-font-size: 11px;" +
            "-fx-text-fill: " + (displayTipeTiket.equals("Gratis") ? "#FF9800;" : "#E53935;")
        );

        Label lblCat = new Label(acara.getCategory() != null ? acara.getCategory() : "Umum");
        lblCat.setStyle("-fx-font-family: 'Poppins'; -fx-font-size: 11px; -fx-text-fill: #A0A9B5;");

        labelBox.getChildren().addAll(lblPrice, lblCat);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button btnBeli = new Button("Beli Tiket");
        btnBeli.setStyle("-fx-background-color: #FF9800; -fx-text-fill: white; -fx-font-family: 'Poppins'; -fx-font-size: 9px; -fx-font-weight: bold; -fx-background-radius: 10; -fx-padding: 4 12;");
        btnBeli.setCursor(javafx.scene.Cursor.HAND);
        btnBeli.setOnAction(event -> {
            if (DashboardUser.getInstance() != null) DashboardUser.getInstance().pindahKePesanTiket();
        });

        Button btnDetail = new Button("Lihat Detail");
        btnDetail.setStyle("-fx-background-color: #FF9800; -fx-text-fill: white; -fx-font-family: 'Poppins'; -fx-font-size: 9px; -fx-font-weight: bold; -fx-background-radius: 10; -fx-padding: 4 12;");
        btnDetail.setCursor(javafx.scene.Cursor.HAND);
        btnDetail.setOnAction(event -> {
            if (DashboardUser.getInstance() != null) {
                DashboardUser.getInstance().pindahKeDetailRekomendasi(acara);
            }
        });

        bottomRow.getChildren().addAll(labelBox, spacer, btnBeli, btnDetail);
        card.getChildren().addAll(imagePane, lblTitle, lblDesc, bottomRow);
        return card;
    }

    public Parent getView() {
        return view;
    }
}