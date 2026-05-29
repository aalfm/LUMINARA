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
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;

public class ManajemenPenyelenggara {

    private VBox view;

    public ManajemenPenyelenggara() {
        view = new VBox(25);
        view.setPadding(new Insets(20, 20, 20, 80)); 
        view.setAlignment(Pos.TOP_LEFT);

        // 1. HEADER HALAMAN
        VBox header = new VBox(-5);
        Label lblHi = new Label("Hai, admin.");
        lblHi.getStyleClass().add("greeting-title");
        Label lblSub = new Label("Pantau penyelenggara acara ya . . .");
        lblSub.getStyleClass().add("greeting-subtitle");
        header.getChildren().addAll(lblHi, lblSub);

        // 2. MINI SUMMARY CARDS SECTION
        HBox cardsRow = new HBox(20);
        cardsRow.setAlignment(Pos.TOP_LEFT);

        // Kartu Total Penyelenggara
        VBox cardTotal = createMiniCard("🏢", "TOTAL PENYELENGGARA", "7", "box-gray", event -> {
            if (Dashboard.getInstance() != null) {
                // Pastikan kamu sudah membuat fungsi jembatan ini di Dashboard
                Dashboard.getInstance().pindahKeDaftarPenyelenggara(); 
            }
        });
        
        // Kartu Penyelenggara Diblokir
        VBox cardBlokir = createMiniCard("🚫", "AKUN DIBLOKIR", "2", "box-orange", event -> {
            if (Dashboard.getInstance() != null) {
                Dashboard.getInstance().pindahKeDaftarBlokirPenyelenggara(); 
            }
        });
        
        cardsRow.getChildren().addAll(cardTotal, cardBlokir);

        // 3. TABLE CONTAINER (Wadah Tabel Utama)
        VBox tableBox = new VBox(0);
        tableBox.getStyleClass().add("management-table-container");
        tableBox.setMaxWidth(770);
        // 👉 PERBAIKAN 1: Tambahkan background putih dan border radius utuh pada wadah
        tableBox.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 8; -fx-border-color: #D3D9DE; -fx-border-radius: 8;");
        VBox.setVgrow(tableBox, Priority.ALWAYS); // Memaksa wadah tabel meluas ke bawah

        HBox tableHeader = new HBox();
        tableHeader.getStyleClass().add("management-table-header");
        tableHeader.setAlignment(Pos.CENTER_LEFT);
        tableHeader.setPadding(new Insets(12, 25, 12, 25)); 
        // 👉 PERBAIKAN 2: Radius melengkung HANYA di sudut atas (8 8 0 0)
        tableHeader.setStyle("-fx-background-color: #D3D9DE; -fx-background-radius: 8 8 0 0;");

        Label colNama = new Label("Nama Organisasi");
        colNama.getStyleClass().add("table-header-text");
        colNama.setPrefWidth(300); 

        Label colTanggal = new Label("Tanggal Bergabung");
        colTanggal.getStyleClass().add("table-header-text");
        colTanggal.setPrefWidth(270); 

        Label colStatus = new Label("Status");
        colStatus.getStyleClass().add("table-header-text");
        colStatus.setPrefWidth(150); 

        tableHeader.getChildren().addAll(colNama, colTanggal, colStatus);

        // Baris Isi Data Tabel (Body)
        VBox tableBody = new VBox(15);
        tableBody.setPadding(new Insets(20, 25, 20, 25));
        tableBody.setStyle("-fx-background-color: transparent;");

        // Memasukkan data penyelenggara acara (Diulang 4 kali agar tabel bisa di-scroll)
        for (int i = 0; i < 4; i++) {
            tableBody.getChildren().addAll(
                createTableRow("Komunitas Ilmiah Remaja", "kir.mks@gmail.com", "2026, Januari 15", "Diterima", "#4CAF50"),
                createTableRow("Vendor Abal-Abal", "scam.event@gmail.com", "2026, Februari 10", "Diblokir", "#FF9800"),
                createTableRow("HIMA Sistem Informasi", "himasi@unhas.ac.id", "2026, Maret 22", "Diterima", "#4CAF50"),
                createTableRow("Dinas Lingkungan Hidup", "dlh.makassar@gov.id", "2026, Mei 02", "Diterima", "#4CAF50")
            );
        }

        // =====================================================================
        // 4. SCROLL PANE (Pengganti Tombol Footer "Lihat Detail")
        // =====================================================================
        ScrollPane scrollTable = new ScrollPane(tableBody);
        scrollTable.setFitToWidth(true); // Memaksa isi tabel menyesuaikan lebar layar
        
        // 👉 PERBAIKAN 3: Sembunyikan scrollbar vertikal dengan NEVER
        scrollTable.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); 
        scrollTable.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); // Scroll horizontal dimatikan
        
        // 👉 PERBAIKAN 4: Radius melengkung HANYA di sudut bawah area scroll (0 0 8 8)
        scrollTable.setStyle("-fx-background: transparent; -fx-background-color: transparent; -fx-border-color: transparent; -fx-background-radius: 0 0 8 8;");
        
        // Memaksa area scroll mengisi sisa ruang kosong di layar bawah
        VBox.setVgrow(scrollTable, Priority.ALWAYS);
        // =====================================================================

        // Masukkan Header dan ScrollTable ke Wadah Utama
        tableBox.getChildren().addAll(tableHeader, scrollTable);

        // Gabungkan semua ke view
        view.getChildren().addAll(header, cardsRow, tableBox);
    }

    // Method pembuat kartu mini
    private VBox createMiniCard(String iconText, String title, String number, String boxColorClass, javafx.event.EventHandler<javafx.event.ActionEvent> onAction) {
        VBox card = new VBox(0);
        card.getStyleClass().add("dashboard-card");
        card.setPadding(new Insets(0));
        card.setPrefSize(160, 95);
        card.setMinWidth(160);

        Label icon = new Label(iconText);
        icon.getStyleClass().add("card-icon");
        icon.setStyle("-fx-font-size: 13px;");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button btnLihat = new Button("Lihat");
        btnLihat.getStyleClass().add("btn-mini-lihat");
        
        if (onAction != null) {
            btnLihat.setOnAction(onAction);
        }

        HBox topRow = new HBox(icon, spacer, btnLihat);
        topRow.setAlignment(Pos.CENTER_LEFT);
        topRow.setPadding(new Insets(8, 12, 0, 12));

        Label lblTitle = new Label(title);
        lblTitle.getStyleClass().add("card-title");
        lblTitle.setStyle("-fx-font-size: 9px;");
        VBox.setMargin(lblTitle, new Insets(3, 10, 3, 12));

        Label lblNumber = new Label(number);
        lblNumber.getStyleClass().add("card-number-text");
        lblNumber.setStyle("-fx-font-size: 18px;");

        StackPane numberBox = new StackPane(lblNumber);
        numberBox.getStyleClass().addAll("card-number-box", boxColorClass);
        numberBox.setMaxWidth(Double.MAX_VALUE);
        VBox.setVgrow(numberBox, Priority.ALWAYS);

        card.getChildren().addAll(topRow, lblTitle, numberBox);
        card.setCursor(javafx.scene.Cursor.HAND);
        
        return card;
    }

    // Method pembuat baris tabel
    private HBox createTableRow(String name, String email, String date, String status, String dotColor) {
        HBox row = new HBox();
        row.setAlignment(Pos.CENTER_LEFT);
        row.setPadding(new Insets(5, 0, 5, 0));

        VBox nameBox = new VBox(2);
        nameBox.setPrefWidth(300); 
        Label lblName = new Label(name);
        lblName.getStyleClass().add("table-row-name");
        Label lblEmail = new Label(email);
        lblEmail.getStyleClass().add("table-row-email");
        nameBox.getChildren().addAll(lblName, lblEmail);

        Label lblDate = new Label(date);
        lblDate.getStyleClass().add("table-row-date");
        lblDate.setPrefWidth(270);

        HBox statusBox = new HBox(8);
        statusBox.setAlignment(Pos.CENTER_LEFT);
        statusBox.setPrefWidth(150);
        
        Circle dot = new Circle(4);
        dot.setStyle("-fx-fill: " + dotColor + ";");
        
        Label lblStatus = new Label(status);
        lblStatus.getStyleClass().add("table-row-status");
        statusBox.getChildren().addAll(dot, lblStatus);

        row.getChildren().addAll(nameBox, lblDate, statusBox);
        return row;
    }

    public Parent getView() {
        return view;
    }
}