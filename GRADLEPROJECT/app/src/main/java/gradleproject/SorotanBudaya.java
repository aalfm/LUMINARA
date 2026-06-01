package gradleproject;

import gradleproject.dao.SorotanDAO;
import gradleproject.models.Sorotan;
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

import java.util.List;

public class SorotanBudaya {

    private VBox view;
    private SorotanDAO sorotanDAO;

    public SorotanBudaya() {
        sorotanDAO = new SorotanDAO(); // Inisialisasi DAO

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

        // 2. SEKSYEN UTAMA KARTU GRID
        VBox sectionSorotan = new VBox(0);
        sectionSorotan.setMaxWidth(800);
        VBox.setVgrow(sectionSorotan, Priority.ALWAYS);

        HBox tabSorotan = new HBox();
        Label lblTabSorotan = new Label("Sorotan Budaya");
        lblTabSorotan.setStyle("-fx-background-color: #FF9800; -fx-text-fill: white; -fx-font-family: 'Poppins'; -fx-font-weight: bold; -fx-font-size: 13px; -fx-background-radius: 10 10 0 0; -fx-padding: 6 22;");
        tabSorotan.getChildren().add(lblTabSorotan);

        // Wadah Biru Gelap Frame Utama
        VBox boxBlueContainer = new VBox(0);
        boxBlueContainer.setStyle("-fx-background-color: #0A3B5C; -fx-background-radius: 0 15 15 15; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.08), 10, 0, 0, 4);");
        boxBlueContainer.setPadding(new Insets(20, 20, 20, 20));
        VBox.setVgrow(boxBlueContainer, Priority.ALWAYS);

        // Grid 2 Kolom Rapi penampung ubin kartu putih
        GridPane cardsGrid = new GridPane();
        cardsGrid.setHgap(20); 
        cardsGrid.setVgap(20); 
        cardsGrid.setStyle("-fx-background-color: transparent;");

        // 🔥 MENGAMBIL DATA DARI DATABASE
        List<Sorotan> daftarSorotan = sorotanDAO.getAllSorotan();

        if (daftarSorotan.isEmpty()) {
            Label lblKosong = new Label("Belum ada artikel sorotan budaya saat ini.");
            lblKosong.setStyle("-fx-text-fill: white; -fx-font-family: 'Poppins'; -fx-font-style: italic;");
            cardsGrid.add(lblKosong, 0, 0);
        } else {
            // Looping data ke dalam Grid 2 Kolom
            for (int i = 0; i < daftarSorotan.size(); i++) {
                Sorotan s = daftarSorotan.get(i);
                
                int kolom = i % 2; // Sisa bagi 2 (Kolom 0 atau 1)
                int baris = i / 2; // Hasil bagi 2 (Baris akan bertambah tiap 2 item)

                cardsGrid.add(createSorotanCard(s), kolom, baris);
            }
        }

        // Konstruksi ScrollPane bagian dalam
        ScrollPane scrollInner = new ScrollPane(cardsGrid);
        scrollInner.setFitToWidth(true);
        scrollInner.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollInner.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollInner.setStyle("-fx-background: transparent; -fx-background-color: transparent; -fx-border-color: transparent;");
        VBox.setVgrow(scrollInner, Priority.ALWAYS);

        boxBlueContainer.getChildren().add(scrollInner);
        sectionSorotan.getChildren().addAll(tabSorotan, boxBlueContainer);

        view.getChildren().addAll(welcomeHeader, sectionSorotan);
    }

    // --- METHOD HELPER: Menerima Model 'Sorotan' secara dinamis ---
    SorotanBudayaView sorotanDetailView; // Simpan referensi untuk akses di tombol detail
    private VBox createSorotanCard(Sorotan sorotan) {
        VBox card = new VBox(10);
        card.setPrefWidth(360); 
        card.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 15; -fx-padding: 15; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.05), 5, 0, 0, 2);");

        // Foto Sampul Atas Kartu
        StackPane imagePane = new StackPane();
        imagePane.setPrefSize(330, 130);
        imagePane.setStyle("-fx-background-color: #E2E8F0; -fx-background-radius: 12;");

        ImageView iv = new ImageView();
        Image img;
        String imgPath = sorotan.getImagePath();

        try {
            if (imgPath != null && imgPath.startsWith("file:")) {
                // Gunakan background loading false agar gambar langsung terarsip di UI
                img = new Image(imgPath, false);
            } 
            else if (imgPath != null && getClass().getResourceAsStream(imgPath) != null) {
                img = new Image(getClass().getResourceAsStream(imgPath));
            } 
            else {
                img = new Image(getClass().getResourceAsStream("/aset/gambarLuminara/sorotan1.png"));
            }
        } catch (Exception e) {
            System.out.println("Error load image: " + e.getMessage());
            img = new Image(getClass().getResourceAsStream("/aset/gambarLuminara/sorotan1.png"));
        }

        iv.setImage(img);
        iv.setFitWidth(330);
        iv.setFitHeight(130); // 🎯 KUNCI 1: WAJIB SET TINGGI AGAR GAMBAR TIDAK MENCIL
        iv.setPreserveRatio(false);

        // Beri efek melengkung pada sudut gambar
        Rectangle clip = new Rectangle(330, 130);
        clip.setArcWidth(20);
        clip.setArcHeight(20);
        iv.setClip(clip);

        // 🎯 KUNCI 2: MASUKKAN IMAGEVIEW KE DALAM STACKPANE!
        imagePane.getChildren().add(iv); 

        // Judul Artikel Budaya
        Label lblTitle = new Label(sorotan.getJudul());
        lblTitle.setStyle("-fx-font-family: 'Poppins'; -fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: #0A3B5C;");
        lblTitle.setWrapText(true);

        // Deskripsi Narasi
        String deskripsi = (sorotan.getDeskripsiSingkat() != null) ? sorotan.getDeskripsiSingkat() : "-";
        Label lblDesc = new Label(deskripsi);
        lblDesc.setStyle("-fx-font-family: 'Poppins'; -fx-font-size: 10px; -fx-text-fill: #5A7184; -fx-line-spacing: 1.5;");
        lblDesc.setWrapText(true);
        lblDesc.setMaxHeight(45);

        // Baris Tombol Aksi Bawah
        HBox bottomRow = new HBox();
        bottomRow.setAlignment(Pos.CENTER_RIGHT);
        bottomRow.setPadding(new Insets(5, 0, 0, 0));

        Button btnDetail = new Button("Lihat Detail");
        btnDetail.setStyle("-fx-background-color: #FF9800; -fx-text-fill: white; -fx-font-family: 'Poppins'; -fx-font-size: 9px; -fx-font-weight: bold; -fx-background-radius: 10; -fx-padding: 4 15;");
        btnDetail.setCursor(javafx.scene.Cursor.HAND);

        btnDetail.setOnAction(event -> {
            if (DashboardUser.getInstance() != null) {
                DashboardUser.getInstance().pindahKeDetailSorotan();
            }
        });

        bottomRow.getChildren().add(btnDetail);
        
        // 🎯 KUNCI 3: Susun komponen ke dalam struktur VBox utama kartu
        card.getChildren().addAll(imagePane, lblTitle, lblDesc, bottomRow);
        return card;
    }
        
    
    public Parent getView() {
        return view;
    }
    
}


