package gradleproject;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class UlasanPage {

    public void start(Stage primaryStage) {
        // Root menggunakan StackPane untuk menampung Background Pelabuhan
        StackPane root = new StackPane();
        root.getStyleClass().add("dashboard-root");

        try {
            String bgPath = getClass().getResource("/aset/gambarLuminara/gambar-bg.png").toExternalForm();
            root.setStyle("-fx-background-image: url('" + bgPath + "'); " +
                          "-fx-background-repeat: no-repeat; " +
                          "-fx-background-size: cover; " +
                          "-fx-background-position: center center;");
        } catch (Exception e) {
            root.setStyle("-fx-background-color: #0A2540;");
        }

        HBox mainLayout = new HBox();
        mainLayout.setAlignment(Pos.CENTER_LEFT);

        // ==================== 1. SIDEBAR NAVIGATION (KIRI) ====================
        // 👉 FIX UTAMA: Hubungkan langsung ke helper tunggal agar seluruh ikon kustom & warna aktif termuat otomatis!
        VBox sidebar = SidebarHelper.createSidebar("Ulasan", primaryStage);

        // ==================== 2. MAIN CONTENT AREA (KANAN) ====================
        VBox rightArea = new VBox(20);
        rightArea.setPadding(new Insets(35, 40, 20, 40));
        HBox.setHgrow(rightArea, Priority.ALWAYS);

        // Judul Utama Halaman Ulasan sesuai Mockup
        Label lblHeaderTitle = new Label("Untuk Luminara...");
        lblHeaderTitle.getStyleClass().add("budaya-header-title"); 

        // ScrollPane agar deretan kartu testimoni bisa di-scroll dengan rapi
        ScrollPane contentScroll = new ScrollPane();
        contentScroll.getStyleClass().add("budaya-scroll-pane");
        contentScroll.setFitToWidth(true);
        VBox.setVgrow(contentScroll, Priority.ALWAYS);

        // Grid Kumpulan Kartu Ulasan (2 Kolom)
        GridPane reviewsGrid = new GridPane();
        reviewsGrid.setHgap(25);
        reviewsGrid.setVgap(20);
        reviewsGrid.setAlignment(Pos.TOP_CENTER);

        // Membuat 6 Kartu Testimonial Sesuai Gambar Referensi Anda
        VBox card1 = createReviewCard("Alfm", "A", "#14B8A6", 4, "Luminara tuh nggak cuma event app, tapi vibes culturenya dapet banget!!!");
        VBox card2 = createReviewCard("Ra-fly", "R", "#F59E0B", 4, "Finally ada platform yang bisa bikin budaya lokal keliatan keren, bukan jadul doang *_*");
        VBox card3 = createReviewCard("sutarmanN", "S", "#06B6D4", 4, "Luminara tuh bantu banget sih buat tau event budaya di Makassar, jadi gak ketinggalan acara keren");
        VBox card4 = createReviewCard("Cira", "C", "#84CC16", 5, "Ini sih solusi banget buat anak muda yang mau explore event budaya tapi gak tau info-nya di mana");
        VBox card5 = createReviewCard("Hyn", "H", "#E2E8F0", 4, "Luminara tuh literally jembatan antara event budaya Makassar dan anak muda yang butuh info cepat");
        VBox card6 = createReviewCard("Ali", "A", "#3B82F6", 4, "Luminara = solusi buat orang yang niatnya mau ikut event tapi malas cari info");

        // Menyusun posisi kartu ke dalam baris dan kolom grid (Kolom, Baris)
        reviewsGrid.add(card1, 0, 0);
        reviewsGrid.add(card2, 1, 0);
        reviewsGrid.add(card3, 0, 1);
        reviewsGrid.add(card4, 1, 1);
        reviewsGrid.add(card5, 0, 2);
        reviewsGrid.add(card6, 1, 2);

        contentScroll.setContent(reviewsGrid);
        rightArea.getChildren().addAll(lblHeaderTitle, contentScroll);

        mainLayout.getChildren().addAll(sidebar, rightArea);
        root.getChildren().add(mainLayout);

        Scene scene = new Scene(root, 1024, 720);
        try {
            scene.getStylesheets().add(getClass().getResource("/style/guest/intro.css").toExternalForm());
        } catch (Exception e) {}

        primaryStage.setTitle("Luminara - Ulasan Pengunjung");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // ❌ Metode lama createMenuButton() yang manual dan duplikat sudah dihapus bersih dari sini ❌

    // Fungsi Pabrik Pembuat Kartu Komponen Ulasan Pengunjung
    private VBox createReviewCard(String name, String initial, String avatarColor, int starCount, String reviewText) {
        VBox cardRoot = new VBox(12);
        cardRoot.getStyleClass().add("ulasan-card");
        cardRoot.setPrefWidth(330);
        cardRoot.setMaxWidth(350);
        cardRoot.setPadding(new Insets(20));

        // Baris Atas: Profil Avatar, Nama, dan Bintang Rating
        HBox headerRow = new HBox(12);
        headerRow.setAlignment(Pos.CENTER_LEFT);

        // Lingkaran Inisial Avatar
        StackPane avatarCircle = new StackPane();
        avatarCircle.setPrefSize(40, 40);
        avatarCircle.setMinSize(40, 40);
        avatarCircle.setStyle("-fx-background-color: " + avatarColor + "; -fx-background-radius: 50%;");
        
        Label lblInitial = new Label(initial);
        lblInitial.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: white;");
        avatarCircle.getChildren().add(lblInitial);

        // Susunan Teks Nama & Deretan Bintang
        VBox profileInfo = new VBox(2);
        Label lblName = new Label(name);
        lblName.getStyleClass().add("ulasan-card-name");

        // Membuat string penampung bintang emas (★)
        StringBuilder stars = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            if (i < starCount) stars.append("★");
            else stars.append("☆");
        }
        Label lblStars = new Label(stars.toString());
        lblStars.getStyleClass().add("ulasan-card-stars");

        profileInfo.getChildren().addAll(lblName, lblStars);
        headerRow.getChildren().addAll(avatarCircle, profileInfo);

        // Box Kotak Pesan Review (Bagian bawah berlatar belakang abu-abu)
        VBox commentBox = new VBox();
        commentBox.getStyleClass().add("ulasan-comment-box");
        commentBox.setPadding(new Insets(15));
        VBox.setVgrow(commentBox, Priority.ALWAYS);

        Label lblComment = new Label(reviewText);
        lblComment.getStyleClass().add("ulasan-comment-text");
        lblComment.setWrapText(true);
        commentBox.getChildren().add(lblComment);

        cardRoot.getChildren().addAll(headerRow, commentBox);
        return cardRoot;
    }
}