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

public class ManajemenPengguna {

    private VBox view;

    public ManajemenPengguna() {
        view = new VBox(25);
        view.setPadding(new Insets(20, 20, 20, 80)); 
        view.setAlignment(Pos.TOP_LEFT);

        // 1. HEADER HALAMAN
        VBox header = new VBox(-5);
        Label lblHi = new Label("Hai, admin.");
        lblHi.getStyleClass().add("greeting-title");
        Label lblSub = new Label("Pantau pengguna ya . . .");
        lblSub.getStyleClass().add("greeting-subtitle");
        header.getChildren().addAll(lblHi, lblSub);

        // 2. MINI SUMMARY CARDS SECTION
        HBox cardsRow = new HBox(20);
        cardsRow.setAlignment(Pos.TOP_LEFT);

        VBox cardTotal = createMiniCard("👤", "TOTAL PENGGUNA", "19", "box-gray", event -> {
            System.out.println("➤ 1. KARTU KLIK: Berhasil mendeteksi klik!");
            if (Dashboard.getInstance() != null) {
                System.out.println("➤ 2. KONEKSI: Menghubungi Dashboard...");
                Dashboard.getInstance().pindahKeDaftarPengguna();
            } else {
                System.out.println("❌ ERROR: Jembatan Dashboard terputus (null)!");
            }
        });
        
        VBox cardBlokir = createMiniCard("🚫", "AKUN DIBLOKIR", "3", "box-orange", event -> {
            if (Dashboard.getInstance() != null) {
                Dashboard.getInstance().pindahKeDaftarBlokirPengguna();
            }
        });
        
        cardsRow.getChildren().addAll(cardTotal, cardBlokir);

        // 3. TABLE CONTAINER (Wadah Tabel Utama)
        VBox tableBox = new VBox(0);
        tableBox.getStyleClass().add("management-table-container");
        tableBox.setMaxWidth(770);
        // 👉 PERBAIKAN 1: Background putih & Border radius utuh pada container tabel
        tableBox.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 8; -fx-border-color: #D3D9DE; -fx-border-radius: 8;");
        VBox.setVgrow(tableBox, Priority.ALWAYS); // Memaksa wadah tabel meluas ke bawah

        HBox tableHeader = new HBox();
        tableHeader.getStyleClass().add("management-table-header");
        tableHeader.setAlignment(Pos.CENTER_LEFT);
        tableHeader.setPadding(new Insets(12, 25, 12, 25)); 
        // 👉 PERBAIKAN 2: Radius melengkung HANYA di atas
        tableHeader.setStyle("-fx-background-color: #D3D9DE; -fx-background-radius: 8 8 0 0;");

        Label colNama = new Label("Nama");
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

        // Memasukkan data pengguna (Diulang 4 kali agar tabel bisa di-scroll)
        for (int i = 0; i < 4; i++) {
            tableBody.getChildren().addAll(
                createTableRow("Alifah Mahrani", "alfm@gmail.com", "2026, Januari 1", "Diterima", "#4CAF50"),
                createTableRow("Zahwa", "zahwa@gmail.com", "2026, Januari 1", "Diblokir", "#FF9800"),
                createTableRow("Syarief Rahmat", "syarief@gmail.com", "2026, Februari 28", "Diterima", "#4CAF50"),
                createTableRow("Fa'iqh Musharraf", "faiq@gmail.com", "2026, Mei 1", "Diterima", "#4CAF50")
            );
        }

        // =====================================================================
        // 4. SCROLL PANE (Pengganti Tombol Footer "Lihat Detail")
        // =====================================================================
        ScrollPane scrollTable = new ScrollPane(tableBody);
        scrollTable.setFitToWidth(true); 
        // 👉 PERBAIKAN 3: Sembunyikan scrollbar vertikal & horizontal
        scrollTable.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); 
        scrollTable.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); 
        // 👉 PERBAIKAN 4: Radius melengkung HANYA di bawah area scroll
        scrollTable.setStyle("-fx-background: transparent; -fx-background-color: transparent; -fx-border-color: transparent; -fx-background-radius: 0 0 8 8;");
        
        VBox.setVgrow(scrollTable, Priority.ALWAYS);
        // =====================================================================

        // Masukkan Header dan ScrollTable ke Wadah Utama (Tombol Footer dihapus)
        tableBox.getChildren().addAll(tableHeader, scrollTable);

        view.getChildren().addAll(header, cardsRow, tableBox);
    }

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