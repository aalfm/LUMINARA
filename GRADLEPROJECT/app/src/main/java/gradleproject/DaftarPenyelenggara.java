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

public class DaftarPenyelenggara {

    private VBox view;
    // ✅ 1. TANDA PENGENAL DARI HALAMAN MANA
    private boolean dariBeranda; 

    // ✅ 2. KONSTRUKTOR MENERIMA PARAMETER BOOLEAN
    public DaftarPenyelenggara(boolean dariBeranda) {
        this.dariBeranda = dariBeranda; // Simpan nilai dari parameter ke variabel
        
        view = new VBox(20);
        view.setPadding(new Insets(20, 20, 20, 80)); 
        view.setAlignment(Pos.TOP_LEFT);

        // 1. HEADER APPLICATION
        VBox header = new VBox(-5);
        Label lblHi = new Label("Hai, admin.");
        lblHi.getStyleClass().add("greeting-title");
        Label lblSub = new Label("Gimana hari ini . . .");
        lblSub.getStyleClass().add("greeting-subtitle");
        header.getChildren().addAll(lblHi, lblSub);

        // 2. JUDUL HALAMAN 
        Label lblPageTitle = new Label("Daftar Penyelenggara");
        lblPageTitle.getStyleClass().add("section-title");

        // 3. WADAH DAFTAR PENYELENGGARA
        VBox listContainer = new VBox(12);
        listContainer.setMaxWidth(770);
        listContainer.setStyle("-fx-background-color: transparent;");

        // Data Dummy Penyelenggara (Diulang 3 kali agar bisa di-scroll)
        for (int i = 0; i < 3; i++) {
            listContainer.getChildren().addAll(
                createOrganizerRow("Alifah Mahalini", "Bergabung hari ini pukul 23.00 WITA"),
                createOrganizerRow("Alifah Mahalini", "Bergabung hari ini pukul 20.00 WITA"),
                createOrganizerRow("Alifah Mahalini", "Bergabung hari ini pukul 13.08 WITA"),
                createOrganizerRow("Alifah Mahalini", "Bergabung kemarin pukul 23.00 WITA"),
                createOrganizerRow("Alifah Mahalini", "Bergabung kemarin pukul 11.00 WITA")
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

    private HBox createOrganizerRow(String name, String joinTime) {
        HBox row = new HBox(15);
        row.setAlignment(Pos.CENTER_LEFT);
        row.getStyleClass().add("user-row-box"); 
        row.setPadding(new Insets(10, 15, 10, 15)); // Tambahan padding agar jarak dalam baris lebih rapi

        // Ikon Penyelenggara 
        Label icon = new Label("👤");
        icon.setStyle("-fx-font-size: 16px; -fx-text-fill: #003A6C;");

        // Nama Penyelenggara
        Label lblName = new Label(name);
        lblName.getStyleClass().add("user-name-text");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // Waktu Gabung
        Label lblTime = new Label(joinTime);
        lblTime.getStyleClass().add("join-time-text");

        // Tombol Detail
        Button btnDetail = new Button("Detail");
        btnDetail.getStyleClass().add("btn-detail"); // Pastikan di CSS kamu ada ".btn-detail" atau ubah ke ".btn-lihat"
        btnDetail.setCursor(javafx.scene.Cursor.HAND);

        // ✅ 3. SEKARANG LOGIKA INI SUDAH BISA MEMBACA VARIABEL dariBeranda
        btnDetail.setOnAction(event -> {
            if (Dashboard.getInstance() != null) {
                if (dariBeranda) {
                    Dashboard.getInstance().pindahKeProfilPenyelenggara(); 
                } else {
                    Dashboard.getInstance().pindahKeDetailPenyelenggara(); 
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