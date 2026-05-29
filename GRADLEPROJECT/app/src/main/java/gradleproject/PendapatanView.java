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
import javafx.scene.layout.*;

public class PendapatanView extends StackPane {

    public ManajemenAcaraView mainDashboard;
    
    // Simpan kedua panel secara permanen agar tidak dibuat ulang
    private VBox overviewContent;
    private VBox detailContent;

    // Node Detail Konten Pendapatan Dinamis
    private Label lblNamaAcaraHeader;
    private Label lblStatusBadge;
    private Label lblSubDetailAcara;
    private VBox tabelPembeliRowsContainer;

    public PendapatanView(ManajemenAcaraView mainDashboard) {
        this.mainDashboard = mainDashboard;
        
        // Inisialisasi struktur layout sekali saja di awal
        inisialisasiOverviewLayout();
        inisialisasiDetailLayout();
        
        // Tampilan default
        tampilkanOverview();
    }

    public void tampilkanOverview() {
        this.getChildren().clear();
        this.getChildren().add(overviewContent);
    }

    public void tampilkanDetail(ManajemenAcaraView.AcaraMock data, String totalPendapatan, String tiketTerjual) {
        this.getChildren().clear();
        this.getChildren().add(detailContent);
        
        // UPDATE KONTEN TEKS DINAMIS SECARA AMAN
        lblNamaAcaraHeader.setText(data.nama);
        lblSubDetailAcara.setText(data.tanggal + "   •   " + data.waktu + "   •   " + data.lokasi);
        
        // Kosongkan baris tabel pembeli sebelum diisi data acara yang baru
        tabelPembeliRowsContainer.getChildren().clear();
        
        // Ekstraksi Angka Tiket Terjual yang Aman
        int jumlahPembeli = 0;
        try {
            String angkaMentah = tiketTerjual.split("/")[0].replaceAll("[^0-9]", "").trim();
            if (!angkaMentah.isEmpty()) {
                jumlahPembeli = Integer.parseInt(angkaMentah);
            }
        } catch (Exception e) {
            jumlahPembeli = 0; 
        }
        
        if (jumlahPembeli <= 0) {
            jumlahPembeli = 3; 
        }
        
        String[] daftarNamaTiruan = {"Alifah Mahrani", "Zahwa", "Syarief Rahmat", "Fa'iq Musharraf"};
        String[] daftarInisial = {"A", "Z", "SR", "FM"};

        for (int i = 0; i < jumlahPembeli; i++) {
            String nama = daftarNamaTiruan[i % daftarNamaTiruan.length] + (i >= daftarNamaTiruan.length ? " " + (i/4 + 1) : "");
            String inisial = daftarInisial[i % daftarInisial.length];
            
            String statusBayar = (i % 3 == 2) ? "Belum Lunas" : "Lunas";
            String warnaStatus = (i % 3 == 2) ? "#FF9412" : "#60E514";
            
            String telp = "081234567" + String.format("%03d", i);
            String email = nama.toLowerCase().replace(" ", "").replace(".", "") + "@gmail.com";

            // PERBAIKAN: Parameter totalPendapatan tidak dikirimkan lagi ke baris tabel rincian
            tabelPembeliRowsContainer.getChildren().add(
                buatBarisPembeli(inisial, nama, telp, email, statusBayar, warnaStatus)
            );
        }
    }

