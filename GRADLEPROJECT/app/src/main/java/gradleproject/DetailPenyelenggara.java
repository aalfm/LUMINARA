package gradleproject;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class DetailPenyelenggara {

    private VBox view;

    public DetailPenyelenggara() {
        view = new VBox(25);
        view.setPadding(new Insets(20, 20, 20, 80));
        view.setAlignment(Pos.TOP_LEFT);

        // Header Halaman
        VBox headerBox = new VBox(-5);
        Label lblHi = new Label("Hai, admin.");
        lblHi.getStyleClass().add("greeting-title");
        Label lblSub = new Label("Pantau penyelenggara ya . . .");
        lblSub.getStyleClass().add("greeting-subtitle");
        headerBox.getChildren().addAll(lblHi, lblSub);

        Label lblDetailTitle = new Label("Detail Penyelenggara");
        lblDetailTitle.getStyleClass().add("section-title");

        VBox tableWrapper = new VBox(15); 
        tableWrapper.setMaxWidth(770);

        HBox tableHeader = new HBox();
        tableHeader.getStyleClass().add("detail-header-container");
        tableHeader.setAlignment(Pos.CENTER_LEFT);
        tableHeader.setPadding(new Insets(15, 30, 15, 30));

        Label colNama = new Label("Nama");
        colNama.getStyleClass().add("detail-header-text");
        colNama.setPrefWidth(180);

        Label colEmail = new Label("Email");
        colEmail.getStyleClass().add("detail-header-text");
        colEmail.setPrefWidth(220);

        Label colTelepon = new Label("No. Telepon");
        colTelepon.getStyleClass().add("detail-header-text");
        colTelepon.setPrefWidth(180);

        Label colKet = new Label("Keterangan");
        colKet.getStyleClass().add("detail-header-text");
        colKet.setPrefWidth(100);
        colKet.setAlignment(Pos.CENTER);

        tableHeader.getChildren().addAll(colNama, colEmail, colTelepon, colKet);

        VBox tableBody = new VBox(15);
        tableBody.getStyleClass().add("detail-body-container");
        tableBody.setPadding(new Insets(25, 25, 25, 25));

        // Data dummy untuk baris tabel penyelenggara
        tableBody.getChildren().addAll(
            createDetailRow("Alifah\nMahrani", "alfm@gmail.com", "081234567890"),
            createDetailRow("Zahwa", "zahwa@gmail.com", "081234567890"),
            createDetailRow("Syarief\nRahmat", "syarief@gmail.com", "081234567890"),
            createDetailRow("Fa'iqh\nMusharraf", "faiq@gmail.com", "081234567890")
        );

        tableWrapper.getChildren().addAll(tableHeader, tableBody);

        HBox pagination = new HBox(10);
        pagination.setAlignment(Pos.CENTER_RIGHT);
        pagination.setMaxWidth(770); 
        
        Button btnPrev = new Button("<");
        btnPrev.getStyleClass().add("btn-page-inactive");
        
        Button btnNext = new Button(">");
        btnNext.getStyleClass().add("btn-page-active");
        
        pagination.getChildren().addAll(btnPrev, btnNext);

        view.getChildren().addAll(headerBox, lblDetailTitle, tableWrapper, pagination);
    }

    private HBox createDetailRow(String name, String email, String phone) {
        HBox row = new HBox();
        row.getStyleClass().add("detail-row-card");
        row.setAlignment(Pos.CENTER_LEFT);
        row.setPadding(new Insets(15, 30, 15, 30));

        Label lblName = new Label(name);
        lblName.getStyleClass().add("detail-row-name");
        lblName.setPrefWidth(180);
        lblName.setWrapText(true);

        Label lblEmail = new Label(email);
        lblEmail.getStyleClass().add("detail-row-text");
        lblEmail.setPrefWidth(220);

        Label lblPhone = new Label(phone);
        lblPhone.getStyleClass().add("detail-row-text");
        lblPhone.setPrefWidth(180);

        HBox actionBox = new HBox();
        actionBox.setAlignment(Pos.CENTER_LEFT);
        
        Button btnBlokir = new Button("Blokir");
        btnBlokir.getStyleClass().add("btn-blokir");
        actionBox.getChildren().add(btnBlokir);

        row.getChildren().addAll(lblName, lblEmail, lblPhone, actionBox);
        return row;
    }

    public Parent getView() {
        return view;
    }
}