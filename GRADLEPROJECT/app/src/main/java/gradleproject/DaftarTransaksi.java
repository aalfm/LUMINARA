package gradleproject;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class DaftarTransaksi {

    private VBox view;

    public DaftarTransaksi() {
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
        VBox.setVgrow(tableBox, Priority.ALWAYS); // Memaksa tabel mengisi sisa layar ke bawah

        // A. Header Tabel (Abu-abu) - Sesuai Mockup Terbaru
        HBox tableHeader = new HBox();
        tableHeader.setStyle("-fx-background-color: #D3D9DE; -fx-background-radius: 15 15 0 0;"); 
        tableHeader.setAlignment(Pos.CENTER_LEFT);
        tableHeader.setPadding(new Insets(12, 25, 12, 25)); 

        Label colNama = new Label("Nama Acara");
        colNama.setStyle("-fx-text-fill: #1A3C5A; -fx-font-weight: bold; -fx-font-family: 'Poppins';");
        colNama.setPrefWidth(220); 

        Label colOrg = new Label("Penyelenggara");
        colOrg.setStyle("-fx-text-fill: #1A3C5A; -fx-font-weight: bold; -fx-font-family: 'Poppins';");
        colOrg.setPrefWidth(180); 

        Label colTotal = new Label("Total");
        colTotal.setStyle("-fx-text-fill: #1A3C5A; -fx-font-weight: bold; -fx-font-family: 'Poppins';");
        colTotal.setPrefWidth(150); 

        Label colStatus = new Label("Status");
        colStatus.setStyle("-fx-text-fill: #1A3C5A; -fx-font-weight: bold; -fx-font-family: 'Poppins';");
        colStatus.setPrefWidth(100); 
        colStatus.setAlignment(Pos.CENTER);

        tableHeader.getChildren().addAll(colNama, colOrg, colTotal, colStatus);

        // B. Body Tabel (Wadah Biru Gelap)
        VBox blueBox = new VBox(15);
        blueBox.setStyle("-fx-background-color: #0A3B5C; -fx-background-radius: 0 0 15 15;");
        blueBox.setPadding(new Insets(20, 25, 20, 25));
        VBox.setVgrow(blueBox, Priority.ALWAYS); // Mengisi sisa ruang ke bawah

        VBox listContainer = new VBox(12);
        listContainer.setStyle("-fx-background-color: transparent;");

        // 👉 PERBAIKAN: Mengisi data dummy penuh agar sama dengan halaman utama (Rp25.000 & Badge Tanggal)
        for (int i = 0; i < 4; i++) {
            listContainer.getChildren().addAll(
                createTransactionRow("Makassar Traditional\nCostume Showcase", "Zahwa anak\nkedua", "Rp25.000", "20/05/2026"),
                createTransactionRow("Makassar Traditional\nCostume Showcase", "Zahwa anak\nkedua", "Rp25.000", "20/05/2026"),
                createTransactionRow("Makassar Traditional\nCostume Showcase", "Zahwa anak\nkedua", "Rp25.000", "20/05/2026")
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

    // 👉 PERBAIKAN: Fungsi Row Baris menggunakan Badge Oranye untuk teks Tanggal di kolom Status
    private HBox createTransactionRow(String name, String organizer, String total, String date) {
        HBox row = new HBox();
        row.setAlignment(Pos.CENTER_LEFT);
        row.setStyle("-fx-background-color: #F8F7F4; -fx-background-radius: 10; -fx-padding: 10 15 10 15;");
        
        Label lblName = new Label(name);
        lblName.setStyle("-fx-text-fill: #1A3C5A; -fx-font-weight: bold; -fx-font-family: 'Poppins'; -fx-font-size: 12px;");
        lblName.setPrefWidth(205); 

        Label lblOrg = new Label(organizer);
        lblOrg.setStyle("-fx-text-fill: #5A7184; -fx-font-family: 'Poppins'; -fx-font-size: 12px;");
        lblOrg.setPrefWidth(180);

        Label lblTotal = new Label(total);
        lblTotal.setStyle("-fx-text-fill: #5A7184; -fx-font-family: 'Poppins'; -fx-font-size: 12px;");
        lblTotal.setPrefWidth(150);

        HBox statusBox = new HBox();
        statusBox.setPrefWidth(100);
        statusBox.setAlignment(Pos.CENTER);
        
        // Membuat badge tanggal warna oranye agar pas di kolom Status
        Label lblStatus = new Label(date);
        lblStatus.setStyle("-fx-background-color: #FF9800; -fx-text-fill: white; -fx-background-radius: 15; -fx-padding: 4 15; -fx-font-family: 'Poppins'; -fx-font-size: 10px;");
        
        statusBox.getChildren().add(lblStatus);

        row.getChildren().addAll(lblName, lblOrg, lblTotal, statusBox);
        return row;
    }

    public Parent getView() {
        return view;
    }
}