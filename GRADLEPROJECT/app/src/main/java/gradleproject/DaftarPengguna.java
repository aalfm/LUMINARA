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
import javafx.scene.layout.VBox;

public class DaftarPengguna {

    private VBox view;
    private boolean dariBeranda; // 👈 INI TANDA PENGENALNYA

    // Konstruktor sekarang menerima parameter
    public DaftarPengguna(boolean dariBeranda) {
        this.dariBeranda = dariBeranda; 
        
        view = new VBox(20);
        view.setPadding(new Insets(20, 20, 20, 80)); 
        view.setAlignment(Pos.TOP_LEFT);

        // 1. HEADER
        VBox header = new VBox(-5);
        Label lblHi = new Label("Hai, admin.");
        lblHi.getStyleClass().add("greeting-title");
        Label lblSub = new Label("Gimana hari ini . . .");
        lblSub.getStyleClass().add("greeting-subtitle");
        header.getChildren().addAll(lblHi, lblSub);

        // 2. JUDUL HALAMAN
        Label lblPageTitle = new Label("Daftar Pengguna");
        lblPageTitle.getStyleClass().add("section-title");

        // 3. WADAH DAFTAR (Tabel)
        VBox listContainer = new VBox(12);
        listContainer.setMaxWidth(770);
        listContainer.setStyle("-fx-background-color: transparent;");

        // Memasukkan data dummy (Diulang 3 kali agar cukup panjang untuk di-scroll)
        for (int i = 0; i < 3; i++) {
            listContainer.getChildren().addAll(
                createUserRow("Alifah Mahalini", "Bergabung hari ini pukul 23.00 WITA"),
                createUserRow("Zahwa", "Bergabung hari ini pukul 20.30 WITA"),
                createUserRow("Syarief Rahmat", "Bergabung hari ini pukul 13.00 WITA"),
                createUserRow("Fa'iqh Musharraf", "Bergabung kemarin pukul 22.00 WITA")
            );
        }

        // =====================================================================
        // 4. SCROLL PANE (Menggantikan Tombol Paginasi)
        // =====================================================================
        ScrollPane scrollTable = new ScrollPane(listContainer);
        scrollTable.setFitToWidth(true); // Memaksa isi tabel menyesuaikan lebar layar
        scrollTable.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED); // Scroll vertikal otomatis
        scrollTable.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); // Scroll horizontal mati
        scrollTable.setStyle("-fx-background: transparent; -fx-background-color: transparent; -fx-border-color: transparent;");
        
        // Memaksa area scroll mengisi sisa ruang layar ke bawah
        VBox.setVgrow(scrollTable, Priority.ALWAYS);
        // =====================================================================

        // Masukkan semua elemen ke dalam view (Paginasi sudah dihapus)
        view.getChildren().addAll(header, lblPageTitle, scrollTable);
    }

    private HBox createUserRow(String name, String joinTime) {
        HBox row = new HBox(15);
        row.setAlignment(Pos.CENTER_LEFT);
        row.getStyleClass().add("user-row-box");
        // Tambahan padding agar jarak dalam baris lebih rapi (opsional, sesuaikan dengan CSS-mu)
        row.setPadding(new Insets(10, 15, 10, 15)); 

        Label icon = new Label("👤");
        icon.setStyle("-fx-font-size: 16px; -fx-text-fill: #003A6C;");

        Label lblName = new Label(name);
        lblName.getStyleClass().add("user-name-text");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label lblTime = new Label(joinTime);
        lblTime.getStyleClass().add("join-time-text");

        Button btnDetail = new Button("Detail");
        btnDetail.getStyleClass().add("btn-lihat"); 
        btnDetail.setCursor(javafx.scene.Cursor.HAND);

        // =======================================================================
        // LOGIKA PINTAR: Tombol akan mengecek dia dipanggil dari mana!
        // =======================================================================
        btnDetail.setOnAction(event -> {
            if (Dashboard.getInstance() != null) {
                if (dariBeranda) {
                    // Jika dari Beranda -> Buka Gambar 1 (Kartu Oranye)
                    Dashboard.getInstance().pindahKeProfilPengguna();
                } else {
                    // Jika dari Manajemen -> Buka Gambar 2 (Tabel Blokir)
                    Dashboard.getInstance().pindahKeDetailPengguna();
                }
            }
        });
        
        row.getChildren().addAll(icon, lblName, spacer, lblTime, btnDetail);
        return row;
    }

    public Parent getView() {
        return view;
    }
}