package gradleproject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
// <-- TAMBAHKAN IMPORT INI AGAR GRIDPANE DIKENALI
import javafx.scene.layout.*;

public class PesertaView extends StackPane {

    public ManajemenAcaraView mainDashboard;
    
    private VBox overviewContent;
    private VBox detailContent;

    // Node Detail Konten Peserta Dinamis
    private Label lblNamaAcaraPesertaHeader;
    private Label lblSubDetailAcaraPeserta;
    private Label lblTotalPesertaRingkasan;
    private VBox tabelPesertaRowsContainer;

    public PesertaView(ManajemenAcaraView mainDashboard) {
        this.mainDashboard = mainDashboard;
        tampilkanOverview();
    }

    public void tampilkanOverview() {
        this.getChildren().clear();
        this.getChildren().add(getPesertaOverviewContent());
    }

    public void tampilkanDetail(ManajemenAcaraView.AcaraMock data) {
        this.getChildren().clear();
        this.getChildren().add(getPesertaDetailContent());
        
        lblNamaAcaraPesertaHeader.setText(data.nama);
        lblSubDetailAcaraPeserta.setText(data.lokasi + "   •   " + data.tanggal + "   •   " + data.waktu);
        lblTotalPesertaRingkasan.setText(data.terdaftar + " / " + data.kuota);

        tabelPesertaRowsContainer.getChildren().clear();
        
        // LOGIKA DINAMIS: Generate jumlah baris sesuai jumlah peserta yang terdaftar
        int jumlahPeserta = Integer.parseInt(data.terdaftar);
        
        // Array nama tiruan untuk variasi data
        String[] daftarNamaTiruan = {
            "Alifah", "Zahwa", "Syarief", "Fa'iq",
        };
        String[] daftarInisial = {"A", "Z", "S", "F"};

        for (int i = 0; i < jumlahPeserta; i++) {
            // Variasi nama dan status agar tidak monoton
            String nama = daftarNamaTiruan[i % daftarNamaTiruan.length] + (i >= daftarNamaTiruan.length ? " " + (i/7) : "");
            String inisial = daftarInisial[i % daftarInisial.length];
            String status = (i % 3 == 2) ? "Tidak Hadir" : "Hadir";
            String warnaStatus = (i % 3 == 2) ? "#FF9412" : "#60E514";
            String telp = "081234567" + String.format("%03d", i);
            String email = nama.toLowerCase().replace(" ", "").replace(".", "") + "@gmail.com";
            
            tabelPesertaRowsContainer.getChildren().add(buatBarisPeserta(inisial, nama, telp, email, status, warnaStatus));
        }
    }

