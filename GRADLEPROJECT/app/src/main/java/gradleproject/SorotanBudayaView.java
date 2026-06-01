package gradleproject;

import gradleproject.dao.SorotanDAO;
import gradleproject.models.Sorotan;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;

public class SorotanBudayaView extends VBox {

    // Deklarasi variabel form agar nilainya bisa diambil saat tombol diklik
    private TextField txtJudul;
    private TextArea txtDeskripsiSingkat;
    private TextArea txtDeskripsiDetail;
    private File selectedImageFile; // Menyimpan referensi file gambar yang diunggah
    private StackPane imageDropZone;

    private SorotanDAO sorotanDAO;

    public SorotanBudayaView() {
        sorotanDAO = new SorotanDAO();

        this.setPadding(new Insets(40, 40, 20, 40));
        this.setSpacing(25);
        this.setAlignment(Pos.TOP_LEFT);
        this.setStyle("-fx-background-color: #FDFBF7;");

        // 1. HEADER
        VBox headerBox = new VBox(5);
        Label lblTitle = new Label("Hai, admin.");
        lblTitle.getStyleClass().add("sorotan-title");
        Label lblSubtitle = new Label("Buat dan publikasikan sorotan budaya baru...");
        lblSubtitle.getStyleClass().add("sorotan-subtitle");
        headerBox.getChildren().addAll(lblTitle, lblSubtitle);

        // 2. KARTU KONTEN UTAMA
        HBox cardContent = new HBox(40); 
        cardContent.setPadding(new Insets(30, 30, 30, 30));
        cardContent.getStyleClass().add("sorotan-card");

        // --- SISI KIRI: FORM ---
        VBox leftForm = new VBox(15);
        HBox.setHgrow(leftForm, Priority.ALWAYS);

        Label lblDetailInfo = new Label("Detail Informasi");
        lblDetailInfo.getStyleClass().add("sorotan-section-title");

        // Inisialisasi Field (Bukan lagi VBox kosong, tapi langsung ditarik objeknya)
        txtJudul = new TextField();
        txtDeskripsiSingkat = new TextArea();
        txtDeskripsiDetail = new TextArea();

        VBox boxJudul = createFieldGroup("Judul Sorotan", "cth. Luminara Fest 2026", txtJudul, 1);
        VBox boxSingkat = createFieldGroup("Deskripsi Singkat", "Deskripsikan secara singkat (maks 50 kata)", txtDeskripsiSingkat, 2);
        VBox boxDetail = createFieldGroup("Deskripsi Detail", "Deskripsikan secara detail (maks 200 kata)", txtDeskripsiDetail, 4);

        leftForm.getChildren().addAll(lblDetailInfo, boxJudul, boxSingkat, boxDetail);

        // --- SISI KANAN: GAMBAR ---
        VBox rightImageSection = new VBox(15);
        rightImageSection.setAlignment(Pos.CENTER);
        rightImageSection.setPrefWidth(280);

        imageDropZone = new StackPane();
        imageDropZone.setPrefSize(280, 320);
        imageDropZone.getStyleClass().add("image-dropzone");
        resetImagePlaceholder(); // Set default icon

        Button btnUnggah = new Button("Unggah Gambar");
        btnUnggah.getStyleClass().add("btn-outline-primary");
        btnUnggah.setMaxWidth(Double.MAX_VALUE); 

        btnUnggah.setOnAction(e -> handleUploadImage(btnUnggah));
        rightImageSection.getChildren().addAll(imageDropZone, btnUnggah);

        cardContent.getChildren().addAll(leftForm, rightImageSection);

        // 3. BAGIAN BAWAH: TOMBOL PUBLIKASI
        HBox bottomActions = new HBox();
        bottomActions.setAlignment(Pos.CENTER_RIGHT); 
        
        Button btnPublikasi = new Button("Publikasikan Sorotan");
        btnPublikasi.getStyleClass().add("btn-primary");
        btnPublikasi.setOnMouseEntered(e -> btnPublikasi.setStyle("-fx-effect: dropshadow(gaussian, rgba(255, 152, 0, 0.4), 10, 0, 0, 4);"));
        btnPublikasi.setOnMouseExited(e -> btnPublikasi.setStyle("-fx-effect: null;"));

        // 👉 LOGIKA SIMPAN KE DATABASE
        btnPublikasi.setOnAction(e -> handlePublikasi());

        bottomActions.getChildren().add(btnPublikasi);

        this.getChildren().addAll(headerBox, cardContent, bottomActions);
    }