    // =========================================================
    // LAYOUT 1: INITIALIZE PENDAPATAN OVERVIEW
    // =========================================================
    private void inisialisasiOverviewLayout() {
        overviewContent = new VBox(25);
        overviewContent.setPadding(new Insets(40, 40, 20, 40));

        
        // 1. Header Greetings
        VBox greetingBox = new VBox(5);
        Label greeting = new Label("Hai, tim.");
        greeting.getStyleClass().add("heading");
        Label subGreeting = new Label("Sudah dapat berapa . . . ?");
        subGreeting.getStyleClass().add("subheading");
        greetingBox.getChildren().addAll(greeting, subGreeting);

        HBox kartuContainer = new HBox(20);
        HBox kartuTiket = buatKartuRingkasan("TIKET TERJUAL", "1.250", "Total tiket terjual");
        HBox kartuUang = buatKartuRingkasan("TOTAL PENDAPATAN", "37jt", "Total pendapatan kotor");
        HBox.setHgrow(kartuTiket, Priority.ALWAYS);
        HBox.setHgrow(kartuUang, Priority.ALWAYS);
        kartuContainer.getChildren().addAll(kartuTiket, kartuUang);

        GridPane tableHeader = new GridPane();
        tableHeader.setPadding(new Insets(15, 20, 15, 20));
        tableHeader.setStyle("-fx-background-color: #E6ECF0; -fx-background-radius: 10;");
        setupTableConstraints(tableHeader);

        Label h1 = new Label("Nama Acara");
        Label h2 = new Label("Tiket Terjual");
        Label h3 = new Label("Harga Rata-rata (Rp)");
        Label h4 = new Label("Pajak (10%)");
        Label h5 = new Label("Total Pendapatan");
        Label h6 = new Label("Status");

        String styleH = "-fx-font-weight: bold; -fx-text-fill: #002B5B; -fx-font-size: 13px; -fx-font-family: 'Poppins';";
        h1.setStyle(styleH); h2.setStyle(styleH); h3.setStyle(styleH);
        h4.setStyle(styleH); h5.setStyle(styleH); h6.setStyle(styleH);

        tableHeader.add(h1, 0, 0); tableHeader.add(h2, 1, 0); tableHeader.add(h3, 2, 0);
        tableHeader.add(h4, 3, 0); tableHeader.add(h5, 4, 0); tableHeader.add(h6, 5, 0);

        VBox listRowsContainer = new VBox(12);
        listRowsContainer.setPadding(new Insets(10, 0, 10, 0));

        ScrollPane scrollPane = new ScrollPane(listRowsContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background-insets: 0; -fx-padding: 0;");
        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        // Data Mock Utama
        List<ManajemenAcaraView.AcaraMock> daftarAcara = new ArrayList<>();
        daftarAcara.add(new ManajemenAcaraView.AcaraMock("Kecapi & Sulawesi Traditional Ensemble", "2026, Mei 20-22", "19:00:00 - 22:00:00", "Trans Studio Mall Makassar", "11", "100"));
        daftarAcara.add(new ManajemenAcaraView.AcaraMock("Makassar Islamic & Tradisi Festival", "2026, Mei 19-21", "10:00:00 - 14:00:00", "Trans Studio Mall Makassar", "50", "50"));
        daftarAcara.add(new ManajemenAcaraView.AcaraMock("Gandrangan Bulo Rhythm Performance", "2026, Mei 20-21", "16:00:00 - 20:00:00", "Kawasan Center Point of Indonesia (CPI)", "43", "50"));
        daftarAcara.add(new ManajemenAcaraView.AcaraMock("Pelatihan Berbicara Bahasa Makassar", "2026, Mei 20", "09:00:00 - 11:00:00", "Benteng Rotterdam", "15", "20"));

        listRowsContainer.getChildren().add(buatBarisOverviewKeuangan(daftarAcara.get(0), "11", "20.000", "200.000", "1.800.000", "Selesai", "#2B8A3E"));
        listRowsContainer.getChildren().add(buatBarisOverviewKeuangan(daftarAcara.get(1), "50", "25.000", "125.000", "1.125.000", "Selesai", "#2B8A3E"));
        listRowsContainer.getChildren().add(buatBarisOverviewKeuangan(daftarAcara.get(2), "43", "50.000", "500.000", "4.500.000", "Pending", "#E67E22"));
        listRowsContainer.getChildren().add(buatBarisOverviewKeuangan(daftarAcara.get(3), "15", "30.000", "45.000", "445.000", "Selesai", "#2B8A3E"));

        overviewContent.getChildren().addAll(greetingBox, kartuContainer, tableHeader, scrollPane);
    }

    private GridPane buatBarisOverviewKeuangan(ManajemenAcaraView.AcaraMock data, String terjual, String rata2, String pajak, String total, String status, String warnaStatus) {
        GridPane row = new GridPane();
        row.setPadding(new Insets(15, 20, 15, 20));
        row.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 12; -fx-border-color: #DDE5EC; -fx-border-radius: 12; -fx-border-width: 1;");
        setupTableConstraints(row);

        VBox namaBox = new VBox(8);
        Label lblNama = new Label(data.nama);
        lblNama.setWrapText(true);
        lblNama.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #002B5B; -fx-font-family: 'Poppins';");
        
        Button btnRincian = new Button("Rincian");
        btnRincian.setStyle("-fx-background-color: #FF922B; -fx-text-fill: white; -fx-font-size: 11px; -fx-font-weight: bold; -fx-background-radius: 6; -fx-padding: 4 12 4 12; -fx-cursor: hand; -fx-font-family: 'Poppins';");
        btnRincian.setOnAction(e -> tampilkanDetail(data, total, terjual));
        
        namaBox.getChildren().addAll(lblNama, btnRincian);

        Label lblTerjual = new Label(terjual);
        lblTerjual.setStyle("-fx-font-size: 14px; -fx-text-fill: #212529; -fx-font-family: 'Poppins';");
        Label lblRata2 = new Label(rata2);
        lblRata2.setStyle("-fx-font-size: 14px; -fx-text-fill: #212529; -fx-font-family: 'Poppins';");
        Label lblPajak = new Label(pajak);
        lblPajak.setStyle("-fx-font-size: 14px; -fx-text-fill: #212529; -fx-font-family: 'Poppins';");
        Label lblTotal = new Label(total);
        lblTotal.setStyle("-fx-font-weight: bold; -fx-text-fill: #002B5B; -fx-font-family: 'Poppins';");

        HBox statusBox = new HBox(6);
        statusBox.setAlignment(Pos.CENTER_LEFT);
        Region dot = new Region();
        dot.setPrefSize(6, 6); dot.setMinSize(6, 6);
        dot.setStyle("-fx-background-color: " + warnaStatus + "; -fx-background-radius: 50;");
        Label lblStatus = new Label(status);
        lblStatus.setStyle("-fx-font-size: 13px; -fx-text-fill: " + warnaStatus + "; -fx-font-weight: bold; -fx-font-family: 'Poppins';");
        statusBox.getChildren().addAll(dot, lblStatus);

        row.add(namaBox, 0, 0);
        row.add(lblTerjual, 1, 0);
        row.add(lblRata2, 2, 0);
        row.add(lblPajak, 3, 0);
        row.add(lblTotal, 4, 0);
        row.add(statusBox, 5, 0);

        return row;
    }

    // =========================================================
    // LAYOUT 2: INITIALIZE PENDAPATAN DETAIL / RINCIAN
    // =========================================================
    private void inisialisasiDetailLayout() {
        detailContent = new VBox(20);
        detailContent.setPadding(new Insets(40, 40, 20, 40));

        Button btnKembali = new Button(" < Kembali ke Semua Pendapatan");
        btnKembali.setStyle("-fx-background-color: transparent; -fx-text-fill: #002B5B; -fx-font-weight: bold; -fx-cursor: hand; -fx-font-size: 13px; -fx-font-family: 'Poppins'; -fx-padding: 0;");
        btnKembali.setOnAction(e -> tampilkanOverview());
        detailContent.getChildren().add(btnKembali);

        VBox bannerBox = new VBox(10);
        bannerBox.setPadding(new Insets(20));
        bannerBox.setStyle("-fx-background-color: #F1F3F5; -fx-background-radius: 15;");

        HBox topRowBanner = new HBox(12);
        topRowBanner.setAlignment(Pos.CENTER_LEFT);
        lblNamaAcaraHeader = new Label();
        lblNamaAcaraHeader.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #002B5B; -fx-font-family: 'Poppins';");
        
        lblStatusBadge = new Label("Selesai");
        lblStatusBadge.setStyle("-fx-background-color: #E2F0D9; -fx-text-fill: #385723; -fx-font-size: 11px; -fx-font-weight: bold; -fx-padding: 3 10 3 10; -fx-background-radius: 8; -fx-font-family: 'Poppins';");
        topRowBanner.getChildren().addAll(lblNamaAcaraHeader, lblStatusBadge);

        lblSubDetailAcara = new Label();
        lblSubDetailAcara.setStyle("-fx-font-size: 12px; -fx-text-fill: #556B83; -fx-font-family: 'Poppins';");
        bannerBox.getChildren().addAll(topRowBanner, lblSubDetailAcara);
        detailContent.getChildren().add(bannerBox);

        HBox tabMenuBox = new HBox();
        tabMenuBox.setPadding(new Insets(10, 0, 5, 5));
        Label tabPembeli = new Label("Daftar Pembeli");
        tabPembeli.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #FF922B; -fx-border-color: transparent transparent #FF922B transparent; -fx-border-width: 2; -fx-padding: 0 0 5 0; -fx-font-family: 'Poppins';");
        tabMenuBox.getChildren().add(tabPembeli);
        detailContent.getChildren().add(tabMenuBox);

        GridPane detailTableHeader = new GridPane();
        detailTableHeader.setPadding(new Insets(12, 20, 12, 20));
        detailTableHeader.setStyle("-fx-background-color: #E6ECF0; -fx-background-radius: 8;");
        setupDetailTableConstraints(detailTableHeader);

        String styleDH = "-fx-font-weight: bold; -fx-text-fill: #002B5B; -fx-font-size: 13px; -fx-font-family: 'Poppins';";
        Label dh1 = new Label("Nama"); dh1.setStyle(styleDH);
        Label dh2 = new Label("Kontak"); dh2.setStyle(styleDH);
        Label dh3 = new Label("Email"); dh3.setStyle(styleDH);
        // PERBAIKAN: Menghapus total pendapatan dari header, langsung melompat ke Status Pembayaran
        Label dh4 = new Label("Status Pembayaran"); dh4.setStyle(styleDH);

        detailTableHeader.add(dh1, 0, 0); 
        detailTableHeader.add(dh2, 1, 0);
        detailTableHeader.add(dh3, 2, 0); 
        detailTableHeader.add(dh4, 3, 0);
        detailContent.getChildren().add(detailTableHeader);

        // Sediakan penampung baris pembeli kosong di sini
        tabelPembeliRowsContainer = new VBox(10);

        ScrollPane scrollTabel = new ScrollPane(tabelPembeliRowsContainer);
        scrollTabel.setFitToWidth(true);
        scrollTabel.setStyle("-fx-background-color: transparent; -fx-background-insets: 0; -fx-padding: 0;");
        VBox.setVgrow(scrollTabel, Priority.ALWAYS);

        detailContent.getChildren().add(scrollTabel);
    }

    // PERBAIKAN: Parameter 'total' dihapus sepenuhnya dari penulisan baris
    private GridPane buatBarisPembeli(String inisial, String nama, String telp, String email, String status, String warnaStatus) {
        GridPane row = new GridPane();
        row.setPadding(new Insets(12, 20, 12, 20));
        row.setAlignment(Pos.CENTER_LEFT);
        row.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 10; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.02), 4, 0, 0, 1);");
        setupDetailTableConstraints(row);

