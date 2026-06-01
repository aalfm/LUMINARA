package gradleproject;

import gradleproject.dao.TicketDAO;
import gradleproject.models.Event;
import gradleproject.models.TicketTier;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.Parent;

public class PilihTiketView {
    private ScrollPane view;

    public PilihTiketView(Event acara) {
        VBox contentBox = new VBox(20);
        contentBox.setPadding(new Insets(30));

        Label lblTitle = new Label("Pilih Tiket: " + acara.getTitle());
        lblTitle.setStyle("-fx-font-family: 'Poppins'; -fx-font-weight: bold; -fx-font-size: 20px;");

        VBox tierList = new VBox(10);
        TicketDAO dao = new TicketDAO();
        java.util.List<TicketTier> tiers = dao.getTiersByEventId(acara.getId());

        for (TicketTier tier : tiers) {
            HBox tierRow = new HBox(20);
            tierRow.setStyle("-fx-padding: 15; -fx-background-color: white; -fx-background-radius: 10;");
            
            Label lblName = new Label(acara.getName());
            Label lblPrice = new Label("Rp " + String.format("%,.0f", tier.getPrice()));
            Button btnPilih = new Button("Pilih");
            
            btnPilih.setOnAction(e -> {
                // Pindah ke Pembayaran dengan membawa info harga
                DashboardUser.getInstance().pindahKePembayaran(acara, String.valueOf(tier.getPrice()));
            });

            tierRow.getChildren().addAll(lblName, lblPrice, btnPilih);
            tierList.getChildren().add(tierRow);
        }

        contentBox.getChildren().addAll(lblTitle, tierList);
        view = new ScrollPane(contentBox);
        view.setFitToWidth(true);
    }

    public Parent getView() { return view; }
}