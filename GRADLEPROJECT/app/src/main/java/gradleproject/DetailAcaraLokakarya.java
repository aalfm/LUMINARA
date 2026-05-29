package gradleproject;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

public class DetailAcaraLokakarya {

    private VBox view;

    public DetailAcaraLokakarya() {
        view = new VBox(20);
        view.setPadding(new Insets(20, 20, 20, 80)); 
        view.setAlignment(Pos.TOP_LEFT);

        // 1. HEADER
        VBox header = new VBox(-5);
        Label lblHi = new Label("Hai, admin.");
        lblHi.getStyleClass().add("greeting-title");
        Label lblSub = new Label("Pantau pengguna ya . . ."); // Mengikuti teks di mockup
        lblSub.getStyleClass().add("greeting-subtitle");
        header.getChildren().addAll(lblHi, lblSub);

        // 2. WADAH KONTEN UTAMA
        VBox contentContainer = new VBox(0);
        contentContainer.setMaxWidth(770);
        contentContainer.setPadding(new Insets(10, 0, 0, 0));

        // Tab Oranye di atas
        Label lblTab = new Label("Lokakarya");
        lblTab.setStyle("-fx-background-color: #FFC074; -fx-text-fill: #1A3C5A; -fx-font-family: 'Poppins'; -fx-font-weight: bold; -fx-font-size: 14px; -fx-padding: 8 40; -fx-background-radius: 15 15 0 0;");

        // Bingkai Abu-abu tebal
        VBox grayFrame = new VBox();
        grayFrame.setStyle("-fx-background-color: #D3D9DE; -fx-background-radius: 0 15 15 15;");
        grayFrame.setPadding(new Insets(25));
        VBox.setVgrow(grayFrame, Priority.ALWAYS);

        // Kartu Putih di dalam Bingkai Abu-abu
        VBox whiteCard = new VBox(15);
        whiteCard.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 15; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 5);");

        // --- BAGIAN GAMBAR ---
        ImageView imgEvent = new ImageView();
        try {
            // Sesuaikan rute ini dengan file gambar aslimu nanti
            Image img = new Image(getClass().getResourceAsStream("/aset/gambarLuminara/img-loka-detail.png"));
            imgEvent.setImage(img);
        } catch (Exception e) {
            System.out.println("⚠️ Gambar acara tidak ditemukan.");
        }
        imgEvent.setFitWidth(720);
        imgEvent.setFitHeight(200); 
        
        // Memotong sudut atas gambar agar melengkung rapi
        Rectangle clip = new Rectangle(720, 200);
        clip.setArcWidth(15);
        clip.setArcHeight(15);
        imgEvent.setClip(clip);

        // --- BAGIAN TEKS INFORMASI ---
        VBox textInfoBox = new VBox(15);
        textInfoBox.setPadding(new Insets(10, 25, 25, 25)); 

        Label lblTitle = new Label("Legenda Makassar Storytelling Corner");
        lblTitle.setStyle("-fx-font-family: 'Poppins'; -fx-font-weight: bold; -fx-font-size: 18px; -fx-text-fill: #1A3C5A;");

        Label lblDesc = new Label("Ruang pementasan cerita rakyat dan sejarah Makassar melalui seni tutur lisan yang interaktif. Menggabungkan sinrilik, musik tradisional, dan storytelling modern, kegiatan ini menghadirkan kisah-kisah legenda secara dekat, hangat, dan edukatif bagi generasi muda.");
        lblDesc.setWrapText(true); 
        lblDesc.setStyle("-fx-font-family: 'Poppins'; -fx-font-size: 12px; -fx-text-fill: #1A3C5A; -fx-line-spacing: 0.5em;");

        // --- GRID SPESIFIKASI ---
        GridPane gridInfo = new GridPane();
        gridInfo.setHgap(150); 
        gridInfo.setVgap(15);  

        // Kolom 1
        gridInfo.add(createGridItem("Lokasi:", "Trans Studio Mall Makassar"), 0, 0);
        gridInfo.add(createGridItem("Harga:", "Rp25.000"), 0, 1);
        
        // Kolom 2
        gridInfo.add(createGridItem("Tanggal:", "20-22 Mei 2026"), 1, 0);
        gridInfo.add(createGridItem("Kuota:", "100 orang"), 1, 1);

        textInfoBox.getChildren().addAll(lblTitle, lblDesc, gridInfo);
        
        // Gabungkan elemen ke dalam kartu dan bingkai
        whiteCard.getChildren().addAll(imgEvent, textInfoBox);
        grayFrame.getChildren().add(whiteCard);
        contentContainer.getChildren().addAll(lblTab, grayFrame);

        view.getChildren().addAll(header, contentContainer);
    }

    private VBox createGridItem(String header, String value) {
        VBox box = new VBox(2);
        Label lblHeader = new Label(header);
        lblHeader.setStyle("-fx-font-family: 'Poppins'; -fx-font-weight: bold; -fx-font-size: 12px; -fx-text-fill: #1A3C5A;");
        
        Label lblValue = new Label(value);
        lblValue.setStyle("-fx-font-family: 'Poppins'; -fx-font-size: 11px; -fx-text-fill: #1A3C5A;");
        
        box.getChildren().addAll(lblHeader, lblValue);
        return box;
    }

    public Parent getView() {
        return view;
    }
}