        HBox profilBox = new HBox(10);
        profilBox.setAlignment(Pos.CENTER_LEFT);
        StackPane avatar = new StackPane();
        avatar.setPrefSize(32, 32); avatar.setMinSize(32, 32);
        avatar.setStyle("-fx-background-color: #E2ECF5; -fx-background-radius: 50;");
        Label lblInisial = new Label(inisial);
        lblInisial.setStyle("-fx-font-weight: bold; -fx-text-fill: #1864AB; -fx-font-size: 11px; -fx-font-family: 'Poppins';");
        avatar.getChildren().add(lblInisial);
        
        Label lblNama = new Label(nama);
        lblNama.setStyle("-fx-font-weight: bold; -fx-text-fill: #212529; -fx-font-size: 13px; -fx-font-family: 'Poppins';");
        profilBox.getChildren().addAll(avatar, lblNama);

        HBox telpBox = new HBox(6);
        telpBox.setAlignment(Pos.CENTER_LEFT);
        ImageView icTelp = dapatkanIconView("/aset/iconLuminara/icon-user.png", 13);
        Label lblTelp = new Label(telp);
        lblTelp.setStyle("-fx-text-fill: #495057; -fx-font-size: 13px; -fx-font-family: 'Poppins';");
        if (icTelp != null) telpBox.getChildren().add(icTelp);
        telpBox.getChildren().add(lblTelp);

