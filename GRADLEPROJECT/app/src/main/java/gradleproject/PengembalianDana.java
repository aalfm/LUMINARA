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

public class PengembalianDana {

    private VBox view;

    public PengembalianDana() {
        view = new VBox(20);
        view.setPadding(new Insets(20, 20, 20, 80)); 
        view.setAlignment(Pos.TOP_LEFT);

        // 1. HEADER HALAMAN
        VBox header = new VBox(-5);
        Label lblHi = new Label("Hai, admin.");
        lblHi.getStyleClass().add("greeting-title");
        Label lblSub = new Label("Pantau setiap transaksi ya . . .");
        lblSub.getStyleClass().add("greeting-subtitle");
        header.getChildren().addAll(lblHi, lblSub);

        // 2. WADAH TABEL UTAMA KONTEN
        VBox tableBox = new VBox(0);
        tableBox.setMaxWidth(800);
        VBox.setVgrow(tableBox, Priority.ALWAYS);

        // A. Header Tabel (Abu-abu) - Menyesuaikan Kolom di Mockup
        HBox tableHeader = new HBox();
        tableHeader.setStyle("-fx-background-color: #D3D9DE; -fx-background-radius: 15 15 0 0;"); 
        tableHeader.setAlignment(Pos.CENTER_LEFT);
        tableHeader.setPadding(new Insets(12, 25, 12, 25)); 

        Label colNama = new Label("Nama Acara");
        colNama.setStyle("-fx-text-fill: #1A3C5A; -fx-font-weight: bold; -fx-font-family: 'Poppins';");
        colNama.setPrefWidth(220); 

        Label colUser = new Label("User");
        colUser.setStyle("-fx-text-fill: #1A3C5A; -fx-font-weight: bold; -fx-font-family: 'Poppins';");
        colUser.setPrefWidth(180); 

        Label colTotal = new Label("Total");
        colTotal.setStyle("-fx-text-fill: #1A3C5A; -fx-font-weight: bold; -fx-font-family: 'Poppins';");
        colTotal.setPrefWidth(150); 

        Label colKet = new Label("Keterangan");
        colKet.setStyle("-fx-text-fill: #1A3C5A; -fx-font-weight: bold; -fx-font-family: 'Poppins';");
        colKet.setPrefWidth(120); 
        colKet.setAlignment(Pos.CENTER);

        tableHeader.getChildren().addAll(colNama, colUser, colTotal, colKet);

        // B. Body Tabel (Wadah Biru Gelap)
        VBox blueBox = new VBox(15);
        blueBox.setStyle("-fx-background-color: #0A3B5C; -fx-background-radius: 0 0 15 15;");
        blueBox.setPadding(new Insets(20, 25, 20, 25));
        VBox.setVgrow(blueBox, Priority.ALWAYS);

        VBox listContainer = new VBox(12);
        listContainer.setStyle("-fx-background-color: transparent;");

        // Memasukkan data pengembalian dummy (Diulang agar mengaktifkan efek scroll)
        for (int i = 0; i < 4; i++) {
            listContainer.getChildren().addAll(
                createRefundRow("Makassar Traditional\nCostume Showcase", "Ra-Fly Organizer", "Rp7.550.000"),
                createRefundRow("Makassar Traditional\nCostume Showcase", "Ra-Fly Organizer", "Rp7.550.000")
            );
        }

        // C. Konstruksi ScrollPane Transparan
        ScrollPane scrollTable = new ScrollPane(listContainer);
        scrollTable.setFitToWidth(true); 
        scrollTable.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); 
        scrollTable.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); 
        scrollTable.setStyle("-fx-background: transparent; -fx-background-color: transparent; -fx-border-color: transparent;");
        VBox.setVgrow(scrollTable, Priority.ALWAYS);

        blueBox.getChildren().add(scrollTable);
        tableBox.getChildren().addAll(tableHeader, blueBox);

        view.getChildren().addAll(header, tableBox);
    }

    private HBox createRefundRow(String eventName, String user, String total) {
        HBox row = new HBox();
        row.setAlignment(Pos.CENTER_LEFT);
        row.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 10; -fx-padding: 10 15 10 15;");
        
        Label lblName = new Label(eventName);
        lblName.setStyle("-fx-text-fill: #1A3C5A; -fx-font-weight: bold; -fx-font-family: 'Poppins'; -fx-font-size: 12px;");
        lblName.setPrefWidth(205); 

        Label lblUser = new Label(user);
        lblUser.setStyle("-fx-text-fill: #5A7184; -fx-font-family: 'Poppins'; -fx-font-size: 12px;");
        lblUser.setPrefWidth(180);

        Label lblTotal = new Label(total);
        lblTotal.setStyle("-fx-text-fill: #5A7184; -fx-font-family: 'Poppins'; -fx-font-size: 12px;");
        lblTotal.setPrefWidth(150);

        // =====================================================================
        // LOGIKA INTERAKTIF TOMBOL KETERANGAN (StackPane)
        // =====================================================================
        StackPane actionContainer = new StackPane();
        actionContainer.setPrefWidth(120);

        // Wadah 1: Dua Tombol Aksi (Terima & Tolak Vertikal)
        VBox boxButtons = new VBox(4);
        boxButtons.setAlignment(Pos.CENTER);
        
        Button btnTerima = new Button("Terima");
        btnTerima.setStyle("-fx-background-color: #FF9800; -fx-text-fill: white; -fx-background-radius: 15; -fx-padding: 3 15; -fx-font-family: 'Poppins'; -fx-font-size: 10px; -fx-font-weight: bold;");
        btnTerima.setPrefWidth(75);
        btnTerima.setCursor(javafx.scene.Cursor.HAND);

        Button btnTolak = new Button("Tolak");
        btnTolak.setStyle("-fx-background-color: #FFFFFF; -fx-text-fill: #5A7184; -fx-background-radius: 15; -fx-padding: 3 15; -fx-font-family: 'Poppins'; -fx-font-size: 10px; -fx-border-color: #D3D9DE; -fx-border-radius: 15; -fx-font-weight: bold;");
        btnTolak.setPrefWidth(75);
        btnTolak.setCursor(javafx.scene.Cursor.HAND);

        boxButtons.getChildren().addAll(btnTerima, btnTolak);

        // Wadah 2: Hasil Status Setelah Klik (Titik warna + Teks)
        HBox boxResult = new HBox(6);
        boxResult.setAlignment(Pos.CENTER);
        Circle dotIndicator = new Circle(4);
        Label lblStatusText = new Label();
        lblStatusText.setStyle("-fx-font-family: 'Poppins'; -fx-font-weight: bold; -fx-font-size: 11px; -fx-text-fill: #1A3C5A;");
        
        boxResult.getChildren().addAll(dotIndicator, lblStatusText);
        boxResult.setVisible(false);
        boxResult.setManaged(false);

        // Aksi klik "Terima"
        btnTerima.setOnAction(e -> {
            boxButtons.setVisible(false);
            boxButtons.setManaged(false);
            
            dotIndicator.setStyle("-fx-fill: #4CAF50;"); // Titik Hijau
            lblStatusText.setText("Disetujui");
            
            boxResult.setVisible(true);
            boxResult.setManaged(true);
        });

        // Aksi klik "Tolak"
        btnTolak.setOnAction(e -> {
            boxButtons.setVisible(false);
            boxButtons.setManaged(false);
            
            dotIndicator.setStyle("-fx-fill: #FF9800;"); // Titik Oranye
            lblStatusText.setText("Ditolak");
            
            boxResult.setVisible(true);
            boxResult.setManaged(true);
        });

        actionContainer.getChildren().addAll(boxButtons, boxResult);
        // =====================================================================

        row.getChildren().addAll(lblName, lblUser, lblTotal, actionContainer);
        return row;
    }

    public Parent getView() {
        return view;
    }
}