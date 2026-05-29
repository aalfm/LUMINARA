package gradleproject;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class LaporanPendapatan {

    private VBox view;

    public LaporanPendapatan() {
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

        // 2. BARIS JUDUL BAGIAN & BADGE TAHUN (Rata Kanan-Kiri)
        HBox titleRow = new HBox();
        titleRow.setMaxWidth(800);
        titleRow.setAlignment(Pos.CENTER_LEFT);

        Label lblSectionTitle = new Label("LAPORAN PENDAPATAN 📈");
        lblSectionTitle.setStyle("-fx-font-family: 'Poppins'; -fx-font-weight: bold; -fx-font-size: 15px; -fx-text-fill: #1A3C5A;");

        Region titleSpacer = new Region();
        HBox.setHgrow(titleSpacer, Priority.ALWAYS);

        // Badge Oranye penunjuk tahun "2026 >"
        Button btnYearBadge = new Button("2026  >");
        btnYearBadge.setStyle(
            "-fx-background-color: #FF9800; " +
            "-fx-text-fill: white; " +
            "-fx-font-family: 'Poppins'; " +
            "-fx-font-size: 12px; " +
            "-fx-font-weight: bold; " +
            "-fx-background-radius: 10; " +
            "-fx-padding: 6 18;"
        );
        btnYearBadge.setCursor(javafx.scene.Cursor.HAND);
        titleRow.getChildren().addAll(lblSectionTitle, titleSpacer, btnYearBadge);

        // 3. WADAH UTAMA (Bingkai Biru Gelap)
        VBox blueBox = new VBox();
        blueBox.setMaxWidth(800);
        blueBox.setStyle("-fx-background-color: #0A3B5C; -fx-background-radius: 15;");
        blueBox.setPadding(new Insets(25));
        VBox.setVgrow(blueBox, Priority.ALWAYS);

        // 4. KARTU PUTIH DALAM (Tempat Input Capsule Bulan)
        HBox whiteCard = new HBox(40); // Spacing antar kolom kiri dan kanan = 40px
        whiteCard.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 15; -fx-padding: 30 35 30 35;");
        whiteCard.setAlignment(Pos.CENTER);
        VBox.setVgrow(whiteCard, Priority.ALWAYS);

        // Kolom Kiri: Januari - Juni
        VBox colLeft = new VBox(12); // Jarak vertikal antar baris = 12px
        HBox.setHgrow(colLeft, Priority.ALWAYS);
        colLeft.getChildren().addAll(
            createMonthField("Jan: Rp10.775.000"),
            createMonthField("Feb: Rp10.775.000"),
            createMonthField("Mar: Rp10.775.000"),
            createMonthField("Apr: Rp10.775.000"),
            createMonthField("Mei: Rp10.775.000"),
            createMonthField("Jun: Rp10.775.000")
        );

        // Kolom Kanan: Juli - Desember
        VBox colRight = new VBox(12);
        HBox.setHgrow(colRight, Priority.ALWAYS);
        colRight.getChildren().addAll(
            createMonthField("Jul: Rp"),
            createMonthField("Agt: Rp"),
            createMonthField("Sept: Rp"),
            createMonthField("Okt: Rp"),
            createMonthField("Nov: Rp"),
            createMonthField("Des: Rp")
        );

        whiteCard.getChildren().addAll(colLeft, colRight);
        blueBox.getChildren().add(whiteCard);

        view.getChildren().addAll(header, titleRow, blueBox);
    }

    // Method Helper: Membuat capsule field bulat panjang untuk tiap bulan
    private Label createMonthField(String contentText) {
        Label field = new Label(contentText);
        field.setMaxWidth(Double.MAX_VALUE); // Memaksa field melebar rata kolom
        field.setStyle(
            "-fx-background-color: #FFFFFF; " +
            "-fx-border-color: #A0A9B5; " +
            "-fx-border-radius: 20; " +
            "-fx-background-radius: 20; " +
            "-fx-padding: 8 20; " +
            "-fx-font-family: 'Poppins'; " +
            "-fx-font-size: 13px; " +
            "-fx-text-fill: #1A3C5A;"
        );
        return field;
    }

    public Parent getView() {
        return view;
    }
}