    // =========================================================
    // LAYOUT 1: PESERTA OVERVIEW (Tampilan Form Tabel Baru)
    // =========================================================
    private VBox getPesertaOverviewContent() {
        overviewContent = new VBox(20);
        overviewContent.setPadding(new Insets(40, 40, 20, 40));

        VBox greetingBox = new VBox(5);
        Label greeting = new Label("Hai, tim.");
        greeting.getStyleClass().add("heading");
        Label subGreeting = new Label("Ingat untuk atur kinerja acara kamu . . .");
        subGreeting.getStyleClass().add("subheading");
        greetingBox.getChildren().addAll(greeting, subGreeting);

        // Header Table Menu Sesuai Gambar
        GridPane tableHeader = new GridPane();
        tableHeader.setPadding(new Insets(15, 20, 15, 20));
        tableHeader.setStyle("-fx-background-color: #E6ECF0; -fx-background-radius: 10;");
        setupOverviewTableConstraints(tableHeader);

        Label col1 = new Label("Detail Acara");
        Label col2 = new Label("Waktu");
        Label col3 = new Label("Jumlah Peserta");
        Label col4 = new Label("Keterangan");
        
        String styleHeader = "-fx-font-weight: bold; -fx-text-fill: #002B5B; -fx-font-size: 14px; -fx-font-family: 'Poppins';";
        col1.setStyle(styleHeader); col2.setStyle(styleHeader); 
        col3.setStyle(styleHeader); col4.setStyle(styleHeader);
        col3.setAlignment(Pos.CENTER); col4.setAlignment(Pos.CENTER);

        tableHeader.add(col1, 0, 0); tableHeader.add(col2, 1, 0); 
        tableHeader.add(col3, 2, 0); tableHeader.add(col4, 3, 0);

        VBox listRowsContainer = new VBox(12);
        listRowsContainer.setPadding(new Insets(10, 0, 10, 0));

        ScrollPane scrollPane = new ScrollPane(listRowsContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background-insets: 0; -fx-padding: 0;");
        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        // Load Data Pokok
        List<ManajemenAcaraView.AcaraMock> daftarAcara = new ArrayList<>();
        daftarAcara.add(new ManajemenAcaraView.AcaraMock("Makassar Traditional Costume Showcase", "2026, Mei 20-22", "19:00:00 - 22:00:00", "Trans Studio Mall Makassar", "11", "100"));
        daftarAcara.add(new ManajemenAcaraView.AcaraMock("Legenda Makassar Storytelling Corner", "2026, Mei 19-21", "10:00:00 - 14:00:00", "Trans Studio Mall Makassar", "50", "50"));
        daftarAcara.add(new ManajemenAcaraView.AcaraMock("Pappaseng Culture Fest", "2026, Mei 20-21", "16:00:00 - 20:00:00", "Kawasan Center Point of Indonesia (CPI)", "43", "50"));
        daftarAcara.add(new ManajemenAcaraView.AcaraMock("Pelatihan Berbicara Bahasa Makassar", "2026, Mei 20", "09:00:00 - 11:00:00", "Benteng Rotterdam", "15", "20"));

        for (ManajemenAcaraView.AcaraMock acara : daftarAcara) {
            listRowsContainer.getChildren().add(buatBarisOverviewTabel(acara));
        }

        overviewContent.getChildren().addAll(greetingBox, tableHeader, scrollPane);
        return overviewContent;
    }

    private GridPane buatBarisOverviewTabel(ManajemenAcaraView.AcaraMock data) {
        GridPane row = new GridPane();
        row.setPadding(new Insets(15, 20, 15, 20));
        row.setStyle("-fx-background-color: #F3F4F6; -fx-background-radius: 12; -fx-border-color: #DDE5EC; -fx-border-radius: 12; -fx-border-width: 1;");
        setupOverviewTableConstraints(row);

        // Kolom 1: Detail Acara (Nama + Lokasi)
        VBox detailBox = new VBox(4);
        Label lblNama = new Label(data.nama);
        lblNama.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: #002B5B; -fx-font-family: 'Poppins';");
        Label lblLokasi = new Label(data.lokasi);
        lblLokasi.setStyle("-fx-font-size: 12px; -fx-text-fill: #70889F; -fx-font-family: 'Poppins';");
        detailBox.getChildren().addAll(lblNama, lblLokasi);

        // Kolom 2: Waktu (Tanggal + Jam)
        VBox waktuBox = new VBox(4);
        Label lblTgl = new Label(data.tanggal);
        lblTgl.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #002B5B; -fx-font-family: 'Poppins';");
        Label lblJam = new Label(data.waktu);
        lblJam.setStyle("-fx-font-size: 12px; -fx-text-fill: #70889F; -fx-font-family: 'Poppins';");
        waktuBox.getChildren().addAll(lblTgl, lblJam);

        // Kolom 3: Jumlah Peserta
        Label lblRasio = new Label(data.terdaftar + "/" + data.kuota);
        lblRasio.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: #002B5B; -fx-font-family: 'Poppins';");
        lblRasio.setAlignment(Pos.CENTER);

        // Kolom 4: Keterangan (Tombol Aksi)
        HBox btnWrapper = new HBox();
        btnWrapper.setAlignment(Pos.CENTER);
        Button btnLihat = new Button("Lihat Peserta");
        btnLihat.setStyle("-fx-background-color: #FF922B; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 8; -fx-padding: 8 16 8 16; -fx-cursor: hand; -fx-font-family: 'Poppins';");
        btnLihat.setOnAction(e -> tampilkanDetail(data));
        btnWrapper.getChildren().add(btnLihat);

        row.add(detailBox, 0, 0);
        row.add(waktuBox, 1, 0);
        row.add(lblRasio, 2, 0);
        row.add(btnWrapper, 3, 0);

        return row;
    }

    // =========================================================
    // LAYOUT: PESERTA DETAIL (Tabel Nama Peserta)
    // =========================================================
    private VBox getPesertaDetailContent() {
        detailContent = new VBox(20);
        detailContent.setPadding(new Insets(40, 40, 20, 40));

        Button btnKembali = new Button(" < Kembali ke Semua Event");

        btnKembali.getStyleClass().add("btn-kembali"); 
        btnKembali.setOnAction(e -> tampilkanOverview());
        detailContent.getChildren().add(btnKembali);

        VBox bannerAcaraBox = new VBox(10);
        bannerAcaraBox.setPadding(new Insets(20));
        bannerAcaraBox.setStyle("-fx-background-color: #E6ECF0; -fx-background-radius: 15;");

        HBox bannerSplit = new HBox();
        bannerSplit.setAlignment(Pos.CENTER_LEFT);

        VBox leftBanner = new VBox(6);
        HBox.setHgrow(leftBanner, Priority.ALWAYS);
        lblNamaAcaraPesertaHeader = new Label();
        lblNamaAcaraPesertaHeader.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #003A6C; -fx-font-family: 'Poppins';");
        lblSubDetailAcaraPeserta = new Label();
        lblSubDetailAcaraPeserta.setStyle("-fx-font-size: 12px; -fx-text-fill: #495057; -fx-font-family: 'Poppins';");
        leftBanner.getChildren().addAll(lblNamaAcaraPesertaHeader, lblSubDetailAcaraPeserta);

        VBox rightBanner = new VBox(4);
        rightBanner.setAlignment(Pos.CENTER_LEFT);
        Label lblPesertaTitle = new Label("Peserta");
        lblPesertaTitle.setStyle("-fx-font-size: 11px; -fx-text-fill: #6C757D; -fx-font-family: 'Poppins';");
        lblTotalPesertaRingkasan = new Label();
        lblTotalPesertaRingkasan.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #003A6C; -fx-font-family: 'Poppins';");
        rightBanner.getChildren().addAll(lblPesertaTitle, lblTotalPesertaRingkasan);
        
        Region spacerLine = new Region();
        spacerLine.setPrefSize(1, 35);
        spacerLine.setStyle("-fx-background-color: #CED4DA;");
        HBox.setMargin(spacerLine, new Insets(0, 20, 0, 20));

        bannerSplit.getChildren().addAll(leftBanner, spacerLine, rightBanner);
        bannerAcaraBox.getChildren().add(bannerSplit);
        detailContent.getChildren().add(bannerAcaraBox);

        tabelPesertaRowsContainer = new VBox(10);
        tabelPesertaRowsContainer.setPadding(new Insets(10, 5, 10, 5));

        ScrollPane scrollTabel = new ScrollPane(tabelPesertaRowsContainer);
        scrollTabel.setFitToWidth(true);
        scrollTabel.setStyle("-fx-background-color: transparent; -fx-background-insets: 0; -fx-padding: 0;");
        VBox.setVgrow(scrollTabel, Priority.ALWAYS);

        detailContent.getChildren().add(scrollTabel);
        return detailContent;
    }

    private HBox buatBarisPeserta(String inisial, String nama, String telp, String email, String status, String warnaStatus) {
        HBox row = new HBox(20);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setPadding(new Insets(15, 20, 15, 20));
        row.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 12; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.03), 5, 0, 0, 2);");

