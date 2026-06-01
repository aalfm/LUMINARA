package gradleproject;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

// 👉 1. IMPORT DATABASE & FORMAT WAKTU DI SINI
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import gradleproject.config.DbConnect;

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

        // =====================================================================
        // 👉 3. AMBIL DATA DARI DATABASE (Menggantikan Data Dummy)
        // =====================================================================
        String query = "SELECT username, created_at FROM users WHERE UPPER(role) = 'ORGANIZER' OR UPPER(role) = 'PENYELENGGARA' ORDER BY id DESC";
        
        try (Connection conn = DbConnect.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
             
            // Menyiapkan alat untuk merapikan format tanggal (YYYY-MM-DD ke teks yang mudah dibaca)
            SimpleDateFormat dbFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat displayFormat = new SimpleDateFormat("'Bergabung pada' dd MMMM yyyy 'pukul' HH.mm 'WITA'");
            
            while (rs.next()) {
                String nama = rs.getString("username");
                String tglMentah = rs.getString("created_at");
                String tglTampil = "Baru saja bergabung"; // Nilai default jika gagal di-parse
                
                try {
                    if (tglMentah != null) {
                        Date date = dbFormat.parse(tglMentah);
                        tglTampil = displayFormat.format(date);
                    }
                } catch(Exception e) {
                    // Biarkan menggunakan nilai mentah atau default jika terjadi error konversi format
                }
                
                // Tambahkan baris baru ke layar secara dinamis
                listContainer.getChildren().add(createOrganizerRow(nama, tglTampil));
            }
        } catch (Exception e) {
            System.out.println("⚠️ Gagal memuat daftar penyelenggara: " + e.getMessage());
        }

        // =====================================================================
        // 4. SCROLL PANE
        // =====================================================================
        ScrollPane scrollTable = new ScrollPane(listContainer);
        scrollTable.setFitToWidth(true); // Memaksa isi tabel menyesuaikan lebar layar
        scrollTable.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED); // Scroll vertikal otomatis
        scrollTable.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); // Scroll horizontal mati
        scrollTable.setStyle("-fx-background: transparent; -fx-background-color: transparent; -fx-border-color: transparent;");
        
        // Memaksa area scroll mengisi sisa ruang layar ke bawah
        VBox.setVgrow(scrollTable, Priority.ALWAYS);
        // =====================================================================

        // Masukkan semua elemen ke dalam view
        view.getChildren().addAll(header, lblPageTitle, scrollTable);
    }

    private HBox createOrganizerRow(String name, String joinTime) {
        HBox row = new HBox(15);
        row.setAlignment(Pos.CENTER_LEFT);
        row.getStyleClass().add("user-row-box"); 
        row.setPadding(new Insets(10, 15, 10, 15)); 

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
        btnDetail.getStyleClass().add("btn-detail"); 
        btnDetail.setCursor(javafx.scene.Cursor.HAND);

        // ✅ 3. SEKARANG LOGIKA INI SUDAH BISA MEMBACA VARIABEL dariBeranda
        btnDetail.setOnAction(event -> {
            if (DashboardAdmin.getInstance() != null) {
                if (dariBeranda) {
                    DashboardAdmin.getInstance().pindahKeProfilPenyelenggara(); 
                } else {
                    DashboardAdmin.getInstance().pindahKeDetailPenyelenggara(); 
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