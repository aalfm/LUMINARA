package gradleproject;

import gradleproject.dao.EventDAO;
import gradleproject.dao.TicketDAO;
import gradleproject.models.Event;
import gradleproject.models.Ticket;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.util.ArrayList;
import java.util.List;

public class PesertaView extends StackPane {

    public ManajemenAcaraView mainDashboard;
    private Label lblNamaAcaraPesertaHeader;
    private Label lblSubDetailAcaraPeserta;
    private Label lblTotalPesertaRingkasan;
    private VBox tabelPesertaRowsContainer;

    public PesertaView(ManajemenAcaraView mainDashboard) {
        this.mainDashboard = mainDashboard;
        
        if (UserSession.getInstance() == null) {
            System.out.println("SESSION NULL");
            return;
        }
        tampilkanOverview();
    }

    public void tampilkanOverview() {
        this.getChildren().clear();

        VBox container = new VBox(20);
        container.setPadding(new Insets(20, 40, 20, 40));

        Label title = new Label("Daftar Acara Anda");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #003A6C;");

        GridPane tableHeader = new GridPane();
        tableHeader.setPadding(new Insets(15, 20, 15, 20));
        tableHeader.setStyle("-fx-background-color: #E6ECF0; -fx-background-radius: 10;");
        setupOverviewTableConstraints(tableHeader);

        Label h1 = new Label("Detail Acara"); h1.setStyle("-fx-font-weight: bold; -fx-text-fill: #495057;");
        Label h2 = new Label("Waktu"); h2.setStyle("-fx-font-weight: bold; -fx-text-fill: #495057;");
        Label h3 = new Label("Jumlah Peserta"); h3.setStyle("-fx-font-weight: bold; -fx-text-fill: #495057;");
        Label h4 = new Label("Keterangan"); h4.setStyle("-fx-font-weight: bold; -fx-text-fill: #495057;");
        tableHeader.add(h1, 0, 0); tableHeader.add(h2, 1, 0); tableHeader.add(h3, 2, 0); tableHeader.add(h4, 3, 0);

        VBox listContainer = new VBox(10);
        ScrollPane scroll = new ScrollPane(listContainer);
        scroll.setFitToWidth(true);
        scroll.setStyle("-fx-background-color: transparent;");
        VBox.setVgrow(scroll, Priority.ALWAYS); 
        
        int organizerId = 0;
        if (mainDashboard != null && mainDashboard.getCurrentOrganizer() != null) {
            organizerId = mainDashboard.getCurrentOrganizer().getId();
        } else {
            System.out.println("DEBUG: Gagal! Organizer Profile belum dimuat.");
            return;
        }

        EventDAO eventDAO = new EventDAO();
        List<Event> daftarAcara = eventDAO.findByOrganizerId(organizerId);

        if (daftarAcara == null) daftarAcara = new ArrayList<>();
        System.out.println("DEBUG EVENT SIZE: " + daftarAcara.size());

        for (Event acara : daftarAcara) {
            listContainer.getChildren().add(buatBarisOverviewTabel(acara));
        }

        container.getChildren().addAll(title, tableHeader, scroll);
        this.getChildren().add(container);
    }

    private GridPane buatBarisOverviewTabel(Event acara) {
        GridPane row = new GridPane();
        row.setPadding(new Insets(15, 20, 15, 20));
        row.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 12; -fx-border-color: #E2E8F0; -fx-border-radius: 12;");
        setupOverviewTableConstraints(row);

        Label lblNama = new Label(acara.getTitle());
        lblNama.setStyle("-fx-font-weight: bold; -fx-text-fill: #003A6C; -fx-font-size: 14px;");

        String waktu = (acara.getEventDate() != null) ? acara.getEventDate().toString() : "-";
        Label lblWaktu = new Label(waktu);
        lblWaktu.setStyle("-fx-text-fill: #495057; -fx-font-size: 13px;");

        TicketDAO ticketDAO = new TicketDAO();
        int jumlahPeserta = ticketDAO.countTicketsByEventId(acara.getId()); 

        Label lblPeserta = new Label(jumlahPeserta + " / " + acara.getQuota());
        lblPeserta.setStyle("-fx-font-weight: bold; -fx-text-fill: #003A6C; -fx-font-size: 14px;");

        Button btnLihat = new Button("Lihat Peserta");
        btnLihat.setStyle("-fx-background-color: #FF922B; -fx-text-fill: white; -fx-background-radius: 8; -fx-cursor: hand; -fx-font-weight: bold;");
        btnLihat.setOnAction(e -> tampilkanDetail(acara));

        row.add(lblNama, 0, 0);
        row.add(lblWaktu, 1, 0);
        row.add(lblPeserta, 2, 0);
        row.add(btnLihat, 3, 0);

        return row;
    }

