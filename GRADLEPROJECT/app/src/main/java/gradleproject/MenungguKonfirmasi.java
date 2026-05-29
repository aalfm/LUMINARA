package gradleproject;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane; // <--- Import ScrollPane
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class MenungguKonfirmasi {

    private VBox view;

    public MenungguKonfirmasi() {
        view = new VBox(15);
        view.setPadding(new Insets(20, 20, 20, 80)); // Jarak presisi sejajar dashboard
        view.setAlignment(Pos.TOP_LEFT);

        // 1. HEADER HALAMAN
        VBox header = new VBox(-5);
        Label lblHi = new Label("Hai, admin.");
        lblHi.getStyleClass().add("greeting-title");
        Label lblSub = new Label("Pantau terus ya . . .");
        lblSub.getStyleClass().add("greeting-subtitle");
        header.getChildren().addAll(lblHi, lblSub);

        // 2. WADAH UTAMA KONTEN (Tab Oranye + Box Biru)
        VBox contentContainer = new VBox(0);
        contentContainer.setMaxWidth(770);
        contentContainer.setPadding(new Insets(15, 0, 0, 0));
        VBox.setVgrow(contentContainer, Priority.ALWAYS); // Memaksa wadah meluas ke bawah

        // Tab Badge Oranye di bagian atas
        Label lblTab = new Label("Menunggu Konfirmasi");
        lblTab.getStyleClass().add("event-orange-tab");

        // Wadah Besar Biru Gelap
        VBox blueBox = new VBox(15);
        blueBox.getStyleClass().add("event-blue-container");
        VBox.setVgrow(blueBox, Priority.ALWAYS); // Background biru mengisi sisa layar ke bawah

        // Wadah khusus untuk menampung kartu konfirmasi (Agar bisa di-scroll)
        VBox cardContainer = new VBox(15);
        cardContainer.setStyle("-fx-background-color: transparent;");
        cardContainer.setPadding(new Insets(0, 5, 0, 0));

        // Memasukkan data dummy (Diulang 4 kali agar bisa langsung dicoba scroll-nya)
        for (int i = 0; i < 4; i++) {
            cardContainer.getChildren().add(
                createConfirmationCard(
                    "Makassar Traditional Costume Showcase",
                    "Trans Studio Mall Makassar",
                    "20-22 Mei 2026 / 19:00 - 22:00"
                )
            );
        }

        // =====================================================================
        // 3. SCROLL PANE (Di dalam Wadah Biru)
        // =====================================================================
        ScrollPane scrollTable = new ScrollPane(cardContainer);
        scrollTable.setFitToWidth(true);
        scrollTable.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollTable.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollTable.setStyle("-fx-background: transparent; -fx-background-color: transparent; -fx-border-color: transparent;");
        
        VBox.setVgrow(scrollTable, Priority.ALWAYS);
        // =====================================================================

        // Masukkan ScrollPane ke dalam blueBox
        blueBox.getChildren().add(scrollTable);
        contentContainer.getChildren().addAll(lblTab, blueBox);

        // Masukkan ke view utama (Paginasi dihapus)
        view.getChildren().addAll(header, contentContainer);
    }

    // Method Helper untuk membuat kartu konfirmasi
    private VBox createConfirmationCard(String title, String location, String dateTime) {
        VBox confirmationCard = new VBox(10);
        confirmationCard.getStyleClass().add("confirm-white-card");
        confirmationCard.setPadding(new Insets(15, 25, 20, 25));

        // Row Header internal kartu
        HBox cardHeaderRow = new HBox();
        cardHeaderRow.setAlignment(Pos.CENTER_LEFT);
        
        Label lblDescHeader = new Label("Deskripsi Acara");
        lblDescHeader.getStyleClass().add("confirm-table-header");
        
        Label lblStatusHeader = new Label("Status");
        lblStatusHeader.getStyleClass().add("confirm-table-header");
        lblStatusHeader.setPadding(new Insets(0, 45, 0, 0)); 

        Region headerSpacer = new Region();
        HBox.setHgrow(headerSpacer, Priority.ALWAYS);
        cardHeaderRow.getChildren().addAll(lblDescHeader, headerSpacer, lblStatusHeader);

        // Garis Pembatas
        Region divider = new Region();
        divider.getStyleClass().add("confirm-divider");

        // Row Body internal kartu
        HBox cardBodyRow = new HBox(20);
        cardBodyRow.setAlignment(Pos.CENTER_LEFT);

        // Bagian Teks Informasi
        VBox detailsBox = new VBox(6);
        detailsBox.setAlignment(Pos.CENTER_LEFT);

        Label hTitle = new Label("Nama Kegiatan:");
        hTitle.getStyleClass().add("event-field-header");
        Label vTitle = new Label(title);
        vTitle.getStyleClass().add("event-field-value");

        Label hLocation = new Label("Lokasi:");
        hLocation.getStyleClass().add("event-field-header");
        Label vLocation = new Label(location);
        vLocation.getStyleClass().add("event-field-value");

        Label hDateTime = new Label("Tanggal /Waktu:");
        hDateTime.getStyleClass().add("event-field-header");
        Label vDateTime = new Label(dateTime);
        vDateTime.getStyleClass().add("event-field-value");

        detailsBox.getChildren().addAll(hTitle, vTitle, hLocation, vLocation, hDateTime, vDateTime);

        Region bodySpacer = new Region();
        HBox.setHgrow(bodySpacer, Priority.ALWAYS);

        // Bagian Tombol Aksi
        HBox actionButtons = new HBox(12);
        actionButtons.setAlignment(Pos.CENTER_RIGHT);

        // Tombol Tolak: Putih dengan bayangan gelap
        Button btnTolak = new Button("Tolak");
        btnTolak.setStyle(
            "-fx-background-color: #FFFFFF; " +
            "-fx-text-fill: #1A3C5A; " +
            "-fx-background-radius: 20; " +
            "-fx-padding: 6 20 6 20; " +
            "-fx-font-family: 'Poppins'; " +
            "-fx-font-size: 12px; " +
            "-fx-font-weight: bold; " +
            "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.15), 8, 0, 0, 3);"
        );
        btnTolak.setCursor(javafx.scene.Cursor.HAND);

        // Tombol Setujui: Oranye dengan Glow
        Button btnSetujui = new Button("Setujui");
        btnSetujui.setStyle(
            "-fx-background-color: #FF9800; " +
            "-fx-text-fill: white; " +
            "-fx-background-radius: 20; " +
            "-fx-padding: 6 20 6 20; " +
            "-fx-font-family: 'Poppins'; " +
            "-fx-font-size: 12px; " +
            "-fx-font-weight: bold; " +
            "-fx-effect: dropshadow(three-pass-box, rgba(255,152,0,0.6), 8, 0, 0, 3);"
        );
        btnSetujui.setCursor(javafx.scene.Cursor.HAND);

        actionButtons.getChildren().addAll(btnTolak, btnSetujui);

        cardBodyRow.getChildren().addAll(detailsBox, bodySpacer, actionButtons);
        
        confirmationCard.getChildren().addAll(cardHeaderRow, divider, cardBodyRow);
        
        return confirmationCard;
    }

    public Parent getView() {
        return view;
    }
}