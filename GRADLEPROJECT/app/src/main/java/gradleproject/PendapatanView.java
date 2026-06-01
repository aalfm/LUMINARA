package gradleproject;

import gradleproject.dao.EventDAO;
import gradleproject.dao.TicketDAO;
import gradleproject.models.Event;
import gradleproject.models.Ticket;

import java.io.InputStream;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.List;
import java.util.ArrayList;
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
    
    // Panel Layout Utama
    private VBox overviewContent;
    private VBox detailContent;
    private VBox listRowsContainer; 

    // Node Detail Konten Pendapatan Dinamis
    private Label lblNamaAcaraHeader;
    private Label lblStatusBadge;
    private Label lblSubDetailAcara;
    private VBox tabelPembeliRowsContainer;
    
    // Label Dinamis untuk Kartu Ringkasan
    private Label lblTotalTiketVal;
    private Label lblTotalPendapatanVal;

    public PendapatanView(ManajemenAcaraView mainDashboard) {
        this.mainDashboard = mainDashboard;
        
        inisialisasiOverviewLayout();
        inisialisasiDetailLayout();
        
        tampilkanOverview();
    }

    public void tampilkanOverview() {
        this.getChildren().clear();
        loadDataPendapatan(); // Tarik ulang data ter-update
        this.getChildren().add(overviewContent);
    }

    // 🎯 FIX: Sekarang Menerima Objek Event Langsung (Bukan Data Tiruan)
    public void tampilkanDetail(Event acara) {
        this.getChildren().clear();
        this.getChildren().add(detailContent);
        
        lblNamaAcaraHeader.setText(acara.getTitle());
        String tanggal = acara.getEventDate() != null ? acara.getEventDate().toString() : "-";
        String lokasi = acara.getLocation() != null ? acara.getLocation() : "-";
        lblSubDetailAcara.setText(tanggal + "   •   " + lokasi);
        
        String statusAcara = acara.getStatus() != null ? acara.getStatus() : "Draft";
        lblStatusBadge.setText(statusAcara);
        
        tabelPembeliRowsContainer.getChildren().clear();
        
        // 🎯 FIX: Ambil daftar pembeli asli dari database
        TicketDAO ticketDAO = new TicketDAO();
        List<Ticket> daftarPeserta = ticketDAO.getTicketsByEventId(acara.getId());
        
        if (daftarPeserta == null) daftarPeserta = new ArrayList<>();

        for (Ticket t : daftarPeserta) {
            String nama = t.getUserName() != null ? t.getUserName() : "Tanpa Nama";
            
            // Buat inisial otomatis dari nama asli
            String inisial = "??";
            String[] kata = nama.trim().split("\\s+");
            if (kata.length >= 2) {
                inisial = (kata[0].substring(0, 1) + kata[1].substring(0, 1)).toUpperCase();
            } else if (kata[0].length() > 0) {
                inisial = (kata[0].length() > 1) ? kata[0].substring(0, 2).toUpperCase() : kata[0].toUpperCase();
            }

            // 🎯 FIX: Cek status pembayaran dari database
            String statusDB = t.getPaymentStatus();
            String statusBayar = "Paid".equalsIgnoreCase(statusDB) ? "Lunas" : "Belum Lunas";
            String warnaStatus = "Paid".equalsIgnoreCase(statusDB) ? "#60E514" : "#FF9412"; // Hijau/Orange

            String telp = t.getUserPhone() != null ? t.getUserPhone() : "-";
            String email = t.getUserEmail() != null ? t.getUserEmail() : "-";

            tabelPembeliRowsContainer.getChildren().add(
                buatBarisPembeli(inisial, nama, telp, email, statusBayar, warnaStatus)
            );
        }
    }

    // =========================================================
    // 🎯 LOGIKA PENGAMBILAN DATA (FILTER EVENT PAID & HITUNG UANG)
    // =========================================================
    private void loadDataPendapatan() {
        listRowsContainer.getChildren().clear();
        EventDAO eventDAO = new EventDAO();
        TicketDAO ticketDAO = new TicketDAO();
        
        int organizerId = 0;
        if (mainDashboard != null && mainDashboard.getCurrentOrganizer() != null) {
            organizerId = mainDashboard.getCurrentOrganizer().getId();
        }

        List<Event> allEvents = eventDAO.findByOrganizerId(organizerId);

        int grandTotalTiket = 0;
        double grandTotalPendapatan = 0;

        if (allEvents != null) {
            for (Event e : allEvents) {
                // 1. FILTER: HANYA TAMPILKAN EVENT BERBAYAR SAJA
                if ("Paid".equalsIgnoreCase(e.getTicketType())) {
                    
                    // 2. HITUNG PENDAPATAN DARI TIKET YANG LUNAS
                    List<Ticket> tiketEventIni = ticketDAO.getTicketsByEventId(e.getId());
                    int tiketTerjualLunas = 0;
                    
                    if(tiketEventIni != null) {
                        for(Ticket t : tiketEventIni) {
                            if("Paid".equalsIgnoreCase(t.getPaymentStatus())) {
                                tiketTerjualLunas++; // Hanya hitung yang sudah bayar
                            }
                        }
                    }

                    double hargaRata2 = e.getPrice();
                    double totalKotor = hargaRata2 * tiketTerjualLunas;
                    double pajak = totalKotor * 0.10; // Potongan pajak 10% untuk Admin
                    double totalBersih = totalKotor - pajak; // Sisa uang untuk Penyelenggara

                    grandTotalTiket += tiketTerjualLunas;
                    grandTotalPendapatan += totalBersih;

                    // 3. PEMETAAN STATUS WARNA UI
                    String statusDB = e.getStatus();
                    String warnaStatus = "#E67E22"; // Kuning untuk Draft/Pending
                    if ("Active".equalsIgnoreCase(statusDB) || "Approved".equalsIgnoreCase(statusDB)) warnaStatus = "#2B8A3E"; // Hijau
                    else if ("Past".equalsIgnoreCase(statusDB) || "Rejected".equalsIgnoreCase(statusDB) || "Selesai".equalsIgnoreCase(statusDB)) warnaStatus = "#7F8C8D"; // Abu-abu

                    // 4. MASUKKAN KE DALAM TABEL
                    listRowsContainer.getChildren().add(buatBarisOverviewKeuangan(
                        e,
                        String.valueOf(tiketTerjualLunas),
                        formatRupiah(hargaRata2),
                        formatRupiah(pajak),
                        formatRupiah(totalBersih),
                        statusDB != null ? statusDB : "Draft",
                        warnaStatus
                    ));
                }
            }
        }
        
        // 5. UPDATE KARTU RINGKASAN DI ATAS TABEL
        lblTotalTiketVal.setText(String.valueOf(grandTotalTiket));
        lblTotalPendapatanVal.setText(formatRupiah(grandTotalPendapatan));
    }

    private String formatRupiah(double nominal) {
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(new Locale ("id", "ID"));
        return formatRupiah.format(nominal);
    }

    // =========================================================
    // LAYOUT 1: INITIALIZE PENDAPATAN OVERVIEW
    // =========================================================
    private void inisialisasiOverviewLayout() {
        overviewContent = new VBox(25);
        overviewContent.setPadding(new Insets(40, 40, 20, 40));

        VBox greetingBox = new VBox(5);
        Label greeting = new Label("Hai, tim.");
        greeting.getStyleClass().add("heading");
        Label subGreeting = new Label("Sudah dapat berapa . . . ?");
        subGreeting.getStyleClass().add("subheading");
        greetingBox.getChildren().addAll(greeting, subGreeting);

        lblTotalTiketVal = new Label("0");
        lblTotalPendapatanVal = new Label("Rp 0");

        HBox kartuContainer = new HBox(20);
        HBox kartuTiket = buatKartuRingkasan("TIKET TERJUAL", lblTotalTiketVal, "Total tiket terjual (Lunas)");
        HBox kartuUang = buatKartuRingkasan("TOTAL PENDAPATAN", lblTotalPendapatanVal, "Total pendapatan bersih (-10% Admin)");
        HBox.setHgrow(kartuTiket, Priority.ALWAYS);
        HBox.setHgrow(kartuUang, Priority.ALWAYS);
        kartuContainer.getChildren().addAll(kartuTiket, kartuUang);

        GridPane tableHeader = new GridPane();
        tableHeader.setPadding(new Insets(15, 20, 15, 20));
        tableHeader.setStyle("-fx-background-color: #E6ECF0; -fx-background-radius: 10;");
        setupTableConstraints(tableHeader);

        Label h1 = new Label("Nama Acara");
        Label h2 = new Label("Tiket Terjual");
        Label h3 = new Label("Harga Rata-rata");
        Label h4 = new Label("Pajak (10%)");
        Label h5 = new Label("Total Pendapatan");
        Label h6 = new Label("Status");

        String styleH = "-fx-font-weight: bold; -fx-text-fill: #002B5B; -fx-font-size: 13px; -fx-font-family: 'Poppins';";
        h1.setStyle(styleH); h2.setStyle(styleH); h3.setStyle(styleH);
        h4.setStyle(styleH); h5.setStyle(styleH); h6.setStyle(styleH);

        tableHeader.add(h1, 0, 0); tableHeader.add(h2, 1, 0); tableHeader.add(h3, 2, 0);
        tableHeader.add(h4, 3, 0); tableHeader.add(h5, 4, 0); tableHeader.add(h6, 5, 0);

        listRowsContainer = new VBox(12);
        listRowsContainer.setPadding(new Insets(10, 0, 10, 0));

        ScrollPane scrollPane = new ScrollPane(listRowsContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background-insets: 0; -fx-padding: 0;");
        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        overviewContent.getChildren().addAll(greetingBox, kartuContainer, tableHeader, scrollPane);
    }

    private GridPane buatBarisOverviewKeuangan(Event acara, String terjual, String rata2, String pajak, String total, String status, String warnaStatus) {
        GridPane row = new GridPane();
        row.setPadding(new Insets(15, 20, 15, 20));
        row.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 12; -fx-border-color: #DDE5EC; -fx-border-radius: 12; -fx-border-width: 1;");
        setupTableConstraints(row);

        VBox namaBox = new VBox(8);
        Label lblNama = new Label(acara.getTitle() != null ? acara.getTitle() : "Tanpa Judul");
        lblNama.setWrapText(true);
        lblNama.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #002B5B; -fx-font-family: 'Poppins';");
        
        Button btnRincian = new Button("Rincian");
        btnRincian.setStyle("-fx-background-color: #FF922B; -fx-text-fill: white; -fx-font-size: 11px; -fx-font-weight: bold; -fx-background-radius: 6; -fx-padding: 4 12 4 12; -fx-cursor: hand; -fx-font-family: 'Poppins';");
        // 🎯 FIX: Mem-passing objek 'acara' langsung ke halaman detail
        btnRincian.setOnAction(e -> tampilkanDetail(acara));
        
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
        
        lblStatusBadge = new Label("Status");
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
        Label dh4 = new Label("Status Pembayaran"); dh4.setStyle(styleDH);

        detailTableHeader.add(dh1, 0, 0); 
        detailTableHeader.add(dh2, 1, 0);
        detailTableHeader.add(dh3, 2, 0); 
        detailTableHeader.add(dh4, 3, 0);
        detailContent.getChildren().add(detailTableHeader);

        tabelPembeliRowsContainer = new VBox(10);
        ScrollPane scrollTabel = new ScrollPane(tabelPembeliRowsContainer);
        scrollTabel.setFitToWidth(true);
        scrollTabel.setStyle("-fx-background-color: transparent; -fx-background-insets: 0; -fx-padding: 0;");
        VBox.setVgrow(scrollTabel, Priority.ALWAYS);

        detailContent.getChildren().add(scrollTabel);
    }

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
        row.add(statusBox, 3, 0); 

        return row;
    }

    private HBox buatKartuRingkasan(String title, Label lblValue, String desc) {
        HBox kartu = new HBox(15);
        kartu.setPadding(new Insets(15, 20, 15, 20));
        kartu.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 12; -fx-border-color: #E6ECF0; -fx-border-width: 1;");
        kartu.setAlignment(Pos.CENTER_LEFT);

        VBox infoBox = new VBox(2);
        Label lblTitle = new Label(title);
        lblTitle.setStyle("-fx-font-size: 11px; -fx-text-fill: #70889F; -fx-font-weight: bold; -fx-font-family: 'Poppins';");
        
        lblValue.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #002B5B; -fx-font-family: 'Poppins';");
        
        Label lblDesc = new Label(desc);
        lblDesc.setStyle("-fx-font-size: 11px; -fx-text-fill: #9CB1C6; -fx-font-family: 'Poppins';");
        
        infoBox.getChildren().addAll(lblTitle, lblValue, lblDesc);
        kartu.getChildren().add(infoBox);
        return kartu;
    }

    private ImageView dapatkanIconView(String path, double ukuran) {
        try (InputStream stream = getClass().getResourceAsStream(path)) {
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

    private void setupDetailTableConstraints(GridPane grid) {
        ColumnConstraints c1 = new ColumnConstraints(); c1.setPercentWidth(32); 
        ColumnConstraints c2 = new ColumnConstraints(); c2.setPercentWidth(18); 
        ColumnConstraints c3 = new ColumnConstraints(); c3.setPercentWidth(30); 
        ColumnConstraints c4 = new ColumnConstraints(); c4.setPercentWidth(20); 
        grid.getColumnConstraints().setAll(c1, c2, c3, c4);
    }
}