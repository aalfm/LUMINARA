package gradleproject;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;

public class AcaraFestival {
    
    private VBox view;

    public AcaraFestival() {
        view = new VBox(25);
        view.setPadding(new Insets(20, 20, 20, 80)); 
        view.setAlignment(Pos.TOP_LEFT);

        // 1. HEADER
        VBox header = new VBox(-5);
        Label lblHi = new Label("Hai, admin.");
        lblHi.getStyleClass().add("greeting-title");
        Label lblSub = new Label("Pantau pengguna ya . . ."); 
        lblSub.getStyleClass().add("greeting-subtitle");
        header.getChildren().addAll(lblHi, lblSub);

        // 2. JUDUL KATEGORI
        Label lblTitle = new Label("Kategori Festival");
        lblTitle.setStyle("-fx-font-family: 'Poppins'; -fx-font-weight: bold; -fx-font-size: 16px; -fx-text-fill: #1A3C5A;");

        // 3. WADAH TABEL
        VBox tableBox = new VBox(0);
        tableBox.setMaxWidth(770);
        
        tableBox.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 8; -fx-border-color: #D3D9DE; -fx-border-radius: 8;");
        VBox.setVgrow(tableBox, Priority.ALWAYS); 

        // HEADER TABEL
        HBox tableHeader = new HBox();
        tableHeader.setAlignment(Pos.CENTER_LEFT);
        tableHeader.setPadding(new Insets(12, 25, 12, 25)); 
        tableHeader.setStyle("-fx-background-color: #D3D9DE; -fx-background-radius: 8 8 0 0;");

        Label colNama = new Label("Nama");
        colNama.setStyle("-fx-text-fill: #5A7184; -fx-font-weight: bold;");
        colNama.setPrefWidth(400); 

        Label colDetail = new Label("Detail");
        colDetail.setStyle("-fx-text-fill: #5A7184; -fx-font-weight: bold;");
        colDetail.setPrefWidth(150); 
        colDetail.setAlignment(Pos.CENTER); 

        Label colStatus = new Label("Status");
        colStatus.setStyle("-fx-text-fill: #5A7184; -fx-font-weight: bold;");
        colStatus.setPrefWidth(150); 
        colStatus.setAlignment(Pos.CENTER); 

        tableHeader.getChildren().addAll(colNama, colDetail, colStatus);

        // ISI TABEL
        VBox tableBody = new VBox(15);
        tableBody.setPadding(new Insets(20, 25, 20, 25));
        tableBody.setStyle("-fx-background-color: transparent;");

        // Data Dummy (Diulang 4 kali agar tabel bisa di-scroll)
        for (int i = 0; i < 4; i++) {
            tableBody.getChildren().add(createRow("Makassar Traditional Costume Showcase", "Trans Studio Mall Makassar"));
        }

        // =====================================================================
        // 4. SCROLL PANE 
        // =====================================================================
        ScrollPane scrollTable = new ScrollPane(tableBody);
        scrollTable.setFitToWidth(true); 
        scrollTable.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); 
        scrollTable.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); 
        scrollTable.setStyle("-fx-background: transparent; -fx-background-color: transparent; -fx-border-color: transparent; -fx-background-radius: 0 0 8 8;");
        
        VBox.setVgrow(scrollTable, Priority.ALWAYS);
        // =====================================================================

        tableBox.getChildren().addAll(tableHeader, scrollTable);
        view.getChildren().addAll(header, lblTitle, tableBox);
    }

    // Method Helper untuk membuat baris tabel dengan interaksi ubah status
    private HBox createRow(String title, String location) {
        HBox row = new HBox();
        row.setAlignment(Pos.CENTER_LEFT);
        row.setPadding(new Insets(10, 0, 10, 0));

        // Kolom 1: Nama & Lokasi
        VBox nameBox = new VBox(3);
        nameBox.setPrefWidth(400); 
        
        Label lblTitle = new Label(title);
        lblTitle.setStyle("-fx-font-family: 'Poppins'; -fx-font-weight: bold; -fx-text-fill: #1A3C5A; -fx-font-size: 13px;");
        
        Label lblLocation = new Label(location);
        lblLocation.setStyle("-fx-font-family: 'Poppins'; -fx-text-fill: #5A7184; -fx-font-size: 11px;");
        
        nameBox.getChildren().addAll(lblTitle, lblLocation);

        // Kolom 2: Tombol Lihat
        HBox detailBox = new HBox();
        detailBox.setPrefWidth(150);
        detailBox.setAlignment(Pos.CENTER);

        Button btnLihat = new Button("Lihat");
        btnLihat.setStyle(
            "-fx-background-color: #FF9800; " +
            "-fx-text-fill: white; " +
            "-fx-background-radius: 20; " +
            "-fx-padding: 5 15; " +
            "-fx-font-family: 'Poppins'; " +
            "-fx-font-size: 12px; " +
            "-fx-effect: dropshadow(three-pass-box, rgba(255,152,0,0.6), 8, 0, 0, 3);"
        );
        btnLihat.setCursor(javafx.scene.Cursor.HAND);

        btnLihat.setOnAction(event -> {
            if (Dashboard.getInstance() != null) {
                Dashboard.getInstance().pindahKeDetailAcaraFestival();
            }
        });
        
        detailBox.getChildren().add(btnLihat);

        // =======================================================================
        // KOLOM 3: LOGIKA PERUBAHAN STATUS (StackPane)
        // =======================================================================
        StackPane statusContainer = new StackPane();
        statusContainer.setPrefWidth(150);

        // WADAH A: Berisi Tombol Terima & Tolak
        VBox actionButtonsBox = new VBox(6);
        actionButtonsBox.setAlignment(Pos.CENTER);
        
        Button btnTerima = new Button("Terima");
        btnTerima.setStyle(
            "-fx-background-color: #FF9800; " +
            "-fx-text-fill: white; " +
            "-fx-background-radius: 20; " +
            "-fx-padding: 5 15; " +
            "-fx-font-family: 'Poppins'; " +
            "-fx-font-size: 12px; " +
            "-fx-effect: dropshadow(three-pass-box, rgba(255,152,0,0.6), 8, 0, 0, 3);"
        );
        btnTerima.setCursor(javafx.scene.Cursor.HAND);
        btnTerima.setPrefWidth(85); 
        
        Button btnTolak = new Button("Tolak");
        btnTolak.setStyle(
            "-fx-background-color: #FFFFFF; " +
            "-fx-text-fill: #1A3C5A; " + 
            "-fx-background-radius: 20; " +
            "-fx-padding: 5 15; " +
            "-fx-font-family: 'Poppins'; " +
            "-fx-font-size: 12px; " +
            "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.15), 8, 0, 0, 3);"
        );
        btnTolak.setCursor(javafx.scene.Cursor.HAND);
        btnTolak.setPrefWidth(85); 

        actionButtonsBox.getChildren().addAll(btnTerima, btnTolak);

        // WADAH B: Berisi Hasil Status (Titik warna + Teks) -> Awalnya disembunyikan
        HBox resultStatusBox = new HBox(8);
        resultStatusBox.setAlignment(Pos.CENTER);
        
        Circle dotIndicator = new Circle(4);
        Label lblStatusText = new Label();
        lblStatusText.setStyle("-fx-font-family: 'Poppins'; -fx-font-weight: bold; -fx-font-size: 12px; -fx-text-fill: #1A3C5A;");
        
        resultStatusBox.getChildren().addAll(dotIndicator, lblStatusText);
        resultStatusBox.setVisible(false);  // Sembunyikan visualnya
        resultStatusBox.setManaged(false); // Jangan ambil ruang tata letak saat disembunyikan

        // LOGIKA KLIK TOMBOL TERIMA
        btnTerima.setOnAction(e -> {
            // Hilangkan tombol
            actionButtonsBox.setVisible(false);
            actionButtonsBox.setManaged(false);
            
            // Ubah gaya kotak status (Titik hijau, teks Diterima)
            dotIndicator.setStyle("-fx-fill: #4CAF50;"); 
            lblStatusText.setText("Diterima");
            
            // Tampilkan kotak status
            resultStatusBox.setVisible(true);
            resultStatusBox.setManaged(true);
        });

        // LOGIKA KLIK TOMBOL TOLAK
        btnTolak.setOnAction(e -> {
            // Hilangkan tombol
            actionButtonsBox.setVisible(false);
            actionButtonsBox.setManaged(false);
            
            // Ubah gaya kotak status (Titik oranye, teks Ditolak)
            dotIndicator.setStyle("-fx-fill: #FF9800;"); 
            lblStatusText.setText("Ditolak");
            
            // Tampilkan kotak status
            resultStatusBox.setVisible(true);
            resultStatusBox.setManaged(true);
        });

        // Masukkan Wadah A dan Wadah B ke dalam StackPane
        statusContainer.getChildren().addAll(actionButtonsBox, resultStatusBox);
        // =======================================================================

        row.getChildren().addAll(nameBox,
            detailBox, statusContainer);
        return row;
    }

    public Parent getView() {
        return view;
    }
}