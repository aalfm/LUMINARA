package gradleproject;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane; // <--- Import ScrollPane
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class DaftarBlokirPenyelenggara {

    private VBox view;

    public DaftarBlokirPenyelenggara() {
        view = new VBox(20);
        view.setPadding(new Insets(20, 20, 20, 80)); 
        view.setAlignment(Pos.TOP_LEFT);

        // 1. HEADER
        VBox header = new VBox(-5);
        Label lblHi = new Label("Hai, admin.");
        lblHi.getStyleClass().add("greeting-title");
        Label lblSub = new Label("Pantau penyelenggara ya . . ."); 
        lblSub.getStyleClass().add("greeting-subtitle");
        header.getChildren().addAll(lblHi, lblSub);

        // 2. JUDUL HALAMAN
        HBox titleBox = new HBox(10);
        titleBox.setAlignment(Pos.CENTER_LEFT);
        
        Label lblPageTitle = new Label("Daftar Blokir");
        lblPageTitle.getStyleClass().add("section-title");
        
        titleBox.getChildren().addAll(lblPageTitle);

        // 3. WADAH DAFTAR BARIS
        VBox listContainer = new VBox(12);
        listContainer.setMaxWidth(770);
        listContainer.setStyle("-fx-background-color: transparent;");

        // Memasukkan data dummy (Diulang 4 kali agar daftar panjang dan bisa di-scroll)
        for (int i = 0; i < 4; i++) {
            listContainer.getChildren().addAll(
                createBlokirRow("Alifah Mahalini", "Diblokir hari ini pukul 12.23 WITA"),
                createBlokirRow("Ra-fly", "Diblokir hari ini pukul 07.17 WITA"),
                createBlokirRow("Alifah Mahalini", "Diblokir Senin pukul 17.47 WITA")
            );
        }

        // =====================================================================
        // 4. SCROLL PANE (Menggantikan Paginasi)
        // =====================================================================
        ScrollPane scrollTable = new ScrollPane(listContainer);
        scrollTable.setFitToWidth(true); // Memaksa lebar menyesuaikan layar
        scrollTable.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED); // Scroll vertikal otomatis
        scrollTable.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); // Scroll horizontal mati
        scrollTable.setStyle("-fx-background: transparent; -fx-background-color: transparent; -fx-border-color: transparent;");
        
        // Memaksa scroll area mengisi sisa layar ke bawah
        VBox.setVgrow(scrollTable, Priority.ALWAYS);
        // =====================================================================

        // Masukkan Header, Judul, dan ScrollTable (Paginasi dihapus)
        view.getChildren().addAll(header, titleBox, scrollTable);
    }

    // FUNGSI UNTUK MENCETAK BARIS (Tanpa Tombol Detail)
    private HBox createBlokirRow(String name, String blockTime) {
        HBox row = new HBox(15);
        row.setAlignment(Pos.CENTER_LEFT);
        row.getStyleClass().add("user-row-box"); // Pakai style kartu oranye yang sudah ada
        row.setPadding(new Insets(10, 15, 10, 15)); // Tambahan padding agar lebih rapi

        // Ikon User Outline
        Label icon = new Label("👤");
        icon.setStyle("-fx-font-size: 16px; -fx-text-fill: #0A3B5C;");

        // Nama Pengguna
        Label lblName = new Label(name);
        lblName.getStyleClass().add("user-name-text");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // Keterangan Waktu Blokir
        Label lblTime = new Label(blockTime);
        lblTime.getStyleClass().add("join-time-text");

        // Perhatikan di sini tidak ada tambahan Button Detail karena ini halaman blokir
        row.getChildren().addAll(icon, lblName, spacer, lblTime);
        return row;
    }

    public Parent getView() {
        return view;
    }
}