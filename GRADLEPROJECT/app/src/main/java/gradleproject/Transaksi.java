package gradleproject;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

public class Transaksi {

    private VBox view;

    public Transaksi() {
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

        // 2. BAGIAN ATAS: TABEL TRANSAKSI
        VBox topSection = new VBox(0);
        topSection.setMaxWidth(800);

        HBox tableHeader = new HBox();
        tableHeader.setStyle("-fx-background-color: #D3D9DE; -fx-background-radius: 15 15 0 0;"); 
        tableHeader.setAlignment(Pos.CENTER_LEFT);
        tableHeader.setPadding(new Insets(12, 25, 12, 25)); 

        Label colNama = new Label("Nama Acara");
        colNama.setStyle("-fx-text-fill: #1A3C5A; -fx-font-weight: bold; -fx-font-family: 'Poppins';");
        colNama.setPrefWidth(220); 

        Label colPeserta = new Label("Peserta");
        colPeserta.setStyle("-fx-text-fill: #1A3C5A; -fx-font-weight: bold; -fx-font-family: 'Poppins';");
        colPeserta.setPrefWidth(180); 

        Label colTotal = new Label("Total");
        colTotal.setStyle("-fx-text-fill: #1A3C5A; -fx-font-weight: bold; -fx-font-family: 'Poppins';");
        colTotal.setPrefWidth(150); 

        Label colTanggal = new Label("Tanggal");
        colTanggal.setStyle("-fx-text-fill: #1A3C5A; -fx-font-weight: bold; -fx-font-family: 'Poppins';");
        colTanggal.setPrefWidth(100); 
        colTanggal.setAlignment(Pos.CENTER);

        tableHeader.getChildren().addAll(colNama, colPeserta, colTotal, colTanggal);

        VBox blueBox = new VBox(15);
        blueBox.setStyle("-fx-background-color: #0A3B5C; -fx-background-radius: 0 0 15 15;");
        blueBox.setPadding(new Insets(20, 25, 20, 25));

        VBox listContainer = new VBox(12);
        listContainer.setStyle("-fx-background-color: transparent;");

        listContainer.getChildren().addAll(
            createTransactionRow("Makassar Traditional\nCostume Showcase", "Zahwa anak\nkedua", "Rp25.000", "20/05/2026"),
            createTransactionRow("Makassar Traditional\nCostume Showcase", "Zahwa anak\nkedua", "Rp25.000", "20/05/2026")
        );

        ScrollPane scrollTable = new ScrollPane(listContainer);
        scrollTable.setFitToWidth(true); 
        scrollTable.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); 
        scrollTable.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); 
        scrollTable.setStyle("-fx-background: transparent; -fx-background-color: transparent; -fx-border-color: transparent;");

        blueBox.getChildren().add(scrollTable);
        topSection.getChildren().addAll(tableHeader, blueBox);

        HBox boxLihatLainnya = new HBox();
        boxLihatLainnya.setAlignment(Pos.CENTER_RIGHT);
        boxLihatLainnya.setPadding(new Insets(10, 0, 0, 0));
        boxLihatLainnya.setMaxWidth(800); 
        
        Button btnLihatLainnya = new Button("Lihat lainnya");
        btnLihatLainnya.setStyle("-fx-background-color: #FF9800; -fx-text-fill: white; -fx-font-family: 'Poppins'; -fx-font-size: 11px; -fx-background-radius: 15; -fx-padding: 4 20;");
        btnLihatLainnya.setCursor(javafx.scene.Cursor.HAND);
        btnLihatLainnya.setOnAction(event -> {
            if (Dashboard.getInstance() != null) Dashboard.getInstance().pindahKeDaftarTransaksiPenuh();
        });
        boxLihatLainnya.getChildren().add(btnLihatLainnya);

        // =====================================================================
        // 3. BAGIAN BAWAH: KARTU RINGKASAN
        // =====================================================================
        HBox bottomSection = new HBox(20);
        bottomSection.setMaxWidth(800);
        bottomSection.setAlignment(Pos.TOP_LEFT);
        bottomSection.setPadding(new Insets(10, 0, 0, 0));

        // --- KARTU KIRI: PENGEMBALIAN DANA (PERBAIKAN POSISI TENGAH) ---
        VBox cardRefund = new VBox(0); // Set spacing 0 agar kontrol manual pakai spacer
        cardRefund.setStyle("-fx-background-color: #F8F9FA; -fx-background-radius: 15; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 8, 0, 0, 3);");
        cardRefund.setPadding(new Insets(20));
        cardRefund.setPrefSize(280, 240);
        cardRefund.setMaxSize(280, 240);
        cardRefund.setAlignment(Pos.TOP_CENTER);

        Label lblRefund = new Label("PERMINTAAN\nPENGEMBALIAN DANA");
        lblRefund.setTextAlignment(TextAlignment.CENTER);
        lblRefund.setStyle("-fx-font-family: 'Poppins'; -fx-font-weight: bold; -fx-font-size: 13px; -fx-text-fill: #1A3C5A;");

        // 👉 KUNCI 1: Spacer Atas (Mendorong kotak angka ke bawah)
        Region spacerAtas = new Region();
        
        VBox.setVgrow(spacerAtas, Priority.ALWAYS);

        // Kotak Oranye Angka 5
        StackPane badgeRefund = new StackPane();
        badgeRefund.setStyle("-fx-background-color: #FF9800; -fx-background-radius: 12;");
        badgeRefund.setPrefSize(100, 70); // Ukuran sedikit diperbesar agar proporsional
        badgeRefund.setMaxSize(100, 70);
        
        Label lblAngka = new Label("5"); 
        lblAngka.setStyle("-fx-font-family: 'Poppins'; -fx-font-weight: bold; -fx-font-size: 32px; -fx-text-fill: #1A3C5A;");
        badgeRefund.getChildren().add(lblAngka);

        // 👉 KUNCI 2: Spacer Bawah (Mendorong kotak angka ke atas & tombol ke paling bawah)
        Region spacerBawah = new Region();
        
        VBox.setVgrow(spacerBawah, Priority.ALWAYS);

        HBox btnBoxRefund = new HBox();
        btnBoxRefund.setAlignment(Pos.CENTER_RIGHT);
        
        Button btnLihatRefund = new Button("Lihat");
        btnLihatRefund.setStyle("-fx-background-color: #FF9800; -fx-text-fill: white; -fx-font-family: 'Poppins'; -fx-font-size: 10px; -fx-background-radius: 12; -fx-padding: 3 15;");
        btnLihatRefund.setCursor(javafx.scene.Cursor.HAND);
        btnLihatRefund.setOnAction(event -> {
            if (Dashboard.getInstance() != null) Dashboard.getInstance().pindahKePengembalianDana();
        });
        btnBoxRefund.getChildren().add(btnLihatRefund);

        // Susun: Judul -> Spacer Atas -> Angka -> Spacer Bawah -> Tombol
        cardRefund.getChildren().addAll(lblRefund, spacerAtas, badgeRefund, spacerBawah, btnBoxRefund);

        // --- KARTU KANAN: LAPORAN PENDAPATAN ---
        VBox cardIncome = new VBox(12);
        cardIncome.setStyle("-fx-background-color: #F8F9FA; -fx-background-radius: 15; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 8, 0, 0, 3);");
        cardIncome.setPadding(new Insets(20));
        cardIncome.setPrefSize(400, 240);
        cardIncome.setMaxSize(500, 240);
        
        HBox.setHgrow(cardIncome, Priority.ALWAYS); 

        Label lblIncome = new Label("LAPORAN PENDAPATAN 📈");
        lblIncome.setStyle("-fx-font-family: 'Poppins'; -fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: #1A3C5A;");

        VBox listBulan = new VBox(10);
        listBulan.getChildren().addAll(createMonthField("Jan: Rp"),
        createMonthField("Feb: Rp"), 
        createMonthField("Mar: Rp"));

        Region incomeSpacer = new Region();
        VBox.setVgrow(incomeSpacer, Priority.ALWAYS);

        HBox btnBoxIncome = new HBox();
        btnBoxIncome.setAlignment(Pos.CENTER_RIGHT);
        
        Button btnDetailIncome = new Button("Detail");
        btnDetailIncome.setStyle("-fx-background-color: #FF9800; -fx-text-fill: white; -fx-font-family: 'Poppins'; -fx-font-size: 10px; -fx-background-radius: 12; -fx-padding: 3 15;");
        btnBoxIncome.getChildren().add(btnDetailIncome);
        btnDetailIncome.setCursor(javafx.scene.Cursor.HAND);
        
        btnDetailIncome.setOnAction(event -> {
            if (Dashboard.getInstance() != null) {
                Dashboard.getInstance().pindahKeLaporanPendapatan();
            }
        });

        cardIncome.getChildren().addAll(lblIncome, listBulan, incomeSpacer, btnBoxIncome);

        bottomSection.getChildren().addAll(cardRefund, cardIncome);
        view.getChildren().addAll(header, topSection, boxLihatLainnya, bottomSection);
    }

    private HBox createTransactionRow(String name, String participant, String total, String date) {
        HBox row = new HBox();
        row.setAlignment(Pos.CENTER_LEFT);
        row.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 10; -fx-padding: 10 15 10 15;");
        
        Label lblName = new Label(name);
        lblName.setStyle("-fx-text-fill: #1A3C5A; -fx-font-weight: bold; -fx-font-family: 'Poppins'; -fx-font-size: 12px;");
        lblName.setPrefWidth(205); 
        
        Label lblParticipant = new Label(participant);
        lblParticipant.setStyle("-fx-text-fill: #5A7184; -fx-font-family: 'Poppins'; -fx-font-size: 12px;");
        lblParticipant.setPrefWidth(180);

        Label lblTotal = new Label(total);
        lblTotal.setStyle("-fx-text-fill: #5A7184; -fx-font-family: 'Poppins'; -fx-font-size: 12px;");
        lblTotal.setPrefWidth(150);

        HBox dateBox = new HBox();
        dateBox.setPrefWidth(100);
        dateBox.setAlignment(Pos.CENTER);

        Label lblDate = new Label(date);
        lblDate.setStyle("-fx-background-color: #FF9800; -fx-text-fill: white; -fx-background-radius: 15; -fx-padding: 4 15; -fx-font-family: 'Poppins'; -fx-font-size: 10px;");
        dateBox.getChildren().add(lblDate);
        row.getChildren().addAll(lblName, lblParticipant, lblTotal, dateBox);
        return row;
    }

    private Label createMonthField(String text) {
        Label field = new Label(text);
        field.setMaxWidth(Double.MAX_VALUE); 
        field.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #A0A9B5; -fx-border-radius: 20; -fx-background-radius: 20; -fx-padding: 6 15; -fx-font-family: 'Poppins'; -fx-font-size: 12px; -fx-text-fill: #1A3C5A;");
        return field;
    }

    public Parent getView() { return view; }
}