        // 1. Avatar Bulat
        StackPane avatarCircle = new StackPane();
        avatarCircle.setPrefSize(40, 40); avatarCircle.setMinSize(40, 40);
        avatarCircle.setStyle("-fx-background-color: #FFE8CC; -fx-background-radius: 50;");
        Label lblInisial = new Label(inisial);
        lblInisial.setStyle("-fx-font-weight: bold; -fx-text-fill: #D9480F; -fx-font-size: 13px; -fx-font-family: 'Poppins';");
        avatarCircle.getChildren().add(lblInisial);

        // 2. Kolom Nama Peserta
        Label lblNama = new Label(nama);
        lblNama.setStyle("-fx-font-weight: bold; -fx-text-fill: #212529; -fx-font-size: 14px; -fx-font-family: 'Poppins';");
        lblNama.setPrefWidth(200);

        // 3. Kolom Nomor Telepon
        HBox telpContainer = new HBox(8);
        telpContainer.setAlignment(Pos.CENTER_LEFT);
        telpContainer.setPrefWidth(160);
        ImageView iconTelp = dapetkanIconView("/aset/iconLuminara/icon-user.png", 14);
        Label lblTelp = new Label(telp);
        lblTelp.setStyle("-fx-text-fill: #495057; -fx-font-size: 13px; -fx-font-family: 'Poppins';");
        if (iconTelp != null) telpContainer.getChildren().add(iconTelp);
        telpContainer.getChildren().add(lblTelp);