    private void setupOverviewTableConstraints(GridPane grid) {
        ColumnConstraints c1 = new ColumnConstraints(); c1.setPercentWidth(40);
        ColumnConstraints c2 = new ColumnConstraints(); c2.setPercentWidth(30);
        ColumnConstraints c3 = new ColumnConstraints(); c3.setPercentWidth(15);
        ColumnConstraints c4 = new ColumnConstraints(); c4.setPercentWidth(15);
        grid.getColumnConstraints().setAll(c1, c2, c3, c4);
    }

    public void tampilkanDetail(Event acara) {
        this.getChildren().clear();
        this.getChildren().add(getPesertaDetailContent());

        lblNamaAcaraPesertaHeader.setText(acara.getTitle());

        String lokasi = (acara.getLocation() != null) ? acara.getLocation() : "-";
        String waktu = (acara.getEventDate() != null) ? acara.getEventDate().toString() : "-";

        lblSubDetailAcaraPeserta.setText(lokasi + "    " + waktu);

        TicketDAO ticketDAO = new TicketDAO();
        List<Ticket> daftarPeserta = ticketDAO.getTicketsByEventId(acara.getId());

        if (daftarPeserta == null) daftarPeserta = new ArrayList<>();

        lblTotalPesertaRingkasan.setText(daftarPeserta.size() + " / " + acara.getQuota());

        tabelPesertaRowsContainer.getChildren().clear();

        for (Ticket t : daftarPeserta) {
            String namaAsli = t.getUserName() != null ? t.getUserName() : "Tanpa Nama";
            
            String inisial = "??";
            String[] kata = namaAsli.trim().split("\\s+");
            if (kata.length >= 2) {
                inisial = (kata[0].substring(0, 1) + kata[1].substring(0, 1)).toUpperCase();
            } else if (kata[0].length() > 0) {
                inisial = (kata[0].length() > 1) ? kata[0].substring(0, 2).toUpperCase() : kata[0].toUpperCase();
            }

            tabelPesertaRowsContainer.getChildren().add(
                buatBarisPeserta(t, inisial, namaAsli, t.getUserPhone(), t.getUserEmail())
            );
        }
    }

    private VBox getPesertaDetailContent() {
        VBox detailContent = new VBox(25);
        detailContent.setPadding(new Insets(30, 40, 30, 40));

        Button btnKembali = new Button("< Kembali ke Semua Event");
        btnKembali.setStyle("-fx-background-color: #F8F9FA; -fx-text-fill: #003A6C; -fx-font-weight: bold; " +
                            "-fx-cursor: hand; -fx-border-color: #E2E8F0; -fx-border-radius: 8; " +
                            "-fx-background-radius: 8; -fx-padding: 8 15 8 15;");
        btnKembali.setOnAction(e -> tampilkanOverview());

        HBox bannerContainer = new HBox(20);
        bannerContainer.setAlignment(Pos.CENTER_LEFT);

        VBox leftBox = new VBox(5);
        leftBox.setPadding(new Insets(15, 20, 15, 20));
        leftBox.setStyle("-fx-background-color: #E6ECF0; -fx-border-color: #003A6C; " +
                         "-fx-border-radius: 8; -fx-background-radius: 8;");
        HBox.setHgrow(leftBox, Priority.ALWAYS); 

        lblNamaAcaraPesertaHeader = new Label();
        lblNamaAcaraPesertaHeader.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #003A6C; -fx-font-family: 'Poppins';");
        
        lblSubDetailAcaraPeserta = new Label();
        lblSubDetailAcaraPeserta.setStyle("-fx-font-size: 13px; -fx-text-fill: #003A6C; -fx-font-family: 'Poppins';");
        
        leftBox.getChildren().addAll(lblNamaAcaraPesertaHeader, lblSubDetailAcaraPeserta);

        VBox rightBox = new VBox(2);
        rightBox.setAlignment(Pos.CENTER);
        rightBox.setPadding(new Insets(15, 40, 15, 40));
        rightBox.setStyle("-fx-background-color: #E6ECF0; -fx-border-color: #003A6C; " +
                          "-fx-border-radius: 8; -fx-background-radius: 8;");

        Label lblPesertaTitle = new Label("Peserta");
        lblPesertaTitle.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: #003A6C; -fx-font-family: 'Poppins';");
        
        lblTotalPesertaRingkasan = new Label();
        lblTotalPesertaRingkasan.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #003A6C; -fx-font-family: 'Poppins';");
        
        rightBox.getChildren().addAll(lblPesertaTitle, lblTotalPesertaRingkasan);

        bannerContainer.getChildren().addAll(leftBox, rightBox);

        tabelPesertaRowsContainer = new VBox(12);
        ScrollPane scrollTabel = new ScrollPane(tabelPesertaRowsContainer);
        scrollTabel.setFitToWidth(true);
        scrollTabel.setStyle("-fx-background-color: transparent; -fx-background: transparent;");
        VBox.setVgrow(scrollTabel, Priority.ALWAYS);

        detailContent.getChildren().addAll(btnKembali, bannerContainer, scrollTabel);
        return detailContent;
    }

