package gradleproject;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class ProfilPengguna {

    private VBox view;

    public ProfilPengguna() {
        view = new VBox(25);
        view.setPadding(new Insets(20, 20, 20, 80));
        view.setAlignment(Pos.TOP_LEFT);

        // Header Halaman
        VBox headerBox = new VBox(-5);
        Label lblHi = new Label("Hai, admin.");
        lblHi.getStyleClass().add("greeting-title");
        Label lblSub = new Label("Gimana hari ini . . .");
        lblSub.getStyleClass().add("greeting-subtitle");
        headerBox.getChildren().addAll(lblHi, lblSub);

        // KARTU ORANYE (Wadah Paling Belakang)
        VBox orangeCard = new VBox();
        orangeCard.getStyleClass().add("card-orange-wrapper");
        orangeCard.setMaxWidth(770);

        Label lblDetailTitle = new Label("Detail Pengguna");
        lblDetailTitle.getStyleClass().add("detail-card-title");
        VBox.setMargin(lblDetailTitle, new Insets(15, 20, 15, 25));

        // KARTU BIRU GELAP
        VBox blueCard = new VBox();
        blueCard.getStyleClass().add("card-blue-wrapper");
        VBox.setVgrow(blueCard, Priority.ALWAYS);

        // KARTU PUTIH (Area Konten Data)
        VBox whiteCard = new VBox();
        whiteCard.getStyleClass().add("card-white-content");

        GridPane grid = new GridPane();
        grid.setVgap(15);
        
        ColumnConstraints colData = new ColumnConstraints();
        colData.setHgrow(Priority.ALWAYS); 
        ColumnConstraints colStatus = new ColumnConstraints();
        colStatus.setMinWidth(200);
        colStatus.setHalignment(HPos.CENTER);
        grid.getColumnConstraints().addAll(colData, colStatus);

        Label lblData = new Label("Data");
        lblData.getStyleClass().add("detail-inner-header");
        GridPane.setHalignment(lblData, HPos.CENTER); 

        Label lblStatus = new Label("Status");
        lblStatus.getStyleClass().add("detail-inner-header");

        grid.add(lblData, 0, 0);
        grid.add(lblStatus, 1, 0);

        Region separatorLine = new Region();
        separatorLine.getStyleClass().add("detail-separator-line");
        GridPane.setColumnSpan(separatorLine, 2); 
        grid.add(separatorLine, 0, 1);

        VBox dataBox = new VBox(8);
        dataBox.setPadding(new Insets(10, 0, 10, 60)); 

        dataBox.getChildren().addAll(
            createLabel("Nama Pengguna", "detail-data-title"),
            createLabel("Alifah Mahalini", "detail-data-value"),
            createRegionSpacer(10), 
            createLabel("Email", "detail-data-title"),
            createLabel("alfm@gmail.com", "detail-data-value"),
            createRegionSpacer(10),
            createLabel("Nomor Telepon", "detail-data-title"),
            createLabel("081234567890", "detail-data-value")
        );

        VBox statusBox = new VBox();
        statusBox.setAlignment(Pos.CENTER);
        Button btnAktif = new Button("Aktif");
        btnAktif.getStyleClass().add("btn-status-aktif");
        statusBox.getChildren().add(btnAktif);

        grid.add(dataBox, 0, 2);
        grid.add(statusBox, 1, 2);

        whiteCard.getChildren().add(grid);
        blueCard.getChildren().add(whiteCard);
        orangeCard.getChildren().addAll(lblDetailTitle, blueCard);

        // Pagination (< >)
        HBox pagination = new HBox(10);
        pagination.setAlignment(Pos.CENTER_RIGHT);
        pagination.setMaxWidth(770); 
        
        Button btnPrev = new Button("<");
        btnPrev.getStyleClass().add("btn-page-inactive");
        Button btnNext = new Button(">");
        btnNext.getStyleClass().add("btn-page-active");
        
        pagination.getChildren().addAll(btnPrev, btnNext);

        view.getChildren().addAll(headerBox, orangeCard, pagination);
    }

    private Label createLabel(String text, String cssClass) {
        Label lbl = new Label(text);
        lbl.getStyleClass().add(cssClass);
        return lbl;
    }

    private Region createRegionSpacer(double height) {
        Region spacer = new Region();
        spacer.setMinHeight(height);
        return spacer;
    }

    public Parent getView() {
        return view;
    }
}