        // 4. Kolom Email
        HBox emailContainer = new HBox(8);
        emailContainer.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(emailContainer, Priority.ALWAYS);
        ImageView iconEmail = dapetkanIconView("/aset/iconLuminara/icon-masuk-keluar.png", 14);
        Label lblEmail = new Label(email);
        lblEmail.setStyle("-fx-text-fill: #495057; -fx-font-size: 13px; -fx-font-family: 'Poppins';");
        if (iconEmail != null) emailContainer.getChildren().add(iconEmail);
        emailContainer.getChildren().add(lblEmail);

        // 5. Status Kehadiran
        HBox statusBox = new HBox(8);
        statusBox.setAlignment(Pos.CENTER_LEFT);
        statusBox.setPrefWidth(110);
        
        Region dotStatus = new Region();
        dotStatus.setPrefSize(8, 8); 
        dotStatus.setMinSize(8, 8);
        dotStatus.setStyle("-fx-background-color: " + warnaStatus + "; -fx-background-radius: 50;");
        
        Label lblStatus = new Label(status);
        lblStatus.setStyle("-fx-text-fill: " + warnaStatus + "; -fx-font-weight: bold; -fx-font-size: 14px; -fx-font-family: 'Poppins';");
        statusBox.getChildren().addAll(dotStatus, lblStatus);

        row.getChildren().addAll(avatarCircle, lblNama, telpContainer, emailContainer, statusBox);
        return row;
    }

    private ImageView dapetkanIconView(String path, double ukuran) {
        try {
            InputStream stream = getClass().getResourceAsStream(path);
            if (stream != null) {
                ImageView iv = new ImageView(new Image(stream));
                iv.setFitWidth(ukuran);
                iv.setFitHeight(ukuran);
                iv.setPreserveRatio(true);
                return iv;
            }
        } catch (Exception e) {
            System.out.println("Gagal memuat ikon: " + path);
        }
        return null;
    }

    // =========================================================================
    // 🛠️ METHOD BARU: Mengatur Jangkauan Lebar Kolom Tabel Overview (Ditambahkan)
    // =========================================================================
    private void setupOverviewTableConstraints(GridPane grid) {
        ColumnConstraints c1 = new ColumnConstraints(); c1.setPercentWidth(40); // Kolom 1 (Detail Acara)
        ColumnConstraints c2 = new ColumnConstraints(); c2.setPercentWidth(30); // Kolom 2 (Waktu)
        ColumnConstraints c3 = new ColumnConstraints(); c3.setPercentWidth(15); // Kolom 3 (Jumlah Peserta)
        ColumnConstraints c4 = new ColumnConstraints(); c4.setPercentWidth(15); // Kolom 4 (Tombol Aksi)
        grid.getColumnConstraints().setAll(c1, c2, c3, c4);
    }
}