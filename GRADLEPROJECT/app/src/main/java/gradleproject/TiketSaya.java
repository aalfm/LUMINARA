package gradleproject;

import gradleproject.dao.EventDAO;
import gradleproject.dao.TicketDAO;
import gradleproject.models.Event;
import gradleproject.models.Ticket;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.text.SimpleDateFormat;
import java.util.List;

public class TiketSaya {

    private VBox view; 
    private VBox listContainer; 

    public TiketSaya() {
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

        // 2. SEKSYEN DETAIL TIKET
        VBox sectionTiket = new VBox(0);
        sectionTiket.setMaxWidth(800);
        VBox.setVgrow(sectionTiket, Priority.ALWAYS);

        HBox tabTiket = new HBox();
        Label lblTabTiket = new Label("Tiket Kamu");
        lblTabTiket.setStyle("-fx-background-color: #FF9800; -fx-text-fill: white; -fx-font-family: 'Poppins'; -fx-font-weight: bold; -fx-font-size: 13px; -fx-background-radius: 10 10 0 0; -fx-padding: 6 22;");
        tabTiket.getChildren().add(lblTabTiket);

        // Wadah Biru Gelap Luar
        VBox boxTiketContainer = new VBox(0);
        boxTiketContainer.setStyle("-fx-background-color: #0A3B5C; -fx-background-radius: 0 15 15 15; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.08), 10, 0, 0, 4);");
        boxTiketContainer.setPadding(new Insets(20, 20, 20, 20));
        VBox.setVgrow(boxTiketContainer, Priority.ALWAYS);

        // Kontainer Penampung List Kartu Tiket Putih
        listContainer = new VBox(15);
        listContainer.setStyle("-fx-background-color: transparent;");
        listContainer.setPadding(new Insets(5, 5, 5, 5));

        System.out.println("TiketSaya mencari tiket user = " + UserSession.getInstance().getUserId());
        muatDataTiketDariDatabase(UserSession.getInstance().getUserId());

        ScrollPane scrollInnerTiket = new ScrollPane(listContainer);
        scrollInnerTiket.setFitToWidth(true); 
        scrollInnerTiket.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); 
        scrollInnerTiket.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); 
        scrollInnerTiket.setStyle("-fx-background: transparent; -fx-background-color: transparent; -fx-border-color: transparent;");
        VBox.setVgrow(scrollInnerTiket, Priority.ALWAYS);

        boxTiketContainer.getChildren().add(scrollInnerTiket);
        sectionTiket.getChildren().addAll(tabTiket, boxTiketContainer);

        view.getChildren().addAll(welcomeHeader, sectionTiket);
    }

    private void muatDataTiketDariDatabase(int userId) {
        listContainer.getChildren().clear();

        TicketDAO ticketDAO = new TicketDAO();
        EventDAO eventDAO = new EventDAO();

        List<Ticket> daftarTiket = ticketDAO.findByUserId(userId);
        
        int tiketAktifDitampilkan = 0; // 🎯 Penghitung tiket yang benar-benar tampil

        SimpleDateFormat formatTanggal = new SimpleDateFormat("dd MMM yyyy / HH:mm");

        for (Ticket tiket : daftarTiket) {
            
            // 🎯 LOGIKA MENGHILANG: Jika tiket sudah dihadiri (1) atau dibatalkan (2), JANGAN TAMPILKAN!
            int statusKehadiran = 0;
            try {
                statusKehadiran = tiket.getIsAttended();
            } catch (Exception e) {}

            if (statusKehadiran != 0) {
                continue; // Lewati tiket ini dan lanjut ke tiket berikutnya
            }

            Event acara = eventDAO.findById(tiket.getEventId());
            String idTiketString = String.valueOf(tiket.getId());
            
            String namaAcara = (acara != null && acara.getTitle() != null) ? acara.getTitle() : "Acara Tidak Tersedia";
            String lokasiAcara = (acara != null && acara.getLocation() != null) ? acara.getLocation() : "Lokasi Tidak Diketahui";
            
            String tanggalWaktu = "TBA";
            if (acara != null && acara.getEventDate() != null) {
                tanggalWaktu = formatTanggal.format(acara.getEventDate());
            }

            VBox kartuTiket = createDetailedTicketCard(idTiketString, namaAcara, tanggalWaktu, lokasiAcara);
            listContainer.getChildren().add(kartuTiket);
            
            tiketAktifDitampilkan++; // Hitung tiket yang berhasil ditampilkan
        }

        // 🎯 CEK KOSONG: Jika semua tiket sudah dihadiri, tampilkan pesan kosong
        if (tiketAktifDitampilkan == 0) {
            Label lblKosong = new Label("Kamu belum memiliki tiket aktif saat ini.");
            lblKosong.setStyle("-fx-text-fill: #FFFFFF; -fx-font-family: 'Poppins'; -fx-font-size: 13px; -fx-font-style: italic;");
            listContainer.getChildren().add(lblKosong);
        }
    }

    private VBox createDetailedTicketCard(String id, String nama, String tanggalWaktu, String lokasi) {
        VBox card = new VBox(12);
        card.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 15; -fx-padding: 20 25 20 25; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.05), 5, 0, 0, 2);");
        card.setMaxWidth(Double.MAX_VALUE);

        GridPane grid = new GridPane();
        grid.setHgap(50); 
        grid.setVgap(12); 

        VBox blockId = new VBox(2);
        Label lblId = new Label("ID Tiket");
        lblId.setStyle("-fx-font-family: 'Poppins'; -fx-font-size: 11px; -fx-text-fill: #5A7184; -fx-font-weight: bold;");
        Label valId = new Label(id);
        valId.setStyle("-fx-font-family: 'Poppins'; -fx-font-size: 12px; -fx-text-fill: #0A3B5C;");
        blockId.getChildren().addAll(lblId, valId);

        VBox blockWaktu = new VBox(2);
        Label lblWaktu = new Label("Tanggal / Waktu");
        lblWaktu.setStyle("-fx-font-family: 'Poppins'; -fx-font-size: 11px; -fx-text-fill: #5A7184; -fx-font-weight: bold;");
        Label valWaktu = new Label(tanggalWaktu);
        valWaktu.setStyle("-fx-font-family: 'Poppins'; -fx-font-size: 12px; -fx-text-fill: #0A3B5C;");
        blockWaktu.getChildren().addAll(lblWaktu, valWaktu);

        VBox blockNama = new VBox(2);
        Label lblNama = new Label("Nama Kegiatan");
        lblNama.setStyle("-fx-font-family: 'Poppins'; -fx-font-size: 11px; -fx-text-fill: #5A7184; -fx-font-weight: bold;");
        Label valNama = new Label(nama);
        valNama.setStyle("-fx-font-family: 'Poppins'; -fx-font-size: 13px; -fx-text-fill: #0A3B5C; -fx-font-weight: bold;");
        valNama.setWrapText(true); 
        blockNama.getChildren().addAll(lblNama, valNama);

        VBox blockLokasi = new VBox(2);
        Label lblLokasi = new Label("Lokasi");
        lblLokasi.setStyle("-fx-font-family: 'Poppins'; -fx-font-size: 11px; -fx-text-fill: #5A7184; -fx-font-weight: bold;");
        Label valLokasi = new Label(lokasi);
        valLokasi.setStyle("-fx-font-family: 'Poppins'; -fx-font-size: 12px; -fx-text-fill: #0A3B5C;");
        valLokasi.setWrapText(true);
        blockLokasi.getChildren().addAll(lblLokasi, valLokasi);

        grid.add(blockId, 0, 0);
        grid.add(blockWaktu, 1, 0);
        grid.add(blockNama, 0, 1);
        grid.add(blockLokasi, 1, 1);

        GridPane.setHgrow(blockId, Priority.ALWAYS);
        GridPane.setHgrow(blockWaktu, Priority.ALWAYS);

        HBox actionBox = new HBox();
        actionBox.setAlignment(Pos.CENTER_RIGHT); 
        
        Button btnBatal = new Button("Batal");
        btnBatal.setStyle("-fx-background-color: #FF9800; -fx-text-fill: white; -fx-font-family: 'Poppins'; -fx-font-weight: bold; -fx-font-size: 11px; -fx-background-radius: 5px; -fx-padding: 5 20; -fx-cursor: hand;");

        btnBatal.setOnMouseEntered(e -> btnBatal.setStyle("-fx-background-color: #F57C00; -fx-text-fill: white; -fx-font-family: 'Poppins'; -fx-font-weight: bold; -fx-font-size: 11px; -fx-background-radius: 5px; -fx-padding: 5 20; -fx-cursor: hand;"));
        btnBatal.setOnMouseExited(e -> btnBatal.setStyle("-fx-background-color: #FF9800; -fx-text-fill: white; -fx-font-family: 'Poppins'; -fx-font-weight: bold; -fx-font-size: 11px; -fx-background-radius: 5px; -fx-padding: 5 20; -fx-cursor: hand;"));

        btnBatal.setOnAction(e -> {
            card.setVisible(false);
            card.setManaged(false);
        });

        actionBox.getChildren().add(btnBatal);
        card.getChildren().addAll(grid, actionBox);
        
        return card;
    }

    public Parent getView() {
        return view;
    }
}