        HBox emailBox = new HBox(6);
        emailBox.setAlignment(Pos.CENTER_LEFT);
        ImageView icEmail = dapatkanIconView("/aset/iconLuminara/icon-masuk-keluar.png", 13);
        Label lblEmail = new Label(email);
        lblEmail.setStyle("-fx-text-fill: #495057; -fx-font-size: 13px; -fx-font-family: 'Poppins';");
        if (icEmail != null) emailBox.getChildren().add(icEmail);
        emailBox.getChildren().add(lblEmail);

        HBox statusBox = new HBox(6);
        statusBox.setAlignment(Pos.CENTER_LEFT);
        Region dot = new Region();
        dot.setPrefSize(7, 7); dot.setMinSize(7, 7);
        dot.setStyle("-fx-background-color: " + warnaStatus + "; -fx-background-radius: 50;");
        
        Label lblStatusBaris = new Label(status); 
        lblStatusBaris.setStyle("-fx-text-fill: " + warnaStatus + "; -fx-font-weight: bold; -fx-font-size: 13px; -fx-font-family: 'Poppins';");
        statusBox.getChildren().addAll(dot, lblStatusBaris);

        row.add(profilBox, 0, 0); 
        row.add(telpBox, 1, 0); 
        row.add(emailBox, 2, 0);
        row.add(statusBox, 3, 0); // Menempati kolom indeks ke-3 langsung setelah Email