    private HBox buatBarisPeserta(Ticket ticket, String inisial, String nama, String telp, String email) {
        HBox row = new HBox(20); 
        row.setAlignment(Pos.CENTER_LEFT);
        row.setPadding(new Insets(15, 30, 15, 30));
        row.setStyle("-fx-background-color: #F8F9FA; -fx-background-radius: 12; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.05), 10, 0, 0, 3);");

        StackPane avatar = new StackPane();
        avatar.setPrefSize(45, 45);
        avatar.setStyle("-fx-background-color: #FFD8A8; -fx-background-radius: 50;");
        Label lblInisial = new Label(inisial);
        lblInisial.setStyle("-fx-font-weight: bold; -fx-text-fill: #D9480F; -fx-font-family: 'Poppins'; -fx-font-size: 16px;");
        avatar.getChildren().add(lblInisial);

        Label lblNama = new Label(nama != null ? nama : "Tanpa Nama"); 
        lblNama.setPrefWidth(180);
        lblNama.setStyle("-fx-font-weight: bold; -fx-font-family: 'Poppins'; -fx-text-fill: #003A6C; -fx-font-size: 15px;");
        
        Label lblTelp = new Label(telp != null ? telp : "-"); 
        lblTelp.setPrefWidth(150);
        lblTelp.setStyle("-fx-font-family: 'Poppins'; -fx-text-fill: #003A6C; -fx-font-size: 14px;");
        
        Label lblEmail = new Label(email != null ? email : "-"); 
        HBox.setHgrow(lblEmail, Priority.ALWAYS); 
        lblEmail.setStyle("-fx-font-family: 'Poppins'; -fx-text-fill: #003A6C; -fx-font-size: 14px;");

        StackPane actionContainer = new StackPane();
        actionContainer.setPrefWidth(100);
        actionContainer.setAlignment(Pos.CENTER_RIGHT);

        int statusKehadiran = 0;
        try {
            statusKehadiran = ticket.getIsAttended(); // Membaca status riwayat database
        } catch (Exception e) {
            System.err.println("Metode getIsAttended tidak ditemukan di Ticket.java. Gunakan status 0.");
        }

        if (statusKehadiran == 1) {
            actionContainer.getChildren().setAll(buatStatusIndicator(true));
        } else if (statusKehadiran == 2) {
            actionContainer.getChildren().setAll(buatStatusIndicator(false));
        } else {
            VBox btnBox = new VBox(5);
            btnBox.setAlignment(Pos.CENTER);
            Button btnHadir = new Button("Hadir");
            Button btnTidak = new Button("Tidak");
            btnHadir.setPrefSize(70, 25); btnTidak.setPrefSize(70, 25);
            
            btnHadir.setStyle("-fx-background-color: #FF922B; -fx-text-fill: white; -fx-background-radius: 15; -fx-font-size: 11px; -fx-font-family: 'Poppins'; -fx-font-weight: bold; -fx-cursor: hand;");
            btnTidak.setStyle("-fx-background-color: #FFFFFF; -fx-text-fill: #718096; -fx-border-color: #CED4DA; -fx-border-radius: 15; -fx-background-radius: 15; -fx-font-size: 11px; -fx-font-family: 'Poppins'; -fx-cursor: hand;");

            TicketDAO ticketDAO = new TicketDAO();

            btnHadir.setOnAction(e -> {
                if (ticketDAO.updateKehadiran(ticket.getId(), 1)) {
                    actionContainer.getChildren().setAll(buatStatusIndicator(true));
                }
            });

            btnTidak.setOnAction(e -> {
                if (ticketDAO.updateKehadiran(ticket.getId(), 2)) {
                    actionContainer.getChildren().setAll(buatStatusIndicator(false));
                }
            });

            btnBox.getChildren().addAll(btnHadir, btnTidak);
            actionContainer.getChildren().add(btnBox);
        }

        row.getChildren().addAll(avatar, lblNama, lblTelp, lblEmail, actionContainer);
        return row; 
    }

    private HBox buatStatusIndicator(boolean isHadir) {
        HBox indicator = new HBox(8);
        indicator.setAlignment(Pos.CENTER_LEFT);
        Region bar = new Region();
        bar.setPrefSize(4, 25);
        bar.setStyle("-fx-background-color: " + (isHadir ? "#60E514" : "#FF9412") + "; -fx-background-radius: 5;");
        Label lblStatus = new Label(isHadir ? "Hadir" : "Tidak\nHadir");
        lblStatus.setStyle("-fx-font-size: 11px; -fx-font-weight: bold; -fx-text-fill: #002B5B; -fx-font-family: 'Poppins';");
        indicator.getChildren().addAll(bar, lblStatus);
        return indicator;
    }
}