    // ==========================================
    // LOGIKA UPLOAD GAMBAR KE MEMORI UI
    // ==========================================
    private void handleUploadImage(Button sourceButton) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Pilih Gambar Sorotan");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("File Gambar", "*.png", "*.jpg", "*.jpeg")
        );

        File file = fileChooser.showOpenDialog(sourceButton.getScene().getWindow());
        if (file != null) {
            selectedImageFile = file; // Simpan file untuk dicopy saat publikasi
            try {
                Image image = new Image(file.toURI().toString());
                ImageView uploadedImageView = new ImageView(image);
                uploadedImageView.setFitWidth(280);
                uploadedImageView.setFitHeight(320);
                uploadedImageView.setPreserveRatio(false); 
                
                Rectangle clip = new Rectangle(280, 320);
                clip.setArcWidth(24);
                clip.setArcHeight(24);
                uploadedImageView.setClip(clip);

                imageDropZone.getChildren().clear();
                imageDropZone.getChildren().add(uploadedImageView);
            } catch (Exception ex) {
                showAlert(Alert.AlertType.ERROR, "Error", "Gagal memuat pratinjau gambar.");
            }
        }
    }

    // ==========================================
    // LOGIKA SIMPAN (PUBLIKASI)
    // ==========================================
    private void handlePublikasi() {
        String judul = txtJudul.getText().trim();
        String singkat = txtDeskripsiSingkat.getText().trim();
        String detail = txtDeskripsiDetail.getText().trim();

        if (judul.isEmpty() || singkat.isEmpty() || detail.isEmpty() || selectedImageFile == null) {
            showAlert(Alert.AlertType.WARNING, "Peringatan", "Harap isi semua kolom dan unggah gambar.");
            return;
        }

        try {
            // 1. Buat folder 'uploads/sorotan' di direktori project jika belum ada
            File destDir = new File("uploads/sorotan");
            if (!destDir.exists()) {
                destDir.mkdirs();
            }

            // 2. Salin file gambar dari komputer Admin ke dalam folder 'uploads/sorotan'
            String namaFileBaru = System.currentTimeMillis() + "_" + selectedImageFile.getName();
            File destFile = new File(destDir, namaFileBaru);
            Files.copy(selectedImageFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

            // 3. Simpan path tersebut menggunakan awalan "file:" agar JavaFX bisa membacanya nanti
            String finalImagePath = "file:" + destFile.getAbsolutePath();

            // 4. Masukkan ke Database
            Sorotan sorotanBaru = new Sorotan(0, judul, singkat, detail, finalImagePath);
            boolean sukses = sorotanDAO.insertSorotan(sorotanBaru);

            if (sukses) {
                showAlert(Alert.AlertType.INFORMATION, "Sukses", "Sorotan budaya berhasil dipublikasikan!");
                resetForm(); // Bersihkan form
            } else {
                showAlert(Alert.AlertType.ERROR, "Gagal", "Gagal menyimpan data ke database.");
            }

        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Kesalahan Sistem", "Terjadi error saat menyimpan file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ==========================================
    // METHOD HELPER UTILITY
    // ==========================================
    private VBox createFieldGroup(String labelText, String prompt, javafx.scene.control.TextInputControl inputField, int rows) {
        VBox group = new VBox(5);
        Label lbl = new Label(labelText);
        lbl.getStyleClass().add("form-label");
        
        inputField.setPromptText(prompt);
        inputField.getStyleClass().add("form-input");
        
        if (inputField instanceof TextArea) {
            ((TextArea) inputField).setPrefRowCount(rows);
            ((TextArea) inputField).setWrapText(true);
        }
        
        group.getChildren().addAll(lbl, inputField);
        return group;
    }

    private void resetForm() {
        txtJudul.clear();
        txtDeskripsiSingkat.clear();
        txtDeskripsiDetail.clear();
        selectedImageFile = null;
        resetImagePlaceholder();
    }

    private void resetImagePlaceholder() {
        imageDropZone.getChildren().clear();
        Label fallbackIcon = new Label("🖼️");
        fallbackIcon.setStyle("-fx-font-size: 50px; -fx-text-fill: #CBD5E1;");
        imageDropZone.getChildren().add(fallbackIcon);
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}