        return row;
    }

    private HBox buatKartuRingkasan(String title, String value, String desc) {
        HBox kartu = new HBox(15);
        kartu.setPadding(new Insets(15, 20, 15, 20));
        kartu.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 12; -fx-border-color: #E6ECF0; -fx-border-width: 1;");
        kartu.setAlignment(Pos.CENTER_LEFT);

        VBox infoBox = new VBox(2);
        Label lblTitle = new Label(title);
        lblTitle.setStyle("-fx-font-size: 11px; -fx-text-fill: #70889F; -fx-font-weight: bold; -fx-font-family: 'Poppins';");
        Label lblValue = new Label(value);
        lblValue.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #002B5B; -fx-font-family: 'Poppins';");
        Label lblDesc = new Label(desc);
        lblDesc.setStyle("-fx-font-size: 11px; -fx-text-fill: #9CB1C6; -fx-font-family: 'Poppins';");
        
        infoBox.getChildren().addAll(lblTitle, lblValue, lblDesc);
        kartu.getChildren().add(infoBox);
        return kartu;
    }

    private ImageView dapatkanIconView(String path, double ukuran) {
        try {
            InputStream stream = getClass().getResourceAsStream(path);
            if (stream != null) {
                ImageView iv = new ImageView(new Image(stream));
                iv.setFitWidth(ukuran); iv.setFitHeight(ukuran);
                iv.setPreserveRatio(true);
                return iv;
            }
        } catch (Exception e) {
            System.out.println("Gagal memuat ikon: " + path);
        }
        return null;
    }

    private void setupTableConstraints(GridPane grid) {
        ColumnConstraints c1 = new ColumnConstraints(); c1.setPercentWidth(35);
        ColumnConstraints c2 = new ColumnConstraints(); c2.setPercentWidth(12);
        ColumnConstraints c3 = new ColumnConstraints(); c3.setPercentWidth(15);
        ColumnConstraints c4 = new ColumnConstraints(); c4.setPercentWidth(13);
        ColumnConstraints c5 = new ColumnConstraints(); c5.setPercentWidth(15);
        ColumnConstraints c6 = new ColumnConstraints(); c6.setPercentWidth(10);
        grid.getColumnConstraints().setAll(c1, c2, c3, c4, c5, c6);
    }

    // PERBAIKAN: Pembagian 4 kolom dengan proporsi lebar baru agar data pembeli terentang rapi dan seimbang
    private void setupDetailTableConstraints(GridPane grid) {
        ColumnConstraints c1 = new ColumnConstraints(); c1.setPercentWidth(32); // Nama Pembeli
        ColumnConstraints c2 = new ColumnConstraints(); c2.setPercentWidth(18); // Kontak / No. Telp
        ColumnConstraints c3 = new ColumnConstraints(); c3.setPercentWidth(30); // Email
        ColumnConstraints c4 = new ColumnConstraints(); c4.setPercentWidth(20); // Status Pembayaran
        grid.getColumnConstraints().setAll(c1, c2, c3, c4);
    }
}