package gradleproject;

import gradleproject.dao.TransactionDAO;
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

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Map;

public class DetailPendapatanAdmin {

    private VBox view;
    private TransactionDAO transactionDAO;
    private NumberFormat rupiahFormat;

    public DetailPendapatanAdmin() {
        // Inisialisasi DAO dan Format Rupiah
        transactionDAO = new TransactionDAO();
        rupiahFormat = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        rupiahFormat.setMaximumFractionDigits(0);

        view = new VBox(25);
        view.setPadding(new Insets(40, 50, 40, 50)); 
        view.setAlignment(Pos.TOP_LEFT);
        view.setStyle("-fx-background-color: #F8F9FA;");

        // 1. TOP HEADER ROW
        HBox headerRow = new HBox();
        headerRow.setAlignment(Pos.CENTER_LEFT);

        Label lblTitle = new Label("Pendapatan (Pajak 10%)");
        lblTitle.setStyle("-fx-font-family: 'Poppins'; -fx-font-weight: bold; -fx-font-size: 26px; -fx-text-fill: #0A3B5C;");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // Filter Tahun (Saat ini di-set statis ke 2026 sebagai default)
        int tahunAktif = 2026; 
        Button btnYear = new Button(tahunAktif + "  >");
        btnYear.setStyle("-fx-background-color: #FF9800; -fx-text-fill: white; -fx-font-family: 'Poppins'; -fx-font-weight: bold; -fx-font-size: 12px; -fx-background-radius: 8; -fx-padding: 5 15;");
        btnYear.setCursor(javafx.scene.Cursor.HAND);

        headerRow.getChildren().addAll(lblTitle, spacer, btnYear);

        // =====================================================================
        // 2. KARTU PUTIH UTAMA LIST BULANAN 
        // =====================================================================
        VBox whiteCard = new VBox(20);
        whiteCard.setPadding(new Insets(40, 45, 40, 45));
        whiteCard.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 20; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.04), 10, 0, 0, 4);");
        VBox.setVgrow(whiteCard, Priority.ALWAYS);

        GridPane gridPendapatan = new GridPane();
        gridPendapatan.setHgap(40); 
        gridPendapatan.setVgap(15); 
        gridPendapatan.setAlignment(Pos.TOP_CENTER);

        // 🔥 AMBIL DATA PENDAPATAN DARI DATABASE
        Map<Integer, Double> monthlyTax = transactionDAO.getMonthlyTaxRevenue(tahunAktif);

        // Array nama bulan
        String[] namaBulan = {"Jan", "Feb", "Mar", "Apr", "Mei", "Jun", "Jul", "Agu", "Sep", "Okt", "Nov", "Des"};

        // Loop untuk menyusun 12 bulan ke dalam Grid (2 Kolom)
        for (int i = 0; i < 12; i++) {
            int nomorBulan = i + 1;
            
            // Ambil total pajak, jika null/tidak ada transaksi maka default 0.0
            double totalPajak = monthlyTax.getOrDefault(nomorBulan, 0.0);
            
            // Format ke string Rupiah (Contoh: "Rp 15.000")
            String formattedAmount = rupiahFormat.format(totalPajak).replace("Rp", "Rp");

            // Tentukan posisi kolom dan baris
            int kolom = (i < 6) ? 0 : 1; // 0-5 di kiri, 6-11 di kanan
            int baris = (i < 6) ? i : (i - 6);

            gridPendapatan.add(createMonthRow(namaBulan[i] + ":", formattedAmount), kolom, baris);
        }

        // Mengatur agar kedua kolom melebar secara seimbang (50:50)
        gridPendapatan.getChildren().forEach(node -> GridPane.setHgrow(node, Priority.ALWAYS));

        whiteCard.getChildren().add(gridPendapatan);
        view.getChildren().addAll(headerRow, whiteCard);
    }

    // Method Helper 
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