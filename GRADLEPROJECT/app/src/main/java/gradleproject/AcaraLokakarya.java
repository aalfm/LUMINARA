package gradleproject;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane; // 🎯 Dipastikan ter-import
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane; 
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;    

public class AcaraLokakarya {
    
    // 🎯 FIX 1: Ubah tipe view utama menjadi ScrollPane
    private ScrollPane view;

    public AcaraLokakarya() {
        VBox contentBox = new VBox(25);
        contentBox.setPadding(new Insets(20, 20, 30, 80)); 
        contentBox.setAlignment(Pos.TOP_LEFT);
        contentBox.setStyle("-fx-background-color: transparent;");

        // 1. HEADER
        VBox header = new VBox(-5);
        Label lblHi = new Label("Hai, admin.");
        lblHi.getStyleClass().add("greeting-title");
        Label lblSub = new Label("Pantau acara ya . . ."); 
        lblSub.getStyleClass().add("greeting-subtitle");
        header.getChildren().addAll(lblHi, lblSub);

        // 2. JUDUL KATEGORI
        Label lblTitle = new Label("Kategori Lokakarya");
        lblTitle.setStyle("-fx-font-family: 'Poppins'; -fx-font-weight: bold; -fx-font-size: 16px; -fx-text-fill: #1A3C5A;");

        // 3. WADAH TABEL
        VBox tableBox = new VBox(0);
        tableBox.setMaxWidth(770);
        tableBox.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 8; -fx-border-color: #D3D9DE; -fx-border-radius: 8;");
        // 🎯 FIX 2: Hapus VBox.setVgrow pada tableBox agar tidak ada tumpang tindih transparan

        // HEADER TABEL
        HBox tableHeader = new HBox();
        tableHeader.setStyle("-fx-background-color: #D3D9DE; -fx-background-radius: 8 8 0 0;"); 
        tableHeader.setAlignment(Pos.CENTER_LEFT);
        tableHeader.setPadding(new Insets(12, 25, 12, 25)); 

        Label colNama = new Label("Nama");
        colNama.setStyle("-fx-text-fill: #5A7184; -fx-font-weight: bold;");
        colNama.setPrefWidth(400); 

        Label colDetail = new Label("Detail");
        colDetail.setStyle("-fx-text-fill: #5A7184; -fx-font-weight: bold;");
        colDetail.setPrefWidth(150); 
        colDetail.setAlignment(Pos.CENTER); 

        Label colStatus = new Label("Status");
        colStatus.setStyle("-fx-text-fill: #5A7184; -fx-font-weight: bold;");
        colStatus.setPrefWidth(150); 
        colStatus.setAlignment(Pos.CENTER); 

        tableHeader.getChildren().addAll(colNama, colDetail, colStatus);

        // ISI TABEL
        VBox tableBody = new VBox(15);
        tableBody.setPadding(new Insets(20, 25, 20, 25));
        tableBody.setStyle("-fx-background-color: transparent;");

        // AMBIL DATA DARI DATABASE
        gradleproject.dao.EventDAO eventDAO = new gradleproject.dao.EventDAO();
        java.util.List<gradleproject.models.Event> daftarLokakarya = eventDAO.findByCategory("Lokakarya");

        boolean adaDataDraft = false;

        for (gradleproject.models.Event acara : daftarLokakarya) {
            if (acara.getStatus().equalsIgnoreCase("Draft")) {
                tableBody.getChildren().add(createRow(acara));
                adaDataDraft = true;
            }
        }

        if (!adaDataDraft) {
            Label lblKosong = new Label("Tidak ada acara baru yang menunggu persetujuan.");
            lblKosong.setStyle("-fx-font-family: 'Poppins'; -fx-text-fill: #A0A9B5; -fx-font-style: italic;");
            tableBody.getChildren().add(lblKosong);
        }

        // 4. SUB-SCROLL PANE
        ScrollPane scrollTable = new ScrollPane(tableBody);
        scrollTable.setFitToWidth(true); 
        scrollTable.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED); 
        scrollTable.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); 
        scrollTable.setStyle("-fx-background: transparent; -fx-background-color: transparent; -fx-border-color: transparent; -fx-background-radius: 0 0 8 8;");
        
        // 🎯 FIX 3: Kunci batas tinggi tabel agar sensor mouse normal
        scrollTable.setMinHeight(200);
        scrollTable.setMaxHeight(400);

        tableBox.getChildren().addAll(tableHeader, scrollTable);
        contentBox.getChildren().addAll(header, lblTitle, tableBox);

        // 5. BUNGKUS DENGAN SCROLLPANE UTAMA
        view = new ScrollPane(contentBox);
        view.setFitToWidth(true);
        view.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        view.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        view.setStyle("-fx-background: transparent; -fx-background-color: transparent; -fx-border-color: transparent;");
    }

    private HBox createRow(gradleproject.models.Event acara) {
        HBox row = new HBox();
        row.setAlignment(Pos.CENTER_LEFT);
        row.setPadding(new Insets(10, 0, 10, 0));

        VBox nameBox = new VBox(3);
        nameBox.setPrefWidth(400); 
        
        Label lblTitle = new Label(acara.getTitle());
        lblTitle.setStyle("-fx-font-family: 'Poppins'; -fx-font-weight: bold; -fx-text-fill: #1A3C5A; -fx-font-size: 13px;");
        
        Label lblLocation = new Label(acara.getCategory()); 
        lblLocation.setStyle("-fx-font-family: 'Poppins'; -fx-text-fill: #5A7184; -fx-font-size: 11px;");
        
        nameBox.getChildren().addAll(lblTitle, lblLocation);

        HBox detailBox = new HBox();
        detailBox.setPrefWidth(150);
        detailBox.setAlignment(Pos.CENTER);
        
        Button btnLihat = new Button("Lihat");
        btnLihat.setStyle("-fx-background-color: #FF9800; -fx-text-fill: white; -fx-background-radius: 20; -fx-padding: 5 15; -fx-font-family: 'Poppins'; -fx-font-size: 12px; -fx-effect: dropshadow(three-pass-box, rgba(255,152,0,0.6), 8, 0, 0, 3);");
        btnLihat.setCursor(javafx.scene.Cursor.HAND);
        
        // 🎯 FIX 4: Pastikan melempar objek (acara) ke metode jembatan
        btnLihat.setOnAction(event -> {
            if (DashboardAdmin.getInstance() != null) {
                DashboardAdmin.getInstance().pindahKeDetailAcaraLokakarya(acara);
            }
        });
        
        detailBox.getChildren().add(btnLihat);

        // KOLOM 3: STATUS
        StackPane statusContainer = new StackPane();
        statusContainer.setPrefWidth(150);

        VBox actionButtonsBox = new VBox(6);
        actionButtonsBox.setAlignment(Pos.CENTER);
        
        Button btnTerima = new Button("Terima");
        btnTerima.setStyle("-fx-background-color: #FF9800; -fx-text-fill: white; -fx-background-radius: 20; -fx-padding: 5 15; -fx-font-family: 'Poppins'; -fx-font-size: 12px; -fx-effect: dropshadow(three-pass-box, rgba(255,152,0,0.6), 8, 0, 0, 3);");
        btnTerima.setCursor(javafx.scene.Cursor.HAND);
        btnTerima.setPrefWidth(85); 
        
        Button btnTolak = new Button("Tolak");
        btnTolak.setStyle("-fx-background-color: #FFFFFF; -fx-text-fill: #1A3C5A; -fx-background-radius: 20; -fx-padding: 5 15; -fx-font-family: 'Poppins'; -fx-font-size: 12px; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.15), 8, 0, 0, 3);");
        btnTolak.setCursor(javafx.scene.Cursor.HAND);
        btnTolak.setPrefWidth(85); 

        actionButtonsBox.getChildren().addAll(btnTerima, btnTolak);

        HBox resultStatusBox = new HBox(8);
        resultStatusBox.setAlignment(Pos.CENTER);
        Circle dotIndicator = new Circle(4);
        Label lblStatusText = new Label();
        lblStatusText.setStyle("-fx-font-family: 'Poppins'; -fx-font-weight: bold; -fx-font-size: 12px; -fx-text-fill: #1A3C5A;");
        
        resultStatusBox.getChildren().addAll(dotIndicator, lblStatusText);
        resultStatusBox.setVisible(false);  
        resultStatusBox.setManaged(false); 

        btnTerima.setOnAction(e -> {
            gradleproject.dao.EventDAO eventDAO = new gradleproject.dao.EventDAO();
            boolean suksesUpdate = eventDAO.updateStatus(acara.getId(), "Active"); 
            if (suksesUpdate) {
                actionButtonsBox.setVisible(false);
                actionButtonsBox.setManaged(false);
                dotIndicator.setStyle("-fx-fill: #4CAF50;"); 
                lblStatusText.setText("Diterima");
                resultStatusBox.setVisible(true);
                resultStatusBox.setManaged(true);
            }
        });

        btnTolak.setOnAction(e -> {
            gradleproject.dao.EventDAO eventDAO = new gradleproject.dao.EventDAO();
            boolean suksesUpdate = eventDAO.updateStatus(acara.getId(), "Rejected"); 
            if (suksesUpdate) {
                actionButtonsBox.setVisible(false);
                actionButtonsBox.setManaged(false);
                dotIndicator.setStyle("-fx-fill: #FF9800;"); 
                lblStatusText.setText("Ditolak");
                resultStatusBox.setVisible(true);
                resultStatusBox.setManaged(true);
            }
        });

        statusContainer.getChildren().addAll(actionButtonsBox, resultStatusBox);
        row.getChildren().addAll(nameBox, detailBox, statusContainer);
        
        return row;
    }

    public Parent getView() {
        return view;
    }
}