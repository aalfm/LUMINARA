package gradleproject;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane; // <--- Import ScrollPane
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class AktivitasTerbaru {

    private VBox view;

    public AktivitasTerbaru() {
        view = new VBox(20);
        view.setPadding(new Insets(20, 20, 20, 80)); // Jarak konsisten dengan halaman daftar lainnya
        view.setAlignment(Pos.TOP_LEFT);

        // 1. HEADER HALAMAN
        VBox header = new VBox(-5);
        Label lblHi = new Label("Hai, admin.");
        lblHi.getStyleClass().add("greeting-title");
        Label lblSub = new Label("Gimana hari ini . . .");
        lblSub.getStyleClass().add("greeting-subtitle");
        header.getChildren().addAll(lblHi, lblSub);

        // 2. JUDUL HALAMAN
        Label lblPageTitle = new Label("Aktivitas Terbaru");
        lblPageTitle.getStyleClass().add("section-title");

        // 3. DAFTAR LOG AKTIVITAS (Wadah List)
        VBox listContainer = new VBox(12);
        listContainer.setMaxWidth(770);
        listContainer.setStyle("-fx-background-color: transparent;");

        // Memasukkan data log aktivitas (Diulang 4 kali agar memanjang ke bawah dan bisa di-scroll)
        for (int i = 0; i < 4; i++) {
            listContainer.getChildren().addAll(
                createActivityRow("/aset/iconLuminara/profil.png", "Pendaftar baru: Alifah Mahalini", "sebagai pengguna", "11.58 WITA"),
                createActivityRow("/aset/iconLuminara/keluar.png", "User diblokir: Ra-Fly", "oleh Admin", "7.17 WITA"),
                createActivityRow("/aset/iconLuminara/pengaturan.png", "Acara \"Makassar Traditional Costume Showcase\" disetujui.", "", "Kemarin"),
                createActivityRow("/aset/iconLuminara/profil.png", "Pendaftar baru: Alifah Mahalini", "sebagai pengguna", "11.58 WITA")
            );
        }

        // =====================================================================
        // 4. SCROLL PANE (Menggantikan Tombol Paginasi)
        // =====================================================================
        ScrollPane scrollTable = new ScrollPane(listContainer);
        scrollTable.setFitToWidth(true); // Memaksa wadah list menyesuaikan lebar layar
        scrollTable.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED); // Scroll vertikal muncul otomatis
        scrollTable.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); // Scroll horizontal dimatikan
        scrollTable.setStyle("-fx-background: transparent; -fx-background-color: transparent; -fx-border-color: transparent;");
        
        // Memaksa area scroll mengisi sisa ruang layar ke bawah
        VBox.setVgrow(scrollTable, Priority.ALWAYS);
        // =====================================================================

        // Masukkan Header, Judul, dan ScrollTable ke view (Paginasi dihapus)
        view.getChildren().addAll(header, lblPageTitle, scrollTable);
    }

    private HBox createActivityRow(String iconPath, String title, String subtitle, String time) {
        HBox row = new HBox(15);
        row.setAlignment(Pos.CENTER_LEFT);
        row.getStyleClass().add("activity-row-full"); 

        ImageView iconView = new ImageView();
        try {
            Image img = new Image(getClass().getResourceAsStream(iconPath));
            iconView.setImage(img);
            iconView.setFitWidth(20);
            iconView.setFitHeight(20);
            iconView.setPreserveRatio(true);
        } catch (Exception e) {
            // Mengantisipasi jika rute icon salah agar layout tidak broken
        }

        VBox textContainer = new VBox(2);
        Label lblTitle = new Label(title);
        lblTitle.getStyleClass().add("activity-title-text");

        Label lblSub = new Label(subtitle);
        lblSub.getStyleClass().add("activity-subtitle-text");

        textContainer.getChildren().addAll(lblTitle, lblSub);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label lblTime = new Label(time);
        lblTime.getStyleClass().add("activity-time-text");

        row.getChildren().addAll(iconView, textContainer, spacer, lblTime);
        return row;
    }

    // Method ini mengembalikan VBox 'view' yang valid ke BorderPane Utama
    public Parent getView() {
        return view; 
    }
}