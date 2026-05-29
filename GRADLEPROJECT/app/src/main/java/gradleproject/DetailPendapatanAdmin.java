package gradleproject;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class DetailPendapatanAdmin {

    private VBox view;

    public DetailPendapatanAdmin() {
        view = new VBox(25);
        view.setPadding(new Insets(40, 50, 40, 50)); // Padding longgar khas admin panel
        view.setAlignment(Pos.TOP_LEFT);
        view.setStyle("-fx-background-color: #F8F9FA;");

        // 1. TOP HEADER ROW (Judul "Pendapatan" + Filter Tahun Oranye)
        HBox headerRow = new HBox();
        headerRow.setAlignment(Pos.CENTER_LEFT);

        Label lblTitle = new Label("Pendapatan");
        lblTitle.setStyle("-fx-font-family: 'Poppins'; -fx-font-weight: bold; -fx-font-size: 26px; -fx-text-fill: #0A3B5C;");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // Tombol Filter Tahun (Sesuai mockup [2026 >])
        Button btnYear = new Button("2026  >");
        btnYear.setStyle("-fx-background-color: #FF9800; -fx-text-fill: white; -fx-font-family: 'Poppins'; -fx-font-weight: bold; -fx-font-size: 12px; -fx-background-radius: 8; -fx-padding: 5 15;");
        btnYear.setCursor(javafx.scene.Cursor.HAND);

        headerRow.getChildren().addAll(lblTitle, spacer, btnYear);

        // =====================================================================
        // 2. KARTU PUTIH UTAMA LIST BULANAN (CENTRAL CARD TWO-COLUMNS)
        // =====================================================================
        VBox whiteCard = new VBox(20);
        whiteCard.setPadding(new Insets(40, 45, 40, 45));
        whiteCard.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 20; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.04), 10, 0, 0, 4);");
        VBox.setVgrow(whiteCard, Priority.ALWAYS);

        GridPane gridPendapatan = new GridPane();
        gridPendapatan.setHgap(40); // Jarak horizontal antar kolom kanan-kiri
        gridPendapatan.setVgap(15); // Jarak vertikal antar baris bulan
        gridPendapatan.setAlignment(Pos.TOP_CENTER);

        // Kolom Kiri (Januari - Juni)
        gridPendapatan.add(createMonthRow("Jan:", "Rp7.250.000"), 0, 0);
        gridPendapatan.add(createMonthRow("Feb:", "Rp10.000.000"), 0, 1);
        gridPendapatan.add(createMonthRow("Mar:", "Rp7.775.000"), 0, 2);
        gridPendapatan.add(createMonthRow("Apr:", "Rp4.500.000"), 0, 3);
        gridPendapatan.add(createMonthRow("Mei:", "Rp7.775.000"), 0, 4);
        gridPendapatan.add(createMonthRow("Jun:", "Rp4.500.000"), 0, 5);

        // Kolom Kanan (Juli - Desember)
        gridPendapatan.add(createMonthRow("Jul:", "Rp7.250.000"), 1, 0);
        gridPendapatan.add(createMonthRow("Agu:", "Rp10.000.000"), 1, 1);
        gridPendapatan.add(createMonthRow("Sep:", "Rp7.775.000"), 1, 2);
        gridPendapatan.add(createMonthRow("Okt:", "Rp4.500.000"), 1, 3);
        gridPendapatan.add(createMonthRow("Nov:", "Rp7.775.000"), 1, 4);
        gridPendapatan.add(createMonthRow("Des:", "Rp4.500.000"), 1, 5);

        // Mengatur agar kedua kolom melebar secara seimbang (50:50)
        gridPendapatan.getChildren().forEach(node -> GridPane.setHgrow(node, Priority.ALWAYS));

        whiteCard.getChildren().add(gridPendapatan);
        view.getChildren().addAll(headerRow, whiteCard);
    }

    // Method Helper untuk membuat baris kapsul list pendapatan bulanan
    private HBox createMonthRow(String monthName, String totalAmount) {
        HBox row = new HBox(15);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setPadding(new Insets(10, 20, 10, 20));
        row.setPrefWidth(300);
        row.setStyle("-fx-background-color: #F8F9FA; -fx-border-color: #0A3B5C; -fx-border-radius: 15; -fx-background-radius: 15;");

        Label lblMonth = new Label(monthName);
        lblMonth.setStyle("-fx-font-family: 'Poppins'; -fx-font-weight: bold; -fx-font-size: 13px; -fx-text-fill: #0A3B5C;");

        Label lblAmount = new Label(totalAmount);
        lblAmount.setStyle("-fx-font-family: 'Poppins'; -fx-font-size: 13px; -fx-text-fill: #0A3B5C;");

        row.getChildren().addAll(lblMonth, lblAmount);
        return row;
    }

    public Parent getView() {
        return view;
    }
}